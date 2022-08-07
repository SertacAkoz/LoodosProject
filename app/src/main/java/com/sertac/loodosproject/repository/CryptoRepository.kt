package com.sertac.loodosproject.repository

import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.sertac.loodosproject.api.CryptoAPI
import com.sertac.loodosproject.model.Coin
import com.sertac.loodosproject.model.CoinDetail
import com.sertac.loodosproject.roomdb.CoinDao
import com.sertac.loodosproject.roomdb.CoinRoom
import com.sertac.loodosproject.util.Constants
import com.sertac.loodosproject.util.CustomSharedPreferences
import com.sertac.loodosproject.util.Resource
import javax.inject.Inject

class CryptoRepository @Inject constructor(
    private val cryptoAPI : CryptoAPI,
    private val cryptoDao: CoinDao,
    private val customSharedPreferences: CustomSharedPreferences
) {

//    private var firebaseDatabase: DataBaseReference = FirebaseAuth.getInstance()
    private var firebaseDatabase = FirebaseDatabase.getInstance("https://loodos-1f74e-default-rtdb.europe-west1.firebasedatabase.app")

    suspend fun updateDatabases(){

        try {
            val response = cryptoAPI.getCoinList()
            if (response.isSuccessful){
                response.body()?.let {
                    saveCoinListInFirebaseDatabase(it)
                    insertCoinToRoomDB(it)
                }

            }else{
                println("Exception-CryptoRepository-updateDatabases --> ${response.message()}")
            }
        }catch (e:Exception){
            println("Exception-CryptoRepository-updateDatabases-catch --> ${e}")
        }
    }

    fun saveCoinListInFirebaseDatabase(coinList : List<Coin>){

        for (item in coinList){
            firebaseDatabase.getReference(Constants.SEARCH_COIN_NAME_PATH).child(item.name).setValue(item).addOnCompleteListener {
                println("CryptoRepository-saveCoinListInFirebaseDatabase-CoinsName --> Bitti")
            }

            firebaseDatabase.getReference(Constants.SEARCH_COIN_SYMBOL_PATH).child(item.symbol).setValue(item).addOnCompleteListener {
                println("CryptoRepository-saveCoinListInFirebaseDatabase-CoinsSymbol --> Bitti")
            }
        }

    }

    fun searchCoinByName(name : String, myCallback: (result: Coin?) -> Unit) {
        firebaseDatabase.getReference(Constants.SEARCH_COIN_NAME_PATH).child(name).get().addOnSuccessListener {
            if (it.exists()){

                val id = it.child("id").value
                val currentPrice = it.child("currentPrice").value
                val imageUrl = it.child("imageUrl").value
                val lastUpdate = it.child("lastUpdate").value
                val name = it.child("name").value
                val symbol = it.child("symbol").value
                val coin = Coin(id.toString(), symbol.toString(), name.toString(), imageUrl.toString(), currentPrice.toString().toFloat(), lastUpdate.toString(), null)

                myCallback.invoke(coin)
            }else {
                myCallback.invoke(null)
            }

        }
    }

    fun searchCoinBySymbol(symbol : String, myCallback: (result: Coin?) -> Unit){
        firebaseDatabase.getReference(Constants.SEARCH_COIN_SYMBOL_PATH).child(symbol).get().addOnSuccessListener {
            if (it.exists()){
                val id = it.child("id").value
                val currentPrice = it.child("currentPrice").value
                val imageUrl = it.child("imageUrl").value
                val lastUpdate = it.child("lastUpdate").value
                val name = it.child("name").value
                val symbol = it.child("symbol").value
                val coin = Coin(id.toString(), symbol.toString(), name.toString(), imageUrl.toString(), currentPrice.toString().toFloat(), lastUpdate.toString(), null)

                myCallback.invoke(coin)
            }else {
                myCallback.invoke(null)
            }
        }
    }

    fun insertFavoriteCoin(coin : Coin, myCallback: (result: Boolean) -> Unit){
        val email : String = customSharedPreferences.getEmail().split("@")[0]
        firebaseDatabase.getReference(Constants.FAVORITE_PATH).child(email).child(coin.name).setValue(coin).addOnSuccessListener {
            println("CryptoRepository-insertFavoriteCoin --> Success")
            myCallback.invoke(true)
        }.addOnFailureListener {
            println("CryptoRepository-insertFavoriteCoin --> Fail")
            myCallback.invoke(false)
        }

    }

    fun exampleInsert(){
        var listCoin = arrayListOf<Coin>()
        val newCoin = Coin("SetoMeto","seto","SetoCoin","https://google.com",1650.5.toFloat(),"lastupdate",null)
        val newCoin2 = Coin("SetoMeto2","seto2","SetoCoin2","https://google.com",1650.5.toFloat(),"lastupdate2",null)
        listCoin.add(newCoin)
        listCoin.add(newCoin2)
//        firebaseDatabase.getReference("Coins").child("SetoCoin").setValue(newCoin).addOnSuccessListener {
//            println("CryptoRepository-deneme --> Success")
//        }.addOnFailureListener {
//            println("CryptoRepository-deneme --> Fail")
//        }

        firebaseDatabase.getReference("Example").setValue(listCoin).addOnSuccessListener {
            println("CryptoRepository-deneme --> Success")
        }.addOnFailureListener {
            println("CryptoRepository-deneme --> Fail")
        }
    }

    fun getCurrentUserFavoriteList(myCallback: (result: List<Coin>?) -> Unit){
        println("exampleGet-exampleGet-exampleGet-exampleGet-exampleGet-exampleGet")
        val email : String = customSharedPreferences.getEmail().split("@")[0]
        firebaseDatabase.getReference(Constants.FAVORITE_PATH).child(email).get().addOnSuccessListener {

            if (it.exists()){
                var arrayList = arrayListOf<Coin>()

                for(item in it.children){
                    println(item.value) // item.child().value
                    arrayList.add(Coin(
                        item.child("id").value.toString(),
                        item.child("symbol").value.toString(),
                        item.child("name").value.toString(),
                        item.child("imageUrl").value.toString(),
                        item.child("currentPrice").value.toString().toFloat(),
                        item.child("lastUpdate").value.toString(),
                        null
                    ))
                }

                myCallback.invoke(arrayList)
            }else {
                myCallback.invoke(null)
            }
        }

    }

    suspend fun getCoinList(): Resource<List<Coin>> {
        return try {
            val response = cryptoAPI.getCoinList()
            if (response.isSuccessful){
                response.body()?.let {

                    return@let Resource.success(response.body())
                } ?: Resource.error("Response is null", null)

            }else{
                println("Exception-CryptoRepository-ResponseNotSuccess --> ${response.message()}")
                Resource.error(response.message(), null)
            }
        }catch (e:Exception){
            println("Exception-CryptoRepository-catch --> ${e}")
            Resource.error(e.message.toString(), null)
        }
    }

    suspend fun getCoinDetail(id:String): Resource<CoinDetail> {
        return try {
            val response = cryptoAPI.getCoinDetail(id)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(response.body())
                } ?: Resource.error("Response is null", null)

            }else{
                println("Exception-CryptoRepository-ResponseNotSuccess --> ${response.message()}")
                Resource.error(response.message(), null)
            }
        }catch (e:Exception){
            println("Exception-CryptoRepository-catch --> ${e}")
            Resource.error(e.message.toString(), null)
        }
    }


    suspend fun insertCoinToRoomDB(coinList:List<Coin>): Resource<List<Coin>> {
        val returnList = cryptoDao.insertCoin(*coinList.toTypedArray())

        var i = 0
        while (i < coinList.size){
            coinList[i].roomId = returnList[i].toInt()
            i += 1
        }

        return Resource.success(coinList)
    }

    suspend fun searchCoinByNameSqLite(name:String): Coin {
        println("Searching-------------------------------------------")
        return cryptoDao.searchByName(name)

    }

    suspend fun searchCoinBySymbolSqLite(symbol:String): Coin {
        return cryptoDao.searchBySymbol(symbol)
    }

}