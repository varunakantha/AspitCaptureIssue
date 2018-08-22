package no.aspit.aspitcapture.extention

import android.app.Activity
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

inline fun Activity.snack(message: String, duration: Int = Toast.LENGTH_SHORT) {
    this.findViewById<View>(android.R.id.content)?.let {
        Snackbar.make(it, message, duration).show()
    }
}

inline fun Activity.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}