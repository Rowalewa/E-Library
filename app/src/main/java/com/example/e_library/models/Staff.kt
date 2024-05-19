package com.example.e_library.models

class Staff {
    var fullName: String = ""
    var gender: String = ""
    var maritalStatus: String = ""
    var phoneNumber: String = ""
    var dateOfBirth: String = ""
    var email: String = ""
    var pass: String = ""
    var staffProfilePictureUrl: String = ""
    var staffStatus: String = ""
    var staffId: String = ""

    constructor(
        fullName: String,
        gender: String,
        maritalStatus: String,
        phoneNumber: String,
        dateOfBirth: String,
        email: String,
        pass: String,
        staffProfilePictureUrl: String,
        staffStatus: String,
        staffId: String
    ){
        this.fullName = fullName
        this.gender = gender
        this.maritalStatus = maritalStatus
        this.phoneNumber = phoneNumber
        this.dateOfBirth = dateOfBirth
        this.email = email
        this.pass = pass
        this.staffProfilePictureUrl = staffProfilePictureUrl
        this.staffStatus = staffStatus
        this.staffId = staffId
    }

    constructor()
}