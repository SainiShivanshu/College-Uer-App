package com.example.collegeuserapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import com.example.collegeuserapp.R
import com.example.collegeuserapp.adapter.EbookAdapter
import com.example.collegeuserapp.databinding.ActivityEbookBinding
import com.example.collegeuserapp.model.AddEbookModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class EbookActivity : AppCompatActivity() {
    private lateinit var binding:ActivityEbookBinding
    private lateinit var temp:ArrayList<AddEbookModel>
    private lateinit var pdf:ArrayList<AddEbookModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEbookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title="Ebooks"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getEbookList()

temp= ArrayList()
    }

    private fun getEbookList() {
         pdf =ArrayList<AddEbookModel>()

        Firebase.firestore.collection("Ebooks")
            .orderBy("ebookTitle", Query.Direction.ASCENDING)
            .get().addOnSuccessListener {
               pdf.clear()
                for (doc in it) {
                    val data = doc.toObject(AddEbookModel::class.java)
                       pdf.add(data)
                }
                temp.addAll(pdf)
         binding.ebookRecycler.adapter=EbookAdapter(this,temp)
         binding.shimmerViewContainer.stopShimmer()
                binding.shimmerView.visibility=View.GONE
            }
            .addOnFailureListener(OnFailureListener {
              Log.d("error",it.toString())
            })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search,menu)
val item = menu!!.findItem(R.id.search_action)

val searchView=item!!.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                temp.clear()
                val searchText=newText!!.toLowerCase(Locale.getDefault())
                if(searchText.isNotEmpty()){

                    pdf.forEach {
                        if(it.ebookTitle!!.toLowerCase(Locale.getDefault()).contains(searchText)){
                            temp.add(it)
                        }
                    }

                    binding.ebookRecycler.adapter!!.notifyDataSetChanged()

                }
                else {
                    temp.clear()
                    temp.addAll(pdf)
                    binding.ebookRecycler.adapter!!.notifyDataSetChanged()
                }


                return false
            }


        })
        return super.onCreateOptionsMenu(menu)
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