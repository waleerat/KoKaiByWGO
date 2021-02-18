package com.wgoweb.kokaibywgo.models


data class ResponseVowelData (
    var vowels: List<VowelItem>
)


data class VowelItem (
    var image: String,
    var sound: String,
    var vowelThai: String,
    var vowelEnglish: String,
    var reading_sound: String,
    var writing_pattern: String
)



data class VowelModel (
    var image: Int,
    var sound: String,
    var vowelThai: String,
    var vowelEnglish: String,
    var reading_sound: String,
    var writing_pattern: String?
)
