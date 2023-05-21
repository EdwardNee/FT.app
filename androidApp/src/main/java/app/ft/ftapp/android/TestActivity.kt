package app.ft.ftapp.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import app.ft.ftapp.android.presentation.MainComposable
import app.ft.ftapp.android.ui.navigation.AppDestination
import app.ft.ftapp.yandex_mapkit
import com.yandex.mapkit.MapKitFactory

class TestActivity : AppCompatActivity() {
    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.setApiKey(yandex_mapkit)
//        DIFactory.locationListener = this
        MapKitFactory.initialize(this)
        super.onCreate(savedInstanceState)
        setContent {
            MainComposable(startDestination = AppDestination.AppBars)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
            val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            view.updatePadding(bottom = bottom)
            insets
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun KeyBoardHide() {
        val keyboardController = LocalSoftwareKeyboardController.current
        keyboardController?.hide()
    }
}