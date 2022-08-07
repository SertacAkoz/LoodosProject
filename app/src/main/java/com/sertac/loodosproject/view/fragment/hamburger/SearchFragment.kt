package com.sertac.loodosproject.view.fragment.hamburger

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.sertac.loodosproject.R
import com.sertac.loodosproject.alert.CoinDialog
import com.sertac.loodosproject.databinding.FragmentListBinding
import com.sertac.loodosproject.databinding.FragmentSearchBinding
import com.sertac.loodosproject.util.SweetAlert
import com.sertac.loodosproject.viewmodel.CryptoViewModel
import javax.inject.Inject

class SearchFragment @Inject constructor(
    private val glide : RequestManager
) : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    private lateinit var viewModel : CryptoViewModel

    private var control : Int? = null

    companion object{
        private var view:View? =  null

        private fun setView(view:View){
            this.view = view
        }

        fun getView():View?{
            return this.view
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareFragment()
        viewModel = ViewModelProvider(requireActivity()).get(CryptoViewModel::class.java)

//        viewModel.exampleInsert()


        onClicks()
        observeLiveData()


    }

    private fun prepareFragment(){
        setView(binding.root)
    }

    private fun onClicks(){
        binding.btnSearch.setOnClickListener {
            control = 1
            if (binding.inputCoinName.text.isNotEmpty() && binding.inputCoinSymbol.text.isNotEmpty()){
                SweetAlert.errorPopup(context, "Error", "Please fill only 1 input.")
            }else {
                if (binding.inputCoinName.text.isNotEmpty()){
//                    viewModel.searchCoinByName(binding.inputCoinName.text.toString())
                    viewModel.searchCoinByName(binding.inputCoinName.text.toString())
                }else if (binding.inputCoinSymbol.text.isNotEmpty()){
//                    viewModel.searchCoinBySymbol(binding.inputCoinSymbol.text.toString())
                    viewModel.searchCoinBySymbol(binding.inputCoinSymbol.text.toString())
                }
            }
        }
    }

    private fun observeLiveData(){
        viewModel.searchCoin.observe(viewLifecycleOwner, Observer { coin ->
            if (control == 1){
                context?.let { context ->
                    coin?.let {
                        it.getContentIfNotHandled()?.let { data ->
                            val dialog = CoinDialog(context, glide, data, 1)
                            dialog.show()
                        }?: SweetAlert.errorPopup(context, "Error", "Coin is not exists.")
                    }
                }
            }

        })
    }

    override fun onStop() {
        super.onStop()
        control = null
    }

}