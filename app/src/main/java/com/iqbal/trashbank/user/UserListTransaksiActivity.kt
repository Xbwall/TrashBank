package com.iqbal.trashbank.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.iqbal.trashbank.R
import com.iqbal.trashbank.adapter.AdapterUserListJP
import com.iqbal.trashbank.adapter.AdapterUserListTR
import com.iqbal.trashbank.app.ApiConfig
import com.iqbal.trashbank.model.ResponseListJP
import com.iqbal.trashbank.model.ResponseListTR
import kotlinx.android.synthetic.main.activity_user_list_jadwal.*
import kotlinx.android.synthetic.main.activity_user_list_transaksi.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserListTransaksiActivity : AppCompatActivity() {

    private val list = ArrayList<ResponseListTR>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list_transaksi)

        userrecyclerviewTR.setHasFixedSize(true)
        userrecyclerviewTR.layoutManager = LinearLayoutManager(this)

        ApiConfig.instance.ListTR().enqueue(object : Callback<ArrayList<ResponseListTR>>{
            override fun onResponse(
                call: Call<ArrayList<ResponseListTR>>,
                response: Response<ArrayList<ResponseListTR>>
            ) {
                val respon = response.body()!!
                respon.let { list.addAll(it) }
                val adp = AdapterUserListTR(list)
                userrecyclerviewTR.adapter = adp
            }

            override fun onFailure(call: Call<ArrayList<ResponseListTR>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}