package com.sertac.loodosproject.view.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.sertac.loodosproject.R
import com.sertac.loodosproject.adapter.AuthAdapter
import com.sertac.loodosproject.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
//    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val actionBar = supportActionBar
//        actionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("")))
        actionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.gradientEndColor)))
        actionBar.hide()
        configureActivity()
//        firebaseAuth = FirebaseAuth.getInstance()
//
//        firebaseAuth.currentUser?.let {
//            println("Current-Email --> ${it.email}")
//        }
//        println("CurrentUser --> ${firebaseAuth.currentUser!!.email}")


//        println("Firebase Auth Instance --> ${firebaseAuth.app}")

//        firebaseAuth.createUserWithEmailAndPassword("asd@gmail.com","123123").addOnCompleteListener {
//            if (it.isSuccessful){
//                println("MainActivity-CreateUser --> Success")
//            }else{
//                println("Exception-MainActivity-CreateUser --> ${it.exception}")
//            }
//        }

//        firebaseAuth.signInWithEmailAndPassword("asd@gmail.com","123123").addOnCompleteListener {
//            if (it.isSuccessful){
//                println("MainActivity-Login --> Success")
//                println(it.result)
//            }else{
//                println("Exception-MainActivity-Login --> ${it.exception}")
//                println(it.result)
//            }
//        }
//        firebaseAuth.signOut()

    }

    override fun onResume() {
        super.onResume()

    }

    private fun configureActivity(){
        var fragmentManager: FragmentManager? = supportFragmentManager
//        println("Example asdasdasdasd")

        val adapter = AuthAdapter(fragmentManager!!, lifecycle)

        binding.viewPager.adapter = adapter

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Login"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Register"))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    binding.viewPager.setCurrentItem(it.position)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })
    }
}