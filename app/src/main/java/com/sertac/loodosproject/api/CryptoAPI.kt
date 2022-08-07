package com.sertac.loodosproject.api

import com.sertac.loodosproject.model.Coin
import com.sertac.loodosproject.model.CoinDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoAPI {
    @GET("coins/markets?vs_currency=usd")
    suspend fun getCoinList() : Response<List<Coin>>

    @GET("coins/{id}")
    suspend fun getCoinDetail(@Path("id") id:String) : Response<CoinDetail>
}