package no.aspit.aspitcapture.ui.patientlookup

import android.os.Bundle
import no.aspit.aspitcapture.R
import no.aspit.aspitcapture.common.BaseActivity
import no.aspit.aspitcapture.common.CustomActionBar

class PatientSummaryActivity : BaseActivity(),CustomActionBar.CustomActionBarInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_summary)
    }

    override fun onClose() {
        finish()
    }
}