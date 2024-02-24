package com.kkh.android_firebase

import android.os.Bundle
import android.view.View
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.setCustomKeys
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
                val crash = FirebaseCrashlytics.getInstance()
                crash.log("TEST CRASH 01")

                crash.setCustomKeys {
                    key("name", "kwanghee")
                    key("test", true)
                }
                throw Exception("Test Crash~~")
            }
        }
    }
}