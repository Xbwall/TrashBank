package com.iqbal.trashbank.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.iqbal.trashbank.R
import com.iqbal.trashbank.model.ResponseListWG
import kotlinx.android.synthetic.main.item_transaksi_admin.view.*

class AdapterAdminListWG(ctx:Context,list:List<ResponseListWG>):ArrayAdapter<ResponseListWG>(ctx,0,list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return super.getView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return super.getDropDownView(position, convertView, parent)
    }

    private fun myView(position: Int, convertView: View?, parent: ViewGroup): View {

        val list = getItem(position)
        val view = convertView?: LayoutInflater.from(context).inflate(R.layout.item_transaksi_admin,parent,false)

        list?.let {
            view.body_warga.text = list.nama.toString()
            view.id_warga.text = list.id.toString()
        }

        return view
    }

}