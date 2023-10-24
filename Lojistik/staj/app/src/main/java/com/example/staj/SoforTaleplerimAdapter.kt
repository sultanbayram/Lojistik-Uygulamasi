package com.example.staj
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SoforTaleplerimAdapter : RecyclerView.Adapter<SoforTaleplerimAdapter.TalepViewHolder>() {

    private var talepList: List<Talep> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalepViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_talep, parent, false)
        return TalepViewHolder(view)
    }

    override fun onBindViewHolder(holder: TalepViewHolder, position: Int) {
        val talep = talepList[position]

        // Talep verilerini görüntüle
        holder.talepNereden.text = "Nereden: ${talep.ilanNereden}"
        holder.talepNereye.text = "Nereye: ${talep.ilanNereye}"
        holder.talepOlculer.text = "Ölçüler: ${talep.ilanOlculer}"
        holder.talepFiyat.text = "Fiyat: ${talep.ilanFiyat}"
        holder.talepTarih.text = "Tarih: ${talep.ilanTarih}"
    }

    override fun getItemCount(): Int {
        return talepList.size
    }

    fun setData(data: List<Talep>) {
        talepList = data
        notifyDataSetChanged()
    }

    inner class TalepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val talepNereden: TextView = itemView.findViewById(R.id.talepNereden)
        val talepNereye: TextView = itemView.findViewById(R.id.talepNereye)
        val talepOlculer: TextView = itemView.findViewById(R.id.talepOlculer)
        val talepFiyat: TextView = itemView.findViewById(R.id.talepFiyat)
        val talepTarih: TextView = itemView.findViewById(R.id.talepTarih)
    }
}

