package com.example.llcgs.android_kotlin.other.gather

import android.content.Context
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.other.gather.presenter.impl.TwentyTwoPresenter
import com.example.llcgs.android_kotlin.other.gather.view.TwentyTwoView
import com.gomejr.myf.core.kotlin.logD
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * 集合
 *
 * */
class TwentyTwoActivity : BaseActivity<TwentyTwoView, TwentyTwoPresenter>(), TwentyTwoView {

    override fun createPresenter()= TwentyTwoPresenter()

    val array1 = arrayOf("NBA","CBA","MBA")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twenty_two)

        // 集合
        // 与大多数语言不同，Kotlin区分可变集合和不可变集合（lists、sets、maps等）。精确控制什么时候集合可编辑有助于消除bug和设计良好的API
        // 声明可变集合&不可变集合

        // Kotlin 的 List<out T> 类型是⼀个提供只读操作如size 、get 等的接口。和Java类似，它继承自 Collection<T> 进而继承自
        // Iterable<T> 。改变 list 的方法是由 MutableList<T> 加入的。这⼀模式同样使用于 Set<out T>/MutableSet<T> 及 Map<K, out
        // V>/MutableMap<K, V>

        // list0 可变, 声明可变，说明可以调用add方法 进行数据的添加
        val list0 = MutableList(10){
            /*
            * MutableList的泛型是Any
            * */
            when(it){
                0 -> it to "NBA"
                1 -> it to "CBA"
                2 -> it to "MBA"
                else -> -100
            }
        }
        "添加之前的长度: ${list0.size}".logD()
        list0.add("Kobe")
        list0.add(5 to "James")
        list0.add(6 to "Wade")
        list0.add(7 to "McGrady")
        list0.add(8 to "Answer")
        list0.add(9 to "Nash")
        list0.add(10 to "Bosh")
        list0.add(11 to "Durant")
        list0.add(12 to "Jodn")
        list0.add(13 to "Paul")
        "添加之后的长度: ${list0.size}".logD()
        list0.first()
        list0.forEach{
            it.logD()
        }

        // list1
        val list1 = ArrayList<String>()
        "添加之前的长度: ${list1.size}".logD()
        list1.add("Wade")
        list1.add("Wade")
        list1.add("Wade")
        list1.add("Wade")
        list1.add("Wade")
        list1.add("Wade")
        list1.add("Wade")
        list1.add("Wade")
        list1.forEach { it.logD() }
        "添加之后的长度: ${list1.size}".logD()

        // list2 不可变
        val list2 = listOf("James", "James", "James", "James", "James", "James", "James")
        "添加之前的长度: ${list2.size}".logD()
        // 没有对外提供add方法，所以不能添加，长度是不可变的

        // list3 不可变，空集合，但是永远为空，没有add方法
        val list3 = listOf<String>()

        // list4 可变
        val list4 = arrayListOf<String>()
        list4.add("")

        // list5 不可变，丢弃集合中的空元素，没有add方法 只提供了size，get等方法
        val list5 = listOfNotNull("NBA", null, "CBA", "MBA", "Kobe", "James", "Wade")
        "添加之前的长度: ${list5.size}".logD()

        // list6 不可变，创建一个泛型为String的空集合
        val list6 = emptyList<String>()

        // list7 可变
        val list7 = mutableListOf("NBA", null, "CBA", "MBA", "Kobe", "James", "Wade")
        list7.add(1, "MVP")


    // set集合
        // set0 可变，添加不重复的元素，所以添加之前和添加之后的长度一样
        val set0 = mutableSetOf("NBA", "NBA", "CBA", "CBA", "MBA")
        "添加之前的长度: ${set0.size}".logD()
        set0.add("MBA")
        "添加之后的长度: ${set0.size}".logD()

        // set1 不可变，不能添加重复元素
        val set1 = setOf("Kobe", "Wade", "James", "Paul", "Wade")
        set1.size.logD()
        set1.forEach { it.logD() }

        // set2 可变，不能添加重复元素，hashset集合
        val set2 = hashSetOf("NBA", "CBA", "MBA")
        set2.add("Kobe")
        set2.add("Yao")
        set2.add("CEO")
        set2.forEach { it.logD() }

        // set3 可变集合
        val set3 = linkedSetOf("1","2","3")
        set3.add("4")
        set3.forEach { it.logD() }

        // set4 可变集合 排序好的结合 自动将集合中的内容排序
        val set4 = sortedSetOf(1,5,3,4,2)
        set4.add(-1)
        set4.add(6)
        set4.add(-2)
        set4.add(9)
        set4.add(7)
        set4.add(10)
        set4.forEach { it.logD() }

        // set5 空集合 不可变，没有add方法
        val set5 = emptySet<String>()
        set5.isEmpty().logD()

        // set6 set集合去重，set中封装了一个hashmap
        // 调用add方法时，将入参作为map的key进行存贮，map的value都是PRESENT
        // 因为map中添加的时候 key相同，新值将旧的值覆盖
        // 其实是能重复添加 只是看到的现象是不能重复。
        val map: HashMap<String, Any> = HashMap<String, Any>()
        val set6 = object : MutableSet<String>{
            override fun add(element: String): Boolean {
                return map.put(element, Context.CONTEXT_RESTRICTED) == null
            }

            override fun addAll(elements: Collection<String>): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun clear() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun iterator(): MutableIterator<String> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun remove(element: String): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun removeAll(elements: Collection<String>): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun retainAll(elements: Collection<String>): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override val size: Int
                get() = map.size

            override fun contains(element: String): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun containsAll(elements: Collection<String>): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun isEmpty(): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

    // map集合
        // map1 不可变集合
        val map1 = mapOf(1 to "NBA", 2 to "NBA", 3 to "NBA", 4 to "NBA", 5 to "NBA", 6 to "NBA")
        val entries = map1.entries
        entries.forEach {
            it.key.logD()
            it.value.logD()
        }

        // map2 可变集合
        val map2 = mutableMapOf(1 to "NBA", 2 to "CBA", 3 to "MBA", 4 to "TBA", 5 to "GBA", 6 to "SBA")
        map2.put(7, "DBA")

        // map3 可变集合
        val map3 = hashMapOf(1 to "NBA", 2 to "CBA", 3 to "MBA", 4 to "TBA", 5 to "GBA", 6 to "SBA")
        map3.put(7, "HBA")
        map3.put(8, "JBA")

        // map4 可变集合
        val map4 = linkedMapOf(1 to "NBA", 2 to "CBA", 3 to "MBA", 4 to "TBA", 5 to "GBA", 6 to "SBA")
        map4.put(7, "HBA")

        // map5
        val map5 = sortedMapOf(1 to "NBA", 2 to "CBA", 3 to "MBA", 4 to "TBA", 5 to "GBA", 6 to "SBA")
        map5.put(7, "HBA")

        // map6 不可变的 空集合
        val map6 = emptyMap<Int, String>()

        // map7
        val map7 = HashMap<Int, String>()
        val map8 = LinkedHashMap<Int, String>()
        // 自己实现map
        val map9 = object : SortedMap<Int, String>{
            override fun containsValue(value: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override val entries: MutableSet<MutableMap.MutableEntry<Int, String>>
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
            override val values: MutableCollection<String>
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

            override fun tailMap(fromKey: Int?): SortedMap<Int, String> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override val size: Int
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

            override fun containsKey(key: Int?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun get(key: Int?): String? {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun isEmpty(): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun put(key: Int?, value: String?): String? {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun putAll(from: Map<out Int, String>) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun remove(key: Int?): String? {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override val keys: MutableSet<Int>
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

            override fun subMap(fromKey: Int?, toKey: Int?): SortedMap<Int, String> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun firstKey(): Int {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun headMap(toKey: Int?): SortedMap<Int, String> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun clear() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun lastKey(): Int {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun comparator(): Comparator<in Int> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }




    }

}



