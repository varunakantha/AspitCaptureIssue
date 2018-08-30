package no.aspit.aspitcapture.ui.startscreen

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import no.aspit.aspitcapture.R
import no.aspit.aspitcapture.common.BaseActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val pinCode = getSharedPref()?.getString(PIN_CODE,null)

        object : CountDownTimer(2000,1000){
            override fun onFinish() =
                    if (pinCode == null) {
                        startActivity(Intent(this@SplashActivity, RegistrationActivity::class.java))
                        finish()
                    }
                    else {
                        startActivity(Intent(this@SplashActivity,LoginActivity::class.java))
                        finish()
                    }

            override fun onTick(millisUntilFinished: Long) {
            }
        }.start()
    }
}
