package com.file.saurabh.cointracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.file.saurabh.cointracker.adapters.AlertsRecyclerViewAdapter
import com.file.saurabh.cointracker.database.CoinDatabase
import com.file.saurabh.cointracker.databinding.FragmentKeyPricesBinding

import com.file.saurabh.cointracker.utils.AlertsItemTouchHelperAssistant
import com.file.saurabh.cointracker.viewmodels.AlertViewModelFactory
import com.file.saurabh.cointracker.viewmodels.KeyPriceViewModel


class KeyPricesFragment : Fragment() {



    private lateinit var alertsRecyclerView: RecyclerView
    private lateinit var alertViewModelFactory: AlertViewModelFactory
    private lateinit var keyPriceViewModel: KeyPriceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentKeyPricesBinding>(inflater,R.layout.fragment_key_prices,container,false)

        alertsRecyclerView = binding.alertsRecyclerView
        val database = CoinDatabase.getInstance(binding.root.context.applicationContext)

        alertViewModelFactory = AlertViewModelFactory(database)
        keyPriceViewModel = ViewModelProvider(this,alertViewModelFactory).get(KeyPriceViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        adds adapter to alertrecyclerview and displays data in it
         */
        displayAlerts(view)

    }

    private fun displayAlerts(view: View) {
        val alertsRecyclerViewAdapter = AlertsRecyclerViewAdapter()

        alertsRecyclerView.layoutManager = LinearLayoutManager(view.context.applicationContext)
        alertsRecyclerView.adapter = alertsRecyclerViewAdapter
        alertsRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        /* make list item able to delete with a swipe*/
        val itemTouchHelperAssistant = AlertsItemTouchHelperAssistant(alertsRecyclerViewAdapter, ItemTouchHelper.LEFT)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperAssistant)
        itemTouchHelper.attachToRecyclerView(alertsRecyclerView)

        keyPriceViewModel.keyPricesList.observe(viewLifecycleOwner){

           it?.let{
               alertsRecyclerViewAdapter.refreshAlerts(it)
           }
        }

    }


}