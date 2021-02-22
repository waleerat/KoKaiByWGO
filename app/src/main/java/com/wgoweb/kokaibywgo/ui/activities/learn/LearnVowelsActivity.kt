package com.wgoweb.kokaibywgo.ui.activities.learn


import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.ActivityLearnVowelsBinding
import com.wgoweb.kokaibywgo.models.VowelModel
import com.wgoweb.kokaibywgo.ui.activities.BaseActivity
import com.wgoweb.kokaibywgo.utils.Constants

class LearnVowelsActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLearnVowelsBinding

    private lateinit var mVowelItems: ArrayList<VowelModel>
    private var mCurrentPosition: Int = 0
    private var mCurrentSoundFileName: String = ""

    private var restTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLearnVowelsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        mVowelItems = Constants.getVowelItems(this)  // Get All Items
        if (mVowelItems.size > 0) {

        }

        checkDisableButton()
        disabledOrEnableAutoPlayButton("btnPauseSound", false)
        binding.ivVowel.setOnClickListener(this)
        binding.btnPreviousSound.setOnClickListener(this)
        binding.btnAutoSound.setOnClickListener(this)
        binding.btnPauseSound.setOnClickListener(this)
        binding.btnNextSound.setOnClickListener(this)
    }

    @Suppress("DEPRECATION")
    override fun onResume() {
        super.onResume()

        mCurrentPosition = 0
        /*if (mVowelItems.size > 0 ) {
            getVowelItem()
        }

        binding.btnAutoSound.isEnabled = false
        Handler().postDelayed({
            binding.btnAutoSound.isEnabled = true
        },(2000.toLong()))*/
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_previous_sound -> {
                if (mCurrentPosition > 0 ) {
                    mCurrentPosition--
                    getVowelItem()
                }
            }

            R.id.btn_auto_sound -> {
                enablePlayBackAndPlayNextButton("btnPreviousSound", false)
                enablePlayBackAndPlayNextButton("btnNextSound", false)
                getAutoPlayItem()
            }

            R.id.btn_pause_sound -> {
                if (restTimer != null) {
                    restTimer!!.cancel()
                    mCurrentPosition--
                    enablePlayBackAndPlayNextButton("btnPreviousSound", true)
                    enablePlayBackAndPlayNextButton("btnNextSound", true)
                    enabledAutoPlayButton()
                }
                //checkDisableButton()
            }

            R.id.btn_next_sound , R.id.iv_vowel -> {
                if (mCurrentPosition == mVowelItems.size-1 ) {
                    mCurrentPosition = 0
                } else {
                    mCurrentPosition++
                }
                getVowelItem()
            }
        }
    }


    /**
     * Bottom icons Action
     * */

    fun getCurrentSoundAndImage(){
        if (mCurrentPosition >= 0  && mCurrentPosition <= mVowelItems.size-1) {
            binding.ivVowel.setImageResource(mVowelItems[mCurrentPosition].image)
            setImageSize(binding.ivVowel, false)
            mCurrentSoundFileName =  mVowelItems[mCurrentPosition].sound
        }
    }

    @Suppress("DEPRECATION")
    private fun getVowelItem(){
        setEnablePreviousAndNextSoundButton(false)
        getCurrentSoundAndImage()
        if (mCurrentPosition == 0) {
            Handler().postDelayed({
                playSound(mCurrentSoundFileName)
            },1000 )
        } else {
            playSound(mCurrentSoundFileName)
        }
        // Enable buttons after speech
        Handler().postDelayed({
            setEnablePreviousAndNextSoundButton(true)
            checkDisableButton()
        },(2000.toLong()))

    }

    private fun getAutoPlayItem(){
        setEnablePreviousAndNextSoundButton(false)
        restTimer = object : CountDownTimer((mVowelItems.size * 2000).toLong(),2000) {
            override fun onTick(millisUntilFinished: Long) {
                setEnablePreviousAndNextSoundButton(false)
                if (mCurrentPosition <= mVowelItems.size-1) {
                    // Disable Button
                    disabledAutoPlayButton()
                    getCurrentSoundAndImage()
                    playSound(mCurrentSoundFileName)
                    mCurrentPosition++
                    if (mCurrentPosition > mVowelItems.size-1) {
                        mCurrentPosition = 0
                    }
                }
            }

            override fun onFinish() {
                setEnablePreviousAndNextSoundButton(true)
                checkDisableButton()
                if (mCurrentPosition > mVowelItems.size-1) {
                    enablePlayBackAndPlayNextButton("btnPreviousSound", false)
                    enablePlayBackAndPlayNextButton("btnNextSound", true)
                    enabledAutoPlayButton()
                }
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
        }
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
                    var drawable = ContextCompat.getDrawable(this@LearnVowelsActivity, R.drawable.tts_play_back)
                    binding.btnPreviousSound.setImageDrawable(drawable)
                    binding.btnPreviousSound.isEnabled = true
                } else {
                    var drawable = ContextCompat.getDrawable(this@LearnVowelsActivity, R.drawable.tts_play_back_disable)
                    binding.btnPreviousSound.setImageDrawable(drawable)
                    binding.btnPreviousSound.isEnabled = false
                }
            }
            "btnNextSound" -> {
                if (isEnable) {
                    var drawable = ContextCompat.getDrawable(this@LearnVowelsActivity, R.drawable.tts_play_next)
                    binding.btnNextSound.setImageDrawable(drawable)
                    binding.btnNextSound.isEnabled = true
                } else {
                    var drawable = ContextCompat.getDrawable(this@LearnVowelsActivity, R.drawable.tts_play_next_disable)
                    binding.btnNextSound.setImageDrawable(drawable)
                    binding.btnNextSound.isEnabled = false
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
        binding.tvTitle.text = Constants.LEARN_VOWEL_TEXT

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_topbar_back_arrow)
        }
        binding.toolbarCustom.setNavigationOnClickListener {
            if (restTimer != null) {
                restTimer!!.cancel()
            }
            stopSound()
            onBackPressed()
        }
    }



}