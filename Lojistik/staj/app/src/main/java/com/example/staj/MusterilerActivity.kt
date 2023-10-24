package com.example.staj

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class MusterilerActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MusteriListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_musteriler)

        firestore = FirebaseFirestore.getInstance()

        recyclerView = findViewById(R.id.musterilerRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MusteriListAdapter()

        recyclerView.adapter = adapter

        loadMusterilerFromFirestore()

        adapter.setOnMusteriClickListener { musteri ->
            val nonNullId = musteri.id ?: "" // Null kontrolü
            deleteMusteriFromFirestore(nonNullId)
        }
    }

    private fun loadMusterilerFromFirestore() {
        firestore.collection("musteri")
            .get()
            .addOnSuccessListener { result ->
                val musteriList = mutableListOf<Musteri>()
                for (document in result) {
                    val id = document.id
                    val firmaAdi=document.getString("firmaAdi")
                    val kullaniciAdi = document.getString("kullaniciAdi")
                    val sifre = document.getString("sifre")

                    val musteri = Musteri(id,firmaAdi, kullaniciAdi, sifre)
                    musteriList.add(musteri)
                }
                adapter.setData(musteriList)
            }
            .addOnFailureListener { exception ->
                // Firestore'dan veri çekme işlemi başarısız oldu
            }
    }

    private fun deleteMusteriFromFirestore(musteriId: String) {
        firestore.collection("musteri").document(musteriId)
            .delete()
            .addOnSuccessListener {

                loadMusterilerFromFirestore()
            }
            .addOnFailureListener {
                // Musteri silme işlemi başarısız oldu
            }
    }
}

