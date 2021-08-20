package com.file.saurabh.cointracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.file.saurabh.cointracker.database.CoinDatabase
import com.file.saurabh.cointracker.database.Transactions
import kotlinx.coroutines.*

class TransactionsViewModel(private val  database: CoinDatabase,private val coinCode : String) : ViewModel() {
    private  var _transactionPerCoinList : LiveData<List<Transactions>> = MutableLiveData()
    private val coroutineScope : CoroutineScope = CoroutineScope(Job() + Dispatchers.IO)

    val transactionPerCoinList : LiveData<List<Transactions>>
        get() = _transactionPerCoinList

    init {
        coroutineScope.launch {
            _transactionPerCoinList = getTransactionList()
        }
    }

    private suspend fun getTransactionList(): LiveData<List<Transactions>> {
        return  withContext(Dispatchers.IO){
            val list = database.transactionsDao
                .getTransactionsOfParticularCoin(coinCode)
            list
        }
    }


}