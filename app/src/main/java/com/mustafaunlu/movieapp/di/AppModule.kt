package com.mustafaunlu.movieapp.di

import android.content.Context
import android.content.SharedPreferences
import com.mustafaunlu.movieapp.pref.SessionManager
import com.mustafaunlu.movieapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context : Context): SharedPreferences =
        context.getSharedPreferences(Constants.PREF_NAME,Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideSessionManager(sharedPreferences: SharedPreferences) : SessionManager = SessionManager(sharedPreferences)








}