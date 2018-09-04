package no.aspit.capture.common

import android.content.Context
import no.aspit.capture.ui.uploadsummary.UploadFileType
import no.aspit.capture.extention.deleteString
import no.aspit.capture.extention.readString
import no.aspit.capture.extention.saveString
import no.aspit.capture.net.Token

class Utils {
    fun getAccessToken(context: Context): Token? {
        return JsonParser().toToken(context.readString(Constant.KEY_AUTH_TOKEN))
    }

    fun clearAccessToken(context: Context) {
        context.deleteString(Constant.KEY_AUTH_TOKEN)
    }

    fun saveToken(context: Context, token: Token) {
        context.saveString(Constant.KEY_AUTH_TOKEN, JsonParser().toJson(token))
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

fun getCurrentTime() = System.currentTimeMillis()

