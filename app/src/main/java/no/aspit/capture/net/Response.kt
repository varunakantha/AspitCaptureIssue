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
        return authToken.isNotEmpty() && authToken.isNotBlank()
    }
}

@JsonClass(generateAdapter = true)
data class Patient(
        @Json(name = "nin") val nin: String,
        @Json(name = "firstName") val firstName: String,
        @Json(name = "middleName") val middleName: String?,
        @Json(name = "lastName") val lastName: String,
        @Json(name = "sex") val sex: String,
        @Json(name = "dateOfBirth") val dob: String
)
