package com.iqbal.trashbank.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iqbal.trashbank.R
import com.iqbal.trashbank.model.ResponseListTR
import kotlinx.android.synthetic.main.activity_admin_list_transaksi.*

class AdminListTransaksiActivity : AppCompatActivity() {

    private val list = ArrayList<ResponseListTR>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_list_transaksi)

        insertTR.setOnClickListener {
            startActivity(Intent(this,AdminFormTransaksiActivity::class.java))
        }


    }
}