package com.example.petbuddy

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignupActivity : AppCompatActivity() {

    private lateinit var fullNameInputLayout: TextInputLayout
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout

    private lateinit var fullNameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText

    private lateinit var signupButton: MaterialButton
    private lateinit var loginText: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // ===== STATUS BAR SETUP =====
        window.statusBarColor = Color.WHITE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        // ============================

        initializeViews()
        animateViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        fullNameInputLayout = findViewById(R.id.fullNameInputLayout)
        emailInputLayout = findViewById(R.id.emailInputLayout)
        passwordInputLayout = findViewById(R.id.passwordInputLayout)

        fullNameEditText = findViewById(R.id.fullNameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        signupButton = findViewById(R.id.signupButton)
        loginText = findViewById(R.id.loginText)
    }

    private fun animateViews() {
        val views = listOf(
            findViewById<View>(R.id.signupLogo),
            findViewById<View>(R.id.signupAppName),
            fullNameInputLayout,
            emailInputLayout,
            passwordInputLayout,
            signupButton,
            loginText
        )

        views.forEachIndexed { index, view ->
            view.alpha = 0f
            view.translationY = 50f
            view.animate()
                .alpha(1f)
                .translationY(0f)
                .setStartDelay((index * 150).toLong())
                .setDuration(500)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .start()
        }
    }

    private fun setupClickListeners() {
        signupButton.setOnClickListener {
            handleSignup()
        }

        loginText.setOnClickListener {
            // Navigate back to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun handleSignup() {
        val fullName = fullNameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        var valid = true

        if (TextUtils.isEmpty(fullName)) {
            fullNameInputLayout.error = "Full Name cannot be empty"
            valid = false
        } else {
            fullNameInputLayout.error = null
        }

        if (TextUtils.isEmpty(email)) {
            emailInputLayout.error = "Email cannot be empty"
            valid = false
        } else {
            emailInputLayout.error = null
        }

        if (TextUtils.isEmpty(password)) {
            passwordInputLayout.error = "Password cannot be empty"
            valid = false
        } else {
            passwordInputLayout.error = null
        }

        if (!valid) return

        signupButton.isEnabled = false
        signupButton.text = "Signing up..."
        Handler(Looper.getMainLooper()).postDelayed({
            signupButton.isEnabled = true
            signupButton.text = "Sign Up"

            Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show()

            // Navigate to LoginActivity after signup
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}
