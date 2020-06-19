package com.timhuang.coroutinerequestdemo.data

import com.timhuang.coroutinerequestdemo.config.Config
import org.json.JSONObject

data class Placeholder(val albumId:Int,val id:Int,val title:String,val url:String,val thumbnailUrl:String)


fun JSONObject.asPlaceHolder():Placeholder{
    return Placeholder(
        getInt(Config.AlbumId),
        getInt(Config.Id),
        getString(Config.Title),
        getString(Config.Url),
        getString(Config.ThumbnailUrl)
    )
}