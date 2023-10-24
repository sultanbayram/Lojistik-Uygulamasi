package com.example.staj
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class IlanlarimActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: IlanlarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ilanlarim)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        recyclerView = findViewById(R.id.IlanlarRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = IlanlarAdapter()

        recyclerView.adapter = adapter

        loadIlanlarFromFirestore()

        adapter.setOnIlanClickListener { ilan ->
            if (ilan.id != null) {
                deleteIlanFromFirestore(ilan.id)
            }
        }
    }

    private fun loadIlanlarFromFirestore() {
        val user = auth.currentUser
        if (user != null) {
            val kullaniciEmail = user.email

            firestore.collection("paylasilan_ilanlar")
                .whereEqualTo("kullaniciemail", kullaniciEmail)
                .get()
                .addOnSuccessListener { result ->
                    val ilanList = mutableListOf<Ilan>()
                    for (document in result) {
                        val id = document.id
                        val nereden = document.getString("nereden")
                        val nereye = document.getString("nereye")
                        val olculer = document.getString("olculer")
                        val fiyat = document.getString("fiyati")
                        val tarihTimestamp = document.getTimestamp("tarih")
                        val tarih = tarihTimestamp?.toDate().toString()
                        val ilan = Ilan(id, nereden, nereye, olculer, fiyat, tarih)
                        ilanList.add(ilan)
                    }
                    adapter.setData(ilanList)
                }
                .addOnFailureListener { exception ->
                    // İlanları çekerken hata oluştu
                    Toast.makeText(this, "İlanları çekerken hata oluştu", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun deleteIlanFromFirestore(ilanId: String) {
        firestore.collection("paylasilan_ilanlar").document(ilanId)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "İlan başarıyla silindi", Toast.LENGTH_SHORT).show()
                // İlan silindi, gerekiyorsa ek işlemler yapılabilir.
                loadIlanlarFromFirestore() // Verileri güncelle
            }
            .addOnFailureListener {
                // İlan silme işlemi başarısız oldu
                Toast.makeText(this, "İlan silinirken bir hata oluştu", Toast.LENGTH_SHORT).show()
            }
    }
}
