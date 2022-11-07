package com.example.collegeuserapp.activity

import android.R
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.collegeuserapp.MainActivity
import com.example.collegeuserapp.databinding.ActivityProfileBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import kotlin.collections.ArrayList

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding:ActivityProfileBinding
    private lateinit var items:ArrayList<String>
    private lateinit var depart:ArrayList<String>
    private lateinit var branch:String
    private lateinit var programme:String
    private var userImage: Uri?=null
    private lateinit var dialog: ProgressDialog
    private lateinit var preferences : SharedPreferences


    private var launchGalleryActivity =registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode== Activity.RESULT_OK){
            userImage=it.data!!.data
            binding.userImage.setImageURI(userImage)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title="Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        preferences=this.getSharedPreferences("user", MODE_PRIVATE)
        loadUserInfo()
        dialog= ProgressDialog(this)
        dialog.setTitle("Please Wait")
        dialog.setMessage("Profile is being Updated")
        dialog.setCancelable(false)

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

        binding.userImage.setOnClickListener {
            val intent= Intent("android.intent.action.GET_CONTENT")
            intent.type="image/*"
            launchGalleryActivity.launch(intent)
        }



        binding.proceed.setOnClickListener {
            if(binding.userName.text!!.isEmpty()){
                binding.userName.requestFocus()
                binding.userName.error="Empty"
            }
            else if(binding.userNumber.text!!.isEmpty()){
                binding.userNumber.requestFocus()
                binding.userNumber.error="Empty"
            }
            else if(binding.rollNo.text!!.isEmpty()){
                binding.rollNo.requestFocus()
                binding.rollNo.error="Empty"
            }
            else if(binding.address.text!!.isEmpty()){
                binding.address.requestFocus()
                binding.address.error="Empty"
            }

            else if(branch=="Select Department" || branch==null){
                Toast.makeText(this,"Select Department", Toast.LENGTH_SHORT).show()
            }
            else if(programme=="Select Programme" || programme==null){
                Toast.makeText(this,"Select Programme", Toast.LENGTH_SHORT).show()
            }

            else if(userImage!=null){
                uploadImage()
            }
            else{
                InsertData()
            }

        }
    }

    private fun InsertData() {
        val map = hashMapOf<String,Any>()
        map["name"]=binding.userName.text.toString()
        map["mobileNo"]=binding.userNumber.text.toString()
        map["address"]=binding.address.text.toString()
        map["programme"]=programme
        map["branch"]=branch
        map["rollNo"]=binding.rollNo.text.toString()

        Firebase.firestore.collection("Users")
            .document(binding.emailId.text.toString())
            .update(map).addOnSuccessListener {
                Toast.makeText(this,"Profile Updated!!",Toast.LENGTH_SHORT).show()


            }
            .addOnFailureListener {
                Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()
            }
    }

    private fun storeData(image: String) {
        val map = hashMapOf<String,Any>()
        map["name"]=binding.userName.text.toString()
        map["mobileNo"]=binding.userNumber.text.toString()
        map["imageUrl"]=image
        map["address"]=binding.address.text.toString()
        map["programme"]=programme
        map["branch"]=branch
        map["rollNo"]=binding.rollNo.text.toString()

        Firebase.firestore.collection("Users")
            .document(binding.emailId.text.toString())
            .update(map).addOnSuccessListener {
                Toast.makeText(this,"Profile Updated!!",Toast.LENGTH_SHORT).show()


            }
            .addOnFailureListener {
                Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadImage() {
        dialog.show()
        val fileName = UUID.randomUUID().toString()+".jpg"

        val refStorage = FirebaseStorage.getInstance().reference.child("Users/$fileName")
        refStorage.putFile(userImage!!)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener { image->
                    storeData(image!!.toString())
                }
            }
            .addOnFailureListener{
                dialog.dismiss()
                Toast.makeText(this,"Something Went Wrong with storage" , Toast.LENGTH_SHORT).show()
            }
    }


    private fun loadUserInfo() {
        Firebase.firestore.collection("Users")
            .document(preferences.getString("email", "")!!)
            .get().addOnSuccessListener {
                binding.emailId.setText(it.getString("emailId"))
                binding.userName.setText(it.getString("name"))
                binding.userNumber.setText(it.getString("mobileNo"))
                binding.address.setText(it.getString("address"))
                binding.rollNo.setText(it.getString("rollNo"))
                Glide.with(this).load(it.getString("imageUrl")).into(binding.userImage)

                var i=0
                when (it.getString("branch")) {
                    "Chemical Engineering" -> {
                        i = 1
                        branch="Chemical Engineering"
                    }
                    "Computer Science & Engineering" -> {
                        i = 2
                        branch= "Computer Science & Engineering"
                    }
                    "Electronics Engineering" -> {
                        i = 3
                        branch= "Electronics Engineering"
                    }
                    "Petroleum Engineering & Geoengineering" -> {
                        i = 4
                        branch= "Petroleum Engineering & Geoengineering"
                    }
                    "Mathematical Science" -> {
                        i = 5
                        branch="Mathematical Science"
                    }
                    "Management Studies" -> {
                        i = 6
                        branch="Management Studies"
                    }
                    "Science and Humanities" -> {
                        i = 7
                        branch="Science and Humanities"
                    }

                }
                binding.Branch.setSelection(i)

                var j=0

                when (it.getString("programme")) {
                    "B Tech"->{
                        j=1
                        programme="B Tech"
                    }
                    "IDD"->{
                        j=2
                        programme=  "IDD"
                    }
                    "MBA"->{
                        j=3
                        programme="MBA"
                    }
                    "M Tech"->{
                        j=4
                        programme="M Tech"
                    }
                    "PhD"->{
                        j=5
                        programme= "PhD"
                    }
                }
binding.Programme.setSelection(j)


            }
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}