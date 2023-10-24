package com.example.staj

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val personelButton: Button = findViewById(R.id.personelButton)
        val musteriButton: Button = findViewById(R.id.musteriButton)
        val soforButton: Button = findViewById(R.id.soforButton)

        personelButton.setOnClickListener {
            val intent = Intent(this, PersonelGirisActivity::class.java)
            startActivity(intent)
        }

        musteriButton.setOnClickListener {
            val intent = Intent(this, MusteriGirisActivity::class.java)
            startActivity(intent)
        }

        soforButton.setOnClickListener {
            val intent = Intent(this, SoforGirisActivity::class.java)
            startActivity(intent)
        }
    }
}


