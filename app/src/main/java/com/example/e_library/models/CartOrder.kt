package com.example.e_library.models

data class CartOrder (
    var cartOrderBookId: String = "",
    var cartOrderBookTitle: String = "",
    var cartOrderBookAuthor: String = "",
    var cartOrderBookISBNNumber: String = "",
    var cartOrderBookGenre: String = "",
    var cartOrderBookPublisher: String = "",
    var cartOrderBookSynopsis: String = "",
    var cartOrderBookImageUrl: String = "",
    var cartOrderBookQuantity: Int = 1,
    var cartOrderClientProfilePictureUrl: String = "",
    var clientId: String = "",
    var cartOrderClientFullName: String = "",
    var cartOrderClientEmailAddress: String = "",
    var cartOrderClientPhoneNumber: String = "",
    var cartOrderLocationName: String = "",
    var cartOrderLocationPrice: String = "",
    var cartOrderLocationDistanceInKm: String = "",
    var cartOrderDate: String = "",
    var cartOrderStatus: String = "",
    var cartOrderId: String = ""
)