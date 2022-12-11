package com.example.collegeuserapp

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.example.collegeuserapp.activity.*
import com.example.collegeuserapp.databinding.ActivityMainBinding
import com.example.collegeuserapp.model.UserModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var  binding: ActivityMainBinding
    var actionBarDrawerToggle:ActionBarDrawerToggle?=null
    private lateinit var preferences : SharedPreferences
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



        preferences=this.getSharedPreferences("user", MODE_PRIVATE)


        if (FirebaseAuth.getInstance().currentUser != null) {
            Firebase.firestore.collection("Users").document(preferences.getString("email", "")!!.toString())
                .get().addOnSuccessListener {
                    var data = it.toObject<UserModel>()
                    var bin= binding.navigationView.getHeaderView(0)
                    var name =   bin.findViewById(R.id.navUserName) as TextView
                    var email = bin.findViewById(R.id.navUser_emailId) as TextView
                    var img = bin.findViewById(R.id.navUserImage) as CircleImageView
                    name.setText(data!!.name.toString())
                    email.setText(data!!.emailId.toString())
                    Glide.with(this).load(data.imageUrl.toString()).into(img)
                    img.setOnClickListener {
                        val intent = Intent(this, FullImageActivity::class.java)
                        intent.putExtra("image",data!!.imageUrl.toString())
                        intent.putExtra("title","Profile Image")
                        startActivity(intent)
                    }
                    if(preferences.getString("rollNo","")==""){
                        val editor = preferences.edit()
                        editor.putString("rollNo", data!!.rollNo.toString())
                        editor.apply()
                    }


                }
        }



        binding.navigationView.itemIconTintList=null
        actionBarDrawerToggle= ActionBarDrawerToggle(this,binding.drawerLayout,R.string.Open,R.string.Close)

        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle!!)
        actionBarDrawerToggle!!.syncState()


//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding!!.toolbar.title="Home"
        setSupportActionBar(binding!!.toolbar)
      binding.  toolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }


        binding.navigationView.setNavigationItemSelectedListener(this)
        val navController=findNavController(R.id.fragment)

        NavigationUI.setupWithNavController(binding.bottomNavigationView,navController)

binding.bottomNavigationView.setOnNavigationItemSelectedListener {
    when(it.itemId){
        R.id.homeFragment->{
            binding.toolbar.title="Home"
            navController.navigate(R.id.homeFragment)
        }

        R.id.noticeFragment-> {
            binding.toolbar.title = "Notices"
        navController.navigate(R.id.noticeFragment)}
        else->{
            binding.toolbar.title="Gallery"
            navController.navigate(R.id.galleryFragment)
        }

    }

true}

setupDrawerAnimation()

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
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.rgipt.ac.in/")
                    )
                )
            }
            R.id.about -> {
                Toast.makeText(this, "about", Toast.LENGTH_SHORT).show()
            }
            R.id.share -> {
//                Toast.makeText(this, "share", Toast.LENGTH_SHORT).show()
                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type = "text/plain"
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "College User App")
                startActivity(Intent.createChooser(sharingIntent, "Share app via"))
            }

        }
        return true
    }


    private fun setupDrawerAnimation() {
        val scaleControl = 6f

        binding.drawerLayout.setScrimColor(Color.TRANSPARENT)
        binding.drawerLayout.elevation = 0f
        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                val slideX = drawerView.width * slideOffset
       binding.constraint         .translationX = slideX
                 binding.constraint.scaleY = 1 - (slideOffset/scaleControl)
            }

            override fun onDrawerStateChanged(newState: Int) {}
            override fun onDrawerClosed(drawerView: View) {}
            override fun onDrawerOpened(drawerView: View) {}
        })
    }

}