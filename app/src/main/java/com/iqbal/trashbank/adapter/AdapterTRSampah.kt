package com.iqbal.trashbank.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.iqbal.trashbank.databinding.ItemJenisSampahBinding
import com.iqbal.trashbank.helper.Constant
import com.iqbal.trashbank.helper.SharedPref
import com.iqbal.trashbank.model.ModelTRSampah
import com.iqbal.trashbank.user.UserFormPengajuan
import kotlinx.android.synthetic.main.item_jenis_sampah.view.*
import kotlinx.android.synthetic.main.item_jenis_sampah.view.btn_delete
import kotlin.coroutines.coroutineContext


class AdapterTRSampah(val listsampah: ArrayList<ModelTRSampah>,val sharepref:SharedPref): RecyclerView.Adapter<AdapterTRSampah.ViewHolder>() {

    val status = sharepref.getString(Constant.PREF_Status_Pengajuan)

      inner class ViewHolder(itemview : ItemJenisSampahBinding):RecyclerView.ViewHolder(itemview.root){
        fun bind(bind: ModelTRSampah){
            with(itemView){
                itemView.itm_Jenis_Sampah.text = bind.jenisSampah
                itemView.itm_harga_sampah.text = bind.hargaSampah.toString() + "/Kg"
                if (status == "Diajukan" || status == "Diterima" || status == "Ditolak"){
                    itemView.btn_delete.isVisible = false
                    itemView.btn_delete.isEnabled = false
                }else{
                    itemView.btn_delete.setOnClickListener{ onItemClickListener?.iconDeleteClick(bind)}
                }
            }
        }
    }

    private var onItemClickListener: onAdapterListener? = null

    interface onAdapterListener {
        fun iconDeleteClick(list: ModelTRSampah)

    }
        fun setOnItemClick(onItemClickCalback: onAdapterListener){
            this.onItemClickListener = onItemClickCalback
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        ItemJenisSampahBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listsampah[position])
    }

    override fun getItemCount() = listsampah.size

}