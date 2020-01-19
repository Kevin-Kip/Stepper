package com.example.stepper.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.provider.Settings
import com.example.stepper.models.Deliveries
import com.example.stepper.models.Distance
import com.example.stepper.models.Stairs
import dmax.dialog.SpotsDialog
import java.util.*

object Commons {

    const val PAYMENT = "payment"
    const val CLIENT_TOKEN = "client-token"
    const val PAYPAL_CONFIG = ""

    const val ORDER = "order"
    const val PRICE = "price"

    const val PICK_IMAGE_MULTIPLE = 1111
    const val READ_EXTERNAL_CODE = 2345

    const val BASE_URL = "https://yourwebsite.com/api"

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

    val furnitures = listOf(
        Deliveries(1, "Sofa or Loveseat or Recliner", 45F),
        Deliveries(2, "Boxed items (up to 4 “3x3 boxes”) ", 45F, limit = 4),
        Deliveries(3, "Area Rugs (up to 3 rugs)", 55F, limit = 3),
        Deliveries(4, "Hardwood / Laminate / Vinyl Flooring (up to 350lb)", 125F),
        Deliveries(5, "Hardwood / Laminate / Vinyl Flooring (up to 700lb)", 175F),
        Deliveries(6, "Hardwood / Laminate / Vinyl Flooring (up to 1500lb)", 225F),
        Deliveries(7, "Ottoman or Single Person Chair ", 25F),
        Deliveries(8, "Sectional (up to 4 pieces)", 150F, limit = 4),
        Deliveries(9, "Dining Room Set (table and up to 6 chairs)", 75F),
        Deliveries(10, "Patio Furniture Set (table and up to 6 chairs) ", 75F),
        Deliveries(11, "Outdoor Grill or Smoker ", 75F),
        Deliveries(12, "Mattress/Boxspring Set (Queen or King)", 50F),
        Deliveries(13, "Mattress/Boxspring Set (Twin or Full)", 45F),
        Deliveries(14, "Bedroom Set (Frame or Headboard or Footboard)", 25F),
        Deliveries(15, "Bedroom Set (Nightstand up to 2)", 25F, limit = 2),
        Deliveries(16, "Dresser / Entertainment Stand", 50F),
        Deliveries(17, "Appliances (Washer or Dryer)", 45F),
        Deliveries(18, "Appliances (Refrigerator)", 100F),
        Deliveries(19, "Appliances (Stove or Dishwasher)", 45F),
        Deliveries(20, "Miscellaneous Large Item 51lb or more", 75F),
        Deliveries(21, "Miscellaneous Small Item 50lb or less", 40F),
        Deliveries(22, "Roll of Carpet (up to 350lb)", 125F),
        Deliveries(23, "Roll of Carpet (up to 700lb)", 175F)
    )

    val distances = listOf(
        Distance(1, "1-5 miles", 0F),
        Distance(2, "6-10 miles", 10F),
        Distance(3, "11-15 miles", 20F),
        Distance(4, "21-30 miles", 30F)
    )

    val stairs =
        listOf(
            Stairs(1, "1 flight", 15F),
            Stairs(2, "2 flights", 30F),
            Stairs(3, "3 flights", 45F),
            Stairs(4, "4 flights", 60F),
            Stairs(5, "5 flights", 75F),
            Stairs(6, "6 flights", 90F),
            Stairs(7, "No flight", 0F)
        )

    fun getUrl(apiKey: String, name: String): String {
        return "https://maps.googleapis.com/maps/api/place/autocomplete/json?" +
                "input=$name&key=$apiKey"
    }
}
