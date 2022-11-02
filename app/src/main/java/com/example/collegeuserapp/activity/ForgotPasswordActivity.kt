package com.example.collegeuserapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.collegeuserapp.databinding.ActivityForgotPasswordBinding
import com.example.collegeuserapp.databinding.ActivitySignUpBinding

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}