package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityLoginScreenBinding

class LoginScreen : AppCompatActivity() {
    // Declare the binding variable
    private lateinit var binding: ActivityLoginScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the binding object
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)

        // Set the content view to the root of the binding object
        setContentView(binding.root)

        // Set up the CheckBox with clickable "terms and condition" text
        setupCheckBox()

        // Set up the TextView with clickable "Signup" text
        setupSignupTextView()

        // Set OnClickListener for the Login Button
        binding.appCompatButton.setOnClickListener {
            // Navigate to the HomeScreen
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }
    }

    private fun setupCheckBox() {
        // Access the CheckBox using View Binding
        val checkBox: CheckBox = binding.checkBox2

        // Full text for CheckBox
        val checkBoxText = "By creating an account, you agree to our terms and condition"

        // Create a SpannableString for CheckBox
        val checkBoxSpannableString = SpannableString(checkBoxText)

        // Define the start and end index of the clickable text for CheckBox
        val checkBoxStartIndex = checkBoxText.indexOf("terms and condition")
        val checkBoxEndIndex = checkBoxStartIndex + "terms and condition".length

        // Make the text clickable for CheckBox
        val checkBoxClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Display a Toast message
                Toast.makeText(this@LoginScreen, "Terms and Conditions clicked!", Toast.LENGTH_SHORT).show()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                // Customize the appearance of the clickable text
                ds.color = ContextCompat.getColor(this@LoginScreen, R.color.teal_700) // Set text color
                ds.isUnderlineText = true // Add underline
            }
        }

        // Apply the ClickableSpan to the specific part of the text for CheckBox
        checkBoxSpannableString.setSpan(checkBoxClickableSpan, checkBoxStartIndex, checkBoxEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Set the SpannableString to the CheckBox
        checkBox.text = checkBoxSpannableString

        // Enable clicking on the text for CheckBox
        checkBox.movementMethod = LinkMovementMethod.getInstance()

        // Disable highlighting of the text when clicked (optional) for CheckBox
        checkBox.highlightColor = Color.TRANSPARENT
    }

    private fun setupSignupTextView() {
        // Access the TextView using View Binding
        val textViewSignup = binding.textViewSignup

        // Full text for TextView
        val signupText = "Don't have an account? Signup"

        // Create a SpannableString for TextView
        val signupSpannableString = SpannableString(signupText)

        // Define the start and end index of the clickable text ("Signup") for TextView
        val signupStartIndex = signupText.indexOf("Signup")
        val signupEndIndex = signupStartIndex + "Signup".length

        // Make the text clickable for TextView
        val signupClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Navigate to the SignupScreen
                val intent = Intent(this@LoginScreen, SignupScreen::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                // Customize the appearance of the clickable text
                ds.color = ContextCompat.getColor(this@LoginScreen, R.color.teal_700) // Set text color to blue
                ds.isUnderlineText = true // Add underline (optional)
            }
        }

        // Apply the ClickableSpan to the specific part of the text for TextView
        signupSpannableString.setSpan(signupClickableSpan, signupStartIndex, signupEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Set the SpannableString to the TextView
        textViewSignup.text = signupSpannableString

        // Enable clicking on the text for TextView
        textViewSignup.movementMethod = LinkMovementMethod.getInstance()

        // Disable highlighting of the text when clicked (optional) for TextView
        textViewSignup.highlightColor = Color.TRANSPARENT
    }
}