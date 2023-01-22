package app.ft.ftapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.ft.ftapp.android.presentation.announcement.AnnounceCard
import app.ft.ftapp.android.presentation.announcement.AnnounceScreen
import app.ft.ftapp.android.presentation.creation.AnnounceCreationScreen
import app.ft.ftapp.android.ui.theme.MyApplicationTheme
import app.ft.ftapp.android.ui.theme.appBackground

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = appBackground//MaterialTheme.colors.background
                ) {
                    AnnounceCreationScreen()
//                    GreetingView(Greeting().greet())
                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
