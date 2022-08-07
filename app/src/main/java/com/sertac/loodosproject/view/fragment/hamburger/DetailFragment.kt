package com.sertac.loodosproject.view.fragment.hamburger

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.sertac.loodosproject.R
import com.sertac.loodosproject.databinding.FragmentDetailBinding
import com.sertac.loodosproject.databinding.FragmentListBinding
import com.sertac.loodosproject.model.Coin
import com.sertac.loodosproject.model.CoinDetail
import com.sertac.loodosproject.util.SweetAlert
import com.sertac.loodosproject.util.Utils
import com.sertac.loodosproject.viewmodel.CryptoViewModel
import javax.inject.Inject

class DetailFragment @Inject constructor(
    val glide : RequestManager
) : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel : CryptoViewModel

    private var coinIc:String? = null

    private var fragmentControl = 0
    private var clickControl : Int? = null

    private lateinit var favoriteCoin:Coin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            coinIc = DetailFragmentArgs.fromBundle(it).id
        }

        viewModel = ViewModelProvider(requireActivity()).get(CryptoViewModel::class.java)

        coinIc?.let {
            viewModel.getCoinDetail(it)
        }

        observeLiveData()
        onClicks()
    }

    private fun onClicks(){
        binding.btnAddFavorite.setOnClickListener {
            clickControl = 1
            viewModel.insertFavoriteCoin(favoriteCoin)
        }
    }

    override fun onResume() {
        super.onResume()
        fragmentControl = 1
    }

    override fun onStop() {
        super.onStop()
        fragmentControl = 0
    }

    private fun prepareSpinner(coinDetail:CoinDetail){
        val adapter = ArrayAdapter(activity!!,android.R.layout.simple_spinner_item,getSpinnerItems(coinDetail))

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.coinChangeSpinner.adapter = adapter

        val adapterViewOnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                when(p2){
                    0 -> binding.textDailyChange.text = coinDetail.marketData.priceChangeDaily.toString()
                    1 -> binding.textDailyChange.text = coinDetail.marketData.priceChangeWeekly.toString()
                    2 -> binding.textDailyChange.text = coinDetail.marketData.priceChange14D.toString()
                    3 -> binding.textDailyChange.text = coinDetail.marketData.priceChange30D.toString()
                    4 -> binding.textDailyChange.text = coinDetail.marketData.priceChange60D.toString()
                    5 -> binding.textDailyChange.text = coinDetail.marketData.priceChangeYearly.toString()
                    else -> binding.textDailyChange.text = coinDetail.marketData.priceChangeDaily.toString()
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.coinChangeSpinner.onItemSelectedListener = adapterViewOnItemSelectedListener
//        binding.coinChangeSpinner.setOnItemClickListener { adapterView, view, i, l ->
//            when(i){
//                0 -> binding.textDailyChange.text = coinDetail.marketData.priceChangeDaily.toString()
//                1 -> binding.textDailyChange.text = coinDetail.marketData.priceChangeWeekly.toString()
//                2 -> binding.textDailyChange.text = coinDetail.marketData.priceChange14D.toString()
//                3 -> binding.textDailyChange.text = coinDetail.marketData.priceChange30D.toString()
//                4 -> binding.textDailyChange.text = coinDetail.marketData.priceChange60D.toString()
//                5 -> binding.textDailyChange.text = coinDetail.marketData.priceChangeYearly.toString()
//                else -> binding.textDailyChange.text = coinDetail.marketData.priceChangeDaily.toString()
//            }
//        }
    }

    private fun getSpinnerItems(coinDetail: CoinDetail): List<String> {
        val array = arrayListOf<String>()
//        array.add("Change Daily")
//        array.add("Change 7 Day")
//        array.add("Change 14 Day")
//        array.add("Change 30 Day")
//        array.add("Change 60 Day")
//        array.add("Change 1 Year")

        array.add("Daily Exchange")
        array.add("7 Day Exchange")
        array.add("14 Day Exchange")
        array.add("30 Day Exchange")
        array.add("60 Day Exchange")
        array.add("1 Year Exchange")


        return array
    }

    private fun observeLiveData(){
        viewModel.coinDetail.observe(viewLifecycleOwner, Observer {
            println("DetailFragment")
            println(it.data)
            it.data?.let { coinDetail ->

                prepareSpinner(coinDetail)

                if (fragmentControl == 1){
                    SweetAlert.basicMessage(context, "Hashed Coin Name",Utils.hashInput(coinDetail.name))
                }

                favoriteCoin = Coin(coinDetail.id, coinDetail.symbol, coinDetail.name,coinDetail.image.large,coinDetail.marketData.currentPrice.usd,coinDetail.marketData.lastUpdated, null)

                binding.textCoinName.text = coinDetail.name
                binding.textCoinPrice.text = "${coinDetail.marketData.currentPrice.usd} USD"
                binding.textCoinDescription.text = coinDetail.description.en
                binding.textDailyChange.text = coinDetail.marketData.priceChangeDaily.toString()

                glide.load(coinDetail.image.large).into(binding.coinImage)
            }

        })

        viewModel.favoriteCoinIsInserted.observe(viewLifecycleOwner, Observer {
            if (clickControl == 1){
                if (it){
                    SweetAlert.successPopup(context, "Success", "Coin added to favorite list.")
                }else {
                    SweetAlert.errorPopup(context, "Fail", "Something went wrong.")
                }
            }

        })
    }


}