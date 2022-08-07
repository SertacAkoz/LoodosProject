package com.sertac.loodosproject

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.sertac.loodosproject.adapter.FavoriteRecyclerAdapter
import com.sertac.loodosproject.adapter.ListRecyclerAdapter
import com.sertac.loodosproject.backgroundservice.CoinUpdateControlService
import com.sertac.loodosproject.repository.CryptoRepository
import com.sertac.loodosproject.view.activity.ui.home.HomeFragment
import com.sertac.loodosproject.view.fragment.hamburger.*
import javax.inject.Inject

class MyFragmentFactory @Inject constructor(
    private val listRecyclerAdapter: ListRecyclerAdapter,
    private val glide : RequestManager,
    private val favoriteRecyclerAdapter: FavoriteRecyclerAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ListFragment::class.java.name -> ListFragment(listRecyclerAdapter)
            DetailFragment::class.java.name -> DetailFragment(glide)
            SearchFragment::class.java.name -> SearchFragment(glide)
            FavoriteFragment::class.java.name -> FavoriteFragment(favoriteRecyclerAdapter)
            SearchRoomFragment::class.java.name -> SearchRoomFragment(glide)
            else -> super.instantiate(classLoader, className)
        }
    }
}