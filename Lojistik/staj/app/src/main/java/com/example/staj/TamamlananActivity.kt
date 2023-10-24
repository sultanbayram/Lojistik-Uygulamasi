package com.example.staj
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TamamlananActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TamamlananAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tamamlanan)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        recyclerView = findViewById(R.id.tamamlananRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TamamlananAdapter()

        recyclerView.adapter = adapter

        loadTamamlananIlanlarFromFirestore()
    }

    private fun loadTamamlananIlanlarFromFirestore() {

            firestore.collection("tamamlanan_ilanlar")
                .get()
                .addOnSuccessListener { result ->
                    val tamamlananList = mutableListOf<TamamlananIlani>()
                    for (document in result) {
                        val id = document.id
                        val soforAdSoyad = document.getString("soforEmail")
                        val ilanNereden = document.getString("nereden")
                        val ilanNereye = document.getString("nereye")
                        val ilanOlculer = document.getString("olculer")
                        val ilanFiyat = document.getString("fiyat")
                        val ilanTarih = document.getString("tarih")
                        val tamamlananIlan = TamamlananIlani(id, soforAdSoyad,ilanNereden , ilanNereye, ilanOlculer, ilanFiyat, ilanTarih)
                        tamamlananList.add(tamamlananIlan)
                    }
                    adapter.setData(tamamlananList)
                }
                .addOnFailureListener { exception ->

                    Toast.makeText(this, "Verileri çekerken hata oluştu", Toast.LENGTH_SHORT).show()
                }
        }
    }


