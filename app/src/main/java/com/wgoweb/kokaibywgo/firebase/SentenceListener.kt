package com.wgoweb.kokaibywgo.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.wgoweb.kokaibywgo.models.*
import com.wgoweb.kokaibywgo.ui.activities.lessons.SentenceActivity
import com.wgoweb.kokaibywgo.utils.Constants
import com.wgoweb.kokaibywgo.utils.SharePreferenceHelper


class SentenceListener {
    private val mFireStore = FirebaseFirestore.getInstance()
 
    var mSentenceItems = ArrayList<SentenceModel>()
    var mSectionId: String = ""

    fun getDataListItemForSentenceActivity(activity: SentenceActivity, sectionId: String) {
        //For testing
        //SharePreferenceHelper().clearLevelPreference(activity, Constants.REF_SENTENCE_PREFERENCE)
        mSectionId = sectionId
        mSentenceItems = SharePreferenceHelper().getSentenceReference(activity)
        if (mSentenceItems.size == 0) {
            getSentenceList(activity, mSectionId)
        } else {
            passResultToActivity(activity)
        }
        if ( mSentenceItems.size == 0) {
            // Hide the progress dialog if there is any error which getting the dashboard items list.
            activity.hideProgressDialog()
        }
    }


    private fun getSentenceList(activity: SentenceActivity, sectionId: String) {
        // >>> Sentence
        //Log.i("Get collection >>", Constants.TBL_SENTENCES)
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
                    mSentenceItems.add(rowData)
                }
                passResultToActivity(activity)
            }
        // >>> Sentence
    }

    private fun passResultToActivity(activity: SentenceActivity){
        val SentenceItemsBySectionId = ArrayList<SentenceModel>()
        mSentenceItems!!.filter { it.section_id.trim() == mSectionId.trim() }.forEach { sentence ->
            SentenceItemsBySectionId.add(sentence)
        }
        // Save to SharePreference
        activity.saveSentenceToPreference(SentenceItemsBySectionId)
        // Pass the success result to the base fragment.
        activity.successItemsList(SentenceItemsBySectionId)
    }

}