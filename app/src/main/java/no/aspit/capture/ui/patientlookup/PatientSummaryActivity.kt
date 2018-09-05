package no.aspit.capture.ui.patientlookup

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_patient_summary.*
import no.aspit.capture.R
import no.aspit.capture.common.BaseActivity
import no.aspit.capture.common.Constant.Companion.KEY_PATIENT_OBJECT
import no.aspit.capture.common.CustomActionBar
import no.aspit.capture.common.JsonParser
import no.aspit.capture.extention.readString
import no.aspit.capture.ui.uploadsummary.UploadsActivity

class PatientSummaryActivity : BaseActivity(),CustomActionBar.ActionBarListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_summary)

        fillPatientData()

        patientSummaryConfirm.setOnClickListener { v ->
            startActivity(Intent(this@PatientSummaryActivity, UploadsActivity::class.java))
            finish()
        }
    }

    private fun fillPatientData() {
        val patient = JsonParser().toPatient(readString(KEY_PATIENT_OBJECT))
        patient_name.text = patient?.firstName + " " + patient?.lastName
        patient_ssn.text = patient?.nin
    }

    override fun onClose() {
        finish()
    }
}