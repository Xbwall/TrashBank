package com.iqbal.trashbank.app

import com.iqbal.trashbank.login.ResponseLogin
import com.iqbal.trashbank.model.ResponseDelJP
import com.iqbal.trashbank.model.ResponseListJP
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("nohp") nohp: String,
        @Field("password") password: String
    ): Call<ResponseLogin>

    @GET("listJP")
    fun listJP(): Call<ArrayList<ResponseListJP>>

    @DELETE("deleteJP/{id}")
    fun deleteJP(
        @Path("id") id:Int
    ):Call<ResponseDelJP>

    @FormUrlEncoded
    @POST("insertJP")
    fun insertJP(
        @Field("tanggal") tgl:String,
        @Field("id_pengurus") id_pengurus:Int
    ):Call<ResponseDelJP>
}