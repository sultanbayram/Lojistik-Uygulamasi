package com.example.staj

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class IlanDuzenleActivity : AppCompatActivity() {

    private lateinit var txtnereden: TextView
    private lateinit var txtnereye: TextView
    private lateinit var txtolculer: TextView
    private lateinit var txtfiyat: TextView
    private lateinit var fiyatBilgiEditText: EditText
    private lateinit var database: FirebaseFirestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ilanduzenle)


        txtnereden = findViewById(R.id.txtnereden)
        txtnereye = findViewById(R.id.txtnereye)
        txtolculer = findViewById(R.id.txtolculer)
        txtfiyat = findViewById(R.id.txtfiyat)
        fiyatBilgiEditText = findViewById(R.id.fiyatBilgiEditText)

        database = FirebaseFirestore.getInstance()


        txtnereden.text = intent.extras?.getString("nereden")
        txtnereye.text = intent.extras?.getString("nereye")
        txtolculer.text = intent.extras?.getString("olculer")
        txtfiyat.text = intent.extras?.getString("fiyat")


        fiyatBilgiEditText = findViewById(R.id.fiyatBilgiEditText)
    }

    // Paylaş butonuna tıklanınca çalışacak fonksiyon
    fun paylas(view: View) {
        val user = FirebaseAuth.getInstance().currentUser?.email.toString()
        val tarih = Timestamp.now().toDate()
        val nereden = txtnereden.text.toString()
        val nereye = txtnereye.text.toString()
        val olculer = txtolculer.text.toString()

        val ilanFiyat = fiyatBilgiEditText.text.toString()

        val postHashMap = hashMapOf<String, Any>()

        postHashMap["kullaniciemail"] = user
        postHashMap["nereden"] = nereden
        postHashMap["nereye"] = nereye
        postHashMap["olculer"] = olculer
        postHashMap["fiyati"] = ilanFiyat
        postHashMap["tarih"] = tarih

        database.collection("paylasilan_ilanlar")
            .add(postHashMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "İlan başarıyla paylaşıldı.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
    }
}








