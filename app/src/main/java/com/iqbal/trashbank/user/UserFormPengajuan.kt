package com.iqbal.trashbank.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iqbal.trashbank.databinding.ActivityUserFormPengajuanBinding

class UserFormPengajuan : AppCompatActivity() {
    private lateinit var b:ActivityUserFormPengajuanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityUserFormPengajuanBinding.inflate(layoutInflater)
        setContentView(b.root)

    }
}