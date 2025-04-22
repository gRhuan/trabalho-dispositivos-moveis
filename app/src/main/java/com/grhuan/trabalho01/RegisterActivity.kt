package com.grhuan.trabalho01

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.grhuan.trabalho01.api.ApiService
import com.grhuan.trabalho01.databinding.ActivityRegisterBinding
import com.grhuan.trabalho01.dto.RegisterRequest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //importante
        var baseUrl = "http://trabalho-two-do.duckdns.org:3333/"

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var binding: ActivityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val mail = binding.editTextEmail.text.toString().trim()
            val pass = binding.editTextPassword.text.toString().trim()
            val rpass = binding.editTextRepeatPassword.text.toString().trim()
            val msgError = binding.msgError

            if (pass == rpass) {

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

                        val login = Intent(this@RegisterActivity, MainActivity::class.java)
                        startActivity(login)

                    } catch (e: HttpException) {
                        when (e.code()) {
                            400 -> {
                                msgError.text = "Email já cadastrado"
                                msgError.visibility = View.VISIBLE
                            }

                            else -> Log.e("LoginError", "inesperado: ${e.code()}")
                        }
                    }
                }
            } else{
                msgError.text="As senhas não coincidem"
                msgError.visibility = View.VISIBLE
            }
        }
    }
}