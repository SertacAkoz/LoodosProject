package com.sertac.loodosproject.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.sertac.loodosproject.model.Coin
import com.sertac.loodosproject.model.CoinDetail
import com.sertac.loodosproject.repository.CryptoRepository
import com.sertac.loodosproject.roomdb.CoinRoom
import com.sertac.loodosproject.util.Constants
import com.sertac.loodosproject.util.Event
import com.sertac.loodosproject.util.Resource
import com.sertac.loodosproject.util.SweetAlert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CryptoViewModel @ViewModelInject constructor(
    private val cryptoRepository: CryptoRepository
) : ViewModel() {

    private val getCoinListResponse = MutableLiveData<Resource<List<Coin>>>()
    val coinList : LiveData<Resource<List<Coin>>>
        get() = getCoinListResponse

    private val getCoinDetailResponse = MutableLiveData<Resource<CoinDetail>>()
    val coinDetail : LiveData<Resource<CoinDetail>>
        get() = getCoinDetailResponse

    private val searchCoinResult = MutableLiveData<Event<Coin?>>()
    val searchCoin : LiveData<Event<Coin?>>
        get() = searchCoinResult

    private val favoriteListResult = MutableLiveData<List<Coin>?>()
    val favoriteList : LiveData<List<Coin>?>
        get() = favoriteListResult

    val favoriteCoinIsInserted = MutableLiveData<Boolean>()

    fun getCoinList(){
        viewModelScope.launch {
            getCoinListResponse.value = cryptoRepository.getCoinList()

            getCoinListResponse.value?.let { resource ->
                resource.data?.let { coinList ->
                    cryptoRepository.saveCoinListInFirebaseDatabase(coinList)
                    insertCoinToRoomDB(coinList)
                }
            }

//            cryptoRepository.getCoinList().data?.let {
//                insertCoinToRoomDB(it)
//            }
        }
    }

    fun getCoinDetail(id:String){
        viewModelScope.launch {
            getCoinDetailResponse.value = cryptoRepository.getCoinDetail(id)
        }
    }

    fun searchCoinByName(name : String){
        println("Arama Function Başladı")
        cryptoRepository.searchCoinByName(name){
            println("Arama Function Bitti --> ${it}")
            searchCoinResult.value = Event(it)
        }
    }

    fun searchCoinBySymbol(symbol : String){
        cryptoRepository.searchCoinBySymbol(symbol){

            searchCoinResult.value = Event(it)


//            it?.let {
//                searchCoinResult.value = Event(it)
//            } ?: searchCoinResult.postValue(Event(Coin(Constants.SEARCH_COIN_SYMBOL_PATH,"","","","5.0".toFloat(), "",null)))


        }
    }

    fun exampleInsert(){
        cryptoRepository.exampleInsert()
    }

    fun insertFavoriteCoin(coin:Coin){
        cryptoRepository.insertFavoriteCoin(coin){
            favoriteCoinIsInserted.value = it
        }
    }

    fun getCurrentUserFavoriteList(){
        cryptoRepository.getCurrentUserFavoriteList {
            favoriteListResult.value = it
        }
    }


//    private suspend fun insertCoinToRoomDB(coinList: List<Coin>) = viewModelScope.launch {
//        cryptoRepository.insertCoinToRoomDB(coinList)
//    }

    private suspend fun insertCoinToRoomDB(coinList: List<Coin>) = GlobalScope.launch(Dispatchers.IO) {
        cryptoRepository.insertCoinToRoomDB(coinList)
    }

    fun searchCoinByNameSqLite(name : String) = GlobalScope.launch(Dispatchers.IO) {
        searchCoinResult.postValue(Event(cryptoRepository.searchCoinByNameSqLite(name)))
    }

    fun searchCoinBySymbolSqLite(symbol : String) = GlobalScope.launch(Dispatchers.IO) {

//        val abc = cryptoRepository.searchCoinBySymbolSqLite(symbol)
        searchCoinResult.postValue(Event(cryptoRepository.searchCoinBySymbolSqLite(symbol)))
    }
}