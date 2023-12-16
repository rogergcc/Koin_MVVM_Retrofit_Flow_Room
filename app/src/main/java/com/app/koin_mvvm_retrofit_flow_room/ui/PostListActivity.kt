package com.app.koin_mvvm_retrofit_flow_room.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.koin_mvvm_retrofit_flow_room.R
import com.app.koin_mvvm_retrofit_flow_room.databinding.ActivityPostListBinding
import com.app.koin_mvvm_retrofit_flow_room.data.ApiResultHandler
import com.app.koin_mvvm_retrofit_flow_room.data.Post
import com.app.koin_mvvm_retrofit_flow_room.viewmodels.MainViewModel
import org.koin.android.ext.android.inject


class PostListActivity : AppCompatActivity() {

    private val mainViewModel : MainViewModel by inject()
    lateinit var activityMainBinding: ActivityPostListBinding
    lateinit var postListAdapter: PostListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this@PostListActivity, R.layout.activity_post_list)
        init()
        getPostsAPI()
        apiGetPosts()
        observePostDBData()
    }

    private fun init() {
        try {
            postListAdapter = PostListAdapter()
            activityMainBinding.list.apply { adapter= postListAdapter }
            activityMainBinding.swipeRefreshLayout.setOnRefreshListener { getPostsAPI() }
        } catch (e: Exception) {
            e.stackTrace
        }
    }


    private fun apiGetPosts() {
        try {
            mainViewModel.responseposts.observe(this) { response ->
                val apiResultHandler = ApiResultHandler<List<Post>>(this@PostListActivity,
                    onLoading = {
                       showProgress(true)
                    },
                    onSuccess = { data ->
                        showProgress(false)
                        data?.let{mainViewModel.insertAllPostIntoDb(it)}
                        getPostsFromDB()
                        activityMainBinding.swipeRefreshLayout.isRefreshing = false
                    },
                    onFailure = {
                        showProgress(false)
                        activityMainBinding.swipeRefreshLayout.isRefreshing = false
                        getPostsFromDB()
                    })
                apiResultHandler.handleApiResult(response)
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    private fun showProgress(isShown:Boolean)= if(isShown) activityMainBinding.progress.visibility = View.VISIBLE else activityMainBinding.progress.visibility = View.GONE

    private fun getPostsAPI() {
        mainViewModel.getPostsList()
    }
    private fun getPostsFromDB() {
        mainViewModel.getAllPostsFromDb()
    }
    private fun observePostDBData() {
        try {
            mainViewModel.posts.observe(this) { data ->
                data?.let { postListAdapter.setPosts(it) }
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }
}