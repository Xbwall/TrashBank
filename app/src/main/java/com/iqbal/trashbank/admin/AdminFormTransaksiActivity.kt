package com.iqbal.trashbank.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.iqbal.trashbank.R
import com.iqbal.trashbank.adapter.AdapterAdminListJP
import com.iqbal.trashbank.app.ApiConfig
import com.iqbal.trashbank.model.DropdownWG
import com.iqbal.trashbank.model.ResponseDelJP
import com.iqbal.trashbank.model.ResponseListJP
import com.iqbal.trashbank.model.ResponseListWG
import kotlinx.android.synthetic.main.activity_admin_form_transaksi.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AdminFormTransaksiActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    //variabel dropdown jadwal pengambilan
    private val listIdJP = ArrayList<Int>()
    private val listTanggalJP = ArrayList<String>()

    //variabel dropdown masyarakat
    private val listIdWarga = ArrayList<Int>()
    private val listNamaWarga = ArrayList<String>()

    private var id_jp:Int = 0
    private var id_wg:Int = 0

    //variabel tampung data get untuk update
    val id_trnsksi = intent.extras?.getString("id_transaksi")
    val nominal = intent.extras?.getString("nominal")
    val id_masyarakat = intent.extras?.getString("id_masyarakat")
    val id_jadwal_pengambilan = intent.extras?.getString("id_jadwal_pengambilan")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_form_transaksi)

        initSpinnerJadwal()
        initSpinnerWarga()

        if(id_trnsksi !== null){
            spinnerJadwal.setSelection(getIndex(listIdJP,id_jadwal_pengambilan!!.toInt()))
            spinnerPenduduk.setSelection(getIndex(listIdWarga,id_masyarakat!!.toInt()))
        }

        btn_save.setOnClickListener {
            if(id_trnsksi == null){
                insert(id_jp,id_wg,edt_nominal.text.toString())
            }else{
                update(id_trnsksi!!.toInt(),id_jp,id_wg,edt_nominal.text.toString())
            }
        }
    }

    private fun getIndex(list:ArrayList<Int>,idjp: Int):Int{
        val index = list.indexOf(idjp)
        return index
    }

    private fun insert(id_jp:Int,id_wg:Int,nominal:String){
        ApiConfig.instance.insertTR(nominal,id_wg,id_jp).enqueue(object :Callback<ResponseDelJP>{
            override fun onResponse(call: Call<ResponseDelJP>, response: Response<ResponseDelJP>) {
                startActivity(Intent(this@AdminFormTransaksiActivity,AdminListTransaksiActivity::class.java))
                finish()
                Toast.makeText(this@AdminFormTransaksiActivity,response.body()?.Message,Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ResponseDelJP>, t: Throwable) {
                Log.e("ERR",t.message.toString())
                Toast.makeText(this@AdminFormTransaksiActivity,t.message.toString(),Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun update(id_tr:Int,id_jp:Int,id_wg:Int,nominal:String){
        ApiConfig.instance.updateTR(id_tr,nominal,id_wg,id_jp).enqueue(object :Callback<ResponseDelJP>{
            override fun onResponse(call: Call<ResponseDelJP>, response: Response<ResponseDelJP>) {
                startActivity(Intent(this@AdminFormTransaksiActivity,AdminListTransaksiActivity::class.java))
                finish()
                Toast.makeText(this@AdminFormTransaksiActivity,response.body()?.Message,Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ResponseDelJP>, t: Throwable) {
                Log.e("ERR",t.message.toString())
                Toast.makeText(this@AdminFormTransaksiActivity,t.message.toString(),Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun initSpinnerJadwal() {
        ApiConfig.instance.listJP().enqueue(object : Callback<ArrayList<ResponseListJP>> {
            override fun onResponse(
                call: Call<ArrayList<ResponseListJP>>,
                response: Response<ArrayList<ResponseListJP>>
            ) {
                val res = response.body()
                res?.forEach {
                    listIdJP.add(it.id)
                    listTanggalJP.add(it.tanggal.toString())
                }

                spinnerJadwal.onItemSelectedListener = this@AdminFormTransaksiActivity
                val adpt =ArrayAdapter(this@AdminFormTransaksiActivity,android.R.layout.simple_spinner_dropdown_item, listTanggalJP)
                spinnerJadwal.adapter = adpt

            }

            override fun onFailure(call: Call<ArrayList<ResponseListJP>>, t: Throwable) {
                Log.e("ERR",t.message.toString())
            }

        })
    }

    private fun initSpinnerWarga() {
        ApiConfig.instance.listWG().enqueue(object : Callback<ArrayList<ResponseListWG>> {
            override fun onResponse(
                call: Call<ArrayList<ResponseListWG>>,
                response: Response<ArrayList<ResponseListWG>>
            ) {
                val res = response.body()
                res?.forEach {
                    listIdWarga.add(it.id)
                    listNamaWarga.add(it.nama.toString())
                }

                spinnerPenduduk.onItemSelectedListener = this@AdminFormTransaksiActivity
                val adpt =ArrayAdapter(this@AdminFormTransaksiActivity,android.R.layout.simple_spinner_dropdown_item, listNamaWarga)
                spinnerPenduduk.adapter = adpt

            }

            override fun onFailure(call: Call<ArrayList<ResponseListWG>>, t: Throwable) {
                Log.e("ERR",t.message.toString())
            }

        })
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        p0?.getItemAtPosition(p2)
        if(p0?.selectedItem == spinnerJadwal.selectedItem){
            id_jp = listIdJP[p2]
            Log.e("select","selected id_jp = "+id_jp.toString())
        }else if(p0?.selectedItem == spinnerPenduduk.selectedItem){
            id_wg = listIdWarga[p2]
            Log.e("select","selected id_wg = "+id_wg.toString())
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}