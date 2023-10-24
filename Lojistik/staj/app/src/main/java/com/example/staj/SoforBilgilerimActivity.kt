package com.example.staj

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SoforBilgilerimActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private lateinit var editAdSoyad: EditText
    private lateinit var editAracTipi: EditText
    private lateinit var editPlaka: EditText
    private lateinit var editTcKimlikNo: EditText
    private lateinit var editTelefon: EditText
    private lateinit var btnKaydet: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sofor_bilgilerim)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        editAdSoyad = findViewById(R.id.editAdSoyad)
        editAracTipi = findViewById(R.id.editAracTipi)
        editPlaka = findViewById(R.id.editPlaka)
        editTcKimlikNo = findViewById(R.id.editTcKimlikNo)
        editTelefon = findViewById(R.id.editTelefon)
        btnKaydet = findViewById(R.id.btnKaydet)

        loadSoforBilgileri()

        btnKaydet.setOnClickListener { updateSoforBilgileri() }
    }

    private fun loadSoforBilgileri() {
        val user = auth.currentUser
        if (user != null) {
            val kullaniciId = user.uid

            firestore.collection("sofor")
                .document(kullaniciId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val adSoyad = documentSnapshot.getString("adSoyad")
                    val aracTipi = documentSnapshot.getString("aracTipi")
                    val plaka = documentSnapshot.getString("plaka")
                    val tcKimlikNo = documentSnapshot.getString("tcKimlikNo")
                    val telefon = documentSnapshot.getString("telefon")

                    editAdSoyad.setText(adSoyad)
                    editAracTipi.setText(aracTipi)
                    editPlaka.setText(plaka)
                    editTcKimlikNo.setText(tcKimlikNo)
                    editTelefon.setText(telefon)
                }
        }
    }

    private fun updateSoforBilgileri() {
        val user = auth.currentUser
        if (user != null) {
            val kullaniciId = user.uid

            val updatedAdSoyad = editAdSoyad.text.toString()
            val updatedAracTipi = editAracTipi.text.toString()
            val updatedPlaka = editPlaka.text.toString()
            val updatedTcKimlikNo = editTcKimlikNo.text.toString()
            val updatedTelefon = editTelefon.text.toString()

            val updatedData = mutableMapOf<String, Any>(
                "adSoyad" to updatedAdSoyad,
                "aracTipi" to updatedAracTipi,
                "plaka" to updatedPlaka,
                "tcKimlikNo" to updatedTcKimlikNo,
                "telefon" to updatedTelefon
            )

            firestore.collection("sofor")
                .document(kullaniciId)
                .update(updatedData)
                .addOnSuccessListener {
                    // Güncelleme başarılı
                    // İsteğe bağlı olarak bir Toast veya başka bir geri bildirim eklenebilir
                }
                .addOnFailureListener { exception ->
                    // Güncelleme başarısız
                    // İsteğe bağlı olarak bir Toast veya başka bir hata mesajı eklenebilir
                }
        }
    }


    }


