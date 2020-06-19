package com.timhuang.coroutinerequestdemo.helper

import com.timhuang.coroutinerequestdemo.data.Placeholder
import com.timhuang.coroutinerequestdemo.data.asPlaceHolder
import org.json.JSONArray

object ParseHelper {

    fun parseJson(jsonString: String): MutableList<Placeholder> {
        val array = JSONArray(jsonString)
        val list = mutableListOf<Placeholder>()
        for (i in 0 until array.length()-1){
            val item = array.getJSONObject(i)
            list.add(item.asPlaceHolder())
        }
        return list
    }
}