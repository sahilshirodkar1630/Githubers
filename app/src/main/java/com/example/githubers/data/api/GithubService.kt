package com.example.githubers.data.api

import com.example.githubers.data.models.UserResponse
import com.example.mvvm.data.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id:String = "abc"): Response<User>

    @GET("search/users")
    suspend fun searchUser(@Query("q")name: String): Response<UserResponse>
}