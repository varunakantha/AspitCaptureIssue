package no.aspit.capture.ui.imagecapture

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import com.squareup.picasso.Picasso
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.activity_captured_image_further_options.*
import no.aspit.capture.R
import no.aspit.capture.common.BaseActivity
import no.aspit.capture.common.CustomActionBar
import java.io.File

class CapturedImageFurtherOptionSelectionActivity : BaseActivity(), CustomActionBar.ActionBarListener {

    lateinit var imageView: ImageView

    lateinit var imageSend: ImageButton
    lateinit var sendText: TextView
    lateinit var sendBackground: ConstraintLayout
    lateinit var file: File

    lateinit var imagePath: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_captured_image_further_options)

        imageView = imageViewCapturedImage

        imageSend = sendButtonImage
        sendText = sendButtonTextView
        sendBackground = sendButtonBackground

        imagePath = intent?.getStringExtra("image_file_path")!!
        file = Compressor(this).compressToFile(File(imagePath!!))
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
        sendBackground?.setOnClickListener { goToImageDetailsActivity() }
    }

    private fun goToImageDetailsActivity() {
        val intent = Intent(this, CapturedImageDetailsAddActivity::class.java)
        intent.putExtra("image_file_path", imagePath)
        intent.putExtra("editable", 1)
        startActivity(intent)
    }

    override fun onClose() {
        finish()
    }

}