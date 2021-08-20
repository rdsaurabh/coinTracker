package com.file.saurabh.cointracker.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "coin_table")
data class CoinData(
        @PrimaryKey
        val coinCode : String,
        val lowPrice : String,
        val highPrice : String,
        val lastPrice : String,
)

@Entity(tableName = "key_price")
data class KeyPrice(
        @PrimaryKey(autoGenerate = true)
        val keyPriceId : Long = 0L,
        val coinCode : String,
        val keyPriceAmount : Long,
        val keyPriceType : Int
)

@Entity(tableName = "transactions")
data class Transactions(
        @PrimaryKey(autoGenerate = true)
        val transactionId : Long = 0L,
        val coinCode : String,
        val transactionType : Int,
        val transactionAmount : Long
)

@Dao
interface CoinDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(coin : CoinData)

    @Update
    fun update(coin: CoinData)

    @Query("SELECT * FROM coin_table")
    fun getListOfCoinData() : LiveData<List<CoinData>>

    @Delete
    fun delete(coin: CoinData)
}

@Dao
interface KeyPriceDao{
    @Insert
    fun insert(keyPrice : KeyPrice)

    @Update
    fun update(keyPrice : KeyPrice)

    @Delete
    fun delete(keyPrice : KeyPrice)

    @Query("SELECT * FROM key_price")
    fun getListOfAlerts() : LiveData<List<KeyPrice>>
}

@Dao
interface TransactionsDao {

    @Insert
    fun insert(transaction : Transactions)

    @Update
    fun update(transaction: Transactions)

    @Delete
    fun delete(transaction: Transactions)

    @Query("SELECT sum(transactionAmount) from transactions")
    fun totalInvestedAmount() : LiveData<Long>

    @Query("SELECT * FROM transactions")
    fun getListOfTransactions() : LiveData<List<Transactions>>

    @Query("SELECT coinCode,sum(transactionAmount) FROM transactions GROUP BY coinCode ORDER BY sum(transactionAmount) DESC")
    fun getListOfAmountInvestedPerCoin() : LiveData<List<AmountPerCoin>>

    @Query("SELECT * FROM transactions WHERE coinCode = :coinCode")
    fun getTransactionsOfParticularCoin(coinCode: String) : LiveData<List<Transactions>>

}

/*in case you only need a few columns' data instead of every column go for a model which is specific
    for those columns.This model handles case when only two particular colums are needed
 */
data class AmountPerCoin(
        val coinCode : String,
        @ColumnInfo(name = "sum(transactionAmount)" )
        val amount : Long
)


@Database(entities = [KeyPrice::class,CoinData::class,Transactions::class], version = 19,exportSchema = false)
abstract class CoinDatabase : RoomDatabase() {

    abstract val coinDao: CoinDao
    abstract val keyPriceDao : KeyPriceDao
    abstract val transactionsDao : TransactionsDao

    companion object {

        @Volatile
        private var INSTANCE: CoinDatabase? = null

        fun getInstance(context: Context): CoinDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            CoinDatabase::class.java,
                            "coin_database"
                    )
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}


