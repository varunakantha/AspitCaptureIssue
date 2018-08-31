package no.aspit.aspitcapture.ui.startscreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import no.aspit.aspitcapture.R
import no.aspit.aspitcapture.common.BaseActivity
import no.aspit.aspitcapture.common.Constant
import no.aspit.aspitcapture.common.Constant.Companion.KEY_PIN_CODE
import no.aspit.aspitcapture.common.Utils
import no.aspit.aspitcapture.extention.readString


class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        object : CountDownTimer(2000, 1000) {
            override fun onFinish() {
                if (!checkForToken()) {
                    redirectToAuthentication()
                    finish()
                } else {
                    val intent = Intent(this@SplashActivity, RegistrationActivity::class.java)
                    intent.putExtra("FROM_BEGINNING", true)
                    startActivity(intent)
                    finish()
                }
            }

            override fun onTick(millisUntilFinished: Long) {
            }
        }.start()
    }

    private fun redirectToAuthentication() {
        val intentAuthentication = Intent(Intent.ACTION_VIEW, getAuthenticationUri())
        intentAuthentication.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intentAuthentication)
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

    private fun checkForToken(): Boolean {
        val token = Utils().getAccessToken(this)
        token?.let {
            if (it.isValid()) {
                return true
            }
        }
        return false
    }
}
