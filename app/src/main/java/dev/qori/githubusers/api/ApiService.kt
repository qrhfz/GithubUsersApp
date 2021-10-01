package dev.qori.githubusers.api

import dev.qori.githubusers.models.SearchResponse
import dev.qori.githubusers.models.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("users")
    @Headers("Authorization: token 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")
    fun getUserList(): Call<List<UserResponse>>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUser(
        @Path("username") username: String
    ): Call<UserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<UserResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<UserResponse>>

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun searchUser(
        @Query("q") username: String
    ): Call<SearchResponse>
}