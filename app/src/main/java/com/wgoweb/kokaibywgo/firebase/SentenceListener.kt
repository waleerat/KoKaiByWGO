package com.wgoweb.kokaibywgo.firebase

import android.R
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FirebaseFirestore
import com.wgoweb.kokaibywgo.models.*
import com.wgoweb.kokaibywgo.ui.activities.lessons.ChapterActivity
import com.wgoweb.kokaibywgo.ui.activities.lessons.SentenceActivity
import com.wgoweb.kokaibywgo.ui.activities.quiz.QuizLessonActivity
import com.wgoweb.kokaibywgo.utils.Constants
import com.wgoweb.kokaibywgo.utils.SharePreferenceHelper


class SentenceListener {
    private val mFireStore = FirebaseFirestore.getInstance()
 
    var mSentenceItems = ArrayList<SentenceModel>()
    var mSectionCode: String = ""

    fun getDataListItemForSentenceActivity(activity: SentenceActivity, sectionId: String) {
        //For testing
        //SharePreferenceHelper().clearLevelPreference(activity, Constants.REF_SENTENCE_PREFERENCE)
        mSectionCode = sectionId
        mSentenceItems = SharePreferenceHelper().getSentenceReference(activity)
        if (mSentenceItems.size == 0) {
            getSentenceList(activity, mSectionCode)
        } else {
            passResultToActivity(activity)
            if ( mSentenceItems.size == 0) {
                // Hide the progress dialog if there is any error which getting the dashboard items list.
                activity.hideProgressDialog()
            }
        }
        //Log.i("mSentenceItems Size >>",mSentenceItems.size.toString())
    }


    private fun getSentenceList(activity: SentenceActivity, sectionId: String) {
        // >>> Sentence
        //Log.i("Get collection >>", Constants.TBL_SENTENCES)
        mFireStore.collection(Constants.COLLECTION_SENTENCE)
            .orderBy("order_id")
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->
                // A for loop as per the list of documents to convert them into levels ArrayList.
                for (i in document.documents) {
                    val wordList = i.data?.get("word_list") as List<String>
                    val orderId = i.data?.get("order_id").toString().toIntOrNull()!!
                    // < Save to SentenceItem List >
                    val rowData = SentenceModel(
                        i.id, //sentence_id
                        i.data?.get("level_code").toString(),
                        i.data?.get("chapter_code").toString(),
                        i.data?.get("section_code").toString(),
                        i.data?.get("sentence_code").toString(),
                        orderId,
                        i.data?.get("sentence_name").toString(),
                        wordList
                    )
                    mSentenceItems.add(rowData)
                }
                Log.i("ToPreference Size >>",mSentenceItems.size.toString())
                // Save to SharePreference
                activity.saveSentenceToPreference(mSentenceItems)
                activity.hideProgressDialog()
                passResultToActivity(activity)
            }
        // >>> Sentence
    }

    private fun passResultToActivity(activity: SentenceActivity){
        val SentenceItemsBySectionId = ArrayList<SentenceModel>()
        mSentenceItems!!.filter { it.section_code.trim() == mSectionCode.trim() }.forEach { sentence ->
            // get longest string
            SentenceItemsBySectionId.add(sentence)
        }
        // Pass the success result to the base fragment.
        activity.successItemsList(SentenceItemsBySectionId)
    }

    /**
     *
     * QUIZ
     *
     * */

    @RequiresApi(Build.VERSION_CODES.M)
    fun getDataListItemForQuizActivity(activity: QuizLessonActivity) {
        //For testing
        //SharePreferenceHelper().clearLevelPreference(activity, Constants.REF_SENTENCE_PREFERENCE)

        mSentenceItems = SharePreferenceHelper().getSentenceReference(activity)
        if (mSentenceItems.size == 0) {
            getSentenceListForQuiz(activity)
        } else {
            activity.successItemsList(mSentenceItems)
            if ( mSentenceItems.size == 0) {
                // Hide the progress dialog if there is any error which getting the dashboard items list.
                activity.hideProgressDialog()
            }
        }


        mSentenceItems = SharePreferenceHelper().getSentenceReference(activity)
        if (mSentenceItems.size == 0) {
            getSentenceListForQuiz(activity)
        } else {
            passResultToActivity(activity)
            if ( mSentenceItems.size == 0) {
                // Hide the progress dialog if there is any error which getting the dashboard items list.
                activity.hideProgressDialog()
            }
        }


    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun getSentenceListForQuiz(activity: QuizLessonActivity) {
        // >>> Sentence
        Log.i("Get LEVEL_ID >>", Constants.LEVEL_ID.trim())
        mFireStore.collection(Constants.COLLECTION_SENTENCE)
           // .whereEqualTo("level_code", "L001")
            .orderBy("order_id")
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->
                // A for loop as per the list of documents to convert them into levels ArrayList.
                for (i in document.documents) {
                    val wordList = i.data?.get("word_list") as List<String>
                    val orderId = i.data?.get("order_id").toString().toIntOrNull()!!
                    // < Save to SentenceItem List >
                    val rowData = SentenceModel(
                        i.id, //sentence_id
                        i.data?.get("level_code").toString(),
                        i.data?.get("chapter_code").toString(),
                        i.data?.get("section_code").toString(),
                        i.data?.get("sentence_code").toString(),
                        orderId,
                        i.data?.get("sentence_name").toString(),
                        wordList
                    )
                    mSentenceItems.add(rowData)
                }
                Log.i("Get mSentenceItems >>", mSentenceItems.toString())
                // Save to SharePreference
                activity.saveSentenceToPreference(mSentenceItems)
                activity.hideProgressDialog()
                passResultToActivity(activity)
            }
        // >>> Sentence
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun passResultToActivity(activity: QuizLessonActivity){
        val sentenceItemsByLevelId = ArrayList<SentenceModel>()
        Constants.LONGEST_SENTENCE = ""
        mSentenceItems!!.filter { it.level_code.trim() ==Constants.LEVEL_ID.trim() }.forEach { sentence ->
            sentenceItemsByLevelId.add(sentence)
        }
        // Pass the success result to the base fragment.
        activity.successItemsList(sentenceItemsByLevelId)
    }

}