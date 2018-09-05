package no.aspit.capture.ui.startscreen

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import no.aspit.capture.R
import no.aspit.capture.common.Authenticator
import no.aspit.capture.common.BaseActivity


class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        object : CountDownTimer(2000, 1000) {
            override fun onFinish() {
                if (!authenticator.checkForToken()) {
                    authenticator.redirectToAuthentication()
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
}
