package com.app.koin_mvvm_retrofit_flow_room.data.remote


import android.content.Context
import com.app.koin_mvvm_retrofit_flow_room.utils.Constants.Companion.API_INTERNET_MESSAGE
import com.app.koin_mvvm_retrofit_flow_room.utils.NetWorkResult
import com.app.koin_mvvm_retrofit_flow_room.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response



inline fun <reified T> toResultFlow(isInternetConnected: Boolean, crossinline call: suspend () -> Response<T>): Flow<NetWorkResult<T>> {
    return flow {
        if (isInternetConnected) {
            emit(NetWorkResult.Loading(true))
            try {
                val c = call()
                if (c.isSuccessful && c.body() != null) {
                    emit(NetWorkResult.Success(c.body()))
                } else {
                    emit(NetWorkResult.Error(c.message()))
                }
            } catch (e: Exception) {
                emit(NetWorkResult.Error(e.toString()))
            }
        } else {
            emit(NetWorkResult.Error(API_INTERNET_MESSAGE))
        }
    }.flowOn(Dispatchers.IO)
}

