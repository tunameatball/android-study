package com.kkh.webview_test

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.kkh.webview_test.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener(this)

        val data = intent?.data
        Log.d("intent data", data.toString())
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btn_login -> {
                showWebView(view.context)
            }
        }
    }

    private fun showWebView(context: Context) {
//        startActivity(Intent(context, WebViewActivity::class.java))
        startActivity(Intent(context, AuthenticationActivity::class.java))


    }


}