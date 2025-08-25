package com.example.petbuddy

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity

class LaunchActivity : AppCompatActivity() {

    private lateinit var appLogo: View
    private lateinit var appName: View
    private lateinit var appTagline: View
    private lateinit var loadingDots: Array<View>

    private val splashTimeOut: Long = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        initializeViews()
        startAnimations()

        // Navigate to LoginActivity after splash timeout
        Handler(Looper.getMainLooper()).postDelayed({
            startLoginActivity()
        }, splashTimeOut)
    }

    private fun initializeViews() {
        appLogo = findViewById(R.id.appLogo)
        appName = findViewById(R.id.appName)
        appTagline = findViewById(R.id.appTagline)

        loadingDots = arrayOf(
            findViewById(R.id.dot1),
            findViewById(R.id.dot2),
            findViewById(R.id.dot3)
        )

        // Initial animation states
        appLogo.alpha = 0f
        appLogo.scaleX = 0.5f
        appLogo.scaleY = 0.5f

        appName.alpha = 0f
        appName.translationY = 30f

        appTagline.alpha = 0f
        appTagline.translationY = 20f
    }

    private fun startAnimations() {
        animateLogo()
        animateTexts()
        animateLoadingDots()
    }

    private fun animateLogo() {
        val scaleX = ObjectAnimator.ofFloat(appLogo, "scaleX", 0.5f, 1.1f, 1.0f)
        val scaleY = ObjectAnimator.ofFloat(appLogo, "scaleY", 0.5f, 1.1f, 1.0f)
        val alpha = ObjectAnimator.ofFloat(appLogo, "alpha", 0f, 1f)

        AnimatorSet().apply {
            playTogether(scaleX, scaleY, alpha)
            duration = 1000
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    private fun animateTexts() {
        Handler(Looper.getMainLooper()).postDelayed({
            AnimatorSet().apply {
                playTogether(
                    ObjectAnimator.ofFloat(appName, "alpha", 0f, 1f),
                    ObjectAnimator.ofFloat(appName, "translationY", 30f, 0f)
                )
                duration = 700
                interpolator = AccelerateDecelerateInterpolator()
                start()
            }
        }, 500)

        Handler(Looper.getMainLooper()).postDelayed({
            AnimatorSet().apply {
                playTogether(
                    ObjectAnimator.ofFloat(appTagline, "alpha", 0f, 1f),
                    ObjectAnimator.ofFloat(appTagline, "translationY", 20f, 0f)
                )
                duration = 600
                interpolator = AccelerateDecelerateInterpolator()
                start()
            }
        }, 800)
    }

    private fun animateLoadingDots() {
        Handler(Looper.getMainLooper()).postDelayed({
            loadingDots.forEachIndexed { index, dot ->
                ObjectAnimator.ofFloat(dot, "translationY", 0f, -15f, 0f).apply {
                    duration = 600
                    repeatCount = ValueAnimator.INFINITE
                    repeatMode = ValueAnimator.REVERSE
                    interpolator = AccelerateDecelerateInterpolator()
                    startDelay = (index * 200).toLong()
                    start()
                }
            }
        }, 1000)
    }

    private fun startLoginActivity() {
        val intent = Intent(this, OnboardingActivity1::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}
