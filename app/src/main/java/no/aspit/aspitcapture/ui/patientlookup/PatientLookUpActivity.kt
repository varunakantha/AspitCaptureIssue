package no.aspit.aspitcapture.ui.patientlookup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_patient_look_up.*
import no.aspit.aspitcapture.R
import no.aspit.aspitcapture.common.BaseActivity
import no.aspit.aspitcapture.common.CustomActionBar
import no.aspit.aspitcapture.ui.startscreen.RegistrationActivity

class PatientLookUpActivity : BaseActivity(),CustomActionBar.ActionBarListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_look_up)

        patientSSN.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.length == 5) {
                    startActivity(Intent(this@PatientLookUpActivity, PatientSummaryActivity::class.java))
                    patientSSN.text = null
                }
            }
        })
    }

    override fun onClose() {
        startActivity(Intent(this@PatientLookUpActivity,RegistrationActivity::class.java))
        finish()
    }
}
