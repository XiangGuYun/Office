package com.office.yewu

import android.os.Bundle
import com.office.bean.ZhuangRongFenLeiShu
import com.office.net.Req
import com.office.yewu.products.ProductsActivity
import com.yp.baselib.BaseActivity
import com.yp.baselib.LayoutId
import com.yp.baselib.StatusBarColor
import com.yp.oom.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 主页
 */
@StatusBarColor("#000000")
@LayoutId(R.layout.activity_main)
class MainActivity : BaseActivity() {

    override fun init(bundle: Bundle?) {
        // 场景妆容区域的六个按钮，其显示与否根据后端动态返回决定
        val btnZrList = listOf(btnCJZR1, btnCJZR2, btnCJZR3, btnCJZR4, btnCJZR5, btnCJZR6)

        // 场景妆容的数据集合
        val listZR = ArrayList<ZhuangRongFenLeiShu.Data.Child>()

        Req.getZhuangRongFenLeiShu(0){ it ->
            it.data.forEach {
                // 收集场景妆容下的数据类
                if(it.name == "场景妆容"){
                    listZR.addAll(it.childList)
                }
            }
            listZR.forEachIndexed { index, child ->
                // 根据场景妆容数据类；来决定这个区域按钮显示和名称
                btnZrList[index].show().text = child.name
            }
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
        btnXieZhuang.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 9)
        }
        btnHuFuXiLie.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 10)
        }
        btnMianBuQingJie.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 11)
        }
        btnMianBuHuLi.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 12)
        }
        btnYanBuHuLi.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 13)
        }
        btnChunBuHuLi.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 14)
        }
        btnGongJuXiLie.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 15)
        }
        btnShuaJu.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 16)
        }
        btnYiQi.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 17)
        }
        btnFuZhu.click {
            goTo<ProductsActivity>("type" to "Products", "index" to 18)
        }
    }

}