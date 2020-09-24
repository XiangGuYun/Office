package com.office.yewu.products

import com.kotlinlib.common.LLLP
import com.yp.baselib.BaseFragment
import com.yp.baselib.LayoutId
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R
import kotlinx.android.synthetic.main.fragment_zhuang_rong_sub.*

@LayoutId(R.layout.fragment_zhuang_rong_sub)
class ZhuangRongSubFragment : BaseFragment(), RVInterface {
    override fun init() {
        rvZR.wrap.gridManager(2).rvAdapter(
            listOf(1, 1, 1, 1, 1, 1),
            { holder, pos ->

                holder.iv(R.id.ivZR).post {
                    holder.iv(R.id.ivZR).doLP<LLLP> {
                        it.width = (getAct().srnWidth - 130.dp - 30.dp - 15.dp)/2
                        it.height = (getAct().srnWidth - 130.dp - 30.dp - 15.dp)/2
                    }
                }

            },
            R.layout.item_zr
        )

    }
}