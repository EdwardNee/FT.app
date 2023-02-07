package app.ft.ftapp.android

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.ft.ftapp.android.presentation.announce_details.AnnouncementDetails
import app.ft.ftapp.android.presentation.groupchat.GroupChat
import app.ft.ftapp.android.ui.theme.MyApplicationTheme
import app.ft.ftapp.android.ui.theme.appBackground

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = appBackground//MaterialTheme.colors.background
                ) {

//                    Call Window.setDecorFitsSystemWindows(boolean) with false
//                    and install an View.OnApplyWindowInsetsListener on your root content view
//                              that fits insets of type WindowInsets.Type.ime().

//                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//                    window.setDecorFitsSystemWindows(false)
//                    AnnouncementDetails()
                    GroupChat()
//                    ChatMessageComponent()
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
