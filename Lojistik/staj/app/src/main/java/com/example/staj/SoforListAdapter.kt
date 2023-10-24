package com.example.staj
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.staj.Sofor // Uygulamanızın paket adını ve R sınıfını kullanmalısınız

class SoforListAdapter : RecyclerView.Adapter<SoforListAdapter.SoforViewHolder>() {

    private var soforList: List<Sofor> = emptyList()
    private var onSoforClickListener: ((Sofor) -> Unit)? = null

    fun setData(soforList: List<Sofor>) {
        this.soforList = soforList
        notifyDataSetChanged()
    }

    fun setOnSoforClickListener(listener: (Sofor) -> Unit) {
        onSoforClickListener = listener
    }

    inner class SoforViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val kullaniciAdiTextView: TextView = itemView.findViewById(R.id.kullaniciAdiTextView)
        val adSoyadTextView: TextView = itemView.findViewById(R.id.adSoyadTextView)
        val aracTipiTextView: TextView = itemView.findViewById(R.id.aracTipiTextView)
        val plakaTextView: TextView = itemView.findViewById(R.id.plakaTextView)
        val telefonTextView: TextView = itemView.findViewById(R.id.telefonTextView)
        val silButton: Button = itemView.findViewById(R.id.silButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoforViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_sofor, parent, false)
        return SoforViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SoforViewHolder, position: Int) {
        val currentSofor = soforList[position]

        holder.kullaniciAdiTextView.text = currentSofor.kullaniciAdi
        holder.adSoyadTextView.text = currentSofor.adSoyad
        holder.aracTipiTextView.text = currentSofor.aracTipi
        holder.plakaTextView.text = currentSofor.plaka
        holder.telefonTextView.text = currentSofor.telefon

        holder.silButton.setOnClickListener {
            onSoforClickListener?.invoke(currentSofor)
        }
    }

    override fun getItemCount(): Int {
        return soforList.size
    }
}
