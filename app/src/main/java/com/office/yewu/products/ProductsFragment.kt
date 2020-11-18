package com.office.yewu.products

import android.os.Bundle
import com.kotlinlib.common.LLLP
import com.kotlinlib.common.bitmap.BmpUtils
import com.office.bean.ShangPinFenYeLieBiao
import com.office.constant.Id
import com.office.constant.MsgWhat
import com.office.net.OK
import com.office.net.Req
import com.yp.baselib.BaseFragment
import com.yp.baselib.LayoutId
import com.yp.baselib.listener.VpChangeListener
import com.yp.baselib.utils.BusUtils
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R
import kotlinx.android.synthetic.main.fragment_products.*
import kotlin.math.min

/**
 * Products对应的右侧Fragment
 */
@LayoutId(R.layout.fragment_products)
class ProductsFragment : BaseFragment(), RVInterface, BmpUtils {

    override fun init() {

    }

    val list = ArrayList<ArrayList<ShangPinFenYeLieBiao.Data>>()

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        val id = arguments!!.getInt("id")

        Req.getShangPinFenYeLieBiao(categoryId = if(id==-1) OK.OPTIONAL else id.toString()) {
            if(it.data == null) return@getShangPinFenYeLieBiao
            val itemSize = it.data.size
            val pageSize = (itemSize + 3) / 4

            for (i in 0 until pageSize) {
                list.add(ArrayList())
            }

            list.forEachIndexed { index, list ->
                list.addAll(it.data.subList(4 * index, min(4 * index + 4, it.data.size)))
            }

            vpDetail.setViewAdapter(pageSize) {
                val view = getAct().inflate(R.layout.fragment_products_sub)
                view.rv(R.id.rvDetail).wrap.gridManager(2).rvMultiAdapter(list[it], { holder, pos ->
                    showBitmap(act(), holder.iv(R.id.ivProduct), list[it][pos].imgCover)
                    holder.apply {
                        iv(R.id.ivProduct).doLP<LLLP> {
                            val size = (getAct().srnWidth - 140.dp - 30.dp - 2.5.dp) / 2
                            it.width = size-10.dp
                            it.height = size-10.dp
                        }
                        tv(R.id.tvDesc).text = list[it][pos].titleList
                        itemClick { _ ->
                            BusUtils.post(MsgWhat.SWITCH_TO_DETAIL_PAGE, list[it][pos].id)
                        }
                        tv(R.id.tvName).text = list[it][pos].name
                        tv(R.id.tvEnName).text = list[it][pos].enName
                    }
                }, {
                    0
                }, R.layout.item_grid_product)
                view
            }

        }

        var whiteDotIndex = 0

        if(list.size > 1){
            rvDots.wrap.managerHorizontal().rvMultiAdapter(
                list,
                { holder, pos ->

                },
                {
                    if (whiteDotIndex == it) 0 else 1
                }, R.layout.item_dot_white, R.layout.item_dot_dark
            )

            vpDetail.setOnPageChangeListener(object : VpChangeListener {
                override fun onPageSelected(p0: Int) {
                    whiteDotIndex = p0
                    rvDots.update()
                }
            })
        }


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

//            FragPagerUtils<ProductsSubFragment>(this, vpDetail, list.map {
//                ProductsSubFragment.newInstance(it)
//            })