package com.example.e_library.models

//__________________This will be in the cart and then when cleared , some details move to the BorrowedBooksTable, those already there, and the cart is cleared__________________//
data class Delivery(
    // actually most of this fields will be copied from the cart, when the delivery personnel picks up the book, the remove from cart button is deactivated for client since status changes from ordered to picked up and the client now sees delivery details button instead of the remove from cart so he or she sees the personnel details and what not, based on this model.
    //____prefilled by addListenerForSingleValueEvent____//
    var deliveryId: String = "",
    var deliveryPersonnelId: String = "",
    var deliveryPersonnelFullName: String = "",
    var deliveryPersonnelEmailAddress: String = "",
    var deliveryPersonnelPhoneNumber: String = "",
    //____prefilled by addListenerForSingleValueEvent____//
    //____prefilled from cartOrders____//
    var deliveryBookId: String = "",
    var deliveryBookTitle: String = "",
    var deliveryBookAuthor: String = "",
    var deliveryBookISBNNumber: String = "",
    var deliveryBookGenre: String = "",
    var deliveryBookPublisher: String = "",
    var deliveryBookSynopsis: String = "",
    var deliveryBookImageUrl: String = "",
    var deliveryBookQuantity: Int = 0,
    var deliveryClientId: String = "",
    var deliveryClientFullName: String = "",
    var deliveryClientEmailAddress: String = "",
    var deliveryClientPhoneNumber: String = "",
    var deliveryLocationName: String = "",  // just cartOrderLocationName
    var deliveryLocationPrice: String = "",  // just cartOrderLocationPrice
    var deliveryLocationDistanceInKm: String = "", // just cartOrderDistanceInKm
    var deliveryCartOrderDate: String = "",
    var deliveryCartOrderStatus: String = "",
    //____prefilled from cartOrders____//
    //___set by the delivery guys_____//
    var deliveryDepartureDate: String = "", // the today of the delivery guy picking it.
    var deliveryArrivalDate: String = "" // the today of the delivery guy reaching pickup point
    //___set by delivery guys____//
    // on delivery pickup, it still stays in cart but the button to remove it is deactivated which means we need a status field in the cart.
    // need for notifications.
)