package com.nitish.newsapp.view

import com.nitish.newsapp.model.NewsResponse

interface MainActivityView {
    fun onNewsClick()
    fun addNews(response: NewsResponse, category : Int)
    fun onLoadComplete()
}