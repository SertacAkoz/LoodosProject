package com.sertac.loodosproject.model

import com.google.gson.annotations.SerializedName

data class CoinMarketData(
    @SerializedName("price_change_percentage_24h")
    val priceChangeDaily : Float,
    @SerializedName("price_change_percentage_7d")
    val priceChangeWeekly: Float,
    @SerializedName("price_change_percentage_14d")
    val priceChange14D: Float,
    @SerializedName("price_change_percentage_30d")
    val priceChange30D: Float,
    @SerializedName("price_change_percentage_60d")
    val priceChange60D: Float,
    @SerializedName("price_change_percentage_1y")
    val priceChangeYearly: Float,
    @SerializedName("current_price")
    val currentPrice : CoinCurrentPrice,
    @SerializedName("last_updated")
    val lastUpdated: String,
)
