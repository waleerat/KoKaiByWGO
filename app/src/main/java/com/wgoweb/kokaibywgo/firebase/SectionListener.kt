package com.wgoweb.kokaibywgo.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.wgoweb.kokaibywgo.models.SectionModel
import com.wgoweb.kokaibywgo.ui.activities.lessons.SectionActivity
import com.wgoweb.kokaibywgo.utils.Constants
import com.wgoweb.kokaibywgo.utils.SharePreferenceHelper

class SectionListener {
    private val mFireStore = FirebaseFirestore.getInstance()

    var mSectionItems = ArrayList<SectionModel>()

    var mChapterId: String = ""

    fun getDataListItemForSectionActivity(activity: SectionActivity, chapterId: String) {
        //For testing
        //SharePreferenceHelper().clearLevelPreference(activity, Constants.REF_SECTION_PREFERENCE)
        mChapterId = chapterId
        mSectionItems = SharePreferenceHelper().getSectionReference(activity)
        if (mSectionItems.size == 0) {
            getSectionList(activity, chapterId)
        } else {
            passResultToActivity(activity)
        }
        if ( mSectionItems.size == 0) {
            // Hide the progress dialog if there is any error which getting the dashboard items list.
            activity.hideProgressDialog()
        }
    }


    private fun getSectionList(activity: SectionActivity, chapterId: String) {
        // >>> Section
        //Log.i("Get collection >>", Constants.TBL_SECTIONS)
        mFireStore.collection(Constants.TBL_SECTIONS)
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->
                // A for loop as per the list of documents to convert them into levels ArrayList.
                for (i in document.documents) {
                    val SectionId = i.id
                    // < Save to SectionItem List >
                    val rowData = SectionModel(
                        i.id, //Section_id
                        i.data?.get("chapter_id").toString(), //section_name
                        i.data?.get("chapter_name").toString(),
                        i.data?.get("section_name").toString(),
                        i.data?.get("section_title").toString()
                    )
                    mSectionItems.add(rowData)
                }
                passResultToActivity(activity)
            }
        // >>> Section
    }

    private fun passResultToActivity(activity: SectionActivity){
        val sectionItemsByChapterId = ArrayList<SectionModel>()
        mSectionItems!!.filter { it.chapter_id.trim() == mChapterId.trim() }.forEach { section ->
            sectionItemsByChapterId.add(section)
        }
        // Save to SharePreference
        activity.saveSectionToPreference(sectionItemsByChapterId)
        // Pass the success result to the base fragment.
        activity.successItemsList(sectionItemsByChapterId)
    }

}