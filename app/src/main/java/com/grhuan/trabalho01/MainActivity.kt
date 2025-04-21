package com.grhuan.trabalho01

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.grhuan.trabalho01.api.ApiService
import com.grhuan.trabalho01.databinding.ActivityMainBinding
import com.grhuan.trabalho01.dto.LoginRequest
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    var baseUrl = "http://trabalho-two-do.duckdns.org:3333/"
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    //mudando de tela
    binding.register.setOnClickListener {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

        binding.btn.setOnClickListener {
            val mail = binding.mail.text.toString().trim()
            val pass = binding.pass.text.toString().trim()

            lifecycleScope.launch {

                try {
                    val retrofit = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    val api = retrofit.create(ApiService::class.java)
                    val request = LoginRequest(
                        email = mail,
                        password = pass
                    )
                    val response = api.loginUser(request)

                    val dashbord = Intent(this@MainActivity, DashbordActivity::class.java)
                    dashbord.putExtra("name", response.user.name)
                    startActivity(dashbord)

                    Log.d("Login", "Usuário logado: ${response.user.name}")

                } catch (e: Exception) {


                    //aqui pode colocar uma mensagem de erro para aparecer na tela
                    //se der erro e porque errou a senha ou email
                    Log.e("LoginError", "Erro ao fazer login: ${e.message}")
                }
            }
        }
    }
}