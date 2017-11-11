package com.example.llcgs.android_kotlin.kotlin.classandobject.dataclass.bean

/**
 * com.example.llcgs.android_kotlin.classandobject.dataclass.bean.User
 * @author liulongchao
 * @since 2017/7/24
 */


data class User(var name: String="", var age: String="") {

    // 为了确保⽣成的代码的⼀致性和有意义的⾏为，数据类必须满⾜以下要求
    // 1.主构造函数需要至少有一个参数
    // 2.主构造函数的所有参数需要标记为val或者var
    // 3.数据类不能是抽象，开发，密封或者内部的
    // 4.在1.1之前数据类只能实现接口
    // 5.自1.1起，数据类可以扩展其他类
    // 6.如果生成的类需要含有一个无参的构造函数，则所有的属性必须指定默认值

    // 声明数据类，编译器⾃动从主构造函数中声明的所有属性导出以下成员
    // 1.equals()/hasCode()
    // 2.toString() User(name=" + this.name + ", age=" + this.age + ")
    // 3.component() 函数 按声明顺序对应于所有属性
    // 4.copy() 函数 new User(name, age)

    /**
     * public int hashCode() {
            return (this.name != null?this.name.hashCode():0) * 31 + (this.age != null?this.age.hashCode():0);
       }

        public boolean equals(Object var1) {
            if(this != var1) {
                if(var1 instanceof User) {
                    User var2 = (User)var1;
                    if(Intrinsics.areEqual(this.name, var2.name) && Intrinsics.areEqual(this.age, var2.age)) {
                        return true;
                    }
                }
                return false;
            } else {
                return true;
            }
        }
     * */

    /**
     *  public String toString() {
            return "User(name=" + this.name + ", age=" + this.age + ")";
        }
     * */

    /**
     *  @NotNull
        public final String component1() {
            return this.name;
        }

        @NotNull
        public final String component2() {
            return this.age;
        }
     *
     * */

    /**
     *  @NotNull
        public final User copy(@NotNull String name, @NotNull String age) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(age, "age");
            return new User(name, age);
        }
     * */

    /**
     *  public User() {
            this((String)null, (String)null, 3, (DefaultConstructorMarker)null);
        }
     * */

}