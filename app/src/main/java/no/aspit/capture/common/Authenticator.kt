package no.aspit.capture.common

import android.content.Context
import android.content.Intent
import android.net.Uri


class Authenticator(var context: Context) {
    fun redirectToAuthentication() {
        val intentAuthentication = Intent(Intent.ACTION_VIEW, getAuthenticationUri())
        intentAuthentication.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        context.startActivity(intentAuthentication)
    }

    private fun getAuthenticationUri(): Uri? {
        return Uri.parse(Constant.AUTHORIZATION_ENDPOINT)
                .buildUpon()
                .appendQueryParameter("client_id", Constant.CLIENT_ID)
                .appendQueryParameter("client_secret", Constant.CLIENT_SECRET)
                .appendQueryParameter("redirect_uri", Constant.REDIRECT_URI)
                .appendQueryParameter("scope", Constant.AUTHORIZATION_SCOPE)
                .appendQueryParameter("response_type", "code")
                .build()
    }

    fun checkForToken(): Boolean {
        val token = Utils().getAccessToken(context)
        token?.let {
            if (it.isValid()) {
                return true
            }
        }
        return false
    }
}
