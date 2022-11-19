package com.example.collegeuserapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.collegeuserapp.databinding.ActivityForgotPasswordBinding
import com.example.collegeuserapp.databinding.ActivitySignUpBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
auth=FirebaseAuth.getInstance()

        binding.Enter.setOnClickListener {
            validateData()
        }


    }

    private fun validateData() {
        if(binding.emailId.text!!.isEmpty()){
            binding.emailId.requestFocus()
            binding.emailId.error="Empty"
        }
        else{
            forgotPassword()
        }
    }

    private fun forgotPassword() {
        auth.sendPasswordResetEmail(binding.emailId.text.toString())
            .addOnCompleteListener{task->
if(task.isSuccessful){
Toast.makeText(this,"Check your email",Toast.LENGTH_SHORT).show()

    finish()
}
                else{
            Toast.makeText(this,task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()
                }
            }
    }
}