package com.example.collegeuserapp.activity

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.collegeuserapp.adapter.ComplainAdapter
import com.example.collegeuserapp.databinding.ActivityComplainListBinding
import com.example.collegeuserapp.model.Complain
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ComplainListActivity : AppCompatActivity() {
    private lateinit var binding:ActivityComplainListBinding
    private lateinit var preferences : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityComplainListBinding.inflate(layoutInflater)
        setContentView(binding.root)

supportActionBar?.title="Previous Complain"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        preferences=this.getSharedPreferences("user", MODE_PRIVATE)
        var list = ArrayList<Complain>()

        Firebase.firestore.collection("Complain")
            .whereEqualTo("emailId",preferences.getString("email",""))
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get().addOnSuccessListener {
                list.clear()
                for(doc in it) {
                    val data = doc.toObject(Complain::class.java)
                    list.add(data)
                }

                binding.complainRecycler.adapter=ComplainAdapter(this,list)

            }
            .addOnFailureListener {
                Toast.makeText(this,it.message.toString(), Toast.LENGTH_SHORT).show()
            }

    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}