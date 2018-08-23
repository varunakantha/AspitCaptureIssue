package no.aspit.aspitcapture.ui.imagecapture

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.captured_image_add_details.*
import no.aspit.aspitcapture.R
import no.aspit.aspitcapture.common.BaseActivity

class CapturedImageDetailsAddActivity : BaseActivity() {

    lateinit var capturedImage: ImageView
    lateinit var imageTitle: TextView
    lateinit var imageTitleEdit: EditText
    lateinit var imageCommentEdit: EditText
    lateinit var confirmButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.captured_image_add_details)
        capturedImage = captured_imageView
        imageTitle = image_title_textView
        imageTitleEdit = image_title_editText
        imageCommentEdit = image_comment_editText
        confirmButton = image_details_confirm_button
    }

}