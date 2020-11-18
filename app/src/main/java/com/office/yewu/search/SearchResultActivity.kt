package com.office.yewu.search

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.widget.TextView
import com.kotlinlib.common.FLLP
import com.kotlinlib.common.LLLP
import com.kotlinlib.common.bitmap.BmpUtils
import com.office.net.Req
import com.office.yewu.OfficeBaseActivity
import com.office.yewu.product_detail.ProductDetailActivity
import com.yp.baselib.FragPagerUtils
import com.yp.baselib.LayoutId
import com.yp.baselib.StatusBarColor
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

            val productList = it.data
            if(productList == null) return@getShangPinFenYeLieBiao
            Req.searchZhuangRong(name = extraStr("search")) {
                val zrList = it.data

                vpSearchResult.setViewAdapter(2) {
                    val view = inflate(R.layout.rv_search_result)
                    when (it) {
                        0 -> {
                            view.rv(R.id.rvSearchResult).wrap.gridManager(3).rvAdapter(productList,
                                { holder, pos ->

                                    showBitmap(
                                        this,
                                        holder.iv(R.id.ivProduct),
                                        productList[pos].imgCover
                                    )
                                    holder.apply {
                                        iv(R.id.ivProduct).doLP<LLLP> {
                                            val size = (srnWidth - 40.dp) / 3
                                            it.width = size
                                            it.height = size
                                        }
                                        tv(R.id.tvDesc).text = productList[pos].name
                                        itemClick {
                                            goTo<ProductDetailActivity>(
                                                "id" to productList[pos].id,
                                                "type" to "Product"
                                            )
                                        }
                                    }

                                }, R.layout.item_grid_product
                            )
                        }
                        1 -> {
                            view.rv(R.id.rvSearchResult).wrap.gridManager(3).rvAdapter(zrList,
                                { holder, pos ->
//                                    showBitmap(this, holder.iv(R.id.ivZR), zrList[pos].img)
                                    holder.apply {
                                        v(R.id.flImg).post {
                                            holder.v(R.id.flImg).doLP<LLLP> {
                                                it.width = (srnWidth - 40.dp) / 3
                                                it.height = (srnWidth - 40.dp) / 3
                                            }
                                            showBitmap(this@SearchResultActivity,
                                                holder.iv(R.id.ivZR), zrList[pos].img)
                                        }
                                        tv(R.id.tvName).text = zrList[pos].name
                                        tv(R.id.tvEnName).text = zrList[pos].enName
                                        itemClick {
                                            goTo<ProductDetailActivity>(
                                                "id" to zrList[pos].id,
                                                "type" to "ZhuangRong"
                                            )
                                        }
                                    }

                                }, R.layout.item_zr
                            )
                        }
                    }

                    view
                }

                tlSearchResult.setTabTextColors(Color.WHITE, Color.WHITE)
                tlSearchResult.setSelectedTabIndicatorColor(Color.WHITE)

                vpSearchResult.bindTabLayout(tlSearchResult, 2, false,
                    FragPagerUtils.TabListener { tab, index ->
                        when (index) {
                            0 -> {
                                tab.text = "商品(${productList.size})"
                            }
                            1 -> {
                                tab.text = "妆容(${zrList.size})"
                            }
                        }
                    })

            }

        }
    }
}