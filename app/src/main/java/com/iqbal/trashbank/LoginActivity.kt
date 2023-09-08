package com.iqbal.trashbank

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.iqbal.trashbank.admin.HomeAdminActivity
import com.iqbal.trashbank.app.ApiConfig
import com.iqbal.trashbank.databinding.ActivityLoginBinding
import com.iqbal.trashbank.helper.Constant
import com.iqbal.trashbank.helper.SharedPref
import com.iqbal.trashbank.model.ResponseLogin
import com.iqbal.trashbank.user.HomeUserActivity
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private var statusLogin = false
    private lateinit var s: SharedPref
    private lateinit var b: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(b.root)

        s = SharedPref(this)

        btn_login.setOnClickListener {
            login()
        }
        b.txtDaftar.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }

    fun login(){
        if (edt_nohp.editText?.text?.isEmpty() == true){
            edt_nohp.error = "Kolom NoHp Tidak boleh Kosong"
            edt_nohp.requestFocus()
            return
        }
        if (edt_password.editText?.text?.isEmpty() == true){
            edt_password.error = "Kolom Password Tidak boleh Kosong"
            edt_password.requestFocus()
            return
        }

        val loading = ProgressDialog(this)
        loading.setMessage("Loading...")
        loading.setCancelable(false)
        loading.show()

        ApiConfig.instance.login(edt_nohp.editText?.text.toString(), edt_password.editText?.text.toString())
            .enqueue(object : Callback<ResponseLogin> {
                override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                    val respon = response.body()!!
                    if (respon.Success == 1 ){
                        s.putString(Constant.PREF_ID_USER,respon.id_user.toString())
                        s.putString(Constant.PREF_NAMA,respon.nama.toString())
                        s.putString(Constant.PREF_ID_ROLE,respon.id_role.toString())
                        s.putString(Constant.PREF_ROLE_NAME,respon.role_name.toString())
                        s.putString(Constant.PREF_TELEPON,respon.nohp.toString())
                        s.putString(Constant.PREF_SALDO,respon.saldo.toString())

                        val role = respon.id_role
                        if(role == 1){
                            val intent = Intent(this@LoginActivity, HomeAdminActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            loading.hide()
                            Toast.makeText(this@LoginActivity, "Selamat Datang Di aplikasi Bank Sampah,"+respon.role_name+", "+respon.nama, Toast.LENGTH_SHORT).show()
                        }else{
                            val intent = Intent(this@LoginActivity, HomeUserActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            loading.hide()
                            Toast.makeText(this@LoginActivity, "Selamat Datang Di aplikasi Bank Sampah"+respon.role_name+", "+respon.nama, Toast.LENGTH_SHORT).show()
                        }
                    }else {
                        Toast.makeText(this@LoginActivity, "ERROR" + respon.Message, Toast.LENGTH_LONG).show()
                        Log.e("ERROR_login", "========" + respon.Message.toString())
                        loading.hide()
                    }
                }
                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "ERROR:" + t.message, Toast.LENGTH_LONG).show()
                    Log.e("ERROR_login", "========" + t.message.toString())
                    loading.hide()
                }
            })
    }
}