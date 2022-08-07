package com.sertac.loodosproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "coins")
data class Coin(
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id : String,
    @ColumnInfo(name = "symbol")
    @SerializedName("symbol")
    val symbol : String,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name : String,
    @ColumnInfo(name = "image")
    @SerializedName("image")
    val imageUrl : String,
    @ColumnInfo(name = "current_price")
    @SerializedName("current_price")
    val currentPrice : Float,
    @ColumnInfo(name = "last_updated")
    @SerializedName("last_updated")
    val lastUpdate : String,
    @PrimaryKey(autoGenerate = true)
    var roomId : Int? = null
)
