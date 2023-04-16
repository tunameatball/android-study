package com.kkh.nation_app.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kkh.nation_app.databinding.ItemCountryBinding
import com.kkh.nation_app.model.Country

class CountryListAdapter(var countries: ArrayList<Country>) :
    RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    fun updateCountries(newCountries: List<Country>) {
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CountryViewHolder (
        ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class CountryViewHolder(val binding: ItemCountryBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(country: Country) {
            binding.name.text = country.name
            binding.capital.text = country.capital
            binding.imageView.loadImage(country.flag)
        }
    }
}