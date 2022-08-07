package com.sertac.loodosproject.view.fragment.hamburger

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sertac.loodosproject.R
import com.sertac.loodosproject.adapter.ListRecyclerAdapter
import com.sertac.loodosproject.databinding.FragmentListBinding
import com.sertac.loodosproject.databinding.FragmentLoginBinding
import com.sertac.loodosproject.util.Status
import com.sertac.loodosproject.util.SweetAlert
import com.sertac.loodosproject.viewmodel.CryptoViewModel
import javax.inject.Inject

class ListFragment @Inject constructor(
    val listRecyclerAdapter: ListRecyclerAdapter
) : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var viewModel : CryptoViewModel

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
        binding = FragmentListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preapareFragment()
        binding.listRecyclerView.adapter = listRecyclerAdapter
        binding.listRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(requireActivity()).get(CryptoViewModel::class.java)
        viewModel.getCoinList()

        observeLiveData()

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getCoinList()
            binding.swipeRefreshLayout.isRefreshing = false
        }

    }

    private fun observeLiveData(){
        viewModel.coinList.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    it.data?.let { list ->
                        listRecyclerAdapter.coinList = list
                    }
                }
                Status.ERROR -> {
                    SweetAlert.errorPopup(context, "Error", it.message)
                }
            }
        })
    }

    private fun preapareFragment(){
        setView(binding.root)
    }
}