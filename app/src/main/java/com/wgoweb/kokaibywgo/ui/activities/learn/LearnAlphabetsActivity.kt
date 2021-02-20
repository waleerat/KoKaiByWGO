package com.wgoweb.kokaibywgo.ui.activities.learn

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.ActivityLearnAlphabetsBinding
import com.wgoweb.kokaibywgo.models.AlphabetModel
import com.wgoweb.kokaibywgo.ui.activities.BaseActivity
import com.wgoweb.kokaibywgo.utils.Constants

class LearnAlphabetsActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLearnAlphabetsBinding
    private lateinit var mAlphabetItems: ArrayList<AlphabetModel>
    private var mCurrentPostition: Int = 0
    private var mCurrentSoundFileName: String = ""

    private var restTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLearnAlphabetsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        mAlphabetItems = Constants.getAlphabetItems(this)  // Get All Items
        checkDisableButton()
        disabledOrEnableAutoPlayButton("btnPauseSound", false)
        binding.ivAlphabet.setOnClickListener(this)
        binding.btnPreviousSound.setOnClickListener(this)
        binding.btnAutoSound.setOnClickListener(this)
        binding.btnPauseSound.setOnClickListener(this)
        binding.btnNextSound.setOnClickListener(this)

    }


    override fun onResume() {
        super.onResume()
        mCurrentPostition = 0
        if (mAlphabetItems.size > 0 ) {
            getAlphabetItem()
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_previous_sound -> {
                if (mCurrentPostition > 0 ) {
                    mCurrentPostition--
                    getAlphabetItem()
                }
                checkDisableButton()
            }

            R.id.btn_auto_sound -> {
                controlPlayBackAndPlayNextButton("btnPreviousSound", false)
                controlPlayBackAndPlayNextButton("btnNextSound", false)
                getAutoPlayItem()
            }

            R.id.btn_pause_sound -> {
                Log.i("btn_pause_sound >>", "if (restTimer != null) {")
                if (restTimer != null) {
                    restTimer!!.cancel()
                    mCurrentPostition--
                    controlPlayBackAndPlayNextButton("btnPreviousSound", true)
                    controlPlayBackAndPlayNextButton("btnNextSound", true)
                    enabledAutoPlayButton()

                }
                checkDisableButton()
            } 



            R.id.btn_next_sound , R.id.iv_alphabet -> {
                if (mCurrentPostition == mAlphabetItems.size-1 ) {
                    mCurrentPostition = 0
                } else {
                    mCurrentPostition++
                }
                checkDisableButton()
                getAlphabetItem()
            }
        }
    }

    fun getCurrentSoundAndImage(){
        binding.ivAlphabet.setImageResource(mAlphabetItems[mCurrentPostition].image)
        setImageSize(binding.ivAlphabet, false)
        mCurrentSoundFileName =  mAlphabetItems[mCurrentPostition].sound
    }

    @Suppress("DEPRECATION")
    private fun getAlphabetItem(){
        getCurrentSoundAndImage()
        if (mCurrentPostition == 0) {
            Handler().postDelayed({
                playSound(mCurrentSoundFileName)
            },1000 )
        } else {
            playSound(mCurrentSoundFileName)
        }

    }

    private fun getAutoPlayItem(){

        restTimer = object : CountDownTimer((mAlphabetItems.size * 2000).toLong(),2000) {
            override fun onTick(millisUntilFinished: Long) {
                if (mCurrentPostition <= mAlphabetItems.size-1) {
                    // Disable Button
                    disabledAutoPlayButton()
                    getCurrentSoundAndImage()
                    playSound(mCurrentSoundFileName)
                    mCurrentPostition++
                    if (mCurrentPostition > mAlphabetItems.size-1) {
                        mCurrentPostition = 0

                    }
                }
            }

            override fun onFinish() {
                if (mCurrentPostition > mAlphabetItems.size-1) {

                    controlPlayBackAndPlayNextButton("btnPreviousSound", false)
                    controlPlayBackAndPlayNextButton("btnNextSound", true)
                    enabledAutoPlayButton()

                }
            }
        }.start()
    }

    private fun checkDisableButton(){
        if (mCurrentPostition == 0) {
            Log.i("checkDisableButton >>", "Case 2")
            controlPlayBackAndPlayNextButton("btnPreviousSound", false)
            controlPlayBackAndPlayNextButton("btnNextSound", true)
        } else if (mCurrentPostition > mAlphabetItems.size-1) {
            Log.i("checkDisableButton >>", "Case 1")
            controlPlayBackAndPlayNextButton("btnPreviousSound", true)
            controlPlayBackAndPlayNextButton("btnNextSound", false)
        } else if (mCurrentPostition >= 0 || mCurrentPostition < mAlphabetItems.size-1) {
            Log.i("checkDisableButton >>", "Case 2")
            controlPlayBackAndPlayNextButton("btnPreviousSound", true)
            controlPlayBackAndPlayNextButton("btnNextSound", true)

        } else {
            Log.i("checkDisableButton >>", "Case 3")
            controlPlayBackAndPlayNextButton("btnPreviousSound", true)
            controlPlayBackAndPlayNextButton("btnNextSound", true)
        }
    }


    private fun controlPlayBackAndPlayNextButton(buttonViwId: String, isEnable: Boolean){
        when(buttonViwId) {
            "btnPreviousSound" -> {
                if (isEnable) {
                    var drawable = ContextCompat.getDrawable(this@LearnAlphabetsActivity, R.drawable.tts_play_back)
                    binding.btnPreviousSound.setImageDrawable(drawable)
                } else {
                    var drawable = ContextCompat.getDrawable(this@LearnAlphabetsActivity, R.drawable.tts_play_back_disable)
                    binding.btnPreviousSound.setImageDrawable(drawable)
                }
            }
            "btnNextSound" -> {
                if (isEnable) {
                    var drawable = ContextCompat.getDrawable(this@LearnAlphabetsActivity, R.drawable.tts_play_next)
                    binding.btnNextSound.setImageDrawable(drawable)
                } else {
                    var drawable = ContextCompat.getDrawable(this@LearnAlphabetsActivity, R.drawable.tts_play_next_disable)
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
    

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarCustom)
        binding.tvTitle.text = "Learn Alphabet"

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_topbar_back_arrow)
        }
        binding.toolbarCustom.setNavigationOnClickListener {
            stopSound()
            onBackPressed()
        }
    }
}