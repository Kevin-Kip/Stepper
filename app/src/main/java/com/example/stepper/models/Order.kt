package com.example.stepper.models

import android.os.Parcel
import android.os.Parcelable

class Order(
    var pickupLocation: String? = null,
    var destinationLocation: String? = null,
    var pickupApartment: String? = null,
    var destinationApartment: String? = null,
    var pickupStairs: String? = null,
    var destinationStairs: String? = null,
    var furniture: String? = null,
    var needAssembly: Boolean? = null,
    var furnitureNumber: String? = null,
    var distance: String? = null,
    var twoGoodGuys: Boolean? = null,
    var pickupWindow: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var hasProofOfOwnerShip: String? = null
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pickupLocation)
        parcel.writeString(destinationLocation)
        parcel.writeString(pickupApartment)
        parcel.writeString(destinationApartment)
        parcel.writeString(pickupStairs)
        parcel.writeString(destinationStairs)
        parcel.writeString(furniture)
        parcel.writeValue(needAssembly)
        parcel.writeString(furnitureNumber)
        parcel.writeString(distance)
        parcel.writeValue(twoGoodGuys)
        parcel.writeString(pickupWindow)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeString(hasProofOfOwnerShip)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Order> {
        override fun createFromParcel(parcel: Parcel): Order {
            return Order(parcel)
        }

        override fun newArray(size: Int): Array<Order?> {
            return arrayOfNulls(size)
        }
    }
}