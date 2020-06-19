package com.timhuang.coroutinerequestdemo.helper

import android.util.Log
import com.timhuang.coroutinerequestdemo.config.RequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
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

//use extension would be autocloseable
private fun convertInputStreamToString(inputStream: InputStream): String {
    Log.d("MainActivity","size:${inputStream.available()}")
    return inputStream.bufferedReader().use(BufferedReader::readText)
}