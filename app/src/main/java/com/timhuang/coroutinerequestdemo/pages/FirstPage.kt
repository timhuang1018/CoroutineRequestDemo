package com.timhuang.coroutinerequestdemo.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.timhuang.coroutinerequestdemo.R
import com.timhuang.coroutinerequestdemo.config.Result
import com.timhuang.coroutinerequestdemo.helper.toast
import com.timhuang.coroutinerequestdemo.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.first_page.*

class FirstPage :Fragment() {

    private lateinit var viewModel:MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.first_page,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {

        viewModel = activity?.run { ViewModelProvider(this).get(MainViewModel::class.java) } ?: throw IllegalArgumentException("Wrong Activity")

        tv_request.setOnClickListener {
            viewModel.requestPlaceholders()
        }

        viewModel.state.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { result ->
                when(result){
                    is Result.Success->{
                        findNavController().navigate(result.nav)
                    }
                    is Result.Failure->{
                        toast(result.info)
                    }
                }
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {isLoading->
            progress_bar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
    }
}