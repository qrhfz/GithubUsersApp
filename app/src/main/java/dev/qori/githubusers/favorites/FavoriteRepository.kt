package dev.qori.githubusers.favorites

import android.app.Application
import dev.qori.githubusers.GithubUsersDatabase
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val favoriteDao:FavoriteDao = GithubUsersDatabase.getDatabase(application).favoriteDao()
    private val executorService = Executors.newSingleThreadExecutor()

    fun getAllFavorites() = favoriteDao.getAllFavorites()
    fun insert(favorite: Favorite){
        executorService.execute{favoriteDao.insert(favorite)}
    }

    fun delete(favorite: Favorite) {
        executorService.execute { favoriteDao.delete(favorite) }
    }

}