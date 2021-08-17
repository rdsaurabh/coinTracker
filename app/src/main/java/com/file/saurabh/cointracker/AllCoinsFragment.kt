package com.file.saurabh.cointracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.file.saurabh.cointracker.adapters.AllCoinsRecyclerViewAdapter
import com.file.saurabh.cointracker.databinding.FragmentAllCoinsBinding
import com.file.saurabh.cointracker.viewmodels.AllCoinsViewModel


class AllCoinsFragment : Fragment() {
    private lateinit var allCoinsViewModel: AllCoinsViewModel
    private lateinit var allCoinsRecyclerView : RecyclerView
    private lateinit var allCoinsBindingUtil: FragmentAllCoinsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        allCoinsBindingUtil = DataBindingUtil.inflate(inflater,R.layout.fragment_all_coins,container,false)
        allCoinsViewModel = ViewModelProvider(this).get(AllCoinsViewModel::class.java)
        allCoinsRecyclerView = allCoinsBindingUtil.allCoinsRecyclerView
        return allCoinsBindingUtil.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allCoinsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        val allCoinsRecyclerViewAdapter  = AllCoinsRecyclerViewAdapter()

        allCoinsRecyclerView.adapter = allCoinsRecyclerViewAdapter
        allCoinsRecyclerView.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))



        allCoinsViewModel.marketsProperty.observe(viewLifecycleOwner){

            it?.let {
                allCoinsRecyclerViewAdapter.updateCoinsList(it.markets)
            }

        }

    }


}