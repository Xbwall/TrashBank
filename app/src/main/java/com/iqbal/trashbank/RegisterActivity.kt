package com.iqbal.trashbank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.iqbal.trashbank.app.ApiConfig
import com.iqbal.trashbank.databinding.ActivityRegisterBinding
import com.iqbal.trashbank.helper.SharedPref
import com.iqbal.trashbank.model.ResponseLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var s: SharedPref
    private lateinit var b: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.btnDaftar.setOnClickListener {
            daftar()
        }
        b.txtLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

    }

    private fun daftar() {
        if (b.edtNohp.editText?.text?.isEmpty() == true){
            b.edtNohp.error = "Kolom NoHp Tidak boleh Kosong"
            b.edtNohp.requestFocus()
            return
        }
        if (b.edtPassword.editText?.text?.isEmpty() == true){
            b.edtPassword.error = "Kolom Password Tidak boleh Kosong"
            b.edtPassword.requestFocus()
            return
        }
        if (b.edtNama.editText?.text?.isEmpty() == true){
            b.edtNama.error = "Kolom Nama Tidak boleh Kosong"
            b.edtNama.requestFocus()
            return
        }

        var id_role = 2
        ApiConfig.instance.register(
            b.edtNama.editText?.text.toString(),
            b.edtNohp.editText?.text.toString(),
            b.edtPassword.editText?.text.toString(),
            id_role)
            .enqueue(object : Callback<ResponseLogin>{
                override fun onResponse(call: Call<ResponseLogin>,response: Response<ResponseLogin>
                ) {
                    var respon = response.body()!!
                    startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
                    Toast.makeText(this@RegisterActivity, respon.Message, Toast.LENGTH_SHORT).show()
                }
                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "ERROR" + t.message.toString(), Toast.LENGTH_LONG).show()
                    Log.e("ERROR_login", "========" + t.message.toString())
                }
            })
    }
}