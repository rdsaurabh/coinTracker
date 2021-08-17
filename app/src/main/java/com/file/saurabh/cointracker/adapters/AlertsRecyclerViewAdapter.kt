package com.file.saurabh.cointracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.file.saurabh.cointracker.R
import com.file.saurabh.cointracker.database.Alert
import com.file.saurabh.cointracker.databinding.AlertsListItemBinding

class AlertsRecyclerViewAdapter : RecyclerView.Adapter<AlertsRecyclerViewAdapter.AlertsViewHolder>() {

    private var alertList : MutableList<Alert> = mutableListOf()

    class AlertsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val coinDataTextView : TextView = itemView.findViewById(R.id.coinCodeTextView)
        val alertTypeTextView : TextView = itemView.findViewById(R.id.alertTypeTextView)
        val alertPriceTextView : TextView = itemView.findViewById(R.id.alertPriceTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertsViewHolder {
        val inflater = LayoutInflater.from(parent.context.applicationContext)
        val binding = DataBindingUtil.inflate<AlertsListItemBinding>(inflater,R.layout.alerts_list_item,parent,false)
        return AlertsViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AlertsViewHolder, position: Int) {
        val context = holder.itemView.context.applicationContext
        val alertType : String = if (alertList[position].alertType == 1)  context.getString(R.string.sell)else context.getString(R.string.buy)
        holder.coinDataTextView.text = alertList[position].coinCode.toUpperCase()
        holder.alertTypeTextView.text = alertType
        holder.alertPriceTextView.text = context.getString(R.string.rupee) + alertList[position].alertPrice.toString()
    }

    override fun getItemCount(): Int = alertList.size

    fun refreshAlerts(it: List<Alert>) {
        alertList.clear()
        alertList.addAll(it)
        notifyDataSetChanged()
    }

    fun getItemAt(adapterPosition: Int): Alert = alertList[adapterPosition]

}