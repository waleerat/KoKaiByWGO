package com.wgoweb.kokaibywgo.firebase

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.wgoweb.kokaibywgo.models.ChapterModel
import com.wgoweb.kokaibywgo.models.SectionModel
import com.wgoweb.kokaibywgo.models.SentenceModel
import com.wgoweb.kokaibywgo.models.LevelModel
import com.wgoweb.kokaibywgo.ui.activities.SplashActivity
import com.wgoweb.kokaibywgo.utils.Constants
import com.wgoweb.kokaibywgo.utils.SharePreferenceHelper

class SplashScreenListener {
    private val mFireStore = FirebaseFirestore.getInstance()

    var mLevelItems = ArrayList<LevelModel>()
    var mChapterItems = ArrayList<ChapterModel>()
    var mSectionItems = ArrayList<SectionModel>()
    var mSentenceItems = ArrayList<SentenceModel>()

    fun getAllDataFromFirestros(activity: SplashActivity){

         checkLevelSharePreference(activity)
         checkChapterSharePreference(activity)
         checkSectionSharePreference(activity)
         checkSentenceSharePreference(activity)
    }

    fun checkLevelSharePreference(activity: SplashActivity){
        mLevelItems = SharePreferenceHelper().getLevelPreference(activity)
        if (mLevelItems.size == 0) {
            getLevelList(activity)
        }
    }

    fun checkChapterSharePreference(activity: SplashActivity){
        mChapterItems = SharePreferenceHelper().getChapterReference(activity)
        if (mChapterItems.size == 0) {
            getChapterList(activity)
        }
    }

    fun checkSectionSharePreference(activity: SplashActivity){
        mSectionItems = SharePreferenceHelper().getSectionReference(activity)
        if (mSectionItems.size == 0) {
            getSectionList(activity)
        }
    }

    fun checkSentenceSharePreference(activity: SplashActivity){
        mSentenceItems = SharePreferenceHelper().getSentenceReference(activity)
        if (mSentenceItems.size == 0) {
            getSentenceList(activity)
        }
    }


    fun getLevelList(activity: SplashActivity){
        //Log.i("Get collection >>", Constants.TBL_LEVELS)
        mFireStore.collection(Constants.TBL_LEVELS)
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->
                // A for loop as per the list of documents to convert them into levels ArrayList.
                for (i in document.documents) {
                    val levelId = i.id
                    val orderId = 1 //i.data?.get("order_id").toString().toIntOrNull()!!
                    val rowData = LevelModel(
                        levelId,
                        i.data?.get("level_code").toString(),
                        i.data?.get("level_name").toString(),
                        orderId,
                        i.data?.get("level_description").toString(),
                        i.data?.get("setting_background_color").toString(),
                        i.data?.get("setting_image").toString()
                    )
                    mLevelItems.add(rowData)
                }
                activity.saveLevelToSharePreference(mLevelItems)
            }

    }

     private fun getChapterList(activity: SplashActivity){
        // >>> Chapter
        //Log.i("Get collection >>", Constants.TBL_CHAPTERS)
         mFireStore.collection(Constants.TBL_CHAPTERS)
             .get() // Will get the documents snapshots.
             .addOnSuccessListener { document ->
                 // A for loop as per the list of documents to convert them into levels ArrayList.
                 for (i in document.documents) {
                     val chapterId = i.id
                     // < Save to ChapterItem List >
                     val rowData = ChapterModel(
                         i.id, //chapter_id
                         i.data?.get("level_id").toString(), //chapter_name
                         i.data?.get("level_code").toString(),
                         i.data?.get("chapter_name").toString(),
                     )
                     mChapterItems.add(rowData)
                 }
                 activity.saveChapterToSharePreference(mChapterItems)
             }

        // >>> Chapter
    }

    private fun getSectionList(activity: SplashActivity) {
        // >>> Section
        //Log.i("Get collection >>", Constants.TBL_SECTIONS)
        mFireStore.collection(Constants.TBL_SECTIONS)
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->
                // A for loop as per the list of documents to convert them into levels ArrayList.
                for (i in document.documents) {
                    val sectionId = i.id
                    // < Save to SectionItem List >
                    val sectionData = SectionModel(
                        sectionId, //section_id
                        i.data?.get("chapter_id").toString(),
                        i.data?.get("chapter_name").toString(),
                        i.data?.get("section_name").toString(), //section_name
                        i.data?.get("section_title").toString(), //section_title

                    )
                    mSectionItems.add(sectionData)
                }
                activity.saveSectionToSharePreference(mSectionItems)
            }
        // >>> Section
    }

    private fun getSentenceList(activity: SplashActivity) {
        // >>> Sentence
        //Log.i("Get collection >>", Constants.TBL_SENTENCES)
        val sections = ArrayList<String>()
        mFireStore.collection(Constants.TBL_SENTENCES)
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->
                // A for loop as per the list of documents to convert them into levels ArrayList.
                for (i in document.documents) {
                    val sectionId = i.id
                    val chapterId = i.data?.get("chapter_id").toString()
                    val words = i.data?.get("words") as List<String>
                    val orderId = i.data?.get("order_id").toString().toIntOrNull()!!
                    // < Save to SentenceItem List >
                    val rowData = SentenceModel(
                        i.id, //sentence_id
                        i.data?.get("chapter_id").toString(),
                        i.data?.get("section_id").toString(),
                        i.data?.get("chapter_name").toString(),
                        i.data?.get("section_name").toString(),
                        orderId,
                        i.data?.get("sentence").toString(),
                        words
                    )
                    //sections.add(i.data?.get("section_name").toString())
                    mSentenceItems.add(rowData)
                }
                activity.saveSentenceToSharePreference(mSentenceItems)
            }
        // >>> Sentence
    }

}


@Suppress("DEPRECATION")
object ConnectivityUtils {
    fun isConnected(context: Context): Boolean {
        val connectivityManager = context.applicationContext.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}