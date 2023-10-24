package com.example.staj

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.staj.Musteri


class MusteriListAdapter : RecyclerView.Adapter<MusteriListAdapter.MusteriViewHolder>() {
    private var musteriList: List<Musteri> = emptyList()
    private var onMusteriClickListener: ((Musteri) -> Unit)? = null

    fun setData(musteriList: List<Musteri>) {
        this.musteriList = musteriList
        notifyDataSetChanged()
    }
    fun setOnMusteriClickListener(listener: (Musteri) -> Unit) {
        onMusteriClickListener = listener
    }
    inner class MusteriViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mfirmaAdiTextView: TextView = itemView.findViewById(R.id.mfirmaAdiTextView)
        val mkullaniciAdiTextView: TextView = itemView.findViewById(R.id.mkullaniciAdiTextView)
        val msifreTextView: TextView = itemView.findViewById(R.id.msifreTextView)
        val msilButton: Button = itemView.findViewById(R.id.msilButton)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusteriViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_musteri, parent, false)
        return MusteriViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MusteriViewHolder, position: Int) {
        val currentMusteri = musteriList[position]
        holder.mfirmaAdiTextView.text = currentMusteri.firmaAdi
        holder.mkullaniciAdiTextView.text = currentMusteri.kullaniciAdi
        holder.msifreTextView.text = currentMusteri.sifre

        holder.msilButton.setOnClickListener {
            onMusteriClickListener?.invoke(currentMusteri)
        }
    }
    override fun getItemCount(): Int {
        return musteriList.size
    }
}
