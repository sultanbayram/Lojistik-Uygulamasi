package com.example.staj

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class SoforGirisActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soforgiris)

        auth = FirebaseAuth.getInstance()

        val semailEditText = findViewById<EditText>(R.id.soforemailEditText)
        val ssifreEditText = findViewById<EditText>(R.id.soforsifreEditText)
        val sgirisButton = findViewById<Button>(R.id.soforgirisButton)

        sgirisButton.setOnClickListener {
            val email = semailEditText.text.toString()
            val sifre = ssifreEditText.text.toString()

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
                         val intent = Intent(this, SoforAnaSayfaActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    Toast.makeText(this, "Giriş başarısız", Toast.LENGTH_SHORT).show()
                }
            }
    }
}