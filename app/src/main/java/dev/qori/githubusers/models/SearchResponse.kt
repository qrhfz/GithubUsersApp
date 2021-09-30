package dev.qori.githubusers.models

import com.google.gson.annotations.SerializedName

class SearchResponse {
    @field:SerializedName("items")
    val items: List<UserResponse>? = null
}