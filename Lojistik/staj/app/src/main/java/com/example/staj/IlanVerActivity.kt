package com.example.staj

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query



    class IlanVerActivity : AppCompatActivity() {

        private lateinit var auth: FirebaseAuth
        private lateinit var database: FirebaseFirestore
        private lateinit var recyclerViewAdapter: IlanVerAdapter
        private val ilanListesi = ArrayList<Ilan>()
        private var email = ""
        private var geri3: Button? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_ilanver)

            auth = FirebaseAuth.getInstance()
            database = FirebaseFirestore.getInstance()

            email = intent.getStringExtra("email") ?: ""
            Log.d("email", email)

            tanimla()
            verileriAl()

            val layoutManager = LinearLayoutManager(this)
            val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
            recyclerView.layoutManager = layoutManager

            recyclerViewAdapter = IlanVerAdapter(ilanListesi) { view, ilan ->
                onItemClick(view, ilan)
            }
            recyclerView.adapter = recyclerViewAdapter
        }

        private fun verileriAl() {
            database.collection("musteri_ilanlar")
                .orderBy("tarih", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_LONG).show()
                    } else {
                        if (snapshot != null && !snapshot.isEmpty) {
                            ilanListesi.clear()
                            for (document in snapshot.documents) {
                                val id = document.id
                                val kullaniciEmail = document.getString("kullaniciemail") ?: ""
                                val nereden = document.getString("nereden") ?: ""
                                val nereye = document.getString("nereye") ?: ""
                                val olculer = document.getString("olculer") ?: ""
                                val fiyat = document.getString("fiyat") ?: ""
                                val tarihTimestamp = document.getTimestamp("tarih")
                                val tarih = tarihTimestamp?.toDate().toString()
                                val indirilenPost = Ilan(id, kullaniciEmail, nereden, nereye, olculer, fiyat, tarih)
                                ilanListesi.add(indirilenPost)
                            }
                            recyclerViewAdapter.notifyDataSetChanged()
                        }
                    }
                }
        }



        private fun onItemClick(view: Any, ilan: Any) {
            // Tıklama işlemlerini burada gerçekleştirebilirsiniz
        }



        fun tanimla() {

            geri3 = findViewById<View>(R.id.geri3)as Button
            geri3!!.setOnClickListener{
                val intent = Intent(this@IlanVerActivity, PersonelAnaSayfaActivity::class.java)
                startActivity(intent)

            }}
    }