package com.wgoweb.kokaibywgo.firebase

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.*
import com.wgoweb.kokaibywgo.models.ChapterModel
import com.wgoweb.kokaibywgo.models.LevelModel
import com.wgoweb.kokaibywgo.ui.activities.SplashActivity
import com.wgoweb.kokaibywgo.ui.fragments.LessonsFragment
import com.wgoweb.kokaibywgo.ui.fragments.QuizFragment
import com.wgoweb.kokaibywgo.utils.Constants
import com.wgoweb.kokaibywgo.utils.SharePreferenceHelper
import kotlinx.coroutines.*

class LevelListener {
    private val mFireStore = FirebaseFirestore.getInstance()

    var mLevelItems = ArrayList<LevelModel>()

    fun getLevelItemsListForLessonFragment(fragment: LessonsFragment, activity: Activity) {
        mLevelItems = SharePreferenceHelper().getLevelPreference(activity)
        if (mLevelItems.size == 0) {
            getLevelItemsListForLessonFragment(fragment)
            Log.i("GET getChapterList >>", mLevelItems.size.toString())
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


    fun getLevelItemsListForQuizFragment(fragment: QuizFragment, activity: Activity) {
        mLevelItems = SharePreferenceHelper().getLevelPreference(activity)
        if (mLevelItems.size == 0) {
            getLevelItemsListForQuizFragment(fragment)
            Log.i("GET getChapterList >>", mLevelItems.size.toString())
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
        Log.i("Get collection >>", Constants.TBL_LEVELS)
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