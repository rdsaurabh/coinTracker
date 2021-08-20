package com.file.saurabh.cointracker.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.file.saurabh.cointracker.database.AmountPerCoin
import com.file.saurabh.cointracker.database.CoinDatabase
import kotlinx.coroutines.*

class PortfolioViewModel(private val database: CoinDatabase)  : ViewModel(){
    private  val coroutineScope : CoroutineScope = CoroutineScope(Job() + Dispatchers.IO)
    private var _totalAmount : LiveData<Long> = MutableLiveData()
    private var _coinsInvestedInList : LiveData<List<AmountPerCoin>> = MutableLiveData()

    val totalAmount : LiveData<Long>
        get() = _totalAmount

    val coinsInvestedInList : LiveData<List<AmountPerCoin>>
        get() = _coinsInvestedInList

    init {
        coroutineScope.launch {
            _totalAmount = getTotalAmount()
        }

        coroutineScope.launch {
            _coinsInvestedInList = getCoinsInvestedInList()
        }
    }

    private suspend fun getCoinsInvestedInList(): LiveData<List<AmountPerCoin>> {
        return  withContext(Dispatchers.IO){
            val list = database.transactionsDao
                    .getListOfAmountInvestedPerCoin()
            list
        }

    }


    private suspend fun getTotalAmount(): LiveData<Long> {

     return  withContext(Dispatchers.IO){
            val transactions = database.transactionsDao
                    .totalInvestedAmount()
            transactions
        }

    }

}