package com.example.staj

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MusteriEkleActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_musteriekle)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val mfirmaAdiEditText: EditText = findViewById(R.id.mfirmaAdiEditText)
        val mkullaniciAdiEditText: EditText = findViewById(R.id.mkullaniciAdiEditText)
        val msifreEditText: EditText = findViewById(R.id.msifreEditText)
        val mekleButton: Button = findViewById(R.id.mekleButton)

        mekleButton.setOnClickListener {
            val firmaAdi = mfirmaAdiEditText.text.toString()
            val kullaniciAdi = mkullaniciAdiEditText.text.toString()
            val sifre = msifreEditText.text.toString()


            auth.createUserWithEmailAndPassword(kullaniciAdi, sifre)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (user != null) {
                            val userId = user.uid

                            val musteriMap = hashMapOf(
                                "kullaniciId" to userId,
                                "firmaAdi" to firmaAdi,
                                "kullaniciAdi" to kullaniciAdi,
                                "sifre" to sifre
                            )

                            firestore.collection("musteri")
                                .add(musteriMap)
                                .addOnSuccessListener {

                                    Toast.makeText(this, "Musteri başarıyla eklendi", Toast.LENGTH_SHORT).show()
                                    setResult(RESULT_OK)
                                    finish()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this, "veriler eklenirken bir hata oluştu", Toast.LENGTH_SHORT).show()
                                }
                        }
                    } else {
                        Toast.makeText(this, "musteri eklenirken bir hata oluştu", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}

