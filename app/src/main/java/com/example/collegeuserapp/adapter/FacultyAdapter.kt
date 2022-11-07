package com.example.collegeuserapp.adapter


import android.content.Context
import android.content.Intent
import android.net.Uri

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.collegeuserapp.databinding.FacultyItemLayoutBinding
import com.example.collegeuserapp.model.AddFacultyModel


class FacultyAdapter(val context:Context,val list:ArrayList<AddFacultyModel>)
    :RecyclerView.Adapter<FacultyAdapter.FacultyViewHolder>(){

    inner class FacultyViewHolder(val binding : FacultyItemLayoutBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacultyViewHolder {
        return FacultyViewHolder(FacultyItemLayoutBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: FacultyViewHolder, position: Int) {

        holder.binding.facultyName.text=list[position].name
        holder.binding.facultyPost.text=list[position].post
        holder.binding.facultyEmail.text=list[position].email
        holder.binding.facultyMobileNo.text=list[position].mobileNo
        Glide.with(context).load(list[position].image).into(holder.binding.facultyImage)

        holder.binding.facultyEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:") // only email apps should handle this
                putExtra(Intent.EXTRA_EMAIL, list[position].email)

            }
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            }
        }

        holder.binding.facultyMobileNo.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:+91"+list[position].mobileNo.toString())
            }
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}