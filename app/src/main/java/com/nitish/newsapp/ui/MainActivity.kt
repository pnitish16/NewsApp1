package com.nitish.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.foodapp.foodbuddies.BaseActivity
import com.nitish.newsapp.fragments.ArticlesItemFragment
import com.nitish.newsapp.fragments.BlankFragment
import com.nitish.newsapp.model.ArticlesItem
import com.nitish.newsapp.presenter.MainActivityPresenter
import com.nitish.newsapp.view.MainActivityView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(),
    ArticlesItemFragment.OnListFragmentInteractionListener, MainActivityView {

    private lateinit var presenter: MainActivityPresenter

    var categoryMap = mutableMapOf<Int, List<ArticlesItem?>>()
    private lateinit var pagerAdapter: PagerAdapter
    private val categoryList = listOf("Top Headlines", "Business", "Entertainment", "General", "Health", "Science")
    private var countPositions = mutableMapOf<Int, Int>()
    private var currentFragPosition = 0
    private var fragmentTags = mutableMapOf<Int, String>()
    var lastCompletelyVisibleItemPosition = 0

    override fun getLayoutResource(): Int {
        return com.nitish.newsapp.R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setScreenTitle(com.nitish.newsapp.R.string.app_name)
        presenter = MainActivityPresenter()
        presenter.setView(this)
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))
        /*viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                viewPager.currentItem = p0
                (pagerAdapter as PageAdapter).getFragment(p0)?.onResume()
            }

        })*/
        for (i in categoryList.indices) {
            tablayout.addTab(tablayout.newTab().setText(categoryList[i]))
        }

        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                val tab = p0.apply { } ?: return
                viewPager.currentItem = tab.position
            }

        })
        loadArticles()
    }

    fun loadArticles() {

        for (i in categoryList.indices) {
            countPositions[i] = 1
        }
        pbLoading.visibility = View.VISIBLE
        presenter.loadNews1()
    }

    //region PageAdapter inflating the categories
    inner class PageAdapter internal constructor(fm: FragmentManager) :
        FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            val articles = categoryMap[position].apply { } ?: return BlankFragment()
            return ArticlesItemFragment.newInstance(1, articles, position)
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val obj = super.instantiateItem(container, position)
            if (obj is Fragment) {
                val tag = obj.tag!!
                fragmentTags[position] = tag
            }
            return obj
        }

        override fun getCount(): Int {
            return categoryList.size
        }

        override fun getItemPosition(`object`: Any): Int {
            // POSITION_NONE makes it possible to reload the PagerAdapter
            return PagerAdapter.POSITION_NONE
        }

        fun getFragment(position: Int): Fragment? {
            val tag = fragmentTags[position] ?: return null
            return supportFragmentManager.findFragmentByTag(tag)
        }

    }
    //endregion

    //region handling the article item click
    override fun onListFragmentInteraction(item: ArticlesItem) {
        val detailActivityIntent = Intent(this, ArticleDetailActivity::class.java)
        detailActivityIntent.putExtra("item", item)
        startActivity(detailActivityIntent)
    }
    //endregion

    //region presenter callback method
    override fun onLoadComplete(
        articleMap: MutableMap<Int, List<ArticlesItem?>>,
        totalListPositions: MutableMap<Int, Int>
    ) {

        this.categoryMap = articleMap

        pagerAdapter = PageAdapter(supportFragmentManager)

        val handler = Handler()
        handler.postDelayed({
            //Do something after 100ms
            pbLoading.visibility = View.GONE
            viewPager.adapter = pagerAdapter
        }, 2000)

    }
    //endregion

    //region load more handling
    fun onLoadMore(fragposition: Int, lastCompletelyVisibleItemPosition: Int) {
        currentFragPosition = fragposition
        countPositions[fragposition] = countPositions[fragposition]!! + 1
        pbLoading.visibility = View.VISIBLE
        presenter.loadNewsMore(countPositions[fragposition]!!, currentFragPosition)
        this.lastCompletelyVisibleItemPosition = lastCompletelyVisibleItemPosition
    }

    override fun onLoadMore(articles: List<ArticlesItem?>) {

        if (articles.isEmpty()) {
            pbLoading.visibility = View.GONE
            return
        }


        val listItems = mutableListOf<ArticlesItem?>()

        listItems.addAll(categoryMap[currentFragPosition]!!)
        listItems.addAll(articles)
        categoryMap[currentFragPosition] = listItems

//        pagerAdapter = PageAdapter(supportFragmentManager)
        pagerAdapter.notifyDataSetChanged()

        val handler = Handler()
        handler.postDelayed({
            //Do something after 100ms
            pbLoading.visibility = View.GONE
            viewPager.adapter = pagerAdapter
            viewPager.currentItem = currentFragPosition
        }, 2000)


    }
    //endregion
}
