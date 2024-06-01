package com.example.e_library.models

import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.model.DirectionsResult
import com.google.maps.model.TravelMode

data class DeliveryLocation(
    var deliveryLocationName: String = "",
    var deliveryLocationDistanceInKm: Double = 0.0,
    val deliveryLocationLatitude: Double = 0.0,
    val deliveryLocationLongitude: Double = 0.0
) {
    // here i changed the price to val and deleted the initializer "Double = 0.0"
    val deliveryLocationPrice: Double
        get() = calculateDeliveryPrice()

    private fun calculateDeliveryPrice(): Double {
        return deliveryLocationDistanceInKm * 40 // Constant figure of 40 per kilometer
    }
//    fun calculateRoadDistanceAndPriceFromCbd(): Pair<Double, Double> {
//        val cbdLatitude = -1.286389
//        val cbdLongitude = 36.817223
//        val apiKey = "YOUR_GOOGLE_MAPS_API_KEY"
//        val geoApiContext = GeoApiContext.Builder().apiKey(apiKey).build()
//        val directionsApiRequest = DirectionsApi.newRequest(geoApiContext)
//            .mode(TravelMode.DRIVING)
//            .origin("$cbdLatitude, $cbdLongitude")
//            .destination("$deliveryLocationLatitude, $deliveryLocationLongitude")
//        val directionsResult: DirectionsResult = directionsApiRequest.await()
//
//        // Check if the directions request was successful
//        if (directionsResult.routes.isNotEmpty()) {
//            val route = directionsResult.routes[0]
//            val leg = route.legs[0]
//            val distanceInKm = leg.distance.inMeters / 1000.0 // Convert meters to kilometers
//            val price = distanceInKm * 40 // Constant price per kilometer
//            return Pair(distanceInKm, price)
//        } else {
//            throw Exception("No route found")
//        }
//    }
    // Constructor that calculates the distance from the CBD
    // lat1 is cbd and lon2 is cbd
    // val cbdLatitude = -1.286389
    //    val cbdLongitude = 36.817223
//    constructor(name: String, lat: Double, lon: Double) : this(
//        name,
//        calculateDistance(-1.286389, 36.817223, lat, lon),
//        lat,
//        lon
//    )
//
//    val deliveryLocationPrice: Double
//        get() = calculateDeliveryPrice()
//
//    private fun calculateDeliveryPrice(): Double {
//        return deliveryLocationDistanceInKm * 40 // Constant figure of 40 per kilometer
//    }
//
//    // Companion object to hold the calculateDistance function
//    companion object {
//        fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
//            val theta = lon1 - lon2
//            var dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) +
//                    Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
//                    Math.cos(Math.toRadians(theta))
//            dist = Math.acos(dist)
//            dist = Math.toDegrees(dist)
//            dist *= 60 * 1.1515
//            dist *= 1.609344 // Convert to kilometers
//            return dist
//        }
//    }

}
