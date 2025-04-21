package com.grhuan.trabalho01

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.grhuan.trabalho01.api.ApiService
import com.grhuan.trabalho01.databinding.ActivityMainBinding
import com.grhuan.trabalho01.databinding.ActivityRegister2Binding
import com.grhuan.trabalho01.dto.LoginRequest
import com.grhuan.trabalho01.dto.RegisterRequest
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //importante
        var baseUrl = "http://trabalho-two-do.duckdns.org:3333/"

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var binding: ActivityRegister2Binding = ActivityRegister2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            val name = binding.name.text.toString().trim()
            val mail = binding.mail.text.toString().trim()
            val pass = binding.pass.text.toString().trim()

            lifecycleScope.launch {

                try {
                    val retrofit = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    val api = retrofit.create(ApiService::class.java)
                    val request = RegisterRequest(
                        name = name,
                        email = mail,
                        password = pass
                    )

                    val response = api.loginUser(request)
                    Log.d("Login", "Usuário cadastrado")

                } catch (e: Exception) {
                    Log.e("LoginError", "Erro ao fazer login: ${e.message}")
                }
            }
        }
    }
}