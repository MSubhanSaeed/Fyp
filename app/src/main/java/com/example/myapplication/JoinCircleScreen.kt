package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityJoinCircleScreenBinding

class JoinCircleScreen : AppCompatActivity() {

    private lateinit var binding: ActivityJoinCircleScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize binding and set the content view
        binding = ActivityJoinCircleScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the back arrow click
        binding.backArrow.setOnClickListener {
            val intent = Intent(this, CreateGroupScreen::class.java)
            startActivity(intent)
            finish() // optional: close current activity if you don’t want to return back to it
        }
    }
}
