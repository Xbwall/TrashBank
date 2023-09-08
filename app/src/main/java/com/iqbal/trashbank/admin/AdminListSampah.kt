package com.iqbal.trashbank.admin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.iqbal.trashbank.adapter.AdapterListSampah
import com.iqbal.trashbank.app.ApiConfig
import com.iqbal.trashbank.databinding.ActivityAdminListSampahBinding
import com.iqbal.trashbank.helper.SharedPref
import com.iqbal.trashbank.model.ModelTRSampah
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminListSampah : AppCompatActivity() {

    lateinit var s : SharedPref
    lateinit var b : ActivityAdminListSampahBinding
    private val list = ArrayList<ModelTRSampah>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAdminListSampahBinding.inflate(layoutInflater)
        setContentView(b.root)

        val loading = ProgressDialog(this)
        loading.setMessage("Loading...")
        loading.setCancelable(false)
        loading.show()

        b.tambahjenissampah.setOnClickListener {
            startActivity(Intent(this,AdminAddSampah::class.java))
        }

        loadlistsampah(loading)
        loading.hide()
    }

    private fun loadlistsampah(loading:ProgressDialog) {
        b.rcJenissampah.setHasFixedSize(true)
        b.rcJenissampah.layoutManager = LinearLayoutManager(this)
        ApiConfig.instance.ListSampah().enqueue(object : Callback<ArrayList<ModelTRSampah>> {
            override fun onResponse(
                call: Call<ArrayList<ModelTRSampah>>,
                response: Response<ArrayList<ModelTRSampah>>
            ) {
                val respon = response.body()!!
                list.clear()
                respon.let { list.addAll(it)}
                val adp = AdapterListSampah(list)
                b.rcJenissampah.adapter = adp
                adp.setOnItemClick(object : AdapterListSampah.onAdapterListener{
                    override fun iconDeleteClick(list: ModelTRSampah) {
                        deletesampah(list.id.toString(),loading)
                    }

                })
                loading.hide()
            }

            override fun onFailure(call: Call<ArrayList<ModelTRSampah>>, t: Throwable) {
                Log.e("ERROR_WARGA",t.message.toString())
                loading.hide()
            }

        })
    }

    private fun deletesampah(idsampah: String, loading: ProgressDialog) {
        ApiConfig.instance.deletesampah(idsampah.toInt()).enqueue(object : Callback<ModelTRSampah>{
            override fun onResponse(call: Call<ModelTRSampah>, response: Response<ModelTRSampah>) {
                Toast.makeText(this@AdminListSampah, response.body()!!.Message, Toast.LENGTH_LONG).show()
                loading.hide()
            }

            override fun onFailure(call: Call<ModelTRSampah>, t: Throwable) {
                Log.e("ERROR_deletesampah",t.message.toString())
                loading.hide()
            }

        })
    }
}