package com.iqbal.trashbank.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iqbal.trashbank.R
import kotlinx.android.synthetic.main.activity_home_user.*

class HomeUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_user)

        crd_jdwluser.setOnClickListener({
            startActivity(Intent(this, UserListJadwalActivity::class.java))
        })
    }
}