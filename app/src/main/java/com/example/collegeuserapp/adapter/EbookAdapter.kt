package com.example.collegeuserapp.adapter


import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeuserapp.activity.PdfViewerActivity
import com.example.collegeuserapp.databinding.PdfListBinding
import com.example.collegeuserapp.model.AddEbookModel




class EbookAdapter (val context: Context, val list: ArrayList<AddEbookModel>)
    :RecyclerView.Adapter<EbookAdapter.EbookViewHolder>(){
    inner class EbookViewHolder(val binding: PdfListBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EbookViewHolder {
        return EbookViewHolder(PdfListBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: EbookViewHolder, position: Int) {

        holder.binding.ebookTitle.text=list[position].ebookTitle

        holder.binding.ebookDownload.setOnClickListener {

         val request = DownloadManager.Request(Uri.parse(list[position].ebookUrl))
             .setTitle(list[position].ebookTitle)
             .setDescription("Downloading...")
             .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
             .setAllowedOverMetered(true)

           val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)

        }

        holder.itemView.setOnClickListener {
val intent = Intent(context,PdfViewerActivity::class.java)
            intent.putExtra("title",list[position].ebookTitle)
            intent.putExtra("url",list[position].ebookUrl)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return list.size
    }
}