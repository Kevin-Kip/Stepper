package com.example.stepper

import `in`.madapps.placesautocomplete.PlaceAPI
import `in`.madapps.placesautocomplete.adapter.PlacesAutoCompleteAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.stepper.models.Order
import com.example.stepper.utils.Commons
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val stairs =
        listOf("1 flight", "2 flights", "3 flights", "4 flights", "5 flights", "No stairs")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        button_next.setOnClickListener {
            submitData()
        }

        val placesApi = PlaceAPI.Builder().apiKey("AIzaSyCX_Kk1ovrMy7adzAq99L40gSc5mVwB8Lo")
            .build(this@MainActivity)
        pickup_location_input.setAdapter(PlacesAutoCompleteAdapter(this, placesApi))
        destination_location_input.setAdapter(PlacesAutoCompleteAdapter(this, placesApi))

        pickup_stairs_input.setItems(stairs)
        destination_stairs_input.setItems(stairs)
    }

//    private fun initPlaces() {
//        if (!Places.isInitialized()) {
//            Places.initialize(applicationContext, getString(R.string.google_maps_key))
//        }
//
//        val pickupLocationFragment =
//            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
//        pickupLocationFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))
//        pickupLocationFragment.setHint("Pickup Location")
//
//        pickupLocationFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
//            override fun onPlaceSelected(place: Place) {
//                val latLng = place.latLng!!
//                mMap.addMarker(MarkerOptions().position(latLng))
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
//            }
//
//            override fun onError(p0: Status) {
//
//            }
//        })
//
//        val destinationLocationFragment =
//            supportFragmentManager.findFragmentById(R.id.destination_autocomplete_fragment) as AutocompleteSupportFragment
//        destinationLocationFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))
//        destinationLocationFragment.setHint("Destination Location")
//
//        destinationLocationFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
//            override fun onPlaceSelected(place: Place) {
//                val latLng = place.latLng!!
//                mMap.addMarker(MarkerOptions().position(latLng))
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
//            }
//
//            override fun onError(p0: Status) {
//
//            }
//        })
//    }

    private fun submitData() {
        val order = Order()
        order.pickupLocation = pickup_location_input.text.toString()
        order.destinationLocation = destination_location_input.text.toString()
        order.pickupApartment = pickup_apartment_input.text.toString()
        order.destinationApartment = destination_apartment_input.text.toString()
        order.pickupStairs = stairs[pickup_stairs_input.selectedIndex]
        order.destinationStairs = stairs[destination_stairs_input.selectedIndex]

        val i = Intent(this@MainActivity, SelectFurnitureActivity::class.java)
        i.putExtra(Commons.ORDER, order)
        startActivity(i)
        overridePendingTransition(R.anim.right_to_left_enter, R.anim.left_to_right_enter)
    }

    override fun onResume() {
        super.onResume()
        val order = intent.getParcelableExtra(Commons.ORDER) as Order?
        if (order != null) {
            if (order!!.pickupLocation != null) {
                pickup_location_input.setText(order!!.pickupLocation, TextView.BufferType.EDITABLE)
            }
            if (order!!.destinationLocation != null) {
                destination_location_input.setText(
                    order!!.destinationLocation,
                    TextView.BufferType.EDITABLE
                )
            }
            if (order!!.pickupApartment != null) {
                pickup_apartment_input.setText(
                    order!!.pickupApartment,
                    TextView.BufferType.EDITABLE
                )
            }
            if (order!!.destinationApartment != null) {
                destination_apartment_input.setText(
                    order!!.destinationApartment,
                    TextView.BufferType.EDITABLE
                )
            }
            if (order!!.pickupStairs != null) {
                val index = stairs.indexOf(order!!.pickupStairs!!)
                pickup_stairs_input.selectedIndex = index
            }
            if (order!!.destinationStairs != null) {
                val index = stairs.indexOf(order!!.destinationStairs!!)
                destination_stairs_input.selectedIndex = index
            }
        }
    }

    //    override fun onItemSelected(selectedPlace: Place?) {
//        val latLng = selectedPlace?.latLng!!
//        mMap.addMarker(MarkerOptions().position(latLng))
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
//    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onMapReady(map: GoogleMap?) {
        mMap = map!!
    }
}

