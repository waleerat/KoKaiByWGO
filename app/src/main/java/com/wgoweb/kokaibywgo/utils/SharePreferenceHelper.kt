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
import com.wgoweb.kokaibywgo.ui.fragments.LessonsFragment
import com.wgoweb.kokaibywgo.ui.fragments.QuizFragment

class SharePreferenceHelper {

    fun setSharePreference(activity: Activity, preferenceName: String, jsonSting:String){
       Log.i("SAVE-preferenceName >>", "SAVE !!")

        if (jsonSting != "") {
            val sharedPreferences =  activity.getSharedPreferences(Constants.REF_APP_NAME_REFERENCE, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()

            editor.putString(preferenceName, jsonSting)
            editor.apply()
        }
    }
    // Just for testing
    fun clearLevelPreference(activity: Activity, preferenceName: String){
        val settings: SharedPreferences =
            activity.getSharedPreferences(Constants.REF_APP_NAME_REFERENCE, Context.MODE_PRIVATE)
        settings.edit().remove(preferenceName).commit()
    }

     fun getLevelPreference(activity: Activity) : ArrayList<LevelModel> {
        var returnItems = ArrayList<LevelModel>()

        val sharedPreferences =  activity.getSharedPreferences(Constants.REF_APP_NAME_REFERENCE, Context.MODE_PRIVATE)
        val jsonString: String = sharedPreferences.getString(Constants.REF_LEVEL_PREFERENCE, "")!!
         Log.i("getLevelPreference >>", jsonString)
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
        val sharedPreferences =  activity.getSharedPreferences(Constants.REF_APP_NAME_REFERENCE, Context.MODE_PRIVATE)
         val jsonString: String = sharedPreferences.getString(Constants.REF_CHAPTER_PREFERENCE, "")!!

         Log.i("getChapterReference >>", jsonString)
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

        val sharedPreferences =  activity.getSharedPreferences(Constants.REF_APP_NAME_REFERENCE, Context.MODE_PRIVATE)
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

        val sharedPreferences =  activity.getSharedPreferences(Constants.REF_APP_NAME_REFERENCE, Context.MODE_PRIVATE)
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