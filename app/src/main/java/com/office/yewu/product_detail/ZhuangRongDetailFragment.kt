package com.office.yewu.product_detail

import com.kotlinlib.common.LLLP
import com.yp.baselib.BaseFragment
import com.yp.baselib.LayoutId
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R
import kotlinx.android.synthetic.main.fragment_zr_detail.*
import kotlinx.android.synthetic.main.fragment_zr_detail.llXGSP
import kotlinx.android.synthetic.main.fragment_zr_detail.rvVideo

/**
 * 妆容详情
 */
@LayoutId(R.layout.fragment_zr_detail)
class ZhuangRongDetailFragment : BaseFragment(), RVInterface{
    override fun init() {
        ivZrDetail.doLP<LLLP> {
            it.width = getAct().srnWidth - 100.dp - 20.dp
            it.height = it.width
        }

        titleBar1.setTitle("上妆 #步骤")

        titleBar1.click {
            if(!titleBar1.isOpened){
                titleBar1.open()
                titleBar2.close()
                tvStep.show()
                llXGSP.gone()
            } else {
                titleBar1.close()
                tvStep.gone()
            }
        }

        titleBar2.setTitle("相关视频 # VIDEO")

        rvVideo.wrap.gridManager(2).rvAdapter(
            listOf(1, 1, 1),
            { holder, pos ->
            }, R.layout.item_xgsp
        )

        titleBar2.click {
            if (!titleBar2.isOpened) {
                titleBar2.open()
                titleBar1.close()
                llXGSP.show()
                tvStep.gone()
            } else {
                titleBar2.close()
                llXGSP.gone()
            }
        }
    }
}