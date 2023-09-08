package com.iqbal.trashbank.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.iqbal.trashbank.adapter.AdapterAdminListPengajuan
import com.iqbal.trashbank.adapter.AdapterPengajuan
import com.iqbal.trashbank.app.ApiConfig
import com.iqbal.trashbank.databinding.ActivityAdminListPengajuanBinding
import com.iqbal.trashbank.helper.Constant
import com.iqbal.trashbank.helper.SharedPref
import com.iqbal.trashbank.model.ModelListPengajuan
import com.iqbal.trashbank.model.ModelListPengajuanAdmin
import com.iqbal.trashbank.user.UserFormPengajuan
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminListPengajuan : AppCompatActivity() {

    lateinit var s : SharedPref
    lateinit var b : ActivityAdminListPengajuanBinding
    private val list = ArrayList<ModelListPengajuanAdmin>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAdminListPengajuanBinding.inflate(layoutInflater)
        setContentView(b.root)

        s = SharedPref(this)

        b.rcPengajuan.setHasFixedSize(true)
        b.rcPengajuan.layoutManager = LinearLayoutManager(this)

        ApiConfig.instance.AdminListPengajuan().enqueue(object : Callback<ArrayList<ModelListPengajuanAdmin>>{
            override fun onResponse(
                call: Call<ArrayList<ModelListPengajuanAdmin>>,
                response: Response<ArrayList<ModelListPengajuanAdmin>>
            ) {
                var listresponse = response.body()
                list.clear()
                b.rcPengajuan.adapter?.notifyDataSetChanged()
                listresponse?.let {list.addAll(it)}
                val adp = AdapterAdminListPengajuan(list)
                b.rcPengajuan.adapter = adp

                adp.setOnItemClick(object : AdapterAdminListPengajuan.onAdapterListener{
                    override fun onClick(list: ModelListPengajuanAdmin) {
                        s.putString(Constant.PREF_ID_PENGAJUAN_ADMIN, list.id.toString())
                        s.putString(Constant.PREF_NAMA_NASABAH, list.nama.toString())
                        s.putString(Constant.PREF_ID_MASYARAKAT_ADMIN, list.idMasyarakat.toString())
                        s.putString(Constant.PREF_TANGGAL_PENGAJUAN, list.tanggalPengajuan.toString())
                        s.putString(Constant.PREF_Status_Pengajuan, list.status.toString())
                        s.putString(Constant.PREF_NOTE_PENGAJUAN, list.note.toString())
                        startActivity(Intent(this@AdminListPengajuan, AdminFormPengajuan::class.java))
                    }

                })
            }

            override fun onFailure(call: Call<ArrayList<ModelListPengajuanAdmin>>, t: Throwable) {
                Log.e("ERROR FROM PENGAJUAN = ",t.message.toString())
            }

        })
    }
}