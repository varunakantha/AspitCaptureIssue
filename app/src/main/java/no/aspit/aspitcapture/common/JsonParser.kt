package no.aspit.aspitcapture.common

import com.squareup.moshi.Moshi
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import no.aspit.aspitcapture.net.Token
import no.aspit.aspitcapture.net.TokenJsonAdapter
import java.lang.reflect.ParameterizedType

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
}