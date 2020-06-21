package com.timhuang.coroutinerequestdemo.helper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.timhuang.coroutinerequestdemo.config.RequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


suspend fun httpGet(myURL: String?): String {
    val inputStream: InputStream

    return withContext(Dispatchers.IO){
        // create URL
        val url: URL = URL(myURL)

        // create HttpURLConnection
        val conn: HttpURLConnection = url.openConnection() as HttpURLConnection

        // make GET request to the given URL
        conn.connect()

        // receive response as inputStream
        inputStream = conn.inputStream

        // convert inputstream to string
        if(inputStream != null)
            convertInputStreamToString(inputStream)
        else
            throw RequestException
    }
}


suspend fun getBitmap(url:String): Bitmap? {
//    Log.d("RequestEx","getBitmap called url:$url")
    return withContext(Dispatchers.Default){
        try {
            val image = BitmapFactory.decodeStream(getInputStream(url))
            image
        } catch (e: IOException) {
            Log.e("RequestEx","getBitmap exception :${e.message}")
            null
        }
    }
}

//this request need user agent , or the download will fail
suspend fun getInputStream(url: String):InputStream{
    try {
        val url = URL(url)
        val connection = url.openConnection()
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
        connection.connect()
        return connection.getInputStream()
    }catch (e:Exception){
        Log.e("RequestEx","getInputStream called and error :${e.cause},${e.localizedMessage}")
        throw e
    }
}

//use extension would be autocloseable
private fun convertInputStreamToString(inputStream: InputStream): String {
    Log.d("MainActivity","size:${inputStream.available()}")
    return inputStream.bufferedReader().use(BufferedReader::readText)
}