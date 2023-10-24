package com.example.staj

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MusteriIlanVerActivity : AppCompatActivity() {

    private lateinit var edtNereden: EditText
    private lateinit var edtNereye: EditText
    private lateinit var edtOlculer: EditText
    private lateinit var edtilanFiyat: EditText
    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_musteriilanver)

        database = FirebaseFirestore.getInstance()

        edtNereden = findViewById(R.id.ilanNeredenEditText)
        edtNereye = findViewById(R.id.ilanNereyeEditText)
        edtOlculer = findViewById(R.id.OlculerEditText)
        edtilanFiyat = findViewById(R.id.ilanFiyatEditText)
    }

    fun paylas(view: View) {
        val user = FirebaseAuth.getInstance().currentUser?.email.toString()
        val tarih = Timestamp.now().toDate()
        val nereden = edtNereden.text.toString()
        val nereye = edtNereye.text.toString()
        val olculer = edtOlculer.text.toString()
        val ilanFiyat = edtilanFiyat.text.toString()

        val postHashMap = hashMapOf<String, Any>()

        postHashMap["kullaniciemail"] = user
        postHashMap["nereden"] = nereden
        postHashMap["nereye"] = nereye
        postHashMap["olculer"] = olculer
        postHashMap["fiyati"] = ilanFiyat
        postHashMap["tarih"] = tarih
        database.collection("musteri_ilanlar")
            .add(postHashMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "İlan başarıyla paylaşıldı.", Toast.LENGTH_SHORT).show()
                    finish() // Activity'yi kapat
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
    }
    private val TAG = "MusteriIlanVerActivity"

    private fun getAllUsers() {
        database.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}





/* override fun onCreate(savedInstanceState: Bundle?) {
     super.onCreate(savedInstanceState)
     setContentView(R.layout.activity_musteriilanver)

     firestore = FirebaseFirestore.getInstance()
     auth = FirebaseAuth.getInstance()
     val neredenEditText: EditText = findViewById(R.id.neredenEditText)
     val nereyeEditText: EditText = findViewById(R.id.nereyeEditText)
     val olculerEditText: EditText = findViewById(R.id.olculerEditText)
     val fiyatEditText: EditText = findViewById(R.id.fiyatEditText)
     val milanVerButton: Button = findViewById(R.id.musteriilanVerButton)

     milanVerButton.setOnClickListener {
         val kullanici = auth.currentUser
         if (kullanici != null) {
             val kullaniciId = kullanici.uid
             val nereden = neredenEditText.text.toString()
             val nereye = nereyeEditText.text.toString()
             val olculer = olculerEditText.text.toString()
             val fiyat = fiyatEditText.text.toString()

             val ilanMap = hashMapOf(
                 "kullaniciId" to kullaniciId,
                 "nereden" to nereden,
                 "nereye" to nereye,
                 "olculer" to olculer,
                 "fiyat" to fiyat
             )

             firestore.collection("musteri_ilanlar")
                 .add(ilanMap)
                 .addOnSuccessListener {
                     // İlan başarıyla eklendi
                     Toast.makeText(this, "İlan başarıyla eklendi", Toast.LENGTH_SHORT).show()
                     setResult(RESULT_OK)
                     finish()
                 }
                 .addOnFailureListener {
                     // İlan ekleme işlemi başarısız oldu
                     Toast.makeText(this, "İlan eklenirken bir hata oluştu", Toast.LENGTH_SHORT).show()
                 }
         }
     }
 }*/


