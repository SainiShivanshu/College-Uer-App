package com.example.collegeuserapp.activity

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.collegeuserapp.databinding.ActivityPdfViewerBinding


class PdfViewerActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPdfViewerBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPdfViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var url =intent.getStringExtra("url")
        var title = intent.getStringExtra("title")

        supportActionBar?.title=title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



binding.pdfView.loadUrl("https://docs.google.com/gview?embedded=true&url="+url!!)
        binding.pdfView.settings.javaScriptEnabled=true
        binding.pdfView.settings.setSupportZoom(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}