package com.mustafaunlu.movieapp.di

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.mustafaunlu.movieapp.pref.SessionManager
import com.mustafaunlu.movieapp.repo.retrofit.RetrofitServiceInstance
import com.mustafaunlu.movieapp.util.Constants
import com.mustafaunlu.movieapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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


    //FIREBASE

    @Provides
    @Singleton
    fun provideFirestoreInstance() : FirebaseFirestore{
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideStorageInstance() : FirebaseStorage{
        return Firebase.storage
    }
    @Provides
    @Singleton
    fun provideAuthInstance() : FirebaseAuth{
        return Firebase.auth
    }

    //RETROFIT

    @Provides
    @Singleton
    fun getRetrofitServiceInstance(retrofit: Retrofit) :RetrofitServiceInstance{
        return retrofit.create(RetrofitServiceInstance::class.java)
    }

    @Provides
    @Singleton
    fun getRetrofitInstance() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }









}