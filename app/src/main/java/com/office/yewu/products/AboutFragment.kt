package com.office.yewu.products

import com.kotlinlib.common.LLLP
import com.kotlinlib.common.bitmap.BmpUtils
import com.office.net.Req
import com.office.yewu.OfficeVideoActivity
import com.yp.baselib.BaseFragment
import com.yp.baselib.LayoutId
import com.yp.oom.R
import kotlinx.android.synthetic.main.fragment_about.*

@LayoutId(R.layout.fragment_about)
class AboutFragment : BaseFragment(),BmpUtils {
    override fun init() {
        ivStory.post {
            ivStory.doLP<LLLP> {
                it.width = getAct().srnWidth-140.dp
                it.height = it.width * 12 / 26
            }
        }

        videoView.doLP<LLLP> {
            it.width = getAct().srnWidth-140.dp
            it.height = it.width * 146 / 260
        }

        Req.getBaseParams(1002){
            videoView.click { v->
                goTo<OfficeVideoActivity>("isNetVideo" to true, "path" to it.data)
            }
            ivPlay.click {v->
                goTo<OfficeVideoActivity>("isNetVideo" to true, "path" to it.data)
            }
            showBitmap(getAct(), ivPre, it.data+"?x-oss-process=video/snapshot,t_1000,f_png,w_0,h_0,m_fast")
        }

    }
}