package no.aspit.aspitcapture.net

import com.squareup.moshi.Json


//sample code
data class Token(@Json(name = "token") val token: String)
