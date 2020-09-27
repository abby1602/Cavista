package com.cavista.cavista

import com.cavista.cavista.data.ApiResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiInterface {



    @GET("search/1")
    fun loadImageList(@Query("q") query : String
        ,@Header("Authorization") token : String) : Call<ApiResponseModel>
}