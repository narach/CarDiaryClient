package com.example.cardiaryclient.di

import com.example.cardiaryclient.BuildConfig
import com.example.cardiaryclient.api.CarsApiHelper
import com.example.cardiaryclient.api.CarsApiHelperImpl
import com.example.cardiaryclient.api.CarsApiService
import com.example.cardiaryclient.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module // Dagger Hilt - Configure components to inject
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = Constants.carServiceBaseurl

    @Provides
    @Singleton
    fun provideOkHttpClient() =
        if (BuildConfig.DEBUG) {
            val loggingInterceptor= HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        } else {
            OkHttpClient.Builder().build()
        }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, carServiceBaseUrl: String) : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(carServiceBaseUrl)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideCarsApiService(retrofit: Retrofit) =
        retrofit.create(CarsApiService::class.java)

    @Singleton
    @Provides
    fun provideCarsApiHelper(apiService: CarsApiService) : CarsApiHelper =
        CarsApiHelperImpl(carsApiService = apiService)
}