package no.aspit.capture.extention

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import no.aspit.capture.common.Constant
import no.aspit.capture.ui.uploadsummary.UploadDataModel

inline fun Activity.snack(message: String, duration: Int = Toast.LENGTH_SHORT) {
    this.findViewById<View>(android.R.id.content)?.let {
        Snackbar.make(it, message, duration).show()
    }
}

inline fun Activity.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

@SuppressLint("ApplySharedPref")
inline fun Context.saveString(key: String, value: String) {
    this.getSharedPreferences(Constant.LOCAL_STORAGE_NAME, Context.MODE_PRIVATE).edit()
            .putString(key, value)
            .commit()
}

inline fun Context.saveStringAsync(key: String, value: String) {
    this.getSharedPreferences(Constant.LOCAL_STORAGE_NAME, Context.MODE_PRIVATE).edit()
            .putString(key, value)
            .apply()
}

inline fun Context.readString(key: String, defValue: String = ""): String {
    val string = this.getSharedPreferences(Constant.LOCAL_STORAGE_NAME, Context.MODE_PRIVATE)
            .getString(key, defValue)!!
    return string
}

@SuppressLint("ApplySharedPref")
inline fun Context.deleteString(key: String) {
    this.getSharedPreferences(Constant.LOCAL_STORAGE_NAME, Context.MODE_PRIVATE).edit()
            .putString(key, "")
            .commit()
}

inline fun Context.deleteStringAsync(key: String) {
    this.getSharedPreferences(Constant.LOCAL_STORAGE_NAME, Context.MODE_PRIVATE).edit()
            .putString(key, "")
            .apply()
}

fun Context.showDialog(title: String, message: String, positive: String, negative: String, positiveCallback: () -> Unit) {
    var alertDialogBuilder = AlertDialog.Builder(this)
    alertDialogBuilder.setTitle(title)
            .setMessage(message)
            .setPositiveButton(positive) { _, _ -> positiveCallback() }
            .setNegativeButton(negative) { _, _ -> return@setNegativeButton }
    var dialog = alertDialogBuilder.create()
    dialog.show()
}

fun Context.showDialog(title: String, message: String, positive: String, negative: String, uploadDataModel: UploadDataModel, positiveCallback: (UploadDataModel) -> Unit, negativeCallBack: (UploadDataModel) -> Unit) {
    var alertDialogBuilder = AlertDialog.Builder(this)
    alertDialogBuilder.setTitle(title)
            .setMessage(message)
            .setPositiveButton(positive) { _, _ -> positiveCallback(uploadDataModel) }
            .setNegativeButton(negative) { _, _ -> negativeCallBack(uploadDataModel) }
    var dialog = alertDialogBuilder.create()
    dialog.show()
}