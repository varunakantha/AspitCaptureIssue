package no.aspit.capture.common

import com.squareup.moshi.Moshi
import no.aspit.capture.net.Patient
import no.aspit.capture.net.PatientJsonAdapter
import no.aspit.capture.net.Token
import no.aspit.capture.net.TokenJsonAdapter

class JsonParser {

    fun moshi() = Moshi.Builder().build()

    fun toJson(value: Token): String {
        return TokenJsonAdapter(moshi()).toJson(value)
    }

    fun toToken(json: String): Token? {
        if (json.isEmpty()) {
            return null
        }
        return TokenJsonAdapter(moshi()).fromJson(json)!!
    }


    fun patientToJson(value: Patient): String {
        return PatientJsonAdapter(moshi()).toJson(value)
    }

    fun toPatient(json: String): Patient? {
        if (json.isEmpty()) {
            return null
        }
        return PatientJsonAdapter(moshi()).fromJson(json)!!
    }

}