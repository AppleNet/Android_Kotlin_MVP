package com.example.llcgs.android_kotlin.kotlin.coroutines.viewmodel;

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.llcgs.android_kotlin.base.network.RetrofitHelper
import com.example.llcgs.android_kotlin.kotlin.variable.bean.Repo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoroutinesViewModel: ViewModel() {

    private val repo: MutableLiveData<List<Repo>> by lazy {
        MutableLiveData<List<Repo>>().also {
            loadUsers()
        }
    }

    fun getRepos() = repo

    private fun loadUsers() {
        RetrofitHelper.getService(RetrofitHelper.GITHUB_BASE_URL)
                .listRepos("AppleNet")
                .enqueue(object : Callback<List<Repo>?> {
                    override fun onFailure(call: Call<List<Repo>?>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<List<Repo>?>, response: Response<List<Repo>?>) {
                        repo.value = response.body()
                    }
                })
    }
}
