package no.aspit.aspitcapture.ui.uploadsummary

enum class UploadFileType(val type: Int) {
    IMAGE(1),
    VIDEO(2),
    DOCUMENT(3)
}

enum class UploadStatus(val status: Int) {
    UPLOADING(0),
    COMPLETED(1)
}