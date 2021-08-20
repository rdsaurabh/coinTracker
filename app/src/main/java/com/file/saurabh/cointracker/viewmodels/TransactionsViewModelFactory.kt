package com.file.saurabh.cointracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.file.saurabh.cointracker.database.CoinDatabase

class TransactionsViewModelFactory(private val database: CoinDatabase,private val coinCode : String) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionsViewModel::class.java)) {
            return TransactionsViewModel(database,coinCode) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}