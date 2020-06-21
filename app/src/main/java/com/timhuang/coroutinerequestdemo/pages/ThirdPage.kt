package com.timhuang.coroutinerequestdemo.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.timhuang.coroutinerequestdemo.R
import com.timhuang.coroutinerequestdemo.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.second_page.*
import kotlinx.android.synthetic.main.third_page.*
import kotlinx.android.synthetic.main.third_page.tv_back
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ThirdPage :Fragment() {

    val args : ThirdPageArgs by navArgs()

    private lateinit var viewModel :MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.third_page,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {

        viewModel = activity?.run { ViewModelProvider(this).get(MainViewModel::class.java) } ?: throw IllegalArgumentException("Wrong Activity")

        with(args.placeholder){
            tv_id_number.text = String.format("id: %s",this.id)
            tv_title.text = String.format("title: %s",this.title)

        }
        lifecycleScope.launch {
            viewModel.loadBitmap(args.placeholder.url)?.let { iv_big_image.setImageBitmap(it) }
        }

        tv_back.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}