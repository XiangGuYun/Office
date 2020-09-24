package com.office.yewu.products

import android.widget.LinearLayout
import com.yp.baselib.BaseFragment
import com.yp.baselib.LayoutId
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R
import kotlinx.android.synthetic.main.fragment_products_sub.*

@LayoutId(R.layout.fragment_products_sub)
class ProductsSubFragment : BaseFragment(), RVInterface {
    override fun init() {
        rvDetail.wrap.gridManager(2).rvMultiAdapter(listOf(1,1,1,1), {
            holder, pos ->
            holder.iv(R.id.ivProduct).doLP<LinearLayout.LayoutParams> {
                val size = (getAct().srnWidth- 100.dp -30.dp - 2.5.dp)/2
                it.width = size
                it.height = size
            }
        },{
            0
        }, R.layout.item_grid_product)
    }
}