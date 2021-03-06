package com.file.saurabh.cointracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.file.saurabh.cointracker.database.CoinDatabase


class AlertViewModelFactory(private val coinDatabase : CoinDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KeyPriceViewModel::class.java)) {
            return KeyPriceViewModel(coinDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}