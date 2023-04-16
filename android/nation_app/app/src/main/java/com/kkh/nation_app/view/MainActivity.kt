package com.kkh.nation_app.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kkh.nation_app.viewmodel.ListViewModel
import com.kkh.nation_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val countryListAdapter = CountryListAdapter(arrayListOf())

    lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ListViewModel::class.java]
        viewModel.refresh()

        binding.countriesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countryListAdapter
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.countries.observe(this, Observer {countries ->
            countries?.let {
                if (binding.countriesList.visibility != View.VISIBLE) {
                    binding.countriesList.visibility = View.VISIBLE
                }
                countryListAdapter.updateCountries(it)
            }
        })

        viewModel.countryLoadError.observe(this, Observer {isError ->
            binding.listError.visibility = if (isError == "") View.GONE else View.VISIBLE
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                binding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.listError.visibility = View.GONE
                    binding.countriesList.visibility = View.GONE
                }
            }
        })
    }
}