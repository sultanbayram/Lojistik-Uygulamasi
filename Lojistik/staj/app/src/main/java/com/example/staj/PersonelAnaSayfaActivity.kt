package com.example.staj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.staj.R

class PersonelAnaSayfaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personelanasayfa)
    }

    fun ilanVerButtonClicked(view: View) {
        val intent = Intent(this, IlanVerActivity::class.java)
        startActivity(intent)
    }

    fun soforEkleButtonClicked(view: View) {
        val intent = Intent(this, SoforEkleActivity::class.java)
        startActivity(intent)
    }

    fun musteriEkleButtonClicked(view: View) {
        val intent = Intent(this, MusteriEkleActivity::class.java)
        startActivity(intent)
    }

    fun ilanlarimButtonClicked(view: View) {
        val intent = Intent(this, IlanlarimActivity::class.java)
        startActivity(intent)
    }

    fun musterilerButtonClicked(view: View) {
        val intent = Intent(this, MusterilerActivity::class.java)
        startActivity(intent)
    }

    fun soforlerButtonClicked(view: View) {
        val intent = Intent(this, SoforlerActivity::class.java)
        startActivity(intent)
    }

    fun taleplerButtonClicked(view: View) {
        val intent = Intent(this, Talepler::class.java)
        startActivity(intent)
    }

    fun tamamlananlarButtonClicked(view: View) {
        val intent = Intent(this, TamamlananActivity::class.java)
        startActivity(intent)
    }

}
