package com.sertac.loodosproject.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sertac.loodosproject.MyFragmentFactory
import com.sertac.loodosproject.R
import com.sertac.loodosproject.backgroundservice.CoinUpdateControlService
import com.sertac.loodosproject.databinding.ActivityHamburgerBinding
import com.sertac.loodosproject.repository.CryptoRepository
import com.sertac.loodosproject.viewmodel.AuthViewModel
import com.sertac.loodosproject.viewmodel.CryptoViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//import com.sertac.loodosproject.view.activity.databinding.ActivityHamburgerBinding

@AndroidEntryPoint
class HamburgerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHamburgerBinding
    private lateinit var viewModel : AuthViewModel

    private lateinit var coinUpdateControlService : Intent

    @Inject
    lateinit var fragmentFactory: MyFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        binding = ActivityHamburgerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHamburger.toolbar)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
//        val actionBar = supportActionBar
//        actionBar?.let {
//            actionBar.hide()
//        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_hamburger)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.listFragment, R.id.searchFragment, R.id.searchRoomFragment, R.id.favoriteFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.navView.getHeaderView(0).findViewById<TextView>(R.id.textViewEmail).text = viewModel.getSharedEmail()

        onClicks()
        observeLiveData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.hamburger, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_hamburger)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun observeLiveData(){
        viewModel.isLoggedOut.observe(this, Observer {
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        })
    }

    private fun onClicks(){
        binding.navView.getHeaderView(0).findViewById<LinearLayout>(R.id.layoutLogOut).setOnClickListener {
            viewModel.logOut()
        }
    }

    override fun onStart() {
        super.onStart()
        coinUpdateControlService = Intent(this, CoinUpdateControlService::class.java)
        startService(coinUpdateControlService)
    }

    override fun onStop() {
        super.onStop()
        stopService(coinUpdateControlService)
    }
}