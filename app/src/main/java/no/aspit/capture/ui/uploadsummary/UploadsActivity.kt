package no.aspit.capture.ui.uploadsummary

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import kotlinx.android.synthetic.main.activity_upload_summary.*
import net.danlew.android.joda.JodaTimeAndroid
import org.joda.time.DateTime
import no.aspit.capture.R
import no.aspit.capture.common.BaseActivity
import no.aspit.capture.common.CustomActionBar
import no.aspit.capture.common.getCurrentTime
import no.aspit.capture.ui.imagecapture.CapturedImageDetailsAddActivity
import no.aspit.capture.ui.imagecapture.CapturedImageFurtherOptionSelectionActivity
import java.io.File
import java.io.IOException
import java.io.Serializable

class UploadsActivity : BaseActivity(), CustomActionBar.ActionBarListener {

    companion object {
        const val REQUEST_TAKE_PHOTO: Int = 1
        var list: MutableList<UploadDataModel> = arrayListOf()
            private set
        var addedItemsCount: Int = 0
            private set
    }


    lateinit var bottomNavigationBar: BottomNavigationViewEx
    lateinit var recyclerView: RecyclerView
    lateinit var imageTextView: TextView
    lateinit var nothingToHereTextView: TextView
    private lateinit var mCurrentPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_summary)
        JodaTimeAndroid.init(this)

        var uploadDataObject: UploadDataModel? = intent?.extras?.get("upload_data_object") as? UploadDataModel
        uploadDataObject?.let {
            if (!list.contains(it)) list?.add(it)
        }

        bottomNavigationBar = bottomNavigationViewUploadActivity
        imageTextView = nothingToSeeHereImageTextView
        nothingToHereTextView = nothingToSeeHereTextView
        recyclerView = recyclerViewUploadItems
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false) as RecyclerView.LayoutManager?

        if (list?.size != 0) {
            imageTextView.visibility = View.GONE
            nothingToHereTextView.visibility = View.GONE
        }

        bottomNavigationBar.setOnNavigationItemSelectedListener {
            selectedItem(it)
        }
        var adapter = UploadItemAdapter(list) { item: UploadDataModel -> partItemClicked(item) }
        recyclerView.adapter = adapter


    }

    private fun partItemClicked(uploadDataModel: UploadDataModel) {
        var intent = Intent(this, CapturedImageDetailsAddActivity::class.java)
        intent.putExtra("upload_object", uploadDataModel as Serializable)
        intent.putExtra("editable", 0)
        startActivity(intent)
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
                        "no.aspit.capture.fileprovider",
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
        addedItemsCount++
        var imageNumber = String.format("%03d", addedItemsCount)
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        storageDir.mkdir()
        val image = File.createTempFile(
                imageNumber, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )
        image.setLastModified(DateTime(getCurrentTime()).millis)
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
        if (list.size > 0) giveWarning() else clearDataAndGoBack()
    }

    private fun clearDataAndGoBack() {
        list.clear()
        addedItemsCount = 0
        finish()
    }


    private fun giveWarning() {
        var alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Warning")
                .setMessage("Upload not complete")
                .setPositiveButton("Ok") { _, _ -> clearDataAndGoBack() }
                .setNegativeButton("Cancel") { _, _ -> return@setNegativeButton }
        var dialog = alertDialogBuilder.create()
        dialog.show()
    }

    override fun onBackPressed() {
        giveWarning()
    }
}