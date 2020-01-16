package com.example.stepper.requests

import com.example.stepper.utils.Commons

object APIClient {
    private const val BASE_URL = Commons.BASE_URL+"/"
    fun getAPIService(): APIService = RetrofitClient.getClient(BASE_URL)!!.create(APIService::class.java)
}