package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySettingScreenBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivitySettingScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listener for Edit Profile TextView
        binding.tvEditProfile.setOnClickListener {
            val intent = Intent(this, Editprofile_Screen::class.java)
            startActivity(intent)
        }
        binding.tvCircleManagement.setOnClickListener {
            val intent = Intent(this, CircleScreen::class.java)
            startActivity(intent)
        }
        binding.tvCreategroup.setOnClickListener {
            val intent = Intent(this, CreateGroupScreen::class.java)
            startActivity(intent)
        }
        binding.logoutContainer.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
            finish() // Optional: Finish current activity
        }
    }
}
