package com.example.collegeuserapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.collegeuserapp.databinding.ActivityGatePassBinding

class GatePassActivity : AppCompatActivity() {

    private lateinit var binding:ActivityGatePassBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityGatePassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title="Gate Pass"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
binding.LocalGatePass.setOnClickListener {
    startActivity(Intent(this,LocalGatePassActivity::class.java))

}

binding.OutOfStation.setOnClickListener {
    startActivity(Intent(this,OutOfStationActivity::class.java))
}

    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}