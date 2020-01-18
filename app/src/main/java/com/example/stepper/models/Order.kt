package com.example.stepper.models

import java.io.Serializable

class Order(
    var pickupLocation: String? = null,
    var destinationLocation: String? = null,
    var pickupApartment: String? = null,
    var destinationApartment: String? = null,
    var pickupStairs: Stairs? = null,
    var destinationStairs: Stairs? = null,
    var needAssembly: Boolean? = null,
    var distance: Distance? = null,
    var twoGoodGuys: Boolean? = null,
    var pickupWindow: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var hasProofOfOwnerShip: String? = null,
    var furniture: MutableList<Deliveries>? = null,
    var price:Float? = null
):Serializable