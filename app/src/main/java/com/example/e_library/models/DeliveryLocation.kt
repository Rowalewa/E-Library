package com.example.e_library.models

data class DeliveryLocation(
    var deliveryLocationName: String = "",
    var deliveryLocationDistanceInKm: Double = 0.0
) {
    // here i changed the price to val and deleted the initializer "Double = 0.0"
    val deliveryLocationPrice: Double
        get() = calculateDeliveryPrice()

    private fun calculateDeliveryPrice(): Double {
        return deliveryLocationDistanceInKm * 40 // Constant figure of 40 per kilometer
    }
}
