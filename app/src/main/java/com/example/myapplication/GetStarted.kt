package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityGetStartedBinding

class GetStarted : AppCompatActivity() {

    // Declare the binding variable
    private lateinit var binding: ActivityGetStartedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the binding object
        binding = ActivityGetStartedBinding.inflate(layoutInflater)

        // Set the content view to the root of the binding object
        setContentView(binding.root)

        // Set an OnClickListener for the button using binding
        binding.getstarted.setOnClickListener {
            // Create an Intent to start the LoginScreen activity
            val intent = Intent(this, SignupScreen::class.java)
            startActivity(intent) // Start the next activity
        }
    }
}
