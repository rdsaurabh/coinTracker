package com.file.saurabh.cointracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.file.saurabh.cointracker.database.Alert
import com.file.saurabh.cointracker.database.CoinDatabase
import kotlinx.coroutines.*

class AlertsViewModel(private  val database: CoinDatabase) : ViewModel() {
    private val scope : CoroutineScope = CoroutineScope(Job() + Dispatchers.IO)
    private  var _alertList : LiveData<List<Alert>> = MutableLiveData()

    val alertList : LiveData<List<Alert>>
        get() = _alertList

    init {
        scope.launch {
            _alertList = getAlertsList()
        }
    }

    private suspend fun getAlertsList() : LiveData<List<Alert>> {
        return withContext(Dispatchers.IO){
            val list = database
                    .alertDao
                    .getListOfAlerts()

            list
        }
    }

}