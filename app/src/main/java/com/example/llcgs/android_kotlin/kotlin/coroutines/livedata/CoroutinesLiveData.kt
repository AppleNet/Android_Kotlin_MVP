package com.example.llcgs.android_kotlin.kotlin.coroutines.livedata;

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.llcgs.android_kotlin.base.network.RetrofitHelper
import com.example.llcgs.android_kotlin.kotlin.variable.bean.Repo
import kotlinx.coroutines.*

class CoroutinesLiveData: ViewModel() {

    val repos = object : LiveData<List<Repo>>(){

        public suspend fun emit(): List<Repo>  {
            return withContext(Dispatchers.IO) {
                loadUsers()
            }
        }
    }

    private suspend fun loadUsers(): List<Repo> {
        return RetrofitHelper.getService(RetrofitHelper.GITHUB_BASE_URL)
                .listReposRetrofit("AppleNet")
    }
}
