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

class LoginActivity : AppCompatActivity() {

    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var forgotPassword: View
    private lateinit var signUpText: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // ===== STATUS BAR SETUP =====
        window.statusBarColor = Color.WHITE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        // ============================

        initializeViews()
        animateViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        emailInputLayout = findViewById(R.id.emailInputLayout)
        passwordInputLayout = findViewById(R.id.passwordInputLayout)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        forgotPassword = findViewById(R.id.forgotPassword)
        signUpText = findViewById(R.id.signUpText)
    }

    private fun animateViews() {
        val views = listOf(
            findViewById<View>(R.id.loginLogo),
            findViewById<View>(R.id.loginAppName),
            emailInputLayout,
            passwordInputLayout,
            loginButton,
            forgotPassword,
            signUpText
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
        loginButton.setOnClickListener {
            handleLogin()
        }

        forgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        signUpText.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleLogin() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        var valid = true

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

        loginButton.isEnabled = false
        loginButton.text = "Logging in..."
        Handler(Looper.getMainLooper()).postDelayed({
            loginButton.isEnabled = true
            loginButton.text = "Login"

            if (email == "tharushi@gmail.com" && password == "123456") {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }, 2000)
    }
}
