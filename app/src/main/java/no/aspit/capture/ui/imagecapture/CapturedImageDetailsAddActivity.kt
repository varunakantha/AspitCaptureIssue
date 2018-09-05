package no.aspit.capture.ui.imagecapture

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import com.squareup.picasso.Picasso
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.captured_image_add_details.*
import no.aspit.capture.R
import no.aspit.capture.common.BaseActivity
import no.aspit.capture.common.CustomActionBar
import no.aspit.capture.common.renameFile
import no.aspit.capture.net.Service
import no.aspit.capture.ui.uploadsummary.UploadDataModel
import no.aspit.capture.ui.uploadsummary.UploadFileType
import no.aspit.capture.ui.uploadsummary.UploadsActivity
import retrofit2.Call
import retrofit2.Callback
import java.io.File
import java.io.FileInputStream
import java.io.Serializable
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

class CapturedImageDetailsAddActivity : BaseActivity(), CustomActionBar.ActionBarListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.captured_image_add_details)
        capturedImage = capturedImageView
        imageTitle = imageFileNameTextView
        imageTitleEdit = imageTitleEditText
        imageCommentEdit = imageCommentEditText
        confirmButton = imageDetailsConfirmButton
        lock = imageViewLockIcon
        editableMode = intent?.getIntExtra("editable", 0)!!

        if (editableMode == 0) editableModeOff() else editableModeOn()
    }

    private fun editableModeOn() {
        imagePath = intent.getStringExtra("image_file_path")!!
        file = Compressor(this).compressToFile(File(imagePath!!))
//        Log.d("On", "${file.length() / 1024}")
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
        imageTitleEdit.setText(uploadDataObject.title)
        imageCommentEdit.isEnabled = false
        imageCommentEdit.setText(uploadDataObject.comment)
        imageTitle.text = uploadDataObject?.file?.name?.toString()?.renameFile(UploadFileType.IMAGE)
        lock.visibility = View.VISIBLE
        file = uploadDataObject?.file!!
//        Log.d("Off", "${file.length() / 1024}")
        file?.let {
            Picasso.get()
                    .load(it.toUri())
                    .into(capturedImage)
        }
    }

    @SuppressLint("NewApi")
    private fun addToUploadList() {

        val byteArray = ByteArray(file.length().toInt())
        val fis = FileInputStream(file)
        val base64 = Base64.getEncoder().encodeToString(file?.readBytes())

        Service(this@CapturedImageDetailsAddActivity).uploadFile(
                "19079800468",
                base64,
                "jpg",
                "Title",
                "note",
                "comment",
                object : Callback<String> {
                    override fun onFailure(call: Call<String>?, t: Throwable?) {
                        Log.d("Response", "failed")
                    }

                    override fun onResponse(call: Call<String>?, response: retrofit2.Response<String>?) {
                        if (response?.code()  == 200) {
                            Log.d("Response", "success")
                        } else {
                            Log.d("Response", "error")
                        }

                    }
                }
        )

        var uploadObject = UploadDataModel(file.name, 0, 1, file, imageTitleEdit.text.toString(), imageCommentEdit.text.toString())
        val intent = Intent(this, UploadsActivity::class.java)
        intent.putExtra("upload_data_object", uploadObject as Serializable)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    override fun onClose() {
        finish()
    }

}

