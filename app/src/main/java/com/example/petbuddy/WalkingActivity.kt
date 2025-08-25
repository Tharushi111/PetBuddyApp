package com.example.petbuddy

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import java.util.Locale
import kotlin.math.max

class WalkingActivity : AppCompatActivity() {

    // UI Components
    private lateinit var backButton: ImageView
    private lateinit var petBuddy: LinearLayout
    private lateinit var petLuna: LinearLayout
    private lateinit var petCharlie: LinearLayout
    private lateinit var walkMorning: LinearLayout
    private lateinit var walkAfternoon: LinearLayout
    private lateinit var walkEvening: LinearLayout
    private lateinit var walkPotty: LinearLayout
    private lateinit var buttonStartWalk: Button
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
    private var selectedWalkType = "Morning Walk"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_walking)

        // ===== STATUS BAR SETUP =====
        window.statusBarColor = Color.WHITE
        window.decorView.systemUiVisibility = android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        // ============================

        initializeViews()
        setupClickListeners()
        updateWalkingStatus()
    }

    private fun initializeViews() {
        // Back button
        backButton = findViewById(R.id.backButton)

        // Pet selection
        petBuddy = findViewById(R.id.petBuddy)
        petLuna = findViewById(R.id.petLuna)
        petCharlie = findViewById(R.id.petCharlie)

        // Walk type selection
        walkMorning = findViewById(R.id.walkMorning)
        walkAfternoon = findViewById(R.id.walkAfternoon)
        walkEvening = findViewById(R.id.walkEvening)
        walkPotty = findViewById(R.id.walkPotty)

        // Scroll controls
        scrollLeftCard = findViewById(R.id.scrollLeftCard)
        scrollRightCard = findViewById(R.id.scrollRightCard)
        petScroll = findViewById(R.id.petScroll)

        // Action button
        buttonStartWalk = findViewById(R.id.buttonStartWalk)

        // Status views
        statusMorningTime = findViewById(R.id.statusMorningTime)
        statusAfternoonTime = findViewById(R.id.statusAfternoonTime)
        statusEveningTime = findViewById(R.id.statusEveningTime)

        statusMorning = findViewById(R.id.statusMorning)
        statusAfternoon = findViewById(R.id.statusAfternoon)
        statusEvening = findViewById(R.id.statusEvening)
    }

    private fun setupClickListeners() {
        // Back button
        backButton.setOnClickListener { finish() }

        // Pet selection listeners
        petBuddy.setOnClickListener { selectPet("Buddy", petBuddy) }
        petLuna.setOnClickListener { selectPet("Kitty", petLuna) }
        petCharlie.setOnClickListener { selectPet("Charlie", petCharlie) }

        // Walk type selection listeners
        walkMorning.setOnClickListener { selectWalkType("Morning Walk", walkMorning) }
        walkAfternoon.setOnClickListener { selectWalkType("Afternoon Walk", walkAfternoon) }
        walkEvening.setOnClickListener { selectWalkType("Evening Walk", walkEvening) }
        walkPotty.setOnClickListener { selectWalkType("Quick Potty", walkPotty) }

        // Scroll button listeners
        scrollLeftCard.setOnClickListener { scrollPetsLeft() }
        scrollRightCard.setOnClickListener { scrollPetsRight() }

        // Start walk button
        buttonStartWalk.setOnClickListener { startWalk() }

        // Scroll listener for showing/hiding scroll buttons
        petScroll.viewTreeObserver.addOnScrollChangedListener {
            updateScrollButtons()
        }
    }

    private fun selectPet(petName: String, selectedView: LinearLayout) {
        resetPetSelections()
        selectedPet = petName
        selectedView.setBackgroundResource(R.drawable.card_selected_green)
        updateButtonText()
        Toast.makeText(this, "$petName selected", Toast.LENGTH_SHORT).show()
    }

    private fun selectWalkType(walkType: String, selectedView: LinearLayout) {
        resetWalkTypeSelections()
        selectedWalkType = walkType
        selectedView.setBackgroundResource(R.drawable.card_selected_green)
        updateButtonText()
    }

    private fun resetPetSelections() {
        listOf(petBuddy, petLuna, petCharlie)
            .forEach { it.setBackgroundResource(R.drawable.card_unselected) }
    }

    private fun resetWalkTypeSelections() {
        listOf(walkMorning, walkAfternoon, walkEvening, walkPotty)
            .forEach { it.setBackgroundResource(R.drawable.card_unselected) }
    }

    private fun updateButtonText() {
        buttonStartWalk.text = when (selectedWalkType) {
            "Quick Potty" -> "Start Quick Walk"
            "Morning Walk" -> "Start Morning Walk"
            "Afternoon Walk" -> "Start Afternoon Walk"
            "Evening Walk" -> "Start Evening Walk"
            else -> "Start Walk"
        }
    }

    private fun startWalk() {
        val message = "Starting ${selectedWalkType.lowercase(Locale.getDefault())} with $selectedPet!"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        markWalkAsCompleted(selectedWalkType)
    }

    private fun markWalkAsCompleted(walkType: String) {
        when (walkType) {
            "Morning Walk" -> {
                statusMorning.setImageResource(R.drawable.ic_check_green)
                statusMorningTime.text = "Completed at 8:30 AM • 25 min"
            }
            "Afternoon Walk" -> {
                statusAfternoon.setImageResource(R.drawable.ic_check_green)
                statusAfternoonTime.text = "Completed at 3:15 PM • 30 min"
            }
            "Evening Walk" -> {
                statusEvening.setImageResource(R.drawable.ic_check_green)
                statusEveningTime.text = "Completed at 7:20 PM • 35 min"
            }
        }
    }

    private fun updateWalkingStatus() {
        // Example statuses
        statusMorning.setImageResource(R.drawable.ic_check_green)
        statusMorningTime.text = "Completed at 8:30 AM • 25 min"

        statusAfternoon.setImageResource(R.drawable.ic_alarm)
        statusAfternoonTime.text = "Due at 3:00 PM"

        statusEvening.setImageResource(R.drawable.ic_alarm)
        statusEveningTime.text = "Due at 7:00 PM"
    }

    private fun scrollPetsLeft() {
        val scrollX = petScroll.scrollX
        val itemWidth = 136 // 120dp + 16dp margin
        petScroll.smoothScrollTo(max(0, scrollX - itemWidth), 0)
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

    override fun onResume() {
        super.onResume()
        updateWalkingStatus()
        updateScrollButtons()
    }
}
