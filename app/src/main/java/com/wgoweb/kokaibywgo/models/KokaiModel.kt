package com.wgoweb.kokaibywgo.models

data class LevelModel (
    var level_id: String,
    var level_code : String,
    var order_id : Int,
    var level_name : String,
    var level_description : String,
    var column_chapter_per_row : String,
    var image : String,
    var background  : String

)


data class ChapterModel (
    var chapter_id: String,
    var level_code: String,
    var chapter_code: String,
    var order_id: Int,
    var chapter_name: String,
    var chapter_desctiption: String,
    var column_chapter_per_row: String,
    var image: String,
    var background: String
)

data class SectionGroupModel (
    var section_group_code: String,
    var section_name: String
)

data class SectionModel (
    var section_id: String,
    var level_code: String,
    var chapter_code: String,
    var section_group: String,
    var section_name: String,
    var order_id: Int,
    var section_code: String,
    var section_descritpion: String,
    var column_sentence_per_row: String,
    var image: String
)

data class SentenceModel (
    var sentence_id: String,
    var level_code: String,
    var chapter_code: String,
    var section_code: String,
    var sentence_code: String,
    var order_id: Int,
    var sentence_text: String,
    var word_list: List<String>
)

