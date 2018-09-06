package no.aspit.capture.ui.uploadsummary

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import kotlinx.android.synthetic.main.activity_upload_summary.*
import kotlinx.android.synthetic.main.custom_action_bar.view.*
import net.danlew.android.joda.JodaTimeAndroid
import no.aspit.capture.R
import no.aspit.capture.common.*
import no.aspit.capture.common.Constant.Companion.EDITABLE_MODE
import no.aspit.capture.common.Constant.Companion.IMAGE_DATA_OBJECT
import no.aspit.capture.common.Constant.Companion.IMAGE_PATH
import no.aspit.capture.extention.readString
import no.aspit.capture.net.Service
import no.aspit.capture.ui.imagecapture.CapturedImageDetailsAddActivity
import no.aspit.capture.ui.imagecapture.CapturedImageFurtherOptionSelectionActivity
import org.joda.time.DateTime
import retrofit2.Call
import retrofit2.Callback
import java.io.File
import java.io.IOException

class UploadsActivity : BaseActivity(), CustomActionBar.ActionBarListener {

    companion object {
        const val REQUEST_TAKE_PHOTO: Int = 1
        const val REQUEST_IMAGE_FURTHER_ACTIONS = 2
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
    private lateinit var context: Context
    private lateinit var uploadObject: UploadDataModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_summary)
        JodaTimeAndroid.init(this)
        context = this
        fillPatientData()
        bottomNavigationBar = bottomNavigationViewUploadActivity
        imageTextView = nothingToSeeHereImageTextView
        nothingToHereTextView = nothingToSeeHereTextView
        recyclerView = recyclerViewUploadItems
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        bottomNavigationBar.setOnNavigationItemSelectedListener {
            selectedItem(it)
        }

        var adapter = UploadItemAdapter(list) { item: UploadDataModel -> partItemClicked(item) }
        recyclerView.adapter = adapter

        setUploadData(intent)
    }

    private fun setUploadData(intent: Intent?) {
        var uploadDataModel: UploadDataModel? = (intent?.extras?.get(IMAGE_DATA_OBJECT) as? UploadDataModel)
        uploadDataModel?.let {
            if (it.status == UploadStatus.QUEUE.status) it.status = UploadStatus.UPLOADING.status
            if (!list.contains(it)) list?.add(it)
            uploadFile(it)
        }
        if (list?.size != 0) {
            imageTextView.visibility = View.GONE
            nothingToHereTextView.visibility = View.GONE
        }
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun fillPatientData() {
        val patient = JsonParser().toPatient(readString(Constant.KEY_PATIENT_OBJECT))
        actionbarUploads.mainTitle.text = patient?.firstName + " " + patient?.lastName
        actionbarUploads.subTitle.text = patient?.nin
    }

    private fun partItemClicked(uploadDataModel: UploadDataModel) {
        var intent = Intent(this, CapturedImageDetailsAddActivity::class.java)
        intent.putExtra("upload_object", uploadDataModel as? Parcelable)
        intent.putExtra(EDITABLE_MODE, 0)
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
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                startImageDetails()
            } else if (requestCode == REQUEST_IMAGE_FURTHER_ACTIONS) {
                setUploadData(intent)
            }
        }
    }

    private fun startImageDetails() {
        val intent = Intent(this, CapturedImageFurtherOptionSelectionActivity::class.java)
        intent.putExtra(IMAGE_PATH, mCurrentPhotoPath)
        startActivityForResult(intent, REQUEST_IMAGE_FURTHER_ACTIONS)
    }

    override fun onClose() {
        if (list.size > 0) giveWarning() else clearDataAndGoBack()
    }

    private fun clearDataAndGoBack() {
        list.clear()
        addedItemsCount = 0
        finish()
    }

    private fun uploadFile(uploadDataModel: UploadDataModel) {
        Service(this@UploadsActivity, Utils().getAccessToken(this)?.authToken!!).uploadFile(
                uploadDataModel?.upload,
                object : Callback<String> {
                    override fun onResponse(call: Call<String>?, response: retrofit2.Response<String>?) {
                        if (response?.isSuccessful!!) {
                            uploadDataModel?.status = UploadStatus.COMPLETED.status
                        } else {
                            uploadDataModel?.status = UploadStatus.FAILED.status
                        }
                        recyclerView.adapter?.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<String>?, t: Throwable?) {
                        uploadDataModel?.status = UploadStatus.FAILED.status
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                }
        )
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