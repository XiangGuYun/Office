package com.office.yewu.products

import android.graphics.Color
import android.support.design.widget.TabLayout
import android.view.LayoutInflater
import com.kotlinlib.common.LLLP
import com.yp.baselib.BaseFragment
import com.yp.baselib.LayoutId
import com.yp.baselib.utils.fragment.old.FragPagerUtils
import com.yp.oom.R
import kotlinx.android.synthetic.main.fragment_new_products.*


/**
 * 新品
 */
@LayoutId(R.layout.fragment_new_products)
class NewProductFragment : BaseFragment() {
    override fun init() {
       initViewPager()
    }

    private fun initViewPager() {
        flNewProduct.doLP<LLLP> {
            val width = getAct().srnWidth - 100.dp - 20.dp
            it.width = width
            it.height = width * 350 / 260
        }

        vpNewProduct.setViewAdapter(5) {
            val view = LayoutInflater.from(getAct()).inflate(R.layout.iv_gallery, null)
            view.setBackgroundColor(randomColor())
            view
        }


        tabLayout.tabMode = TabLayout.MODE_FIXED //设置固定Tab模式

        for (i in 1..5) {
            val tab = tabLayout.newTab()
            tabLayout.addTab(tab)
        }

        //将TabLayout和ViewPager关联起来
        tabLayout.setupWithViewPager(vpNewProduct, true)

        tabLayout.setSelectedTabIndicatorColor(Color.WHITE)
        tabLayout.setSelectedTabIndicatorHeight(1.5.dp)

    }

}