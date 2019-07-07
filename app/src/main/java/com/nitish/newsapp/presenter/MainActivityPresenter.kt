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
    private var totalListPositions = mutableMapOf<Int, Int>()

    val categoryList = listOf("", "Business", "Entertainment", "General", "Health", "Science")

    fun setView(view: MainActivityView) {
        this.view = view
    }

    @SuppressLint("CheckResult")
    fun loadNews1() {
        categoryMap.clear()
        val service = ServiceFactory.createRetrofitService(NewsCatApi::class.java, NewsApi.SERVICE_ENDPOINT)
        for ((count, category) in categoryList.withIndex()) {
            service.getCatNews(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response -> categoryMap[count] = response.articles!! },
                    { throwable -> Log.d("error", throwable.message!!) },
                    {
                        if ((categoryList.size - 1) == count) {
                            view.onLoadComplete(categoryMap, totalListPositions)
                        }
                    })
        }
    }


    @SuppressLint("CheckResult")
    fun loadNewsMore(page: Int,categoryPosition: Int) {
        val service = ServiceFactory.createRetrofitService(NewsCatApi::class.java, NewsApi.SERVICE_ENDPOINT)
        service.getCatNewsMore(categoryList[categoryPosition], page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> view.onLoadMore(response.articles!!) },
                { throwable -> Log.d("error", throwable.message!!) }
            )
    }
}