package com.grhuan.trabalho01

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.grhuan.trabalho01.api.ApiService
import com.grhuan.trabalho01.databinding.ActivityMainBinding
import com.grhuan.trabalho01.dto.LoginRequest
import kotlinx.coroutines.launch
import retrofit2.HttpException
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
    binding.btnRegisterHome.setOnClickListener {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

        binding.btnLogin.setOnClickListener {
            val mail = binding.email.text.toString().trim()
            val pass = binding.password.text.toString().trim()
            val msgError = binding.msgError

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

                } catch (e: HttpException) {
                    when(e.code()){
                        404 -> {
                            msgError.text="Credenciais invalidas"
                            msgError.visibility = View.VISIBLE
                        }
                        401 -> {
                            msgError.text ="Credenciais inválidas."
                            msgError.visibility = View.VISIBLE
                        }
                        else -> Log.e("LoginError", "inesperado: ${e.code()}")
                    }
                }
            }
        }
    }
}