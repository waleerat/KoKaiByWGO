package com.wgoweb.kokaibywgo.firebase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.wgoweb.kokaibywgo.models.ChapterModel
import com.wgoweb.kokaibywgo.ui.activities.lessons.ChapterActivity
import com.wgoweb.kokaibywgo.utils.Constants
import com.wgoweb.kokaibywgo.utils.SharePreferenceHelper
import kotlin.collections.ArrayList

class ChapterListener {
    private val mFireStore = FirebaseFirestore.getInstance()

    var mChapterItems = ArrayList<ChapterModel>()

    var mLevelCode: String = ""

    fun getDataListItemForChapterActivity(activity: ChapterActivity, levelId: String) {
        //For testing
        //SharePreferenceHelper.clearLevelPreference(activity, Constants.REF_CHAPTER_PREFERENCE)
        mLevelCode = levelId
        mChapterItems = SharePreferenceHelper.getChapterReference(activity)
         if (mChapterItems.size == 0) {
            getChapterList(activity, levelId)
        } else {
             passResultToActivity(activity)
             if ( mChapterItems.size == 0) {
                 // Hide the progress dialog if there is any error which getting the dashboard items list.
                 activity.hideProgressDialog()
             }
        }

    }


    private fun getChapterList(activity: ChapterActivity, levelId: String) {
        // >>> Chapter
        //Log.i("Get collection >>", Constants.TBL_CHAPTERS)
        mFireStore.collection(Constants.COLLECTION_CHAPTER)
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->
                // A for loop as per the list of documents to convert them into levels ArrayList.
                for (i in document.documents) {
                    val orderId = i.data?.get("order_id").toString().toIntOrNull()!!
                    val levelId = i.id
                    // < Save to ChapterItem List >
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

                // Save to SharePreference
                activity.saveChapterToPreference(mChapterItems)
                activity.hideProgressDialog()
                passResultToActivity(activity)
            }
        // >>> Chapter
    }


    private fun passResultToActivity(activity: ChapterActivity){
        val chapterItemsByLevelId = ArrayList<ChapterModel>()
        mChapterItems!!.filter { it.level_code.trim() == mLevelCode.trim() }.forEach { chapter ->
            chapterItemsByLevelId.add(chapter)

        }
        // Pass the success result to the base fragment.
        activity.successItemsList(chapterItemsByLevelId)
    }

}