package com.office.yewu.products

import android.os.Bundle
import android.os.Message
import com.office.constant.Id
import com.office.constant.MsgWhat
import com.yp.baselib.utils.fragment.old.FragmentUtils
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R
import com.office.yewu.product_detail.CzProductDetailFragment
import com.office.yewu.product_detail.ZhuangRongDetailFragment
import com.yp.baselib.*
import kotlinx.android.synthetic.main.activity_products.*
import org.greenrobot.eventbus.Subscribe

@Bus
@StatusBarColor("#000000")
@LayoutId(R.layout.activity_products)
class ProductsActivity : BaseActivity(), RVInterface {

    private lateinit var fu: FragmentUtils<BaseFragment>

    var listShowTag = listOf<Pair<String, Int>>()

    @Subscribe
    fun handle(msg: Message){
        when(msg.what){
            MsgWhat.SWITCH_TO_DETAIL_PAGE -> {
                fu.switch(CzProductDetailFragment.newInstance(msg.obj.toString().toInt()))
            }
        }
    }

    override fun init(bundle: Bundle?) {

//        val list = listOf(
//            "新品上市 # NEW IN", "彩妆系列 # BEAUTY ←",
//            " · 妆前", " · 底妆", " · 定妆", " · 颊妆", " · 眉妆", " · 眼妆", " · 唇妆", " · 卸妆",
//            "护肤系列 # COSMETIC", " · 面部清洁", " · 面部护理", " · 眼部护理", " · 唇部护理",
//            "工具系列 # TOOLS", " · 刷具", " · 仪器", " · 辅助", "- MAKEUP", "局部妆容"," · 底妆",
//            " · 颊妆"," · 眉妆"," · 眼妆"," · 唇妆","场景妆容 # SCENE"," · 生活彩妆"," · 职场彩妆",
//            " · 约会彩妆"," · 时尚彩妆","· 趋势彩妆", "- ABOUT ←"
//        )

        val type = extraStr("type")

        when (type) {
            "Products" -> {
                listShowTag = listOf(
                    "新品上市 # NEW IN" to -1, "彩妆系列 # BEAUTY ←" to Id.CAI_ZHUANG_XI_LIE,
                    " · 妆前" to Id.ZHUANG_QIAN, " · 底妆" to Id.DI_ZHUANG, " · 定妆" to Id.DING_ZHUANG,
                    " · 颊妆" to Id.JIA_ZHUANG, " · 眉妆" to Id.MEI_ZHUANG, " · 眼妆" to Id.YAN_ZHUANG,
                    " · 唇妆" to Id.CHUN_ZHUANG, " · 卸妆" to Id.XIE_ZHUANG,
                    "护肤系列 # COSMETIC" to Id.HU_FU_XI_LIE, " · 面部清洁" to Id.MIAN_BU_QING_JIE,
                    " · 面部护理" to Id.MIAN_BU_HU_LI, " · 眼部护理" to Id.YAN_BU_HU_LI,
                    " · 唇部护理" to Id.CHUN_BU_HU_LI, "工具系列 # TOOLS" to Id.GONG_JU_XI_LIE,
                    " · 刷具" to Id.SHUA_JU, " · 仪器" to Id.YI_QI, " · 辅助" to Id.FU_ZHU)
            }
        }

        fu = FragmentUtils<BaseFragment>(this, ArrayList(listShowTag.mapIndexed { index, s ->
            when (index) {
//                0 -> {
//                    NewProductFragment()
//                }
//                1 -> {
//                    ZhuangRongDetailFragment()
//                }
//                list.size - 3 -> {
//                    CzProductDetailFragment()
//                }
//                list.size - 2 -> {
//                    ZhuangRongFragment()
//                }
//                list.size - 1 -> {
//                    AboutFragment()
//                }
                else -> {
                    ProductsFragment.newInstance(s.second) as BaseFragment
                }
            }
        }), R.id.flContainer)

        var selectedIndex = extraInt("index", 0)

        rvLeft.wrap.rvMultiAdapter(
            listShowTag,
            { h, p ->
                h.tv(R.id.tv).text = listShowTag[p].first
                h.itemClick {
                    selectedIndex = p
                    rvLeft.update()
                    fu.switch(p)
                }
            },
            {
                if (it == selectedIndex) 1 else 0
            }, R.layout.item_unselect, R.layout.item_select
        )

        if(selectedIndex != 0) fu.switch(selectedIndex)
    }

}