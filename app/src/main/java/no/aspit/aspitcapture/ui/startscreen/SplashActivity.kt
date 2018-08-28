package no.aspit.aspitcapture.ui.startscreen

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import no.aspit.aspitcapture.R

class SplashActivity : AppCompatActivity() {

    var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        prefs = this.getSharedPreferences(RegistrationActivity.PREFS_FILENAME, android.content.Context.MODE_PRIVATE)
        val pinCode = prefs?.getString(RegistrationActivity.PIN_CODE,null)

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
