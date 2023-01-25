package com.iqbal.trashbank.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.iqbal.trashbank.R
import com.iqbal.trashbank.adapter.AdapterUserListJP
import com.iqbal.trashbank.app.ApiConfig
import com.iqbal.trashbank.model.ResponseListJP
import kotlinx.android.synthetic.main.activity_user_list_jadwal.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserListJadwalActivity : AppCompatActivity() {

    private val list = ArrayList<ResponseListJP>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list_jadwal)

        userrecyclerview.setHasFixedSize(true)
        userrecyclerview.layoutManager = LinearLayoutManager(this)

        ApiConfig.instance.listJP().enqueue(object : Callback<ArrayList<ResponseListJP>>{
            override fun onResponse(
                call: Call<ArrayList<ResponseListJP>>,
                response: Response<ArrayList<ResponseListJP>>
            ) {
                val respon = response.body()!!
                respon.let { list.addAll(it) }
                val adp = AdapterUserListJP(list)
                userrecyclerview.adapter = adp
            }
            override fun onFailure(call: Call<ArrayList<ResponseListJP>>, t: Throwable) {
                Log.e("ERR",t.message.toString())
            }
        })
    }
}