package no.aspit.aspitcapture.ui.imagecapture

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.captured_image_add_details.*
import no.aspit.aspitcapture.R
import no.aspit.aspitcapture.common.BaseActivity

class CapturedImageDetailsAddActivity : BaseActivity() {

    lateinit var imagePath: String

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

        Picasso.get().load(imagePath).into(capturedImage);

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
//        setImage()
    }

    fun setImage() {
        val targetWidth: Int = capturedImage.width
        val targetHeight: Int = capturedImage.height
        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(imagePath, bitmapOptions)

        val photoWidth: Int = bitmapOptions.outWidth
        val photoHeight: Int = bitmapOptions.outHeight
        val scaleFactor = Math.min(photoWidth / targetWidth, photoHeight / targetHeight)

        bitmapOptions.inJustDecodeBounds = false
        bitmapOptions.inSampleSize = scaleFactor
        bitmapOptions.inPurgeable = true

        val bitmap: Bitmap = BitmapFactory.decodeFile(imagePath, bitmapOptions)
        capturedImage.setImageBitmap(bitmap)
    }

}