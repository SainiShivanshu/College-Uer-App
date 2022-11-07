package com.example.collegeuserapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.collegeuserapp.adapter.EbookAdapter
import com.example.collegeuserapp.databinding.ActivityEbookBinding
import com.example.collegeuserapp.model.AddEbookModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EbookActivity : AppCompatActivity() {
    private lateinit var binding:ActivityEbookBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEbookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title="Ebooks"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getEbookList()


    }

    private fun getEbookList() {
        val pdf =ArrayList<AddEbookModel>()

        Firebase.firestore.collection("Ebooks")
            .orderBy("ebookTitle", Query.Direction.ASCENDING)
            .get().addOnSuccessListener {
               pdf.clear()
                for (doc in it) {
                    val data = doc.toObject(AddEbookModel::class.java)
                       pdf.add(data)
                }
         binding.ebookRecycler.adapter=EbookAdapter(this,pdf)
         binding.shimmerViewContainer.stopShimmer()
                binding.shimmerView.visibility=View.GONE
            }
            .addOnFailureListener(OnFailureListener {
              Log.d("error",it.toString())
            })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onPause() {

        binding.shimmerViewContainer.stopShimmer()
        super.onPause()
    }

    override fun onResume() {
        binding.shimmerViewContainer.startShimmer()
        super.onResume()
    }
}