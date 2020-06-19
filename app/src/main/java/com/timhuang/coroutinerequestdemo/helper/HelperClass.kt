package com.timhuang.coroutinerequestdemo.helper

class EventWrapper<T>(private val item:T){
    private var isUsed = false
    fun getContentIfNotHandled():T?{
        if (!isUsed){
            isUsed = true
            return item
        }else{
            return null
        }
    }
}