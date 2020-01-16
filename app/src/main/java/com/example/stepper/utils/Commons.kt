package com.example.stepper.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.provider.Settings
import dmax.dialog.SpotsDialog
import java.util.*

object Commons {

    const val ORDER = "order"

    const val PICK_IMAGE_MULTIPLE = 1111
    const val READ_EXTERNAL_CODE = 2345

    const val BASE_URL = "https://mmmintegrations.com:8090/mpsa_apiint"
    const val ERROR = "error"
    const val SUCCESS = "success"
    const val TIME_INTERVAL = 2000

    const val SAVINGS_BALANCE = "001"
    const val SAVINGS_MINI_STATEMENT = "002"
    const val SHARE_BALANCE = "003"
    const val SHARE_MINI_STATEMENT = "004"
    const val LOAN_BALANCE = "005"
    const val LOAN_MINI_STATEMENT = "006"
    const val NEXT_OF_KIN_DETAILS = "007"
    const val MY_DETAILS = "008"
    const val LOAN_LIMIT = "009"
    const val LOAN_APPLICATION = "010"
    const val REGISTER = "011"

    const val TOKEN = "DLight-Token"

    val MONTHS =
        listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    private val YEARS = mutableListOf<String>()
    fun getYears(): MutableList<String> {
        var year = Calendar.getInstance().get(Calendar.YEAR)
        for (i in 0..6) {
            YEARS.add(year.toString())
            year++
        }
        return YEARS
    }

//    fun isConnectedTo(ssid: String, context: Context): Boolean {
//        val wifiManager =
//            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
//        val wifiInfo = wifiManager.connectionInfo
//        var connectedSSID = ""
//        wifiInfo?.let { connectedSSID = it.ssid }
//        return connectedSSID.isNotEmpty().and(connectedSSID == ssid)
//    }

    fun getDialog(context: Context, message: String?): AlertDialog = SpotsDialog.Builder()
        .setContext(context)
        .setCancelable(false)
        .setMessage(message)
        .build()

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }
}
