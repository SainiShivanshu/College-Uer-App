package com.example.collegeuserapp.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.collegeuserapp.MainActivity
import com.example.collegeuserapp.databinding.ActivityLoginBinding
import com.example.collegeuserapp.databinding.ActivitySignUpBinding
import com.example.collegeuserapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.button4.setOnClickListener {
            openLogin()
        }


        binding.button3.setOnClickListener {
            validateUser()
        }
    }

    private fun validateUser() {
        if(binding.emailId.text!!.toString().isEmpty()){
            binding.emailId.requestFocus()
            binding.emailId.error="Empty"
        }
        else if(binding.password.text!!.isEmpty()){
            binding.password.requestFocus()
            binding.password.error="Empty"
        }
        else{
            storeData()
        }
    }

    private fun storeData() {
        var dialog = ProgressDialog(this)
        dialog .setTitle("Loading..")
       dialog.setMessage("Please Wait")
        dialog.setCancelable(false)

        dialog.show()

        val data=UserModel(emailId = binding.emailId.text.toString())

        auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(binding.emailId.text.toString(),binding.password.text.toString())
            .addOnCompleteListener{ task ->
                if (task.isSuccessful)
                {

                    val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
                    val editor = preferences.edit()
                    editor.putString("email", binding.emailId.text!!.toString())
                    editor.apply()

                    Firebase.firestore.collection("Users").document(binding.emailId.text.toString())
                        .set(data).addOnSuccessListener {
                            dialog.dismiss()
                            Toast.makeText(this,"User registered",Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, SetUpProfileActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            intent.putExtra("email",binding.emailId.text.toString())
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener {
                            dialog.dismiss()
                            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
                        }

                }
                else
                {   dialog.dismiss()
                    Toast.makeText(this,  task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun openLogin() {
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}