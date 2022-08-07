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
import com.sertac.loodosproject.adapter.FavoriteRecyclerAdapter
import com.sertac.loodosproject.databinding.FragmentDetailBinding
import com.sertac.loodosproject.databinding.FragmentFavoriteBinding
import com.sertac.loodosproject.databinding.FragmentSearchBinding
import com.sertac.loodosproject.viewmodel.CryptoViewModel
import kotlinx.android.synthetic.main.fragment_favorite.view.*
import javax.inject.Inject

class FavoriteFragment @Inject constructor(
    val favoriteRecyclerAdapter: FavoriteRecyclerAdapter
) : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: CryptoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favoriteRecyclerView.adapter = favoriteRecyclerAdapter
        binding.favoriteRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(requireActivity()).get(CryptoViewModel::class.java)

        viewModel.getCurrentUserFavoriteList()

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.favoriteList.observe(viewLifecycleOwner, Observer {
            it?.let { coinList ->
                favoriteRecyclerAdapter.favoriteList = coinList
            }
        })
    }


}