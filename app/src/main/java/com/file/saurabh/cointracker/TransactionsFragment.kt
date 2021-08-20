package com.file.saurabh.cointracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.file.saurabh.cointracker.adapters.TransactionsRecyclerViewAdapter
import com.file.saurabh.cointracker.database.CoinDatabase
import com.file.saurabh.cointracker.databinding.FragmentTransactionsBinding
import com.file.saurabh.cointracker.viewmodels.TransactionsViewModel
import com.file.saurabh.cointracker.viewmodels.TransactionsViewModelFactory


class TransactionsFragment : Fragment() {

    /*
    the safe arg argument is passed here for title on app bar and coinCode
    that we need to fetch transactions of that particular coin
     */
    private lateinit var viewModel : TransactionsViewModel
    private lateinit var viewModelFactory : TransactionsViewModelFactory
    private lateinit var transactionsRecyclerView : RecyclerView
    private lateinit var args : TransactionsFragmentArgs
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        args = TransactionsFragmentArgs.fromBundle(requireArguments())

        val binding = DataBindingUtil.inflate<FragmentTransactionsBinding>(inflater,R.layout.fragment_transactions,container,false)
        transactionsRecyclerView = binding.transactionsRecyclerView
        val context = binding.root.context.applicationContext
        val database = CoinDatabase.getInstance(context)

        /**
         * since coinName has been passed in uppercase we need to make it lower case
         * so that database and query parameters have same case.
         * Case stored in database is lowercase
         */

        viewModelFactory = TransactionsViewModelFactory(database,args.coinName.toLowerCase())

        viewModel = ViewModelProvider(this,viewModelFactory).get(TransactionsViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val transactionsRecyclerViewAdapter = TransactionsRecyclerViewAdapter()
        /* set up recycler view*/
        transactionsRecyclerView.adapter = transactionsRecyclerViewAdapter
        transactionsRecyclerView.layoutManager = LinearLayoutManager(view.context.applicationContext)
        transactionsRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        viewModel.transactionPerCoinList.observe(viewLifecycleOwner){
            it?.let {
                transactionsRecyclerViewAdapter.refreshTransactionList(it)
            }
        }

    }


}