package com.wgoweb.kokaibywgo.models

data class Question (
    val id: Int,
    val question: String,
    val drawable: String,
    val optionOne: String,
    val optionTwo: String,
    val optionThree: String,
    val optionFour: String,
    val correctAnswer: Int
)