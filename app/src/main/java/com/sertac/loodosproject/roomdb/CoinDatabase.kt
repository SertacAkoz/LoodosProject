package com.sertac.loodosproject.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sertac.loodosproject.model.Coin

@Database(entities = [Coin::class], version = 1)
abstract class CoinDatabase : RoomDatabase(){
    abstract fun coinDao():CoinDao
}