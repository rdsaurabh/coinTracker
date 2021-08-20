package com.file.saurabh.cointracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.file.saurabh.cointracker.PortfolioFragmentDirections
import com.file.saurabh.cointracker.R
import com.file.saurabh.cointracker.database.AmountPerCoin
import com.file.saurabh.cointracker.databinding.AmountPerCoinItemBinding
import com.file.saurabh.cointracker.databinding.CoinsInvestedInItemBinding

class CoinsInvestedInAdapter : RecyclerView.Adapter<CoinsInvestedInAdapter.CoinsInvestedInViewHolder>() {

    private var coinInvestedInList : MutableList<AmountPerCoin> = mutableListOf()

    class CoinsInvestedInViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val coinName: TextView = itemView.findViewById(R.id.coin_name_text_view)
        val investedAmount: TextView = itemView.findViewById(R.id.invested_amount_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsInvestedInViewHolder {
        val inflater = LayoutInflater.from(parent.context.applicationContext)
        val binding = DataBindingUtil.inflate<AmountPerCoinItemBinding>(inflater,R.layout.amount_per_coin_item,parent,false)
        return CoinsInvestedInViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CoinsInvestedInViewHolder, position: Int) {
        val context = holder.itemView.context.applicationContext
        val coinName = coinInvestedInList[position].coinCode.toUpperCase()
        holder.coinName.text = coinName
        holder.investedAmount.text = context.getString(R.string.rupee) + coinInvestedInList[position].amount.toString()

        holder.itemView.setOnClickListener {
            it.findNavController()
                .navigate(PortfolioFragmentDirections.actionPortfolioToTransactionsFragment(coinName))
        }

    }

    override fun getItemCount(): Int = coinInvestedInList.size

    fun refreshCoinsInvestedInList(updatedList : List<AmountPerCoin> ){
        coinInvestedInList.clear()
        coinInvestedInList.addAll(updatedList)
        notifyDataSetChanged()
    }

}