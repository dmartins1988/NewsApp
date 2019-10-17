package com.example.newsapp.presentation.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.example.newsapp.R
import com.example.newsapp.api.NetworkState
import com.example.newsapp.extensions.gone
import com.example.newsapp.extensions.onSearchQuerySubmit
import com.example.newsapp.extensions.visible
import com.example.newsapp.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.news_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class NewsFragment : BaseFragment(), NewsAdapter.OnNewsClickListener {

    private val viewModel: NewsViewModel by viewModel()
    private lateinit var adapter : NewsAdapter

    companion object {
        fun newInstance() =  NewsFragment()
    }

    override fun getLayoutId(): Int = R.layout.news_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        configureMenu(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        configureObservables()
        configureOnClick()
    }

    private fun configureRecyclerView() {
        adapter = NewsAdapter(this)
        fragment_search_news_rv.adapter = adapter
    }

    private fun configureObservables() {
        viewModel.networkState?.observe(this, Observer { adapter.updateNetworkStatus(it) })
        viewModel.news.observe(this, Observer { adapter.submitList(it) })
    }

    private fun configureOnClick() {
        fragment_search_news_empty_list_button.setOnClickListener { viewModel.refreshAllList() }
    }

    private fun configureMenu(menu: Menu) {
        val searchMenuItem = menu.findItem(R.id.action_search)
        val currentQuery = viewModel.getCurrentyQuery()
        val searchView = searchMenuItem.actionView as SearchView
        if (currentQuery.isNotEmpty()) {
            searchMenuItem.expandActionView()
            searchView.setQuery(currentQuery, false)
            searchView.clearFocus()
        }
        searchView.onSearchQuerySubmit {query ->
            viewModel.fetchQuery(query)
        }
    }

    override fun onClickRetry() {
        viewModel.refreshFailedRequest()
    }

    override fun whenListIsUpdated(size: Int, networkState: NetworkState?) {
        updateUIWhenEmptyList(size, networkState)
        updateUIWhenLoading(size, networkState)
    }

    private fun updateUIWhenLoading(size: Int, networkState: NetworkState?) {
        fragment_search_news_empty_list_title.gone()
        fragment_search_news_empty_list_image.gone()
        fragment_search_news_empty_list_button.gone()
        if (size == 0) {
            if (networkState == NetworkState.SUCCESS) {
                fragment_search_news_empty_list_title.text = getString(R.string.no_result_found)
                fragment_search_news_empty_list_title.visible()
                fragment_search_news_empty_list_image.visible()
                fragment_search_news_empty_list_button.gone()
            }
            else if (networkState == NetworkState.LOADING) {
                fragment_search_news_empty_list_title.text = getString(R.string.technical_error)
                fragment_search_news_empty_list_title.gone()
                fragment_search_news_empty_list_image.gone()
                fragment_search_news_empty_list_button.gone()
            }
        }
    }

    private fun updateUIWhenEmptyList(size: Int, networkState: NetworkState?) {
        fragment_search_news_progress.visibility = if (size == 0 && networkState == NetworkState.LOADING)  View.VISIBLE else View.GONE
    }
}
