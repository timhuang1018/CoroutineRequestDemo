package com.timhuang.coroutinerequestdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.timhuang.coroutinerequestdemo.config.Config
import com.timhuang.coroutinerequestdemo.config.RequestException
import com.timhuang.coroutinerequestdemo.data.Placeholder
import com.timhuang.coroutinerequestdemo.data.asPlaceHolder
import com.timhuang.coroutinerequestdemo.helper.httpGet
import com.timhuang.coroutinerequestdemo.helper.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_request.setOnClickListener {
            GlobalScope.launch {
                requestPlaceholders()
            }
        }
    }

    private suspend fun requestPlaceholders(){
        try {
            val result = httpGet("https://jsonplaceholder.typicode.com/photos")
            parseJson(result)
        }catch (e:Exception){
        //error handling part just let user know the failure
            toast("連線失敗，請稍後再試")
        }
//                Log.d("MainActivity","result:$result")
    }

    private fun parseJson(jsonString: String) {
        val array = JSONArray(jsonString)
        val list = mutableListOf<Placeholder>()
        for (i in 0 until array.length()-1){
            val item = array.getJSONObject(i)
            list.add(item.asPlaceHolder())
        }
//        Log.d("MainActivity","list size:${list.size}")
    }

}