package dev.qori.githubusers.api

import dev.qori.githubusers.models.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users/{username}")
    fun getUser(
        @Path("username") id: String
    ): Call<UserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") id: String
    ): Call<UserResponse>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") id: String
    ): Call<UserResponse>

    @GET("users")
    fun searchUser(
        @Query("username") id: String
    ): Call<UserResponse>
}