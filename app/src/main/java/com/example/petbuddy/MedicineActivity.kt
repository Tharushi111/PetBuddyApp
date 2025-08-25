package com.example.petbuddy

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MedicineActivity : AppCompatActivity() {

    // UI Components
    private lateinit var backButton: ImageView
    private lateinit var petBuddy: LinearLayout
    private lateinit var petLuna: LinearLayout
    private lateinit var petCharlie: LinearLayout
    private lateinit var medicineTablets: LinearLayout
    private lateinit var medicineLiquid: LinearLayout
    private lateinit var medicineInjection: LinearLayout
    private lateinit var medicineSupplements: LinearLayout
    private lateinit var buttonGiveMedicine: Button
    private lateinit var scrollLeftCard: CardView
    private lateinit var scrollRightCard: CardView
    private lateinit var petScroll: HorizontalScrollView

    // Status indicators
    private lateinit var statusMorningTime: TextView
    private lateinit var statusAfternoonTime: TextView
    private lateinit var statusEveningTime: TextView
    private lateinit var statusMorning: ImageView
    private lateinit var statusAfternoon: ImageView
    private lateinit var statusEvening: ImageView

    // Selected states
    private var selectedPet = "Buddy"
    private var selectedMedicineType = "Tablets"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine)

        // ===== STATUS BAR SETUP =====
        window.statusBarColor = Color.WHITE
        window.decorView.systemUiVisibility = android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        // ============================

        initializeViews()
        setupClickListeners()
        updateMedicineStatus()
    }

    private fun initializeViews() {
        // Back button
        backButton = findViewById(R.id.backButton)

        // Pet selection
        petBuddy = findViewById(R.id.petBuddy)
        petLuna = findViewById(R.id.petLuna)
        petCharlie = findViewById(R.id.petCharlie)

        // Medicine type selection
        medicineTablets = findViewById(R.id.medicineTablets)
        medicineLiquid = findViewById(R.id.medicineLiquid)
        medicineInjection = findViewById(R.id.medicineInjection)
        medicineSupplements = findViewById(R.id.medicineSupplements)

        // Scroll controls
        scrollLeftCard = findViewById(R.id.scrollLeftCard)
        scrollRightCard = findViewById(R.id.scrollRightCard)
        petScroll = findViewById(R.id.petScroll)

        // Action button
        buttonGiveMedicine = findViewById(R.id.buttonGiveMedicine)

        // Status views
        statusMorningTime = findViewById(R.id.statusMorningTime)
        statusAfternoonTime = findViewById(R.id.statusAfternoonTime)
        statusEveningTime = findViewById(R.id.statusEveningTime)

        statusMorning = findViewById(R.id.statusMorning)
        statusAfternoon = findViewById(R.id.statusAfternoon)
        statusEvening = findViewById(R.id.statusEvening)
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener { finish() }

        petBuddy.setOnClickListener { selectPet("Buddy", petBuddy) }
        petLuna.setOnClickListener { selectPet("Kitty", petLuna) }
        petCharlie.setOnClickListener { selectPet("Charlie", petCharlie) }

        medicineTablets.setOnClickListener { selectMedicineType("Tablets", medicineTablets) }
        medicineLiquid.setOnClickListener { selectMedicineType("Liquid", medicineLiquid) }
        medicineInjection.setOnClickListener { selectMedicineType("Injection", medicineInjection) }
        medicineSupplements.setOnClickListener { selectMedicineType("Supplements", medicineSupplements) }

        scrollLeftCard.setOnClickListener { scrollPetsLeft() }
        scrollRightCard.setOnClickListener { scrollPetsRight() }

        buttonGiveMedicine.setOnClickListener { giveMedicine() }

        petScroll.viewTreeObserver.addOnScrollChangedListener { updateScrollButtons() }
    }

    private fun selectPet(petName: String, selectedView: LinearLayout) {
        resetPetSelections()
        selectedPet = petName
        selectedView.setBackgroundResource(R.drawable.card_selected_purple)
        updateButtonText()
        Toast.makeText(this, "$petName selected", Toast.LENGTH_SHORT).show()
    }

    private fun selectMedicineType(medicineType: String, selectedView: LinearLayout) {
        resetMedicineTypeSelections()
        selectedMedicineType = medicineType
        selectedView.setBackgroundResource(R.drawable.card_selected_purple)
        updateButtonText()
    }

    private fun resetPetSelections() {
        petBuddy.setBackgroundResource(R.drawable.card_unselected)
        petLuna.setBackgroundResource(R.drawable.card_unselected)
        petCharlie.setBackgroundResource(R.drawable.card_unselected)
    }

    private fun resetMedicineTypeSelections() {
        medicineTablets.setBackgroundResource(R.drawable.card_unselected)
        medicineLiquid.setBackgroundResource(R.drawable.card_unselected)
        medicineInjection.setBackgroundResource(R.drawable.card_unselected)
        medicineSupplements.setBackgroundResource(R.drawable.card_unselected)
    }

    private fun updateButtonText() {
        buttonGiveMedicine.text = when (selectedMedicineType) {
            "Tablets" -> "Give Tablet"
            "Liquid" -> "Give Liquid Medicine"
            "Injection" -> "Give Injection"
            "Supplements" -> "Give Supplement"
            else -> "Give Medicine"
        }
    }

    private fun giveMedicine() {
        val message = "Giving $selectedMedicineType to $selectedPet!"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        markMedicineAsGiven(selectedMedicineType)
    }

    private fun markMedicineAsGiven(medicineType: String) {
        when (medicineType) {
            "Tablets" -> {
                statusMorning.setImageResource(R.drawable.ic_check_green)
                statusMorningTime.text = "Given at 9:15 AM • 1 tablet"
            }
            "Liquid" -> {
                statusAfternoon.setImageResource(R.drawable.ic_check_green)
                statusAfternoonTime.text = "Given at 2:10 PM • 5ml"
            }
            "Supplements" -> {
                statusEvening.setImageResource(R.drawable.ic_check_green)
                statusEveningTime.text = "Given at 7:05 PM • 1 capsule"
            }
        }
        Toast.makeText(this, "$medicineType administered successfully!", Toast.LENGTH_LONG).show()
    }

    private fun updateMedicineStatus() {
        statusMorning.setImageResource(R.drawable.ic_check_green)
        statusMorningTime.text = "Given at 9:00 AM • 1 tablet"

        statusAfternoon.setImageResource(R.drawable.ic_alarm)
        statusAfternoonTime.text = "Due at 2:00 PM • 5ml"

        statusEvening.setImageResource(R.drawable.ic_alarm)
        statusEveningTime.text = "Due at 7:00 PM • 1 capsule"
    }

    private fun scrollPetsLeft() {
        val scrollX = petScroll.scrollX
        val itemWidth = 136
        petScroll.smoothScrollTo(maxOf(0, scrollX - itemWidth), 0)
    }

    private fun scrollPetsRight() {
        val scrollX = petScroll.scrollX
        val itemWidth = 136
        petScroll.smoothScrollTo(scrollX + itemWidth, 0)
    }

    private fun updateScrollButtons() {
        val scrollX = petScroll.scrollX
        val maxScroll = petScroll.getChildAt(0).width - petScroll.width
        scrollLeftCard.visibility = if (scrollX > 0) View.VISIBLE else View.GONE
        scrollRightCard.visibility = if (scrollX < maxScroll) View.VISIBLE else View.GONE
    }
}
