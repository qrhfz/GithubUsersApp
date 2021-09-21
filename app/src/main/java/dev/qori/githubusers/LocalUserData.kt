package dev.qori.githubusers

import android.content.Context

class LocalUserData(context: Context) {
    private val res = context.resources
    private val usernames = res.getStringArray(R.array.username)
    private val names = res.getStringArray(R.array.name)
    private val locations = res.getStringArray(R.array.location)
    private val repositories = res.getStringArray(R.array.repository)
    private val followers = res.getStringArray(R.array.followers)
    private val followings = res.getStringArray(R.array.following)
    private val companies = res.getStringArray(R.array.company)
    private val avatars = res.obtainTypedArray(R.array.avatar)


    val all : List<User>
        get() = usernames.mapIndexed { index, _ ->
                getSingleUser(index)
            }


    private fun getSingleUser(index: Int): User = User(
        usernames[index],
        names[index],
        avatars.getResourceId(index, -1),
        companies[index],
        locations[index],
        repositories[index].toInt(),
        followers[index].toInt(),
        followings[index].toInt(),
    )
}