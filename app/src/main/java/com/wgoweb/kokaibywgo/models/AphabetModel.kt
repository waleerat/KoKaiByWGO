package com.wgoweb.kokaibywgo.models

data class ResponseData (
    var alphabets: List<AlphabetItem>
    )


data class AlphabetItem (
     var image: String,
     var sound: String,
     var alphabet: String,
     var vowelThai: String,
     var vowelEnglish: String,
     var meaning: String,
     var RTGSInitial: String,
     var RTGSFinal: String,
     var AlphabetClass: String
)



data class AlphabetModel (
     var image: Int,
     var sound: String,
     var alphabet: String,
     var vowelThai: String,
     var vowelEnglish: String,
     var meaning: String,
     var RTGSInitial: String,
     var RTGSFinal: String,
     var AlphabetClass: String
)
