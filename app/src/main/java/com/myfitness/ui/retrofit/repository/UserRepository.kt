package com.myfitness.ui.retrofit.repository

import com.myfitness.ui.booking_list.model.User
import com.myfitness.ui.booking_list.model.UserApiResponse
import com.myfitness.ui.retrofit.network.ApiService
import com.myfitness.ui.retrofit.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    private val apiService: ApiService = RetrofitService.apiService

    fun getUsers(page: Int, results: Int, callback: (List<User>?, Throwable?) -> Unit) {
        apiService.getUsers(page, results).enqueue(object : Callback<UserApiResponse> {
            override fun onResponse(
                call: Call<UserApiResponse>,
                response: Response<UserApiResponse>
            ) {
                if (response.isSuccessful) {
                    val userApiResponse = response.body()
                    val users = userApiResponse?.results
                    callback(users, null)
                } else {
                    val error = Throwable("API error: ${response.code()}")
                    callback(null, error)
                }
            }

            override fun onFailure(call: Call<UserApiResponse>, t: Throwable) {
                callback(null, t)
            }
        })
    }
}
