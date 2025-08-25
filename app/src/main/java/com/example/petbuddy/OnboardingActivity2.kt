package com.example.petbuddy

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class OnboardingActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_2)

        setupClickListeners()
        handleBackPressed()
    }

    private fun setupClickListeners() {
        // Skip button
        findViewById<TextView>(R.id.skipButton).setOnClickListener {
            navigateToLogin()
        }

        // Start Now button
        findViewById<MaterialButton>(R.id.startNowButton).setOnClickListener {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
        // Add fade animation
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        // Save onboarding completion status
        saveOnboardingCompleted()
    }

    private fun saveOnboardingCompleted() {
        val sharedPref = getSharedPreferences("PetBuddyPrefs", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("onboarding_completed", true)
            apply()
        }
    }

    private fun handleBackPressed() {
        // Use OnBackPressedDispatcher instead of onBackPressed()
        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val intent = Intent(this@OnboardingActivity2, OnboardingActivity1::class.java)
                    startActivity(intent)
                    finish()
                    // Add slide animation
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                }
            })
    }
}
