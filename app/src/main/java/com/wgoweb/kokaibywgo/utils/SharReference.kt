package com.wgoweb.kokaibywgo.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log



object SharReference {
    private const val mAppNameReference = Constants.REF_APP_NAME_REFERENCE

    fun setSharePreference(activity: Activity, preferenceName: String, referenceSting:String){
        Log.i("SharReference SAVE >>", preferenceName)
        if (referenceSting != "") {
            val sharedPreferences =  activity.getSharedPreferences(mAppNameReference, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(preferenceName, referenceSting)
            editor.apply()
        }
    }

    fun getSharePreference(activity: Activity, preferenceName: String): String {
        Log.i("SharReference GET >>", preferenceName)
        val sharedPreferences = activity.getSharedPreferences(mAppNameReference, Context.MODE_PRIVATE)
        return sharedPreferences.getString(preferenceName, "EMPTY")!!  // return EMPTY IF NULL
    }

    fun clearSharePreference(activity: Activity, preferenceName: String){
        Log.i("SharReference CLEAR >>", preferenceName)
        val settings: SharedPreferences =
            activity.getSharedPreferences(mAppNameReference, Context.MODE_PRIVATE)
        settings.edit().remove(preferenceName).commit()
    }

}

/**
//Call from Activity

   SharReference.setSharePreference(this@MainActivity, "REF_CHAPTER_PREFERENCE",someString)
   mChapterItems = SharReference.getSharePreference(this@MainActivity, "REF_CHAPTER_PREFERENCE")
   SharReference.clearSharePreference(this@MainActivity, "REF_CHAPTER_PREFERENCE")

//Call from Fragment
    SharReference.setSharePreference(this.requireActivity(), "REF_CHAPTER_PREFERENCE",someString)
   mChapterItems = SharReference.getSharePreference(this.requireActivity(), "REF_CHAPTER_PREFERENCE")
   SharReference.clearSharePreference(this.requireActivity(), "REF_CHAPTER_PREFERENCE")

//Call SomeClass from activity
SomeClass().getDataListItem(this@MainActivity)

// Class Functions
Class SomeClass {
    fun getDataListItem(activity: MainActivity) {
     mChapterItems = SharReference.getSharePreference(activity,"REF_CHAPTER_PREFERENCE")
    }

    fun SetDataListItem(activity: MainActivity) {
        val someString = "ABC"
        SharReference.setSharePreference(activity,"REF_CHAPTER_PREFERENCE",someString)
    }
    fun SetDataListItem(activity: MainActivity) {
        SharReference.clearSharePreference(activity,"REF_CHAPTER_PREFERENCE")
    }

}
*/