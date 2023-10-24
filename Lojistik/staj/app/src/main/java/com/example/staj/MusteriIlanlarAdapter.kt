package com.example.staj

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MusteriIlanlarAdapter : RecyclerView.Adapter<MusteriIlanlarAdapter.IlanViewHolder>() {

    private val ilanList: MutableList<MusteriIlan> = mutableListOf()
    private var onIlanClickListener: ((MusteriIlan) -> Unit)? = null

    fun setData(ilanlar: List<MusteriIlan>) {
        ilanList.clear()
        ilanList.addAll(ilanlar)
        notifyDataSetChanged()
    }

    fun setOnIlanClickListener(listener: (MusteriIlan) -> Unit) {
        onIlanClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IlanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_musteri_ilan, parent, false)
        return IlanViewHolder(view)
    }

    override fun onBindViewHolder(holder: IlanViewHolder, position: Int) {
        holder.bind(ilanList[position])
    }

    override fun getItemCount(): Int {
        return ilanList.size
    }

    inner class IlanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val neredenTextView: TextView = itemView.findViewById(R.id.neredenTextView)
        private val nereyeTextView: TextView = itemView.findViewById(R.id.nereyeTextView)
        private val olculerTextView: TextView = itemView.findViewById(R.id.olculerTextView)
        private val fiyatTextView: TextView = itemView.findViewById(R.id.fiyatTextView)

        fun bind(ilan: MusteriIlan) {
            neredenTextView.text = ilan.nereden
            nereyeTextView.text = ilan.nereye
            olculerTextView.text = ilan.olculer
            fiyatTextView.text = ilan.fiyat

            itemView.setOnClickListener {
                onIlanClickListener?.invoke(ilan)
            }
        }
    }
}

