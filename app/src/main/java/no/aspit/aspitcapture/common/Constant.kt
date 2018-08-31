package no.aspit.aspitcapture.common

import no.aspit.aspitcapture.BuildConfig

class Constant {
    companion object {
        var AUTHORIZATION_ENDPOINT = BuildConfig.AUTHORIZATION_BASE_URL + "connect/authorize"
        var TOKEN_ENDPOINT = BuildConfig.AUTHORIZATION_BASE_URL + "connect/token"
        val AUTHORIZATION_SCOPE = BuildConfig.AUTHORIZATION_SCOPE
        val CLIENT_ID = BuildConfig.CLIENT_ID
        val CLIENT_SECRET = BuildConfig.CLIENT_SECRET
        val REDIRECT_URI = BuildConfig.REDIRECT_URI


        val LOCAL_STORAGE_NAME = "aspit_capture_local"
        val KEY_PIN_CODE = "key_pin_code"
        val KEY_AUTH_TOKEN = "key_auth_token"
    }
}