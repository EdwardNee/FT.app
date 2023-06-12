package app.ft.ftapp.android.utils

import android.content.Context
import android.location.Geocoder
import app.ft.ftapp.domain.models.LatLng
import java.io.IOException
import java.util.*


/**
 * Geocodes [LatLng] coordinates to string representable address.
 */
fun Context.getAddress(coordinates: LatLng): String? {
    return try {
        val address =
            Geocoder(this, Locale.getDefault()).getFromLocation(coordinates.lat, coordinates.lon, 1)

        if (address?.isNotEmpty() == true) {
            val line = address[0].getAddressLine(0)
            line.subSequence(0, line.lastIndexOf(", ")).toString()
        } else {
            null
        }
    } catch (e: IOException) {
        println(e.message)
        null
    }
}

/**
 * Geocodes [String] [address] to [LatLng] coordinates.
 */
fun Context.getCoordinates(address: String): LatLng? {
    return try {
        val decodedCoordinates =
            Geocoder(this, Locale.getDefault()).getFromLocationName(address, 100)

        if (decodedCoordinates?.isNotEmpty() == true) {
            LatLng(decodedCoordinates[0].latitude, decodedCoordinates[0].longitude)
        } else {
            null
        }
    } catch (e: IOException) {
        println(e.message)
        null
    }
}