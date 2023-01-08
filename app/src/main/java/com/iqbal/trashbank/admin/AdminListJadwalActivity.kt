package com.iqbal.trashbank.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.iqbal.trashbank.R
import com.iqbal.trashbank.adapter.AdapterAdminList
import com.iqbal.trashbank.app.ApiConfig
import com.iqbal.trashbank.model.ResponseListJP
import kotlinx.android.synthetic.main.activity_admin_list_jadwal.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminListJadwalActivity : AppCompatActivity() {

    private val list = ArrayList<ResponseListJP>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_list_jadwal)

        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(this)

        ApiConfig.instance.listJP().enqueue(object : Callback<ArrayList<ResponseListJP>>{
            override fun onResponse(
                call: Call<ArrayList<ResponseListJP>>,
                response: Response<ArrayList<ResponseListJP>>
            ) {
                val listResponse = response.body()
                listResponse?.let { list.addAll(it) }
                val adp = AdapterAdminList(list)
                recyclerview.adapter = adp
                adp.setOnItemClick(object :AdapterAdminList.OnAdapterListener{
                    override fun OnCLick(list: ResponseListJP) {
                        Toast.makeText(this@AdminListJadwalActivity, list.tanggal, Toast.LENGTH_SHORT).show()
                        Log.d("HSL","TESTING")
                    }
                })
            }

            override fun onFailure(call: Call<ArrayList<ResponseListJP>>, t: Throwable) {
                Log.e("ERR",t.message.toString())
            }

        })

        btn_insertJP.setOnClickListener {
            startActivity(Intent(this, AdminFormJPActivity::class.java))
        }
    }
}