package no.aspit.aspitcapture.ui.startscreen

import android.annotation.TargetApi
import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import co.infinum.goldfinger.Goldfinger
import no.aspit.aspitcapture.R
import no.aspit.aspitcapture.ui.patientlookup.PatientLookUpActivity

class LoginActivity : AppCompatActivity() {

    lateinit var goldfinger: Goldfinger
    private lateinit var keyguardManager: KeyguardManager
    private val REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P){
            getFingerprintBeforeSDK28()
        }else {
            displayBioMetricDialog()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            startActivity(Intent(this@LoginActivity,PatientLookUpActivity::class.java))
        }
    }

    private fun getFingerprintBeforeSDK28(){
        keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        goldfinger = Goldfinger.Builder(this).build()

        if (goldfinger.hasEnrolledFingerprint()){
            goldfinger.authenticate(object : Goldfinger.Callback() {

                override fun onSuccess(value: String) {
                    startActivity(Intent(this@LoginActivity,PatientLookUpActivity::class.java))
                }

                override fun onError(error: co.infinum.goldfinger.Error?) {
                    Toast.makeText(this@LoginActivity,"Fingerprint error. Please try again or use pin code",Toast.LENGTH_LONG)
                            .show()
                }
            })
        }else {
            val intent = keyguardManager
                    .createConfirmDeviceCredentialIntent("Enter your pass code.", "Please use your pass code.")

            startActivityForResult(intent,REQUEST_CODE)
        }
    }

    @TargetApi(28)
    private fun displayBioMetricDialog(){
        BiometricPrompt.Builder(this).setTitle("Fingerprint authentication")
                .setSubtitle("Authenticate using fingerprint")
                .setDescription("Please put fingerprint on the sensor or enter pin code.")
                .setNegativeButton("cancel",mainExecutor, DialogInterface.OnClickListener { dialog, which ->
                    val intent = keyguardManager
                            .createConfirmDeviceCredentialIntent("Enter your pass code.", "Please use your pass code.")
                    startActivityForResult(intent,REQUEST_CODE)
                })
                .build()
    }
}
