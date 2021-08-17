package com.file.saurabh.cointracker.models

data class MarketsProperty(
        val assets: List<Asset>,
        val markets: List<Market>
)

data class Asset(
        val category: String,
        val confirmations: Int?,
        val deposit: String,
        val listingType: String,
        val maxWithdrawAmount: Double?,
        val minDepositAmount: Double?,
        val minWithdrawAmount: Double?,
        val name: String,
        val type: String,
        val withdrawFee: Double?,
        val withdrawal: String
)

data class Fee(
        val ask: Any,
        val bid: Any
)

data class Market(
        val at: Int?,
        val baseMarket: String,
        val basePrecision: Int?,
        val buy: String?,
        val fee: Fee?,
        val feePercentOnProfit: Double?,
        val high: String?,
        val last: String?,
        val low: String?,
        val maxBuyAmount: Int?,
        val maxBuyVolume: Int?,
        val minBuyAmount: Double?,
        val minBuyVolume: Int?,
        val minSellAmount: Double?,
        val open: Any?,
        val quoteMarket: String,
        val quotePrecision: Int?,
        val sell: String?,
        val status: String,
        val type: String,
        val volume: String?
)