package com.timhuang.coroutinerequestdemo.config

import androidx.navigation.NavDirections

object Config{

    //for parsing
    const val AlbumId = "albumId"
    const val Id = "id"
    const val Title = "title"
    const val Url = "url"
    const val ThumbnailUrl = "thumbnailUrl"
}

//dummy exception , skip error handling for now
object RequestException:Exception()

sealed class Result{
    data class Success(val nav:NavDirections):Result()
    data class Failure(val info:String):Result()
}
