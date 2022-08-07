package com.sertac.loodosproject.navigation

import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.sertac.loodosproject.util.SweetAlert
import com.sertac.loodosproject.view.fragment.hamburger.*

abstract class HamburgerNavigationActions {
    companion object{
        fun actionListFragmentToDetailFragment(id:String){
            val navDirection : NavDirections = ListFragmentDirections.actionListFragmentToDetailFragment(id)
            ListFragment.getView()?.let {
                Navigation.findNavController(it).navigate(navDirection)
            }
        }

        fun actionSearchFragmentToDetailFragment(id : String){
            val navDirection : NavDirections = SearchFragmentDirections.actionSearchFragmentToDetailFragment(id)
            SearchFragment.getView()?.let {
                Navigation.findNavController(it).navigate(navDirection)
            }
        }

        fun actionSearchRoomFragmentToDetailFragment(id: String){
            val navDirection: NavDirections = SearchRoomFragmentDirections.actionSearchRoomFragmentToDetailFragment(id)
            SearchRoomFragment.getView()?.let {
                Navigation.findNavController(it).navigate(navDirection)
            }
        }
    }
}