package com.example.petbuddy

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class FeedingActivity : AppCompatActivity() {

    private lateinit var petBuddy: LinearLayout
    private lateinit var petLuna: LinearLayout
    private lateinit var petCharlie: LinearLayout
    private lateinit var mealBreakfast: LinearLayout
    private lateinit var mealLunch: LinearLayout
    private lateinit var mealDinner: LinearLayout
    private lateinit var mealTreat: LinearLayout
    private lateinit var buttonMarkAsFed: Button

    // Feeding status icons
    private lateinit var statusBreakfast: ImageView
    private lateinit var statusLunch: ImageView
    private lateinit var statusDinner: ImageView

    private var selectedPet = "Buddy"
    private var selectedMeal = "Breakfast"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feeding)

        // ===== STATUS BAR SETUP =====
        window.statusBarColor = Color.WHITE
        window.decorView.systemUiVisibility = android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        // ============================

        initViews()
        setupClickListeners()
        updateSelections()
    }

    private fun initViews() {
        // Back button
        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            finish()
        }

        // Pet cards
        petBuddy = findViewById(R.id.petBuddy)
        petLuna = findViewById(R.id.petLuna)
        petCharlie = findViewById(R.id.petCharlie)

        // Meal cards
        mealBreakfast = findViewById(R.id.mealBreakfast)
        mealLunch = findViewById(R.id.mealLunch)
        mealDinner = findViewById(R.id.mealDinner)
        mealTreat = findViewById(R.id.mealTreat)

        // Feeding status icons
        statusBreakfast = findViewById(R.id.statusBreakfast)
        statusLunch = findViewById(R.id.statusLunch)
        statusDinner = findViewById(R.id.statusDinner)

        // Mark as fed button
        buttonMarkAsFed = findViewById(R.id.buttonMarkAsFed)
    }

    private fun setupClickListeners() {
        // Pet selection
        petBuddy.setOnClickListener {
            selectedPet = "Buddy"
            updatePetSelection()
            Toast.makeText(this, "Buddy selected", Toast.LENGTH_SHORT).show()
        }

        petLuna.setOnClickListener {
            selectedPet = "Kitty"
            updatePetSelection()
            Toast.makeText(this, "Kitty selected", Toast.LENGTH_SHORT).show()
        }

        petCharlie.setOnClickListener {
            selectedPet = "Charlie"
            updatePetSelection()
            Toast.makeText(this, "Charlie selected", Toast.LENGTH_SHORT).show()
        }

        // Meal selection
        mealBreakfast.setOnClickListener {
            selectedMeal = "Breakfast"
            updateMealSelection()
            Toast.makeText(this, "Breakfast selected", Toast.LENGTH_SHORT).show()
        }

        mealLunch.setOnClickListener {
            selectedMeal = "Lunch"
            updateMealSelection()
            Toast.makeText(this, "Lunch selected", Toast.LENGTH_SHORT).show()
        }

        mealDinner.setOnClickListener {
            selectedMeal = "Dinner"
            updateMealSelection()
            Toast.makeText(this, "Dinner selected", Toast.LENGTH_SHORT).show()
        }

        mealTreat.setOnClickListener {
            selectedMeal = "Treat"
            updateMealSelection()
            Toast.makeText(this, "Treat selected", Toast.LENGTH_SHORT).show()
        }

        // Mark as fed button
        buttonMarkAsFed.setOnClickListener {
            markAsFed()
        }
    }

    private fun updateSelections() {
        updatePetSelection()
        updateMealSelection()
    }

    private fun updatePetSelection() {
        // Reset all pet cards
        petBuddy.setBackgroundResource(R.drawable.card_unselected)
        petLuna.setBackgroundResource(R.drawable.card_unselected)
        petCharlie.setBackgroundResource(R.drawable.card_unselected)

        // Highlight selected pet
        when (selectedPet) {
            "Buddy" -> petBuddy.setBackgroundResource(R.drawable.card_selected_blue)
            "Kitty" -> petLuna.setBackgroundResource(R.drawable.card_selected_blue)
            "Charlie" -> petCharlie.setBackgroundResource(R.drawable.card_selected_blue)

        }
    }

    private fun updateMealSelection() {
        // Reset all meal cards
        mealBreakfast.setBackgroundResource(R.drawable.card_unselected)
        mealLunch.setBackgroundResource(R.drawable.card_unselected)
        mealDinner.setBackgroundResource(R.drawable.card_unselected)
        mealTreat.setBackgroundResource(R.drawable.card_unselected)

        // Highlight selected meal
        when (selectedMeal) {
            "Breakfast" -> mealBreakfast.setBackgroundResource(R.drawable.card_selected_orange)
            "Lunch" -> mealLunch.setBackgroundResource(R.drawable.card_selected_orange)
            "Dinner" -> mealDinner.setBackgroundResource(R.drawable.card_selected_orange)
            "Treat" -> mealTreat.setBackgroundResource(R.drawable.card_selected_orange)
        }
    }

    private fun markAsFed() {
        val message = "$selectedPet has been fed $selectedMeal!"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

        // Update feeding status
        updateFeedingStatus()
    }

    private fun updateFeedingStatus() {
        when (selectedMeal) {
            "Breakfast" -> {
                statusBreakfast.setImageResource(R.drawable.ic_check_green)
                statusBreakfast.setColorFilter(ContextCompat.getColor(this, R.color.green))
            }
            "Lunch" -> {
                statusLunch.setImageResource(R.drawable.ic_check_green)
                statusLunch.setColorFilter(ContextCompat.getColor(this, R.color.green))
            }
            "Dinner" -> {
                statusDinner.setImageResource(R.drawable.ic_check_green)
                statusDinner.setColorFilter(ContextCompat.getColor(this, R.color.green))
            }
        }
    }
}
