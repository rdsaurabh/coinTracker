package com.file.saurabh.cointracker.utils

import com.file.saurabh.cointracker.R
import java.util.*

class MainActivityUtils {
    private  val set = HashSet<Int>()

    fun getAppConfigSet() : HashSet<Int>{
        if(set.isEmpty()){
            set.add(R.id.allCoins)
            set.add(R.id.wishlist)
            set.add(R.id.portfolio)
            set.add(R.id.alerts)
        }
        return  set
    }

}
