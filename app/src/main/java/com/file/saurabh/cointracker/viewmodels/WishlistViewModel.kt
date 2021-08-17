
package com.file.saurabh.cointracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.file.saurabh.cointracker.database.CoinData
import com.file.saurabh.cointracker.database.CoinDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WishlistViewModel(private val database: CoinDatabase) : ViewModel() {
    val ioScope = CoroutineScope(Job() + Dispatchers.IO)

    private var _wishlist : LiveData<List<CoinData>> = MutableLiveData()

    val wishlist : LiveData<List<CoinData>>
            get() = _wishlist

    init {
        getWishlist()
    }

    private fun getWishlist(){

        ioScope.launch {
            _wishlist = database.coinDao
                    .getListOfCoinData()
        }

    }


}