package com.office.yewu.products

import android.support.v4.view.ViewPager
import com.yp.baselib.BaseFragment
import com.yp.baselib.FragPagerUtils
import com.yp.baselib.LayoutId
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R
import kotlinx.android.synthetic.main.fragment_zhuang_rong.*

@LayoutId(R.layout.fragment_zhuang_rong)
class ZhuangRongFragment : BaseFragment(), RVInterface {
    override fun init() {
        FragPagerUtils<ZhuangRongSubFragment>(this, vpZhuangRong, listOf(
            ZhuangRongSubFragment(), ZhuangRongSubFragment()
        ))

        var whiteDotIndex = 0

        rvDots.wrap.managerHorizontal().rvMultiAdapter(listOf(1, 1),
            {
                    holder, pos ->
            },
            {
                if(whiteDotIndex == it) 0 else 1
            }, R.layout.item_dot_white, R.layout.item_dot_dark)

        vpZhuangRong.setOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {
                whiteDotIndex = p0
                rvDots.update()
            }

            override fun onPageScrollStateChanged(p0: Int) {
            }

        })
    }
}