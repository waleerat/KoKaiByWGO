package com.wgoweb.kokaibywgo.ui.activities.alphabets

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.ActivityLearnAlphabetsBinding
import com.wgoweb.kokaibywgo.models.AlphabetModel
import com.wgoweb.kokaibywgo.ui.activities.BaseActivity
import com.wgoweb.kokaibywgo.utils.Constants

class LearnAlphabetsActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLearnAlphabetsBinding

    private lateinit var mAlphabetItems: ArrayList<AlphabetModel>
    private var mCurrentPostition: Int = 0
    private var mCurrentSoundFileNme: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLearnAlphabetsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        mAlphabetItems = Constants.getAlphabetItems(this)  // Get All Items

        binding.ivAlphabet.setOnClickListener(this)
        binding.btnPreviousAlphabet.setOnClickListener(this)
        binding.btnReplaySound.setOnClickListener(this)
        binding.btnNextAlphabet.setOnClickListener(this)

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
            R.id.btn_previous_alphabet -> {
                if (mCurrentPostition > 0 ) {
                    mCurrentPostition--
                    getAlphabetItem()
                }
            }

            R.id.btn_replay_sound -> {
                playSound( mAlphabetItems[mCurrentPostition].sound)
            }

            R.id.btn_next_alphabet , R.id.iv_alphabet -> {
                if (mCurrentPostition == mAlphabetItems.size-1 ) {
                    mCurrentPostition = 0
                } else {
                    mCurrentPostition++
                }
                getAlphabetItem()
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun getAlphabetItem(){
        binding.ivAlphabet.setImageResource(mAlphabetItems[mCurrentPostition].image)
        setImageSize(binding.ivAlphabet, false)
        mCurrentSoundFileNme =  mAlphabetItems[mCurrentPostition].sound
        if (mCurrentPostition == 0) {
            Handler().postDelayed({
                playSound(mCurrentSoundFileNme)
            },1000 )
        } else {
            playSound(mCurrentSoundFileNme)
        }

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