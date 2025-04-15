package com.astro.mynewsapp.data.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("status")
    var status: String?= null,

    @SerializedName("totalResults" )
    var totalResults: Int?=null,

    @SerializedName("articles")
    var articles: List<Articles>,

    @SerializedName("code")
    var code: String?= null,

    @SerializedName("message")
    var message: String?= null,
)