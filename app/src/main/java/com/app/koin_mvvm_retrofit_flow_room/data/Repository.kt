package com.app.koin_mvvm_retrofit_flow_room.data

import android.content.Context
import com.app.koin_mvvm_retrofit_flow_room.data.remote.RemoteDataSource
import com.app.koin_mvvm_retrofit_flow_room.data.remote.toResultFlow
import com.app.koin_mvvm_retrofit_flow_room.domain.NetworkUtils
import com.app.koin_mvvm_retrofit_flow_room.utils.NetWorkResult
import kotlinx.coroutines.flow.Flow

class Repository(private val remoteDataSource: RemoteDataSource,
                 private val networkUtils: NetworkUtils
) {

    suspend fun getPostList(): Flow<NetWorkResult<List<Post>>> {
        return toResultFlow(networkUtils.hasInternetConnection()){
            remoteDataSource.getPosts()
        }
    }

}