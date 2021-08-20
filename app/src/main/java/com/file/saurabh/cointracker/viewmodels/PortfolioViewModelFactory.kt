package com.file.saurabh.cointracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.file.saurabh.cointracker.database.CoinDatabase

class PortfolioViewModelFactory(private val coinDatabase : CoinDatabase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PortfolioViewModel::class.java)) {
            return PortfolioViewModel(coinDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
