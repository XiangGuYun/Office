package com.office.yewu.product_detail

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.util.Log
import android.widget.ImageView
import com.kotlinlib.common.LLLP
import com.kotlinlib.common.bitmap.BmpUtils
import com.office.constant.MsgWhat
import com.office.net.Req
import com.office.view.Indicator
import com.office.yewu.OfficeVideoActivity
import com.yp.baselib.BaseFragment
import com.yp.baselib.FragPagerUtils
import com.yp.baselib.LayoutId
import com.yp.baselib.utils.BusUtils
import com.yp.baselib.utils.fragment.old.FragmentUtils
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R
import kotlinx.android.synthetic.main.fragment_cz_pd.*
import kotlinx.android.synthetic.main.fragment_cz_pd.flContainerPD
import kotlinx.android.synthetic.main.fragment_cz_pd.llXGSP
import kotlinx.android.synthetic.main.fragment_cz_pd.rvVideo
import kotlinx.android.synthetic.main.fragment_product_gallery.*
import kotlinx.android.synthetic.main.fragment_zr_detail.*

/**
 * 产品详情
 */
@LayoutId(R.layout.fragment_cz_pd)
class CzProductDetailFragment : BaseFragment(), RVInterface, BmpUtils {
    private var fu: FragPagerUtils<ProductGalleryFragment>? = null

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

        flContainerPD.post {
            flContainerPD.doLP<LLLP> {
                it.width = getAct().srnWidth - 140.dp - 20.dp
                it.height = getAct().srnWidth - 140.dp - 20.dp
            }
        }

        Req.getShangPinXiangQing(arguments!!.getInt("id")) {
            // 轮播图
//            val bannerImgList = it.data.bannerImg.split(",")
            if(it.data == null){
                return@getShangPinXiangQing
            }

            // 获取关联妆容
            if(it.data.linkPurposeList != null && it.data.linkPurposeList.isNotEmpty()){
                tvRelationZR.show()
                rvRelationZR.show()
                val list = it.data.linkPurposeList
                rvRelationZR.wrap.managerHorizontal().rvAdapter(
                    list,
                    { holder, pos ->
                        // 关联妆容
                        showBitmap(
                            getAct(),
                            holder.iv(R.id.ivRelProducts),
                            list[pos].img
                        )
                        holder.itemClick { v ->
                            BusUtils.post(MsgWhat.SWITCH_TO_DETAIL_PAGE, list[pos].purposeId, arg2 = 6)
//                                goTo<ProductDetailActivity>("id" to it.data[pos].linkId,
//                                    "type" to "Product")
                        }
                    }, R.layout.item_glcp
                )
            }

            if ( it.data.mallSkuDetailList.isNotEmpty()) {
                val list = it.data.mallSkuDetailList
                flContainerPD.setViewAdapter(list.size){ p->
                    getAct().inflate(R.layout.view_gallery).apply {
                        this.view<ViewPager>(R.id.vpGallery).doLP<LLLP> {
                            val size = if(!arguments!!.getBoolean("isAttachDetailActivity"))
                                getAct().srnWidth - 140.dp - 20.dp
                            else
                                getAct().srnWidth - 40.dp
                            it.width = size
                            it.height = size
                        }
                        val imgs = list[p].productImg.split(",")
                        this.view<ViewPager>(R.id.vpGallery).setViewAdapter(imgs.size) { position: Int ->
                            val view = getAct().inflate(R.layout.iv_gallery) as ImageView
                            showBitmap(getAct(), view, imgs[position])
                            view
                        }

                        if(imgs.size > 1){
                            this.view<Indicator>(R.id.indicator).setDotNumber(imgs.size).bindViewPager(this.view<ViewPager>(R.id.vpGallery))
                        }
                    }
                }
            } else {
                val imgCover = it.data.imgCover
                flContainerPD.setViewAdapter(1){ p->
                    getAct().inflate(R.layout.view_gallery).apply {
                        this.view<ViewPager>(R.id.vpGallery).doLP<LLLP> {
                            val size = if(!arguments!!.getBoolean("isAttachDetailActivity"))
                                getAct().srnWidth - 140.dp - 20.dp
                            else
                                getAct().srnWidth - 40.dp
                            it.width = size
                            it.height = size
                        }
                        val imgs = listOf(imgCover)
                        this.view<ViewPager>(R.id.vpGallery).setViewAdapter(imgs.size) { position: Int ->
                            val view = getAct().inflate(R.layout.iv_gallery) as ImageView
                            showBitmap(getAct(), view, imgs[position])
                            view
                        }

                        if(imgs.size > 1){
                            this.view<Indicator>(R.id.indicator).setDotNumber(imgs.size).bindViewPager(this.view<ViewPager>(R.id.vpGallery))
                        }
                    }
                }
            }

            var selectedIndex = 0

            // 当前选择的颜色值
            if (it.data != null && it.data.mallSkuDetailList.isNotEmpty())
                tvColor.text = it.data.mallSkuDetailList[0].productModel

            // 产品参数
            tvProductParams.text = it.data?.productParam

            // 产品特点
            tvProductCharacter.text = it.data?.productCharacter

            // 相关视频
            val videoList = it.data?.videoDTOList
            videoList?.let {
                rvVideo.wrap.gridManager(2).rvAdapter(
                    videoList,
                    { holder, pos ->
                        showBitmap(
                            getAct(),
                            holder.iv(R.id.ivPreviewFrame),
                            videoList[pos].videoUrl + "?x-oss-process=video/snapshot,t_1000,f_png,w_0,h_0,m_fast"
                        )
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

            }

            // 颜色图片列表
            if(it.data != null){
                when (it.data.skuType) {
                    SINGLE_COLOR -> {
                        val list = it.data.mallSkuDetailList
                        if (list.isNotEmpty())
                            tvColor.text = list[0].productModel
                        rvColor.wrap.gridManager(12).rvAdapter(
                            list,
                            { holder, pos ->
                                showBitmap(
                                    getAct(),
                                    holder.iv(R.id.ivColor),
                                    list[pos].skuIcon
                                )
                                holder.v(R.id.mask).showOrGone(selectedIndex != pos)
                                holder.itemClick {
                                    selectedIndex = pos
                                    rvColor.update()
                                    tvColor.text = list[pos].productModel
                                    flContainerPD.setCurrentItem(pos)
                                }
                            }, R.layout.item_color
                        )
                    }
                    MULTI_COLOR -> {
                        if (it.data.mallSkuDetailList.isNotEmpty()) {
                            tvColor.text = it.data.mallSkuDetailList[0].productModel
                        }
                        rvColor.wrap.gridManager(8).rvAdapter(
                            it.data.mallSkuDetailList,
                            { holder, pos ->
                                showBitmap(
                                    getAct(),
                                    holder.iv(R.id.ivColor),
                                    it.data.mallSkuDetailList[pos].skuIcon
                                )
                                holder.v(R.id.mask).showOrGone(selectedIndex != pos)
                                holder.itemClick { v ->
                                    selectedIndex = pos
                                    tvColor.text = it.data.mallSkuDetailList[pos].productModel
                                    flContainerPD.setCurrentItem(pos)
                                    rvColor.update()
                                }
                            }, R.layout.item_color_multi
                        )
                    }
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