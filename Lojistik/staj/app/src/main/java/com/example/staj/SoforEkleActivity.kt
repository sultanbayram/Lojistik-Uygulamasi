package com.example.staj

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SoforEkleActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soforekle)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val skullaniciAdiEditText: EditText = findViewById(R.id.skullaniciAdiEditText)
        val ssifreEditText: EditText = findViewById(R.id.ssifreEditText)
        val tcKimlikNoEditText: EditText = findViewById(R.id.tcKimlikNoEditText)
        val adSoyadEditText: EditText = findViewById(R.id.adSoyadEditText)
        val aracTipiEditText: EditText = findViewById(R.id.aracTipiEditText)
        val plakaEditText: EditText = findViewById(R.id.plakaEditText)
        val telefonEditText: EditText = findViewById(R.id.telefonEditText)
        val sekleButton: Button = findViewById(R.id.sekleButton)

        sekleButton.setOnClickListener {
            val kullaniciAdi = skullaniciAdiEditText.text.toString()
            val sifre = ssifreEditText.text.toString()
            val tcKimlikNo = tcKimlikNoEditText.text.toString()
            val adSoyad = adSoyadEditText.text.toString()
            val aracTipi = aracTipiEditText.text.toString()
            val plaka = plakaEditText.text.toString()
            val telefon = telefonEditText.text.toString()

            // Kullanıcıyı Firebase Authentication'a kaydet
            auth.createUserWithEmailAndPassword(kullaniciAdi, sifre)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (user != null) {
                            val userId = user.uid
                            // Diğer bilgileri Cloud Firestore'a kaydet
                            val soforMap = hashMapOf(
                                "kullaniciId" to userId,
                                "tcKimlikNo" to tcKimlikNo,
                                "adSoyad" to adSoyad,
                                "aracTipi" to aracTipi,
                                "plaka" to plaka,
                                "telefon" to telefon
                            )

                            firestore.collection("sofor")
                                .add(soforMap)
                                .addOnSuccessListener {
                                    // Veri başarıyla eklendi
                                    // Kullanıcıya bildirim verip ana sayfaya dönme işlemi burada yapılabilir
                                    setResult(RESULT_OK)
                                    finish()
                                }
                                .addOnFailureListener {
                                    // Veri ekleme işlemi başarısız oldu
                                }
                        }
                    } else {
                        // Kullanıcı kaydetme işlemi başarısız oldu
                    }
                }
        }
    }
}
