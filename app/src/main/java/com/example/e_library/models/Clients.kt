package com.example.e_library.models

class Clients {
    var fullName: String = ""
    var gender: String = ""
    var maritalStatus: String = ""
    var phoneNumber: String = ""
    var dateOfBirth: String = ""
    var email: String = ""
    var pass: String = ""
    var fine: Double = 0.0
    var clientProfilePictureUrl: String = ""
    var clientStatus: String = ""
    var clientId: String = ""


    constructor(
        fullName: String,
        gender: String,
        maritalStatus: String,
        phoneNumber: String,
        dateOfBirth: String,
        email: String,
        pass: String,
        clientProfilePictureUrl: String,
        clientStatus: String,
        clientId: String,
        fine: Double

    ){
        this.fullName = fullName
        this.gender = gender
        this.maritalStatus = maritalStatus
        this.phoneNumber = phoneNumber
        this.dateOfBirth = dateOfBirth
        this.email = email
        this.pass = pass
        this.clientProfilePictureUrl = clientProfilePictureUrl
        this.clientStatus = clientStatus
        this.clientId = clientId
        this.fine = fine

    }

    constructor()
}