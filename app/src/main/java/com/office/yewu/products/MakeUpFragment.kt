package com.office.yewu.products

import com.kotlinlib.common.LLLP
import com.yp.baselib.BaseFragment
import com.yp.baselib.LayoutId
import com.yp.oom.R
import com.office.constant.OfficeConstants
import kotlinx.android.synthetic.main.fragment_makeup.*

@LayoutId(R.layout.fragment_makeup)
class MakeUpFragment: BaseFragment() {
    override fun init() {
        ivBig.doLP<LLLP> {
            val width = getAct().srnWidth - OfficeConstants.LEFT_LIST_WIDTH.dp - 17.5.dp
            it.width = width
            it.height = width * 350 / 260
        }
    }
}