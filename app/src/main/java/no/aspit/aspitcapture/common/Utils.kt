package no.aspit.aspitcapture.common

import android.content.Context

class Utils {
    fun getAccessToken(context: Context): String? {
        return context.getSharedPreferences("aspit_capture", Context.MODE_PRIVATE)
                .getString("token", "")
    }

    fun clearAccessToken(context: Context) {
        return context.getSharedPreferences("aspit_capture", Context.MODE_PRIVATE)
                .edit().remove("token").apply()
    }
}