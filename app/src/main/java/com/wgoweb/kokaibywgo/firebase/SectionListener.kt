package com.wgoweb.kokaibywgo.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.wgoweb.kokaibywgo.models.SectionModel
import com.wgoweb.kokaibywgo.ui.activities.lessons.SectionActivity
import com.wgoweb.kokaibywgo.utils.Constants
import com.wgoweb.kokaibywgo.utils.SharePreferenceHelper

class SectionListener {
    private val mFireStore = FirebaseFirestore.getInstance()

    var mSectionItems = ArrayList<SectionModel>()

    var mChapterCode: String = ""

    fun getDataListItemForSectionActivity(activity: SectionActivity, chapterId: String) {
        //For testing
        //SharePreferenceHelper.clearLevelPreference(activity, Constants.REF_SECTION_PREFERENCE)
        mChapterCode = chapterId
        mSectionItems = SharePreferenceHelper.getSectionReference(activity)
        if (mSectionItems.size == 0) {
            getSectionList(activity, chapterId)
        } else {
            passResultToActivity(activity)
            if ( mSectionItems.size == 0) {
                // Hide the progress dialog if there is any error which getting the dashboard items list.
                activity.hideProgressDialog()
            }
        }

    }


    private fun getSectionList(activity: SectionActivity, chapterId: String) {
        // >>> Section
        //Log.i("Get collection >>", Constants.TBL_SECTIONS)
        mFireStore.collection(Constants.COLLECTION_SECTION)
            .orderBy("order_id")
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->
                // A for loop as per the list of documents to convert them into levels ArrayList.
                for (i in document.documents) {
                    val orderId = i.data?.get("order_id").toString().toIntOrNull()!!
                    // < Save to SectionItem List >
                    val rowData = SectionModel(
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
                    mSectionItems.add(rowData)
                }
                // Save to SharePreference
                activity.saveSectionToPreference(mSectionItems)
                activity.hideProgressDialog()
                passResultToActivity(activity)
            }
        // >>> Section
    }

    private fun passResultToActivity(activity: SectionActivity){
        val sectionItemsByChapterId = ArrayList<SectionModel>()
        mSectionItems!!.filter { it.chapter_code.trim() == mChapterCode.trim() }.forEach { section ->
            sectionItemsByChapterId.add(section)
        }

        // Pass the success result to the base fragment.
        activity.successItemsList(sectionItemsByChapterId)
    }

}