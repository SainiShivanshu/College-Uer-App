package com.example.collegeuserapp.activity

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.collegeuserapp.adapter.GatePassAdapter
import com.example.collegeuserapp.databinding.ActivityLocalGatePassBinding
import com.example.collegeuserapp.databinding.ActivityLocalGatePassListBinding
import com.example.collegeuserapp.model.LocalGatePass
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LocalGatePassListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLocalGatePassListBinding
    private lateinit var preferences : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLocalGatePassListBinding.inflate(layoutInflater)
        setContentView(binding.root)
supportActionBar?.title="Previous Gate Passes"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        preferences=this.getSharedPreferences("user", MODE_PRIVATE)

        var list = ArrayList<LocalGatePass>()

        Firebase.firestore.collection("Local Gate Pass")
            .whereEqualTo("rollNo",preferences.getString("rollNo",""))
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                list.clear()
                for(doc in it){
                    val data = doc.toObject(LocalGatePass::class.java)
                    list.add(data)
                }
                binding.RecyclerView.adapter=GatePassAdapter(this,list)

            }
            .addOnFailureListener {
                Log.d("error",it.message.toString())
            }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}