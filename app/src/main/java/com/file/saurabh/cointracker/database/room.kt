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

@Entity(tableName = "alert")
data class Alert(
        @PrimaryKey(autoGenerate = true)
        val alertId : Long = 0L,
        val coinCode : String,
        val alertPrice : Int,
        val alertType : Int
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
interface AlertDao{
    @Insert
    fun insert(alert : Alert)

    @Update
    fun update(alert : Alert)
    @Delete
    fun delete(alert : Alert)

    @Query("SELECT * FROM alert")
    fun getListOfAlerts() : LiveData<List<Alert>>
}

@Database(entities = [Alert::class,CoinData::class], version = 8,exportSchema = false)
abstract class CoinDatabase : RoomDatabase() {

    abstract val coinDao: CoinDao
    abstract val alertDao : AlertDao

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