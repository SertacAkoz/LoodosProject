package com.sertac.loodosproject.backgroundservice

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ServiceLifecycleDispatcher
import androidx.lifecycle.ViewModelProvider
import com.sertac.loodosproject.repository.CryptoRepository
import com.sertac.loodosproject.viewmodel.AuthViewModel
import com.sertac.loodosproject.viewmodel.CryptoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class CoinUpdateControlService : Service() {

    @Inject
    lateinit var cryptoRepository: CryptoRepository

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()

        Thread(Runnable {
            kotlin.run {
                while (true){

                    try {
                        println("CoinUpdateControlService --> Working...")
                        Thread.sleep(120000)
                        GlobalScope.launch(Dispatchers.IO) {
                            cryptoRepository.updateDatabases()
                        }
                    }catch (e: Exception){
                        println("Exception-CoinUpdateControlService --> ${e}")
                    }
                }
            }
        }).start()

    }


}