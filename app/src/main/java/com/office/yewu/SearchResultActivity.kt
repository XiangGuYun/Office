package com.office.yewu

import android.os.Bundle
import com.kotlinlib.common.LLLP
import com.kotlinlib.common.bitmap.BmpUtils
import com.office.constant.MsgWhat
import com.office.net.Req
import com.yp.baselib.BaseActivity
import com.yp.baselib.LayoutId
import com.yp.baselib.StatusBarColor
import com.yp.baselib.utils.BusUtils
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.header.*

/**
 * 搜索结果页
 */
@StatusBarColor("#000000")
@LayoutId(R.layout.activity_search_result)
class SearchResultActivity : OfficeBaseActivity(), RVInterface, BmpUtils {
    override fun init(bundle: Bundle?) {

        ivSearch.hide()

        Req.getShangPinFenYeLieBiao(name = extraStr("search")) {
            rvSearchResult1.wrap.gridManager(3).rvAdapter(it.data,
                {
                    holder, pos ->

                    showBitmap(this, holder.iv(R.id.ivProduct), it.data[pos].imgCover)
                    holder.apply {
                        iv(R.id.ivProduct).doLP<LLLP> {
                            val size = (srnWidth - 100.dp - 30.dp - 2.5.dp) / 2
                            it.width = size
                            it.height = size
                        }
                        tv(R.id.tvDesc).text = it.data[pos].name
//                        itemClick { _ ->
//                            BusUtils.post(MsgWhat.SWITCH_TO_DETAIL_PAGE, it.data[pos].id)
//                        }
                    }

                }, R.layout.item_grid_product)
        }


    }
}