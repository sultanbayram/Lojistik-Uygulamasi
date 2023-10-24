package com.example.staj

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MusteriIlanlarimActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MusteriIlanlarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_musteriilanlarim)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        recyclerView = findViewById(R.id.musteriIlanlarRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MusteriIlanlarAdapter()

        recyclerView.adapter = adapter

        loadMusteriIlanlarFromFirestore()

        adapter.setOnIlanClickListener { ilan ->
            if (ilan.id != null) {
                deleteIlanFromFirestore(ilan.id)
            }
        }

    }


    private fun loadMusteriIlanlarFromFirestore() {
        val user = auth.currentUser
        if (user != null) {
            val kullaniciEmail = user.email

            firestore.collection("musteri_ilanlar")
                .whereEqualTo("kullaniciemail", kullaniciEmail)
                .get()
                .addOnSuccessListener { result ->
                    val ilanList = mutableListOf<MusteriIlan>()
                    for (document in result) {
                        val id = document.id
                        val nereden = document.getString("nereden")
                        val nereye = document.getString("nereye")
                        val olculer = document.getString("olculer")
                        val fiyat = document.getString("fiyati")

                        val ilan = MusteriIlan(id, nereden, nereye, olculer, fiyat)
                        ilanList.add(ilan)
                    }
                    adapter.setData(ilanList)

                }
                .addOnFailureListener { exception ->
                    // İlanları çekerken hata oluştu
                    Toast.makeText(this, "İşleri çekerken hata oluştu", Toast.LENGTH_SHORT).show()
                }
        }
    }


private fun deleteIlanFromFirestore(ilanId: String) {
        firestore.collection("musteri_ilanlar").document(ilanId)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "İlan başarıyla silindi", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                loadMusteriIlanlarFromFirestore() // Verileri güncelle
            }
            .addOnFailureListener {
                // İlan silme işlemi başarısız oldu
                Toast.makeText(this, "İlan silinirken bir hata oluştu", Toast.LENGTH_SHORT).show()
            }
    }
}
