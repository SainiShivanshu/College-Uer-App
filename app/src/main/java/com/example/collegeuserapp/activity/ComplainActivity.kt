package com.example.collegeuserapp.activity

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.collegeuserapp.databinding.ActivityComplainBinding
import com.example.collegeuserapp.model.Complain
import com.example.collegeuserapp.model.LocalGatePass
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ComplainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityComplainBinding
    private lateinit var items:ArrayList<String>
    private lateinit var depart:ArrayList<String>
    private lateinit var category:ArrayList<String>
    private lateinit var branch:String
    private lateinit var programme:String
    private lateinit var type:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityComplainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title="Complain"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        items= arrayListOf("Select Programme","B Tech",
            "IDD","MBA","M Tech",
            "PhD")
        val arrayAdapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item,items)
        binding.Programme.adapter=arrayAdapter

        binding.Programme.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                programme= binding.Programme.selectedItem.toString()
            }

        }

        depart= arrayListOf("Select Department","Chemical Engineering",
            "Computer Science & Engineering","Electronics Engineering",
            "Petroleum Engineering & Geoengineering","Mathematical Science","Management Studies",
            "Science and Humanities")

        val arrayAdapter2 = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item,depart)
        binding.Branch.adapter=arrayAdapter2

        binding.Branch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                branch= binding.Branch.selectedItem.toString()
            }
        }

        category= arrayListOf("Select Category","Carpentry",
            "Electric","Maintenance","Others"
            )

        val arrayAdapter3 = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item,category)
        binding.Category.adapter=arrayAdapter3

        binding.Category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                type= binding.Category.selectedItem.toString()
            }

        }


        binding.proceed.setOnClickListener {
            ValidateData()
        }
    }

    private fun ValidateData() {
        if(binding.name.text!!.isEmpty()){
            binding.name.requestFocus()
            binding.name.error="Empty"
        }
        else if(binding.rollNo.text!!.isEmpty()){
            binding.rollNo.requestFocus()
            binding.rollNo.error="Empty"
        }
        else if(binding.mobileNo.text!!.isEmpty()){
            binding.mobileNo.requestFocus()
            binding.mobileNo.error="Empty"
        }


        else if(binding.RoomNo.text!!.isEmpty()){
            binding.RoomNo.requestFocus()
            binding.RoomNo.error="Empty"
        }
        else if(binding.description.text!!.isEmpty()){
            binding.description.requestFocus()
            binding.description.error="Empty"
        }
        else if(programme=="Select Programme" || programme==null){
            Toast.makeText(this,"Select Programme", Toast.LENGTH_SHORT).show()
        }
        else if(branch=="Select Department" || branch==null){
            Toast.makeText(this,"Select Department", Toast.LENGTH_SHORT).show()
        }

        else if(type=="Select Category" || programme==null){
            Toast.makeText(this,"Select Category", Toast.LENGTH_SHORT).show()
        }
        else{
            storeData()
        }
    }

    private fun storeData() {
        val db = Firebase.firestore.collection("Complain")
        val key = db.document().id

        val   data= Complain(
            id=key,
            name = binding.name.text.toString(),
            rollNo = binding.rollNo.text.toString(),
            programme = programme,
            branch = branch,
            mobileNo = binding.mobileNo.text.toString(),
            timestamp= FieldValue.serverTimestamp(),
            roomNo = binding.RoomNo.text.toString(),
            category = type,
            description = binding.description.text.toString()

        )
        Firebase.firestore.collection("Complain")
            .document(key).set(data).addOnSuccessListener {

                Toast.makeText(this,"Complain Submitted", Toast.LENGTH_SHORT).show()
                binding.name.text=null
                binding.rollNo.text=null
                binding.description.text=null
                binding.mobileNo.text=null
                binding.RoomNo.text=null
           type="Select Category"
                programme= "Select Programme"
                branch="Select Department"
                binding.Branch.setSelection(0)
                binding.Programme.setSelection(0)
                binding.Category.setSelection(0)
            }
            .addOnFailureListener {
                Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()

            }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}