package com.example.newsapp.presentation.ui.main

import com.example.newsapp.R
import com.example.newsapp.presentation.base.BaseFragment
import com.example.newsapp.presentation.ui.recyclerview.NewsAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class NewsFragment : BaseFragment() {

    private val viewModel: NewsViewModel by viewModel()
    private lateinit var adapter : NewsAdapter

    companion object {
        fun newInstance(): NewsFragment {
            return NewsFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.main_fragment


}
