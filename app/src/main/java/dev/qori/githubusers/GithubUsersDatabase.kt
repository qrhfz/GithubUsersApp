package dev.qori.githubusers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.qori.githubusers.favorites.Favorite
import dev.qori.githubusers.favorites.FavoriteDao

@Database(entities = [Favorite::class], version = 1)
abstract class GithubUsersDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: GithubUsersDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): GithubUsersDatabase {
            if (INSTANCE == null) {
                synchronized(GithubUsersDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GithubUsersDatabase::class.java, "github_users_db"
                    )
                        .build()
                }
            }
            return INSTANCE as GithubUsersDatabase
        }
    }
}