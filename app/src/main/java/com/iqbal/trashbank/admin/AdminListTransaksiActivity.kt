package com.iqbal.trashbank.admin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.iqbal.trashbank.R
import com.iqbal.trashbank.adapter.AdapterAdminListJP
import com.iqbal.trashbank.adapter.AdapterAdminListTR
import com.iqbal.trashbank.app.ApiConfig
import com.iqbal.trashbank.helper.Constant
import com.iqbal.trashbank.helper.SharedPref
import com.iqbal.trashbank.model.ResponseDelTR
import com.iqbal.trashbank.model.ResponseListTR
import kotlinx.android.synthetic.main.activity_admin_list_jadwal.*
import kotlinx.android.synthetic.main.activity_admin_list_transaksi.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminListTransaksiActivity : AppCompatActivity() {

    private val list = ArrayList<ResponseListTR>()
    lateinit var s: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_list_transaksi)

        val loading = ProgressDialog(this)

        loading.setMessage("Loading...")
        loading.setCancelable(false)
        loading.show()

        s = SharedPref(this)
        val id_user = s.getString(Constant.PREF_ID_USER)
        Log.d("HOME","id_user = "+id_user)

        insertTR.setOnClickListener {
            startActivity(Intent(this,AdminFormTransaksiActivity::class.java))
        }

        loadlist(loading)

    }

    fun loadlist(loading:ProgressDialog){

        recyclerviewTR.setHasFixedSize(true)
        recyclerviewTR.layoutManager = LinearLayoutManager(this)

        ApiConfig.instance.ListTR().enqueue(object : Callback<ArrayList<ResponseListTR>>{
            override fun onResponse(
                call: Call<ArrayList<ResponseListTR>>,
                response: Response<ArrayList<ResponseListTR>>
            ) {
                val listResponse = response.body()
                list.clear()
                recyclerviewTR.adapter?.notifyDataSetChanged()

                listResponse?.let { list.addAll(it) }
                val adp = AdapterAdminListTR(list)
                recyclerviewTR.adapter = adp

                loading.hide()

                adp.setOnItemClick(object : AdapterAdminListTR.onAdapterListener{
                    override fun onClick(list: ResponseListTR) {
                        s.putString(Constant.PREF_ID_TRANSAKSI, list.id.toString())
                        s.putString(Constant.PREF_NOMINAL,list.nominal.toString())
                        s.putString(Constant.PREF_ID_MASYARAKAT,list.id_masyarakat.toString())
                        s.putString(Constant.PREF_ID_JADWALPENGAMBILAN,list.id_jadwal_pengambilan.toString())
                        s.putString(Constant.PREF_NAMA_TRANSAKSI,list.nama.toString())
                        startActivity(Intent(this@AdminListTransaksiActivity, AdminFormTransaksiActivity::class.java))
                    }

                    override fun iconDeleteClick(list: ResponseListTR) {
                        hitDeleteTR(list.id!!.toInt(),loading)
                    }
                })
            }

            override fun onFailure(call: Call<ArrayList<ResponseListTR>>, t: Throwable) {
                Log.e("ERR",t.message.toString())
            }

        })
    }

    private fun hitDeleteTR(id: Int,loading:ProgressDialog) {
        ApiConfig.instance.deleteTR(id).enqueue(object : Callback<ResponseDelTR>{
            override fun onResponse(call: Call<ResponseDelTR>, response: Response<ResponseDelTR>) {
                val respon = response.body()!!
                if(respon.StatusCode == 1){
                    loadlist(loading)
                    Toast.makeText(this@AdminListTransaksiActivity, respon.Message, Toast.LENGTH_SHORT).show()
                    Log.d("HSL",respon.Message.toString())
                }else{
                    Toast.makeText(this@AdminListTransaksiActivity, respon.Message, Toast.LENGTH_SHORT).show()
                    Log.d("HSL",respon.Message.toString())
                    loading.hide()
                }

            }

            override fun onFailure(call: Call<ResponseDelTR>, t: Throwable) {
                Log.e("ERR",t.message.toString())
                loading.hide()
            }

        })
    }
}