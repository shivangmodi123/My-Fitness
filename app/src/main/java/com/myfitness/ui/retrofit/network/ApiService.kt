package com.myfitness.ui.retrofit.network

import com.myfitness.ui.booking_list.model.UserApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/")
    fun getUsers(
        @Query("page") page: Int,
        @Query("results") results: Int
    ): Call<UserApiResponse>
}