package com.example.staj

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SoforIslerimAdapter : RecyclerView.Adapter<SoforIslerimAdapter.IsViewHolder>() {

    private var isList: List<SIlan> = emptyList()
    private var onTamamlandiClickListener: ((SIlan) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_is_ilani, parent, false)
        return IsViewHolder(view)
    }

    override fun onBindViewHolder(holder: IsViewHolder, position: Int) {
        val isilan = isList[position]

        // İş verilerini görüntüle
        holder.isNereden.text = "Nereden: ${isilan.ilanNereden}"
        holder.isNereye.text = "Nereye: ${isilan.ilanNereye}"
        holder.isOlculer.text = "Ölçüler: ${isilan.ilanOlculer}"
        holder.isFiyat.text = "Fiyat: ${isilan.ilanFiyat}"
        holder.isTarih.text = "Tarih: ${isilan.ilanTarih}"

        // Tamamlandı butonuna tıklama işlemi
        holder.tamamlaButton.setOnClickListener {
            onTamamlandiClickListener?.invoke(isilan)
        }
    }

    override fun getItemCount(): Int {
        return isList.size
    }

    fun setData(data: List<SIlan>) {
        isList = data
        notifyDataSetChanged()
    }

    fun setOnTamamlandiClickListener(listener: (SIlan) -> Unit) {
        onTamamlandiClickListener = listener
    }

    inner class IsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val isNereden: TextView = itemView.findViewById(R.id.isNereden)
        val isNereye: TextView = itemView.findViewById(R.id.isNereye)
        val isOlculer: TextView = itemView.findViewById(R.id.isOlculer)
        val isFiyat: TextView = itemView.findViewById(R.id.isFiyat)
        val isTarih: TextView = itemView.findViewById(R.id.isTarih)
        val tamamlaButton: Button = itemView.findViewById(R.id.btnTamamlandi)
    }
}