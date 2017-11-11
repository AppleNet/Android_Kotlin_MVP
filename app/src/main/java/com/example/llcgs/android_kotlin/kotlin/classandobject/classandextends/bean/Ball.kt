package com.example.llcgs.android_kotlin.kotlin.classandobject.classandextends.bean

/**
 * com.example.llcgs.android_kotlin.classandobject.classandextends.bean.Ball
 * @author liulongchao
 * @since 2017/7/20
 */


open class Ball{

    // 声明一个超类，可以被其他类继承

    /**
     *  public class Ball {
        }

        // open修饰的 可以被子类重写
        @NotNull
        public String playBall() {
            return "";
        }
        // 非open修饰的，final类型 不可以被子类重写
        @NotNull
        public final String player() {
            return "";
        }

        // open修饰的 属性可以被子类覆盖
        @NotNull
        public String getPlayer() {
            return this.player;
        }

        public void setPlayer(@NotNull String var1) {
            Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
            this.player = var1;
        }

        // 非open修饰的 属性不可以被子类覆盖
        @NotNull
        public final String getPalyBall() {
            return this.palyBall;
        }

        public final void setPalyBall(@NotNull String var1) {
            Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
            this.palyBall = var1;
        }
     *
     *  这样编译出来的是声明java时候的默认情况
     *
     * */

    // FootBall BasketBall 为子类 可以继承 Ball
    constructor()
    constructor(weight: Int)
    constructor(weight: Int, name:String)

    open var player : String = "Jdon"
    var palyBall: String = "BasketBall"

    open fun playBall(): String{
        return ""
    }

    fun player(): String{
        return ""
    }

    open fun drink(){

    }


}