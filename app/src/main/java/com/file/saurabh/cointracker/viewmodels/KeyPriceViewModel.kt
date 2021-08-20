package com.file.saurabh.cointracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel

import com.file.saurabh.cointracker.database.CoinDatabase
import com.file.saurabh.cointracker.database.KeyPrice
import kotlinx.coroutines.*

class KeyPriceViewModel(private  val database: CoinDatabase) : ViewModel() {
    private val scope : CoroutineScope = CoroutineScope(Job() + Dispatchers.IO)
    private  var _keyPricesList : LiveData<List<KeyPrice>> = MutableLiveData()

    val keyPricesList : LiveData<List<KeyPrice>>
        get() = _keyPricesList

    init {
        scope.launch {
            _keyPricesList = getAlertsList()
        }
    }

    private suspend fun getAlertsList() : LiveData<List<KeyPrice>> {
        return withContext(Dispatchers.IO){
            val list = database
                    .keyPriceDao
                    .getListOfAlerts()

            list
        }
    }

}