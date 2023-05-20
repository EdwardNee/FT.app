package app.ft.ftapp.android

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.core.app.ActivityCompat
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
import com.hse.auth.AuthHelper
import com.hse.auth.utils.AuthConstants
import com.yandex.mapkit.MapKitFactory
import org.kodein.di.instance


/**
 * Application main activity.
 */
class MainActivity : AppCompatActivity(), OnGetUserLocation {

    private val kodein = DIFactory.di
    private val viewModel: MainActivityViewModel by kodein.instance(tag = "mainact_vm")
    private val REQUEST_LOGIN = 510


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_LOGIN -> {
                val accessToken = data?.getStringExtra(AuthConstants.KEY_ACCESS_TOKEN)
                val refreshToken = data?.getStringExtra(AuthConstants.KEY_REFRESH_TOKEN)
                val dataToken = data?.getStringExtra(AuthConstants.KEY_ACCESS_EXPIRES_IN_MILLIS)
                val datsToken = data?.getStringExtra(AuthConstants.KEY_REFRESH_EXPIRES_IN_MILLIS)

                Log.d(
                    "TAG_OF_F",
                    "onActivityResult: $dataToken refter $refreshToken refter $accessToken"
                )
                viewModel.parseJwt(accessToken)
                viewModel.checkIfExpired(System.currentTimeMillis() / 1000)
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
//        BaseApplication.appComponent.inject(this)
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

//        val serviceIntent = Intent(this, AnnounceService::class.java)
//        startService(serviceIntent)

        println(
            "TAG_OF_ ${
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.SCHEDULE_EXACT_ALARM
                ) == PackageManager.PERMISSION_GRANTED
            }.. ${
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.USE_EXACT_ALARM
                ) == PackageManager.PERMISSION_GRANTED
            } ${
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.RECEIVE_BOOT_COMPLETED
                ) == PackageManager.PERMISSION_GRANTED
            }"
        )

        setContent {
            val isExpired: Boolean by viewModel.isExpired.collectAsState()
            println("isExpired $isExpired")
            if (isExpired) {
                processHseAuth()
            }
            MainComposable()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
            val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            view.updatePadding(bottom = bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()
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

    override fun processHseAuth() {
        AuthHelper.login(this, REQUEST_LOGIN)
    }

    fun onE() {
        val intent = Intent(this, AnnounceRemainderReceiver::class.java)
        val pIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val i2 = Intent(this, MainActivity::class.java)
        val pi2 =
            PendingIntent.getActivity(applicationContext, 0, i2, PendingIntent.FLAG_UPDATE_CURRENT)
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 15000, pIntent)
        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(
                System.currentTimeMillis() + 10000,
                pi2
            ), pIntent
        )
    }

    /**
     * Returns users current location.
     */
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
