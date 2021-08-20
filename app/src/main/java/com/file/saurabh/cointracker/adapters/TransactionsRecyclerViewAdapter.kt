package com.file.saurabh.cointracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.file.saurabh.cointracker.R
import com.file.saurabh.cointracker.database.AmountPerCoin
import com.file.saurabh.cointracker.database.Transactions
import com.file.saurabh.cointracker.databinding.TransactionsListItemBinding
import kotlin.math.abs

class TransactionsRecyclerViewAdapter :
    RecyclerView.Adapter<TransactionsRecyclerViewAdapter.TransactionsViewHolder>() {
    private var transactionsList : MutableList<Transactions> = mutableListOf()

    class TransactionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val transactionType : TextView = itemView.findViewById(R.id.transaction_type_text_view)
        val transactionAmount : TextView = itemView.findViewById(R.id.transaction_amount_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        val inflater = LayoutInflater.from(parent.context.applicationContext)
        val binding = DataBindingUtil.inflate<TransactionsListItemBinding>(inflater,R.layout.transactions_list_item,parent,false)
        return TransactionsViewHolder((binding.root))
    }

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        val context = holder.itemView.context.applicationContext
        val transactionType = transactionsList[position].transactionType

        if(transactionType == 0){
            holder.itemView.setBackgroundColor(context.getColor(R.color.green_for_buy))
        }else{
            holder.itemView.setBackgroundColor(context.getColor(R.color.red_for_sell))
        }
        holder.transactionType.text = if(transactionType == 1) context.getString(R.string.sell) else context.getString(R.string.buy)

        holder.transactionAmount.text = context.getString(R.string.rupee) + abs(transactionsList[position].transactionAmount)
    }

    override fun getItemCount(): Int = transactionsList.size

    /*refresh data in recycler view if any changes happened in live data*/
    fun refreshTransactionList(updatedList : List<Transactions>){
        transactionsList.clear()
        transactionsList.addAll(updatedList)
        notifyDataSetChanged()
    }
}