package app.ft.ftapp.android

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import app.ft.ftapp.android.presentation.MainComposable
import app.ft.ftapp.di.DIFactory

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DIFactory.initCtx = this
        setContent {
            MainComposable()
        }
    }
}
