package com.wgoweb.kokaibywgo.ui.activities.lessons

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.ActivityChapterBinding
import com.wgoweb.kokaibywgo.firebase.ChapterListener
import com.wgoweb.kokaibywgo.models.ChapterModel
import com.wgoweb.kokaibywgo.ui.activities.BaseActivity
import com.wgoweb.kokaibywgo.ui.activities.adapters.ChapterActivityAdapter
import com.wgoweb.kokaibywgo.utils.Constants
import com.wgoweb.kokaibywgo.utils.SharePreferenceHelper
import java.util.ArrayList

class ChapterActivity : BaseActivity() {

    private lateinit var binding: ActivityChapterBinding
    var mLevelId: String = ""
    var mLevelName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChapterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(Constants.INTENT_LEVEL_ID)) {
            mLevelId = intent.getStringExtra(Constants.INTENT_LEVEL_ID)!!
        }
        if (intent.hasExtra(Constants.INTENT_LEVEL_NAME)) {
            mLevelName = intent.getStringExtra(Constants.INTENT_LEVEL_NAME)!!
            //binding.activityTitle.text = mLevelName
        }
        //Log.i("Get Chapter from >>", "$mLevelId   $mLevelName")
        setupActionBar()
        binding.tvNoItemsFound.visibility = View.GONE
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        ChapterListener().getDataListItemForChapterActivity(this@ChapterActivity, mLevelId)
    }


    // call this function from ChapterListener
    fun saveChapterToPreference(itemsList: ArrayList<ChapterModel>) {

        if (itemsList.size > 0) {
            val jsonString = Gson().toJson(itemsList)
                SharePreferenceHelper.setSharePreference(this@ChapterActivity, Constants.REF_CHAPTER_PREFERENCE,jsonString )
        }
    }

    // call this function from ChapterListener
    fun successItemsList(itemsList: ArrayList<ChapterModel>){

        // Hide the progress dialog.
        hideProgressDialog()
        if (itemsList.size > 0) {
            binding.rvViewItems.visibility = View.VISIBLE
            binding.tvNoItemsFound.visibility = View.GONE

            binding.rvViewItems.layoutManager = LinearLayoutManager(this@ChapterActivity)
            binding.rvViewItems.setHasFixedSize(true)

            val itemAdapter = ChapterActivityAdapter(this@ChapterActivity, itemsList, object: OnClickListener {
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

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarCustom)
        binding.tvTitle.text = Constants.LESSON_TEXT

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
        fun onClick(currentText: String) {
        }
    }

    }

