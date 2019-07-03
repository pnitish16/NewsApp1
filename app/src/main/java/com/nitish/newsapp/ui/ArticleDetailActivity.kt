package com.nitish.newsapp.ui

import android.os.Bundle
import com.bumptech.glide.Glide
import com.foodapp.foodbuddies.BaseActivity
import com.nitish.newsapp.R
import com.nitish.newsapp.model.ArticlesItem
import kotlinx.android.synthetic.main.activity_article_detail.*


class ArticleDetailActivity : BaseActivity() {

    private lateinit var articleItem: ArticlesItem

    override fun getLayoutResource(): Int {
        return R.layout.activity_article_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.extras != null) {
            val extra = intent.extras.apply { } ?: return
            articleItem = extra.get("item") as ArticlesItem
        }

        setScreenTitle(articleItem.author.apply { } ?: "No Title")

        showBackButton()

        tvAnchorName.text = articleItem.author
        tvPublishedDate.text = articleItem.publishedAt
        tvArticleTitle.text = articleItem.title
        tvArticleDescription.text = articleItem.description

        val urlImage1 = articleItem.urlToImage.apply { } ?: ""
        if (urlImage1.isNotEmpty()) {
            Glide.with(ivArticleDetailImage)
                .load(urlImage1)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(ivArticleDetailImage)
        }

    }

}
