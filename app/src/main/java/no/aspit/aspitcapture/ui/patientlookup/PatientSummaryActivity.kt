package no.aspit.aspitcapture.ui.patientlookup

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_patient_summary.*
import no.aspit.aspitcapture.R
import no.aspit.aspitcapture.common.BaseActivity
import no.aspit.aspitcapture.common.CustomActionBar
import no.aspit.aspitcapture.ui.uploadsummary.UploadsActivity

class PatientSummaryActivity : BaseActivity(),CustomActionBar.CustomActionBarInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_summary)

        patient_summary_confirm.setOnClickListener { v ->
            startActivity(Intent(this@PatientSummaryActivity, UploadsActivity::class.java))
        }
    }

    override fun onClose() {
        finish()
    }
}