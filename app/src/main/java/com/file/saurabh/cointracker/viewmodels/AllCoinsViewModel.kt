package com.file.saurabh.cointracker.viewmodels


import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.Worker
import androidx.work.WorkerParameters

import com.file.saurabh.cointracker.NetworkUtils.CoinsApi
import com.file.saurabh.cointracker.models.MarketsProperty
import kotlinx.coroutines.*
import java.lang.Exception

class AllCoinsViewModel : ViewModel() {
    private val job : Job = Job()

    private var coroutineScope : CoroutineScope = CoroutineScope(job + Dispatchers.Main)


    private var _marketsProperty : MutableLiveData<MarketsProperty> = MutableLiveData()

    val marketsProperty : LiveData<MarketsProperty>
        get() = _marketsProperty

    init {
        allCoins()
    }


    private fun allCoins(){
        coroutineScope.launch {
            _marketsProperty.value = getAllCoins()
        }
    }

    private suspend fun getAllCoins(): MarketsProperty? {
          return withContext(Dispatchers.IO){
               var resultSet : MarketsProperty? = null
                try{
                     resultSet = CoinsApi.allCoinsObject.getMarketStatus().await()
                }catch (e : Exception){
                    Log.d("APIERROR",e.message.toString())
                }
                resultSet
           }
    }
}

