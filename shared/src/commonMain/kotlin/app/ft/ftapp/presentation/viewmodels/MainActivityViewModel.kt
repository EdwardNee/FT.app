package app.ft.ftapp.presentation.viewmodels

import app.ft.ftapp.di.DIFactory
import app.ft.ftapp.domain.models.LatLng
import app.ft.ftapp.domain.models.RegisterUser
import app.ft.ftapp.domain.models.ServerResult
import app.ft.ftapp.domain.models.SessionState
import app.ft.ftapp.domain.usecase.server.RegisterUserUseCase
import app.ft.ftapp.utils.CustomJwtParser
import app.ft.ftapp.utils.PreferencesHelper
import app.ft.ftapp.utils.TimeUtil
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kodein.di.instance

/**
 * MainActivity ViewModel to deal with some permissions and other.
 */
class MainActivityViewModel : ViewModel() {
    private val kodein = DIFactory.di
    private val jwtParser: CustomJwtParser by kodein.instance()
    private val preferences: PreferencesHelper by kodein.instance()
    private val registerUserUseCase: RegisterUserUseCase by kodein.instance()

    private val _currentUserLocation = MutableSharedFlow<LatLng>()
    val userLocation: SharedFlow<LatLng>
        get() = _currentUserLocation.asSharedFlow()

    private var _locationGranted = MutableStateFlow(false)
    val locationGranted: StateFlow<Boolean>
        get() = _locationGranted.asStateFlow()

    private val _registerState = MutableStateFlow<ModelsState>(ModelsState.Loading)
    val registerState: StateFlow<ModelsState>
        get() = _registerState.asStateFlow()

    val isExpired = MutableStateFlow<Boolean>(false)
    val isAuthed = MutableStateFlow<Boolean>(false)

    val isMapInitialized = MutableStateFlow<Boolean>(false)

    /**
     * Event calls.
     */
    fun onEvent(event: ActivityEvents) {
        when (event) {
            is ActivityEvents.PermissionEvent.GrantPermission -> {
                permissionGrant(event.grantStatus)
            }
            is ActivityEvents.LocationDetect -> {
                userLocationDetect(event.location)
            }

            is ActivityEvents.RegisterUser -> {
                println("isExpired ${preferences.userName} ${preferences.userMail}")
                registerUserCall(preferences.userName ?: "", preferences.userMail ?: "")
            }
        }
    }

    /**
     * Checks whether session token is expired or not.
     */
    fun checkIfExpired(currentTime: Long): Boolean {
        val checked = TimeUtil.matchDates(currentTime, preferences.tokenExpirationDate ?: -1L)

        println("isExpired checkIfExpired ${currentTime} ${preferences.tokenExpirationDate} $checked, ${preferences.tokenExpirationDate}")
        return when (checked) {
            SessionState.SessionExpired, SessionState.SessionInvalid -> {
                isExpired.value = true
                true
            }
            SessionState.SessionValid -> {
                isExpired.value = false
                isAuthed.value = true
                false
            }
        }
    }

    /**
     * Method that parses jwt token.
     */
    fun parseJwt(jwt: String?) {
        val parsedResult = jwtParser.parse(jwt ?: return)
        preferences.tokenExpirationDate = parsedResult.expirationDate
        preferences.userName = "${parsedResult.givenName} ${parsedResult.familyName}"
        preferences.userMail = parsedResult.email
        println("isExpired parseJwt ${parsedResult} ${preferences.tokenExpirationDate}")
    }


    /**
     * Changes permission for location grant.
     */
    private fun permissionGrant(grantStatus: Boolean) {
        _locationGranted.value = grantStatus
    }

    /**
     * Sets the user location [LatLng].
     */
    private fun userLocationDetect(location: LatLng) {
        viewModelScope.launch {
            _currentUserLocation.emit(location)
        }
    }

    /**
     * Calls [RegisterUserUseCase].
     */
    private fun registerUserCall(username: String, email: String) {
        val userToSend = RegisterUser(email, username)

        viewModelScope.launch {
            val result = registerUserUseCase(userToSend)

            when (result) {
                is ServerResult.ResultException -> {
                    _registerState.value =
                        ModelsState.Error(result.throwable.message ?: ERROR_REGISTER)
                }
                is ServerResult.SuccessfulResult -> {
                    _registerState.value = ModelsState.Success(result.model)
                }
                is ServerResult.UnsuccessfulResult -> {
                    _registerState.value = ModelsState.Error(result.error)
                }
            }
        }
    }

    companion object {
        private const val ERROR_REGISTER = "Ошибка системы"
    }
}

/**
 * Classes of Activity events.
 */
open class ActivityEvents {
    open class PermissionEvent : ActivityEvents() {
        class GrantPermission(val grantStatus: Boolean) : PermissionEvent()
    }

    object RegisterUser : ActivityEvents()

    class LocationDetect(val location: LatLng) : ActivityEvents()
}