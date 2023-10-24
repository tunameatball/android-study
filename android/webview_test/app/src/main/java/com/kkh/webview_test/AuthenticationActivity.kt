package com.kkh.webview_test

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent

class AuthenticationActivity : AppCompatActivity() {

    private val CHROME_PACKAGE_NAME = "com.android.chrome"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        showCustomTab(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("Authentication", intent?.data.toString().orEmpty())
        finish()
    }

    private fun showCustomTab(context: Context) {
        val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
        val customTabsIntent: CustomTabsIntent = builder.build()

        // 패키지가 설치되어 있는 경우 Chrome을 사용하고, 그렇지 않으면 기본 웹 브라우저를 사용합니다.
        val packageName: String? = customTabsIntent.intent.`package`

        if (packageName == null || !packageName.equals(CHROME_PACKAGE_NAME)) {
            customTabsIntent.intent.setPackage(CHROME_PACKAGE_NAME)
        }

        customTabsIntent.launchUrl(this@AuthenticationActivity, Uri.parse("https://auth-sia.aioncloud.com:50443/login?connector_url=aisase-connector://auth"))
    }
}