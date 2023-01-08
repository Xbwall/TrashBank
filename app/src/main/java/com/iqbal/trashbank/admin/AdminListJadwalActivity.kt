package com.iqbal.trashbank.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.iqbal.trashbank.R
import com.iqbal.trashbank.adapter.AdapterAdminListJP
import com.iqbal.trashbank.app.ApiConfig
import com.iqbal.trashbank.model.ResponseDelJP
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

        val id_user = intent.extras?.getString("iduser")
        Log.d("HOME","id_user = "+id_user)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(this)

        ApiConfig.instance.listJP().enqueue(object : Callback<ArrayList<ResponseListJP>>{
            override fun onResponse(
                call: Call<ArrayList<ResponseListJP>>,
                response: Response<ArrayList<ResponseListJP>>
            ) {
                val listResponse = response.body()
                listResponse?.let { list.addAll(it) }
                val adp = AdapterAdminListJP(list)
                recyclerview.adapter = adp
                adp.setOnItemClick(object :AdapterAdminListJP.OnAdapterListener{
                    //content Click
                    override fun OnCLick(list: ResponseListJP) {
                        val intent = Intent(this@AdminListJadwalActivity,AdminFormJPActivity::class.java)
                        intent.putExtra("id_jadwalpengambilan",list.id.toString())
                        intent.putExtra("tgl_jadwalpengambilan",list.tanggal)
                        intent.putExtra("iduser",id_user)
                        startActivity(intent)
                    }
                    //icon delete klick
                    override fun IconDeleteClick(list: ResponseListJP) {
                        hitDeleteJP(list.id)
                    }
                })
            }

            override fun onFailure(call: Call<ArrayList<ResponseListJP>>, t: Throwable) {
                Log.e("ERR",t.message.toString())
            }

        })

        btn_insertJP.setOnClickListener {
            val intent = Intent(this, AdminFormJPActivity::class.java)
            intent.putExtra("iduser",id_user)
            startActivity(intent)
        }
    }

    fun hitDeleteJP(id:Int){
        ApiConfig.instance.deleteJP(id)
            .enqueue(object : Callback<ResponseDelJP>
            {
                override fun onResponse(call: Call<ResponseDelJP>, response: Response<ResponseDelJP>)
                {
                    val respon = response.body()!!
                    startActivity(Intent(this@AdminListJadwalActivity,AdminListJadwalActivity::class.java))
                    finish()
                    Toast.makeText(this@AdminListJadwalActivity, "Telah dihapus", Toast.LENGTH_SHORT).show()
                    Log.d("HSL",respon.Message.toString())
                }

                override fun onFailure(call: Call<ResponseDelJP>, t: Throwable) {
                    Log.e("ERR",t.message.toString())
                }

            })
    }
}