package com.timhuang.coroutinerequestdemo.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timhuang.coroutinerequestdemo.data.Placeholder
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timhuang.coroutinerequestdemo.R
import com.timhuang.coroutinerequestdemo.helper.EventWrapper
import com.timhuang.coroutinerequestdemo.pages.SecondPageDirections
import com.timhuang.coroutinerequestdemo.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.item_placeholder.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect

class GridItemsAdapter(private val viewModel: MainViewModel) :ListAdapter<Placeholder,GridItemViewHolder>(PlaceHolderDiff){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridItemViewHolder {
        return GridItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_placeholder,parent,false),
            viewModel
        )
    }

    override fun onBindViewHolder(holder: GridItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewRecycled(holder: GridItemViewHolder) {
        super.onViewRecycled(holder)
        holder.clear()
    }

}

@ExperimentalCoroutinesApi
class GridItemViewHolder(itemView:View, val viewModel: MainViewModel):RecyclerView.ViewHolder(itemView){

    private val scope = CoroutineScope(SupervisorJob()+Dispatchers.Main)
    fun bind(item:Placeholder){
        //clear loading if view get reused still left state
        setLoaging(false)

        with(itemView.iv_thumb_nail){
            viewModel.hasCache(item.id)?.let {
                setContext(item)
                this.setImageBitmap(it.bitmap) } ?: drawNow(item)
        }
    }

    private fun setContext(item: Placeholder) {
        itemView.tv_id_number.text = item.id.toString()
        itemView.tv_title.text = item.title
    }

    private fun drawNow(item: Placeholder) {
        //ask viewmodel to draw
        viewModel.loadBitmap(item)
        //set loading
        setLoaging(true)
        //wait for drawing
        scope.launch {
            val flow = viewModel.broadcastChannel
                .asFlow()
            flow.collect {container->

                Log.d("GridItemViewHolder","flow:$flow , receive item :${container.id}")
                if (container.id==item.id){
                    itemView.iv_thumb_nail.setImageBitmap(container.bitmap)
                    setContext(item)
                    setLoaging(false)
                    itemView.setOnClickListener {
                        goThirdPage(item)
                    }
                    cancel()
                }
            }
        }
    }

    private fun goThirdPage(item: Placeholder) {
        viewModel.navigation.value = EventWrapper(SecondPageDirections.actionSecondPageToThirdPage(item))
    }

    private fun setLoaging(isLoading: Boolean) {
        if (isLoading){
            itemView.item_progressbar.visibility = View.VISIBLE
        }else{
            itemView.item_progressbar.visibility = View.GONE
        }
    }

    fun clear(){
        scope.cancel()
    }
}

object PlaceHolderDiff:DiffUtil.ItemCallback<Placeholder>(){
    override fun areItemsTheSame(oldItem: Placeholder, newItem: Placeholder): Boolean {
        return oldItem.id == newItem.id
    }
    //ignore this part since we don't have item change
    override fun areContentsTheSame(oldItem: Placeholder, newItem: Placeholder): Boolean {
        return oldItem.id == newItem.id
    }

}