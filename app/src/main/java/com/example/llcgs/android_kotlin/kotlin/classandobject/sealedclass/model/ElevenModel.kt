package com.example.llcgs.android_kotlin.kotlin.classandobject.sealedclass.model

import com.example.llcgs.android_kotlin.base.model.BaseModel
import com.example.llcgs.android_kotlin.kotlin.classandobject.sealedclass.bean.SubConst
import com.example.llcgs.android_kotlin.kotlin.classandobject.sealedclass.bean.User
import com.example.llcgs.android_kotlin.kotlin.classandobject.sealedclass.bean.UserSub

/**
 * com.example.llcgs.android_kotlin.classandobject.sealedclass.model.ElevenModel
 * @author liulongchao
 * @since 2017/7/24
 */


class ElevenModel: BaseModel {

    // TODO 在这里 不需要使用else来做非限制，因为密封类已经限定了类继承结构，只有这几种情况
    fun eval(user: User): String{
        when(user){
            is User.Const ->{
                return ""
            }
            is User.Sum ->{
                ""
            }
            is User.NotNumber ->{
                ""
            }
            is User.Const1 ->{
                ""
            }
            is User.Sum1 ->{
                ""
            }
            is UserSub ->{
                ""
            }
            is SubConst ->{

            }
            // 但是 也可以写else 可有可无
            else ->{
                ""
            }
        }
        return ""
    }

}