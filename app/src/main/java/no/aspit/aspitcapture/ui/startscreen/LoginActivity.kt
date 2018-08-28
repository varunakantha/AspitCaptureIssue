package no.aspit.aspitcapture.ui.startscreen

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import no.aspit.aspitcapture.R
import no.aspit.aspitcapture.ui.patientlookup.PatientLookUpActivity

class LoginActivity : AppCompatActivity() {

    var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        prefs = this.getSharedPreferences(RegistrationActivity.PREFS_FILENAME, android.content.Context.MODE_PRIVATE)
        val pinCode = prefs?.getString(RegistrationActivity.PIN_CODE,null)

        userPinCode.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (s.toString() == pinCode) {
                    startActivity(Intent(this@LoginActivity, PatientLookUpActivity::class.java))
                    userPinCode.text = null
                }}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

    }


}
