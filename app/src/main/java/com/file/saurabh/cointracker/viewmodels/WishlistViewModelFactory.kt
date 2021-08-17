package com.file.saurabh.cointracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.file.saurabh.cointracker.database.CoinDatabase

class WishlistViewModelFactory(private val coinDatabase : CoinDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WishlistViewModel::class.java)) {
            return WishlistViewModel(coinDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
