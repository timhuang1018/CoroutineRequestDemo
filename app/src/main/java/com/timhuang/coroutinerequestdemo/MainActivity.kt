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
// put most ui logic into pages and utilize navigation component library
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}