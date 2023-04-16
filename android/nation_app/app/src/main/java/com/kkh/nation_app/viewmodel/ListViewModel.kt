package com.kkh.nation_app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kkh.nation_app.model.Country
import com.kkh.nation_app.model.service.CountriesService
import kotlinx.coroutines.*

class ListViewModel : ViewModel() {

    val countriesService = CountriesService.getCountriesService()
    var job: Job? = null

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }

    val countries = MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<String?> ()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
        loading.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = countriesService.getCountries()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.e("123123", response.body().toString())
                    countries.value = response.body()
                    countryLoadError.value = ""
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
        countryLoadError.value = message
        loading.value = false
    }

    override fun onCleared() {
        job?.cancel()
    }
}