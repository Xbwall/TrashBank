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
import com.iqbal.trashbank.app.ApiConfig
import com.iqbal.trashbank.helper.Constant
import com.iqbal.trashbank.helper.SharedPref
import com.iqbal.trashbank.model.ResponseDelJP
import com.iqbal.trashbank.model.ResponseListJP
import kotlinx.android.synthetic.main.activity_admin_list_jadwal.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminListJadwalActivity : AppCompatActivity() {

    private val list = ArrayList<ResponseListJP>()
    lateinit var s:SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_list_jadwal)

        s = SharedPref(this)
        val id_user = s.getString(Constant.PREF_ID_USER)

        val loading = ProgressDialog(this)
        loading.setMessage("Loading...")
        loading.setCancelable(false)
        loading.show()

        btn_insertJP.setOnClickListener {
            startActivity(Intent(this, AdminFormJPActivity::class.java))
        }

        loadList(id_user.toString(),loading)

    }

    fun loadList(idusr:String,loading:ProgressDialog){

        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(this)

        ApiConfig.instance.listJP().enqueue(object : Callback<ArrayList<ResponseListJP>>{
            override fun onResponse(
                call: Call<ArrayList<ResponseListJP>>,
                response: Response<ArrayList<ResponseListJP>>
            ) {
                val listResponse = response.body()
                list.clear()
                recyclerview.adapter?.notifyDataSetChanged()
                listResponse?.let { list.addAll(it) }
                val adp = AdapterAdminListJP(list)
                recyclerview.adapter = adp
                loading.hide()

                adp.setOnItemClick(object :AdapterAdminListJP.OnAdapterListener{
                    //content Click
                    override fun OnCLick(list: ResponseListJP) {
                        val intent = Intent(this@AdminListJadwalActivity,AdminFormJPActivity::class.java)
                        s.putString(Constant.PREF_ID_JP,list.id.toString())
                        s.putString(Constant.PREF_TANGGAL_JP,list.tanggal.toString())
                        startActivity(intent)
                    }
                    //icon delete klick
                    override fun IconDeleteClick(list: ResponseListJP) {
                        hitDeleteJP(list.id,idusr,loading)
                    }
                })
            }

            override fun onFailure(call: Call<ArrayList<ResponseListJP>>, t: Throwable) {
                Log.e("ERR",t.message.toString())
                loading.hide()
            }
        })
    }

    fun hitDeleteJP(id:Int,idusr:String,loading:ProgressDialog){
        ApiConfig.instance.deleteJP(id)
            .enqueue(object : Callback<ResponseDelJP>
            {
                override fun onResponse(call: Call<ResponseDelJP>, response: Response<ResponseDelJP>)
                {
                    val respon = response.body()!!
                    if(respon.StatusCode == 1){
                        loadList(idusr,loading)
                        loading.hide()
                        Toast.makeText(this@AdminListJadwalActivity, respon.Message, Toast.LENGTH_SHORT).show()
                        Log.d("HSL",respon.Message.toString())
                    }else{
                        loading.hide()
                        Toast.makeText(this@AdminListJadwalActivity, respon.Message, Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<ResponseDelJP>, t: Throwable) {
                    Log.e("ERR",t.message.toString())
                    loading.hide()
                }
            })
    }
}