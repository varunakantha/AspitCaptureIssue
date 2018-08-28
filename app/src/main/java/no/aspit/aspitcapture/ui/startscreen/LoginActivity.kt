package no.aspit.aspitcapture.ui.startscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import no.aspit.aspitcapture.R
import no.aspit.aspitcapture.ui.patientlookup.PatientLookUpActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userPinCode.setOnClickListener { v ->
            startActivity(Intent(this@LoginActivity,PatientLookUpActivity::class.java))
        }

    }


}
