package com.example.petbuddy

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import java.text.SimpleDateFormat
import java.util.*

class VetCareActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageView
    private lateinit var btnEmergency: ImageView
    private lateinit var cardPetSelection: CardView
    private lateinit var imgSelectedPet: ImageView
    private lateinit var tvSelectedPetName: TextView
    private lateinit var tvSelectedPetType: TextView

    // Quick Actions
    private lateinit var cardFindVet: CardView
    private lateinit var cardBookAppointment: CardView

    // Appointment Views
    private lateinit var cardNextAppointment: CardView
    private lateinit var tvAppointmentDay: TextView
    private lateinit var tvAppointmentMonth: TextView
    private lateinit var tvAppointmentTitle: TextView
    private lateinit var tvAppointmentVet: TextView
    private lateinit var tvAppointmentTime: TextView

    // Health Records
    private lateinit var cardVaccinations: CardView
    private lateinit var cardMedicalHistory: CardView
    private lateinit var cardPrescriptions: CardView

    // Emergency Contacts
    private lateinit var cardEmergencyVet: CardView
    private lateinit var cardRegularVet: CardView
    private lateinit var btnCallEmergency: ImageView
    private lateinit var btnCallRegular: ImageView

    // Sample data
    private var selectedPetName = "Buddy"
    private var selectedPetType = "Golden Retriever"
    private var emergencyVetPhone = "+94713452233"
    private var regularVetPhone = "+94712245667"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vet_care)

        // ===== STATUS BAR SETUP =====
        window.statusBarColor = Color.WHITE
        window.decorView.systemUiVisibility = android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        // ============================

        initializeViews()
        setupClickListeners()
        loadPetData()
        loadAppointmentData()

        // back button handling with animation
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
        })
    }

    private fun initializeViews() {
        // Header
        btnBack = findViewById(R.id.btn_back)
        btnEmergency = findViewById(R.id.btn_emergency)

        // Pet Selection
        cardPetSelection = findViewById(R.id.card_pet_selection)
        imgSelectedPet = findViewById(R.id.img_selected_pet)
        tvSelectedPetName = findViewById(R.id.tv_selected_pet_name)
        tvSelectedPetType = findViewById(R.id.tv_selected_pet_type)

        // Quick Actions
        cardFindVet = findViewById(R.id.card_find_vet)
        cardBookAppointment = findViewById(R.id.card_book_appointment)

        // Appointments
        cardNextAppointment = findViewById(R.id.card_next_appointment)
        tvAppointmentDay = findViewById(R.id.tv_appointment_day)
        tvAppointmentMonth = findViewById(R.id.tv_appointment_month)
        tvAppointmentTitle = findViewById(R.id.tv_appointment_title)
        tvAppointmentVet = findViewById(R.id.tv_appointment_vet)
        tvAppointmentTime = findViewById(R.id.tv_appointment_time)

        // Health Records
        cardVaccinations = findViewById(R.id.card_vaccinations)
        cardMedicalHistory = findViewById(R.id.card_medical_history)
        cardPrescriptions = findViewById(R.id.card_prescriptions)

        // Emergency Contacts
        cardEmergencyVet = findViewById(R.id.card_emergency_vet)
        cardRegularVet = findViewById(R.id.card_regular_vet)
        btnCallEmergency = findViewById(R.id.btn_call_emergency)
        btnCallRegular = findViewById(R.id.btn_call_regular)
    }

    private fun setupClickListeners() {
        // Header Actions
        btnBack.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        btnEmergency.setOnClickListener {
            showEmergencyDialog()
        }

        // Pet Selection
        cardPetSelection.setOnClickListener {
            Toast.makeText(this, "Select Pet", Toast.LENGTH_SHORT).show()
        }

        // Quick Actions
        cardFindVet.setOnClickListener {
            Toast.makeText(this, "Finding nearby vets...", Toast.LENGTH_SHORT).show()
        }

        cardBookAppointment.setOnClickListener {
            Toast.makeText(this, "Book Appointment", Toast.LENGTH_SHORT).show()
        }

        // Appointment
        cardNextAppointment.setOnClickListener {
            Toast.makeText(this, "View Appointment Details", Toast.LENGTH_SHORT).show()
        }

        // Health Records
        cardVaccinations.setOnClickListener {
            Toast.makeText(this, "Vaccination Records", Toast.LENGTH_SHORT).show()
        }

        cardMedicalHistory.setOnClickListener {
            Toast.makeText(this, "Medical History", Toast.LENGTH_SHORT).show()
        }

        cardPrescriptions.setOnClickListener {
            Toast.makeText(this, "Prescriptions", Toast.LENGTH_SHORT).show()
        }

        // Emergency Contacts
        cardEmergencyVet.setOnClickListener {
            Toast.makeText(this, "Emergency Vet Info", Toast.LENGTH_SHORT).show()
        }

        cardRegularVet.setOnClickListener {
            Toast.makeText(this, "Regular Vet Info", Toast.LENGTH_SHORT).show()
        }

        // Call Buttons
        btnCallEmergency.setOnClickListener {
            makePhoneCall(emergencyVetPhone)
        }

        btnCallRegular.setOnClickListener {
            makePhoneCall(regularVetPhone)
        }
    }

    private fun loadPetData() {
        tvSelectedPetName.text = selectedPetName
        tvSelectedPetType.text = selectedPetType
    }

    private fun loadAppointmentData() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 1) // Next day

        val dayFormat = SimpleDateFormat("dd", Locale.getDefault())
        val monthFormat = SimpleDateFormat("MMM", Locale.getDefault())

        tvAppointmentDay.text = dayFormat.format(calendar.time)
        tvAppointmentMonth.text = monthFormat.format(calendar.time).uppercase()
        tvAppointmentTitle.text = "Routine Checkup"
        tvAppointmentVet.text = "Dr. Sarah Fernando"
        tvAppointmentTime.text = "2:30 PM - 3:00 PM"
    }

    private fun makePhoneCall(phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Cannot make call", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showEmergencyDialog() {
        val emergencyOptions = arrayOf(
            "Call Emergency Vet",
            "Find Nearest Emergency Clinic",
            "Pet First Aid Guide",
            "Cancel"
        )

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Emergency Options")
            .setItems(emergencyOptions) { dialog, which ->
                when (which) {
                    0 -> makePhoneCall(emergencyVetPhone)
                    1 -> Toast.makeText(this, "Finding emergency clinics...", Toast.LENGTH_SHORT).show()
                    2 -> Toast.makeText(this, "Opening first aid guide...", Toast.LENGTH_SHORT).show()
                    3 -> dialog.dismiss()
                }
            }
            .show()
    }

    // Helper function to update pet selection
    fun updateSelectedPet(petName: String, petType: String, petImageResource: Int? = null) {
        selectedPetName = petName
        selectedPetType = petType

        tvSelectedPetName.text = petName
        tvSelectedPetType.text = petType

        petImageResource?.let {
            imgSelectedPet.setImageResource(it)
        }
    }

    private fun hasUpcomingAppointments(): Boolean {
        return true
    }

    private fun toggleAppointmentVisibility() {
        cardNextAppointment.visibility = if (hasUpcomingAppointments()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}
