package com.example.collegeuserapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.collegeuserapp.model.AddNoticeModel
import com.example.collegeuserapp.databinding.NoticeListBinding


class NoticeListAdapter (val context:Context,val list:ArrayList<AddNoticeModel>)
    :RecyclerView.Adapter<NoticeListAdapter.NoticeListViewHolder>()
    {

        inner class NoticeListViewHolder(val binding : NoticeListBinding)
            : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeListViewHolder {
            return NoticeListViewHolder(NoticeListBinding.inflate(LayoutInflater.from(context),parent,false))
        }

        override fun onBindViewHolder(holder: NoticeListViewHolder, position: Int) {
            holder.binding.NoticeDesc.text=list[position].noticeDesc
            holder.binding.NoticeDate.text=list[position].date
            holder.binding.NoticeTime.text=list[position].time
            holder.binding.NoticeHeading.text=list[position].title
            if(list[position].image.size<1 || (list[position].image.size==1 && list[position].image[0]=="")){

            }
            else{
                holder.binding.NoticeImageRecycler.visibility=View.VISIBLE
                holder.binding.NoticeImageRecycler.adapter=NoticeImageAdapter(context,list[position].image)
            }


        }

        override fun getItemCount(): Int {
            return list.size
        }
    }