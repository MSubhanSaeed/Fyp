package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityLocationScreenBinding

class LocationScreen : AppCompatActivity() {
    private lateinit var binding: ActivityLocationScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityLocationScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up button click listeners using the helper function
        binding.btnHome.setOnClickListener { navigateTo(HomeScreen::class.java) }
        binding.btnIndividual.setOnClickListener { navigateTo(IndividualScreen::class.java) }
        binding.btnCircle.setOnClickListener { navigateTo(CircleScreen::class.java) }
        binding.btnAccount.setOnClickListener { navigateTo(AccountScreen::class.java) }

        // Sample Pakistani cities
        val pakistaniCities = listOf(
            "Karachi", "Lahore", "Islamabad", "Rawalpindi", "Peshawar",
            "Quetta", "Multan", "Faisalabad", "Sialkot", "Hyderabad"
        )

        // Set up ListView adapter using View Binding
        val adapter = LocationAdapter(this, pakistaniCities)
        binding.locationsListView.adapter = adapter
    }

    // Helper function to handle navigation
    private fun navigateTo(destination: Class<*>) {
        startActivity(Intent(this, destination))
    }
}