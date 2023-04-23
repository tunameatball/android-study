package com.kkh.today_notice_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = OkHttpClient()

        val request: Request = Request.Builder()
            .url("http://10.0.2.2:8080")
            .build()

        val callback  = object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("client", e.toString())

            }

            override fun onResponse(call: Call, response: Response) {

                if (response.isSuccessful) {
                    val body = response.body?.string()
                    Log.e("client123", body ?: "")
                } else {

                }
            }
        }

        client.newCall(request).enqueue(callback)


//        Thread {
//            try {
//                val socket = Socket("10.0.2.2", 8080)
//                val printer = PrintWriter(socket.getOutputStream())
//                val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
//
//                printer.println("GET / HTTP/1.1")
//                printer.println("Host: 127.0.0.1:8080")
//                printer.println("User-Agent: android")
//                printer.println("\r\n")
//                printer.flush()
//
//                var input: String? = "-1"
//                while (input != null) {
//                    input = reader.readLine()
//                    Log.e("Read", input)
//                }
//                reader.close()
//                printer.close()
//                socket.close()
//            } catch (e: Exception) {
//                Log.e("Client", e.toString())
//            }
//        }.start()

    }
}