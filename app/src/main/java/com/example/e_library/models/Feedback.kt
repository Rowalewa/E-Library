package com.example.e_library.models

class Feedback {
    var feedbackName: String = ""
    var feedbackEmailAddress: String = ""
    var feedbackText: String = ""

    constructor(
        feedBackName: String,
        feedBackEmailAddress: String,
        feedbackText: String
    ){
        this.feedbackName = feedBackName
        this.feedbackEmailAddress = feedBackEmailAddress
        this.feedbackText = feedbackText
    }

    constructor()
}