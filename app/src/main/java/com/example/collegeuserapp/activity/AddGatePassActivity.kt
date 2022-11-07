package com.example.collegeuserapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.collegeuserapp.databinding.ActivityAddGatePassBinding

class AddGatePassActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddGatePassBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddGatePassBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}