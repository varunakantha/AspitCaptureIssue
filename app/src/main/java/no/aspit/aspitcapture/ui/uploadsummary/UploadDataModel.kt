package no.aspit.aspitcapture.ui.uploadsummary

import java.io.File
import java.io.Serializable

data class UploadDataModel(val name: String, val status: Int, val fileType: Int, var file: File, var title: String, var comment: String) : Serializable {
}