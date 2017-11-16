package com.example.llcgs.android_kotlin.mvvm.observablefields.model

import android.databinding.*

/**
 * com.example.llcgs.android_kotlin.mvvm.observablefields.model.FieldsModel
 * @author liulongchao
 * @since 2017/11/15
 */
class FieldsModel {

    /**
     *   ObservableField,
     *   ObservableBoolean,
     *   ObservableByte,
     *   ObservableChar,
     *   ObservableShort,
     *   ObservableInt,
     *   ObservableLong,
     *   ObservableFloat,
     *   ObservableDouble,
     *   ObservableParcelable
     * */

    var field = ObservableField<String>("CBA")
    var BooleanFlag = ObservableBoolean(false)
    var ByteFlag = ObservableByte(0)
    var CharFlag = ObservableChar('z')
    var ShortFlag = ObservableShort(0)
    var IntFlag = ObservableInt(0)
    var LongFlag = ObservableLong(0)
    var FloatFlag = ObservableFloat(0f)
    var DoubleFlag = ObservableDouble(0.0)
    var parcel = ObservableParcelable<Person>()

}