package com.office.yewu.product_detail

import android.os.Bundle
import com.yp.baselib.BaseFragment
import com.yp.baselib.LayoutId
import com.yp.baselib.utils.fragment.old.FragmentUtils
import com.yp.oom.R

@LayoutId(R.layout.activity_product_detail)
class ProductDetailFragment : BaseFragment() {
    override fun init() {
        if (arguments!!.getBoolean("isProduct")) {
            FragmentUtils(
                getAct(),
                CzProductDetailFragment.newInstance(arguments!!.getInt("id"), true),
                R.id.flContainer
            )
        } else {
            FragmentUtils(
                getAct(),
                ZhuangRongDetailFragment.newInstance(arguments!!.getInt("id"), true),
                R.id.flContainer
            )
        }
    }

    fun doPop(){

    }

    companion object {
        fun newInstance(isProduct: Boolean, id: Int): ProductDetailFragment {
            return ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putBoolean("isProduct", isProduct)
                    putInt("id", id)
                }
            }
        }
    }
}