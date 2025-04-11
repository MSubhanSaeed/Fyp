package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapp.LocationScreen
import com.example.myapplication.databinding.ActivityHomeScreenBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.random.Random


class HomeScreen : AppCompatActivity(), OnMapReadyCallback {

    private var mGoogleMap: GoogleMap? = null
    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        val randomNames = listOf("Ali", "Ahmed", "Aisha", "Fatima", "Hassan", "Zain", "Hina", "Omar", "Sara", "Bilal")

        // Generate random pairs with tracking enabled or disabled
        val namePairs = List(5) {
            val name = randomNames[Random.nextInt(randomNames.size)]
            val trackingEnabled = Random.nextBoolean()
            Pair(name, trackingEnabled)
        }

        // Set adapter
        val adapter = TrackingAdapter(this, namePairs)
        binding.listView.adapter = adapter
        binding.btnHome.setOnClickListener {
            // Create an Intent to start HomeActivity
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }
        binding.btnIndividual.setOnClickListener {
            // Create an Intent to start HomeActivity
            val intent = Intent(this, IndividualScreen::class.java)
            startActivity(intent)
        }
        binding.btnPlaces.setOnClickListener {
            // Create an Intent to start HomeActivity
            val intent = Intent(this, LocationScreen::class.java)
            startActivity(intent)
        }
        binding.btnCircle.setOnClickListener {
            // Create an Intent to start HomeActivity
            val intent = Intent(this, CircleScreen::class.java)
            startActivity(intent)
        }
        binding.btnAccount.setOnClickListener {
            // Create an Intent to start HomeActivity
            val intent = Intent(this, AccountScreen::class.java)
            startActivity(intent)
        }
        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Access the map fragment using View Binding
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        if (mapFragment == null) {
            Log.e("HomeScreen", "Map fragment is null")
            // Handle the error (e.g., show a message to the user)
        } else {
            mapFragment.getMapAsync(this)
        }

        // Check and request location permissions
        if (checkLocationPermissions()) {
            startLocationUpdates()
        } else {
            requestLocationPermissions()
        }

        // Set up the map type button
        val btnMapType: Button = binding.btnMapType
        btnMapType.setOnClickListener {
            changeMapType()
        }

        // Initialize and set up the Spinner
        setupSpinner()
    }

    private fun setupSpinner() {
        // Get the Spinner using View Binding
        val spinner: Spinner = binding.dropDownMenu

        // Create an ArrayAdapter using the string array and a custom layout
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.dropdown_items, // String array resource
            R.layout.custom_spinner_item // Custom layout for each item
        )

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the Spinner
        spinner.adapter = adapter

        // Set a listener for item selection
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Handle item selection
                val selectedItem = parent?.getItemAtPosition(position).toString()
                Toast.makeText(this@HomeScreen, "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Log.d("HomeScreen", "Map is ready")
        mGoogleMap = googleMap

        // Set the default map type to Normal
        mGoogleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL

        // Enable My Location Button
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap?.isMyLocationEnabled = true
        }

        // Enable Zoom Controls
        mGoogleMap?.uiSettings?.isZoomControlsEnabled = true

        // Enable Compass
        mGoogleMap?.uiSettings?.isCompassEnabled = true
    }

    // Change the map type
    private fun changeMapType() {
        when (mGoogleMap?.mapType) {
            GoogleMap.MAP_TYPE_NORMAL -> {
                mGoogleMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
                binding.btnMapType.text = "Satellite"
            }
            GoogleMap.MAP_TYPE_SATELLITE -> {
                mGoogleMap?.mapType = GoogleMap.MAP_TYPE_TERRAIN
                binding.btnMapType.text = "Terrain"
            }
            GoogleMap.MAP_TYPE_TERRAIN -> {
                mGoogleMap?.mapType = GoogleMap.MAP_TYPE_HYBRID
                binding.btnMapType.text = "Hybrid"
            }
            GoogleMap.MAP_TYPE_HYBRID -> {
                mGoogleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
                binding.btnMapType.text = "Normal"
            }
            else -> {
                mGoogleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
                binding.btnMapType.text = "Normal"
            }
        }

    }

    // Check if location permissions are granted
    private fun checkLocationPermissions(): Boolean {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }

    // Request location permissions
    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    // Start receiving location updates
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        if (checkLocationPermissions()) {
            val locationRequest = LocationRequest.create().apply {
                interval = 10000 // Update interval in milliseconds
                fastestInterval = 5000 // Fastest update interval
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult.lastLocation?.let { location ->
                        // Update the map with the user's live location
                        val userLatLng = LatLng(location.latitude, location.longitude)
                        mGoogleMap?.clear() // Clear previous markers
                        val userLocationMarker = mGoogleMap?.addMarker(
                            MarkerOptions().position(userLatLng).title("Me")
                        )
                        userLocationMarker?.showInfoWindow() // Show the title by default
                        mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 16f))
                    }
                }
            }

            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    // Handle permission request result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()
            } else {
                Log.e("HomeScreen", "Location permission denied")
                // Show a rationale or redirect the user to app settings
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop location updates when the activity is destroyed
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }
}