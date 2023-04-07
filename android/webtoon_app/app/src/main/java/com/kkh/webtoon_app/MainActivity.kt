package com.kkh.webtoon_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView = findViewById<WebView>(R.id.web_view)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true

        webView.loadUrl("https://www.google.com")
    }
}