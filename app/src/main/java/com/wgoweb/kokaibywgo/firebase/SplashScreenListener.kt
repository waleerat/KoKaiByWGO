package com.wgoweb.kokaibywgo.firebase

import android.content.Context
import android.net.ConnectivityManager
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
        mFireStore.collection(Constants.COLLECTION_LEVEL)
            .orderBy("order_id")
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->
                // A for loop as per the list of documents to convert them into levels ArrayList.
                for (i in document.documents) {

                    val orderId = i.data?.get("order_id").toString().toIntOrNull()!!
                    val rowData = LevelModel(
                        i.id,
                        i.data?.get("level_code").toString(),
                        orderId,
                        i.data?.get("level_name").toString(),
                        i.data?.get("level_description").toString(),
                        i.data?.get("column_chapter_per_row").toString(),
                        i.data?.get("image").toString(),
                        i.data?.get("background").toString()
                    )
                    mLevelItems.add(rowData)
                }
                activity.saveLevelToSharePreference(mLevelItems)
            }

    }

     private fun getChapterList(activity: SplashActivity){
        // >>> Chapter
        //Log.i("Get collection >>", Constants.TBL_CHAPTERS)
         mFireStore.collection(Constants.COLLECTION_CHAPTER)
             .orderBy("order_id")
             .get() // Will get the documents snapshots.
             .addOnSuccessListener { document ->
                 // A for loop as per the list of documents to convert them into levels ArrayList.
                 for (i in document.documents) {
                     val orderId = i.data?.get("order_id").toString().toIntOrNull()!!

                     val rowData = ChapterModel(
                         i.id,
                         i.data?.get("level_code").toString(),
                         i.data?.get("chapter_code").toString(),
                         orderId,
                         i.data?.get("chapter_name").toString(),
                         i.data?.get("chapter_desctiption").toString(),
                         i.data?.get("column_chapter_per_row").toString(),
                         i.data?.get("image").toString(),
                         i.data?.get("background").toString()
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
        mFireStore.collection(Constants.COLLECTION_SECTION)
            .orderBy("order_id")
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->
                // A for loop as per the list of documents to convert them into levels ArrayList.
                for (i in document.documents) {
                    val orderId = i.data?.get("order_id").toString().toIntOrNull()!!
                    val sectionData = SectionModel(
                        i.id, //section_id
                        i.data?.get("level_code").toString(),
                        i.data?.get("chapter_code").toString(),
                        i.data?.get("section_group").toString(),
                        i.data?.get("section_name").toString(),
                        orderId,
                        i.data?.get("section_code").toString(),
                        i.data?.get("section_descritpion").toString(),
                        i.data?.get("column_sentence_per_row").toString(),
                        i.data?.get("image").toString(),
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
        mFireStore.collection(Constants.COLLECTION_SENTENCE)
            .orderBy("order_id")
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->
                // A for loop as per the list of documents to convert them into levels ArrayList.
                for (i in document.documents) {
                    val words = i.data?.get("word_list") as List<String>
                    val orderId = i.data?.get("order_id").toString().toIntOrNull()!!
                    // < Save to SentenceItem List >
                    val rowData = SentenceModel(
                        i.id, //sentence_id
                        i.data?.get("level_code").toString(),
                        i.data?.get("chapter_code").toString(),
                        i.data?.get("section_code").toString(),
                        i.data?.get("sentence_code").toString(),
                        orderId,
                        i.data?.get("sentence_text").toString(),
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