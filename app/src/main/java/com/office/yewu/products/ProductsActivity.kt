package com.office.yewu.products

import android.graphics.Typeface
import android.os.Bundle
import android.os.Message
import com.office.bean.ZhuangRongFenLeiShu
import com.office.constant.Id
import com.office.constant.MsgWhat
import com.office.yewu.MainActivity
import com.office.yewu.OfficeBaseActivity
import com.office.yewu.StandbyActivity
import com.yp.baselib.utils.fragment.old.FragmentUtils
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R
import com.office.yewu.product_detail.CzProductDetailFragment
import com.office.yewu.product_detail.ZhuangRongDetailFragment
import com.yp.baselib.*
import kotlinx.android.synthetic.main.activity_products.*
import kotlinx.android.synthetic.main.header.*
import org.greenrobot.eventbus.Subscribe

/**
 * 产品页
 */
@Bus
@StatusBarColor("#000000")
@LayoutId(R.layout.activity_products)
class ProductsActivity : OfficeBaseActivity(), RVInterface {

    private lateinit var productsDetailFragment: CzProductDetailFragment
    private lateinit var zrDetailFragment: ZhuangRongDetailFragment
    private lateinit var fu: FragmentUtils<BaseFragment>

    /**
     * 右侧详情Fragment是否处于显示状态
     */
    private var showRightDetailFragment = false

    /**
     * 显示在左侧的标签列表
     * Pair的first代表标签名称
     * Pair的second代表标签ID
     */
    private var listShowTag = arrayListOf<Pair<String, Int>>()

    @Subscribe
    fun handle(msg: Message) {
        when (msg.what) {
            MsgWhat.SWITCH_TO_DETAIL_PAGE -> {
                // 根据传过来的产品ID来切换到对应的产品详情页
                showRightDetailFragment = true
                if (type == "Products") {
                    productsDetailFragment =
                        CzProductDetailFragment.newInstance(msg.obj.toString().toInt())
                    fu.switchFragmentWithStack(productsDetailFragment)
                } else {
                    zrDetailFragment =
                        ZhuangRongDetailFragment.newInstance(msg.obj.toString().toInt())
                    fu.switchFragmentWithStack(zrDetailFragment)
                }
            }
        }
    }

    override fun onBackPressedSupport() {
        if (showRightDetailFragment) {
            showRightDetailFragment = false
            if (type == "Products") {
                productsDetailFragment.pop()
            } else {
                zrDetailFragment.pop()
            }
        } else {
            super.onBackPressedSupport()
        }
    }

    lateinit var type: String

    override fun init(bundle: Bundle?) {

        ivOffice.click {
            goTo<StandbyActivity>()
        }

        // 获取到显示类型，需要根据显示类型来决定左侧区域显示哪些标签
        type = extraStr("type")
        when (type) {
            "Products" -> {
                listShowTag = arrayListOf(
                    "新品上市 # NEW IN" to Id.XIN_PIN_SHANG_SHI,
                    "彩妆系列 # BEAUTY ←" to Id.CAI_ZHUANG_XI_LIE,
                    " · 妆前" to Id.ZHUANG_QIAN,
                    " · 底妆" to Id.DI_ZHUANG,
                    " · 定妆" to Id.DING_ZHUANG,
                    " · 颊妆" to Id.JIA_ZHUANG,
                    " · 眉妆" to Id.MEI_ZHUANG,
                    " · 眼妆" to Id.YAN_ZHUANG,
                    " · 唇妆" to Id.CHUN_ZHUANG,
                    " · 卸妆" to Id.XIE_ZHUANG,
                    "护肤系列 # COSMETIC" to Id.HU_FU_XI_LIE,
                    " · 面部清洁" to Id.MIAN_BU_QING_JIE,
                    " · 面部护理" to Id.MIAN_BU_HU_LI,
                    " · 眼部护理" to Id.YAN_BU_HU_LI,
                    " · 唇部护理" to Id.CHUN_BU_HU_LI,
                    "工具系列 # TOOLS" to Id.GONG_JU_XI_LIE,
                    " · 刷具" to Id.SHUA_JU,
                    " · 仪器" to Id.YI_QI,
                    " · 辅助" to Id.FU_ZHU
                )
            }
            "MakeUp" -> {
                tvTop.gone()
                view23.show()
                listShowTag = arrayListOf(
                    "新品上市 # NEW IN" to Id.XIN_PIN_SHANG_SHI,
                    "- MAKEUP" to Id.MAKE_UP,
                    "局部妆容 # FEATURE" to Id.JU_BU_ZHUANG_RONG,
                    " · 底妆" to Id.DI_ZHUANG_JB,
                    " · 颊妆" to Id.JIA_ZHUANG_JB,
                    " · 眉妆" to Id.MEI_ZHUANG_JB,
                    " · 眼妆" to Id.YAN_ZHUANG_JB,
                    " · 唇妆" to Id.CHUN_ZHUANG_JB,
                    "场景妆容 # SCENE" to Id.CHANG_JING_ZHUANG_RONG
//                    " · 生活彩妆" to if (MainActivity.zrNameList.any { it.contains("生活") }) Id.SHENG_HUO_CAI_ZHUANG else Id.NULL,
//                    " · 职场彩妆" to if (MainActivity.zrNameList.any { it.contains("职场") }) Id.ZHI_CHANG_CAI_ZHUANG else Id.NULL,
//                    " · 约会彩妆" to if (MainActivity.zrNameList.any { it.contains("约会") }) Id.YUE_HUI_CAI_ZHUANG else Id.NULL,
//                    " · 时尚彩妆" to if (MainActivity.zrNameList.any { it.contains("时尚") }) Id.SHI_SHANG_CAI_ZHUANG else Id.NULL,
//                    " · 趋势彩妆" to if (MainActivity.zrNameList.any { it.contains("趋势") }) Id.QU_SHU_CAI_ZHUANG else Id.NULL
                )
                val listZR = extraSerial("listZR") as ArrayList<ZhuangRongFenLeiShu.Data.Child>?
                listZR?.forEach {
                    listShowTag.add(" · ${it.name}" to it.id)
                }
            }
            "About" -> {
                tvTop.gone()
                view23.show()
                listShowTag = arrayListOf(
                    "新品上市 # NEW IN" to Id.XIN_PIN_SHANG_SHI,
                    "- MAKEUP" to Id.MAKE_UP,
                    "- ABOUT" to Id.ABOUT
                )
            }
        }

        listShowTag.removeAll {
            it.second == -2
        }

        // 根据标签列表映射出对应的Fragment列表并进行管理
        fu = FragmentUtils<BaseFragment>(this, ArrayList(listShowTag.mapIndexed { _, s ->
            when {
                s.second == Id.XIN_PIN_SHANG_SHI -> {
                    NewProductFragment.newInstance(false)
                }
                s.second == Id.MAKE_UP -> {
                    NewProductFragment.newInstance(true)
                }
                type == "Products" -> {
                    ProductsFragment.newInstance(s.second) as BaseFragment
                }
                type == "MakeUp" -> {
                    ZhuangRongSubFragment.newInstance(s.second)
                }
                else -> {
                    AboutFragment()
                }
            }
        }), R.id.flContainer)

        // 获取到默认选中的标签项
        var selectedIndex = extraInt("index", 0)

        // 构建左侧列表
        rvLeft.wrap.rvMultiAdapter(
            listShowTag,
            { h, p ->
                if (listShowTag[p].first.contains("ABOUT") ||
                    listShowTag[p].first.contains("MAKEUP") ||
                    listShowTag[p].first.contains("PRODUCTS")
                ) {
                    h.tv(R.id.tv).setFont("font/LucidaGrande.ttf").size(8f).typeface =
                        Typeface.defaultFromStyle(Typeface.BOLD)
                } else {
                    h.tv(R.id.tv).size(7f).typeface =
                        Typeface.defaultFromStyle(Typeface.NORMAL)
                }
                if(selectedIndex == p){
                    h.tv(R.id.tv).text = listShowTag[p].first+" ←"
                } else {
                    h.tv(R.id.tv).text = listShowTag[p].first
                }
                h.itemClick {
                    if(selectedIndex == p) return@itemClick
                    selectedIndex = p
                    rvLeft.update()
                    if (showRightDetailFragment) {
                        showRightDetailFragment = false
                        if (type == "Products") {
                            productsDetailFragment.pop()
                        } else {
                            zrDetailFragment.pop()
                        }
                    }
                    fu.switch(p)
                }
            },
            {
                if (it == selectedIndex) 1 else 0
            }, R.layout.item_unselect, R.layout.item_select
        )

        // 切换到对应的右侧Fragment
        if (selectedIndex != 0) fu.switch(selectedIndex)
    }

}

//        val list = listOf(
//            "新品上市 # NEW IN", "彩妆系列 # BEAUTY ←",
//            " · 妆前", " · 底妆", " · 定妆", " · 颊妆", " · 眉妆", " · 眼妆", " · 唇妆", " · 卸妆",
//            "护肤系列 # COSMETIC", " · 面部清洁", " · 面部护理", " · 眼部护理", " · 唇部护理",
//            "工具系列 # TOOLS", " · 刷具", " · 仪器", " · 辅助", "- MAKEUP", "局部妆容"," · 底妆",
//            " · 颊妆"," · 眉妆"," · 眼妆"," · 唇妆","场景妆容 # SCENE"," · 生活彩妆"," · 职场彩妆",
//            " · 约会彩妆"," · 时尚彩妆","· 趋势彩妆", "- ABOUT ←"
//        )

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