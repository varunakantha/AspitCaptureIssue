package no.aspit.aspitcapture.ui.uploadsummary

import java.io.Serializable

data class UploadDataModel(val name: String, val status: Int, val fileType: Int) : Serializable {
}