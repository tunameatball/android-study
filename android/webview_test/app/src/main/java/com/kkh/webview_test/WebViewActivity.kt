package com.kkh.webview_test

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import com.kkh.webview_test.databinding.ActivityWebviewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        login()

//        startActivity(Intent(Settings.ACTION_SECURITY_SETTINGS))

    }

    private fun login() {
        binding.wvLogin.run{
            webViewClient = MyWebView()
            webChromeClient = WebChromeClient()

            loadUrl("https://auth-sia.aioncloud.com:50443/login?connector_url=aisase-connector://auth")
        }

    }
}