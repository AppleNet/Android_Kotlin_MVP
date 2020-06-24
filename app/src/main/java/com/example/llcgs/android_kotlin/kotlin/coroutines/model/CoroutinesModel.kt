package com.example.llcgs.android_kotlin.kotlin.coroutines.model;

import com.example.llcgs.android_kotlin.base.model.BaseModel
import com.example.llcgs.android_kotlin.base.network.RetrofitHelper
import com.example.llcgs.android_kotlin.kotlin.variable.bean.Repo

class CoroutinesModel: BaseModel   {

    suspend fun getUser(): List<Repo> {
        return RetrofitHelper.getService(RetrofitHelper.GITHUB_BASE_URL).listReposRetrofit("AppleNet")
    }
}
