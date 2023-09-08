package com.iqbal.trashbank.user

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.iqbal.trashbank.adapter.AdapterPengajuan
import com.iqbal.trashbank.app.ApiConfig
import com.iqbal.trashbank.databinding.ActivityUserListPengajuanBinding
import com.iqbal.trashbank.helper.Constant
import com.iqbal.trashbank.helper.SharedPref
import com.iqbal.trashbank.model.ModelListPengajuan
import kotlinx.android.synthetic.main.activity_admin_list_transaksi.*
import kotlinx.android.synthetic.main.activity_user_list_pengajuan.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserListPengajuan : AppCompatActivity() {

    private lateinit var b : ActivityUserListPengajuanBinding
    lateinit var s : SharedPref
    lateinit var id_user: String
    private val list = ArrayList<ModelListPengajuan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityUserListPengajuanBinding.inflate(layoutInflater)
        setContentView(b.root)

        val loading = ProgressDialog(this)

        loading.setMessage("Loading...")
        loading.setCancelable(false)
        loading.show()

        s = SharedPref(this)
        id_user = s.getString(Constant.PREF_ID_USER).toString()


        b.imgInsertpengajuan.setOnClickListener({
            insertpengajuan(id_user)
        })

        loadlist(loading)

    }

    private fun insertpengajuan(idUser: String?) {
        var status = "Belum Diajukan"
        var note = ""
        ApiConfig.instance.insertpengajuan(status,note,idUser.toString().toInt()).enqueue(object : Callback<ModelListPengajuan>{
            override fun onResponse(call: Call<ModelListPengajuan>, response: Response<ModelListPengajuan>) {
                var respon = response.body()!!
                s.putString(Constant.PREF_ID_PENGAJUAN,respon.id_pengajuan.toString())
                startActivity(Intent(this@UserListPengajuan,UserFormPengajuan::class.java))

            }
            override fun onFailure(call: Call<ModelListPengajuan>, t: Throwable) {
                Log.e("ERROR FROM PENGAJUAN = ",t.message.toString())
            }

        })
    }
    private fun loadlist(loading:ProgressDialog){
        b.rcPengajuan.setHasFixedSize(true)
        b.rcPengajuan.layoutManager = LinearLayoutManager(this)

        ApiConfig.instance.ListPengajuan(id_user.toInt()).enqueue(object : Callback<ArrayList<ModelListPengajuan>>{
            override fun onResponse(
                call: Call<ArrayList<ModelListPengajuan>>,
                response: Response<ArrayList<ModelListPengajuan>>
            ) {
                var listresponse = response.body()
                list.clear()
                b.rcPengajuan.adapter?.notifyDataSetChanged()
                listresponse?.let {list.addAll(it)}
                val adp = AdapterPengajuan(list)
                b.rcPengajuan.adapter = adp
                loading.hide()

                adp.setOnItemClick(object : AdapterPengajuan.onAdapterListener{
                    override fun iconDeleteClick(list: ModelListPengajuan) {
                        deletePengajuan(list.id!!.toInt(),loading)
                    }

                    override fun onClick(list: ModelListPengajuan) {
                        s.putString(Constant.PREF_ID_PENGAJUAN,list.id.toString())
                        s.putString(Constant.PREF_Status_Pengajuan,list.status.toString())
                        s.putString(Constant.PREF_NOTE_PENGAJUAN,list.status.toString())
                        startActivity(Intent(this@UserListPengajuan, UserFormPengajuan::class.java))
                    }

                })
            }

            override fun onFailure(call: Call<ArrayList<ModelListPengajuan>>, t: Throwable) {
                Log.e("ERROR.LIST.PENGAJUAN = ",t.message.toString())
            }

        })
    }

    private fun deletePengajuan(id: Int, loading: ProgressDialog) {
        ApiConfig.instance.deletepengajuan(id).enqueue(object : Callback<ModelListPengajuan>{
            override fun onResponse(
                call: Call<ModelListPengajuan>,
                response: Response<ModelListPengajuan>
            ) {
                val respon = response.body()!!
                if(respon.StatusCode == 1){
                    loadlist(loading)
                    Toast.makeText(this@UserListPengajuan,respon.Message, Toast.LENGTH_SHORT).show()
                    Log.d("DELETE HASIL = ",respon.Message.toString())
                }else{
                    Toast.makeText(this@UserListPengajuan,respon.Message, Toast.LENGTH_SHORT).show()
                    Log.d("DELETE HASIL = ",respon.Message.toString())
                    loading.hide()
                }
            }

            override fun onFailure(call: Call<ModelListPengajuan>, t: Throwable) {
                Log.e("ERROR DELETE = ",t.message.toString())
                loading.hide()
            }

        })

    }
}