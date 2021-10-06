package dev.qori.githubusers.models

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("login")
    val username: String,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("public_repos")
    val repos: Int,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("company")
    val company: String?,

    @field:SerializedName("location")
    val location: String?,
)
