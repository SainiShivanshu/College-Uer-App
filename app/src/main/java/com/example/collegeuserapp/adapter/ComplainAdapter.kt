package com.example.collegeuserapp.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeuserapp.R
import com.example.collegeuserapp.databinding.ComplainBinding
import com.example.collegeuserapp.databinding.GatePassLayoutBinding
import com.example.collegeuserapp.model.Complain


class ComplainAdapter(val context: Context, val list:ArrayList<Complain>)
    : RecyclerView.Adapter<ComplainAdapter.ComplainViewHolder>(){

    inner class ComplainViewHolder(val binding : GatePassLayoutBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplainViewHolder {
        return ComplainViewHolder(GatePassLayoutBinding.inflate(LayoutInflater.from(context),parent,false))

    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ComplainViewHolder, position: Int) {
        holder.binding.name.text=list[position].name
        holder.binding.rollNo.text=list[position].rollNo
        holder.binding.status.text=list[position].status

        if(list[position].status=="Pending")      holder.binding.indicator.visibility= View.VISIBLE


        if(list[position].status=="In process"){
            holder.binding.indicator.visibility= View.VISIBLE
            holder.binding.indicator.setCardBackgroundColor(R.color.green)
        }

        holder.itemView.setOnClickListener {
            val view = LayoutInflater.from(context).inflate(R.layout.complain,null)
            val binding: ComplainBinding = ComplainBinding.bind(view)

            val dialog  = AlertDialog.Builder(context)
                .setTitle("Description")
                .setView(binding.root)
                .create()
            dialog.show()
            binding.name.text=list[position].name
binding.email.text=list[position].emailId
            binding.MobileNo.text=list[position].rollNo
            binding.rollNo.text=list[position].rollNo
            binding.category.text=list[position].category
            binding.programme.text=list[position].programme
            binding.status.text=list[position].status
            binding.roomNo.text=list[position].roomNo
            binding.description.text=list[position].description

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}