package com.example.petbuddy

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class OnboardingActivity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_1)

        setupClickListeners()
        handleBackPressed()
    }

    private fun setupClickListeners() {
        // Skip button → Go to Home
        findViewById<TextView>(R.id.skipButton).setOnClickListener {
            navigateToLogin()
        }

        // Get Started button → Go to Onboarding 2
        findViewById<MaterialButton>(R.id.getStartedButton).setOnClickListener {
            navigateToOnboarding2()
        }
    }

    private fun navigateToOnboarding2() {
        val intent = Intent(this, OnboardingActivity2::class.java)
        startActivity(intent)
        // Slide animation
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
        // Fade animation
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun handleBackPressed() {
        // Disable going back from first onboarding screen
        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Close the whole app when back is pressed
                    finishAffinity()
                }
            })
    }
}
