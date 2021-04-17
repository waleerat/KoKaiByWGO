package com.wgoweb.kokaibywgo.ui.activities

import android.R.array
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.gson.Gson
import com.wgoweb.kokaibywgo.databinding.ActivitySplashBinding
import com.wgoweb.kokaibywgo.firebase.SplashScreenListener
import com.wgoweb.kokaibywgo.models.ChapterModel
import com.wgoweb.kokaibywgo.models.LevelModel
import com.wgoweb.kokaibywgo.models.SectionModel
import com.wgoweb.kokaibywgo.models.SentenceModel
import com.wgoweb.kokaibywgo.utils.Constants
import com.wgoweb.kokaibywgo.utils.SharePreferenceHelper
import java.util.*


@Suppress("DEPRECATION")
class SplashActivity : BaseActivity() {
    private  lateinit var binding: ActivitySplashBinding

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setFullScreen()
        SplashScreenListener().getUpdateDataVersionFromFireStore(this)

        Handler().postDelayed(
            {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            },
            2500
        )
    }

    fun checkLastUpdatedDataVersion(FireStoreLastUpdatedDataVersion: String){
        val preferenceLastUpdatedDataVersion = SharePreferenceHelper.getUpdateDataVersion(this)
         Log.i("Lasted update >>", "$preferenceLastUpdatedDataVersion == $FireStoreLastUpdatedDataVersion")

        if (preferenceLastUpdatedDataVersion != FireStoreLastUpdatedDataVersion || preferenceLastUpdatedDataVersion.isEmpty() ) {
            // clear all reference
            SharePreferenceHelper.clearLevelPreference(this, Constants.REF_LEVEL_PREFERENCE)
            SharePreferenceHelper.clearLevelPreference(this, Constants.REF_CHAPTER_PREFERENCE)
            SharePreferenceHelper.clearLevelPreference(this, Constants.REF_SECTION_PREFERENCE)
            SharePreferenceHelper.clearLevelPreference(this, Constants.REF_SENTENCE_PREFERENCE)
            // set new version
            SharePreferenceHelper.setSharePreference(this,Constants.REF_UPDATED_DATA_VERSION, FireStoreLastUpdatedDataVersion)
            //Log.i("setUpdateDataVersion >>", "$FireStoreLastUpdatedDataVersion")
        }
    }

    /**
     * call this functions from SplashScreenListener
     * */
    fun saveLevelToSharePreference(itemsList: ArrayList<LevelModel>) {
        if (itemsList.size > 0) {
            val jsonString = Gson().toJson(itemsList)
            SharePreferenceHelper.setSharePreference(this, Constants.REF_LEVEL_PREFERENCE, jsonString )
        }
    }

    fun saveChapterToSharePreference(itemsList: ArrayList<ChapterModel>) {
        if (itemsList.size > 0) {
            val jsonString = Gson().toJson(itemsList)
           SharePreferenceHelper.setSharePreference(this, Constants.REF_CHAPTER_PREFERENCE,jsonString )
        }
    }

    fun saveSectionToSharePreference(itemsList: ArrayList<SectionModel>) {
        if (itemsList.size > 0) {
            val jsonString = Gson().toJson(itemsList)
           SharePreferenceHelper.setSharePreference(this, Constants.REF_SECTION_PREFERENCE,jsonString )
        }
    }

    fun saveSentenceToSharePreference(itemsList: ArrayList<SentenceModel>) {
        if (itemsList.size > 0) {
            val jsonString = Gson().toJson(itemsList)
           SharePreferenceHelper.setSharePreference(this, Constants.REF_SENTENCE_PREFERENCE,jsonString )
        }
    }
}