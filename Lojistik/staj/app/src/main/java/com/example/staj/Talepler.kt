package com.example.staj
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Talepler : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaleplerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talepler)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        recyclerView = findViewById(R.id.taleplerRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TaleplerAdapter()

        recyclerView.adapter = adapter

        loadTaleplerFromFirestore()

        adapter.setOnOnaylaClickListener { talep ->
            // Onayla butonuna tıklanıldığında yapılacak işlemler
            onaylaTalep(talep)
        }

        adapter.setOnReddetClickListener { talep ->
            // Reddet butonuna tıklanıldığında yapılacak işlemler
            reddetTalep(talep)
        }
    }

    private fun loadTaleplerFromFirestore() {
        firestore.collection("talepler")
            .get()
            .addOnSuccessListener { result ->
                val talepList = mutableListOf<Taleps>()
                for (document in result) {
                    val id = document.id
                    val soforAdSoyad = document.getString("soforEmail")
                    val ilanNereden = document.getString("ilanNereden")
                    val ilanNereye = document.getString("ilanNereye")
                    val ilanOlculer = document.getString("ilanOlculer")
                    val ilanFiyat = document.getString("ilanFiyat")
                    val ilanTarih = document.getString("ilanTarih")
                    val talep = Taleps(id, soforAdSoyad, ilanNereden, ilanNereye, ilanOlculer, ilanFiyat, ilanTarih)
                    talepList.add(talep)
                }
                adapter.setData(talepList)
            }
            .addOnFailureListener { exception ->
                // Talepleri çekerken hata oluştu
                Toast.makeText(this, "Talepleri çekerken hata oluştu", Toast.LENGTH_SHORT).show()
            }
    }

    private fun onaylaTalep(talep: Taleps) {
        val user = auth.currentUser
        if (user != null) {
            val soforId = user.uid

            val onaylananTalep = hashMapOf(
                "soforId" to soforId,
                "soforEmail" to talep.soforEmail,
                "ilanNereden" to talep.ilanNereden,
                "ilanNereye" to talep.ilanNereye,
                "ilanOlculer" to talep.ilanOlculer,
                "ilanFiyat" to talep.ilanFiyat,
                "ilanTarih" to talep.ilanTarih
            )

            firestore.collection("onaylanan")
                .add(onaylananTalep)
                .addOnSuccessListener { documentReference ->

                    Toast.makeText(this, "Talep onaylandı.", Toast.LENGTH_SHORT).show()

                    // "talepler" koleksiyonundan ilanı sil
                    firestore.collection("talepler")
                        .document(talep.id!!)
                        .delete()
                        .addOnSuccessListener {
                            // İlan başarıyla silindiğinde yapılacak işlemler
                            Toast.makeText(this, "Talep silindi.", Toast.LENGTH_SHORT).show()
                            loadTaleplerFromFirestore() // Talepleri yeniden yükle
                        }
                        .addOnFailureListener { exception ->
                            // İlan silinemediğinde yapılacak işlemler
                            Toast.makeText(this, "Talep silinemedi.", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener { exception ->
                    // İlan kaydedilemediğinde yapılacak işlemler
                    Toast.makeText(this, "Talep onaylama işlemi başarısız oldu.", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun reddetTalep(talep: Taleps) {
        firestore.collection("talepler")
            .document(talep.id!!)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Talep reddedildi.", Toast.LENGTH_SHORT).show()
                loadTaleplerFromFirestore() // Talepleri yeniden yükle
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Talep reddetme işlemi başarısız oldu.", Toast.LENGTH_SHORT).show()
            }
    }
}

