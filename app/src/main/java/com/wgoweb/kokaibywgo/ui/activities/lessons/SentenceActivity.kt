package com.wgoweb.kokaibywgo.ui.activities.lessons


import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.ActivitySentenceBinding
import com.wgoweb.kokaibywgo.firebase.SentenceListener
import com.wgoweb.kokaibywgo.models.SentenceModel
import com.wgoweb.kokaibywgo.ui.activities.BaseActivity
import com.wgoweb.kokaibywgo.ui.activities.adapters.SentenceActivityAdapter
import com.wgoweb.kokaibywgo.utils.Constants
import com.wgoweb.kokaibywgo.utils.SharePreferenceHelper
import java.util.*
import kotlin.math.roundToInt


class SentenceActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySentenceBinding

    var mSectionId: String = ""
    var mSectionName: String = ""
    var mCurrentPostition: Int = 0

    private lateinit var  mSentenceItems: ArrayList<SentenceModel>

    // Timmer
    var mRestTimerDuration: Int = 2
    private var restTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySentenceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Constants.LONGEST_SENTENCE = "" // reset longest sentence to empty
        if (intent.hasExtra(Constants.INTENT_SECTION_ID)) {
            mSectionId = intent.getStringExtra(Constants.INTENT_SECTION_ID)!!
        }
        if (intent.hasExtra(Constants.INTENT_SECTION_NAME)) {
            mSectionName = intent.getStringExtra(Constants.INTENT_SECTION_NAME)!!
            binding.activityTitle.text = mSectionName
        }

        setupActionBar()
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        SentenceListener().getDataListItemForSentenceActivity(this@SentenceActivity, mSectionId)


        binding.tvNoItemsFound.visibility = View.GONE
        disabledOrEnableAutoPlayButton("btnPauseSound", false)
        binding.btnPreviousSound.setOnClickListener(this)
        binding.btnAutoSound.setOnClickListener(this)
        binding.btnPauseSound.setOnClickListener(this)
        binding.btnNextSound.setOnClickListener(this)
            // Log.i("Get Sentence from >>", "$mSectionId   $mSectionName")
    }


    // call this function from SectionListener
    fun saveSentenceToPreference(itemsList: ArrayList<SentenceModel>) {
        checkDisableButton()
        if (itemsList.size > 0) {
            val jsonString = Gson().toJson(itemsList)
            SharePreferenceHelper().setSharePreference(this@SentenceActivity, Constants.REF_SENTENCE_PREFERENCE,jsonString )
        }
    }

    // call this function from SentenceListener
    fun successItemsList(itemsList: ArrayList<SentenceModel>){
        mSentenceItems = itemsList
        // Hide the progress dialog.
        hideProgressDialog()
        if (itemsList.size > 0) {
            binding.rvViewItems.visibility = View.VISIBLE
            binding.tvNoItemsFound.visibility = View.GONE

            binding.rvViewItems.layoutManager = LinearLayoutManager(this@SentenceActivity)
            binding.rvViewItems.setHasFixedSize(true)

            val itemAdapter = SentenceActivityAdapter(this@SentenceActivity, itemsList, object:
                OnClickListener {
                override fun onClick(currentText: String) {
                    speakOut(currentText)
                }
            })
            // adapter instance is set to the recyclerview to inflate the items.
            binding.rvViewItems.adapter = itemAdapter
        } else {
            binding.rvViewItems.visibility = View.GONE
            binding.tvNoItemsFound.visibility = View.VISIBLE
        }
    }

    /**
     * Bottom Icon Action
     * */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_previous_sound -> {
                if (mCurrentPostition > 0 ) {
                    mCurrentPostition--
                    getVowelItem()
                }
                checkDisableButton()
            }

            R.id.btn_auto_sound -> {
                controlPlayBackAndPlayNextButton("btnPreviousSound", false)
                controlPlayBackAndPlayNextButton("btnNextSound", false)
                getAutoPlayItem()
            }

            R.id.btn_pause_sound -> {
                if (restTimer != null) {
                    restTimer!!.cancel()
                    mCurrentPostition--
                    controlPlayBackAndPlayNextButton("btnPreviousSound", true)
                    controlPlayBackAndPlayNextButton("btnNextSound", true)
                    enabledAutoPlayButton()

                }
                checkDisableButton()
            }

            R.id.btn_next_sound , R.id.iv_vowel -> {
                if (mCurrentPostition == mSentenceItems.size-1 ) {
                    mCurrentPostition = 0
                } else {
                    mCurrentPostition++
                }
                getVowelItem()
                checkDisableButton()
            }
        }
    }

    /**
     * Bottom icons Action
     * */

    fun playCurrentSentence(){
        if (mCurrentPostition >= 0  && mCurrentPostition <= mSentenceItems.size-1) {
            speakOut(mSentenceItems[mCurrentPostition].sentence_text)
        }
    }

    @Suppress("DEPRECATION")
    private fun getVowelItem(){ 
        if (mCurrentPostition == 0) {
            Handler().postDelayed({
                playCurrentSentence()
            },1000 )
        } else {
            playCurrentSentence()
        }

    }

    private fun getAutoPlayItem(){
        val timerInMinutes = timerPerSentence()

        restTimer = object : CountDownTimer((mSentenceItems.size * (timerInMinutes * 1000)).toLong(),(timerInMinutes * 1000).toLong()) {
            override fun onTick(millisUntilFinished: Long) {
                if (mCurrentPostition <= mSentenceItems.size-1) {
                    // Disable Button
                    disabledAutoPlayButton()
                    playCurrentSentence() 
                    mCurrentPostition++
                    if (mCurrentPostition > mSentenceItems.size-1) {
                        mCurrentPostition = 0

                    }
                }
            }
            override fun onFinish() {
                if (mCurrentPostition > mSentenceItems.size-1) {
                    controlPlayBackAndPlayNextButton("btnPreviousSound", false)
                    controlPlayBackAndPlayNextButton("btnNextSound", true)
                    enabledAutoPlayButton()
                }
            }
        }.start()
    }

    fun  timerPerSentence() : Int{
        var timerInMinutes = 3

        if (Constants.LONGEST_SENTENCE.length > 30) {
            timerInMinutes = (Constants.LONGEST_SENTENCE.length  /  8.1).roundToInt()
        }
        return timerInMinutes
    }

    private fun checkDisableButton(){
        if (mCurrentPostition == 0) {
            controlPlayBackAndPlayNextButton("btnPreviousSound", false)
            controlPlayBackAndPlayNextButton("btnNextSound", true)
        } else if (mCurrentPostition > mSentenceItems.size-1) {
            controlPlayBackAndPlayNextButton("btnPreviousSound", true)
            controlPlayBackAndPlayNextButton("btnNextSound", false)
        } else if (mCurrentPostition >= 0 || mCurrentPostition < mSentenceItems.size-1) {
            controlPlayBackAndPlayNextButton("btnPreviousSound", true)
            controlPlayBackAndPlayNextButton("btnNextSound", true)

        } else {
            controlPlayBackAndPlayNextButton("btnPreviousSound", true)
            controlPlayBackAndPlayNextButton("btnNextSound", true)
        }
    }


    private fun controlPlayBackAndPlayNextButton(buttonViwId: String, isEnable: Boolean){
        when(buttonViwId) {
            "btnPreviousSound" -> {
                if (isEnable) {
                    var drawable = ContextCompat.getDrawable(this@SentenceActivity, R.drawable.tts_play_back)
                    binding.btnPreviousSound.setImageDrawable(drawable)
                } else {
                    var drawable = ContextCompat.getDrawable(this@SentenceActivity, R.drawable.tts_play_back_disable)
                    binding.btnPreviousSound.setImageDrawable(drawable)
                }
            }
            "btnNextSound" -> {
                if (isEnable) {
                    var drawable = ContextCompat.getDrawable(this@SentenceActivity, R.drawable.tts_play_next)
                    binding.btnNextSound.setImageDrawable(drawable)
                } else {
                    var drawable = ContextCompat.getDrawable(this@SentenceActivity, R.drawable.tts_play_next_disable)
                    binding.btnNextSound.setImageDrawable(drawable)
                }
            }
        }
    }

    private fun disabledOrEnableAutoPlayButton(buttonViwId: String, isEnable: Boolean){
        when(buttonViwId) {
            "btnPauseSound" -> {
                if (isEnable) {
                    binding.btnPauseSound.visibility = View.VISIBLE
                } else {
                    binding.btnPauseSound.visibility = View.GONE
                }
            }
            "btnAutoSound" -> {
                if (isEnable) {
                    binding.btnAutoSound.visibility = View.VISIBLE
                } else {
                    binding.btnAutoSound.visibility = View.GONE
                }
            }
        }
    }

    fun disabledAutoPlayButton(){
        /*
        var drawable = ContextCompat.getDrawable(this@SentenceActivity, R.drawable.tts_play_back)
        binding.btnAutoSound.setImageDrawable(drawable)*/
        disabledOrEnableAutoPlayButton("btnAutoSound", false)
        disabledOrEnableAutoPlayButton("btnPauseSound", true)
    }
    private fun enabledAutoPlayButton(){
        /*
        binding.btnAutoSound.isEnabled = false
        var drawable = ContextCompat.getDrawable(this@SentenceActivity, R.drawable.tts_auto_play)*/
        disabledOrEnableAutoPlayButton("btnAutoSound", true)
        disabledOrEnableAutoPlayButton("btnPauseSound", false)
    }
    /**
     * Bottom Icon Action
     * */
    

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarCustom)
        binding.tvTitle.text = Constants.LESSON_TEXT//mSectionName

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_topbar_back_arrow)
        }
        binding.toolbarCustom.setNavigationOnClickListener {
            onBackPressed()
        }
    }
    public interface OnClickListener {
        fun onClick(currentText: String)
    }

}
