package com.app.koin_mvvm_retrofit_flow_room.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.koin_mvvm_retrofit_flow_room.data.Post
import com.app.koin_mvvm_retrofit_flow_room.data.Repository
import com.app.koin_mvvm_retrofit_flow_room.db.PostDao
import com.app.koin_mvvm_retrofit_flow_room.utils.NetWorkResult
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository, application: Application, private val dao: PostDao): BaseViewModel(application) {

    private val _responseposts: MutableLiveData<NetWorkResult<List<Post>>> = MutableLiveData()
    val responseposts: LiveData<NetWorkResult<List<Post>>> = _responseposts

    private val _posts: MutableLiveData<List<Post>> = MutableLiveData()
    val posts: LiveData<List<Post>> = _posts

    // get data from api
    fun getPostsList() = viewModelScope.launch {
        repository.getPostList(context).collect { values ->
            _responseposts.value = values
        }
    }

    // insert into room
    fun insertAllPostIntoDb(posts: List<Post>) = viewModelScope.launch {
        dao.insertAllPost(posts)
    }

    //get data from db
    fun getAllPostsFromDb() = viewModelScope.launch {
        dao.getPost().collect { values ->
            _posts.value = values
        }
    }

}