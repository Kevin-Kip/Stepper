package com.example.stepper.requests

import com.example.stepper.models.Price
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @POST("deliveries")
    fun getPrice(
        @Field("pickupLocation") pickupLocation: String,
        @Field("destinationLocation")destinationLocation: String,
        @Field("pickupApartment") pickupApartment: String,
        @Field("destinationApartment") destinationApartment: String,
        @Field("pickupStairs") pickupStairs: String,
        @Field("destinationStairs") destinationStairs: String,
        @Field("furniture") furniture: String,
        @Field("needAssembly") needAssembly: Boolean,
        @Field("furnitureNumber") furnitureNumber: String,
        @Field("distance") distance: String,
        @Field("twoGoodGuys") twoGoodGuys: Boolean,
        @Field("pickupWindow") pickupWindow: String,
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("hasProofOfOwnerShip") hasProofOfOwnerShip: String
    ): Call<Price>

    @POST("claim-notification-file/{uuid}")
    @Multipart
    fun uploadFiles(
        @Part attachments: List<MultipartBody.Part>,
        @Path("uuid") uuid: String
    )

    @GET("deliveries")
    fun attemptRegister(): Call<JSONObject>
}