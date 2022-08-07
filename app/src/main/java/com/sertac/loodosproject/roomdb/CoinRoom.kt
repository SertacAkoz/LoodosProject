package com.sertac.loodosproject.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "coins")
data class CoinRoom(
    var id : String,
    var symbol : String,
    var name : String,
    var imageUrl : String,
    var currentPrice : Float,
    var lastUpdate : String,
    @PrimaryKey(autoGenerate = true)
    var roomId : Int? = null
)
