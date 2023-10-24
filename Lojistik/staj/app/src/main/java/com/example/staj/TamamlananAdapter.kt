package com.example.staj

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TamamlananAdapter : RecyclerView.Adapter<TamamlananAdapter.TamamlananViewHolder>() {

    private var tamamlananList: List<TamamlananIlani> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TamamlananViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tamamlanan, parent, false)
        return TamamlananViewHolder(view)
    }

    override fun onBindViewHolder(holder: TamamlananViewHolder, position: Int) {
        val tamamlananIlan = tamamlananList[position]

        // Tamamlanan ilan verilerini görüntüle
        holder.tamamlananNereden.text = "Nereden: ${tamamlananIlan.nereden}"
        holder.tamamlananNereye.text = "Nereye: ${tamamlananIlan.nereye}"
        holder.tamamlananOlculer.text = "Ölçüler: ${tamamlananIlan.olculer}"
        holder.tamamlananFiyat.text = "Fiyat: ${tamamlananIlan.fiyat}"
        holder.tamamlananTarih.text = "Tarih: ${tamamlananIlan.tarih}"
        holder.tamamlananSoforEmail.text = "Şoför Email: ${tamamlananIlan.soforEmail}"
    }

    override fun getItemCount(): Int {
        return tamamlananList.size
    }

    fun setData(data: List<TamamlananIlani>) {
        tamamlananList = data
        notifyDataSetChanged()
    }

    inner class TamamlananViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tamamlananNereden: TextView = itemView.findViewById(R.id.tamamlananNereden)
        val tamamlananNereye: TextView = itemView.findViewById(R.id.tamamlananNereye)
        val tamamlananOlculer: TextView = itemView.findViewById(R.id.tamamlananOlculer)
        val tamamlananFiyat: TextView = itemView.findViewById(R.id.tamamlananFiyat)
        val tamamlananTarih: TextView = itemView.findViewById(R.id.tamamlananTarih)
        val tamamlananSoforEmail: TextView = itemView.findViewById(R.id.tamamlananSoforEmail)
    }
}