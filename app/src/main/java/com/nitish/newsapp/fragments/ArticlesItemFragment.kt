package com.nitish.newsapp.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nitish.newsapp.adapter.MyArticlesItemRecyclerViewAdapter
import com.nitish.newsapp.model.ArticlesItem
import com.nitish.newsapp.ui.MainActivity
import com.nitish.newsapp.utils.EndlessRecyclerOnScrollListener


/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [ArticlesItemFragment.OnListFragmentInteractionListener] interface.
 */
class ArticlesItemFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1
    private var position: Int = -1
    private lateinit var items: List<ArticlesItem?>
    private lateinit var rvArticles: RecyclerView
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var rootView: View

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
            items = it.getParcelableArrayList(ARG_ITEMS)
            position = it.getInt(ARG_POSITION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(com.nitish.newsapp.R.layout.fragment_articlesitem_list, container, false)
        rootView = view

        return view
    }

    override fun onResume() {
        super.onResume()
        // Set the adapter
        swipeLayout = rootView.findViewById(com.nitish.newsapp.R.id.pullToRefresh)
        rvArticles = rootView.findViewById(com.nitish.newsapp.R.id.rvArticles)

        val items1 = (activity as MainActivity).categoryMap[position]
        with(rvArticles) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = MyArticlesItemRecyclerViewAdapter(activity!!, items1!!)
//            adapter = MyArticlesItemRecyclerViewAdapter(activity!!, items)
        }
       /* if ((activity as MainActivity).lastCompletelyVisibleItemPosition != 0)
            rvArticles.smoothScrollToPosition((activity as MainActivity).lastCompletelyVisibleItemPosition)*/


        swipeLayout.setOnRefreshListener {
            (activity as MainActivity).loadArticles()
            swipeLayout.isRefreshing = false
        }

        rvArticles.addOnScrollListener(object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
//                Toast.makeText(activity!!,"Loading Dat", Toast.LENGTH_SHORT).show()
                (activity as MainActivity).onLoadMore(position,  (rvArticles.layoutManager as LinearLayoutManager).findLastVisibleItemPosition())
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: ArticlesItem)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
        const val ARG_ITEMS = "list_items"
        const val ARG_POSITION = "position"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int, items: List<ArticlesItem?>, position: Int) =
            ArticlesItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                    val articles = ArrayList<ArticlesItem?>(items.size)
                    articles.addAll(items)
                    putParcelableArrayList(ARG_ITEMS, articles)
                    putInt(ARG_POSITION, position)
                }
            }
    }
}
