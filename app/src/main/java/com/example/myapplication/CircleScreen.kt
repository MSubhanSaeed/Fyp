package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityCircleScreenBinding

class CircleScreen : AppCompatActivity() {
    private lateinit var binding: ActivityCircleScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Initialize ViewBinding
        binding = ActivityCircleScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

//        // Set up the places button click listener
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
    }
}