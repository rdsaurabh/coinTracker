package com.file.saurabh.cointracker.adapters

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.file.saurabh.cointracker.R
import com.file.saurabh.cointracker.WishlistFragment
import com.file.saurabh.cointracker.database.*
import com.file.saurabh.cointracker.databinding.WishlistItemBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WishlistRecyclerViewAdapter(private val fragment : WishlistFragment) : RecyclerView.Adapter<WishlistRecyclerViewAdapter.WishlistViewHolder>() {
    private var myWishlist = arrayListOf<CoinData>()
    private val ioScope = CoroutineScope(Job() + Dispatchers.IO)


    class WishlistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val wishlistMarketName : TextView = itemView.findViewById(R.id.wishlistItemMarketName)
        val addKeyPrice : TextView = itemView.findViewById(R.id.add_key_price)
        val newTransaction : TextView = itemView.findViewById(R.id.new_transaction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<WishlistItemBinding>(inflater,R.layout.wishlist_item,parent,false)

        return WishlistViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        holder.wishlistMarketName.text = myWishlist[position].coinCode.toUpperCase()
        //pop a dialog to save a new alert to database
        holder.addKeyPrice.setOnClickListener {
            openKeyPricesDialog(holder,position)
        }

        /*
        the popup layout which  is being used to handle transactions,is same as add key price.
        because idea is more or less same.
         */
        holder.newTransaction.setOnClickListener {
            openNewTransactionDialog(holder,position)
        }
    }
    /* these following dialogs are for handling insertions of new key prices and
    transactions respectively.
     */
    private fun openNewTransactionDialog(holder: WishlistRecyclerViewAdapter.WishlistViewHolder, position: Int) {
        val builder = AlertDialog.Builder(holder.itemView.context)

        val view = LayoutInflater.from(fragment.requireContext())
                .inflate(R.layout.key_prices_dialog_layout,null)

        val coinCode = myWishlist[position].coinCode

        view.findViewById<TextView>(R.id.coin_label).text = "New Transaction on ${coinCode.toUpperCase()}"

        builder.setView(view)
                .setPositiveButton("ADD", DialogInterface.OnClickListener{ dialog, id->
                    val num = view.findViewById<TextInputEditText>(R.id.add_alert_edit_text).text.toString()
                    val selectedRadioId = view.findViewById<RadioGroup>(R.id.radioGroup).checkedRadioButtonId

                    val flag = if(selectedRadioId == R.id.radio_sell) 1 else 0

                    if(num.isEmpty()){
                        failedToAdd(view)
                    }else{
                        /**
                         * This function call will handle in case a new transaction is initiated.
                         * But in case the transaction is sell type then we need to negate the amount
                         * flag == 1 indicates sell transaction
                         * which means that amount has been subtracted
                         */
                        var amount = num.toLong()
                        amount = if(flag == 1) -amount else amount
                        addTransaction(coinCode,amount,flag)
                    }

                } ).setNegativeButton("Cancel",null)


        builder.create()
        builder.show()

    }

    private fun addTransaction(coinCode: String, amount : Long, flag: Int) {

        ioScope.launch {

            CoinDatabase.getInstance(fragment.requireContext().applicationContext)
                    .transactionsDao
                    .insert(Transactions(
                            coinCode = coinCode,
                            transactionAmount = amount,
                            transactionType = flag

                    ))
        }
        Toast.makeText(fragment.requireContext().applicationContext,"Transaction added",Toast.LENGTH_SHORT)
                .show()

    }

    private fun openKeyPricesDialog(holder: WishlistViewHolder, position: Int) {
        val builder = AlertDialog.Builder(holder.itemView.context)

        val view = LayoutInflater.from(fragment.requireContext())
                .inflate(R.layout.key_prices_dialog_layout,null)

        val coinCode = myWishlist[position].coinCode

        view.findViewById<TextView>(R.id.coin_label).text = "Add alert on ${coinCode.toUpperCase()}"

        builder.setView(view)
                .setPositiveButton("ADD", DialogInterface.OnClickListener{ dialog, id->
                    val num = view.findViewById<TextInputEditText>(R.id.add_alert_edit_text).text.toString()
                    val selectedRadioId = view.findViewById<RadioGroup>(R.id.radioGroup).checkedRadioButtonId

                    val flag = if(selectedRadioId == R.id.radio_sell) 1 else 0

                    if(num.isEmpty()){
                        failedToAdd(view)
                    }else{
                        addKeyPrice(coinCode,num.toLong(),flag)
                    }

                } ).setNegativeButton("Cancel",null)


        builder.create()
        builder.show()

    }

    private fun addKeyPrice(coinCode : String, amount : Long, flag : Int) {
        // flag == 0 means the alert is on buy
        //flag == 1 means the alert is on sell

        ioScope.launch {
            CoinDatabase.getInstance(fragment.requireContext().applicationContext).keyPriceDao
                    .insert(KeyPrice(coinCode = coinCode,
                            keyPriceAmount = amount,
                            keyPriceType = flag
                    ))
        }

        Toast.makeText(fragment.requireContext(),"New Alert Added",Toast.LENGTH_SHORT)
                .show()

    }

    private fun failedToAdd(view : View) {
        Toast.makeText(view.context.applicationContext,"INVALID AMOUNT!!",Toast.LENGTH_SHORT)
                .show()
    }

    override fun getItemCount(): Int  = myWishlist.size

    fun refreshWishlist(newWishlist: List<CoinData>) {
        myWishlist.clear()
        myWishlist.addAll(newWishlist)
        notifyDataSetChanged()
    }

    fun getItemAt(position: Int) : CoinData = myWishlist[position]
}