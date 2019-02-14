package com.example.llcgs.android_kotlin.git;

import com.example.llcgs.android_kotlin.utils.log.logD

/**
 * com.example.llcgs.android_kotlin.git.GitDeleteTest
 * @author liulongchao
 * @since 2019/2/14
 */
class GitDeleteTest {

    /**
     *  删除文件，两种方式
     *  1.一般情况下，直接在文件管理中把没用的文件删了，这时，Git就知道你删除了文件，因此，工作区合版本库就不一致了，git status命令就会高速你哪些文件被删除了
     *  然后使用commit命令 git commit -m ""
     *  然后使用push命令 git push
     *
     *  2.确实要从版本库珊瑚该文件，使用命令git rm删掉，并且commit
     *  git rm <file>
     *  git commit -m ""
     *  git push
     *
     * */
    fun delete(){
        "delete".logD()
    }

}
