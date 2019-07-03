package com.nitish.newsapp.presenter

import android.annotation.SuppressLint
import android.util.Log
import com.nitish.newsapp.model.ArticlesItem
import com.nitish.newsapp.network.NewsApi
import com.nitish.newsapp.network.NewsCatApi
import com.nitish.newsapp.network.ServiceFactory
import com.nitish.newsapp.view.MainActivityView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivityPresenter {

    private lateinit var view: MainActivityView
    private var categoryMap = mutableMapOf<Int, List<ArticlesItem?>>()

    val categoryList = listOf("", "Business", "Entertainment", "General", "Health", "Science")

    fun setView(view: MainActivityView) {
        this.view = view
    }

    @SuppressLint("CheckResult")
    fun loadNews1() {
        categoryMap.clear()
        val service = ServiceFactory.createRetrofitService(NewsCatApi::class.java, NewsApi.SERVICE_ENDPOINT)
        for ((count, category) in categoryList.withIndex()) {
            /*if (category == "") {
                service.getNews()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { response -> categoryMap.put(count,response.articles!!) },
                        { throwable -> Log.d("error", throwable.message!!) },
                        {
                            if ((categoryList.size - 1) == count) {
                                view.onLoadComplete(categoryMap)
                            }
                        })
            } else {*/
                service.getCatNews(category)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { response -> categoryMap.put(count,response.articles!!) },
                        { throwable -> Log.d("error", throwable.message!!) },
                        {
                            if ((categoryList.size - 1) == count) {
                                view.onLoadComplete(categoryMap)
                            }
                        })
//            }
        }
    }

    /*private fun getObservable1(category: String): Observable<NewsResponse> {
        val service = ServiceFactory.createRetrofitService(NewsCatApi::class.java, NewsApi.SERVICE_ENDPOINT)
        return service.getCatNews(category)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getObservable(): Observable<NewsResponse> {
        val service = ServiceFactory.createRetrofitService(NewsApi::class.java, NewsApi.SERVICE_ENDPOINT)
        return service.getTopHeadlines()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getObserver(): DisposableObserver<NewsResponse> {
        return object : DisposableObserver<NewsResponse>() {

            override fun onNext(movieResponse: NewsResponse) {
                Log.d(TAG, "OnNext" + movieResponse.status)
                headlines = movieResponse.articles!!
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
                view.showNews(headlines!!)
            }
        }
    }


    private fun getObserver1(category: String): DisposableObserver<NewsResponse> {
        return object : DisposableObserver<NewsResponse>() {

            override fun onNext(movieResponse: NewsResponse) {
                Log.d(TAG, "OnNext" + movieResponse.status)
                headlines = movieResponse.articles!!
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
                view.showCatNews(headlines!!, category)
            }
        }
    }*/


}