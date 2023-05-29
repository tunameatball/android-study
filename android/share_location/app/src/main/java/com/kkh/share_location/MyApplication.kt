package com.kkh.share_location

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.NATIVE_APP_KEY)
    }
}