package com.wgoweb.kokaibywgo.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.GsonBuilder
import com.wgoweb.kokaibywgo.models.ChapterModel
import com.wgoweb.kokaibywgo.models.LevelModel
import com.wgoweb.kokaibywgo.models.SectionModel
import com.wgoweb.kokaibywgo.models.SentenceModel

object SharePreferenceHelper {

    private const val mAppNameReference = Constants.REF_APP_NAME_REFERENCE

    fun setSharePreference(activity: Activity, preferenceName: String, referenceSting:String){
        Log.i("SAVE-preferenceName >>", preferenceName)
        if (referenceSting != "") {
            val sharedPreferences =  activity.getSharedPreferences(Constants.REF_APP_NAME_REFERENCE, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(preferenceName, referenceSting)
            editor.apply()

        }
    }
    // Just for testing
    fun clearLevelPreference(activity: Activity, preferenceName: String){
        Log.i("Clear-preferenceName >>", preferenceName)
        val settings: SharedPreferences =
            activity.getSharedPreferences(Constants.REF_APP_NAME_REFERENCE, Context.MODE_PRIVATE)
        settings.edit().remove(preferenceName).commit()
    }


    fun getUpdateDataVersion(activity: Activity): String {
        val sharedPreferences =  activity.getSharedPreferences(mAppNameReference, Context.MODE_PRIVATE)
        val updateVersion = sharedPreferences.getString(Constants.REF_UPDATED_DATA_VERSION, "Nan")!!

        Log.i("getUpdateDataVersion >>", updateVersion)
        return updateVersion
    }


     fun getLevelPreference(activity: Activity) : ArrayList<LevelModel> {
        var returnItems = ArrayList<LevelModel>()

        val sharedPreferences =  activity.getSharedPreferences(mAppNameReference, Context.MODE_PRIVATE)
        val jsonString: String = sharedPreferences.getString(Constants.REF_LEVEL_PREFERENCE, "")!!
        // Log.i("getLevelPreference >>", jsonString)
        if (jsonString != "") {
            val gsonBuilder = GsonBuilder().serializeNulls()
            val gson = gsonBuilder.create()
            val dataListItem: List<LevelModel> = gson.fromJson(jsonString , Array<LevelModel>::class.java).toList()
            // convert list to array list
            returnItems = ArrayList(dataListItem)
        }
        return  returnItems
    }

     fun getChapterReference(activity: Activity) : ArrayList<ChapterModel> {
        var returnItems = ArrayList<ChapterModel>()
        val sharedPreferences =  activity.getSharedPreferences(mAppNameReference, Context.MODE_PRIVATE)
         val jsonString: String = sharedPreferences.getString(Constants.REF_CHAPTER_PREFERENCE, "")!!

        // Log.i("getChapterReference >>", jsonString)
        if (jsonString != "") {
            val gsonBuilder = GsonBuilder().serializeNulls()
            val gson = gsonBuilder.create()
            val dataListItem: List<ChapterModel> = gson.fromJson(jsonString , Array<ChapterModel>::class.java).toList()
            // convert list to array list
            returnItems = ArrayList(dataListItem)
        }
        return  returnItems
    }

     fun getSectionReference(activity: Activity) : ArrayList<SectionModel> {
        var returnItems = ArrayList<SectionModel>()

        val sharedPreferences =  activity.getSharedPreferences(mAppNameReference, Context.MODE_PRIVATE)
        val jsonString: String = sharedPreferences.getString(Constants.REF_SECTION_PREFERENCE, "")!!
         //Log.i("getSectionReference >>", jsonString)
        if (jsonString != "") {
            val gsonBuilder = GsonBuilder().serializeNulls()
            val gson = gsonBuilder.create()
            val dataListItem: List<SectionModel> = gson.fromJson(jsonString , Array<SectionModel>::class.java).toList()
            // convert list to array list
            returnItems = ArrayList(dataListItem)
        }
        return  returnItems
    }

     fun getSentenceReference(activity: Activity) : ArrayList<SentenceModel> {
        var returnItems = ArrayList<SentenceModel>()

        val sharedPreferences =  activity.getSharedPreferences(mAppNameReference, Context.MODE_PRIVATE)
        val jsonString: String = sharedPreferences.getString(Constants.REF_SENTENCE_PREFERENCE, "")!!
         //Log.i("getSentenceReference >>", jsonString)
        if (jsonString != "") {
            val gsonBuilder = GsonBuilder().serializeNulls()
            val gson = gsonBuilder.create()
            val dataListItem: List<SentenceModel> = gson.fromJson(jsonString , Array<SentenceModel>::class.java).toList()
            // convert list to array list
            returnItems = ArrayList(dataListItem)
        }
        return  returnItems
    }
}