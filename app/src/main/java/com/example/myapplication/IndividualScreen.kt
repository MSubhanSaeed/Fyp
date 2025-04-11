package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityIndividualScreenBinding
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

class IndividualScreen : AppCompatActivity(), OnMapReadyCallback {

    private var mGoogleMap: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var binding: ActivityIndividualScreenBinding

    private val PICK_CONTACT_REQUEST = 1 // Request code for the Contacts Picker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityIndividualScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize the map
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Set up the map type change button
        binding.btnMapType.setOnClickListener {
            changeMapType()
        }

        // Set up the contact picker button
        binding.fabAdd.setOnClickListener {
            // Launch the Contacts Picker
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            startActivityForResult(intent, PICK_CONTACT_REQUEST)
        }
        binding.shareloc.setOnClickListener {
            // Launch the Contacts Picker
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            startActivityForResult(intent, PICK_CONTACT_REQUEST)
        }

        // Set up the home button click listener
        binding.btnHome.setOnClickListener {
            // Create an Intent to start HomeActivity
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }

        // Set up the individual button click listener
        binding.btnIndividual.setOnClickListener {
            // Create an Intent to start IndividualScreen
            val intent = Intent(this, IndividualScreen::class.java)
            startActivity(intent)
        }

        // Set up the places button click listener
        binding.btnPlaces.setOnClickListener {
            // Create an Intent to start PlacesScreen
            val intent = Intent(this, LocationScreen::class.java)
            startActivity(intent)
        }

        // Set up the circle button click listener
        binding.btnCircle.setOnClickListener {
            // Create an Intent to start CircleScreen
            val intent = Intent(this, CircleScreen::class.java)
            startActivity(intent)
        }

        // Set up the account button click listener
        binding.btnAccount.setOnClickListener {
            // Create an Intent to start AccountScreen
            val intent = Intent(this, AccountScreen::class.java)
            startActivity(intent)
        }
        // Check and request location permissions
        if (checkLocationPermissions()) {
            startLocationUpdates()
        } else {
            requestLocationPermissions()
        }

        // Initialize and set up the Spinner
        setupSpinner()
    }

    private fun resizeButtonIcon(button: AppCompatButton, drawableResId: Int) {
        // Get the drawable from resources
        val drawable = ContextCompat.getDrawable(this, drawableResId)

        // Calculate the desired icon size (e.g., 70% of the button's height)
        val iconSize = (button.height * 0.7).toInt()

        // Set the bounds of the drawable
        drawable?.setBounds(0, 0, iconSize, iconSize)

        // Apply the resized drawable to the button
        button.setCompoundDrawables(drawable, null, null, null)
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

    // Initialize and set up the Spinner
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
                Toast.makeText(this@IndividualScreen, "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_CONTACT_REQUEST && resultCode == RESULT_OK) {
            // Get the contact URI
            val contactUri: Uri? = data?.data

            // Query the contact's details
            contactUri?.let { uri ->
                val cursor = contentResolver.query(uri, null, null, null, null)
                cursor?.use {
                    if (it.moveToFirst()) {
                        // Get the contact's display name
                        val displayName = it.getString(
                            it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                        )
                        Log.d("SelectedContact", "Name: $displayName")

                        // Get the contact's phone number (if available)
                        val contactId = it.getString(
                            it.getColumnIndex(ContactsContract.Contacts._ID)
                        )
                        val phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            arrayOf(contactId),
                            null
                        )
                        phoneCursor?.use { pc ->
                            if (pc.moveToFirst()) {
                                val phoneNumber = pc.getString(
                                    pc.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                                )
                                Log.d("SelectedContact", "Phone: $phoneNumber")
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }
}