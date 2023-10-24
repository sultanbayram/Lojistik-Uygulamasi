package com.example.staj
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class SoforlerActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SoforListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soforler)

        firestore = FirebaseFirestore.getInstance()

        recyclerView = findViewById(R.id.soforlerRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SoforListAdapter()

        recyclerView.adapter = adapter

        loadSoforlerFromFirestore()

        adapter.setOnSoforClickListener { sofor ->
            deleteSoforFromFirestore(sofor.id)
        }
    }

    private fun loadSoforlerFromFirestore() {
        firestore.collection("sofor")
            .get()
            .addOnSuccessListener { result ->
                val soforList = mutableListOf<Sofor>()
                for (document in result) {
                    val id = document.id
                    val kullaniciAdi = document.getString("kullaniciAdi")
                    val adSoyad = document.getString("adSoyad")
                    val aracTipi = document.getString("aracTipi")
                    val plaka = document.getString("plaka")
                    val telefon = document.getString("telefon")
                    val tcKimlikNo = document.getString("tcKimlikNo")

                    val sofor = Sofor(id, kullaniciAdi, adSoyad, aracTipi, plaka, telefon, tcKimlikNo)
                    soforList.add(sofor)
                }
                adapter.setData(soforList)
            }
            .addOnFailureListener { exception ->
                // Firestore'dan veri çekme işlemi başarısız oldu
            }
    }

    private fun deleteSoforFromFirestore(soforId: String) {
        firestore.collection("sofor").document(soforId)
            .delete()
            .addOnSuccessListener {
                // Sofor başarıyla silindi
                loadSoforlerFromFirestore() // Verileri güncelle
            }
            .addOnFailureListener {
                // Sofor silme işlemi başarısız oldu
            }
    }
}
