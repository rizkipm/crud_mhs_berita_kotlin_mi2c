package com.rizki.crud_mhs_berita_mi2a.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    //http://10.208.104.237:8080/beritaDb/getBerita.php

    private const val BASE_URL = "http://10.208.104.237:8080/beritaDb/"

    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(interceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun interceptor() : OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    val beritaServices : BeritaService by lazy {
        retrofit.create(BeritaService::class.java)
    }

}