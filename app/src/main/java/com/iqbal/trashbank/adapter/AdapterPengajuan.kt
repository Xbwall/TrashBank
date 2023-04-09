package com.iqbal.trashbank.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iqbal.trashbank.databinding.ItemPengajuanBinding
import com.iqbal.trashbank.model.ModelListPengajuan
import com.iqbal.trashbank.model.ResponseListTR
import kotlinx.android.synthetic.main.item_pengajuan.view.*

class AdapterPengajuan(val listPengajuan : ArrayList<ModelListPengajuan>): RecyclerView.Adapter<AdapterPengajuan.ViewHolder>() {
    inner class ViewHolder(itemview: ItemPengajuanBinding):RecyclerView.ViewHolder(itemview.root) {
        fun bind(bind:ModelListPengajuan){
            with(itemView){
                itemView.itm_tanggalpengajuan.text = bind.tanggalPengajuan
                itemView.itm_statuspengajuan.text = bind.status
                itemView.btn_delete.setOnClickListener{ onItemClickListener?.iconDeleteClick(bind)}
            }
        }
    }

    private var onItemClickListener: onAdapterListener? = null

    interface onAdapterListener {
        fun iconDeleteClick(list: ModelListPengajuan)

    }

    fun setOnItemClick(onItemClickCalback: onAdapterListener){
        this.onItemClickListener = onItemClickCalback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        ItemPengajuanBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPengajuan[position])
    }

    override fun getItemCount() = listPengajuan.size

}