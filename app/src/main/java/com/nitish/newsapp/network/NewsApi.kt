package com.nitish.newsapp.network

import com.nitish.newsapp.model.NewsResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface NewsApi {

    @GET("top-headlines?country=us&apiKey=c518b268842048389bab74bf37ade925")
    fun getTopHeadlines(): Observable<NewsResponse>

    companion object {
        val SERVICE_ENDPOINT = "https://newsapi.org/v2/"

    }
}