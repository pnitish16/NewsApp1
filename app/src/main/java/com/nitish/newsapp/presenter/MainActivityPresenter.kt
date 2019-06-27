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

    private val TAG = javaClass.simpleName
    private lateinit var view: MainActivityView
    private var headlines: List<ArticlesItem?>? = emptyList()

    val categoryList = listOf("Top Headlines", "Business", "Entertainment", "General", "Health", "Science")

    fun setView(view: MainActivityView) {
        this.view = view
    }

    @SuppressLint("CheckResult")
    fun loadNews1() {
        val service = ServiceFactory.createRetrofitService(NewsCatApi::class.java, NewsApi.SERVICE_ENDPOINT)
        for ((count, category) in categoryList.withIndex()) {
            if (category == "Top Headlines") {
                service.getNews()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { response -> view.addNews(response, count) },
                        { throwable -> Log.d("error", throwable.message!!) },
                        {
                            if ((categoryList.size - 1) == count) {
                                view.onLoadComplete()
                            }
                        })
            } else {
                service.getCatNews(category)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { response -> view.addNews(response, count) },
                        { throwable -> Log.d("error", throwable.message!!) },
                        {
                            if ((categoryList.size - 1) == count) {
                                view.onLoadComplete()
                            }
                        })
            }
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