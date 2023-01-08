package com.iqbal.trashbank.app

import com.iqbal.trashbank.login.ResponseLogin
import com.iqbal.trashbank.model.ResponseListJP
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("nohp") nohp: String,
        @Field("password") password: String
    ): Call<ResponseLogin>

    @GET("listJP")
    fun listJP(): Call<ArrayList<ResponseListJP>>
}