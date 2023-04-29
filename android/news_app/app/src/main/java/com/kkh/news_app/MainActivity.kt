package com.kkh.news_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        .addConverterFactory(TikXmlConverterFactory.create(
            TikXml.Builder()
                .exceptionOnUnreadXml(false)
                .build()
        )).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = NewsAdapter()
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@MainActivity.adapter

        }
        val newsService = retrofit.create(NewsService::class.java)
        newsService.mainFeed().enqueue(object: Callback<NewsRss> {
            override fun onResponse(call: Call<NewsRss>, response: Response<NewsRss>) {
                Log.e("MainActivity", "${response.body()?.channel?.items}")

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