package com.office.yewu.product_detail

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.kotlinlib.common.LLLP
import com.yp.baselib.BaseFragment
import com.yp.baselib.LayoutId
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R
import kotlinx.android.synthetic.main.fragment_product_gallery.*


/**
 * 商品详情轮播图
 */
@LayoutId(R.layout.fragment_product_gallery)
class ProductGalleryFragment : BaseFragment(), RVInterface {

    companion object{
        fun newInstance(): ProductGalleryFragment {
            return ProductGalleryFragment()
        }
    }

    override fun init() {

        vpGallery.doLP<LLLP> {
            val size = getAct().srnWidth - 100.dp - 20.dp
            it.width = size
            it.height = size
        }

        val pagerAdapter: PagerAdapter = object : PagerAdapter() {
            override fun getCount(): Int {
                return 3
            }

            override fun isViewFromObject(p0: View, p1: Any): Boolean {
                return p0 == p1
            }

            override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
                container.removeView(any as View)
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val view = getAct().inflater.inflate(R.layout.iv_gallery, null)
                view.setBackgroundColor(randomColor())
                container.addView(view)
                return view
            }
        }


        vpGallery.adapter = pagerAdapter

        indicator.setDotNumber(3).bindViewPager(vpGallery)

    }


}