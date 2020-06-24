package com.example.llcgs.android_kotlin.kotlin.coroutines;

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import com.example.llcgs.android_kotlin.base.network.RetrofitHelper
import com.example.llcgs.android_kotlin.kotlin.coroutines.livedata.CoroutinesLiveData
import com.example.llcgs.android_kotlin.kotlin.coroutines.presenter.impl.CoroutinesPresenter
import com.example.llcgs.android_kotlin.kotlin.coroutines.viewmodel.CoroutinesViewModel
import com.example.llcgs.android_kotlin.kotlin.variable.bean.Repo
import com.example.llcgs.android_kotlin.kotlin.coroutines.view.CoroutinesView
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_coroutines.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.reflect.KProperty

class CoroutinesActivity: BaseActivity<CoroutinesView, CoroutinesPresenter>(), CoroutinesView {

    val scope = MainScope()

    override fun createPresenter()= CoroutinesPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)

        launch(Dispatchers.Main){
            //
            println("hello launch coroutines 0: ${Thread.currentThread().name}")
        }

        /**
         *  输出当前线程名字
         * */
        println("hello coroutines main: ${Thread.currentThread().name}")

        /**
         *  声明一个协程，默认Default
         * */
        launch {
            println("hello coroutines 1: ${Thread.currentThread().name}")
        }

        /**
         *  Kotlin中声明一个线程
         * */
        Thread {
            println("hello coroutines 2: ${Thread.currentThread().name}")
        }.start()

        /**
         *  Kotlin中另一种写法创建一个线程
         * */
        thread {
            println("hello coroutines 3: ${Thread.currentThread().name}")
        }

        //
        mPresenter.getUser("AppleNet")

        /**
         *  协程中调用方法，方法必须被suspend标记
         *
         *  默认开始在主线程，uiXXX方法不需要挂起
         * */
        launch(Dispatchers.Main) {
            uiCode1()
            ioCode1()
            uiCode2()
            ioCode2()
            uiCode3()
            ioCode3()
        }

        /**
         *  协程中除了launch创建一个协程之外，还可以用async来创建一个协程
         *
         * */
        val async = async(Dispatchers.Main) {
            ioCode1()
        }
        launch(Dispatchers.Main) {
            async.await()
        }

        /**
         *  协程中，还可以通过声明一个全局的MainScope来使用协程
         *
         *   val scope = MainScope()
         *
         * */
        scope.launch {
            ioCode1()
            coroutineScope {
                ioCode2()
            }
            ioCode3()
        }

        scope.launch {

        }

        /**
         *  有生命周期的协程
         * */
//        lifecycleScope.launch {
//
//        }

        // =================== Retrofit 与协程 =================== //
        // Retrofit的一次简单实现
        RetrofitHelper.getService(RetrofitHelper.GITHUB_BASE_URL)
                .listRepos("AppleNet")
                .enqueue(object : Callback<List<Repo>?> {
                    override fun onFailure(call: Call<List<Repo>?>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<List<Repo>?>, response: Response<List<Repo>?>) {
                        println("Retrofit与协程 -> Retrofit -> name: ${response.body()?.get(0)?.name}")
                    }
                })
        // Retrofit 与协程结合使用
        launch(Dispatchers.Main) {
            val repos = RetrofitHelper.getService(RetrofitHelper.GITHUB_BASE_URL).listReposRetrofit("AppleNet")
            println("Retrofit与协程 -> Coroutines -> name: ${repos[0].name}}")
        }

        // =================== 非协程 与协程 ================ //
        /**
         *  高阶函数调用方式
         * */
        classicIoCode1(false, ::uiCode1)

        /**
         *  高阶函数调用方式
         * */
        classicIoCode1 {
            uiCode1()
        }

        classicIoCode2 {
            uiCode2()
        }

        // =================== RxJava 与协程 =================== //
        RetrofitHelper.getService(RetrofitHelper.GITHUB_BASE_URL)
                .listReposRxJava("AppleNet")
                .subscribeOn(Schedulers.io())
                .compose(bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<List<Repo>?> {
                    override fun onSuccess(t: List<Repo>) {
                        println("RxJava与协程 -> RxJava -> name: " + t[0].name)
                        textView.text = "RxJava与协程 -> RxJava -> name: " + t[0].name
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                    }
                })

        Single.zip(RetrofitHelper.getService(RetrofitHelper.GITHUB_BASE_URL)
                .listReposRxJava("AppleNet"), RetrofitHelper.getService(RetrofitHelper.GITHUB_BASE_URL)
                .listReposRxJava("AppleNet"), BiFunction<List<Repo>, List<Repo>, Boolean> { list1, list2 -> list1[0].name == list2[0].name })
                .subscribeOn(Schedulers.io())
                .compose(bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<Boolean?> {
                    override fun onSuccess(t: Boolean) {
                        println("RxJava与协程 -> RxJava -> value:  $t")
                        textView2.text = "RxJava与协程 -> RxJava -> value:  $t"
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        launch(Dispatchers.Main) {
            val one = async { RetrofitHelper.getService(RetrofitHelper.GITHUB_BASE_URL).listReposRetrofit("AppleNet") }
            val two = async { RetrofitHelper.getService(RetrofitHelper.GITHUB_BASE_URL).listReposRetrofit("AppleNet") }
            val value = one.await()[0].name == two.await()[0].name
            println("RxJava与协程 - > Coroutines -> value: $value")
            textView3.text = "RxJava与协程 - > Coroutines -> value: $value"
        }

        //=================== LiveData/ViewModel 与协程 ===================//
        val model: CoroutinesViewModel by viewModel()
        model.getRepos().observe(this, Observer {
            println("ViewModel与协程 -> ViewModel -> name: ${it?.get(0)?.name}")
        })

        val liveData = CoroutinesLiveData()
    }

    override fun onGetUser(users: List<Repo>) {
        // MVP + Retrofit + Coroutines
        textView4.text = "MVP + Retrofit + Coroutines -> user: " + users[0].name
    }

    /**
     *  挂起测试函数1
     *
     * */
    private suspend fun ioCode1() {
        withContext(Dispatchers.IO) {
            println("hello coroutines io1: ${Thread.currentThread().name}")
        }
    }

    /**
     *  挂起测试函数2
     *
     * */
    private suspend fun ioCode2() {
        withContext(Dispatchers.IO) {
            println("hello coroutines io2: ${Thread.currentThread().name}")
        }
    }

    /**
     *  挂起测试函数3
     *
     * */
    private suspend fun ioCode3() {
        withContext(Dispatchers.IO) {
            println("hello coroutines io3: ${Thread.currentThread().name}")
        }
    }

    private fun uiCode1() {
        println("hello coroutines ui1: ${Thread.currentThread().name}")
    }

    private fun uiCode2() {
        println("hello coroutines ui2: ${Thread.currentThread().name}")
    }

    private fun uiCode3() {
        println("hello coroutines ui3: ${Thread.currentThread().name}")
    }

    /**
     *  kotlin的高阶函数，函数作为参数
     *
     * */
    private fun classicIoCode1(uiThread: Boolean = true, rumain: () -> Unit) {
        thread {
            println("hello coroutines classic io1: ${Thread.currentThread().name}")
            if (uiThread) {
                runOnUiThread {
                    rumain()
                }
            } else {
                rumain()
            }
        }
    }

    val exector = ThreadPoolExecutor(5, 20, 1, TimeUnit.MINUTES, LinkedBlockingDeque())
    /**
     *  kotlin的高阶函数，函数作为参数
     *
     * */
    private fun classicIoCode2(uiThread: Boolean = true, rumain: () -> Unit) {
        exector.execute {
            println("hello coroutines classic io1: ${Thread.currentThread().name}")
            if (uiThread) {
                runOnUiThread {
                    rumain()
                }
            } else {
                rumain()
            }
        }
    }


    class viewModel {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): CoroutinesViewModel {
            return CoroutinesViewModel()
        }
    }
}
