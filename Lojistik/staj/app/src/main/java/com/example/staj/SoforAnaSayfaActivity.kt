package com.example.staj

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class SoforAnaSayfaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soforanasayfa)
    }

    fun soforbilgilerimButtonClicked(view: View) {
        val intent = Intent(this, SoforBilgilerimActivity::class.java)
        startActivity(intent)
    }

    fun soforilanlarButtonClicked(view: View) {
        val intent = Intent(this, SoforIlanlarActivity::class.java)
        startActivity(intent)
    }
    fun sofortaleplerimButtonClicked(view: View) {
       val intent = Intent(this, SoforTaleplerimActivity::class.java)
        startActivity(intent)
    }

    fun soforislerimButtonClicked(view: View) {
        val intent = Intent(this, SoforIslerimActivity::class.java)
        startActivity(intent)
    }

}