package com.office.yewu.products

import com.kotlinlib.common.LLLP
import com.yp.baselib.BaseFragment
import com.yp.baselib.LayoutId
import com.yp.oom.R
import kotlinx.android.synthetic.main.fragment_about.*

@LayoutId(R.layout.fragment_about)
class AboutFragment : BaseFragment() {
    override fun init() {
        ivStory.post {
            ivStory.doLP<LLLP> {
                it.width = getAct().srnWidth-100.dp
                it.height = it.width * 12 / 26
            }
        }

        videoView.doLP<LLLP> {
            it.width = getAct().srnWidth-100.dp
            it.height = it.width * 146 / 260
        }
    }
}