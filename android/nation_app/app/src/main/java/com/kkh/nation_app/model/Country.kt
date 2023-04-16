package com.kkh.nation_app.model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Country(
    @SerializedName("capital")
    @Expose
    var capital: String,
    @SerializedName("name")
    @Expose
    var name: String,
    @SerializedName("flagPNG")
    @Expose
    var flag: String
)