package com.example.newsapp.presentation.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.api.NetworkState
import com.example.newsapp.extensions.gone
import com.example.newsapp.extensions.loadURL
import com.example.newsapp.extensions.visible
import com.example.newsapp.model.Article
import kotlinx.android.synthetic.main.item_article.view.*
import kotlinx.android.synthetic.main.item_network_state.view.*
import java.lang.IllegalArgumentException


class NewsAdapter(private val callback: OnNewsClickListener) : PagedListAdapter<Article, RecyclerView.ViewHolder>(
    diffCallback
) {

    interface OnNewsClickListener {
        fun onClickRetry()
        fun whenListIsUpdated(size: Int, networkState: NetworkState?)
    }

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when(viewType) {
            R.layout.item_article -> NewsViewsHolder(
                view
            )
            R.layout.item_network_state -> NetworkStateViewHolder(
                view
            )
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_article -> (holder as NewsViewsHolder).bindTo(getItem(position))
            R.layout.item_network_state -> (holder as NetworkStateViewHolder).bindTo(networkState, callback)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            R.layout.item_article
        }
    }

    override fun getItemCount(): Int {
        this.callback.whenListIsUpdated(super.getItemCount(), this.networkState)
        return super.getItemCount()
    }

    fun updateNetworkStatus(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow()) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.SUCCESS

    class NewsViewsHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        fun bindTo(item: Article?) {
            item?.let {
                itemView.item_article_img.loadURL(it.urlToImage, itemView.context)
                itemView.item_article_name.text = item.title
            }
        }
    }

    class NetworkStateViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        fun bindTo(networkState: NetworkState?, callback: OnNewsClickListener) {
            hideViews()
            setVisibleRightViews(networkState)
            itemView.item_network_state_button.setOnClickListener { callback.onClickRetry() }
        }

        private fun hideViews() {
            itemView.item_network_state_title.gone()
            itemView.item_network_state_progress_bar.gone()
            itemView.item_network_state_button.gone()
        }

        private fun setVisibleRightViews(networkState: NetworkState?) {
            when(networkState) {
                NetworkState.SUCCESS -> hideViews()
                NetworkState.LOADING -> {
                    itemView.item_network_state_progress_bar.visible()
                }
                NetworkState.FAILED -> {
                    itemView.item_network_state_title.visible()
                    itemView.item_network_state_button.visible()
                }
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean = oldItem == newItem
        }
    }

}


