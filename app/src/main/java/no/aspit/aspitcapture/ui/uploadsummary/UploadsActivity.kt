package no.aspit.aspitcapture.ui.uploadsummary

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.MenuItem
import androidx.core.content.FileProvider
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import kotlinx.android.synthetic.main.activity_upload_summary.*
import no.aspit.aspitcapture.R
import no.aspit.aspitcapture.common.BaseActivity
import no.aspit.aspitcapture.common.CustomActionBar
import no.aspit.aspitcapture.ui.imagecapture.CapturedImageFurtherOptionSelectionActivity
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class UploadsActivity : BaseActivity(),CustomActionBar.CustomActionBarInterface {

    companion object {
        const val REQUEST_TAKE_PHOTO: Int = 1
    }


    lateinit var bottomNavigationBar: BottomNavigationViewEx
    private lateinit var mCurrentPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_summary)

        bottomNavigationBar = bottomNavigationViewUploadActivity
        bottomNavigationBar.setOnNavigationItemSelectedListener {
            selectedItem(it)
        }
    }

    private fun selectedItem(it: MenuItem): Boolean {
        when (it.itemId) {
            R.id.photo_upload -> {
                it.isChecked = true
                dispatchTakePictureIntent()
            }
        }
        return false
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        takePictureIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        if (takePictureIntent?.resolveActivity(packageManager) != null) {
            val photoFile: File? = createImageFile()
            photoFile?.let {
                val photoUri = FileProvider.getUriForFile(
                        this,
                        "no.aspit.aspitcapture.fileprovider",
                        photoFile
                )
                photoUri?.let {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        storageDir.mkdir()
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )
        mCurrentPhotoPath = image.absolutePath
        return image
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            startImageDetails()
        }
    }

    private fun startImageDetails() {
        val intent = Intent(this, CapturedImageFurtherOptionSelectionActivity::class.java)
        intent.putExtra("image_file_path", mCurrentPhotoPath)
        startActivity(intent)
    }

    override fun onClose() {
    }

}