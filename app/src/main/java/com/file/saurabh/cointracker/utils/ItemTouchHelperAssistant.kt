package com.file.saurabh.cointracker.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.file.saurabh.cointracker.adapters.AlertsRecyclerViewAdapter
import com.file.saurabh.cointracker.adapters.WishlistRecyclerViewAdapter
import com.file.saurabh.cointracker.database.CoinDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WishlistItemTouchHelperAssistant(private val adapter : WishlistRecyclerViewAdapter, direction : Int) : ItemTouchHelper.SimpleCallback(0,direction) {
    private val coroutineScope = CoroutineScope(Job() + Dispatchers.IO)

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        TODO("Not yet implemented")
    }

    // deleting data from database
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val dao = CoinDatabase.getInstance(viewHolder.itemView.context.applicationContext).coinDao
        val coinData = adapter.getItemAt(viewHolder.adapterPosition)


        coroutineScope.launch {
            dao.delete(coinData)
        }

    }
}

class AlertsItemTouchHelperAssistant(private val adapter :AlertsRecyclerViewAdapter, direction : Int) : ItemTouchHelper.SimpleCallback(0,direction) {
    private val coroutineScope = CoroutineScope(Job() + Dispatchers.IO)

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        TODO("Not yet implemented")
    }

    // deleting data from database
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val dao = CoinDatabase.getInstance(viewHolder.itemView.context.applicationContext).alertDao
        val alert = adapter.getItemAt(viewHolder.adapterPosition)


        coroutineScope.launch {
            dao.delete(alert)
        }

    }
}