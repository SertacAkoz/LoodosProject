package com.sertac.loodosproject.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sertac.loodosproject.view.fragment.main.LoginFragment
import com.sertac.loodosproject.view.fragment.main.RegisterFragment

class AuthAdapter(
    fragmentManager: FragmentManager,
    lifeCycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifeCycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 1){
            return RegisterFragment()
        }

        return LoginFragment()
    }
}