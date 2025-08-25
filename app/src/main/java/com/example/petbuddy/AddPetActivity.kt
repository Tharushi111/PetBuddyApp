package com.example.petbuddy

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.ImageButton
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.example.petbuddy.R

class AddPetActivity : AppCompatActivity() {

    private lateinit var petNameEditText: TextInputEditText
    private lateinit var petTypeEditText: TextInputEditText
    private lateinit var petAgeEditText: TextInputEditText
    private lateinit var petWeightEditText: TextInputEditText
    private lateinit var selectImageButton: Button
    private lateinit var savePetButton: Button
    private lateinit var backButton: ImageButton

    private var petImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pet)

        // ===== STATUS BAR SETUP =====
        window.statusBarColor = Color.WHITE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        // ============================

        // Initialize views
        backButton = findViewById(R.id.backButton)
        petNameEditText = findViewById(R.id.petNameEditText)
        petTypeEditText = findViewById(R.id.petTypeEditText)
        petAgeEditText = findViewById(R.id.petAgeEditText)
        petWeightEditText = findViewById(R.id.petWeightEditText)
        selectImageButton = findViewById(R.id.selectImageButton)
        savePetButton = findViewById(R.id.savePetButton)

        // Handle back button click
        backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        // Disable handwriting / suggestions for all fields
        petNameEditText.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        petTypeEditText.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        petAgeEditText.inputType = InputType.TYPE_CLASS_NUMBER
        petWeightEditText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        // Ensure soft keyboard behaves correctly
        petNameEditText.showSoftInputOnFocus = true
        petTypeEditText.showSoftInputOnFocus = true
        petAgeEditText.showSoftInputOnFocus = true
        petWeightEditText.showSoftInputOnFocus = true

        // Select pet image
        selectImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 100)
        }

        // Save pet
        savePetButton.setOnClickListener {
            val name = petNameEditText.text.toString()
            val type = petTypeEditText.text.toString()
            val age = petAgeEditText.text.toString()
            val weight = petWeightEditText.text.toString()

            if (name.isEmpty() || type.isEmpty() || age.isEmpty() || weight.isEmpty() || petImageUri == null) {
                Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // TODO: Save pet data to database or pass back to main screen
            Toast.makeText(this, "Pet added successfully!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            petImageUri = data?.data
            selectImageButton.text = "Image Selected"
        }
    }
}
