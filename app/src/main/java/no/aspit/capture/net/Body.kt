package no.aspit.capture.net

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Upload(
        @Json(name = "nin") val nin: String,
        @Json(name = "data") val data: String,
        @Json(name = "assetType") val assetType: String?,
        @Json(name = "title") val title: String,
        @Json(name = "note") val note: String,
        @Json(name = "comment") val comment: String
):Parcelable