package com.office.yewu.product_detail

import android.os.Bundle
import com.kotlinlib.common.bitmap.BmpUtils
import com.office.net.Req
import com.office.yewu.OfficeVideoActivity
import com.yp.baselib.BaseFragment
import com.yp.baselib.LayoutId
import com.yp.baselib.utils.fragment.old.FragmentUtils
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R
import kotlinx.android.synthetic.main.fragment_cz_pd.*

/**
 * 产品详情
 */
@LayoutId(R.layout.fragment_cz_pd)
class CzProductDetailFragment : BaseFragment(), RVInterface, BmpUtils {
    private var fu: FragmentUtils<ProductGalleryFragment>? = null

    private val SINGLE_COLOR = 1

    private val MULTI_COLOR = 2

    companion object {
        fun newInstance(id: Int, isAttachDetailActivity: Boolean = false): CzProductDetailFragment {
            return CzProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt("id", id)
                    putBoolean("isAttachDetailActivity", isAttachDetailActivity)
                }
            }
        }
    }

    override fun init() {

        Req.getShangPinXiangQing(arguments!!.getInt("id")) {
            // 轮播图
            val bannerImgList = it.data.bannerImg.split(",")
            if (it.data.mallSkuDetailList.isNotEmpty()) {
                fu = FragmentUtils<ProductGalleryFragment>(
                    getAct(),
                    ArrayList(it.data.mallSkuDetailList.map {
                        ProductGalleryFragment.newInstance(
                            it.productImg,
                            arguments!!.getBoolean("isAttachDetailActivity")
                        )
                    }
                        .toList()),
                    R.id.flContainerPD
                )
            } else {
                flContainerPD.gone()
            }

            var selectedIndex = 0

            // 当前选择的颜色值
            if (it.data.mallSkuDetailList.isNotEmpty())
                tvColor.text = it.data.mallSkuDetailList[0].productModel

            // 产品参数
            tvProductParams.text = it.data.productParam

            // 产品特点
            tvProductCharacter.text = it.data.productCharacter

            // 相关视频
            val videoList = it.data.videoDTOList
            rvVideo.wrap.gridManager(2).rvAdapter(
                videoList,
                { holder, pos ->
                    holder.tv(R.id.tvTop).text = videoList[pos].videoName
                    holder.tv(R.id.tvBtm).text = videoList[pos].videoCopywriting
                    holder.itemClick {
                        goTo<OfficeVideoActivity>(
                            "isNetVideo" to true,
                            "path" to videoList[pos].videoUrl
                        )
                    }
                }, R.layout.item_xgsp
            )

            // 颜色图片列表
            when (it.data.skuType) {
                SINGLE_COLOR -> {
                    val list = it.data.mallSkuDetailList
                    rvColor.wrap.gridManager(12).rvAdapter(
                        list,
                        { holder, pos ->
                            tvColor.text = list[pos].productModel
                            showBitmap(
                                getAct(),
                                holder.iv(R.id.ivColor),
                                list[pos].skuIcon
                            )
                            holder.v(R.id.mask).showOrGone(selectedIndex != pos)
                            holder.itemClick {
                                selectedIndex = pos
                                tvColor.text = list[pos].productModel
                                fu?.switch(pos)
                                rvColor.update()
                            }
                        }, R.layout.item_color
                    )
                }
                MULTI_COLOR -> {
                    rvColor.wrap.gridManager(8).rvAdapter(
                        it.data.mallSkuDetailList,
                        { holder, pos ->
                            tvColor.text = it.data.mallSkuDetailList[pos].productModel
                            showBitmap(
                                getAct(),
                                holder.iv(R.id.ivColor),
                                it.data.mallSkuDetailList[pos].skuIcon
                            )
                            holder.v(R.id.mask).showOrGone(selectedIndex != pos)
                            holder.itemClick { v ->
                                selectedIndex = pos
                                tvColor.text = it.data.mallSkuDetailList[pos].productModel
                                fu?.switch(pos)
                                rvColor.update()
                            }
                        }, R.layout.item_color_multi
                    )
                }
            }


        }

        flCPCS.click {
            if (!llCPCS.canSee()) {
                showCPCS()
                hideCPTD()
                hideXGSP()
            } else {
                hideCPCS()
            }
        }

        flCPTD.click {
            if (!llCPTD.canSee()) {
                showCPTD()
                hideCPCS()
                hideXGSP()
            } else {
                hideCPTD()
            }
        }

        flXGSP.click {
            if (!llXGSP.canSee()) {
                showXGSP()
                hideCPCS()
                hideCPTD()
            } else {
                hideXGSP()
            }
        }


    }

    private fun hideXGSP() {
        llXGSP.gone()
        line3_1.show()
        line3_2.show()
        tvPlus3.text = "+"
    }

    private fun showXGSP() {
        llXGSP.show()
        line3_1.gone()
        line3_2.gone()
        tvPlus3.text = "-"
    }

    private fun hideCPTD() {
        llCPTD.gone()
        line2_1.show()
        line2_2.show()
        tvPlus2.text = "+"
    }

    private fun showCPTD() {
        llCPTD.show()
        line2_1.gone()
        line2_2.gone()
        tvPlus2.text = "-"
    }

    private fun hideCPCS() {
        llCPCS.gone()
        line1_1.show()
        line1_2.show()
        tvPlus.text = "+"
    }

    private fun showCPCS() {
        llCPCS.show()
        line1_1.gone()
        line1_2.gone()
        tvPlus.text = "-"
    }

}