package com.grhuan.trabalho01

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.grhuan.trabalho01.databinding.ActivityDashbordBinding
import com.grhuan.trabalho01.databinding.ActivityRegister2Binding

class DashbordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var binding: ActivityDashbordBinding = ActivityDashbordBinding.inflate(layoutInflater)
        setContentView(binding.root)

       val name  = intent.getStringExtra("name")

        binding.dysplay.text = "Bem vindo, ${name}"
    }
}