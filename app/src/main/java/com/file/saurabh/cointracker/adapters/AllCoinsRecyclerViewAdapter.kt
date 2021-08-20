package com.file.saurabh.cointracker.adapters


import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.file.saurabh.cointracker.R
import com.file.saurabh.cointracker.database.CoinData
import com.file.saurabh.cointracker.database.CoinDatabase
import com.file.saurabh.cointracker.databinding.AllCoinsListItemBinding
import com.file.saurabh.cointracker.models.Market

import kotlinx.coroutines.*


class AllCoinsRecyclerViewAdapter :
    RecyclerView.Adapter<AllCoinsRecyclerViewAdapter.AllCoinsViewHolder>() {
    private  var  marketList : MutableList<Market> = mutableListOf()

    private val job = Job()
    private val ioScope = CoroutineScope(job + Dispatchers.IO)

    class AllCoinsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val marketName : TextView = itemView.findViewById(R.id.marketName)
        val marketPrice : TextView = itemView.findViewById(R.id.marketPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCoinsViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val bindingUtil = DataBindingUtil.inflate<AllCoinsListItemBinding>(inflater,R.layout.all_coins_list_item,parent,false)

        return AllCoinsViewHolder(bindingUtil.root)
    }

    override fun onBindViewHolder(holder: AllCoinsViewHolder, position: Int) {
        val context = holder.itemView.context
        holder.marketName.text = marketList[position].baseMarket.toUpperCase()
        holder.marketPrice.text =  context.getString(R.string.rupee) +  marketList[position].last

        val coinDao = CoinDatabase.getInstance(holder.marketPrice.context)
                     .coinDao

        /*this shows up the custom dialog for wish listing a coin */
        holder.itemView.setOnClickListener {
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setMessage("Add ${holder.marketName.text} to wish list!" )
                    .setPositiveButton("ADD", DialogInterface.OnClickListener{ dialog, id->

                        ioScope.launch{
                            coinDao.insert(CoinData(coinCode =  marketList[position].baseMarket,
                                    lastPrice = marketList[position].last?:"",
                                    lowPrice = marketList[position].low?:"",
                                    highPrice = marketList[position].high?:"",
                            ))
                        }
                        Toast.makeText(it.context,"${holder.marketName.text} has been added to wishlist",Toast.LENGTH_SHORT).show()
                    } ).setNegativeButton("NO",null)

            builder.create()
            builder.show()
        }
    }

    override fun getItemCount(): Int = marketList.size

   fun updateCoinsList(markets: List<Market>) {
       marketList.clear()

       for(market in markets){
           if (market.quoteMarket == "inr") marketList.add(market)
       }

       notifyDataSetChanged()
   }


}