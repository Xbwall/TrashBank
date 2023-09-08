package com.iqbal.trashbank.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.iqbal.trashbank.adapter.AdapterTRSampah
import com.iqbal.trashbank.app.ApiConfig
import com.iqbal.trashbank.databinding.ActivityUserFormPengajuanBinding
import com.iqbal.trashbank.helper.Constant
import com.iqbal.trashbank.helper.SharedPref
import com.iqbal.trashbank.model.ModelTRSampah
import kotlinx.android.synthetic.main.activity_user_form_pengajuan.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserFormPengajuan : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var b:ActivityUserFormPengajuanBinding
    private val listidsampah = ArrayList<Int>()
    private val listnamasampah = ArrayList<String>()


    private var id_sampah :Int = 0

    private var list = ArrayList<ModelTRSampah>()
    private lateinit var id_pengajuan : String
    private lateinit var status : String
    private lateinit var note : String

    lateinit var s : SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityUserFormPengajuanBinding.inflate(layoutInflater)
        setContentView(b.root)

        s = SharedPref(this)
        id_pengajuan = s.getString(Constant.PREF_ID_PENGAJUAN).toString()
        status = s.getString(Constant.PREF_Status_Pengajuan).toString()
        note = s.getString(Constant.PREF_NOTE_PENGAJUAN).toString()

        if(status == "Diajukan" || status == "Diterima" || status == "Ditolak"){
            b.edtNotes.setText(note)
            b.btnMengajukan.isVisible = false
            b.btnMengajukan.isEnabled = false
            b.LnSpin.isVisible = false
            b.txtNotes.isVisible = true
            b.edtNotes.isEnabled = false
        }else{
            b.edtNotes.isVisible = false
            b.edtNotes.isEnabled = false
            b.txtNotes.isVisible = false
        }

        Log.e("ID",id_pengajuan)
        showsampah(id_pengajuan.toInt())

        b.btnAddSampah.setOnClickListener {
            insertsampah(id_pengajuan.toInt(),id_sampah)
        }

        b.btnMengajukan.setOnClickListener {
            updateStatusPengajuan()
            Log.e("IDP",id_pengajuan)
        }

        b.txtListpengajuan.setOnClickListener {
            startActivity(Intent(this,UserListPengajuan::class.java))
        }

        ApiConfig.instance.ListSampah().enqueue(object : Callback<ArrayList<ModelTRSampah>>{
            override fun onResponse(
                call: Call<ArrayList<ModelTRSampah>>,
                response: Response<ArrayList<ModelTRSampah>>
            ) {
                val res = response.body()
                res?.forEach {
                    listidsampah.add(it.id!!)
                    listnamasampah.add(it.jenisSampah!!)
                }

                b.spinnerJenissampah.onItemSelectedListener = this@UserFormPengajuan
                val adapt = ArrayAdapter(this@UserFormPengajuan,android.R.layout.simple_spinner_dropdown_item,listnamasampah)
                b.spinnerJenissampah.adapter = adapt

            }

            override fun onFailure(call: Call<ArrayList<ModelTRSampah>>, t: Throwable) {
                Log.e("ERR",t.message.toString())
            }

        })

    }

    private fun updateStatusPengajuan() {
        var status_update = "Diajukan"
        ApiConfig.instance.updatepengajuan(id_pengajuan.toInt(),status_update).enqueue(object :Callback<ModelTRSampah>{
            override fun onResponse(call: Call<ModelTRSampah>, response: Response<ModelTRSampah>) {
                val res = response.body()!!
                Toast.makeText(this@UserFormPengajuan, res.Message, Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@UserFormPengajuan,UserListPengajuan::class.java))
            }

            override fun onFailure(call: Call<ModelTRSampah>, t: Throwable) {
                Log.e("ERR",t.message.toString())
            }

        })
    }


    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        p0?.getItemAtPosition(p2)
        if(p0?.selectedItem == spinner_jenissampah.selectedItem){
            id_sampah = listidsampah[p2]
        }
    }

    private fun insertsampah(idPengajuan: Int, idsampah: Int) {
            ApiConfig.instance.transaksiPengajuanSampah(idPengajuan,idsampah).enqueue(object : Callback<ModelTRSampah>{
                override fun onResponse(call: Call<ModelTRSampah>, response: Response<ModelTRSampah>) {
                    val res = response.body()!!
                    Toast.makeText(this@UserFormPengajuan, res.Message, Toast.LENGTH_SHORT).show()
                    showsampah(id_pengajuan.toInt())
                }

                override fun onFailure(call: Call<ModelTRSampah>, t: Throwable) {
                    Log.e("ERR",t.message.toString())
                }

            })
    }

    private fun deleteTRSampah(idTr: Int?) {
        ApiConfig.instance.deleteTRsampah(idTr!!.toInt()).enqueue(object :Callback<ModelTRSampah>{
            override fun onResponse(call: Call<ModelTRSampah>, response: Response<ModelTRSampah>) {

                val res = response.body()!!
                Toast.makeText(this@UserFormPengajuan, res.Message, Toast.LENGTH_SHORT).show()
                showsampah(id_pengajuan.toInt())
            }

            override fun onFailure(call: Call<ModelTRSampah>, t: Throwable) {
                Log.e("ERR",t.message.toString())
            }

        })
    }

    private fun showsampah(idPengajuan:Int?) {

        b.rcSampahList.setHasFixedSize(true)
        b.rcSampahList.layoutManager = LinearLayoutManager(this)

        ApiConfig.instance.TRListsampah(idPengajuan!!.toInt()).enqueue(object : Callback<ArrayList<ModelTRSampah>>{
            override fun onResponse(
                call: Call<ArrayList<ModelTRSampah>>,
                response: Response<ArrayList<ModelTRSampah>>
            ) {

                var listresponse = response.body()
                list.clear()

                b.rcSampahList.adapter?.notifyDataSetChanged()
                listresponse?.let {list.addAll(it)}
                val adp = AdapterTRSampah(list,s)
                b.rcSampahList.adapter = adp
                adp.setOnItemClick(object : AdapterTRSampah.onAdapterListener{
                    override fun iconDeleteClick(list: ModelTRSampah) {

                        if(status == "Diajukan"){
                            Toast.makeText(this@UserFormPengajuan, "Status Pengajuan telah diajukan, sedang direview admin", Toast.LENGTH_LONG).show()
                        }else{
                            val id_tr = list.id
                            Log.e("ID_RT", id_tr.toString())
                            deleteTRSampah(id_tr)
                        }
                    }
                })
                val total = list.fold(0) { acc, list ->
                    acc + list.hargaSampah!!
                }
                b.totalListSampah.text = total.toString()
            }

            override fun onFailure(call: Call<ArrayList<ModelTRSampah>>, t: Throwable) {
                Log.e("ERR",t.message.toString())
            }

        })
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}