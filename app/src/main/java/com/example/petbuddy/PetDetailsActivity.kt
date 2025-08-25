package com.example.petbuddy

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Button
import android.widget.EditText
import java.util.*

class PetDetailsActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var btnChangePhoto: ImageButton
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button
    private lateinit var etPetName: EditText
    private lateinit var etBreed: EditText
    private lateinit var etAge: EditText
    private lateinit var etWeight: EditText
    private lateinit var etColor: EditText
    private lateinit var etDob: EditText
    private lateinit var etMicrochip: EditText
    private lateinit var etOwnerName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etNotes: EditText
    private lateinit var spinnerGender: Spinner
    private lateinit var spinnerSize: Spinner
    private lateinit var ivPetImage: ImageView

    private var selectedImageUri: Uri? = null

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            ivPetImage.setImageURI(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_details)



        initViews()
        setupSpinners()
        loadPetData()
        setupClickListeners()
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btn_back)
        btnSave = findViewById(R.id.btn_save)
        btnCancel = findViewById(R.id.btn_cancel)
        btnChangePhoto = findViewById(R.id.btn_change_photo)
        etPetName = findViewById(R.id.et_pet_name)
        etBreed = findViewById(R.id.et_breed)
        etAge = findViewById(R.id.et_age)
        etWeight = findViewById(R.id.et_weight)
        etColor = findViewById(R.id.et_color)
        etDob = findViewById(R.id.et_dob)
        etMicrochip = findViewById(R.id.et_microchip)
        etOwnerName = findViewById(R.id.et_owner_name)
        etPhone = findViewById(R.id.et_phone)
        etNotes = findViewById(R.id.et_notes)
        spinnerGender = findViewById(R.id.spinner_gender)
        spinnerSize = findViewById(R.id.spinner_size)
        ivPetImage = findViewById(R.id.iv_pet_image)
    }

    private fun setupSpinners() {
        val genderAdapter = ArrayAdapter.createFromResource(
            this, R.array.gender_options, android.R.layout.simple_spinner_item
        )
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = genderAdapter

        val sizeAdapter = ArrayAdapter.createFromResource(
            this, R.array.size_options, android.R.layout.simple_spinner_item
        )
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSize.adapter = sizeAdapter
        spinnerSize.setSelection(2) // Large by default
    }

    private fun loadPetData() {
        // If called from edit, populate data from intent
        intent.extras?.let {
            etPetName.setText(it.getString("petName", ""))
            etBreed.setText(it.getString("petBreed", ""))
            etAge.setText(it.getString("petAge", ""))
            etWeight.setText(it.getString("petWeight", ""))
            etColor.setText(it.getString("petColor", ""))
            etDob.setText(it.getString("petDob", ""))
            etMicrochip.setText(it.getString("petMicrochip", ""))
            etOwnerName.setText(it.getString("petOwner", ""))
            etPhone.setText(it.getString("petPhone", ""))
            etNotes.setText(it.getString("petNotes", ""))

            val gender = it.getString("petGender", "")
            if (gender.isNotEmpty()) {
                val genderPosition = (spinnerGender.adapter as ArrayAdapter<String>).getPosition(gender)
                spinnerGender.setSelection(genderPosition)
            }

            val size = it.getString("petSize", "")
            if (size.isNotEmpty()) {
                val sizePosition = (spinnerSize.adapter as ArrayAdapter<String>).getPosition(size)
                spinnerSize.setSelection(sizePosition)
            }

            val imageRes = it.getInt("petImage", -1)
            if (imageRes != -1) {
                ivPetImage.setImageResource(imageRes)
            }
        }
    }

    private fun setupClickListeners() {
        btnBack.setOnClickListener { finish() }

        btnCancel.setOnClickListener { finish() }

        btnSave.setOnClickListener {
            savePetDetails()
        }

        btnChangePhoto.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

        etDob.setOnClickListener { showDatePicker() }
    }

    private fun savePetDetails() {
        val data = Intent().apply {
            putExtra("petName", etPetName.text.toString())
            putExtra("petBreed", etBreed.text.toString())
            putExtra("petAge", etAge.text.toString())
            putExtra("petWeight", etWeight.text.toString())
            putExtra("petColor", etColor.text.toString())
            putExtra("petDob", etDob.text.toString())
            putExtra("petMicrochip", etMicrochip.text.toString())
            putExtra("petOwner", etOwnerName.text.toString())
            putExtra("petPhone", etPhone.text.toString())
            putExtra("petNotes", etNotes.text.toString())
            putExtra("petGender", spinnerGender.selectedItem.toString())
            putExtra("petSize", spinnerSize.selectedItem.toString())
            // For image, you can save URI as string if picked
            selectedImageUri?.let { putExtra("petImageUri", it.toString()) }
        }
        setResult(RESULT_OK, data)
        Toast.makeText(this, "Pet details saved!", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val dateParts = etDob.text.toString().split("-")
        if (dateParts.size == 3) {
            calendar.set(dateParts[0].toInt(), dateParts[1].toInt() - 1, dateParts[2].toInt())
        }

        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val monthStr = (month + 1).toString().padStart(2, '0')
                val dayStr = dayOfMonth.toString().padStart(2, '0')
                etDob.setText("$year-$monthStr-$dayStr")
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }
}
