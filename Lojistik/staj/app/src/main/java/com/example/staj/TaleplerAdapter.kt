package com.example.staj


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaleplerAdapter : RecyclerView.Adapter<TaleplerAdapter.TalepViewHolder>() {

    private var talepList: List<Taleps> = emptyList()
    private var onOnaylaClickListener: ((Taleps) -> Unit)? = null
    private var onReddetClickListener: ((Taleps) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalepViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_talep1, parent, false)
        return TalepViewHolder(view)
    }

    override fun onBindViewHolder(holder: TalepViewHolder, position: Int) {
        val talep = talepList[position]

        // Talep verilerini görüntüle
        holder.talepEmail.text = "Nereden: ${talep.soforEmail}"
        holder.talepNereden.text = "Nereden: ${talep.ilanNereden}"
        holder.talepNereye.text = "Nereye: ${talep.ilanNereye}"
        holder.talepOlculer.text = "Ölçüler: ${talep.ilanOlculer}"
        holder.talepFiyat.text = "Fiyat: ${talep.ilanFiyat}"
        holder.talepTarih.text = "Tarih: ${talep.ilanTarih}"

        // Onayla ve Reddet butonlarına tıklama işlemleri
        holder.onaylaButton.setOnClickListener {
            onOnaylaClickListener?.invoke(talep)
        }

        holder.reddetButton.setOnClickListener {
            onReddetClickListener?.invoke(talep)
        }
    }

    override fun getItemCount(): Int {
        return talepList.size
    }

    fun setData(data: List<Taleps>) {
        talepList = data
        notifyDataSetChanged()
    }

    fun setOnOnaylaClickListener(listener: (Taleps) -> Unit) {
        onOnaylaClickListener = listener
    }

    fun setOnReddetClickListener(listener: (Taleps) -> Unit) {
        onReddetClickListener = listener
    }

    inner class TalepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val talepEmail: TextView = itemView.findViewById(R.id.talepEmail)
        val talepNereden: TextView = itemView.findViewById(R.id.talepNereden)
        val talepNereye: TextView = itemView.findViewById(R.id.talepNereye)
        val talepOlculer: TextView = itemView.findViewById(R.id.talepOlculer)
        val talepFiyat: TextView = itemView.findViewById(R.id.talepFiyat)
        val talepTarih: TextView = itemView.findViewById(R.id.talepTarih)
        val onaylaButton: TextView = itemView.findViewById(R.id.btnOnayla)
        val reddetButton: TextView = itemView.findViewById(R.id.btnReddet)
    }
}
