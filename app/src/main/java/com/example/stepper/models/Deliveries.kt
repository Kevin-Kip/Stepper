package com.example.stepper.models

import java.io.Serializable

class Deliveries(
    var id: Int? = null,
    var description: String? = null,
    var price:Float?=null,
    var count:Int=1,
    var limit:Int?=null
):Serializable