package com.kkh.test_animation

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.postDelayed
import androidx.core.util.Pair
import com.kkh.test_animation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(window) {
            setFlags(
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)
        }

        binding.centerLogo.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            Log.d("transitionName", binding.centerLogo.transitionName)
            val options = ActivityOptions.makeSceneTransitionAnimation(this, binding.centerLogo, binding.centerLogo.transitionName)

            startActivity(intent, options.toBundle())
        }


    }
}