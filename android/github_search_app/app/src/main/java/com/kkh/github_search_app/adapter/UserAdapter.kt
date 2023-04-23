package com.kkh.github_search_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kkh.github_search_app.databinding.ItemUserBinding
import com.kkh.github_search_app.model.User

class UserAdapter(val onClick: (User) -> Unit) : ListAdapter<User, UserAdapter.UserViewHolder>(diffUtil) {

    inner class UserViewHolder(private val binding: ItemUserBinding) : ViewHolder(binding.root) {

        fun bind(item: User) {
            binding.tvUsername.text = item.userName
            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}