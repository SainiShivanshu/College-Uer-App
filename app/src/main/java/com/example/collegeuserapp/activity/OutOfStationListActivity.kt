package com.example.collegeuserapp.activity

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.collegeuserapp.adapter.OutOfStationAdapter
import com.example.collegeuserapp.databinding.ActivityOutOfStationListBinding
import com.example.collegeuserapp.model.OutOfStation
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class OutOfStationListActivity : AppCompatActivity() {
    private lateinit var binding:ActivityOutOfStationListBinding
    private lateinit var preferences : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOutOfStationListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.title="Previous Gate Passes"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        preferences=this.getSharedPreferences("user", MODE_PRIVATE)


        var list = ArrayList<OutOfStation>()

        Firebase.firestore.collection("Out Of Station Gate Pass")
            .whereEqualTo("emailId",preferences.getString("email",""))
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                list.clear()
                for(doc in it){
                    val data = doc.toObject(OutOfStation::class.java)
                    list.add(data)
                }
                binding.Recycler.adapter= OutOfStationAdapter(this,list)
            }

    }
}