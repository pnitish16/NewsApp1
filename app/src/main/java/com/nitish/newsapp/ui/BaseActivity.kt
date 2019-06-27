package com.foodapp.foodbuddies

import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import com.nitish.newsapp.R
import kotlinx.android.synthetic.main.activity_base.*

/**
 * Created by Nitish_PC on 3/12/2017.
 */
abstract class BaseActivity : AppCompatActivity() {

    private lateinit var mTextViewScreenTitle: TextView

    private lateinit var mImageButtonBack: ImageButton

    protected abstract fun getLayoutResource(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var coordinatorLayout: CoordinatorLayout =
            layoutInflater.inflate(R.layout.activity_base, null) as CoordinatorLayout
        var activityContainer: FrameLayout = coordinatorLayout.findViewById(R.id.layout_container)
        mTextViewScreenTitle = coordinatorLayout.findViewById(R.id.text_screen_title)
        mImageButtonBack = coordinatorLayout.findViewById(R.id.image_back_button)

        layoutInflater.inflate(getLayoutResource(), activityContainer, true)

        hideBackButton()

        super.setContentView(coordinatorLayout)
    }

    private fun configureToolbar() {
        setSupportActionBar(mainToolbar)
    }

    fun setScreenTitle(resId: Int) {
        mTextViewScreenTitle.text = getString(resId)
    }

    fun setScreenTitle(title: String) {
        mTextViewScreenTitle.text = title
    }

    fun hideBackButton() {
        mImageButtonBack.visibility = View.GONE
    }

    fun showBackButton() {
        mImageButtonBack.visibility = View.VISIBLE
        mImageButtonBack.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                finish()
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                    finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
