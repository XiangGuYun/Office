package com.office.yewu.products

import android.os.Bundle
import com.yp.baselib.BaseActivity
import com.yp.baselib.BaseFragment
import com.yp.baselib.LayoutId
import com.yp.baselib.StatusBarColor
import com.yp.baselib.utils.fragment.old.FragmentUtils
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R
import com.office.yewu.product_detail.CzProductDetailFragment
import com.office.yewu.product_detail.ZhuangRongDetailFragment
import kotlinx.android.synthetic.main.activity_products.*

@StatusBarColor("#000000")
@LayoutId(R.layout.activity_products)
class ProductsActivity : BaseActivity(), RVInterface {

    override fun init(bundle: Bundle?) {

        val list = listOf(
            "新品上市 # NEW IN", "彩妆系列 # BEAUTY ←",
            " · 妆前", " · 底妆", " · 定妆", " · 颊妆", " · 眉妆", " · 眼妆", " · 唇妆", " · 卸妆",
            "护肤系列 # COSMETIC", " · 面部清洁", " · 面部护理", " · 眼部护理", " · 唇部护理",
            "工具系列 # TOOLS", " · 刷具", " · 仪器", " · 辅助", "- MAKEUP", "局部妆容"," · 底妆",
            " · 颊妆"," · 眉妆"," · 眼妆"," · 唇妆","场景妆容 # SCENE"," · 生活彩妆"," · 职场彩妆",
            " · 约会彩妆"," · 时尚彩妆","· 趋势彩妆", "- ABOUT ←"
        )

        val fu = FragmentUtils<BaseFragment>(this, ArrayList(list.mapIndexed { index, s ->
            when (index) {
                0 -> {
                    NewProductFragment()
                }
                1 -> {
                    ZhuangRongDetailFragment()
                }
                list.size-3 -> {
                    CzProductDetailFragment()
                }
                list.size-2 ->{
                    ZhuangRongFragment()
                }
                list.size-1 -> {
                    AboutFragment()
                }
                else -> {
                    ProductsFragment()
                }
            }
        }), R.id.flContainer)

        var selectedIndex = 1

        rvLeft.wrap.rvMultiAdapter(list,
            {
                h,p->
                h.tv(R.id.tv).text = list[p]
                h.itemClick {
                    selectedIndex = p
                    rvLeft.update()
                    fu.switch(p)
                }
            },
            {
                if(it == selectedIndex) 1 else 0
            }, R.layout.item_unselect, R.layout.item_select)


    }

}