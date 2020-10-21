package com.office.yewu.product_detail

import android.os.Bundle
import android.widget.ImageView
import com.kotlinlib.common.LLLP
import com.kotlinlib.common.bitmap.BmpUtils
import com.yp.baselib.BaseFragment
import com.yp.baselib.LayoutId
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R
import kotlinx.android.synthetic.main.fragment_product_gallery.*


/**
 * 商品详情轮播图
 */
@LayoutId(R.layout.fragment_product_gallery)
class ProductGalleryFragment : BaseFragment(), RVInterface, BmpUtils {

    companion object {
        fun newInstance(imgUrls: String, isAttachDetailActivity:Boolean = false): ProductGalleryFragment {
            return ProductGalleryFragment().apply {
                arguments = Bundle().apply {
                    putString("imgUrls", imgUrls)
                    putBoolean("isAttachDetailActivity", isAttachDetailActivity)
                }
            }
        }
    }

    override fun init() {

        val imgUrls = arguments!!.getString("imgUrls")

        val isAttachDetailActivity = arguments!!.getBoolean("isAttachDetailActivity")

        imgUrls?.let {
            if (imgUrls.isEmpty()) return@let

            val imgUrlList = imgUrls.split(",")

            vpGallery.doLP<LLLP> {
                val size = if(!isAttachDetailActivity)
                    getAct().srnWidth - 100.dp - 20.dp
                else
                    getAct().srnWidth - 40.dp
                it.width = size
                it.height = size
            }

            vpGallery.setViewAdapter(imgUrlList.size) { position: Int ->
                val view = getAct().inflate(R.layout.iv_gallery) as ImageView
                showBitmap(getAct(), view, imgUrlList[position])
                view
            }

            indicator.setDotNumber(imgUrlList.size).bindViewPager(vpGallery)
        }


    }


}