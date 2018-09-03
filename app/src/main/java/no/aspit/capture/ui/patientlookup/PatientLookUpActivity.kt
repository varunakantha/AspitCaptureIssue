package no.aspit.capture.ui.patientlookup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import kotlinx.android.synthetic.main.activity_patient_look_up.*
import kotlinx.android.synthetic.main.custom_action_bar.view.*
import no.aspit.capture.R
import no.aspit.capture.common.BaseActivity
import no.aspit.capture.common.Constant
import no.aspit.capture.common.CustomActionBar
import no.aspit.capture.extention.readString
import no.aspit.capture.ui.startscreen.RegistrationActivity

class PatientLookUpActivity : BaseActivity(), CustomActionBar.ActionBarListener {

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

        // Move visibility to CustomActionBar class
        customActionBar.closeView.visibility = View.GONE
        customActionBar.text_logout.visibility = View.VISIBLE
        customActionBar.text_logout.setOnClickListener { _ ->
            onClose()
        }

        customActionBar.mainTitle.text = readString(Constant.KEY_MEDIC_NAME)
    }

    override fun onClose() {
        val intent = Intent(this@PatientLookUpActivity, RegistrationActivity::class.java)
        intent.putExtra("FROM_BEGINNING", false)
        startActivity(intent)
        finish()
    }

}
