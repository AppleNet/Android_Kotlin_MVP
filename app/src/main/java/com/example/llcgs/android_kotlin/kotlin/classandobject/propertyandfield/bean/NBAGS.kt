package com.example.llcgs.android_kotlin.kotlin.classandobject.propertyandfield.bean

/**
 * com.example.llcgs.android_kotlin.classandobject.propertyandfield.bean.NBAGS
 * @author liulongchao
 * @since 2017/7/21
 */


class NBAGS {

    /**
     *  声明一个完整属性的语法
     *  var <propertyName>[: <PropertyType>] [= <property_initializer>]
        [<getter>]
        [<setter>]

        其初始器（initializer）、getter 和 setter 都是可选的。属性类型如果可以从初始器 （或者从其 getter 返回值，如下⽂所⽰）中推断出来，也可以省略
     * */
    var name: String
        get() = this.name // 可选的，默认会生成
        set(value) {
            name = value
        } // 可选的，默认会生成

    var age: Int
        get() = this.age
        set(value) {
            age = value
            // 还可以进行一些其他的操所，比如说 直接给textView setText
        }

    // 使用val只读属性的时候 只能get 不能set
    // 否则编译时报错 a ‘val’-property cannot have a setter
    // val 不能拥有property_initializer 因为 val没有幕后字段(backing field)
    // 当去掉get方法后则必须 拥有初始器(property_initializer)
    // 通过get方法 给hobby赋值
    val hobby: String
        get() = "Kobe"
        //set(value) {hobby = value}
    // 如果可以从 getter 推断出属性类型，则可以省略它
    val height get() = 0

    // get/set 可以定义访问控制符 private私有(说明不想对外提供set方法) public protected
        // private
    var address: String = "Los Angeles"
        private set
        /*private */get() = this.address

    // get/set 可以定义访问控制符 public(公有的 对外提供访问，默认public 可以不用声明)
        // public
    var workInfo: String
        public get() = "BasketBall"
        public set(value) {
            workInfo = value
            workInfo = "play BasketBall for person"
        }

    // get/set 可以定义访问控制符 protected(受保护的，包级别的)
        // protected
        // 使用 protected的时候 声明get属性的时候 访问控制符必须和属性保持一致的 负责编译时提示 Getter visibility must be the same as property visibility
        // 所以 属性也必须用 protected 修饰
    protected var phone: String
        protected  get() = this.phone
        protected set(value) {
            phone = value
        }

    // 幕后字段
    // Kotlin 中类不能有字段。然⽽，当使⽤⾃定义访问器时，有时有⼀个幕后字段（backing field）有时是必要的。为此 Kotlin 提供 ⼀个⾃动幕后字段，它可通过使⽤ field 标识符访问
    // field标识符只能放在属性的访问器内
    // 当使用幕后字段的时候 属性必须拥有初始化值
    var education: String = "学前班" // 此初始值直接写入幕后字段
        //get() = this.education
        get() {
            // 如果直接调用get 而不调用set 此时返回的就是默认值"学前班"
            return field
        }
        set(value) {
            when(value){
                "0" -> field = "小学"
                "1" -> field = "初中"
                "2" -> field = "高中"
                "3" -> field = "大学"
                "4" -> field = "硕士"
                "5" -> field = "博士"
                "6" -> field = "博士后"
            }

        }
    // 此时不会产生幕后字段，因为没有声明set方法 get方法 也没有使用代码块
    val isEmpty: Boolean
        get() = (1 == 0)

    // 幕后属性
    // 声明一个私有属性的时候 其Getter和Setter，都是私有的，外部都不可以访问
    // 通过默认 getter 和 setter 访问私有属性会被优化，所以不会引⼊函数调⽤开销
    private var _table: Map<String, String>? = null
    val table: Map<String, String> // table就是幕后属性 再幕后为_table提供信息
        get() {
            if (_table == null){
                _table = HashMap()
            }
            return _table?: throw AssertionError("Set to null by another thread")
        }



    fun getSubNBA() = object: BaseNBA("", ""){
        override val height: String
            get() = "198"
    }

    fun getSub() = object : BaseNBA1("", "", ""){
        override val height: String = "198"
    }


}