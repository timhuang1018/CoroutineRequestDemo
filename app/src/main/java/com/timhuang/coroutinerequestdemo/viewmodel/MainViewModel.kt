package com.timhuang.coroutinerequestdemo.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.timhuang.coroutinerequestdemo.config.Result
import com.timhuang.coroutinerequestdemo.data.ImageContainer
import com.timhuang.coroutinerequestdemo.data.Placeholder
import com.timhuang.coroutinerequestdemo.helper.EventWrapper
import com.timhuang.coroutinerequestdemo.helper.ParseHelper
import com.timhuang.coroutinerequestdemo.helper.getBitmap
import com.timhuang.coroutinerequestdemo.helper.httpGet
import com.timhuang.coroutinerequestdemo.pages.FirstPageDirections
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel : ViewModel() {

    private val _data = MutableLiveData<List<Placeholder>>()
    val data:MutableLiveData<List<Placeholder>>
    get() = _data

    private val cache = mutableMapOf<Int,ImageContainer>()
    val broadcastChannel = BroadcastChannel<ImageContainer>(200)

    val state = MutableLiveData<EventWrapper<Result>>()
    val loading = MutableLiveData<Boolean>()

    val navigation = MutableLiveData<EventWrapper<NavDirections>>()

    fun requestPlaceholders(){

        loading.value = true
        viewModelScope.launch {
            try {
                val result = httpGet("https://jsonplaceholder.typicode.com/photos")
                _data.value = ParseHelper.parseJson(result)
                val action = FirstPageDirections.actionFirstPageToSecondPage()
                state.value = EventWrapper(Result.Success(action))
            }catch (e:Exception){
                //error handling part just let user know the failure
                state.value = EventWrapper(Result.Failure("連線失敗，請稍後再試"))
            }finally {
                loading.value = false
            }
        }

    }

    fun loadBitmap(item: Placeholder) {
        viewModelScope.launch {
            val bitmap = getBitmap(item.thumbnailUrl)
//            Log.d("MainViewModel","get bitmap :$bitmap")
            if (bitmap!=null){
                broadcastChannel.send(ImageContainer(id = item.id,bitmap = bitmap))
            }
        }
    }

    suspend fun loadBitmap(url:String): Bitmap? {
        return getBitmap(url)
    }

    fun hasCache(id: Int): ImageContainer? {
        return cache[id]
    }


}