package dev.qori.githubusers.api

import dev.qori.githubusers.models.SearchResponse
import dev.qori.githubusers.models.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    fun getUserList(): Call<List<UserResponse>>

    @GET("users/{username}")
    fun getUser(
        @Path("username") username: String
    ): Call<UserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<UserResponse>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<UserResponse>>

    @GET("search/users")
    fun searchUser(
        @Query("q") username: String
    ): Call<SearchResponse>
}