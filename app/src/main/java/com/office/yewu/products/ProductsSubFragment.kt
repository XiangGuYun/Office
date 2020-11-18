package com.office.yewu.products

import android.os.Bundle
import android.widget.LinearLayout
import com.office.bean.ShangPinFenYeLieBiao
import com.yp.baselib.BaseFragment
import com.yp.baselib.LayoutId
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R
import kotlinx.android.synthetic.main.fragment_products_sub.*

@LayoutId(R.layout.fragment_products_sub)
class ProductsSubFragment : BaseFragment(), RVInterface {

    companion object{
        fun newInstance(list:ArrayList<ShangPinFenYeLieBiao.Data>): ProductsSubFragment {
            return ProductsSubFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("list", list)
                }
            }
        }
    }

    override fun init() {

        val list = arguments!!.getSerializable("list") as ArrayList<ShangPinFenYeLieBiao.Data>

        rvDetail.wrap.gridManager(2).rvMultiAdapter(list, {
            holder, pos ->
            holder.iv(R.id.ivProduct).doLP<LinearLayout.LayoutParams> {
                val size = (getAct().srnWidth- 140.dp -30.dp - 2.5.dp)/2
                it.width = size
                it.height = size
            }
            holder.tv(R.id.tvDesc).text = list[pos].name
        },{
            0
        }, R.layout.item_grid_product)
    }
}