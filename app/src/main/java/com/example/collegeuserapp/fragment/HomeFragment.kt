package com.example.collegeuserapp.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.collegeuserapp.MainActivity
import com.example.collegeuserapp.databinding.FragmentHomeBinding
import com.example.collegeuserapp.model.UploadImageModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {

private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding=FragmentHomeBinding.inflate(layoutInflater)
        (activity as AppCompatActivity).supportActionBar?.title="Home"
//        (activity as MainActivity).supportActionBar?.title = "Home"
        getSliderImage()

        binding.map.setOnClickListener {
            var uri = Uri.parse("geo:0, 0?q=Rajiv Gandhi Institute of Petroleum Technology Jais")
            var intent = Intent(Intent.ACTION_VIEW,uri)
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }
        return binding.root
    }

    private fun getSliderImage() {


        val slideList = ArrayList<SlideModel>()

       Firebase.firestore.collection("Gallery")
           .whereEqualTo("category","Slider")
           .orderBy("timestamp",Query.Direction.DESCENDING)
            .get().addOnSuccessListener {
                slideList.clear()
                for(doc in it){
                    val data = doc.toObject(UploadImageModel::class.java)
                    for(i in data.image){
                        slideList.add(SlideModel(i,"RGIPT", ScaleTypes.CENTER_INSIDE))
                    }
                }
                binding.imageSlider.setImageList(slideList)
            }
           .addOnFailureListener(OnFailureListener {
               Toast.makeText(requireContext(),"Something went wrong!!",Toast.LENGTH_SHORT).show()
           })
    }


}