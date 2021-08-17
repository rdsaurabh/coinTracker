package com.file.saurabh.cointracker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.file.saurabh.cointracker.adapters.AlertsRecyclerViewAdapter
import com.file.saurabh.cointracker.database.CoinDatabase
import com.file.saurabh.cointracker.databinding.FragmentAlertsBinding
import com.file.saurabh.cointracker.utils.AlertsItemTouchHelperAssistant
import com.file.saurabh.cointracker.utils.WishlistItemTouchHelperAssistant
import com.file.saurabh.cointracker.viewmodels.AlertViewModelFactory
import com.file.saurabh.cointracker.viewmodels.AlertsViewModel
import kotlinx.coroutines.*


class AlertsFragment : Fragment() {

    private lateinit  var addNewAlert : View

    private lateinit var alertsRecyclerView: RecyclerView
    private lateinit var alertViewModelFactory: AlertViewModelFactory
    private lateinit var alertsViewModel: AlertsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentAlertsBinding>(inflater,R.layout.fragment_alerts,container,false)
        addNewAlert = binding.addAlertFragment
        alertsRecyclerView = binding.alertsRecyclerView
        val database = CoinDatabase.getInstance(binding.root.context.applicationContext)

        alertViewModelFactory = AlertViewModelFactory(database)
        alertsViewModel = ViewModelProvider(this,alertViewModelFactory).get(AlertsViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addNewAlert.setOnClickListener {
            findNavController().navigate(AlertsFragmentDirections.actionAlertsToWishlist())
        }

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

        val itemTouchHelperAssistant = AlertsItemTouchHelperAssistant(alertsRecyclerViewAdapter, ItemTouchHelper.LEFT)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperAssistant)
        itemTouchHelper.attachToRecyclerView(alertsRecyclerView)

        alertsViewModel.alertList.observe(viewLifecycleOwner){

           it?.let{
               alertsRecyclerViewAdapter.refreshAlerts(it)
           }
        }

    }


}