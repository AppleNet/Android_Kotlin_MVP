package com.example.llcgs.android_kotlin.third.bean

/**
 * com.example.llcgs.android_kotlin.third.bean.NBA
 * @author liulongchao
 * @since 2017/7/19
 */

/**
 *  创建POTOs
 *   数据类
 *   数据类的创建，会默认提供一下功能
 *   get set默认提供
 *   1.equals()
 *   2.hashCode()
 *   3.toString()
 *   4.copy()
 *
 * */
data class NBA(val name: String, val age: Int)

/**
 *  @NotNull
    public final NBA copy(@NotNull String name, int age) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return new NBA(name, age);
    }

    public String toString() {
        return "NBA(name=" + this.name + ", age=" + this.age + ")";
    }

    public int hashCode() {
        return (this.name != null?this.name.hashCode():0) * 31 + this.age;
    }

    public boolean equals(Object var1) {
        if(this != var1) {
            if(var1 instanceof NBA) {
                NBA var2 = (NBA)var1;
                if(Intrinsics.areEqual(this.name, var2.name) && this.age == var2.age) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }
 *
 * */
