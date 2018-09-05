package no.aspit.capture.ui.startscreen

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_registration.*
import no.aspit.capture.BuildConfig
import no.aspit.capture.R
import no.aspit.capture.common.BaseActivity
import no.aspit.capture.common.Constant
import no.aspit.capture.common.Constant.Companion.KEY_MEDIC_NAME
import no.aspit.capture.common.Constant.Companion.KEY_PIN_CODE
import no.aspit.capture.common.Utils
import no.aspit.capture.extention.readString
import no.aspit.capture.extention.saveString
import no.aspit.capture.extention.toast
import no.aspit.capture.net.Service
import no.aspit.capture.net.Token
import no.aspit.capture.ui.patientlookup.PatientLookUpActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : BaseActivity() {

    lateinit var pinCode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        pinCode = readString(KEY_PIN_CODE)

        if (authenticator.checkForToken()) {
            setRegisterAndLogin(pinCode)
        } else {
            handle(intent)
        }

        registerButton.setOnClickListener { v ->
            val pin = userPinCode.text.toString()
            val confirmPin = confirmPinCode.text.toString()

            if (pin.isNotEmpty() && confirmPin.isNotEmpty()) {
                if (pin == confirmPin) {
                    saveString(KEY_PIN_CODE, pin)
                    startActivity(Intent(this@RegistrationActivity, PatientLookUpActivity::class.java))
                    finish()
                } else {
                    toast(getString(R.string.pin_mismatch), Toast.LENGTH_LONG)
                }
            }
        }

        userPinLogin.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (s.toString() == pinCode) {
                    startActivity(Intent(this@RegistrationActivity, PatientLookUpActivity::class.java))
                    finish()
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        userPinLogin.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (userPinCode.text.toString() != pinCode) {
                    toast(getString(R.string.incorrect_pin), Toast.LENGTH_LONG)
                }
            }
            true
        }
    }

    fun setRegisterAndLogin(pinCode: String) {

        if (intent.getBooleanExtra("FROM_BEGINNING", true) && !pinCode.isEmpty()) { // from splash activity to registration
            loginView.visibility = View.VISIBLE
            registrationView.visibility = View.GONE
        } else {  // user clicked close button in PatientLookupActivity
            loginView.visibility = View.GONE
            registrationView.visibility = View.VISIBLE
        }
    }

    private fun handle(intent: Intent?) {
        intent?.let {
            val data = intent.data
            data?.let {
                if (it.toString().startsWith(BuildConfig.REDIRECT_URI)) {
                    val code = it.getQueryParameter("code")
                    val error = it.getQueryParameter("error")
                    when {
                        code != null -> //get auth token here
                        {
                            showProgress()
                            Service(this@RegistrationActivity).authenticationToken(
                                    code,
                                    Constant.CLIENT_ID,
                                    Constant.REDIRECT_URI,
                                    object : Callback<Token> {
                                        override fun onFailure(call: Call<Token>, t: Throwable) {
                                            hideProgress()
                                        }

                                        override fun onResponse(call: Call<Token>, response: Response<Token>) {
                                            hideProgress()
                                            if (response.isSuccessful) {
                                                response.body()?.let {
                                                    Utils().saveToken(this@RegistrationActivity, it)

                                                    setRegisterAndLogin(pinCode)
                                                    getMedicUser()
                                                }
                                            }
                                        }

                                    })
                        }
                        error != null -> {
                            toast("Authentication error $error")
                        }
                        else -> {
                            toast("Authentication error due to unexpected reason")
                        }
                    }
                }
            }
        }
    }


    fun getMedicUser() {
        showProgress()
        Service(this, Utils().getAccessToken(this)?.authToken!!).getUser(
                object : retrofit2.Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        hideProgress()
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        hideProgress()
                        if (response.isSuccessful) {
                            response.body()?.let {
                                saveString(KEY_MEDIC_NAME, it)
                            }
                        }
                    }
                })
    }

}
