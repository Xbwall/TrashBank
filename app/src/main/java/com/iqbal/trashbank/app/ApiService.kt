package com.iqbal.trashbank.app

import android.view.Display.Mode
import com.iqbal.trashbank.model.ResponseLogin
import com.iqbal.trashbank.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

//=============================LOGIN=======================================

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("nohp") nohp: String,
        @Field("password") password: String
    ): Call<ResponseLogin>

    @FormUrlEncoded
    @POST("insertUsers")
    fun register(
        @Field("nama") nama: String,
        @Field("nohp") nohp: String,
        @Field("password") password: String,
        @Field("id_role") id_role: Int

    ): Call<ResponseLogin>

    @GET("listWG")
    fun listWG():Call<ArrayList<ResponseListWG>>

//=======================JADWAL PENGAMBILAN================================

    @GET("listJP")
    fun listJP(): Call<ArrayList<ResponseListJP>>

    @POST("deleteJP/{id}")
    fun deleteJP(
        @Path("id") id:Int
    ):Call<ResponseDelJP>

    @FormUrlEncoded
    @POST("insertJP")
    fun insertJP(
        @Field("tanggal") tgl:String,
        @Field("id_pengurus") id_pengurus:Int
    ):Call<ResponseDelJP>

    @FormUrlEncoded
    @POST("updateJP")
    fun updateJP(
        @Field("id") id_jadwal:Int,
        @Field("tanggal") tgl:String,
        @Field("id_pengurus") id_pengurus:Int
    ):Call<ResponseDelJP>

//==========================TRANSAKSI======================================

    @GET("listTR")
    fun ListTR():Call<ArrayList<ResponseListTR>>

    @POST("deleteTR/{id}")
    fun deleteTR(
        @Path("id") id:Int
    ):Call<ResponseDelTR>

    @FormUrlEncoded
    @POST("insertTR")
    fun insertTR(
        @Field("nominal") nominal:String,
        @Field("id_masyarakat") id_masyarakat:Int,
        @Field("id_jadwal_pengambilan") id_jadwal_pengambilan:Int
    ):Call<ResponseDelJP>

    @FormUrlEncoded
    @PUT("updateTR")
    fun updateTR(
        @Field("id_transaksi") id_transaksi:Int,
        @Field("nominal") nominal:String,
        @Field("id_masyarakat") id_masyarakat:Int,
        @Field("id_jadwal_pengambilan") id_jadwal_pengambilan:Int
    ):Call<ResponseDelJP>

//==========================PENGAJUAN======================================
    @FormUrlEncoded
    @POST("createpengajuan")
    fun insertpengajuan(
        @Field("status") status:String,
        @Field("note") note:String,
        @Field("id_masyarakat") id_masyarakat: Int
    ):Call<ModelListPengajuan>

    @FormUrlEncoded
    @POST("userlistpengajuan")
    fun ListPengajuan(
        @Field("id") id_user: Int
    ):Call<ArrayList<ModelListPengajuan>>

    @POST("deletepengajuan/{id}")
    fun deletepengajuan(
        @Path("id") id:Int
    ):Call<ModelListPengajuan>

    //=======================ADMIN===SAMPAH======================================

    @GET("listsampah")
    fun ListSampah():Call<ArrayList<ModelTRSampah>>

    @POST("deletesampah/{id}")
    fun deletesampah(
        @Path("id") id:Int
    ):Call<ModelTRSampah>

    @FormUrlEncoded
    @POST("createsampah")
    fun tambahsampah(
        @Field("jenis_sampah") jenis_sampah:String,
        @Field("harga_sampah") harga_sampah:String
    ):Call<ModelTRSampah>

//=======================USER===PENGAJUAN======================================



    @FormUrlEncoded
    @POST("createTRpengajuan")
    fun transaksiPengajuanSampah(
        @Field("id_pengajuan") id_pengajuan:Int,
        @Field("id_sampah") id_sampah:Int
    ):Call<ModelTRSampah>

    @FormUrlEncoded
    @POST("updatepengajuan")
    fun updatepengajuan(
        @Field("id") id_pengajuan:Int,
        @Field("status") status:String
    ):Call<ModelTRSampah>

    @FormUrlEncoded
    @POST("updatepengajuanadmin")
    fun updatepengajuanAdmin(
        @Field("id") id_pengajuan:Int,
        @Field("status") status:String,
        @Field("note") note:String,
        @Field("total_tr") total_tr:String
    ):Call<ModelTRSampah>

    @POST("TRlistsampah/{id}")
    fun TRListsampah(
        @Path("id") id_pengajuan: Int
    ):Call<ArrayList<ModelTRSampah>>

    @POST("deleteTRsampah/{id}")
    fun deleteTRsampah(
        @Path("id") id:Int
    ):Call<ModelTRSampah>
//===================ADMIN=====PENGAJUAN===============================================
    @GET("adminlistpengajuan")
    fun AdminListPengajuan():Call<ArrayList<ModelListPengajuanAdmin>>

}