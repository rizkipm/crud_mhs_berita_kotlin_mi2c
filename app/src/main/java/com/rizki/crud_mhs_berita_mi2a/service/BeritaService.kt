package com.rizki.crud_mhs_berita_mi2a.service

import com.rizki.crud_mhs_berita_mi2a.model.ResponseBerita
import retrofit2.Call
import retrofit2.http.GET

interface BeritaService {
    @GET("getBerita.php")
    fun getAllBerita() : Call<ResponseBerita>
}