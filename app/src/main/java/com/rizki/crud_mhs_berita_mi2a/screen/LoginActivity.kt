package com.rizki.crud_mhs_berita_mi2a.screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rizki.crud_mhs_berita_mi2a.MainActivity
import com.rizki.crud_mhs_berita_mi2a.R
import com.rizki.crud_mhs_berita_mi2a.model.LoginResponse
import com.rizki.crud_mhs_berita_mi2a.model.RegisterResponse
import com.rizki.crud_mhs_berita_mi2a.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        //deklarasi widget
        val etUsername: EditText = findViewById(R.id.etUsername)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val txtToRegister: TextView = findViewById(R.id.txtToRegister)

        //ketika dia berhasil login --> pindah ke page berita
        //akan tambah 1 menu untuk tambah data
        //kita akan buat list data
        //proses delete data


        //ketik teks belum punya akun di klik, maka akan ke page registe
        txtToRegister.setOnClickListener() {
            val toRegister = Intent(this@LoginActivity, RegisterScreenActivity::class.java)
            startActivity(toRegister)
        }
        btnLogin.setOnClickListener() {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            //validasi input kosong
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this@LoginActivity, "Username atau password tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                ApiClient.retrofit.login(username, password).enqueue(
                    object : Callback<LoginResponse> {

                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            if (response.isSuccessful) {
                                val loginResponse = response.body()
                                if(loginResponse != null && response.isSuccessful){
                                    //Login berhasil
                                    Toast.makeText(
                                        this@LoginActivity, "Login Success",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    //mau pindah ke page lain
                                    val toMain = Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(toMain)

                                }else{
                                    //Login gagal
                                    Toast.makeText(
                                        this@LoginActivity, "Login gagal",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            } else {
                                val errorMessage = response.errorBody()?.string() ?: "Unknown Error"
                                Log.e("Login Error", errorMessage)
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Login Failure",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(
                                this@LoginActivity, t.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                )
            }
        }




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}