package com.kkh.news_app

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kkh.news_app.databinding.ActivityMainBinding
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NewsAdapter

    companion object {
        const val BASE_URL = "https://news.google.com/"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            TikXmlConverterFactory.create(
                TikXml.Builder()
                    .exceptionOnUnreadXml(false)
                    .build()
            )
        ).build()


    private val newsService = retrofit.create(NewsService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NewsAdapter()
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@MainActivity.adapter

        }

        binding.chipFeed.setOnClickListener {
            binding.chipGroupInScrollView.clearCheck()
            binding.chipFeed.isChecked = true

            newsService.mainFeed().submitList()
        }

        binding.chipPolitics.setOnClickListener {
            binding.chipGroupInScrollView.clearCheck()
            binding.chipPolitics.isChecked = true

            newsService.politicsNews().submitList()
        }

        binding.chipEconomy.setOnClickListener {
            binding.chipGroupInScrollView.clearCheck()
            binding.chipEconomy.isChecked = true

            newsService.economyNews().submitList()
        }

        binding.chipSocial.setOnClickListener {
            binding.chipGroupInScrollView.clearCheck()
            binding.chipSocial.isChecked = true

            newsService.socialNews().submitList()
        }

        binding.chipIt.setOnClickListener {
            binding.chipGroupInScrollView.clearCheck()
            binding.chipIt.isChecked = true

            newsService.itNews().submitList()
        }

        binding.chipSports.setOnClickListener {
            binding.chipGroupInScrollView.clearCheck()
            binding.chipSports.isChecked = true

            newsService.sportsNews().submitList()
        }

        binding.edtSearch.setOnEditorActionListener { view, action, event ->
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                binding.chipGroupInScrollView.clearCheck()

                binding.edtSearch.clearFocus()

                // 키보드 내리기
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)

                newsService.search(binding.edtSearch.text.toString()).submitList()

                return@setOnEditorActionListener true
            } else {
                return@setOnEditorActionListener false
            }
        }

        binding.chipFeed.performClick()
    }

    private fun Call<NewsRss>.submitList() {
        enqueue(object : Callback<NewsRss> {
            override fun onResponse(call: Call<NewsRss>, response: Response<NewsRss>) {
                val list = response.body()?.channel?.items.orEmpty().transform()
                adapter.submitList(list)

                list.forEachIndexed { index, news ->
                    Thread {
                        try {
                            val jsoup = Jsoup.connect(news.link).get()
                            // meta 태그를 가져옴
                            // 속성 값으로 property 값으로 og를 가지고 있는 것
                            val elements = jsoup.select("meta[property^=og:]")
                            val ogImageNodes = elements.find {
                                it.attr("property") == "og:image"
                            }
                            news.imageUrl = ogImageNodes?.attr("content")
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                        runOnUiThread {
                            adapter.notifyItemChanged(index)
                        }

                    }.start()
                }
            }

            override fun onFailure(call: Call<NewsRss>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}