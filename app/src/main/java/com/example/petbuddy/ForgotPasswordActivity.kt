package com.example.petbuddy

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText
    private lateinit var resetButton: MaterialButton
    private lateinit var backToLogin: android.view.View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // ===== STATUS BAR SETUP =====
        window.statusBarColor = Color.WHITE
        window.decorView.systemUiVisibility = android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        // ============================

        emailInputLayout = findViewById(R.id.emailInputLayout)
        emailEditText = findViewById(R.id.emailEditText)
        resetButton = findViewById(R.id.resetButton)
        backToLogin = findViewById(R.id.backToLogin)

        resetButton.setOnClickListener {
            handleResetPassword()
        }

        backToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun handleResetPassword() {
        val email = emailEditText.text.toString().trim()
        if (TextUtils.isEmpty(email)) {
            emailInputLayout.error = "Email cannot be empty"
            return
        } else {
            emailInputLayout.error = null
        }

        // Simulate reset password
        Toast.makeText(this, "Password reset link sent to $email", Toast.LENGTH_SHORT).show()
    }
}
