package com.nitish.newsapp.network

import com.nitish.newsapp.model.NewsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsCatApi {
//    category=Entertainment
    //newsapi.org/v2/top-headlines?category=general&country=us&apiKey=c518b268842048389bab74bf37ade925
    @GET("top-headlines?country=us&apiKey=c518b268842048389bab74bf37ade925")
    fun getCatNews(@Query("category") categoryName: String): Observable<NewsResponse>

    @GET("top-headlines?country=us&apiKey=c518b268842048389bab74bf37ade925")
    fun getNews(): Observable<NewsResponse>

    @GET("top-headlines?country=us&apiKey=c518b268842048389bab74bf37ade925")
    fun getCatNewsMore(@Query("category") categoryName: String, @Query("page") page : Int): Observable<NewsResponse>

    companion object {
        val SERVICE_ENDPOINT = "https://newsapi.org/v2/"

    }
}