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
import java.util.concurrent.locks.ReentrantLock
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
        val threadOne = Thread {
            Thread.sleep(1000)
            "child threadOne over".logD()
        }

        val threadTwo = Thread {
            Thread.sleep(1000)
            "child threadTwo over".logD()
        }

        threadOne.start()
        threadTwo.start()

        "wait all child thread over".logD()

        threadOne.join()
        threadTwo.join()
    }

    fun testJoin1() {
        val threadOne = Thread {
            "threadOne begin run!".logD()
            while (true) {

            }
        }

        // 获取主线程
        val mainThread = Thread.currentThread()

        // 线程two
        val threadTwo = Thread {
            Thread.sleep(1000)
            mainThread.interrupt()
        }

        // 启动子线程
        threadOne.start()
        // 延迟1s启动线程
        threadTwo.start()
        // 等待线程one执行结束
        threadOne.join()
    }

    private val lock = ReentrantLock()
    fun testSleep() {
        val threadA = Thread {
            // 获取独占锁
            lock.lock()
            "child threadA is in sleep".logD()
            Thread.sleep(10000)
            "child threadA is in awaked".logD()
            // 释放锁
            lock.unlock()
        }

        val threadB = Thread {
            lock.lock()
            "child threadB is in sleep".logD()
            Thread.sleep(10000)
            "child threadB is in awaked".logD()
            lock.unlock()
        }
    }

    fun testSleepInterrupt() {

        val threadA = Thread {
            Thread.sleep(10000)
        }

        threadA.start()

        Thread.sleep(2000)

        threadA.interrupt()
    }

    fun testYield() {
        val thread = Thread {
            for (i in 0..5) {
                // 当i==0的时候，让出cpu执行权，放弃时间片，进行下一轮调度
                if ((i % 5) == 0) {
                    "${Thread.currentThread()} yield cpu...".logD()
                    // 当前线程让出cpu执行权，放弃时间片，进行下一轮调度
                    Thread.yield()
                }
            }
            "${Thread.currentThread()} is over".logD()
        }
    }

    fun testInterrupted() {
        val thread = Thread {
            // 如果当前线程被中断，则退出循环
            while (!Thread.currentThread().isInterrupted) {
                "${Thread.currentThread()} + hello".logD()
            }
        }

        thread.start()
        Thread.sleep(1000)
        // 中断子线程
        "main thread interrupt thread".logD()
        thread.interrupt()

        thread.join()
        "main is over".logD()
    }

    fun testInterrupted1() {
        val thread = Thread {
            "thread begin sleep for 2000 seconds".logD()
            try {
                Thread.sleep(2000000)
            } catch (e: InterruptedException) {
                "thread is interrupted while sleeping".logD()
                return@Thread
            }
            "thread awaking".logD()
        }

        thread.start()
        // 确保子线程进入休眠状态
        Thread.sleep(1000)
        // 打断子线程的休眠，让子线程从sleep函数返回
        thread.interrupt()
        // 等待子线程执行完毕
        thread.join()
        "main thread is over".logD()
    }

    fun testInterrupted2() {
        val thread = Thread{
            while (true) {

            }
        }

        thread.start()
        thread.interrupt()

        "isInterrupted: ${thread.isInterrupted}".logD()
        "isInterrupted: ${Thread.interrupted()}".logD()
        "isInterrupted: ${thread.isInterrupted}".logD()

        thread.join()

        "main thread is over".logD()
    }

    fun testInterrupted3() {
        val thread = Thread{
            while (!Thread.currentThread().isInterrupted) {

            }
            "thread inInterrupted: ${Thread.currentThread().isInterrupted}".logD()
        }

        thread.start()
        thread.interrupt()
        "main thread is over".logD()
    }
}
