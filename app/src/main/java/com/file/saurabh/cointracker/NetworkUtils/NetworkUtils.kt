package com.file.saurabh.cointracker.NetworkUtils


import com.file.saurabh.cointracker.models.MarketsProperty
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private val allCoinsBaseUrl : String = " https://api.wazirx.com/api/v2/"

private val moshi : Moshi by lazy{
    Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
}

private val retrofit  : Retrofit by lazy {
    Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(allCoinsBaseUrl)
            .build()
}

object  CoinsApi{
    val allCoinsObject: AllCoins by lazy {
        retrofit.create(AllCoins::class.java)
    }
}
interface AllCoins{
    @GET("market-status")
    fun getMarketStatus() : Deferred<MarketsProperty>
}