package com.iqbal.trashbank.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.iqbal.trashbank.R
import com.iqbal.trashbank.app.ApiConfig
import com.iqbal.trashbank.helper.SharedPref
import com.iqbal.trashbank.login.ResponseLogin
import com.iqbal.trashbank.model.ResponseDelJP
import com.iqbal.trashbank.user.HomeUserActivity
import kotlinx.android.synthetic.main.activity_admin_form_jpactivity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminFormJPActivity : AppCompatActivity() {

    private lateinit var s: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_form_jpactivity)

        s = SharedPref(this)

        btn_inputJP.setOnClickListener {

        }
    }

    fun insertAPI(tanggal:String,id_pengurus:Int){
        ApiConfig.instance.insertJP(tanggal, id_pengurus)
            .enqueue(object : Callback<ResponseDelJP>
            {
                override fun onResponse(call: Call<ResponseDelJP>, response: Response<ResponseDelJP>)
                {
                    val respon = response.body()!!
                }

                override fun onFailure(call: Call<ResponseDelJP>, t: Throwable) {

                }

            })
    }
}