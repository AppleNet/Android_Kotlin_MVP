package com.example.llcgs.android_kotlin.git;

import com.example.llcgs.android_kotlin.utils.log.logD

/**
 * com.example.llcgs.android_kotlin.git.GitCheckoutTest
 * @author liulongchao
 * @since 2019/2/14
 */
class GitCheckoutTest {

    /**
     *  (use "git add <file>..." to update what will be committed)
     *  (use "git checkout -- <file>..." to discard changes in working directory)
     *
     *   git checkout -- file 可以丢弃工作区的修改：
     *   git checkout -- app/src/main/java/com/example/llcgs/android_kotlin/git/GitCheckoutTest.kt
     *   命令git checkout -- app/src/main/java/com/example/llcgs/android_kotlin/git/GitCheckoutTest.kt的意思就是，把GitCheckoutTest.kt文件在工作区的修改全部撤销，这里有两种情况：
     *   1. GitCheckoutTest.kt自修改后还没有放到暂存区，现在，撤销修改就回到合版本库一模一样的状态。
     *   2. GitCheckoutTest.kt已经添加到了暂存区后，又做了修改，现在，撤销修改就回到添加暂存区后的状态。
     *   总之，就是让这个文件回到最近一次的git commit 或 git add时的状态
     *
     *
     * */

    public fun checkoutTest(){
        "checkoutTest".logD()
    }

    fun checkout(){
        "checkout".logD()
    }
}
