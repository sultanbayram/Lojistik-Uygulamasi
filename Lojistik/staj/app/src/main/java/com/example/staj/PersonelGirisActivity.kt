package com.example.staj

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class PersonelGirisActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personelgiris)

        auth = FirebaseAuth.getInstance()

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val sifreEditText = findViewById<EditText>(R.id.sifreEditText)
        val girisButton = findViewById<Button>(R.id.girisButton)

        girisButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val sifre = sifreEditText.text.toString()

            if (email.isNotEmpty() && sifre.isNotEmpty()) {
                signInWithEmailAndPassword(email, sifre)
            }
        }
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        // Giriş başarılı, PersonelAnaSayfaActivity'ye yönlendir
                        val intent = Intent(this, PersonelAnaSayfaActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    // Giriş başarısız
                }
            }
    }
}


