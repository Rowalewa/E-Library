package com.example.e_library.models

data class DeliveryPersonnel(
    var fullName: String = "",
    var gender: String = "",
    var maritalStatus: String = "",
    var phoneNumber: String = "",
    var dateOfBirth: String = "",
    var accountStatus: String = "",
    var deliveryStatus: String = "",
    var vehicle: String = "",
    var deliveryPersonnelProfilePictureUrl: String = "",
    var email: String = "",
    var pass: String = "",
    var deliveryPersonnelId: String = ""
    //var deliveryPersonnelPickupPoints: List<DeliveryLocation>()
)