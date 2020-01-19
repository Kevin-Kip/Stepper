package com.example.stepper

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.stepper.models.Order
import com.example.stepper.models.Stairs
import com.example.stepper.utils.Commons
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var pickupLocation: String? = null
    private var destinationLocation: String? = null

    private val stairNames =
        listOf(
            "1 flight",
            "2 flights",
            "3 flights",
            "4 flights",
            "5 flights",
            "6 flights",
            "No flight"
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        button_next.setOnClickListener {
            submitData()
        }

        pickupInput.setAdapter(
            PlacesuggestionsAdapter(
                this@MainActivity,
                android.R.layout.simple_list_item_1
            )
        )
        destinationInput.setAdapter(
            PlacesuggestionsAdapter(
                this@MainActivity,
                android.R.layout.simple_list_item_1
            )
        )

        initViewListeners()
//        initPlaces()

        pickup_stairs_input.setItems(stairNames)
        destination_stairs_input.setItems(stairNames)
    }

    private fun initViewListeners() {
        pickupInput.addTextChangedListener { text->
            if (text.toString().isNotEmpty()){
                pickup_location_parent.isErrorEnabled=false
            }
        }
        destinationInput.addTextChangedListener { text->
            if (text.toString().isNotEmpty()){
                destination_location_parent.isErrorEnabled=false
            }
        }
        pickup_apartment_input.addTextChangedListener { text->
            if (text.toString().isNotEmpty()){
                pickup_apartment_parent.isErrorEnabled=false
            }
        }
        destination_apartment_input.addTextChangedListener { text->
            if (text.toString().isNotEmpty()){
                destination_apartment_parent.isErrorEnabled=false
            }
        }
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
//                pickupLocation = place.address
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
//                destinationLocation = place.address
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
        pickupLocation = pickupInput.text.toString()
        destinationLocation = destinationInput.text.toString()
        val pickupStairs: Stairs? = Commons.stairs[pickup_stairs_input.selectedIndex]
        val destinationStairs: Stairs? = Commons.stairs[destination_stairs_input.selectedIndex]
        val pickupApartment:String? = pickup_apartment_input.text.toString()
        val destinationApartment:String? = destination_apartment_input.text.toString()
        order.pickupLocation = pickupLocation
        order.destinationLocation = destinationLocation
        order.pickupApartment = pickupApartment
        order.destinationApartment = destinationApartment
        order.pickupStairs = pickupStairs
        order.destinationStairs = destinationStairs

        if (pickupLocation != null && destinationLocation != null && pickupStairs != null
            && destinationStairs != null && pickupApartment?.isNotEmpty()!!
            && destinationApartment?.isNotEmpty()!!
        ) {
            val i = Intent(this@MainActivity, SelectFurnitureActivity::class.java)
            i.putExtra(Commons.ORDER, order)
            startActivity(i)
            overridePendingTransition(R.anim.right_to_left_enter, R.anim.left_to_right_enter)
        } else{
            if (pickupLocation.isNullOrEmpty()){
                pickup_location_parent.isErrorEnabled=true
                pickup_location_parent.error="Please provide pickup location"
            }
            if (destinationLocation.isNullOrEmpty()){
                destination_location_parent.isErrorEnabled=true
                destination_location_parent.error="Please provide destination location"
            }
            if (pickupApartment == null){
                pickup_apartment_parent.isErrorEnabled=true
                pickup_apartment_parent.error="Please provide pickup apartment"
            }
            if (destinationApartment == null){
                destination_apartment_parent.isErrorEnabled=true
                destination_apartment_parent.error="Please provide destination apartment"
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val order = intent.getSerializableExtra(Commons.ORDER) as Order?
        if (order != null) {
            if (order!!.pickupLocation != null) {
                pickupLocation = order!!.pickupLocation
            }
            if (order!!.destinationLocation != null) {
                destinationLocation = order!!.destinationLocation
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
//                val index = Commons.stairs.indexOf(Stairs(order!!.pickupStairs!!))
//                pickup_stairs_input.selectedIndex = index
            }
            if (order!!.destinationStairs != null) {
//                val index = Commons.stairs.indexOf(Stairs(order!!.destinationStairs!!))
//                destination_stairs_input.selectedIndex = index
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

