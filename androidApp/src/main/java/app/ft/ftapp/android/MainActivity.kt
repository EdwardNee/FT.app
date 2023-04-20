package app.ft.ftapp.android

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import app.ft.ftapp.android.presentation.MainComposable

class MainActivity : ComponentActivity() {

    val REQUEST_LOGIN = 510
//    override fun onNewIntent(intent: Intent?) {
//        AuthHelper.onNewIntent(intent, this, REQUEST_LOGIN)
//        super.onNewIntent(intent)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when (requestCode) {
//            REQUEST_LOGIN -> {
//                if (resultCode != Activity.RESULT_OK || data == null) return
//                val accessToken = data.getStringExtra(AuthConstants.KEY_ACCESS_TOKEN)
//                val refreshToken = data.getStringExtra(AuthConstants.KEY_REFRESH_TOKEN)
////                viewModel.updateLoginState(accessToken, refreshToken)
//            }
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainComposable()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
            val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            view.updatePadding(bottom = bottom)
            insets
        }
    }
}
