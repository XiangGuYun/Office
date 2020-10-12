package com.office.yewu

import android.os.Bundle
import com.office.net.Req
import com.yp.baselib.BaseActivity
import com.yp.baselib.LayoutId
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R
import kotlinx.android.synthetic.main.activity_search_result.*

/**
 * 搜索结果页
 */
@LayoutId(R.layout.activity_search_result)
class SearchResultActivity : BaseActivity(), RVInterface {
    override fun init(bundle: Bundle?) {

        Req.getShangPinFenYeLieBiao(name = extraStr("search")) {
            rvSearchResult1.wrap.gridManager(3).rvAdapter(it.data,
                {
                    holder, pos ->
                }, R.layout.item_shopping_cart)
        }

    }
}