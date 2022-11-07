package com.example.collegeuserapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.collegeuserapp.activity.*
import com.example.collegeuserapp.databinding.ActivityMainBinding
import com.example.collegeuserapp.model.UserModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var  binding: ActivityMainBinding
    var actionBarDrawerToggle:ActionBarDrawerToggle?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        else{
            Thread(Runnable {
                this.runOnUiThread(java.lang.Runnable {

                    val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
                    val email = preferences.getString("email", "")
                    Firebase.firestore.collection("Users").document(email!!.toString()).get()
                        .addOnSuccessListener {

                            val doc = it.toObject<UserModel>()
                            if (doc!!.name == "" || doc!!.branch == "" || doc!!.address == "" || doc!!.mobileNo == "" || doc!!.programme == "" || doc!!.imageUrl == "") {
                                startActivity(
                                    Intent(
                                        this,
                                        SetUpProfileActivity::class.java
                                    ).putExtra("email", doc!!.emailId.toString())
                                )
                                finish()
                            }

                        }
                        .addOnFailureListener {
                            Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }

                })
            }).start()
        }


        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBarDrawerToggle= ActionBarDrawerToggle(this,binding.drawerLayout,R.string.Open,R.string.Close)

        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle!!)
        actionBarDrawerToggle!!.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.navigationView.setNavigationItemSelectedListener(this)
        val navController=findNavController(R.id.fragment)

        NavigationUI.setupWithNavController(binding.bottomNavigationView,navController)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return if(actionBarDrawerToggle!!.onOptionsItemSelected(item)){
           true
       }
        else{
            super.onOptionsItemSelected(item)
       }
    }

    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.close()
        }
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.profile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
                binding.drawerLayout.close()
            }
            R.id.faculty->{
                startActivity(Intent(this,FacultyActivity::class.java))
                binding.drawerLayout.close()
            }
            R.id.ebook -> {
                startActivity(Intent(this,EbookActivity::class.java))
                binding.drawerLayout.close()
            }
            R.id.videoLecture -> {

            }
            R.id.gatepass->{
                startActivity(Intent(this,GatePassActivity::class.java))
                binding.drawerLayout.close()
            }
            R.id.complain->{
                startActivity(Intent(this,ComplainActivity::class.java))
                binding.drawerLayout.close()
            }
            R.id.website -> {
                Toast.makeText(this, "website", Toast.LENGTH_SHORT).show()
            }
            R.id.about -> {
                Toast.makeText(this, "about", Toast.LENGTH_SHORT).show()
            }
            R.id.share -> {
                Toast.makeText(this, "share", Toast.LENGTH_SHORT).show()
            }

        }
        return true
    }
}