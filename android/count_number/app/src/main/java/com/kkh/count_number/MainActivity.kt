package com.kkh.count_number

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvNumber = findViewById<TextView>(R.id.tv_number)

        findViewById<Button>(R.id.btn_init).setOnClickListener {
            tvNumber.text = "0"
        }

        findViewById<Button>(R.id.btn_plus).setOnClickListener {
            var plusNumber = tvNumber.text.toString().toInt() + 1
            tvNumber.text = plusNumber.toString()
        }
    }
}