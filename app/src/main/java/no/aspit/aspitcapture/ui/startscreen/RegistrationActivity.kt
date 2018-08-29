package no.aspit.aspitcapture.ui.startscreen

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_registration.*
import no.aspit.aspitcapture.R
import no.aspit.aspitcapture.common.BaseActivity
import no.aspit.aspitcapture.extention.toast
import no.aspit.aspitcapture.ui.patientlookup.PatientLookUpActivity

class RegistrationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)


        registerButton.setOnClickListener { v ->
            val pin = userPinCode.text.toString()
            val confirmPin = confirmPinCode.text.toString()

            if (pin.isNotEmpty() && confirmPin.isNotEmpty()){
                if (pin == confirmPin){
                    val editor = getSharedPref()?.edit()
                    editor?.putString(PIN_CODE, pin)
                    editor?.apply()
                    startActivity(Intent(this@RegistrationActivity,PatientLookUpActivity::class.java))
                    finish()
                }else {
                    toast(getString(R.string.pin_mismatch),Toast.LENGTH_LONG)
                }
            }
        }
    }

}
