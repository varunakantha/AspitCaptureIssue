package no.aspit.aspitcapture.common

import android.content.Context
import no.aspit.aspitcapture.extention.deleteString
import no.aspit.aspitcapture.extention.readString
import no.aspit.aspitcapture.extention.saveString
import no.aspit.aspitcapture.net.Token

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