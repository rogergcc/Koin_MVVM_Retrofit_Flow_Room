package com.app.koin_mvvm_retrofit_flow_room.data.remote


class RemoteDataSource constructor(private val apiService: ApiService) {
    suspend fun getPosts() = apiService.getPosts()
}