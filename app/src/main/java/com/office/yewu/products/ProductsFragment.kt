package com.office.yewu.products

import android.os.Bundle
import android.os.Message
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.kotlinlib.common.bitmap.BmpUtils
import com.office.bean.ShangPinFenYeLieBiao
import com.office.constant.MsgWhat
import com.office.net.Req
import com.office.yewu.product_detail.CzProductDetailFragment
import com.yp.baselib.BaseFragment
import com.yp.baselib.FragPagerUtils
import com.yp.baselib.LayoutId
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R
import kotlinx.android.synthetic.main.fragment_products.*
import org.greenrobot.eventbus.EventBus
import kotlin.math.min

@LayoutId(R.layout.fragment_products)
class ProductsFragment : BaseFragment(), RVInterface, BmpUtils {
    override fun init() {
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        val id = arguments!!.getInt("id")

        Req.getShangPinFenYeLieBiao(categoryId = id.toString()) {
            val itemSize = it.data.size
            val pageSize = (itemSize + 3) / 4

            val list = ArrayList<ArrayList<ShangPinFenYeLieBiao.Data>>()

            for (i in 0 until pageSize) {
                list.add(ArrayList())
            }

            list.forEachIndexed { index, list ->
                list.addAll(it.data.subList(4 * index, min(4 * index + 4, it.data.size)))
            }

//            FragPagerUtils<ProductsSubFragment>(this, vpDetail, list.map {
//                ProductsSubFragment.newInstance(it)
//            })
            vpDetail.setViewAdapter(pageSize) {
                val view = LayoutInflater.from(act()).inflate(R.layout.fragment_products_sub, null)
                view.rv(R.id.rvDetail).wrap.gridManager(2).rvMultiAdapter(list[it], { holder, pos ->
                    holder.iv(R.id.ivProduct).doLP<LinearLayout.LayoutParams> {
                        val size = (getAct().srnWidth - 100.dp - 30.dp - 2.5.dp) / 2
                        it.width = size
                        it.height = size
                    }
                    showBitmap(act(), holder.iv(R.id.ivProduct), list[it][pos].imgCover)
                    holder.tv(R.id.tvDesc).text = list[it][pos].name
                    holder.itemClick {v->
                        EventBus.getDefault()
                            .post(Message.obtain().apply {
                                what = MsgWhat.SWITCH_TO_DETAIL_PAGE
                                obj = list[it][pos].id
                            })
                    }
                }, {
                    0
                }, R.layout.item_grid_product)
                view
            }

        }


        var whiteDotIndex = 0

        rvDots.wrap.managerHorizontal().rvMultiAdapter(
            listOf(1, 1),
            { holder, pos ->

            },
            {
                if (whiteDotIndex == it) 0 else 1
            }, R.layout.item_dot_white, R.layout.item_dot_dark
        )

        vpDetail.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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

    companion object {
        fun newInstance(id: Int): ProductsFragment {
            return ProductsFragment().apply {
                arguments = Bundle().apply {
                    putInt("id", id)
                }
            }
        }
    }

}