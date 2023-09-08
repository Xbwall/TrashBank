package com.iqbal.trashbank.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.iqbal.trashbank.app.ApiConfig
import com.iqbal.trashbank.databinding.ActivityAdminAddSampahBinding
import com.iqbal.trashbank.helper.SharedPref
import com.iqbal.trashbank.model.ModelTRSampah
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminAddSampah : AppCompatActivity() {

    lateinit var binding: ActivityAdminAddSampahBinding
    lateinit var s: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminAddSampahBinding.inflate(layoutInflater)
        setContentView(binding.root)
        s = SharedPref(this)


        binding.txtListsampah.setOnClickListener{
            startActivity(Intent(this, AdminListSampah::class.java))
        }
        binding.btnTambah.setOnClickListener {
            tambahsampah()
        }
    }

    private fun tambahsampah() {
        ApiConfig.instance.tambahsampah(binding.edtJenisSampah.editText?.text.toString(),
            binding.edtHarga.editText?.text.toString()).enqueue(object : Callback<ModelTRSampah>{
            override fun onResponse(call: Call<ModelTRSampah>, response: Response<ModelTRSampah>) {
                var res = response.body()!!
                Toast.makeText(this@AdminAddSampah, res.Message, Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ModelTRSampah>, t: Throwable) {
                Log.e("ERROR",t.message.toString())
            }

        })
    }
}