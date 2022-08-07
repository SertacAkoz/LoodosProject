package com.sertac.loodosproject.alert

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.bumptech.glide.RequestManager
import com.sertac.loodosproject.databinding.CoinDialogBinding
import com.sertac.loodosproject.model.Coin
import com.sertac.loodosproject.model.CoinDetail
import com.sertac.loodosproject.navigation.HamburgerNavigationActions
import javax.inject.Inject

class CoinDialog @Inject constructor(
    contex: Context,
    private val glide : RequestManager,
    val coin:Coin,
    val control:Int
) : Dialog(contex) {

    private lateinit var binding: CoinDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = CoinDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClicks()
        prepareView()

        println("CoinDialog-Control --> $control")
    }

    private fun prepareView(){
        binding.textCoinName.text = coin.name
        binding.textCoinPrice.text = "${coin.currentPrice} USD"

        glide.load(coin.imageUrl).into(binding.coinImage)
    }

    private fun onClicks(){
        binding.btnGoDetail.setOnClickListener {

            if (control == 1){
                HamburgerNavigationActions.actionSearchFragmentToDetailFragment(coin.id)
            }else if ( control == 2 ) {
                HamburgerNavigationActions.actionSearchRoomFragmentToDetailFragment(coin.id)
            }

            this.dismiss()
        }
    }
}