package com.wgoweb.kokaibywgo.models
data class LevelItems(
    val level_id: String?,
    val level_code: String,
    val level_description: String,
    val level_name: String,
    val order_id: Int,
    val setting_background_color: String,
    val setting_image: String,
    val chapter_lists: ArrayList<ChapterItems>
)

data class ChapterItems(
    val chapter_id: String?,
    val chapter_name: String,
    var sections: ArrayList<String>,
    val section_lists: ArrayList<SectionItems>
)


data class SectionItems (
    val section_id: String?,
    val section_name: String,
    val section_title: String,
    val sentence_lists: ArrayList<SentenceItems>
)

data class SentenceItems (
    var sentence_id: String?,
    var chapter_id: String,
    var section_id: String,
    var chapter_name: String,
    var section_name: String,
    var order_id : Int,
    var sentence : String,
    var words: List<String>
)

/*
data class ChapterObjectArray (

 var objectsArray: ArrayList<ChapterItems> = arrayListOf(
     ChapterItems(
         "Category #1",
         arrayListOf("SubCategory #1.1", "SubCategory #1.2"),
         arrayListOf(
             // SubCategory #1.1
             arrayListOf(
                 SectionItems("#DA70D6", "Orchid"),
                 SectionItems("#2E8B57", "Sea Green")
             ),
             // SubCategory #1.2
             arrayListOf(
                 SectionItems("#2F4F4F", "Dark Slate Gray"),
                 SectionItems("#DCDCDC", "Gainsboro")
             )
         )
     ),
     ChapterItems(
         "Category #2", arrayListOf("SubCategory #2.1"),
         arrayListOf(
             // SubCategory #2.1
             arrayListOf(
                 SectionItems("#FFE4B5", "Moccasin"),
                 SectionItems("#3CB371", "Medium Sea Green")
             )
         )
     )
 )
   */