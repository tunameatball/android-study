package com.kkh.today_notice_app

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

fun main() {
    Thread {
        val port = 8080
        val server = ServerSocket(port)

        while (true) {
            val socket = server.accept()

            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            val printer = PrintWriter(socket.getOutputStream())

            var input: String? = "-1"

            while(input != null && input != "") {
                input = reader.readLine()
            }
            println("ReadData".plus(input))
            printer.println("HTTP/1.1 200 OK")
            printer.println("Content-Type: text/html")
            printer.println("<h1>Hello World!</h1>")
            printer.println("\r\n")
            printer.flush()
            printer.close()

            reader.close()
            socket.close()
        }
    }.start()
}
