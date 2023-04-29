package com.kkh.news_app

import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kkh.news_app.databinding.ActivityWebviewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }

        val url = intent.getStringExtra("url")
        if (url.isNullOrEmpty()) {
            Toast.makeText(this, "잘못된 URL 입니다", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            binding.webView.loadUrl(url)
        }
    }
}