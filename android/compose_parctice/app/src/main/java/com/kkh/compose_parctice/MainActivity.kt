package com.kkh.compose_parctice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kkh.compose_parctice.ui.theme.Compose_parcticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_parcticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyText("Android")
                }
            }
        }
    }
}

@Composable
fun MyText(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )

//    Text(color = Color.Red, text = "Hello $name")

//    Text(color = Color(0xffff9944), text = "Hello $name")

//    Text(color = Color(0xffff9944), text = "Hello $name", fontSize = 30.sp)

//    Text(color = Color(0xffff9944), text = "Hello $name", fontSize = 30.sp, fontWeight = FontWeight.Bold)

//    Text(
//        color = Color(0xffff9944),
//        text = "Hello $name",
//        fontSize = 30.sp,
//        fontWeight = FontWeight.Bold,
//        fontFamily = FontFamily.Cursive
//    )

//    Text(
//        color = Color(0xffff9944),
//        text = "Hello $name",
//        fontSize = 30.sp,
//        fontWeight = FontWeight.Bold,
//        fontFamily = FontFamily.Cursive,
//        letterSpacing = 2.sp
//    )

//    Text(
//        color = Color(0xffff9944),
//        text = "Hello $name \nHello\nHello",
//        fontSize = 30.sp,
//        fontWeight = FontWeight.Bold,
//        fontFamily = FontFamily.Cursive,
//        letterSpacing = 2.sp,
//        maxLines = 2
//    )

//    Text(
//        color = Color(0xffff9944),
//        text = "Hello $name \nHello\nHello",
//        fontSize = 30.sp,
//        fontWeight = FontWeight.Bold,
//        fontFamily = FontFamily.Cursive,
//        letterSpacing = 2.sp,
//        maxLines = 2,
//        textDecoration = TextDecoration.Underline,
//        textAlign = TextAlign.Center,
//    )


    Text(
        modifier = Modifier.width(300.dp),
        color = Color(0xffff9944),
        text = "Hello $name \nHello\nHello",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Cursive,
        letterSpacing = 2.sp,
        maxLines = 2,
        textDecoration = TextDecoration.Underline,
        textAlign = TextAlign.Start,
    )
}

@Composable
fun MyButton(onButtonClicked: () -> Unit) {
//    Button(onClick = onButtonClicked) {
//        Text(text = "Send")
//    }

    Button(
        onClick = onButtonClicked,
//        enabled = false,
        border = BorderStroke(5.dp, Color.Magenta),
        shape = CircleShape,
        contentPadding = PaddingValues(20.dp),
    ) {
        Icon(
            imageVector = Icons.Filled.Send,
            contentDescription = null,
        )
        Spacer(
            modifier = Modifier.size(ButtonDefaults.IconSpacing)
        )
        Text(text = "Send")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Compose_parcticeTheme {
//        MyText("Android")
        MyButton({})
    }
}