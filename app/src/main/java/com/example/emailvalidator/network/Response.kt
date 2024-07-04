package com.example.emailvalidator.network

data class Response(
    val result: String? = null,
    val reason: String? = null,
    val role: Boolean? = null,
    val free: Boolean? = null,
    val disposable: Boolean? = null,
    val acceptAll: Boolean? = null,
    val did_you_mean: String? = null,
    val sendex: Float? = null,
    val email: String? = null,
    val user: String? = null,
    val domain: String? = null,
    val success: Boolean? = null,
    val message: String? = null
)