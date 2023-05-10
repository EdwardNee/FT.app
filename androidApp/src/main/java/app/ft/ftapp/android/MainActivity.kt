package app.ft.ftapp.android

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.work.*
import app.ft.ftapp.android.presentation.MainComposable
import app.ft.ftapp.di.DIFactory
import app.ft.ftapp.domain.models.LatLng
import app.ft.ftapp.presentation.viewmodels.ActivityEvents
import app.ft.ftapp.presentation.viewmodels.MainActivityViewModel
import app.ft.ftapp.utils.OnGetUserLocation
import app.ft.ftapp.yandex_mapkit
import com.google.android.gms.location.LocationServices
import com.hse.core.BaseApplication
import com.hse.core.ui.BaseActivity
import com.yandex.mapkit.MapKitFactory
import org.kodein.di.instance


class MainActivity : BaseActivity(), OnGetUserLocation {

    private val kodein = DIFactory.di
    private val viewModel: MainActivityViewModel by kodein.instance(tag = "mainact_vm")
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

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        BaseApplication.appComponent.inject(this)
        MapKitFactory.setApiKey(yandex_mapkit)
        DIFactory.locationListener = this
        MapKitFactory.initialize(this)
        super.onCreate(savedInstanceState)

//        val chatMessageWork = PeriodicWorkRequestBuilder<MessagesWorker>(5, TimeUnit.SECONDS)
//            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
//            .addTag("TAG_WORK")
//           // .setInputData(Data.Builder().putLong("CHAT_ID", viewModel.chatId.value).build())
//            .build()
//
//        val workManager = WorkManager.getInstance(applicationContext)
//            .enqueue(chatMessageWork)

//        val intent = Intent(this, ChatService::class.java)
//        ContextCompat.startForegroundService(this, intent)


        setContent {

            MainComposable()

//            Button({ AuthHelper.login(this, REQUEST_LOGIN)}) {
//                Text("Adsadб ")
//            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
            val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            view.updatePadding(bottom = bottom)
            insets
        }
    }

    override fun getPermissionForLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.onEvent(ActivityEvents.PermissionEvent.GrantPermission(true))
            getUserCurrentLocation()
        }
    }

    private fun getUserCurrentLocation() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        try {
            if (viewModel.locationGranted.value) {
                val currentLocation = fusedLocationProviderClient.lastLocation
                currentLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        viewModel.onEvent(
                            ActivityEvents.LocationDetect(
                                LatLng(
                                    location.latitude,
                                    location.longitude
                                )
                            )
                        )
                    } else {

                    }
                }.addOnFailureListener {
                    println("TAG_OF_LOCATION ${it.message}")
                }
//                currentLocation.addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        val lastKnownLocation = task.result
//
//                        println("TAG_OF_LOCATION $lastKnownLocation")
//                        if (lastKnownLocation != null) {
//                            viewModel.onEvent(
//                                ActivityEvents.LocationDetect(
//                                    LatLng(
//                                        lastKnownLocation.latitude,
//                                        lastKnownLocation.longitude
//                                    )
//                                )
//                            )
//
//                        } else {
//                            //now location detected
//                        }
//                    }
//                }
            }
        } catch (e: SecurityException) {

        }
    }
}
