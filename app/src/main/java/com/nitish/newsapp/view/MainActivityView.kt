package com.nitish.newsapp.view

import com.nitish.newsapp.model.ArticlesItem

interface MainActivityView {
    fun onLoadComplete(articleMap : MutableMap<Int, List<ArticlesItem?>>,totalListPositions : MutableMap<Int,Int>)
    fun onLoadMore(articles: List<ArticlesItem?>)
}