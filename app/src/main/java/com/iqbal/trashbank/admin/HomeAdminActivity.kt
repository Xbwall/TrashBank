package com.iqbal.trashbank.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iqbal.trashbank.LoginActivity
import com.iqbal.trashbank.R
import com.iqbal.trashbank.databinding.ActivityHomeAdminBinding
import com.iqbal.trashbank.databinding.ActivityUserListPengajuanBinding
import com.iqbal.trashbank.helper.Constant
import com.iqbal.trashbank.helper.SharedPref
import kotlinx.android.synthetic.main.activity_home_admin.*

class HomeAdminActivity : AppCompatActivity() {

    private lateinit var s: SharedPref
    private lateinit var b : ActivityHomeAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityHomeAdminBinding.inflate(layoutInflater)
        setContentView(b.root)

        s = SharedPref(this)
        val nama = s.getString(Constant.PREF_NAMA)
        val nohp = s.getString(Constant.PREF_TELEPON)

        b.txtNohpPengurus.setText(nohp.toString())
        b.txtNamaUser.setText(nama.toString())
        welcome.text = "Selamat Datang, "+nama

        logout.setOnClickListener {
            logout()
        }

        crd_tabung.setOnClickListener {
            startActivity(Intent(this@HomeAdminActivity, AdminListPengajuan::class.java))
        }
        crd_listjadwalpengambilan.setOnClickListener {
            startActivity(Intent(this@HomeAdminActivity, AdminListJadwalActivity::class.java))
        }

        crd_transaksi.setOnClickListener {
            startActivity(Intent(this@HomeAdminActivity, AdminListTransaksiActivity::class.java))
        }
        crd_tambah_sampah.setOnClickListener{
            startActivity(Intent(this@HomeAdminActivity, AdminListSampah::class.java))
        }
    }

    fun logout(){
        s.setStatusLogin(false)
        val intent = Intent(this@HomeAdminActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}

