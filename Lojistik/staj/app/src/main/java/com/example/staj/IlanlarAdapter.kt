package com.example.staj
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IlanlarAdapter : RecyclerView.Adapter<IlanlarAdapter.IlanViewHolder>() {

    private val ilanlar = mutableListOf<Ilan>()
    private var onIlanClickListener: ((Ilan) -> Unit)? = null

    fun setData(ilanList: List<Ilan>) {
        ilanlar.clear()
        ilanlar.addAll(ilanList)
        notifyDataSetChanged()
    }

    fun setOnIlanClickListener(listener: (Ilan) -> Unit) {
        onIlanClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IlanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ilan1, parent, false)
        return IlanViewHolder(view)
    }

    override fun onBindViewHolder(holder: IlanViewHolder, position: Int) {
        val ilan = ilanlar[position]
        holder.bind(ilan)
    }

    override fun getItemCount(): Int {
        return ilanlar.size
    }

    inner class IlanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val neredenTextView: TextView = itemView.findViewById(R.id.neredenTextView)
        private val nereyeTextView: TextView = itemView.findViewById(R.id.nereyeTextView)
        private val olculerTextView: TextView = itemView.findViewById(R.id.olculerTextView)
        private val fiyatTextView: TextView = itemView.findViewById(R.id.fiyatTextView)
        private val tarihTextView: TextView = itemView.findViewById(R.id.tarihTextView)

        fun bind(ilan: Ilan) {
            neredenTextView.text = ilan.nereden
            nereyeTextView.text = ilan.nereye
            olculerTextView.text = ilan.olculer
            fiyatTextView.text = ilan.fiyat
            tarihTextView.text = ilan.tarih

            itemView.setOnClickListener {
                onIlanClickListener?.invoke(ilan)
            }
        }
    }
}