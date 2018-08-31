package no.aspit.capture.ui.startscreen

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import no.aspit.capture.R
import no.aspit.capture.common.BaseActivity
import no.aspit.capture.common.Constant.Companion.KEY_PIN_CODE
import no.aspit.capture.extention.readString
import no.aspit.capture.extention.toast
import no.aspit.capture.ui.patientlookup.PatientLookUpActivity

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val pinCode = readString(KEY_PIN_CODE)

        userPinCode.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (s.toString() == pinCode) {
                    startActivity(Intent(this@LoginActivity, PatientLookUpActivity::class.java))
//                    userPinCode.text = null
                    finish()
                }}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        userPinCode.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                if (userPinCode.text.toString() != pinCode) {
                    toast(getString(R.string.incorrect_pin), Toast.LENGTH_LONG)
                }
            }
             true
        }

    }

}
