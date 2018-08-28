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


class UploadItemAdapter(var list: List<UploadDataModel>) : RecyclerView.Adapter<UploadItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadItemAdapter.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.single_upload_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UploadItemAdapter.ViewHolder, position: Int) {
        holder.fileName.text = list.get(position).name

        var fileType = list[position].fileType
        when (fileType) {
            UploadFileType.IMAGE.type -> {
                holder.fileType.setImageResource(R.drawable.photo_camera_gray)
                list[position].file?.let {
                    Picasso.get()
                            .load(it.toUri())
                            .into(holder.thumbNailImageView)
                }
            }
            UploadFileType.VIDEO.type -> {
                holder.fileType.setImageResource(R.drawable.video_camera_gray)
            }
            UploadFileType.DOCUMENT.type -> {
                holder.fileType.setImageResource(R.drawable.document_pdf_gray)
            }
        }
        var status = list[position].status
        when (status) {
            UploadStatus.UPLOADING.status -> {
                holder.fileUploadStatus.setImageResource(R.drawable.uploading_orange)
            }
            UploadStatus.COMPLETED.status -> {
                holder.fileUploadStatus.setImageResource(R.drawable.upload_completed_green)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var fileName = view.findViewById<TextView>(R.id.textViewFileName)
        var fileType = view.findViewById<ImageView>(R.id.imageViewFileType)
        var fileUploadStatus = view.findViewById<ImageView>(R.id.imageViewUploadStatus)
        var thumbNailImageView = view.findViewById<ImageView>(R.id.ImageViewSummaryItemThumbNail)
    }
}