package no.aspit.aspitcapture.ui.startscreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_registration.*
import no.aspit.aspitcapture.BuildConfig
import no.aspit.aspitcapture.R
import no.aspit.aspitcapture.common.BaseActivity
import no.aspit.aspitcapture.common.Constant
import no.aspit.aspitcapture.common.Constant.Companion.KEY_PIN_CODE
import no.aspit.aspitcapture.common.Utils
import no.aspit.aspitcapture.extention.saveString
import no.aspit.aspitcapture.extention.toast
import no.aspit.aspitcapture.net.Service
import no.aspit.aspitcapture.net.Token
import no.aspit.aspitcapture.ui.patientlookup.PatientLookUpActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_registration)
        handle(intent)

        registerButton.setOnClickListener { v ->
            val pin = userPinCode.text.toString()
            val confirmPin = confirmPinCode.text.toString()

            if (pin.isNotEmpty() && confirmPin.isNotEmpty()) {
                if (pin == confirmPin) {
                    saveString(KEY_PIN_CODE, pin)
                    startActivity(Intent(this@RegistrationActivity, PatientLookUpActivity::class.java))
                    finish()
                }else {
                    toast(getString(R.string.pin_mismatch), Toast.LENGTH_LONG)
                }
            }
        }
    }

    private fun handle(intent: Intent?) {
        intent?.let {
            val data = intent.data
            data?.let {
                Log.d(TAG, "redirect data" + data.toString())
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
                                                    Log.d(TAG, it.toString())
                                                    //here we have to show registration form
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

}
