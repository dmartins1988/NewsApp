package com.example.newsapp.extensions

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadURL(url: String, context: Context) = Glide.with(context).load(url).into(this)