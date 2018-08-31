package no.aspit.capture.net

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Token(
        @Json(name = "access_token") val authToken: String,
        @Json(name = "id_token") val idToken: String,
        @Json(name = "expires_in") val expiresIn: Int,
        @Json(name = "token_type") val tokenType: String
) {
    fun isValid(): Boolean {
        return authToken.isNotEmpty()!! && authToken.isNotBlank()!!
    }
}
