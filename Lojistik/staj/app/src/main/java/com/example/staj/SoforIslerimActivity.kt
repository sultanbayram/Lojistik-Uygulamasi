package com.example.staj
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SoforIslerimActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SoforIslerimAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sofor_islerim)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        recyclerView = findViewById(R.id.islerRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SoforIslerimAdapter()

        recyclerView.adapter = adapter

        loadIslerFromFirestore()

        adapter.setOnTamamlandiClickListener { ilan ->
            // Tamamlandı butonuna tıklanıldığında yapılacak işlemler
            tamamlandi(ilan)
        }
    }

    private fun loadIslerFromFirestore() {
        val user = auth.currentUser
        if (user != null) {
            val soforEmail = user.email

            firestore.collection("onaylanan")
                .whereEqualTo("soforEmail", soforEmail)
                .get()
                .addOnSuccessListener { result ->
                    val isList = mutableListOf<SIlan>()
                    for (document in result) {
                        val id = document.id
                        val soforAdSoyad = document.getString("soforEmail")
                        val ilanNereden = document.getString("ilanNereden")
                        val ilanNereye = document.getString("ilanNereye")
                        val ilanOlculer = document.getString("ilanOlculer")
                        val ilanFiyat = document.getString("ilanFiyat")
                        val ilanTarih = document.getString("ilanTarih")
                        val ilan = SIlan(id, soforAdSoyad, ilanNereden, ilanNereye, ilanOlculer, ilanFiyat, ilanTarih)
                        isList.add(ilan)
                    }
                    adapter.setData(isList)
                }
                .addOnFailureListener { exception ->
                    // İşleri çekerken hata oluştu
                    Toast.makeText(this, "İşleri çekerken hata oluştu", Toast.LENGTH_SHORT).show()
                }
        }
    }private fun tamamlandi(ilan: SIlan) {
        val user = auth.currentUser
        if (user != null) {
            val soforEmail = user.email

            // Tamamlandı işlemi için ilan verilerini hazırla
            val tamamlananIlan = hashMapOf(
                "nereden" to ilan.ilanNereden,
                "nereye" to ilan.ilanNereye,
                "olculer" to ilan.ilanOlculer,
                "fiyat" to ilan.ilanFiyat,
                "tarih" to ilan.ilanTarih,
                "soforEmail" to ilan.soforEmail,
                "soforEmail" to soforEmail
            )

            // "tamamlanan_ilanlar" koleksiyonuna ilanı ekle
            firestore.collection("tamamlanan_ilanlar")
                .add(tamamlananIlan)
                .addOnSuccessListener { documentReference ->
                    // Başarılı bir şekilde eklendiğinde yapılacak işlemler
                    Toast.makeText(this, "İş tamamlandı ve kaydedildi.", Toast.LENGTH_SHORT).show()

                    // "onaylanan" koleksiyonundan ilanı sil
                    firestore.collection("onaylanan")
                        .document(ilan.id!!)
                        .delete()
                        .addOnSuccessListener {
                                // İlan başarıyla silindiğinde yapılacak işlemler
                                Toast.makeText(this, "Onaylanan iş silindi.", Toast.LENGTH_SHORT).show()
                                loadIslerFromFirestore() // İşleri yeniden yükle
                            }
                            .addOnFailureListener { exception ->
                                // İlan silinemediğinde yapılacak işlemler
                                Toast.makeText(this, "Onaylanan iş silinemedi.", Toast.LENGTH_SHORT).show()
                            }
                    }

                .addOnFailureListener { exception ->
                    // İlan kaydedilemediğinde yapılacak işlemler
                    Toast.makeText(this, "İş tamamlanamadı.", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
