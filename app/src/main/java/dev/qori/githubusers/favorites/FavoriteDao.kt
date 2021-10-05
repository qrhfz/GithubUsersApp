package dev.qori.githubusers.favorites

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user:Favorite)

    @Delete
    fun delete(user: Favorite)

    @Query("SELECT * from favorite")
    fun getAllFavorites(): LiveData<List<Favorite>>
}