package com.timhuang.coroutinerequestdemo.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.timhuang.coroutinerequestdemo.R
import com.timhuang.coroutinerequestdemo.adapter.GridItemsAdapter
import com.timhuang.coroutinerequestdemo.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.second_page.*
import java.lang.IllegalArgumentException

class SecondPage :Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter:GridItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.second_page,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        viewModel = activity?.run { ViewModelProvider(this).get(MainViewModel::class.java) } ?: throw IllegalArgumentException("Wrong Activity")

        adapter = GridItemsAdapter(viewModel)
        vertical_list.adapter = adapter
        vertical_list.layoutManager = GridLayoutManager(requireContext(),4)

        viewModel.data.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        tv_back.setOnClickListener {
            findNavController().navigateUp()
        }

    }
}