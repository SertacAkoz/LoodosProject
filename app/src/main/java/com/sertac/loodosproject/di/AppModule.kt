package com.sertac.loodosproject.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.sertac.loodosproject.R
import com.sertac.loodosproject.api.CryptoAPI
import com.sertac.loodosproject.repository.AuthRepository
import com.sertac.loodosproject.repository.CryptoRepository
import com.sertac.loodosproject.roomdb.CoinDao
import com.sertac.loodosproject.roomdb.CoinDatabase
import com.sertac.loodosproject.util.Constants
import com.sertac.loodosproject.util.CustomSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCryptoAPI():CryptoAPI{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(CryptoAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideCoinDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        CoinDatabase::class.java,
        "CoinDB"
    ).build()

    @Singleton
    @Provides
    fun provideCoinDao(database : CoinDatabase) = database.coinDao()

    @Singleton
    @Provides
    fun provideAuthRepository(customSharedPreferences: CustomSharedPreferences) = AuthRepository(customSharedPreferences)

    @Singleton
    @Provides
    fun provideCryptoRepository(api:CryptoAPI, dao:CoinDao, customSharedPreferences: CustomSharedPreferences) = CryptoRepository(api, dao, customSharedPreferences)

    @Singleton
    @Provides
    fun provideCustomSP(@ApplicationContext context: Context) = CustomSharedPreferences(context)

    @Singleton
    @Provides
    fun provideGlide(@ApplicationContext context: Context): RequestManager {
        return Glide.with(context)
            .setDefaultRequestOptions(
                RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
            )
    }


}