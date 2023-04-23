package com.kkh.github_search_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kkh.github_search_app.databinding.ItemRepoBinding
import com.kkh.github_search_app.model.Repo

class RepoAdapter(private val onClick: (Repo) -> Unit) : ListAdapter<Repo, RepoAdapter.RepoViewHolder>(diffUtil){

    inner class RepoViewHolder(private val binding: ItemRepoBinding) : ViewHolder(binding.root) {

        fun bind(repo: Repo) {
            binding.apply {
                tvRepoName.text = repo.name
                tvDescription.text = repo.description
                tvStarCount.text = repo.starCount.toString()
                tvForkCount.text = repo.forkCount.toString()
                root.setOnClickListener {
                    onClick(repo)
                }
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}