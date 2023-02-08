package app.ft.ftapp.android.ui.navigation

import app.ft.ftapp.android.ui.ScreenValues

/**
 * sealed class to distinguish screens instances.
 */
sealed class NavScreens(val screen: String) {
    object Auth : NavScreens(ScreenValues.AUTH)
    object Announce : NavScreens(ScreenValues.ANNOUNCE_DETAIL)
    object ListAnnounces : NavScreens(ScreenValues.ANNOUNCES_LIST)
    object Creation : NavScreens(ScreenValues.CREATION)
    object Chatting : NavScreens(ScreenValues.CHATTING)
}
