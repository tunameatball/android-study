package com.kkh.android_firebase

import android.os.Bundle
import android.view.View
import com.kkh.android_firebase.base.BaseActivity
import com.kkh.android_firebase.databinding.ActivityMainBinding

class MainActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClickListener()
    }

    private fun setClickListener() {
        if (::binding.isInitialized) {
            binding.btnCrash.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_crash -> {
                throw Exception("Test Crash~~")
            }
        }
    }
}