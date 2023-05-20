package com.kkh.food_map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kkh.food_map.databinding.ItemRestaurantBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.Tm128

class RestaurantAdapter(val onClick: (LatLng) -> Unit) :
    RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    private var dataSet = listOf<SearchItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size

    inner class ViewHolder(val binding: ItemRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchItem) {
            binding.tvTitle.text = item.title
            binding.tvCategory.text = item.category
            binding.tvAddress.text = item.roadAddress
            binding.root.setOnClickListener {
                onClick(Tm128(item.mapx.toDouble(), item.mapy.toDouble()).toLatLng())
            }
        }
    }

    fun setData(dataSet: List<SearchItem>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }
}