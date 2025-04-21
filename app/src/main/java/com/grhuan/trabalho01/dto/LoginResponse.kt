package com.grhuan.trabalho01.dto

import com.grhuan.trabalho01.domain.User

data class LoginResponse(
    val message: String,
    val user: User
)
