package com.iqbal.trashbank.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iqbal.trashbank.databinding.ItemPengajuanAdminBinding
import com.iqbal.trashbank.model.ModelListPengajuanAdmin
import kotlinx.android.synthetic.main.item_pengajuan_admin.view.*

class AdapterAdminListPengajuan(val list : ArrayList<ModelListPengajuanAdmin>): RecyclerView.Adapter<AdapterAdminListPengajuan.ViewHolder>() {
    inner class ViewHolder(itemview: ItemPengajuanAdminBinding):RecyclerView.ViewHolder(itemview.root){
        fun bind(bind : ModelListPengajuanAdmin){
            with(itemView){
                itemView.itm_nama_nasabah.text = bind.nama
                itemView.itm_tanggalpengajuan.text = bind.tanggalPengajuan
                itemView.itm_statuspengajuan.text = bind.status
                itemView.content.setOnClickListener { onItemClickListener?.onClick(bind) }
            }
        }
    }

    private var onItemClickListener: onAdapterListener? = null

    interface onAdapterListener {
        fun onClick(list: ModelListPengajuanAdmin)
    }

    fun setOnItemClick(onItemClickCalback: onAdapterListener){
        this.onItemClickListener = onItemClickCalback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        ItemPengajuanAdminBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

}