package com.wgoweb.kokaibywgo.firebase


import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.wgoweb.kokaibywgo.models.ChapterModel
import com.wgoweb.kokaibywgo.ui.activities.lessons.ChapterActivity
import com.wgoweb.kokaibywgo.utils.Constants
import com.wgoweb.kokaibywgo.utils.SharePreferenceHelper
import kotlin.collections.ArrayList
import kotlin.reflect.typeOf

class ChapterListener {
    private val mFireStore = FirebaseFirestore.getInstance()

    var mChapterItems = ArrayList<ChapterModel>()

    var mLevelId: String = ""

    fun getDataListItemForChapterActivity(activity: ChapterActivity, levelId: String) {
        //For testing
        //SharePreferenceHelper().clearLevelPreference(activity, Constants.REF_CHAPTER_PREFERENCE)
        mLevelId = levelId
        mChapterItems = SharePreferenceHelper().getChapterReference(activity)
         if (mChapterItems.size == 0) {
            getChapterList(activity, levelId)
        } else {
             passResultToActivity(activity)
        }
        if ( mChapterItems.size == 0) {
            // Hide the progress dialog if there is any error which getting the dashboard items list.
            activity.hideProgressDialog()
        }
    }


    private fun getChapterList(activity: ChapterActivity, levelId: String) {
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
                passResultToActivity(activity)
            }
        // >>> Chapter
    }


    private fun passResultToActivity(activity: ChapterActivity){
        val chapterItemsByLevelId = ArrayList<ChapterModel>()
        mChapterItems!!.filter { it.level_id.trim() == mLevelId.trim() }.forEach { chapter ->
            chapterItemsByLevelId.add(chapter)
        }
        // Save to SharePreference
        activity.saveChapterToPreference(chapterItemsByLevelId)
        // Pass the success result to the base fragment.
        activity.successItemsList(chapterItemsByLevelId)
    }

}