package com.iqbal.trashbank.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iqbal.trashbank.databinding.ItemTambahSampahBinding
import com.iqbal.trashbank.model.ModelListPengajuanAdmin
import com.iqbal.trashbank.model.ModelTRSampah

class AdapterListSampah(val list: ArrayList<ModelTRSampah>): RecyclerView.Adapter<AdapterListSampah.ViewHolder>() {
    private var onItemClickListener: onAdapterListener? = null
    inner class ViewHolder(private val binding: ItemTambahSampahBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(bind: ModelTRSampah){
            binding.itmJenisSampah.text = bind.jenisSampah
            binding.itmHargaSampah.text = "Rp "+bind.hargaSampah.toString() + " / Kg"
            binding.btnDelete.setOnClickListener{ onItemClickListener?.iconDeleteClick(bind)}
        }
    }

    interface onAdapterListener {
        fun iconDeleteClick(list: ModelTRSampah)
    }
    fun setOnItemClick(onItemClickCalback: onAdapterListener){
        this.onItemClickListener = onItemClickCalback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val binding = ItemTambahSampahBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}