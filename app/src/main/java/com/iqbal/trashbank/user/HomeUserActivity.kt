package com.iqbal.trashbank.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iqbal.trashbank.R
import com.iqbal.trashbank.helper.Constant
import com.iqbal.trashbank.helper.SharedPref
import kotlinx.android.synthetic.main.activity_home_user.*

class HomeUserActivity : AppCompatActivity() {
    lateinit var s: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_user)

        s = SharedPref(this)
        txt_nama_user.text = s.getString(Constant.PREF_NAMA)
        txt_nohp_user.text = s.getString(Constant.PREF_TELEPON)

        crd_jdwluser.setOnClickListener({
            startActivity(Intent(this, UserListJadwalActivity::class.java))
        })

        crd_Riwayattransaksi.setOnClickListener({
            startActivity(Intent(this, UserListTransaksiActivity::class.java))
        })

        crd_tabung.setOnClickListener({
            startActivity(Intent(this, UserTabunganActivity::class.java))
        })
    }
}