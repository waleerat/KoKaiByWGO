package com.wgoweb.kokaibywgo.ui.activities.learn

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.ActivityAlphabetAndSoundBinding
import com.wgoweb.kokaibywgo.models.AlphabetModel
import com.wgoweb.kokaibywgo.models.VowelModel
import com.wgoweb.kokaibywgo.ui.activities.BaseActivity
import com.wgoweb.kokaibywgo.ui.activities.adapters.HorizontalAlphabetsAdapter
import com.wgoweb.kokaibywgo.ui.activities.adapters.HorizontalVowelsAdapter
import com.wgoweb.kokaibywgo.utils.Constants
import java.util.ArrayList

class AlphabetAndSoundActivity : BaseActivity(),View.OnClickListener {
    private lateinit var binding: ActivityAlphabetAndSoundBinding

    private lateinit var mAlphabetItems: ArrayList<AlphabetModel>
    private lateinit var mVowelItems: ArrayList<VowelModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAlphabetAndSoundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        getRowsData()

        binding.btnMenuAllAlphabets.setOnClickListener(this)
        binding.btnMenuAllVowels.setOnClickListener(this)
        binding.btnPlayAlphabetSound.setOnClickListener(this)
        binding.btnPlayVowelSound.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_menu_all_alphabets -> {
                val intent = Intent(this@AlphabetAndSoundActivity, AllAlphabetsActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_menu_all_vowels -> {
                val intent = Intent(this@AlphabetAndSoundActivity, AllVowelsActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_play_alphabet_sound -> {
                speakOut(Constants.LEARN_ALPHABET_TEXT)
            }

            R.id.btn_play_vowel_sound -> {
                speakOut(Constants.LEARN_VOWEL_TEXT)
            }
        }
    }

    private fun getRowsData() {
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        /**
         * ALPHABET LIST
         * */
        mAlphabetItems = Constants.getAlphabetItems(this)  // Get All Items

        if (mAlphabetItems.size > 0) {
            binding.rvAlphabetDataItems.visibility = View.VISIBLE
            binding.tvAlphabetNoItemsFound.visibility = View.GONE

            val itemAlphabetAdapter = HorizontalAlphabetsAdapter(this@AlphabetAndSoundActivity, mAlphabetItems, object:
                OnClickListener {
                override fun onAlphabetClick(selectedItem: AlphabetModel) {
                    playSound(selectedItem.sound)
                    //speakOut(selectedItem.alphabetSound)
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
        /**
         * VOWEL LIST
         * */

        mVowelItems = Constants.getVowelItems(this)  // Get All Items

        if (mVowelItems.size > 0) {
            binding.rvVowelDataItems.visibility = View.VISIBLE
            binding.tvVowelNoItemsFound.visibility = View.GONE

                val itemVowelAdapter = HorizontalVowelsAdapter(this@AlphabetAndSoundActivity, mVowelItems, object:
                    OnClickListener {
                    override fun onVowelClick(selectedItem: VowelModel) {
                        //speakOut(selectedItem.reading_sound)
                        playSound(selectedItem.sound)
                    }
                })
                // adapter instance is set to the recyclerview to inflate the items.
                binding.rvVowelDataItems.layoutManager = LinearLayoutManager(this)
                binding.rvVowelDataItems.setHasFixedSize(true)
                val mLayoutManager = LinearLayoutManager(applicationContext)
                mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

                mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                binding.rvVowelDataItems.layoutManager = mLayoutManager
                binding.rvVowelDataItems.itemAnimator = DefaultItemAnimator()
                binding.rvVowelDataItems.adapter = itemVowelAdapter
            } else {
                binding.rvVowelDataItems.visibility = View.GONE
                binding.tvVowelNoItemsFound.visibility = View.VISIBLE
            }
        // Hide Progress dialog.
        hideProgressDialog()

    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarCustom)
        binding.tvTitle.text = Constants.LEARN_ALPHABET_AND_SOUND_TEXT

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_topbar_back_arrow)
        }
        binding.toolbarCustom.setNavigationOnClickListener {
            onBackPressed()
        }
    }


    interface OnClickListener {
        fun onVowelClick(selectedItem: VowelModel) {

        }
        fun onAlphabetClick(selectedItem: AlphabetModel) {

        }

    }



}