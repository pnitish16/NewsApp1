package com.nitish.newsapp.adapter


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nitish.newsapp.R
import com.nitish.newsapp.model.ArticlesItem
import com.nitish.newsapp.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_articlesitem.view.*

class MyArticlesItemRecyclerViewAdapter(
    private val context: Context,
    private val mValues: List<ArticlesItem?>
) : RecyclerView.Adapter<MyArticlesItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_articlesitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]

        val item1 = item.apply { } ?: return
        holder.tvAuthor.text = item1.author
        holder.tvArticleName.text = item1.title
        holder.tvTime.text = item1.publishedAt

        val urlImage1 = item1.urlToImage.apply { } ?: ""
        if (urlImage1.isNotEmpty()) {
            Glide.with(holder.ivNewsImage)
                .load(urlImage1)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.ivNewsImage)
        }

        holder.mView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                (context as MainActivity).onListFragmentInteraction(item1)
            }
        })
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val tvArticleName: TextView = mView.tvArticleName
        val tvAuthor: TextView = mView.tvAuthor
        val tvTime: TextView = mView.tvTime
        val ivNewsImage: ImageView = mView.ivNewsImage
    }
}
