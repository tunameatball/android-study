package com.kkh.android_firebase

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
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

        sendAnalyticsLog()
    }

    private fun sendAnalyticsLog() {
        val analytics = FirebaseAnalytics.getInstance(this)
        analytics.logEvent(FirebaseAnalytics.Event.LOGIN) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "Main Activity!")
        }
        Log.d("sendAnalyticsLog", "Send!")
    }

    private fun setClickListener() {
        if (::binding.isInitialized) {
            binding.btnCrash.setOnClickListener(this)
            binding.btnAnalytics.setOnClickListener(this)
            binding.btnNonFatalError.setOnClickListener(this)
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

                crash.setCustomKeys {
                    key("name", "Kim Kwang Hee")
                    key("test", false)
                    key("New Test", true)
                }

//                throw Exception("Test Crash~~")
                throw RuntimeException("Test Runtime Crash")
            }

            R.id.btn_analytics -> {
                sendAnalyticsLog()
                Toast.makeText(this, "Send Log", Toast.LENGTH_SHORT).show()
            }

            R.id.btn_non_fatal_error -> {
                val crash = FirebaseCrashlytics.getInstance()
                try {
                    throw RuntimeException("This is Non Fatal Error")
                } catch (e: Exception) {
                    crash.recordException(e)
                    Toast.makeText(this, "Non Fatal Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}