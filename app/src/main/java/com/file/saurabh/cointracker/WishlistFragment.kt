package com.file.saurabh.cointracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.file.saurabh.cointracker.adapters.WishlistRecyclerViewAdapter
import com.file.saurabh.cointracker.database.CoinDatabase
import com.file.saurabh.cointracker.databinding.FragmentWishlistBinding
import com.file.saurabh.cointracker.utils.WishlistItemTouchHelperAssistant
import com.file.saurabh.cointracker.viewmodels.WishlistViewModel
import com.file.saurabh.cointracker.viewmodels.WishlistViewModelFactory


class WishlistFragment : Fragment() {
    private  lateinit var  wishlistRecyclerView: RecyclerView
    private lateinit var wishlistViewModel: WishlistViewModel
    private lateinit var wishlistViewModelFactory:  WishlistViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentWishlistBinding>(inflater,R.layout.fragment_wishlist,container,false)

        wishlistRecyclerView = binding.wishlistRecyclerView
        wishlistViewModelFactory = WishlistViewModelFactory(CoinDatabase.getInstance(requireContext().applicationContext))

        wishlistViewModel = ViewModelProvider(this,wishlistViewModelFactory)
                .get(WishlistViewModel::class.java)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val wishlistRecyclerViewAdapter = WishlistRecyclerViewAdapter(this)
        wishlistRecyclerView.layoutManager = LinearLayoutManager(view.context.applicationContext)
        wishlistRecyclerView.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
        wishlistRecyclerView.adapter = wishlistRecyclerViewAdapter

        //attaching swipe delete functionality
        val itemTouchHelperAssistant = WishlistItemTouchHelperAssistant(wishlistRecyclerViewAdapter, ItemTouchHelper.LEFT)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperAssistant)
        itemTouchHelper.attachToRecyclerView(wishlistRecyclerView)

        wishlistViewModel.wishlist.observe(viewLifecycleOwner){
            it?.let {
                wishlistRecyclerViewAdapter.refreshWishlist(it)
            }
        }






    }

}