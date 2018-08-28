package no.aspit.aspitcapture.ui.imagecapture

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.captured_image_add_details.*
import no.aspit.aspitcapture.R
import no.aspit.aspitcapture.common.BaseActivity
import no.aspit.aspitcapture.common.CustomActionBar
import no.aspit.aspitcapture.ui.uploadsummary.UploadDataModel
import no.aspit.aspitcapture.ui.uploadsummary.UploadsActivity
import java.io.File
import java.io.Serializable

class CapturedImageDetailsAddActivity : BaseActivity(), CustomActionBar.CustomActionBarInterface {

    lateinit var imagePath: String
    lateinit var file: File

    lateinit var capturedImage: ImageView
    lateinit var imageTitle: TextView
    lateinit var imageTitleEdit: EditText
    lateinit var imageCommentEdit: EditText
    lateinit var confirmButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.captured_image_add_details)
        capturedImage = capturedImageView
        imageTitle = imageFileNameTextView
        imageTitleEdit = imageTitleEditText
        imageCommentEdit = imageCommentEditText
        confirmButton = imageDetailsConfirmButton

        imagePath = intent.getStringExtra("image_file_path")
        file = File(imagePath)
        var fileName: String = file?.name?.toString()!!
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

    private fun addToUploadList() {
        var uploadObject = UploadDataModel(file.name, 0, 1, file)
        val intent: Intent = Intent(this, UploadsActivity::class.java)
        intent.putExtra("upload_data_object", uploadObject as Serializable)
        startActivity(intent)
    }

    override fun onClose() {
    }

}