package com.example.staj
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SoforTaleplerimActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SoforTaleplerimAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sofor_taleplerim)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        recyclerView = findViewById(R.id.taleplerRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SoforTaleplerimAdapter()

        recyclerView.adapter = adapter

        loadTaleplerFromFirestore()
    }

    private fun loadTaleplerFromFirestore() {
        val user = auth.currentUser
        if (user != null) {
            val soforEmail = user.email

            firestore.collection("talepler")
                .whereEqualTo("soforEmail", soforEmail)
                .get()
                .addOnSuccessListener { result ->
                    val talepList = mutableListOf<Talep>()
                    for (document in result) {
                        val id = document.id
                        val ilanNereden = document.getString("ilanNereden")
                        val ilanNereye = document.getString("ilanNereye")
                        val ilanOlculer = document.getString("ilanOlculer")
                        val ilanFiyat = document.getString("ilanFiyat")
                        val ilanTarih = document.getString("ilanTarih")
                        val talep = Talep(id, ilanNereden, ilanNereye, ilanOlculer, ilanFiyat, ilanTarih)
                        talepList.add(talep)
                    }
                    adapter.setData(talepList)
                }
                .addOnFailureListener { exception ->
                    // Talepleri çekerken hata oluştu
                }
        }
    }
}
