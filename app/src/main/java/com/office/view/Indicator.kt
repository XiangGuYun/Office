package com.office.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R

class Indicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr), RVInterface {

    private var whiteDotIndex = 0
    private val rv = RecyclerView(context)

    init {
        rv.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        addView(rv)
    }

    fun setRV(dotNumber:Int): Indicator {
        rv.wrap.managerHorizontal().rvMultiAdapter(
            (0 until dotNumber).toList(),
            { holder, pos ->
            },
            {
                if (whiteDotIndex == it) 0 else 1
            }, R.layout.item_dot_white, R.layout.item_dot_dark
        )
        return this
    }

    fun bindViewPager(vp: ViewPager) {
        vp.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {
                whiteDotIndex = p0
                rv.adapter?.notifyDataSetChanged()
            }

            override fun onPageScrollStateChanged(p0: Int) {
            }

        })
    }

}