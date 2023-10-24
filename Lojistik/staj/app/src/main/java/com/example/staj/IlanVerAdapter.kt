package com.example.staj

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class IlanVerAdapter(val ilanList: ArrayList<Ilan>, param: (Any, Any) -> Unit) : RecyclerView.Adapter<IlanVerAdapter.PostHolder>() {

    class PostHolder(viewGroup: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_ilan, viewGroup, false)) {

        private val txtkullanici by lazy { itemView.findViewById<TextView>(R.id.recycler_row_kullanici_emaili) }
        private val nereden by lazy { itemView.findViewById<TextView>(R.id.recycler_row_nereden) }
        private val nereye by lazy { itemView.findViewById<TextView>(R.id.recycler_row_nereye) }
        private val olculer by lazy { itemView.findViewById<TextView>(R.id.recycler_row_olculer) }
        private val fiyat by lazy { itemView.findViewById<TextView>(R.id.recycler_row_fiyat) }


        private var email = FirebaseAuth.getInstance().currentUser?.email.toString()

        fun bindTo(ilan: Ilan, onItemClick: (view: View, newsDTO: Ilan) -> Unit) {
            //databseden gelene veriyi kendi uygulamammızın içerisine burda yediriyoruz
            txtkullanici.text = ilan.kullaniciEmail
            nereden.text = ilan.nereden
            nereye.text = ilan.nereye
            olculer.text = ilan.olculer
            fiyat.text = ilan.fiyat


            itemView.setOnClickListener {
                val intent = Intent(it.context, IlanDuzenleActivity::class.java)
                intent.putExtra("kullaniciEmail", ilan.kullaniciEmail)
                intent.putExtra("nereden", ilan.nereden)
                intent.putExtra("nereye", ilan.nereye)
                intent.putExtra("olculer", ilan.olculer)
                intent.putExtra("fiyat", ilan.fiyat)
                // Diğer verileri burada da ekleyebilirsiniz
                it.context.startActivity(intent)
                onItemClick(it, ilan)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_ilan, parent, false)
        return PostHolder(parent)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val kullaniciTextView = holder.itemView.findViewById<TextView>(R.id.recycler_row_kullanici_emaili)
        val neredenTextView = holder.itemView.findViewById<TextView>(R.id.recycler_row_nereden)
        val nereyeTextView = holder.itemView.findViewById<TextView>(R.id.recycler_row_nereye)
        val olculerTextView = holder.itemView.findViewById<TextView>(R.id.recycler_row_olculer)
        val fiyatTextView = holder.itemView.findViewById<TextView>(R.id.recycler_row_fiyat)

        kullaniciTextView.text = ilanList[position].kullaniciEmail
        neredenTextView.text = ilanList[position].nereden
        nereyeTextView.text = ilanList[position].nereye
        olculerTextView.text = ilanList[position].olculer
        fiyatTextView.text = ilanList[position].fiyat

        holder.bindTo(ilanList[position]) { view, newsDTO ->
            onItemClick(view, newsDTO)
        }
    }


    private fun onItemClick(view: View, newsDTO: Ilan) {
        // Tıklama işlemlerini burada gerçekleştirebilirsiniz
    }

    override fun getItemCount(): Int {
        return ilanList.size
    }
}


