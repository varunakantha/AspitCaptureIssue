package no.aspit.aspitcapture.ui.patientlookup

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_patient_look_up.*
import kotlinx.android.synthetic.main.custom_action_bar.view.*
import no.aspit.aspitcapture.R
import no.aspit.aspitcapture.common.CustomActionBar

class PatientLookUpActivity : AppCompatActivity() {
    lateinit var actionBar : CustomActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_look_up)
        actionBar = custom_action_bar
        actionBar.main_title.text = "Harald Scjierton"
        actionBar.sub_title.text = "Physiotherapist"
        actionBar.constraint_layout.setBackgroundColor(Color.TRANSPARENT)

        patient_ssn.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.length == 5)
                    retrieved_patient_ssn.text = "12443324234"
            }
        })
    }
}
