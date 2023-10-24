package com.example.staj

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SoforIlanlarActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SoforIlanlarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sofor_ilanlar)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        recyclerView = findViewById(R.id.ilansRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SoforIlanlarAdapter()

        recyclerView.adapter = adapter

        loadIlanlarFromFirestore()

        adapter.setOnTalepClickListener { ilan ->
            talepEt(ilan)
        }
    }

    private fun loadIlanlarFromFirestore() {
        firestore.collection("paylasilan_ilanlar")
            .get()
            .addOnSuccessListener { result ->
                val ilanList = mutableListOf<SIlan>()
                for (document in result) {
                    val id = document.id
                    val soforAdSoyad = document.getString("soforEmail")
                    val ilanNereden = document.getString("ilanNereden")
                    val ilanNereye = document.getString("ilanNereye")
                    val ilanOlculer = document.getString("ilanOlculer")
                    val ilanFiyat = document.getString("ilanFiyat")
                    val ilanTarih = document.getString("ilanTarih")
                    val ilan = SIlan(id, soforAdSoyad,ilanNereden , ilanNereye, ilanOlculer, ilanFiyat, ilanTarih)
                    ilanList.add(ilan)
                }
                adapter.setData(ilanList)
            }
            .addOnFailureListener { exception ->

                Toast.makeText(this, "İlanları çekerken hata oluştu", Toast.LENGTH_SHORT).show()
            }
    }

    private fun talepEt(ilan: SIlan) {
        val user = auth.currentUser
        if (user != null) {
            val soforEmail = user.email
            val talep = hashMapOf(
                "soforEmail" to soforEmail,
                "soforEmail" to ilan.soforEmail,
                "ilanNereden" to ilan.ilanNereden,
                "ilanNereye" to ilan.ilanNereye,
                "ilanOlculer" to ilan.ilanOlculer,
                "ilanFiyat" to ilan.ilanFiyat,
                "ilanTarih" to ilan.ilanTarih
            )

            // Firestore'da "talepler" koleksiyonuna ekle
            firestore.collection("talepler")
                .add(talep)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "İlanı talep ettiniz.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Talep işlemi başarısız oldu.", Toast.LENGTH_SHORT).show()
                }
        }
    }

}