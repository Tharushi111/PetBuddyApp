package com.example.petbuddy

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import android.widget.ImageButton
import java.util.*

data class Pet(val name: String, val imageRes: Int, val status: String = "Healthy")

class HomeActivity : AppCompatActivity() {

    private lateinit var petsContainer: LinearLayout
    private lateinit var greetingText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //  STATUS BAR SETUP
        window.statusBarColor = Color.WHITE
        window.decorView.systemUiVisibility = android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        //

        initializeViews()
        setupGreeting()
        setupPets()
        setupClickListeners()
    }

    private fun initializeViews() {
        petsContainer = findViewById(R.id.petsContainer)
        greetingText = findViewById(R.id.greetingText)
    }

    private fun setupGreeting() {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val greeting = when (hour) {
            in 5..11 -> "Good Morning!"
            in 12..16 -> "Good Afternoon!"
            in 17..20 -> "Good Evening!"
            else -> "Good Night!"
        }
        greetingText.text = greeting
    }

    private fun setupPets() {
        val pets = listOf(
            Pet("Buddy", R.drawable.pet_dog, "Healthy"),
            Pet("Kitty", R.drawable.pet_cat, "Healthy"),
            Pet("Charlie", R.drawable.pet_rabbit, "Needs attention")
        )

        displayPets(pets)
    }

    private fun displayPets(pets: List<Pet>) {
        val inflater = LayoutInflater.from(this)
        var row: LinearLayout? = null

        pets.forEachIndexed { index, pet ->
            if (index % 2 == 0) {
                row = LinearLayout(this).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply { bottomMargin = dpToPx(8) }
                    orientation = LinearLayout.HORIZONTAL
                }
                petsContainer.addView(row)
            }

            val card = inflater.inflate(R.layout.pet_card_item, row, false) as MaterialCardView
            card.findViewById<TextView>(R.id.petNames).text = pet.name
            card.findViewById<ImageView>(R.id.petImages).setImageResource(pet.imageRes)

            val statusText = card.findViewById<TextView>(R.id.petStatus)
            statusText?.text = pet.status
            statusText?.setTextColor(
                if (pet.status == "Healthy") getColor(R.color.green_primary)
                else getColor(R.color.orange_primary)
            )

            // --- EDIT BUTTON LISTENER ---
            card.findViewById<ImageButton>(R.id.btnEditPet)?.setOnClickListener {
                val intent = Intent(this, PetDetailsActivity::class.java)
                intent.putExtra("petName", pet.name)
                intent.putExtra("petImage", pet.imageRes)
                intent.putExtra("petStatus", pet.status)
                startActivity(intent)
            }

            val layoutParams = card.layoutParams as LinearLayout.LayoutParams
            if (index % 2 == 0) {
                layoutParams.marginEnd = dpToPx(8)
            } else {
                layoutParams.marginStart = dpToPx(8)
            }

            row?.addView(card)
        }

        // Add "Add Pet" card
        val lastRowIndex = petsContainer.childCount - 1
        if (lastRowIndex >= 0) {
            val lastRow = petsContainer.getChildAt(lastRowIndex) as LinearLayout

            if (pets.size % 2 == 1) {
                addPetCardToRow(lastRow, inflater, true)
            } else {
                val newRow = LinearLayout(this).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    orientation = LinearLayout.HORIZONTAL
                }
                petsContainer.addView(newRow)
                addPetCardToRow(newRow, inflater, false)
            }
        }
    }


    private fun addPetCardToRow(row: LinearLayout, inflater: LayoutInflater, isSecondCard: Boolean) {
        val addPetCard = inflater.inflate(R.layout.pet_card_add, row, false) as MaterialCardView
        addPetCard.setOnClickListener {
            val intent = Intent(this, AddPetActivity::class.java)
            startActivity(intent)
        }

        val layoutParams = addPetCard.layoutParams as LinearLayout.LayoutParams
        if (isSecondCard) {
            layoutParams.marginStart = dpToPx(8)
        } else {
            layoutParams.marginEnd = dpToPx(8)
        }

        row.addView(addPetCard)
    }

    private fun setupClickListeners() {
        findViewById<MaterialCardView>(R.id.feedCard)?.setOnClickListener {
            val intent = Intent(this, FeedingActivity::class.java)
            startActivity(intent)
        }

        findViewById<MaterialCardView>(R.id.walkCard)?.setOnClickListener {
            val intent = Intent(this, WalkingActivity::class.java)
            startActivity(intent)
        }

        findViewById<MaterialCardView>(R.id.medicineCard)?.setOnClickListener {
            val intent = Intent(this, MedicineActivity::class.java)
            startActivity(intent)
        }

        findViewById<MaterialCardView>(R.id.vetCareCard)?.setOnClickListener {
            val intent = Intent(this, VetCareActivity::class.java)
            startActivity(intent)
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}
