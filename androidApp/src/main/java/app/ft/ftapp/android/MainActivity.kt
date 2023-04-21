package app.ft.ftapp.android

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import app.ft.ftapp.android.presentation.MainComposable
import com.hse.core.BaseApplication
import com.hse.core.ui.BaseActivity

class MainActivity : BaseActivity() {

    private val REQUEST_LOGIN = 5

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        Log.d("TAG_OF_F", "onActivityResult: $requestCode $resultCode")
//        if (resultCode == Activity.RESULT_OK) {
//            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (resultCode == Activity.RESULT_OK) {
        Log.d("TAG_OF_F", "onActivityResult: $requestCode $resultCode")
            Toast.makeText(this, "success $resultCode", Toast.LENGTH_SHORT).show()
        super.onActivityResult(requestCode, resultCode, data)
//        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        BaseApplication.appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContent {
            MainComposable()
//            Button({AuthHelper.login(this, REQUEST_LOGIN)}) {
//                Text("Adsadб ")
//            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
            val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            view.updatePadding(bottom = bottom)
            insets
        }
    }
}
