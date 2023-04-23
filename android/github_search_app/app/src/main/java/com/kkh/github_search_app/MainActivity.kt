package com.kkh.github_search_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.kkh.github_search_app.adapter.UserAdapter
import com.kkh.github_search_app.databinding.ActivityMainBinding
import com.kkh.github_search_app.model.Repo
import com.kkh.github_search_app.model.UserDto
import com.kkh.github_search_app.network.GithubService
import com.kkh.github_search_app.network.RetrofitClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val retrofit: Retrofit = RetrofitClient.instance

    private val githubService = retrofit.create(GithubService::class.java)

    private lateinit var userAdapter: UserAdapter

    private lateinit var binding: ActivityMainBinding

    private var query: String = ""

    private val handler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        userAdapter = UserAdapter() {
            val intent = Intent(this@MainActivity, RepoActivity::class.java)
            intent.putExtra("username", it.userName)
            startActivity(intent)
        }

        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }

        val runnable = Runnable {
            searchUser()
        }

        binding.edtSearch.addTextChangedListener {
            query = it.toString()

            handler.removeCallbacks(runnable)
            handler.postDelayed(
                runnable,
                300,
            )
        }

    }

    private fun searchUser() {
        githubService.searchUsers(query).enqueue(object : Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                userAdapter.submitList(response.body()?.items)
            }

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Search Error!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}