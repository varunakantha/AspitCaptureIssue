package no.aspit.capture.ui.patientlookup

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Patient(
        @Json(name = "nin") val nin: String,
        @Json(name = "firstName") val firstName: String,
        @Json(name = "middleName") val middleName: String,
        @Json(name = "lastName") val lastName: String,
        @Json(name = "sex") val sex: String,
        @Json(name = "dateOfBirth") val dob: String
)