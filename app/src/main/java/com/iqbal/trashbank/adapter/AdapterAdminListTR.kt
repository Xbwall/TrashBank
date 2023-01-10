package com.iqbal.trashbank.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iqbal.trashbank.R
import com.iqbal.trashbank.model.ResponseListJP
import com.iqbal.trashbank.model.ResponseListTR
import kotlinx.android.synthetic.main.item_carry.view.*
import kotlinx.android.synthetic.main.item_transaksi.view.*
import kotlinx.android.synthetic.main.item_transaksi.view.itm_tanggaltransaksi

class AdapterAdminListTR(val list: ArrayList<ResponseListTR>): RecyclerView.Adapter<AdapterAdminListTR.ViewHolder>() {

    private var onItemClickListener: onAdapterListener? = null

    interface onAdapterListener {
        fun onClick(list: ResponseListTR)
        fun iconDeleteClick(list: ResponseListTR)

    }

    fun setOnItemClick(onItemClickCalback: onAdapterListener){
        this.onItemClickListener = onItemClickCalback
    }

    inner class ViewHolder(itemview : View):RecyclerView.ViewHolder(itemview) {
        fun bind(role : ResponseListTR){
            with(itemView) {
                itemView.itm_namaTR.text = "Nama Warga : "+role.nama
                itemView.itm_nominal.text = "Nominal Bayar : Rp. "+role.nominal.toString()
                itemView.itm_tgl_pengambilan.text = "Tanggal Pengambilan : "+role.tanggal
                itemView.itm_tanggaltransaksi.text = "Tanggal Transaksi : "+role.tanggal_transaksi
                itemView.contentTR.setOnClickListener { onItemClickListener?.onClick(role) }
                itemView.btn_deleteTR.setOnClickListener{onItemClickListener?.iconDeleteClick(role)}
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item_transaksi,parent,false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

}
