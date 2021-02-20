package com.wgoweb.kokaibywgo.ui.activities.lessons

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.ActivitySectionBinding
import com.wgoweb.kokaibywgo.firebase.SectionListener
import com.wgoweb.kokaibywgo.models.SectionModel
import com.wgoweb.kokaibywgo.ui.activities.BaseActivity
import com.wgoweb.kokaibywgo.ui.activities.adapters.SectionActivityAdapter
import com.wgoweb.kokaibywgo.utils.Constants
import com.wgoweb.kokaibywgo.utils.SharePreferenceHelper
import java.util.ArrayList

class SectionActivity : BaseActivity() {
    private lateinit var binding: ActivitySectionBinding

    var mChapterId: String = ""
    var mChapterName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySectionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (intent.hasExtra(Constants.INTENT_CHAPTER_ID)) {
            mChapterId = intent.getStringExtra(Constants.INTENT_CHAPTER_ID)!!
        }
        if (intent.hasExtra(Constants.INTENT_CHAPTER_NAME)) {
            mChapterName = intent.getStringExtra(Constants.INTENT_CHAPTER_NAME)!!
            binding.activityTitle.text = mChapterName
        }

        setupActionBar()
        binding.tvNoItemsFound.visibility = View.GONE
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))


        SectionListener().getDataListItemForSectionActivity(this@SectionActivity, mChapterId)
        
    }

    // call this function from SectionListener
    fun saveSectionToPreference(itemsList: ArrayList<SectionModel>) {
        if (itemsList.size > 0) {
            val jsonString = Gson().toJson(itemsList)
            SharePreferenceHelper().setSharePreference(this@SectionActivity, Constants.REF_SECTION_PREFERENCE,jsonString )
        }
    }


    // call this function from SectionListener
    fun successItemsList(itemsList: ArrayList<SectionModel>){
        // Hide the progress dialog.
        hideProgressDialog()
        if (itemsList.size > 0) {
            binding.rvViewItems.visibility = View.VISIBLE
            binding.tvNoItemsFound.visibility = View.GONE

            binding.rvViewItems.layoutManager = LinearLayoutManager(this@SectionActivity)
            binding.rvViewItems.setHasFixedSize(true)

            val itemAdapter = SectionActivityAdapter(this@SectionActivity, itemsList, object:
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

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarCustom)
        binding.tvTitle.text = mChapterName

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