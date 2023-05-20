package com.kkh.food_map

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kkh.food_map.databinding.ActivityMainBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.Tm128
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var naverMap: NaverMap
    private var markers = emptyList<Marker>()
    private var restaurantAdapter = RestaurantAdapter {
        collapseBottomSheet()
        moveCamera(it, 17.0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mapView.onCreate(savedInstanceState)

        binding.mapView.getMapAsync(this)

        binding.bottomSheet.rvList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = restaurantAdapter
        }

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                collapseBottomSheet()
                return if (query?.isNotEmpty() == true) {
                    SearchRepository.getGoodRestaurant(query)
                        .enqueue(object : Callback<SearchResult> {
                            override fun onResponse(
                                call: Call<SearchResult>,
                                response: Response<SearchResult>
                            ) {
                                val searchItemList = response.body()?.items.orEmpty()

                                if (searchItemList.isEmpty()) {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "검색 결과가 없습니다",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return
                                } else if (!this@MainActivity::naverMap.isInitialized) {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "오류가 발생했습니다",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return
                                }

                                markers.forEach {
                                    it.map = null
                                }

                                markers = searchItemList.map {
                                    Marker(
                                        Tm128(
                                            it.mapx.toDouble(),
                                            it.mapy.toDouble()
                                        ).toLatLng()
                                    ).apply {
                                        captionText = it.title
                                        map = naverMap
                                        width = 50
                                        height = 70
                                    }
                                }

                                restaurantAdapter.setData(searchItemList)

                                moveCamera(markers.first().position)
                            }

                            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                                Log.e("onFailure", t.message ?: "error")
                            }
                        })
                    false
                } else {
                    true
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onMapReady(p0: NaverMap) {
        naverMap = p0
    }

    fun moveCamera(position: LatLng, zoomLevel: Double = 15.0) {
        if (this::naverMap.isInitialized) {
            val cameraUpdate = CameraUpdate.scrollAndZoomTo(position, zoomLevel)
                .animate(CameraAnimation.Easing)
            naverMap.moveCamera(cameraUpdate)
        }
    }

    fun collapseBottomSheet() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.root)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }
}