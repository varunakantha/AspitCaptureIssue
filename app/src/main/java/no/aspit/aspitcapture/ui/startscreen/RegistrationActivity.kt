package no.aspit.aspitcapture.ui.startscreen

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_registration.*
import no.aspit.aspitcapture.R
import no.aspit.aspitcapture.ui.patientlookup.PatientLookUpActivity

class RegistrationActivity : AppCompatActivity() {

    companion object {
        val PREFS_FILENAME = "AspitPrefFile"
        val PIN_CODE = "AspitPrefFile"
    }
    var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        prefs = this.getSharedPreferences(PREFS_FILENAME, android.content.Context.MODE_PRIVATE)

        registerButton.setOnClickListener { v ->
            val pin = userPinCode.text.toString()
            val confirmPin = confirmPinCode.text.toString()

            if (pin.isNotEmpty() && confirmPin.isNotEmpty()){
                if (pin == confirmPin){
                    val editor = prefs?.edit()
                    editor?.putString(PIN_CODE, pin)
                    editor?.apply()
                    startActivity(Intent(this@RegistrationActivity,PatientLookUpActivity::class.java))
                    finish()
                }else {
                    Toast.makeText(this@RegistrationActivity,"Pin mismatch", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}
