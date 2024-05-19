package com.example.e_library.models

class Contact {
    var guestName: String = ""
    var guestEmail: String = ""
    var guestPhoneNumber: String = ""
    var guestComment: String = ""
    var guestCallback: String = ""
    var guestId: String = ""

    constructor(
        guestName: String,
        guestEmail: String,
        guestPhoneNumber: String,
        guestComment: String,
        guestCallback: String,
        guestId: String
    ){
        this.guestName = guestName
        this.guestEmail = guestEmail
        this.guestPhoneNumber = guestPhoneNumber
        this.guestComment = guestComment
        this.guestCallback = guestCallback
        this.guestId = guestId
    }

    constructor()
}