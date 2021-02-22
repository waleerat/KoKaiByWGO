package com.wgoweb.kokaibywgo.ui.activities.sounds


import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.ActivityAlphabetBySoundBinding
import com.wgoweb.kokaibywgo.models.AlphabetModel
import com.wgoweb.kokaibywgo.models.VowelModel
import com.wgoweb.kokaibywgo.ui.activities.BaseActivity
import com.wgoweb.kokaibywgo.ui.activities.adapters.*
import com.wgoweb.kokaibywgo.utils.Constants
import java.util.ArrayList

class AlphabetBySoundActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAlphabetBySoundBinding

    private var mAlphabetItems: ArrayList<AlphabetModel> = ArrayList<AlphabetModel>()
    private var mVowelItems: ArrayList<VowelModel> = ArrayList<VowelModel>()
    private var mSoundLevel: String = ""
    private var mSoundLevelName: String = ""
    private var mAlphabetsList: String = ""

    private var mCurrentPosition: Int = 0
    private var mCurrentSoundFileName: String = ""
    private var mVowelWithSoundLevelPosition = 0

    private var restTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlphabetBySoundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        if (intent.hasExtra(Constants.INTENT_SOUND_LEVEL)) {
            mSoundLevel = intent.getStringExtra(Constants.INTENT_SOUND_LEVEL)!!
            Constants.SELECTED_SOUND_LEVEL = mSoundLevel
        }
        if (intent.hasExtra(Constants.INTENT_SOUND_LEVEL_NAME)) {
            mSoundLevelName = intent.getStringExtra(Constants.INTENT_SOUND_LEVEL_NAME)!!
            binding.activityTitle.text = mSoundLevelName
        }

        binding.btnMenuAllAlphabets.setOnClickListener(this)
        binding.btnPlayAlphabetSound.setOnClickListener(this)
        binding.btnPreviousSound.setOnClickListener(this)
        binding.btnNextSound.setOnClickListener(this)

        checkDisableButton()
        getItemsList()
        getAlphabetsRecyclerView()

        getVowelRecyclerView()
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_play_alphabet_sound -> {
                when (mSoundLevel) {
                    "high" -> speakOut(Constants.SOUND_HIGH_TEXT)
                    "mid" -> speakOut(Constants.SOUND_MIDDLE_TEXT)
                    "low" -> speakOut(Constants.SOUND_LOW_TEXT)
                }
            }

            R.id.btn_play_alphabet_sound -> speakOut(Constants.LEARN_ALPHABET_TEXT)
            R.id.btn_play_vowel_sound -> speakOut(Constants.LEARN_VOWEL_TEXT)


            R.id.btn_previous_sound -> {
                if (mCurrentPosition > 0 ) {
                    mCurrentPosition--
                    getVowelWithFiveSoundLevels()
                }
                checkDisableButton()
            }

            R.id.btn_auto_sound -> {
                enablePlayBackAndPlayNextButton("btnPreviousSound", false)
                enablePlayBackAndPlayNextButton("btnNextSound", false)
                getAutoPlayItem()
            }

            R.id.btn_pause_sound -> {
                //Log.i("btn_pause_sound >>", "if (restTimer != null) {")
                if (restTimer != null) {
                    restTimer!!.cancel()
                    mCurrentPosition--
                    enablePlayBackAndPlayNextButton("btnPreviousSound", true)
                    enablePlayBackAndPlayNextButton("btnNextSound", true)
                }
                checkDisableButton()
            }


            R.id.btn_next_sound , R.id.iv_alphabet -> {

                getVowelWithFiveSoundLevels()
                if (mCurrentPosition == mAlphabetItems.size-1 ) {
                    mCurrentPosition = 0
                } else {
                    mCurrentPosition++
                }
                checkDisableButton()
            }
        }
    }

    fun getItemsList(){

        /** * ALPHABET LIST  isShowInSoundActivity: true, * */
        val alphabetItems: ArrayList<AlphabetModel> = Constants.getAlphabetItems(this)  // Get All Items
        alphabetItems!!.filter { it.isShowInSoundActivity && it.AlphabetClass == mSoundLevel}.forEach { alphabet ->
            mAlphabetsList += alphabet.alphabet + "  "
            mAlphabetItems.add(alphabet)
        }
        // Point to the first Alphabet
        Constants.SELECTED_ALPHABET =  mAlphabetItems[0].alphabet
        binding.tvAlphabetList.text = mAlphabetsList
        /*** VOWEL LIST  "isShowInSoundActivity": true* */
        val vowelItems: ArrayList<VowelModel> = Constants.getVowelItems(this)  // Get All Items
        vowelItems!!.filter { it.isShowInSoundActivity == true }.forEach { vowel ->
            mVowelItems.add(vowel)
        }
    }

    private fun getAlphabetsRecyclerView() {
        if (mAlphabetItems.size > 0) {
            binding.rvAlphabetDataItems.visibility = View.VISIBLE
            binding.tvAlphabetNoItemsFound.visibility = View.GONE

            val itemAlphabetAdapter = HorizontalAlphabetsSoundLevelAdapter(this, mAlphabetItems, object:
                OnClickListener {
                override fun onAlphabetClick(selectedItem: AlphabetModel) {
                    mCurrentPosition = 0
                    getVowelRecyclerView()
                }
            })
            // adapter instance is set to the recyclerview to inflate the items.
            binding.rvAlphabetDataItems.layoutManager = LinearLayoutManager(this)
            binding.rvAlphabetDataItems.setHasFixedSize(true)
            val mLayoutManager = LinearLayoutManager(applicationContext)
            mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

            mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            binding.rvAlphabetDataItems.layoutManager = mLayoutManager
            binding.rvAlphabetDataItems.itemAnimator = DefaultItemAnimator()
            binding.rvAlphabetDataItems.adapter = itemAlphabetAdapter
        } else {
            binding.rvAlphabetDataItems.visibility = View.GONE
            binding.tvAlphabetNoItemsFound.visibility = View.VISIBLE
        }
    }

    fun getVowelRecyclerView(){
        if (mVowelItems.size > 0) {
            binding.rvVowelDataItems.visibility = View.VISIBLE
            binding.rvVowelDataItems.layoutManager = LinearLayoutManager(this)
            binding.rvVowelDataItems.setHasFixedSize(true)
            val itemAdapter = AlphabetBySoundActivityAdapter(this@AlphabetBySoundActivity, mVowelItems)
            // adapter instance is set to the recyclerview to inflate the items.
            binding.rvVowelDataItems.adapter = itemAdapter
        }
    }

    fun getVowelWithFiveSoundLevels(){
        var vowelWithSoundLevelList: ArrayList<String> = ArrayList<String>()
        mVowelWithSoundLevelPosition = 0
        val v = mVowelItems[mCurrentPosition]
        when (mSoundLevel) {
            "high" -> {
                vowelWithSoundLevelList.add(v.soundLevelOne.replace("ก", Constants.SELECTED_ALPHABET))
                vowelWithSoundLevelList.add(v.soundLevelTwo.replace("ก", Constants.SELECTED_ALPHABET))
                vowelWithSoundLevelList.add(v.soundLevelThree.replace("ก", Constants.SELECTED_ALPHABET))
            }
            "mid" -> {
                vowelWithSoundLevelList.add(v.soundLevelOne.replace("ก", Constants.SELECTED_ALPHABET))
                vowelWithSoundLevelList.add(v.soundLevelTwo.replace("ก", Constants.SELECTED_ALPHABET))
                vowelWithSoundLevelList.add(v.soundLevelThree.replace("ก", Constants.SELECTED_ALPHABET))
                vowelWithSoundLevelList.add(v.soundLevelFour.replace("ก", Constants.SELECTED_ALPHABET))
                vowelWithSoundLevelList.add(v.soundLevelFive.replace("ก", Constants.SELECTED_ALPHABET))

            }
            "low" -> {
                vowelWithSoundLevelList.add(v.soundLevelOne.replace("ก", Constants.SELECTED_ALPHABET))
                vowelWithSoundLevelList.add(v.soundLevelTwo.replace("ก", Constants.SELECTED_ALPHABET))
                vowelWithSoundLevelList.add(v.soundLevelThree.replace("ก", Constants.SELECTED_ALPHABET))
            }
        }

        restTimer = object : CountDownTimer((vowelWithSoundLevelList.size * 1000).toLong(),1000) {
            override fun onTick(millisUntilFinished: Long) {
                setEnablePreviousAndNextSoundButton(false)
                if (mVowelWithSoundLevelPosition <= vowelWithSoundLevelList.size-1) {
                    speakOut(vowelWithSoundLevelList[mVowelWithSoundLevelPosition])
                    Log.i("SoundLevelPosition01 >>", vowelWithSoundLevelList[mVowelWithSoundLevelPosition])
                    mVowelWithSoundLevelPosition++
                }
            }
            override fun onFinish() {
                setEnablePreviousAndNextSoundButton(true)
                checkDisableButton()
            }
        }.start()
    }

    /** Disable button until speech finish*/
    fun setEnablePreviousAndNextSoundButton(isEnable: Boolean) {
        if (isEnable) {
            binding.btnPreviousSound.isEnabled = true
            binding.btnNextSound.isEnabled = true
        } else {
            binding.btnPreviousSound.isEnabled = false
            binding.btnNextSound.isEnabled = false
            enablePlayBackAndPlayNextButton("btnPreviousSound", false)
            enablePlayBackAndPlayNextButton("btnNextSound", false)
        }
    }



    /** PLAY ICONS FUNCTIONS*/
    private fun getAutoPlayItem(){
        restTimer = object : CountDownTimer((mVowelItems.size * 6000).toLong(),6000) {
            override fun onTick(millisUntilFinished: Long) {
                if (mCurrentPosition <= mVowelItems.size-1) {
                    getVowelWithFiveSoundLevels()
                    // Play Sounds
                    getVowelWithFiveSoundLevels()

                    mCurrentPosition++
                    if (mCurrentPosition > mVowelItems.size-1) {
                        mCurrentPosition = 0
                    }
                }
            }

            override fun onFinish() {
                if (mCurrentPosition > mVowelItems.size-1) {
                    enablePlayBackAndPlayNextButton("btnPreviousSound", false)
                    enablePlayBackAndPlayNextButton("btnNextSound", true)
                }
            }
        }.start()
    }


    private fun checkDisableButton(){
        if (mCurrentPosition == 0) {
            enablePlayBackAndPlayNextButton("btnPreviousSound", false)
            enablePlayBackAndPlayNextButton("btnNextSound", true)
        } else if (mCurrentPosition > mVowelItems.size-1) {
            enablePlayBackAndPlayNextButton("btnPreviousSound", true)
            enablePlayBackAndPlayNextButton("btnNextSound", false)
        } else if (mCurrentPosition >= 0 || mCurrentPosition < mVowelItems.size-1) {
            enablePlayBackAndPlayNextButton("btnPreviousSound", true)
            enablePlayBackAndPlayNextButton("btnNextSound", true)

        } else {
            enablePlayBackAndPlayNextButton("btnPreviousSound", true)
            enablePlayBackAndPlayNextButton("btnNextSound", true)
        }
    }

    private fun enablePlayBackAndPlayNextButton(buttonViwId: String, isEnable: Boolean){
        when(buttonViwId) {
            "btnPreviousSound" -> {
                if (isEnable) {
                    var drawable = ContextCompat.getDrawable(this@AlphabetBySoundActivity, R.drawable.tts_play_back)
                    binding.btnPreviousSound.setImageDrawable(drawable)
                    binding.btnPreviousSound.isEnabled = true
                } else {
                    var drawable = ContextCompat.getDrawable(this@AlphabetBySoundActivity, R.drawable.tts_play_back_disable)
                    binding.btnPreviousSound.setImageDrawable(drawable)
                    binding.btnPreviousSound.isEnabled = false
                }
            }
            "btnNextSound" -> {
                if (isEnable) {
                    var drawable = ContextCompat.getDrawable(this@AlphabetBySoundActivity, R.drawable.tts_play_next)
                    binding.btnNextSound.setImageDrawable(drawable)
                    binding.btnNextSound.isEnabled = true
                } else {
                    var drawable = ContextCompat.getDrawable(this@AlphabetBySoundActivity, R.drawable.tts_play_next_disable)
                    binding.btnNextSound.setImageDrawable(drawable)
                    binding.btnNextSound.isEnabled = false
                }
            }
        }
    }

    /** PLAY ICONS FUNCTIONS*/


    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarCustom)
        binding.tvTitle.text = Constants.MENU_MAIN_HOME

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_topbar_back_arrow)
        }
        binding.toolbarCustom.setNavigationOnClickListener {
            if (restTimer != null) {
                restTimer!!.cancel()
            }
            onBackPressed()
        }
    }

    interface OnClickListener {
        fun onAlphabetClick(selectedItem: AlphabetModel) {

        }
    }
}