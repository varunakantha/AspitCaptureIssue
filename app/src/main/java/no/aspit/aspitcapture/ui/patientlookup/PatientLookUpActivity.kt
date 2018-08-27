package no.aspit.aspitcapture.ui.patientlookup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_patient_look_up.*
import no.aspit.aspitcapture.R
import no.aspit.aspitcapture.common.BaseActivity
import no.aspit.aspitcapture.common.CustomActionBar

class PatientLookUpActivity : BaseActivity(),CustomActionBar.CustomActionBarInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_look_up)

        patient_ssn.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.length == 5) {
                    val intent = Intent(this@PatientLookUpActivity, PatientSummaryActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }

    override fun onClose() {
        finish()
    }
}
