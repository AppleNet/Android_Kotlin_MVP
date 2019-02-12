package com.example.llcgs.android_kotlin.thread.model;

import android.util.Log
import com.example.llcgs.android_kotlin.base.model.BaseModel
import com.example.llcgs.android_kotlin.kotlin.classandobject.`object`.A
import com.example.llcgs.android_kotlin.thread.thread.CallTask
import com.example.llcgs.android_kotlin.thread.thread.MyRunnable
import com.example.llcgs.android_kotlin.thread.thread.MyThread
import com.example.llcgs.android_kotlin.utils.log.logD
import java.util.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.FutureTask
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

/**
 * com.example.llcgs.android_kotlin.thread.model.MainThreadModel
 * @author liulongchao
 * @since 2019/1/30
 */
class MainThreadModel : BaseModel {

    @Volatile
    var resourceA: Object = Object()

    @Volatile
    var resourceB: Object = Object()

    fun testWait() {
        val threadA = Thread {
            try {
                // 获取resourceA共享资源的监视器锁
                synchronized(resourceA) {
                    Log.d("MainActivity", "threadA get resourceA lock")
                    // 获取resourceB共享资源的监视器锁
                    synchronized(resourceB) {
                        Log.d("MainActivity", "threadA get resourceB lock")
                        // 线程A阻塞，并释放获取到底resourceA的锁
                        Log.d("MainActivity", "threadA release resourceA lock")
                        resourceA.wait()
                    }
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        val threadB = Thread {
            try {
                // 休眠1s
                Thread.sleep(1000)
                // 获取resourceA共享资源的监视器锁
                synchronized(resourceA) {
                    Log.d("MainActivity", "threadB get resourceA lock")
                    // 获取resourceB共享资源的监视器锁
                    synchronized(resourceB) {
                        Log.d("MainActivity", "threadB get resourceB lock")
                        // 线程B阻塞，并释放获取到resourceA的锁
                        Log.d("MainActivity", "threadA release resourceA lock")
                        resourceA.wait()
                    }
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        // 启动线程
        threadA.start()
        threadB.start()

        // 等待两个线程结束
        try {
            threadA.join()
            threadB.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }

    fun testNotify() {
        val threadA = Thread {
            synchronized(resourceA) {
                Log.d("MainActivity", "threadA get resourceA lock")
                try {
                    Log.d("MainActivity", "threadA start wait")
                    resourceA.wait()
                    Log.d("MainActivity", "threadA end wait")
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }

        val threadB = Thread {
            synchronized(resourceA) {
                Log.d("MainActivity", "threadB get resourceA lock")
                try {
                    Log.d("MainActivity", "threadB start wait")
                    resourceA.wait()
                    Log.d("MainActivity", "threadB end wait")
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }

        val threadC = Thread {
            synchronized(resourceA) {
                Log.d("MainActivity", "threadC start notify")
                //resourceA.notify();
                //resourceA.notifyAll();
                Log.d("MainActivity", "threadC end notify")
            }
        }

        threadA.start()
        threadB.start()
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        threadC.start()

        try {
            threadA.join()
            threadB.join()
            threadC.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun initThread() {
        //
        val myThread = MyThread()
        myThread.start()

        val myRunnable = MyRunnable()
        val thread = Thread(myRunnable)
        thread.start()

        // myRunnable.testNotify();

        // 创建异步任务
        val futureTask = FutureTask(CallTask())
        // 启动线程
        Thread(futureTask).start()
        try {
            val result = futureTask.get()
            Log.d("MainActivity", "CallTask result: $result")
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }

    }

    fun testJoin() {
        val threadOne = Thread{
            Thread.sleep(1000)
            "child threadOne over".logD()
        }

        val threadTwo = Thread{
            Thread.sleep(1000)
            "child threadTwo over".logD()
        }

        threadOne.start()
        threadTwo.start()

        "wait all child thread over".logD()

        threadOne.join()
        threadTwo.join()
    }
}
