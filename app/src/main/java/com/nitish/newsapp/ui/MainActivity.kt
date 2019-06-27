package com.nitish.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import com.foodapp.foodbuddies.BaseActivity
import com.nitish.newsapp.fragments.ArticlesItemFragment
import com.nitish.newsapp.fragments.BlankFragment
import com.nitish.newsapp.model.ArticlesItem
import com.nitish.newsapp.model.NewsResponse
import com.nitish.newsapp.presenter.MainActivityPresenter
import com.nitish.newsapp.view.MainActivityView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(),
    ArticlesItemFragment.OnListFragmentInteractionListener, MainActivityView {

    private lateinit var presenter: MainActivityPresenter

    private var categoryMap = mutableMapOf<Int, List<ArticlesItem?>>()
    private val categoryList = listOf("Top Headlines", "Business", "Entertainment", "General", "Health", "Science")

    override fun getLayoutResource(): Int {
        return com.nitish.newsapp.R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setScreenTitle(com.nitish.newsapp.R.string.app_name)
        presenter = MainActivityPresenter()
        presenter.setView(this)
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))

        for (i in categoryList.indices) {
            tablayout.addTab(tablayout.newTab().setText(categoryList[i]))
        }

        presenter.loadNews1()
    }


    inner class PageAdapter internal constructor(fm: FragmentManager) :
        FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            val articles = categoryMap[position].apply { } ?: return BlankFragment()
            return ArticlesItemFragment.newInstance(1, articles)
        }

        override fun getCount(): Int {
            return categoryList.size
        }
    }


    override fun onListFragmentInteraction(item: ArticlesItem) {
        val detailActivityIntent = Intent(this, ArticleDetailActivity::class.java)
        detailActivityIntent.putExtra("item", item)
        startActivity(detailActivityIntent)
    }

    //region View Callback Functions
    override fun onNewsClick() {
    }


    override fun addNews(response: NewsResponse, category: Int) {
        val articles = response.articles.apply { } ?: return
        Log.d("" + category, "" + articles.size)
        categoryMap.put(category, articles)
    }

    override fun onLoadComplete() {
        val pageAdapter = PageAdapter(supportFragmentManager)
        viewPager.adapter = pageAdapter
    }
    //endregion
}
