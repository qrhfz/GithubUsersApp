package dev.qori.githubusers.userlist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import dev.qori.githubusers.R
import dev.qori.githubusers.models.UserResponse

typealias OnItemClickCallback = (user: UserResponse) -> Unit

class UserListAdapter(
    private var users: List<UserResponse>,
    private val onItemClicked: OnItemClickCallback
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: CircleImageView = view.findViewById(R.id.civAvatar)
        val username: TextView = view.findViewById(R.id.tvUsername)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_user_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]

        holder.username.text = user.username
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .circleCrop()
            .into(holder.avatar)

        holder.itemView.setOnClickListener {
            Log.d("UserListAdapater", user.toString())
            onItemClicked(user)
        }
    }

    override fun getItemCount(): Int = users.size

    fun setData(users: List<UserResponse>){
        this.users = users
        notifyDataSetChanged()
    }
}