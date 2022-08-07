package com.sertac.loodosproject.model

import com.google.gson.annotations.SerializedName

data class CoinDetail(
    val id : String,
    val symbol: String,
    val name : String,
    val description : CoinDescription,
    val image :CoinImage,
    @SerializedName("market_data")
    val marketData: CoinMarketData
)
