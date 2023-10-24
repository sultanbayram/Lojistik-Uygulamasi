package com.example.staj

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MusteriGirisActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_musterigiris)

        auth = FirebaseAuth.getInstance()

        val memailEditText = findViewById<EditText>(R.id.memailEditText)
        val msifreEditText = findViewById<EditText>(R.id.msifreEditText)
        val girisButton = findViewById<Button>(R.id.mgirisButton)

        girisButton.setOnClickListener {
            val email = memailEditText.text.toString()
            val sifre = msifreEditText.text.toString()

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

                        val intent = Intent(this, MusteriAnaSayfaActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    Toast.makeText(this, "Giriş başarısız", Toast.LENGTH_SHORT).show()
                }
            }
    }
}