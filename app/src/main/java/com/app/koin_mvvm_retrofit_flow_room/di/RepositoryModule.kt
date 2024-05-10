package com.app.koin_mvvm_retrofit_flow_room.di

import com.app.koin_mvvm_retrofit_flow_room.data.Repository
import com.app.koin_mvvm_retrofit_flow_room.data.remote.RemoteDataSource
import org.koin.dsl.module


val repositoryModule = module {
    factory {  Repository(get(),get()) }
}


// Path: app/src/main/java/com/app/koin_mvvm_retrofit_flow_room/di/RepositoryModule.kt
// check https://github.com/Droid-Thiru/Android-Clean-Architecture-MVVM-Koin-Room-Rxjava-DataBinding/blob/master/app/src/main/java/com/thiru/cleanarchisample/di/Module.kt#L35

//val repositoryModule = module {
//    single<IRepository> { RepositoryImpl(get()) }
//}