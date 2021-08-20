package com.file.saurabh.cointracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.file.saurabh.cointracker.R
import com.file.saurabh.cointracker.database.KeyPrice

import com.file.saurabh.cointracker.databinding.KeyPricesListItemBinding


class AlertsRecyclerViewAdapter : RecyclerView.Adapter<AlertsRecyclerViewAdapter.AlertsViewHolder>() {

    private var alertList : MutableList<KeyPrice> = mutableListOf()

    class AlertsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val coinDataTextView : TextView = itemView.findViewById(R.id.coinCodeTextView)
        val alertTypeTextView : TextView = itemView.findViewById(R.id.alertTypeTextView)
        val alertPriceTextView : TextView = itemView.findViewById(R.id.alertPriceTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertsViewHolder {
        val inflater = LayoutInflater.from(parent.context.applicationContext)
        val binding = DataBindingUtil.inflate<KeyPricesListItemBinding>(inflater,R.layout.key_prices_list_item,parent,false)
        return AlertsViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AlertsViewHolder, position: Int) {
        val context = holder.itemView.context.applicationContext
        val alertType : String = if (alertList[position].keyPriceType == 1)  context.getString(R.string.sell)else context.getString(R.string.buy)
        holder.coinDataTextView.text = alertList[position].coinCode.toUpperCase()
        holder.alertTypeTextView.text = alertType
        holder.alertPriceTextView.text = context.getString(R.string.rupee) + alertList[position].keyPriceAmount.toString()
    }

    override fun getItemCount(): Int = alertList.size

    fun refreshAlerts(it: List<KeyPrice>) {
        alertList.clear()
        alertList.addAll(it)
        notifyDataSetChanged()
    }

    fun getItemAt(adapterPosition: Int): KeyPrice = alertList[adapterPosition]

}