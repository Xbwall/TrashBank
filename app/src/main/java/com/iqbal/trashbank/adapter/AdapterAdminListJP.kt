package com.iqbal.trashbank.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iqbal.trashbank.R
import com.iqbal.trashbank.model.ResponseListJP
import kotlinx.android.synthetic.main.item_carry.view.*

class AdapterAdminListJP(val list: ArrayList<ResponseListJP>):RecyclerView.Adapter<AdapterAdminListJP.ViewHolder>() {

    private var onItemClickCallback: OnAdapterListener? = null

    fun setOnItemClick(onItemClickCallback: OnAdapterListener){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(itemview : View):RecyclerView.ViewHolder(itemview) {
        fun bind(role: ResponseListJP){
            with(itemView){
                itemView.itm_tanggaltransaksi.text = role.tanggal
                itemView.content.setOnClickListener { onItemClickCallback?.OnCLick(role) }
                itemView.btn_delete.setOnClickListener { onItemClickCallback?.IconDeleteClick(role) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.item_carry,parent,false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnAdapterListener{
        fun OnCLick(list : ResponseListJP)

        fun IconDeleteClick(list : ResponseListJP)

    }


}