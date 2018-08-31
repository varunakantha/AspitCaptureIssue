package no.aspit.aspitcapture.common

import android.content.Context
import no.aspit.aspitcapture.ui.uploadsummary.UploadFileType

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

fun String.renameFile(type: UploadFileType): String? {

    return when (type) {
        UploadFileType.IMAGE -> {
            return "IMG " + this.subSequence(0, 3).toString() + ".jpg"

        }
        UploadFileType.VIDEO -> {
            return "VID " + this.subSequence(0, 3).toString() + ".mp4"
        }
        UploadFileType.DOCUMENT -> {
            return "PDF " + this.subSequence(0, 3).toString() + ".pdf"
        }
    }
}