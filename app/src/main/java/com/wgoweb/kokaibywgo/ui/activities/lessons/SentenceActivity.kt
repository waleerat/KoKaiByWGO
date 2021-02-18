package com.wgoweb.kokaibywgo.ui.activities.lessons


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.ActivitySentenceBinding
import com.wgoweb.kokaibywgo.firebase.SectionListener
import com.wgoweb.kokaibywgo.firebase.SentenceListener
import com.wgoweb.kokaibywgo.models.SectionModel
import com.wgoweb.kokaibywgo.models.SentenceModel
import com.wgoweb.kokaibywgo.ui.activities.BaseActivity
import com.wgoweb.kokaibywgo.utils.Constants
import com.wgoweb.kokaibywgo.utils.SharePreferenceHelper
import java.util.ArrayList

class SentenceActivity : BaseActivity() {
    private lateinit var binding: ActivitySentenceBinding

    var mSectionId: String = ""
    var mSectionName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySentenceBinding.inflate(layoutInflater)
        setContentView(binding.root)


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

        Log.i("Get Sentence from >>", "$mSectionId   $mSectionName")
    }


    // call this function from SectionListener
    fun saveSentenceToPreference(itemsList: ArrayList<SentenceModel>) {
        if (itemsList.size > 0) {
            val jsonString = Gson().toJson(itemsList)
            SharePreferenceHelper().setSharePreference(this@SentenceActivity, Constants.REF_SENTENCE_PREFERENCE,jsonString )
        }
    }

    // call this function from SentenceListener
    fun successItemsList(itemsList: ArrayList<SentenceModel>){
        // Hide the progress dialog.
        hideProgressDialog()
        if (itemsList.size > 0) {
            binding.rvViewItems.visibility = View.VISIBLE
            binding.tvNoItemsFound.visibility = View.GONE

            binding.rvViewItems.layoutManager = LinearLayoutManager(this@SentenceActivity)
            binding.rvViewItems.setHasFixedSize(true)

            val itemAdapter = SentenceActivityAdapter(this@SentenceActivity, itemsList)
            // adapter instance is set to the recyclerview to inflate the items.
            binding.rvViewItems.adapter = itemAdapter
        } else {
            binding.rvViewItems.visibility = View.GONE
            binding.tvNoItemsFound.visibility = View.VISIBLE
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarCustom)
        binding.tvTitle.text = mSectionName

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_topbar_back_arrow)
        }
        binding.toolbarCustom.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}