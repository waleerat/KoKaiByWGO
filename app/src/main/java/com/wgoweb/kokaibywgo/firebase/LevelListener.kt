package com.wgoweb.kokaibywgo.firebase

import android.app.Activity
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.wgoweb.kokaibywgo.models.LevelModel
import com.wgoweb.kokaibywgo.ui.fragments.LessonsFragment
import com.wgoweb.kokaibywgo.ui.fragments.QuizFragment
import com.wgoweb.kokaibywgo.utils.Constants
import com.wgoweb.kokaibywgo.utils.SharePreferenceHelper

class LevelListener {
    private val mFireStore = FirebaseFirestore.getInstance()

    var mLevelItems = ArrayList<LevelModel>()

    fun getLevelItemsListForLessonFragment(fragment: LessonsFragment, activity: Activity) {
        mLevelItems = SharePreferenceHelper.getLevelPreference(activity)
        //Log.i("mLevelItems.size >>",mLevelItems.size.toString())

        if (mLevelItems.size == 0) {
            getLevelItemsListForLessonFragment(fragment)
        } else {
            // Pass the success result to the base fragment.
            fragment.successItemsList(mLevelItems)
            if ( mLevelItems.size == 0) {
                // Hide the progress dialog if there is any error which getting the dashboard items list.
                fragment.hideProgressDialog()
            }
        }

    }


    fun getLevelItemsListForQuizFragment(fragment: QuizFragment, activity: Activity) {
        mLevelItems = SharePreferenceHelper.getLevelPreference(activity)
        if (mLevelItems.size == 0) {
            getLevelItemsListForQuizFragment(fragment)

        } else {
            // Save to SharePreference
            fragment.saveLevelToPreference(mLevelItems)
            // Pass the success result to the base fragment.
            fragment.successItemsList(mLevelItems)
        }
        if ( mLevelItems.size == 0) {
            // Hide the progress dialog if there is any error which getting the dashboard items list.
            fragment.hideProgressDialog()
        }
    }


    private fun getLevelItemsListForLessonFragment(fragment: LessonsFragment){
        //Log.i("Get collection >>", Constants.TBL_LEVELS)
        mFireStore.collection(Constants.COLLECTION_LEVEL)
            .whereEqualTo("active", true)
            .orderBy("order_id")
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->
                // A for loop as per the list of documents to convert them into levels ArrayList.
                for (i in document.documents) {
                    val levelId = i.id
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
                fragment.hideProgressDialog()
                // Save to SharePreference
                fragment.saveLevelToPreference(mLevelItems)
                // Pass the success result to the base fragment.
                fragment.successItemsList(mLevelItems)
            }.addOnFailureListener { e ->
                // Hide the progress dialog if there is any error which getting the dashboard items list.
                fragment.hideProgressDialog()
                Log.e("addOnFailureListener", "Error while getting dashboard items list.", e)
            }
    }


    private fun getLevelItemsListForQuizFragment(fragment: QuizFragment){

        mFireStore.collection(Constants.COLLECTION_LEVEL)
            .whereEqualTo("active", true)
            .orderBy("order_id")
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->
                // A for loop as per the list of documents to convert them into levels ArrayList.
                for (i in document.documents) {
                    val levelId = i.id
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
                // Save to SharePreference
                fragment.saveLevelToPreference(mLevelItems)
                // Pass the success result to the base fragment.
                fragment.successItemsList(mLevelItems)
            }.addOnFailureListener { e ->
                // Hide the progress dialog if there is any error which getting the dashboard items list.
                fragment.hideProgressDialog()
                Log.e("addOnFailureListener", "Error while getting dashboard items list.", e)
            }
    }
}