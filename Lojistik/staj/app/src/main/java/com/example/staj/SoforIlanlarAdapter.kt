package com.example.staj

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



    class SoforIlanlarAdapter : RecyclerView.Adapter<SoforIlanlarAdapter.IlanViewHolder>() {

        private var ilanList: List<SIlan> = emptyList()
        private var onTalepClickListener: ((SIlan) -> Unit)? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IlanViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ilan_sofor, parent, false)
            return IlanViewHolder(view)
        }

        override fun onBindViewHolder(holder: IlanViewHolder, position: Int) {
            val ilan = ilanList[position]

            // İlan verilerini görüntüle

            holder.txtEmail.text = "Nereden: ${ilan.soforEmail}"
            holder.txtNereden.text = "Nereden: ${ilan.ilanNereden}"
            holder.txtNereye.text = "Nereye: ${ilan.ilanNereye}"
            holder.txtOlculer.text = "Ölçüler: ${ilan.ilanOlculer}"
            holder.txtFiyat.text = "Fiyat: ${ilan.ilanFiyat}"
            holder.txtTarih.text = "Tarih: ${ilan.ilanTarih}"

            // Talep Et butonuna tıklama işleyicisini ayarla
            holder.btnTalepEt.setOnClickListener {
                onTalepClickListener?.invoke(ilan)
            }
        }

        override fun getItemCount(): Int {
            return ilanList.size
        }

        fun setData(data: List<SIlan>) {
            ilanList = data
            notifyDataSetChanged()
        }

        fun setOnTalepClickListener(listener: (SIlan) -> Unit) {
            onTalepClickListener = listener
        }

        inner class IlanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val txtEmail: TextView = itemView.findViewById(R.id.txtEmail)
            val txtNereden: TextView = itemView.findViewById(R.id.txtNereden)
            val txtNereye: TextView = itemView.findViewById(R.id.txtNereye)
            val txtOlculer: TextView = itemView.findViewById(R.id.txtOlculer)
            val txtFiyat: TextView = itemView.findViewById(R.id.txtFiyat)
            val txtTarih: TextView = itemView.findViewById(R.id.txtTarih)
            val btnTalepEt: Button = itemView.findViewById(R.id.btnTalepEt)
        }
    }
