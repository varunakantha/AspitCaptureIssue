package no.aspit.capture.ui.patientlookup

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_patient_summary.*
import no.aspit.capture.R
import no.aspit.capture.common.BaseActivity
import no.aspit.capture.common.CustomActionBar
import no.aspit.capture.ui.uploadsummary.UploadsActivity

class PatientSummaryActivity : BaseActivity(),CustomActionBar.ActionBarListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_summary)

        patientSummaryConfirm.setOnClickListener { v ->
            startActivity(Intent(this@PatientSummaryActivity, UploadsActivity::class.java))
            finish()
        }
    }

    override fun onClose() {
        finish()
    }
}