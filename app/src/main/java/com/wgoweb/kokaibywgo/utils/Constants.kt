package com.wgoweb.kokaibywgo.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.wgoweb.kokaibywgo.models.*
import java.io.IOException

object Constants {
    // share reference
    const val REF_APP_NAME_REFERENCE: String = "com.wgoweb.kokaibywgo.reference"
    const val REF_LEVEL_PREFERENCE: String = "levelPreference"
    const val REF_CHAPTER_PREFERENCE: String = "chapterPreference"
    const val REF_SECTION_PREFERENCE: String = "sectionPreference"
    const val REF_SENTENCE_PREFERENCE: String = "sentencePreference"

    // SETTING
    const val MAX_QUIZ_FOR_ALPHABETS = 5
    const val MAX_QUIZ_FOR_VOWEL = 5
    const val SPEECH_DELAY : Float = 0.7f

    // Table
    const val COLLECTION_LEVEL = "KokaiLevel"
    const val COLLECTION_CHAPTER = "KokaiChapter"
    const val COLLECTION_SECTION_GROUP: String = "KokaiSectionGroup"
    const val COLLECTION_SECTION = "KokaiSection"
    const val COLLECTION_SENTENCE = "KokaiSentence"

    // Intent
    const val INTENT_LEVEL_ID = "intent_level_id"
    const val INTENT_LEVEL_NAME = "intent_level_name"
    const val INTENT_CHAPTER_ID = "intent_chapter_id"
    const val INTENT_CHAPTER_NAME = "intent_chapter_name"
    const val INTENT_SECTION_ID = "intent_section_id"
    const val INTENT_SECTION_NAME = "intent_section_name"
    const val INTENT_SENTENCE_ID = "intent_sentence_id"
    const val INTENT_SOUND_LEVEL = "intent_sound_level"
    const val INTENT_SOUND_LEVEL_NAME = "intent_sound_name"

    // For Intent
    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTION: String = "total_question"
    const val CORRECT_ANSWERS: String = "correct_answers"

    // For adapter
    var CURRENT_ROW_BACKGROUND = 0

    // Menu
    const val MENU_MAIN_HOME: String = "เทียบเสียง"
    const val MENU_MAIN_LESSON: String = "บทเรียน"
    const val MENU_MAIN_QUIZ: String = "แบบทดสอบ"
    const val MENU_MAIN_SETTING: String = "ช่วยเหลือ"


    lateinit var LEVEL_ID: String
    private const val LESSON_ORIGINAL_TEXT = "บทเรียน"
    const val LEARN_ALPHABET_TEXT = "ตัวอักษรไทย"
    const val LEARN_VOWEL_TEXT = "สระไทย"
    const val LEARN_ALPHABET_AND_SOUND_TEXT = "ตัวอักษรและสระไทย"
    const val QUIZ_ALPHABET_TEXT = "แบบทดสอบตัวอักษรไทย"
    const val QUIZ_VOWEL_TEXT = "แบบทดสอบสระไทย"
    const val QUIZ_TEXT = "ทดสอบ"
    const val ERROR_EMPTY_QUIZ = "ไม่มีแบบทดสอบในหัวข้อนี้"
    const val SUGGEST_TO_TYPE_SENTENCE = "พิมพ์ประโยคที่ต้องการออกเสียง"

    // Lesson
    var TEXT_TO_SPEECH: String = ""
    var LESSON_TEXT = LESSON_ORIGINAL_TEXT
    var LONGEST_SENTENCE: String = ""

    // Sound
    const val SOUND_HIGH_TEXT = "เสียงสูง"
    const val SOUND_MIDDLE_TEXT = "เสียงกลาง"
    const val SOUND_LOW_TEXT = "เสียงต่ำ"
    var SELECTED_ALPHABET = "ป"
    var SELECTED_SOUND_LEVEL = ""


    fun generateChoices(amountOfRows: Int): ArrayList<Int> {
        var numbers: ArrayList<Int> = ArrayList<Int>()
        val maxRandom = amountOfRows - 1 // 0 to n-1
        for (i in 0..3) {
            var n: Int
            do {
                n = (0..maxRandom).random()
            } while (numbers.contains(n))
            numbers.add(n)
        }
        return numbers
    }

    fun getAnswer() : Int{
        return (0..3).random()
    }
/**
 * GET ALPHABETS
 * **/


     fun getAlphabetItems(context: Context) : ArrayList<AlphabetModel>{
        var itemList = ArrayList<AlphabetModel>()
        var saveItem: AlphabetModel?

        try {
            val getStream = context.assets.open("Alphabets.json")
            val result  = getStream.bufferedReader(Charsets.UTF_8)
            var imageResource: Int
            val responseData = Gson().fromJson(result, ResponseData::class.java)

            for (rowItem in responseData.alphabets) {
                imageResource = context.resources.getIdentifier(rowItem.image, "drawable", context.packageName)

                saveItem = AlphabetModel(
                    imageResource,
                    rowItem.sound,
                    rowItem.isShowInSoundActivity,
                    rowItem.alphabet,
                    rowItem.vowelThai,
                    rowItem.vowelEnglish,
                    rowItem.meaning,
                    rowItem.RTGSInitial,
                    rowItem.RTGSFinal,
                    rowItem.AlphabetClass
                )
                itemList.add(saveItem)
            }
        } catch (e: IOException) {
            //Log.i("Error >>", ">> Error read file")
            e.printStackTrace()
        }
        return itemList
    }

    /**
     * GET VOWELS
     * **/
    fun getVowelItems(context: Context) : ArrayList<VowelModel>{
        var itemList = ArrayList<VowelModel>()
        var saveItem: VowelModel?

        try {
            val getStream = context.assets.open("Vowels.json")
            val result  = getStream.bufferedReader(Charsets.UTF_8)
            var imageResource: Int
            val responseData = Gson().fromJson(result, ResponseVowelData::class.java)

            for (rowItem in responseData.vowels) {
                imageResource = context.resources.getIdentifier(rowItem.image, "drawable", context.packageName)

                saveItem = VowelModel(
                    imageResource,
                    rowItem.sound,
                    rowItem.vowelThai,
                    rowItem.vowelEnglish,
                    rowItem.isShowInSoundActivity,
                    rowItem.soundLevelOne,
                    rowItem.soundLevelTwo,
                    rowItem.soundLevelThree,
                    rowItem.soundLevelFour,
                    rowItem.soundLevelFive,
                    rowItem.writing_pattern)
                itemList.add(saveItem)

            }
        } catch (e: IOException) {
            Log.i("Error >>", ">> Error read file")
            e.printStackTrace()
        }

        return itemList
    }

}
