package com.file.saurabh.cointracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.file.saurabh.cointracker.adapters.CoinsInvestedInAdapter
import com.file.saurabh.cointracker.database.CoinDatabase
import com.file.saurabh.cointracker.databinding.FragmentPortfolioBinding
import com.file.saurabh.cointracker.viewmodels.PortfolioViewModel
import com.file.saurabh.cointracker.viewmodels.PortfolioViewModelFactory

class PortfolioFragment : Fragment() {

    private lateinit var portfolioViewModel: PortfolioViewModel
    private lateinit var portfolioViewModelFactory: PortfolioViewModelFactory
    private lateinit var amountTextView: TextView
    private lateinit var coinsInvestedInRecylerView : RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentPortfolioBinding>(inflater,R.layout.fragment_portfolio,container,false)
        val database = CoinDatabase.getInstance(requireContext().applicationContext)

        amountTextView = binding.amount
        coinsInvestedInRecylerView = binding.coinsInvestedInRecyclerView

        portfolioViewModelFactory = PortfolioViewModelFactory(database)
        portfolioViewModel = ViewModelProvider(this,portfolioViewModelFactory).get(PortfolioViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val coinsInvestedInAdapter  = CoinsInvestedInAdapter()

        coinsInvestedInRecylerView.layoutManager = LinearLayoutManager(view.context.applicationContext)
        coinsInvestedInRecylerView.adapter = coinsInvestedInAdapter
        coinsInvestedInRecylerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        portfolioViewModel.totalAmount.observe(viewLifecycleOwner){
            it?.let{
                amountTextView.text = getString(R.string.rupee) + it.toString()
            }
        }

        portfolioViewModel.coinsInvestedInList.observe(viewLifecycleOwner){
            it?.let {
                coinsInvestedInAdapter.refreshCoinsInvestedInList(it)
            }
        }




    }

}