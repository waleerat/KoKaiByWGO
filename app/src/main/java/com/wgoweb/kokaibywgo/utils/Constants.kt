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

    // Quiz
    const val MAX_QUIZ_FOR_ALPHABETS = 5
    const val MAX_QUIZ_FOR_VOWEL = 5

    // For Intent
    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTION: String = "total_question"
    const val CORRECT_ANSWERS: String = "correct_answers"

    var TEXT_TO_SPEECH: String = ""
    const val LEARN_ALPHABET_TEXT = "ตัวอักษรไทย"
    const val LEARN_VOWEL_TEXT = "สระไทย"
    const val LEARN_ALPHABET_AND_SOUND_TEXT = "ตัวอักษรและสระไทย"
    const val QUIZ_ALPHABET_TEXT = "แบบทดสอบตัวอักษรไทย"
    const val QUIZ_VOWEL_TEXT = "ทดสอบสระไทย"
    const val QUIZ_TEXT = "ทดสอบ"


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
                    rowItem.alphabetSound,
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
                    rowItem.reading_sound,
                    rowItem.writing_pattern)
                itemList.add(saveItem)
                /*
                saveItem = VowelModel(
                    imageResource,
                    rowItem.sound,
                    rowItem.vowelThai,
                    rowItem.vowelEnglish,
                    rowItem.reading_sound,
                    rowItem.writing_pattern
                )
                itemList.add(saveItem)*/
            }
        } catch (e: IOException) {
            Log.i("Error >>", ">> Error read file")
            e.printStackTrace()
        }

        return itemList
    }


    /**
     * GET SENTENCES
     * **/
    fun getSentenceItems(context: Context) : ArrayList<SentenceItem> {
        var itemList = ArrayList<SentenceItem>()
        var saveItem: SentenceItem?

        try {
            val getStream = context.assets.open("LearnToSpeech.json")
            val result  = getStream.bufferedReader(Charsets.UTF_8)
            val responseData = Gson().fromJson(result, ResponseLearnToSpeechData::class.java)

            for (rowItem in responseData.sentences) {
                saveItem = SentenceItem(
                    rowItem.thai,
                    rowItem.english,
                    rowItem.wordType_thai,
                    rowItem.wordType_english,
                    rowItem.image
                )
                itemList.add(saveItem)
            }
        } catch (e: IOException) {
            Log.i("Error >>", ">> Error read file")
            e.printStackTrace()
        }
        return   itemList
    }
/*
    fun getChapterItems(context: Context) : ArrayList<SentenceItem> {
        var itemList = ArrayList<SentenceItem>()
        //var saveItem: ChapterItem?
        try {
            val getStream = context.assets.open("SawasdeeBook.json")
            val result  = getStream.bufferedReader(Charsets.UTF_8)
            val responseData = Gson().fromJson(result, ResponseChapterData::class.java)

            for (chapterItem in responseData.chapters) {
                Log.i("chapterItem >>", chapterItem.chapter_name)
                for (sectionItem in chapterItem.sections) {
                    Log.i("section_name  >>", sectionItem.section_name)
                    Log.i("section_title >>", sectionItem.section_title)
                    for (sentenceItem in sectionItem.sentences) {
                        Log.i("sentenceItem >>", sentenceItem.sentence)
                        for (wordItem in sentenceItem.words) {
                            Log.i("word >>", wordItem.word)
                        }
                    }
                }
            }
        } catch (e: IOException) {
            Log.i("Error >>", ">> Error read file")
            e.printStackTrace()
        }
        return   itemList
    }
*/
}
