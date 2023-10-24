package com.example.staj

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MusteriAnaSayfaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_musterianasayfa)
    }

    fun milanVerButtonClicked(view: View) {
        val intent = Intent(this, MusteriIlanVerActivity::class.java)
        startActivity(intent)
    }

    fun milanlarimButtonClicked(view: View) {
       val intent = Intent(this, MusteriIlanlarimActivity::class.java)
        startActivity(intent)
    }

}