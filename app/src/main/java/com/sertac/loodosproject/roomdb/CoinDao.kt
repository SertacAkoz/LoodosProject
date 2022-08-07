package com.sertac.loodosproject.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sertac.loodosproject.model.Coin

@Dao
interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoin(vararg coin: Coin) : List<Long>

//    @Query("SELECT * FROM coins WHERE name LIKE '% :arg0 %' ")
//    fun searchByName(name : String) : LiveData<List<Coin>>

//    @Query("SELECT * FROM coins WHERE symbol LIKE '% :arg0 %' ")
//    fun searchBySymbol(symbol : String) : LiveData<List<Coin>>

    @Query("SELECT * FROM coins WHERE name = :paramName")
    fun searchByName(paramName : String) : Coin

    @Query("SELECT * FROM coins WHERE symbol = :paramSymbol")
    fun searchBySymbol(paramSymbol : String) : Coin




}