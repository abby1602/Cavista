package com.cavista.cavista.data

data class ApiResponseModel(
    val `data`: List<Data>,
    val status: Int,
    val success: Boolean
)