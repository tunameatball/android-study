package com.kkh.github_search_app.model

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("items")
    val items: List<User>
)