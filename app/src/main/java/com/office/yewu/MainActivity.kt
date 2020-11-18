package com.office.yewu

import android.os.Bundle
import android.util.Log
import com.office.bean.ZhuangRongFenLeiShu
import com.office.net.Req
import com.office.yewu.products.ProductsActivity
import com.tencent.bugly.crashreport.CrashReport
import com.yp.baselib.LayoutId
import com.yp.baselib.StatusBarColor
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R
import kotlinx.android.synthetic.main.activity_main.*


/**
 * 主页
 */
@StatusBarColor("#000000")
@LayoutId(R.layout.activity_main)
class MainActivity : OfficeBaseActivity(), RVInterface {

    companion object {
        /**
         * 场景妆容名称列表
         */
        var zrNameList = ArrayList<String>()
    }

    // 场景妆容的数据集合
    val listZR = ArrayList<ZhuangRongFenLeiShu.Data.Child>()

    override fun init(bundle: Bundle?) {

//        if("2020-12-01".reverseFmtDate("yyyy-MM-dd") < System.currentTimeMillis()){
//            finish()
//        }

        // 场景妆容区域的六个按钮，其显示与否根据后端动态返回决定
//        val btnZrList = listOf(btnCJZR1, btnCJZR2, btnCJZR3, btnCJZR4, btnCJZR5, btnCJZR6)

        val DM = resources.displayMetrics
        Log.e("手机dpi====", DM.widthPixels.toString()+" "+DM.heightPixels+" "+DM.densityDpi.toString() + "")

        Req.getZhuangRongFenLeiShu("0") { it ->
            it.data.forEach {
                // 收集场景妆容下的数据类
                if (it.name == "场景妆容") {
                    listZR.addAll(it.childList)
                }
            }
            listZR.reverse()
            listZR.forEachIndexed { index, child ->
                // 根据场景妆容数据类；来决定这个区域按钮显示和名称
                zrNameList.add(child.name)
//                btnZrList[index].show().text = child.name
            }

            val zrBgList = listOf(
                R.drawable.bg_rect_shcz,
                R.drawable.bg_rect_ffffff,
                R.drawable.bg_rect_yhcz,
                R.drawable.bg_rect_sscz,
                R.drawable.bg_rect_qscz,
                R.drawable.bg_rect_qscz
            )

            rvChangJingZhuangRong.wrap.managerHorizontal().rvMultiAdapter(listZR,
                { h, p ->
                    h.tv(R.id.tv).txt(listZR[p].name).bg(zrBgList[p])
                    h.itemClick {
                        goTo<ProductsActivity>(
                            "type" to "MakeUp", "index" to p + 9, "listZR" to listZR
                        )
                    }
                }, {
                    0
                }, R.layout.item_cjzr1, R.layout.item_cjzr2
            )

        }

        initClickEvent()

    }

    /**
     * 处理所有可点击单位的点击事件
     */
    private fun initClickEvent() {
        btnProducts.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 0)
        }
        btnCaiZhuangXiLie.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 1)
        }
        btnZhuangQian.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 2)
        }
        btnDiZhuang.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 3)
        }
        btnDingZhuang.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 4)
        }
        btnJiaZhuang.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 5)
        }
        btnMeiZhuang.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 6)
        }
        btnYanZhuang.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 7)
        }
        btnChunZhuang.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 8)
        }
//        btnXieZhuang.click {
//            goTo<ProductsActivity>("type" to "Products", "index" to 8)
//        }
        btnHuFuXiLie.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 9)
        }
        btnMianBuQingJie.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 10)
        }
        btnMianBuHuLi.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 11)
        }
        btnYanBuHuLi.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 12)
        }
        btnChunBuHuLi.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 13)
        }
        btnGongJuXiLie.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 14)
        }
        btnShuaJu.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 15)
        }
        btnYiQi.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 16)
        }
        btnFuZhu.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 17)
        }

        /*
        局部妆容
         */
        btnMakeUp.click {
            goTo<ProductsActivity>("type" to "MakeUp", "index" to 1, "listZR" to listZR)
        }

        btnJuBuZhuangRong.click {
            goTo<ProductsActivity>("type" to "MakeUp", "index" to 2, "listZR" to listZR)
        }

        btnDiZhuangJB.click {
            goTo<ProductsActivity>("type" to "MakeUp", "index" to 3, "listZR" to listZR)
        }

        btnJiaZhuangJB.click {
            goTo<ProductsActivity>("type" to "MakeUp", "index" to 4, "listZR" to listZR)
        }

        btnMeiZhuangJB.click {
            goTo<ProductsActivity>("type" to "MakeUp", "index" to 5, "listZR" to listZR)
        }

        btnYanZhuangJB.click {
            goTo<ProductsActivity>("type" to "MakeUp", "index" to 6, "listZR" to listZR)
        }

        btnChunZhuangJB.click {
            goTo<ProductsActivity>("type" to "MakeUp", "index" to 7, "listZR" to listZR)
        }

        btnCJZR.click {
            goTo<ProductsActivity>("type" to "MakeUp", "index" to 8, "listZR" to listZR)
        }

//        btnCJZR1.click {
//            goTo<ProductsActivity>("type" to "MakeUp", "index" to 9)
//        }
//
//        btnCJZR2.click {
//            goTo<ProductsActivity>("type" to "MakeUp", "index" to 10)
//        }
//
//        btnCJZR3.click {
//            goTo<ProductsActivity>("type" to "MakeUp", "index" to 11)
//        }
//
//        btnCJZR4.click {
//            goTo<ProductsActivity>("type" to "MakeUp", "index" to 12)
//        }
//
//        btnCJZR5.click {
//            goTo<ProductsActivity>("type" to "MakeUp", "index" to 13)
//        }
//
//        btnCJZR6.click {
//            goTo<ProductsActivity>("type" to "MakeUp", "index" to 14)
//        }

        /*
        about
         */
        flAbout.click {
            goTo<ProductsActivity>("type" to "About", "index" to 2)
        }
    }

}