package com.iqbal.trashbank.admin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.iqbal.trashbank.R
import com.iqbal.trashbank.adapter.AdapterAdminListJP
import com.iqbal.trashbank.adapter.AdapterAdminListWG
import com.iqbal.trashbank.app.ApiConfig
import com.iqbal.trashbank.model.DropdownWG
import com.iqbal.trashbank.model.ResponseListJP
import com.iqbal.trashbank.model.ResponseListWG
import kotlinx.android.synthetic.main.activity_admin_form_transaksi.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AdminFormTransaksiActivity : AppCompatActivity() {

    private val list = ArrayList<ResponseListJP>()
    private val listWG = ArrayList<ResponseListWG>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_form_transaksi)

        initSpinnerJadwal()
        initSpinnerWarga()

        spinnerJadwal.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedName = parent.getItemAtPosition(position).toString()
                //mendapatkan piliuhan
                Toast.makeText(this@AdminFormTransaksiActivity, "Kamu memilih jadwal $selectedName", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        spinnerPenduduk.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedName = parent.getItemAtPosition(position).toString()
                //mendapatkan piliuhan
                Toast.makeText(this@AdminFormTransaksiActivity, "Kamu memilih warga $selectedName", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })


    }

    private fun initSpinnerJadwal() {
        ApiConfig.instance.listJP().enqueue(object : Callback<ArrayList<ResponseListJP>> {
            override fun onResponse(
                call: Call<ArrayList<ResponseListJP>>,
                response: Response<ArrayList<ResponseListJP>>
            ) {
                val itemSpin: List<ResponseListJP> = response.body()!!
                val listSpinner: MutableList<String> = ArrayList()
                for (i in itemSpin.indices) {
                    listSpinner.add(itemSpin[i].id.toString()+"-"+itemSpin[i].tanggal.toString())
                }
                val adapter = ArrayAdapter(
                    applicationContext,
                    android.R.layout.simple_spinner_item, listSpinner
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerJadwal.setAdapter(adapter)
                val listResponse = response.body()
                listResponse?.let { list.addAll(it) }
                val adp = AdapterAdminListJP(list)

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
                val itemSpin: List<ResponseListWG> = response.body()!!
                val listSpinner: MutableList<DropdownWG> = ArrayList()
                for (i in itemSpin.indices) {
                    val id = itemSpin[i].id
                    val body = itemSpin[i].nama.toString()
                    val m = DropdownWG(id,body)
                    listSpinner.add(m)
                }
                val adtr = AdapterAdminListWG(this@AdminFormTransaksiActivity,listWG)
                spinnerPenduduk.adapter = adtr

            }

            override fun onFailure(call: Call<ArrayList<ResponseListWG>>, t: Throwable) {
                Log.e("ERR",t.message.toString())
            }

        })
    }
}