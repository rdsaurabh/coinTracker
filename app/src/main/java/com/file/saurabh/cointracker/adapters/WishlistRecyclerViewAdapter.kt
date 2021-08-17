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
import com.file.saurabh.cointracker.database.Alert
import com.file.saurabh.cointracker.database.CoinData
import com.file.saurabh.cointracker.database.CoinDatabase
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
        val addAlertLayout : View = itemView.findViewById(R.id.add_alert_wishlist_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<WishlistItemBinding>(inflater,R.layout.wishlist_item,parent,false)

        return WishlistViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        holder.wishlistMarketName.text = myWishlist[position].coinCode.toUpperCase()
        //pop a dialog to save a new alert to database
        holder.addAlertLayout.setOnClickListener {
            openAddAlertDialog(holder,position)
        }
    }

    private fun openAddAlertDialog(holder: WishlistViewHolder,position: Int) {
        val builder = AlertDialog.Builder(holder.itemView.context)

        val view = LayoutInflater.from(fragment.requireContext())
                .inflate(R.layout.alert_dialog_layout,null)

        val coinCode = myWishlist[position].coinCode

        view.findViewById<TextView>(R.id.coin_label).text = "Add alert on ${coinCode.toUpperCase()}"

        builder.setView(view)
                .setPositiveButton("ADD", DialogInterface.OnClickListener{ dialog, id->
                    val num = view.findViewById<TextInputEditText>(R.id.add_alert_edit_text).text.toString()
                    val selectedRadioId = view.findViewById<RadioGroup>(R.id.radioGroup).checkedRadioButtonId

                    val flag = if(selectedRadioId == R.id.radio_sell) 1 else 0

                    if(num.isEmpty()){
                        failedToAddAlert(view)
                    }else{
                        addAlert(coinCode,num.toInt(),flag)
                    }

                } ).setNegativeButton("Cancel",null)


        builder.create()
        builder.show()

    }

    private fun addAlert(coinCode : String,amount : Int,flag : Int) {
        // flag == 0 means the alert is on buy
        //flag == 1 means the alert is on sell

        ioScope.launch {
            CoinDatabase.getInstance(fragment.requireContext().applicationContext).alertDao
                    .insert(Alert(coinCode = coinCode,
                            alertPrice = amount,
                            alertType = flag
                    ))
        }

        Toast.makeText(fragment.requireContext(),"New Alert Added",Toast.LENGTH_SHORT)
                .show()

    }

    private fun failedToAddAlert(view : View) {
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