package com.example.collegeuserapp.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.collegeuserapp.MainActivity
import com.example.collegeuserapp.databinding.ActivityLoginBinding
import com.example.collegeuserapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding=  ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button4.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))

        }

        binding.ForgotPassword.setOnClickListener {
            startActivity(Intent(this,ForgotPasswordActivity::class.java))

        }

        binding.button3.setOnClickListener {
            if(binding.emailId.text!!.isEmpty()){
                binding.emailId.requestFocus()
                binding.emailId.error="Empty"
            }
            else if(binding.password.text!!.isEmpty()){
                binding.password.requestFocus()
                binding.password.error="Empty"
            }
            else{
                validatePassword()
            }
        }

    }

    private fun validatePassword() {
auth = FirebaseAuth.getInstance()

        var dialog = ProgressDialog(this)
        dialog .setTitle("Loading..")
        dialog.setMessage("Please Wait")
        dialog.setCancelable(false)

        dialog.show()

        val password = binding.password.text.toString()
        val email = binding.emailId.text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                {
                    val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
                    val editor = preferences.edit()
                    editor.putString("email", binding.emailId.text!!.toString())
                    editor.apply()

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
                            else{
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()
                            }

                        }
                        .addOnFailureListener {
                            Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }



                }
                else
                {
                    Toast.makeText(this@LoginActivity, task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                }
            }


    }
}