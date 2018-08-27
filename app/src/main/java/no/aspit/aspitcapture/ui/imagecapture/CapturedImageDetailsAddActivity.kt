package no.aspit.aspitcapture.ui.imagecapture

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import java.io.File

class CapturedImageDetailsAddActivity : BaseActivity() {

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
        file?.let {
            Picasso.get()
                    .load(it.toUri())
                    .into(capturedImage)
        }
    }
}