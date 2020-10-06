package com.office.yewu.product_detail

import android.os.Bundle
import com.office.net.Req
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
class CzProductDetailFragment : BaseFragment(), RVInterface {
    private lateinit var fu: FragmentUtils<ProductGalleryFragment>

    private val SINGLE_COLOR = 1

    private val MULTI_COLOR = 2

    companion object{
        fun newInstance(id:Int): CzProductDetailFragment {
            return CzProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt("id", id)
                }
            }
        }
    }

    override fun init() {

        Req.getShangPinXiangQing(arguments!!.getInt("id")){
            // 轮播图
            val bannerImgList = it.data.bannerImg.split(",")
            fu = FragmentUtils<ProductGalleryFragment>(getAct(), ArrayList((1..24).map { ProductGalleryFragment() }.toList()), R.id.flContainerPD)

            var selectedIndex = 0

            // 颜色图片列表
            when (it.data.skuType) {
                SINGLE_COLOR -> {
                    rvColor.wrap.gridManager(12).rvAdapter(
                        (0 until 24).toList(),
                        { holder, pos ->
                            holder.v(R.id.ivColor).setBackgroundColor(randomColor())
                            holder.v(R.id.mask).showOrGone(selectedIndex != pos)
                            holder.itemClick {
                                selectedIndex = pos
                                fu.switch(pos)
                                rvColor.update()
                            }
                        }, R.layout.item_color
                    )
                }
                MULTI_COLOR -> {
                    rvColor.wrap.gridManager(8).rvAdapter(
                        (0 until 8).toList(),
                        { holder, pos ->
                            holder.v(R.id.ivColor).setBackgroundColor(randomColor())
                            holder.v(R.id.mask).showOrGone(selectedIndex != pos)
                            holder.itemClick {
                                selectedIndex = pos
                                fu.switch(pos)
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

        rvVideo.wrap.gridManager(2).rvAdapter(
            listOf(1, 1, 1),
            { holder, pos ->
            }, R.layout.item_xgsp
        )

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