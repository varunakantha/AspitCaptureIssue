package no.aspit.aspitcapture.ui.startscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import no.aspit.aspitcapture.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        object : CountDownTimer(2000,1000){
            override fun onFinish() {
                startActivity(Intent(this@SplashActivity,LoginActivity::class.java))
            }

            override fun onTick(millisUntilFinished: Long) {
            }
        }.start()
    }
}
