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
import com.iqbal.trashbank.model.ResponseDelTR
import com.iqbal.trashbank.model.ResponseListTR
import kotlinx.android.synthetic.main.activity_admin_list_jadwal.*
import kotlinx.android.synthetic.main.activity_admin_list_transaksi.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminListTransaksiActivity : AppCompatActivity() {

    private val list = ArrayList<ResponseListTR>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_list_transaksi)

        val loading = ProgressDialog(this)

        loading.setMessage("Loading...")
        loading.setCancelable(false)
        loading.show()

        val id_user = intent.extras?.getString("iduser")
        Log.d("HOME","id_user = "+id_user)

        insertTR.setOnClickListener {
            startActivity(Intent(this,AdminFormTransaksiActivity::class.java))
            intent.putExtra("iduser",id_user)
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
                        val intent = Intent(this@AdminListTransaksiActivity, AdminFormTransaksiActivity::class.java)
                        intent.putExtra("id_transaksi", list.id.toString())
                        intent.putExtra("nominaltransaksi",list.nominal.toString())
                        //intent.putExtra("tanggaltransaksi",list.tanggal_transaksi)
                        intent.putExtra("id_warga",list.id_masyarakat.toString())
                        intent.putExtra("id_jadwalpengambilan",list.id_jadwal_pengambilan.toString())
                        intent.putExtra("nama",list.nama)
                        //intent.putExtra("iduser",id_user)
                        startActivity(intent)
                    }

                    override fun iconDeleteClick(list: ResponseListTR) {
                        hitDeleteTR(list.id)
                    }
                })
            }

            override fun onFailure(call: Call<ArrayList<ResponseListTR>>, t: Throwable) {
                Log.e("ERR",t.message.toString())
            }

        })
    }

    private fun hitDeleteTR(id: Int) {
        ApiConfig.instance.deleteTR(id).enqueue(object : Callback<ResponseDelTR>{
            override fun onResponse(call: Call<ResponseDelTR>, response: Response<ResponseDelTR>) {
                val respon = response.body()!!
                startActivity(Intent(this@AdminListTransaksiActivity,AdminListTransaksiActivity::class.java))
                finish()
                Toast.makeText(this@AdminListTransaksiActivity, "Telah dihapus", Toast.LENGTH_SHORT).show()
                Log.d("HSL",respon.Message.toString())
            }

            override fun onFailure(call: Call<ResponseDelTR>, t: Throwable) {
                Log.e("ERR",t.message.toString())
            }

        })
    }
}