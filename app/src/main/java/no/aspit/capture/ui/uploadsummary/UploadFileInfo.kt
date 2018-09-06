package no.aspit.capture.ui.uploadsummary

enum class UploadFileType(val type: Int) {
    IMAGE(1),
    VIDEO(2),
    DOCUMENT(3)
}

enum class UploadStatus(val status: Int) {
    UPLOADING(2),
    COMPLETED(1),
    FAILED(0),
    QUEUE(3)
}