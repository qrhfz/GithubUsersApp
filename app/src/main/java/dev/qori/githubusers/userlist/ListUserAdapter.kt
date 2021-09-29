package dev.qori.githubusers.userlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import dev.qori.githubusers.R
import dev.qori.githubusers.models.UserResponse

typealias OnItemClickCallback = (user: UserResponse)->Unit

class ListUserAdapter(
    private val users: List<UserResponse>,
    private val onItemClicked: OnItemClickCallback
): RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: CircleImageView = view.findViewById(R.id.civAvatar)
        val username: TextView = view.findViewById(R.id.tvUsername)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_user_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        val (username, name, avatar) = user

        holder.username.text = username
        Glide.with(holder.itemView.context)
            .load(avatar) // URL Gambar
            .circleCrop() // Mengubah image menjadi lingkaran
            .into(holder.avatar)

        holder.itemView.setOnClickListener {
            onItemClicked(user)
        }
    }

    override fun getItemCount(): Int = users.size

}