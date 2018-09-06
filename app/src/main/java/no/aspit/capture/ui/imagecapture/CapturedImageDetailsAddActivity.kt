package no.aspit.capture.ui.imagecapture

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import com.squareup.picasso.Picasso
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.captured_image_add_details.*
import kotlinx.android.synthetic.main.custom_action_bar.view.*
import no.aspit.capture.R
import no.aspit.capture.common.*
import no.aspit.capture.common.Constant.Companion.EDITABLE_MODE
import no.aspit.capture.common.Constant.Companion.IMAGE_DATA_OBJECT
import no.aspit.capture.common.Constant.Companion.IMAGE_PATH
import no.aspit.capture.common.Constant.Companion.TYPE_IMAGE
import no.aspit.capture.extention.readString
import no.aspit.capture.net.Upload
import no.aspit.capture.ui.uploadsummary.UploadDataModel
import no.aspit.capture.ui.uploadsummary.UploadFileType
import java.io.File
import java.util.*

lateinit var imagePath: String
lateinit var uploadDataObject: UploadDataModel
lateinit var file: File

lateinit var capturedImage: ImageView
lateinit var imageTitle: TextView
lateinit var imageTitleEdit: EditText
lateinit var imageCommentEdit: EditText
lateinit var confirmButton: Button
lateinit var lock: ImageView
var editableMode: Int = 0
var patientNin = ""

class CapturedImageDetailsAddActivity : BaseActivity(), CustomActionBar.ActionBarListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.captured_image_add_details)

        fillPatientData()

        capturedImage = capturedImageView
        imageTitle = imageFileNameTextView
        imageTitleEdit = imageTitleEditText
        imageCommentEdit = imageCommentEditText
        confirmButton = imageDetailsConfirmButton
        lock = imageViewLockIcon
        editableMode = intent?.getIntExtra(EDITABLE_MODE, 0)!!

        if (editableMode == 0) editableModeOff() else editableModeOn()
    }

    private fun fillPatientData() {
        val patient = JsonParser().toPatient(readString(Constant.KEY_PATIENT_OBJECT))
        actionbarImageDetails.mainTitle.text = patient?.firstName + " " + patient?.lastName
        patientNin = patient?.nin!!
        actionbarImageDetails.subTitle.text = patient?.nin
    }

    private fun editableModeOn() {
        imagePath = intent.getStringExtra(IMAGE_PATH)!!
        file = Compressor(this).compressToFile(File(imagePath!!))
        var fileName: String = file?.name?.toString()!!
        fileName = fileName?.renameFile(UploadFileType.IMAGE)!!
        imageTitle.text = fileName
        file?.let {
            Picasso.get()
                    .load(it.toUri())
                    .into(capturedImage)
        }
        confirmButton.setOnClickListener {
            addToUploadList()
        }
    }

    private fun editableModeOff() {
        uploadDataObject = (intent?.extras?.get("upload_object") as? UploadDataModel)!!
        confirmButton.visibility = View.GONE
        imageTitleEdit.isEnabled = false
        imageTitleEdit.setText(uploadDataObject?.upload.title)
        imageCommentEdit.isEnabled = false
        imageCommentEdit.setText(uploadDataObject?.upload.comment)
        imageTitle.text = uploadDataObject?.file?.name?.toString()?.renameFile(UploadFileType.IMAGE)
        lock.visibility = View.VISIBLE
        file = uploadDataObject?.file!!
        file?.let {
            Picasso.get()
                    .load(it.toUri())
                    .into(capturedImage)
        }
    }

    @SuppressLint("NewApi")
    private fun addToUploadList() {
        val base64: String = Base64.getEncoder().encodeToString(file?.readBytes())
        var upload = Upload(patientNin, base64, TYPE_IMAGE, imageTitleEdit.text.toString(), "", imageCommentEdit.text.toString())
        var uploadObject = UploadDataModel(upload, file.name, 3, 1, file)

        val intent = Intent()
        intent.putExtra(IMAGE_DATA_OBJECT, uploadObject as Parcelable)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onClose() {
        finish()
    }

}

