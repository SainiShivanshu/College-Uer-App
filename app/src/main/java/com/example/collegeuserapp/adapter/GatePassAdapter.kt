package com.example.collegeuserapp.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeuserapp.R
import com.example.collegeuserapp.databinding.GatePassLayoutBinding
import com.example.collegeuserapp.databinding.LocalGatePassBinding
import com.example.collegeuserapp.model.LocalGatePass


 class GatePassAdapter(val context: Context, val list:ArrayList<LocalGatePass>)
    : RecyclerView.Adapter<GatePassAdapter.GatePassViewHolder>() {

     inner class GatePassViewHolder(val binding: GatePassLayoutBinding) :
         RecyclerView.ViewHolder(binding.root)

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GatePassViewHolder {
         return GatePassViewHolder(
             GatePassLayoutBinding.inflate(
                 LayoutInflater.from(context),
                 parent,
                 false
             )
         )
     }

     override fun onBindViewHolder(holder: GatePassViewHolder, position: Int) {
         holder.binding.name.text = list[position].name
         holder.binding.rollNo.text = list[position].rollNo
         holder.binding.status.text = list[position].status

         if (list[position].status == "Pending") holder.binding.indicator.visibility = View.VISIBLE

         holder.itemView.setOnClickListener {
             val view = LayoutInflater.from(context).inflate(R.layout.local_gate_pass, null)
             val binding: LocalGatePassBinding = LocalGatePassBinding.bind(view)


             val dialog = AlertDialog.Builder(context)
                 .setTitle("Description")
                 .setView(binding.root)
                 .create()
             dialog.show()
             binding.name.text = list[position].name
             binding.MobileNo.text = list[position].rollNo
             binding.rollNo.text = list[position].rollNo
             binding.branch.text = list[position].branch
             binding.programme.text = list[position].programme
             binding.date.text = list[position].date
             binding.TimeOut.text = list[position].timeOut
             binding.place.text = list[position].place
             binding.status.text = list[position].status
             binding.TimeIn.text = list[position].timeIn


         }
     }

     override fun getItemCount(): Int {
         return list.size
     }
 }