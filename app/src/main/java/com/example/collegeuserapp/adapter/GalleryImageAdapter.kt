package com.example.collegeuserapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.collegeuserapp.activity.FullImageActivity
import com.example.collegeuserapp.databinding.GalleryImageBinding

class GalleryImageAdapter(val context: Context,val list: ArrayList<String>)
    :RecyclerView.Adapter<GalleryImageAdapter.GalleryImageViewHolder>(){

    inner class GalleryImageViewHolder(val binding: GalleryImageBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryImageViewHolder {
      return GalleryImageViewHolder(GalleryImageBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: GalleryImageViewHolder, position: Int) {
        Glide.with(context).load(list[position]).into(holder.binding.image)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, FullImageActivity::class.java)
            intent.putExtra("image",list[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}