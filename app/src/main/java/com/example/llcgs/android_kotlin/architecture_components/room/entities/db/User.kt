package com.example.llcgs.android_kotlin.architecture_components.room.entities.db

import android.arch.persistence.room.*
import android.graphics.Bitmap

/**
 * com.example.llcgs.android_kotlin.architecture_components.room.entities.db.User
 * @author liulongchao
 * @since 2017/12/8
 */

@Entity(tableName = "users", indices = [(Index("name")),(Index(value = ["first_name"], unique = true))])
class User {

    /**
     *  To persist a field, Room must have access to it. You can make a field public, or you can provide a getter and setter for it.
     *  If you use getter and setter methods, keep in mind that they're based on JavaBeans conventions in Room
     *
     * */

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    /**
     *  Similar to the tableName property, Room uses the field names as the column names in the database.
     *  If you want a column to have a different name, add the @ColumnInfo annotation to a field,
     *
     * */
    @ColumnInfo(name = "first_name")
    var firstName: String = ""

    @ColumnInfo(name = "last_name")
    var lastName: String = ""

    var fullName: String = ""
    set(value) {
        field = firstName + "-" + lastName
    }

    @Ignore
    var bitmap: Bitmap ? = null

    /**
     *  使用 indices 创建索引
     *    索引分为聚集索引 非聚集索引
     *    1、聚集索引：
            聚集索引的意思可以理解为顺序排列，比如一个主键自增的表即为聚集索引，即id为1的存在于第一条，id为2的存在于第二条...假使数据库中是使用数组来存放的这张表中的数据，
            那么如果我需要查找第100条，那么直接第一条数据的地址加上100即为第一百条的地址，一次就能查询出来。
            因为数据库中的数据只能按照一个顺序进行排列，所以聚集索引一个数据库只能有一个。
            在mysql中，不能自己创建聚集索引，主键即为聚集索引，如果没有创建主键，那么默认非空的列为聚集索引，如果没有非空的列那么会自动生成一个隐藏列为聚集索引。
            所以一般在mysql中，我们创建的主键即为聚集索引，数据是按照我们的主键顺序进行排列。所以在根据主键进行查询时会非常快

          2、非聚集索引：
            非聚集索引可以简单理解为有序目录，是一种以空间换取时间的方法。举个例子，在一个user表中，有一个id_num，即身份号，此不为主键id，那么这些数据在存储的时候都是无序的，比如
            id为1的id_num为100，id为2的id_num为97，id为3的id_num为98，id为4的id_num为99，id为5的id_num为96。。。id为67的id_num为56。。。
            那么如果我要查找id_num为56的人，那么只能一条一条的遍历，n条就需要查询n次，时间复杂度为O(n)，这是非常耗费性能的。
            所以，现在就需要为id_num增加非聚集索引，添加了非聚集索引后，会给id_num进行排序（内部使用结构为B+树），并且排序后，
            我只需要查询此目录(即查询B+树)，很快就知道为id为56的在数据库中的第67条，而不需要在去遍历表中的所有数据。
            所以，在非聚集索引中，不重复的数据越多，那么索引的效率越高
     *
     * */

}