package no.aspit.aspitcapture.ui.imagecapture

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_captured_image_further_options.*
import no.aspit.aspitcapture.R
import no.aspit.aspitcapture.common.BaseActivity
import java.io.File

class CapturedImageFurtherOptionSelectionActivity : BaseActivity() {
    lateinit var imageView: ImageView

    lateinit var imageSend: ImageButton
    lateinit var sendText: TextView
    lateinit var file: File

    lateinit var imagePath: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_captured_image_further_options)

        imageView = imageViewCapturedImage

        imageSend = sendButtonImage
        sendText = sendButtonTextView

        imagePath = intent?.getStringExtra("image_file_path")!!
        file = File(imagePath)
        file?.let {
            Picasso.get()
                    .load(it.toUri())
                    .into(imageView)
        }
        imageSend?.setOnClickListener {
            goToImageDetailsActivity()
        }
        sendText?.setOnClickListener {
            goToImageDetailsActivity()
        }
    }

    private fun goToImageDetailsActivity() {
        val intent = Intent(this, CapturedImageDetailsAddActivity::class.java)
        intent.putExtra("image_file_path", imagePath)
        startActivity(intent)
    }
}