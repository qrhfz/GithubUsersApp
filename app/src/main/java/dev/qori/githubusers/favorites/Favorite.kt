package dev.qori.githubusers.favorites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey
    var username: String
)