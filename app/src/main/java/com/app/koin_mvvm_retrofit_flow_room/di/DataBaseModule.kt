package com.app.koin_mvvm_retrofit_flow_room.di

import android.app.Application
import androidx.room.Room
import com.app.koin_mvvm_retrofit_flow_room.data.AndroidNetworkUtils
import com.app.koin_mvvm_retrofit_flow_room.db.PostDao
import com.app.koin_mvvm_retrofit_flow_room.db.PostDataBase
import com.app.koin_mvvm_retrofit_flow_room.domain.NetworkUtils
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


fun provideDataBase(application: Application): PostDataBase =
     Room.databaseBuilder(
        application,
        PostDataBase::class.java,
        "table_post"
    ).
     fallbackToDestructiveMigration().build()

fun provideDao(postDataBase: PostDataBase): PostDao = postDataBase.getPostDao()


val dataBaseModule= module {
    single { provideDataBase(get()) }
    single { provideDao(get()) }
    single<NetworkUtils> { AndroidNetworkUtils(androidContext()) }
}


