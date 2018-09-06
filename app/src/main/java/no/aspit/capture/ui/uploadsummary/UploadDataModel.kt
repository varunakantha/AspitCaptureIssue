package no.aspit.capture.ui.uploadsummary

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import no.aspit.capture.net.Upload
import java.io.File


@Parcelize
data class UploadDataModel(val upload: Upload, val name: String, var status: Int, val fileType: Int, var file: File) : Parcelable