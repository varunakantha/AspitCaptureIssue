package no.aspit.aspitcapture.ui.uploadsummary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import no.aspit.aspitcapture.R


class UploadItemAdapter(var list: List<UploadDataModel>, private val itemClickListener: (UploadDataModel) -> Unit) : RecyclerView.Adapter<UploadItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadItemAdapter.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.single_upload_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UploadItemAdapter.ViewHolder, position: Int) {
        holder.bind(list[position], itemClickListener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var fileName = view.findViewById<TextView>(R.id.textViewFileName)!!
        var typeOfFile = view.findViewById<ImageView>(R.id.imageViewFileType)!!
        var fileUploadStatus = view.findViewById<ImageView>(R.id.imageViewUploadStatus)!!
        var thumbNailImageView = view.findViewById<ImageView>(R.id.ImageViewSummaryItemThumbNail)!!

        fun bind(model: UploadDataModel, listener: (UploadDataModel) -> Unit) {
            fileName.text = model.name

            var fileType = model.fileType
            when (fileType) {
                UploadFileType.IMAGE.type -> {
                    typeOfFile.setImageResource(R.drawable.photo_camera_gray)
                    model.file?.let {
                        Picasso.get()
                                .load(it.toUri())
                                .resize(80, 80)
                                .into(thumbNailImageView)
                    }
                }
                UploadFileType.VIDEO.type -> {
                    typeOfFile.setImageResource(R.drawable.video_camera_gray)
                }
                UploadFileType.DOCUMENT.type -> {
                    typeOfFile.setImageResource(R.drawable.document_pdf_gray)
                }
            }
            var status = model.status
            when (status) {
                UploadStatus.UPLOADING.status -> {
                    fileUploadStatus.setImageResource(R.drawable.uploading_orange)
                }
                UploadStatus.COMPLETED.status -> {
                    fileUploadStatus.setImageResource(R.drawable.upload_completed_green)
                }
            }
            itemView.setOnClickListener { listener(model) }
        }
    }
}