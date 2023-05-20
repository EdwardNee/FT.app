package app.ft.ftapp.utils

import android.util.Base64
import app.ft.ftapp.domain.models.JwtAuthData
import org.json.JSONObject

/**
 * Helper class that parses jwt token of hse authorization.
 */
actual class CustomJwtParser {
//    private val jwtParserBuilder: JwtParser? = Jwts.parserBuilder()
//        .setSigningKeyResolver(res)
//        .setSigningKey(Keys.secretKeyFor(SignatureAlgorithm.RS256))
//        .build()


    /**
     * Splits the JWT token and returns payload data.
     */
    private fun getPayload(jwtString: String): String {
        val jwtParts = jwtString.split(".");

        val encodedPayload = jwtParts[1]
        val decodedPayload = String(Base64.decode(encodedPayload, Base64.URL_SAFE))

        return decodedPayload
    }

    /**
     * Parses the claims and returns the [JwtAuthData].
     */
    private fun getClaimedData(decodedPayload: String): JwtAuthData {
        val payloadJson = JSONObject(decodedPayload)

        val commonName: String? = payloadJson.getString("commonname") //Ф И О
        val givenName: String? = payloadJson.getString("given_name")
        val familyName: String? = payloadJson.getString("family_name")
        val email: String? = payloadJson.getString("email")
        val expirationDate: Long = payloadJson.getString("exp").toLong()
        val issuedAt: Long = payloadJson.getString("iat").toLong()
        val notValidBefore: Long = payloadJson.getString("nbf").toLong()

        return JwtAuthData(
            commonName = commonName,
            givenName = givenName,
            familyName = familyName,
            email = email,
            expirationDate = expirationDate,
            issuedAt = issuedAt,
            notValidBefore = notValidBefore
        )
    }

    /**
     * Parses [jwtString] and returns [JwtAuthData].
     */
    actual fun parse(jwtString: String): JwtAuthData {
        val decodedPayload = getPayload(jwtString)
        val parsedData = getClaimedData(decodedPayload)

        return parsedData
    }
}