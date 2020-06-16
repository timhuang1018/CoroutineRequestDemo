package com.timhuang.coroutinerequestdemo.config

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