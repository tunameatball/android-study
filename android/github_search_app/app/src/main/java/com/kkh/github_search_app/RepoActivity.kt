package com.kkh.github_search_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kkh.github_search_app.adapter.RepoAdapter
import com.kkh.github_search_app.databinding.ActivityRepoBinding
import com.kkh.github_search_app.model.Repo
import com.kkh.github_search_app.network.GithubService
import com.kkh.github_search_app.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepoActivity : AppCompatActivity() {

    private val retrofit: Retrofit = RetrofitClient.instance

    private val githubService = retrofit.create(GithubService::class.java)

    private lateinit var binding: ActivityRepoBinding
    private lateinit var repoAdapter: RepoAdapter

    private var page: Int = 0
    private var hasMore = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userName = intent.getStringExtra("username") ?: return

        repoAdapter = RepoAdapter {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.htmlUrl))
            startActivity(intent)
        }

        binding.rvRepo.apply {
            layoutManager = LinearLayoutManager(this@RepoActivity)
            adapter = repoAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val linearLayoutManager = this@apply.layoutManager as LinearLayoutManager

                    val totalCount = linearLayoutManager.itemCount
                    val lastVisiblePosition =
                        linearLayoutManager.findLastCompletelyVisibleItemPosition()

                    if (lastVisiblePosition >= (totalCount - 1) && hasMore) {
                        page += 1
                        searchRepos(userName, page)
                    }
                }
            })
        }

        binding.tvUsername.text = userName

        searchRepos(userName, page)
    }

    private fun searchRepos(userName: String, page: Int) {
        githubService.listRepos(userName, page).enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                hasMore = response.body()?.count() == 30
                repoAdapter.submitList(repoAdapter.currentList + response.body().orEmpty())
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                Toast.makeText(this@RepoActivity, "Search Repo Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}