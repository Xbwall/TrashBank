package com.iqbal.trashbank.app

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

    @DELETE("deletepengajuan/{id}")
    fun deletepengajuan(
        @Path("id") id:Int
    ):Call<ModelListPengajuan>
}