package com.example.collegeuserapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.collegeuserapp.Adapter.NoticeListAdapter
import com.example.collegeuserapp.model.AddNoticeModel
import com.example.collegeuserapp.databinding.FragmentNoticeBinding
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NoticeFragment : Fragment() {

    private lateinit var binding: FragmentNoticeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentNoticeBinding.inflate(layoutInflater)
//        (activity as AppCompatActivity).supportActionBar?.title="Notices"
        val notice = ArrayList<AddNoticeModel>()


        Firebase.firestore.collection("Notices")
            .orderBy("timestamp",Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {

                notice.clear()
                for(doc in it){
                    val data = doc.toObject(AddNoticeModel::class.java)
                    notice.add(data)
                }
                binding.noticeListRecycler.adapter= NoticeListAdapter(requireContext(),notice)

            }

        return binding.root
    }

}