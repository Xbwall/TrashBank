package com.iqbal.trashbank.admin

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import com.iqbal.trashbank.R
import com.iqbal.trashbank.app.ApiConfig
import com.iqbal.trashbank.helper.Constant
import com.iqbal.trashbank.helper.SharedPref
import com.iqbal.trashbank.model.ResponseDelJP
import kotlinx.android.synthetic.main.activity_admin_form_jpactivity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class AdminFormJPActivity : AppCompatActivity() {

    private lateinit var s: SharedPref

    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_form_jpactivity)

        s = SharedPref(this)

        datedPick!!.text = "YYYY-MM-DD"

        val id_jp = s.getString(Constant.PREF_ID_JP)
        val date_jp = s.getString(Constant.PREF_TANGGAL_JP)
        val id_user = s.getString(Constant.PREF_ID_USER)

        Log.d("HOME","id_user = "+id_user)

        val loading = ProgressDialog(this)

        loading.setMessage("Loading...")
        loading.setCancelable(false)
        loading.show()

        if(id_jp == null){
            datedPick!!.text = "YYYY-MM-DD"
        }else{
            datedPick!!.text = date_jp
        }

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        ChooseDate!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@AdminFormJPActivity, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })

        btn_save.setOnClickListener {
            loading.show()
            if(id_jp == null){
                insertAPI(datedPick.text.toString(),id_user.toString(),loading)
            }else{
                updateAPI(datedPick.text.toString(),id_user.toString(),id_jp.toString(),loading)
            }
        }
        listJP.setOnClickListener {
            startActivity(Intent(this@AdminFormJPActivity,AdminListJadwalActivity::class.java))
            finish()
        }
        loading.hide()
    }

    fun insertAPI(tanggal:String,id_pengurus:String,loading:ProgressDialog){
        ApiConfig.instance.insertJP(tanggal, id_pengurus.toInt())
            .enqueue(object : Callback<ResponseDelJP>
            {
                override fun onResponse(call: Call<ResponseDelJP>, response: Response<ResponseDelJP>)
                {
                    val respon = response.body()!!
                    if(respon.StatusCode == 1){
                        startActivity(Intent(this@AdminFormJPActivity,AdminListJadwalActivity::class.java))
                        finish()
                        Toast.makeText(this@AdminFormJPActivity, respon.Message, Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@AdminFormJPActivity, respon.Message, Toast.LENGTH_SHORT).show()
                    }
                    loading.hide()
                }
                override fun onFailure(call: Call<ResponseDelJP>, t: Throwable) {
                    Log.e("ERR",t.message.toString())
                    loading.hide()
                }
            })
    }

    fun updateAPI(tanggal:String,id_pengurus:String, id_jadwal:String,loading:ProgressDialog){
        ApiConfig.instance.updateJP(id_jadwal.toInt(),tanggal, id_pengurus.toInt())
            .enqueue(object : Callback<ResponseDelJP>
            {
                override fun onResponse(call: Call<ResponseDelJP>, response: Response<ResponseDelJP>)
                {
                    val respon = response.body()!!
                    if(respon.StatusCode == 1){
                        startActivity(Intent(this@AdminFormJPActivity,AdminListJadwalActivity::class.java))
                        finish()
                        Toast.makeText(this@AdminFormJPActivity, respon.Message, Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@AdminFormJPActivity, respon.Message, Toast.LENGTH_SHORT).show()
                    }
                    loading.hide()
                }
                override fun onFailure(call: Call<ResponseDelJP>, t: Throwable) {
                    Log.e("ERR",t.message.toString())
                    loading.hide()
                }
            })
    }

    private fun updateDateInView() {
        val myFormat = "yyyy-MM-DD" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        datedPick!!.text = sdf.format(cal.getTime())
    }
}