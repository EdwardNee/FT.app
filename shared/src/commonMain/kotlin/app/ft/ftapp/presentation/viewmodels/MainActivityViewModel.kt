package app.ft.ftapp.presentation.viewmodels

import app.ft.ftapp.domain.models.LatLng
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * MainActivity ViewModel to deal with some permissions and other.
 */
class MainActivityViewModel : ViewModel() {
    private val _currentUserLocation = MutableSharedFlow<LatLng>()
    val userLocation: SharedFlow<LatLng>
        get() = _currentUserLocation.asSharedFlow()

    private var _locationGranted = MutableStateFlow(false)
    val locationGranted: StateFlow<Boolean>
        get() = _locationGranted.asStateFlow()

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
        }
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
}

/**
 * Classes of Activity events.
 */
open class ActivityEvents {
    open class PermissionEvent : ActivityEvents() {
        class GrantPermission(val grantStatus: Boolean) : PermissionEvent()
    }

    class LocationDetect(val location: LatLng) : ActivityEvents()
}