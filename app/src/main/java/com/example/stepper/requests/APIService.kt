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
//    @Headers({"Accept: application/json","Content-Type:"})
//    @Headers({ "Accept: application/json""User-Agent: Your-App-Name","Cache-Control: max-age=640000"})
    fun uploadFiles(
        @Part attachments: List<MultipartBody.Part>,
//        @Part("attachments") fieldName: RequestBody,
        @Path("uuid") uuid: String
    )

    @GET("deliveries")
    fun attemptRegister(): Call<JSONObject>

//    @POST("requests")
//    @Headers("Content-Type: application/json")
//    fun getProfile(
//        @Header("Authorization") token: String,
//        @Body myProfileData: GeneralRequest
//    ): Call<User>
//
//    @POST("requests")
//    @Headers("Content-Type: application/json")
//    fun getNextOfKin(
//        @Header("Authorization") token: String,
//        @Body nextOfKinData: GeneralRequest
//    ): Call<MutableList<NextOfKin>>
//
//    @POST("requests")
//    @Headers("Content-Type: application/json")
//    fun getLoanMiniStatement(
//        @Header("Authorization") token: String,
//        @Body loanMiniStatementData: LoanMiniStatementData
//    ): Call<MutableList<LoanMiniStatement>>
//
//    @POST("requests")
//    @Headers("Content-Type: application/json")
//    fun getLoanBalance(
//        @Header("Authorization") token: String,
//        @Body requestCode: GeneralRequest
//    ): Call<MutableList<LoanBalance>>
//
//    @POST("requests")
//    @Headers("Content-Type: application/json")
//    fun getShareMiniStatement(
//        @Header("Authorization") token: String,
//        @Body shareMiniStatementData: GeneralRequest
//    ): Call<MutableList<ShareMiniStatement>>
//
//    @POST("requests")
//    @Headers("Content-Type: application/json")
//    fun getShareBalance(
//        @Header("Authorization") token: String,
//        @Body shareBalanceData: GeneralRequest
//    ): Call<ShareBalance>
//
//    @POST("requests")
//    @Headers("Content-Type: application/json")
//    fun getSavingsMiniStatement(
//        @Header("Authorization") token: String,
//        @Body savingMiniStatementData: GeneralRequest
//    ): Call<MutableList<SavingsMiniStatement>>
//
//    @POST("requests")
//    @Headers("Content-Type: application/json")
//    fun getSavingsBalance(
//        @Header("Authorization") token: String,
//        @Body savingsBalanceData: GeneralRequest
//    ): Call<SavingsBalance>
//
//    @POST("requests")
//    @Headers("Content-Type: application/json")
//    fun verifyOTP(
//        @Header("Authorization") token: String,
//        @Body otp: VerifyOtp
//    ): Call<AuthResponse>
//
//    @POST("requests")
//    @Headers("Content-Type: application/json")
//    fun requestLoan(
//        @Header("Authorization") token: String,
//        @Body loanApplicationData: LoanApplicationData
//    )
//
//    @POST("requests")
//    @Headers("Content-Type: application/json")
//    fun getLoanLimit(
//        @Header("Authorization") token: String,
//        @Body loanLimitData: GeneralRequest
//    ): Call<LoanLimit>
}