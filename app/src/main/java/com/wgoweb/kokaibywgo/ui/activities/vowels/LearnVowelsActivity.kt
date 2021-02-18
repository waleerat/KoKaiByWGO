package com.wgoweb.kokaibywgo.ui.activities.vowels


import android.os.Bundle
import android.os.Handler
import android.view.View
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.ActivityLearnVowelsBinding
import com.wgoweb.kokaibywgo.models.VowelModel
import com.wgoweb.kokaibywgo.ui.activities.BaseActivity
import com.wgoweb.kokaibywgo.utils.Constants

class LearnVowelsActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLearnVowelsBinding

    private lateinit var mVowelItems: ArrayList<VowelModel>
    private var mCurrentPostition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLearnVowelsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        mVowelItems = Constants.getVowelItems(this)  // Get All Items
        if (mVowelItems.size > 0) {

        }

        binding.ivVowel.setOnClickListener(this)
        binding.btnPreviousVowel.setOnClickListener(this)
        binding.btnReplaySound.setOnClickListener(this)
        binding.btnNextVowel.setOnClickListener(this)
    }


    override fun onResume() {
        super.onResume()

        mCurrentPostition = 0
        if (mVowelItems.size > 0 ) {
            getVowelItem()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_previous_vowel -> {
                if (mCurrentPostition > 0 ) {
                    mCurrentPostition--
                    getVowelItem()
                }
            }

            R.id.btn_replay_sound -> {
                playSound( mVowelItems[mCurrentPostition].sound)
            }

            R.id.btn_next_vowel , R.id.iv_vowel -> {
                if (mCurrentPostition == mVowelItems.size-1 ) {
                    mCurrentPostition = 0
                } else {
                    mCurrentPostition++
                }
                getVowelItem()
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun getVowelItem(){

        binding.ivVowel.setImageResource(mVowelItems[mCurrentPostition].image)
        setImageSize(binding.ivVowel, false)

        if (mCurrentPostition == 0) {
            Handler().postDelayed({
                playSound( mVowelItems[mCurrentPostition].sound)
            },1000 )
        } else {
            playSound( mVowelItems[mCurrentPostition].sound)
        }

    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarCustom)
        binding.tvTitle.text = "Learn Vowel"

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