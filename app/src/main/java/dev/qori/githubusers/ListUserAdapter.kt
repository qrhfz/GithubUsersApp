package dev.qori.githubusers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView

typealias OnItemClickCallback = (user: User)->Unit

class ListUserAdapter(
    private val users: List<User>,
    private val onItemClicked: OnItemClickCallback
): RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: CircleImageView = view.findViewById(R.id.civAvatar)
        val name: TextView = view.findViewById(R.id.tvName)
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
        holder.name.text = name
        holder.avatar.setImageResource(avatar)

        holder.itemView.setOnClickListener {
            onItemClicked(user)
        }
    }

    override fun getItemCount(): Int = users.size

}