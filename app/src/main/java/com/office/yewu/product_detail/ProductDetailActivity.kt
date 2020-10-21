package com.office.yewu.product_detail

import android.os.Bundle
import com.office.yewu.OfficeBaseActivity
import com.yp.baselib.LayoutId
import com.yp.baselib.StatusBarColor
import com.yp.baselib.utils.fragment.old.FragmentUtils
import com.yp.oom.R
import kotlinx.android.synthetic.main.header.*

/**
 * 产品详情或妆容详情
 */
@StatusBarColor("#000000")
@LayoutId(R.layout.activity_product_detail)
class ProductDetailActivity : OfficeBaseActivity() {
    override fun init(bundle: Bundle?) {

        ivSearch.hide()

        when(extraStr("type")){
            "Product"->{
                FragmentUtils(this,
                    CzProductDetailFragment.newInstance(extraInt("id", -1), true),
                    R.id.flContainer)
            }
            "ZhuangRong"->{
                FragmentUtils(this,
                    ZhuangRongDetailFragment.newInstance(extraInt("id", -1), true),
                    R.id.flContainer)
            }
        }

    }
}