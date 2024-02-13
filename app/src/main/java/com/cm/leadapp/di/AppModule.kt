package com.cm.leadapp.di

import android.content.Context
import com.cm.leadapp.api.ApiHelper
import com.cm.leadapp.api.ApiHelperImpl
import com.cm.leadapp.api.LeadApiInterface
import com.cm.leadapp.data.pref.MySharedPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(LeadApiInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .build()

    @Provides
    @Singleton
    fun provideLeadApi(retrofit: Retrofit): LeadApiInterface =
        retrofit.create(LeadApiInterface::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(service: LeadApiInterface): ApiHelper = ApiHelperImpl(service)

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): MySharedPref =
        MySharedPref(context)
}
