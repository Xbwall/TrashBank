package com.iqbal.trashbank.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.iqbal.trashbank.adapter.AdapterPengajuan
import com.iqbal.trashbank.adapter.AdapterTRSampah
import com.iqbal.trashbank.app.ApiConfig
import com.iqbal.trashbank.databinding.ActivityAdminFormPengajuanBinding
import com.iqbal.trashbank.helper.Constant
import com.iqbal.trashbank.helper.SharedPref
import com.iqbal.trashbank.model.ModelListPengajuan
import com.iqbal.trashbank.model.ModelTRSampah
import com.iqbal.trashbank.user.UserFormPengajuan
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminFormPengajuan : AppCompatActivity() {

    lateinit var b : ActivityAdminFormPengajuanBinding
    lateinit var s : SharedPref
    private var list = ArrayList<ModelTRSampah>()
    private var statusFromList:String? = null
    private var notesFromList:String? = null
    private var status:String? = null
    private var id_pengajuan:String? = null
    private var total:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAdminFormPengajuanBinding.inflate(layoutInflater)
        setContentView(b.root)
        s = SharedPref(this)

        id_pengajuan = s.getString(Constant.PREF_ID_PENGAJUAN_ADMIN)
        statusFromList = s.getString(Constant.PREF_Status_Pengajuan)
        notesFromList = s.getString(Constant.PREF_NOTE_PENGAJUAN)
        //set text notes
        if(notesFromList != null){
            b.edtNotes.setText(notesFromList)
        }

        if(statusFromList == "Diterima" || statusFromList == "Ditolak"){
            b.btnDisetujui.isVisible = false
            b.btnDisetujui.isEnabled = false
            b.btnDitolak.isVisible = false
            b.btnDitolak.isEnabled = false
            b.edtNotes.isEnabled = false
        }
        b.txtNamaNasabah.text = s.getString(Constant.PREF_NAMA_NASABAH)
        b.txtTanggalpengajuan.text = s.getString(Constant.PREF_TANGGAL_PENGAJUAN)
        Showform(id_pengajuan)

        b.rcSampahList.setHasFixedSize(true)
        b.rcSampahList.layoutManager = LinearLayoutManager(this)

        b.btnDisetujui.setOnClickListener {
            updateStatus(true)
        }
        b.btnDitolak.setOnClickListener {
            updateStatus(false)
        }

        b.txtListpengajuan.setOnClickListener {
            startActivity(Intent(this,AdminListPengajuan::class.java))
        }

    }

    private fun updateStatus(isApprove: Boolean) {
        if(isApprove == true){
            status = "Diterima"
        }else{
            status = "Ditolak"
        }
        ApiConfig.instance.updatepengajuanAdmin(id_pengajuan!!.toInt(),status.toString(),b.edtNotes.text.toString(),total.toString()).enqueue(object :Callback<ModelTRSampah>{
            override fun onResponse(call: Call<ModelTRSampah>, response: Response<ModelTRSampah>) {
                val res = response.body()!!
                Toast.makeText(this@AdminFormPengajuan, res.Message, Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@AdminFormPengajuan,AdminListPengajuan::class.java))
            }

            override fun onFailure(call: Call<ModelTRSampah>, t: Throwable) {
                Log.e("ERROR",t.message.toString())
            }

        })
    }

    private fun Showform(idPengajuan: String?) {
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

                total = list.fold(0) { acc, list ->
                    acc + list.hargaSampah!!
                }
                b.totalListSampah.text = total.toString()
            }

            override fun onFailure(call: Call<ArrayList<ModelTRSampah>>, t: Throwable) {
                Log.e("ERR",t.message.toString())
            }

        })
    }


}


