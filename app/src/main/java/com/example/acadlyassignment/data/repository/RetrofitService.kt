package com.example.acadlyassignment.data.repository

import com.example.acadlyassignment.data.model.ApiRequest
import com.example.acadlyassignment.data.model.ApiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {

    @POST("scheduled-task-handler")
    fun hitApi(@Body params: ApiRequest): Call<ApiResponse>
}