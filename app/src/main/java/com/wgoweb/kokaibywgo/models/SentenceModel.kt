package com.wgoweb.kokaibywgo.models

    data class ResponseLearnToSpeechData (
        var sentences: List<SentenceItem>
    )

    data class SentenceItem (
        var thai: String,
        var english: String,
        var wordType_thai: String,
        var wordType_english: String,
        var image: String
    )

