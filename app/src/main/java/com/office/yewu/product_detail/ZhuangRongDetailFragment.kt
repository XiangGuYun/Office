package com.office.yewu.product_detail

import android.os.Bundle
import android.text.TextUtils
import com.kotlinlib.common.LLLP
import com.kotlinlib.common.bitmap.BmpUtils
import com.office.net.Req
import com.office.view.SwitchView
import com.office.yewu.OfficeVideoActivity
import com.yp.baselib.BaseFragment
import com.yp.baselib.LayoutId
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R
import kotlinx.android.synthetic.main.fragment_zr_detail.*

/**
 * 妆容详情
 */
@LayoutId(R.layout.fragment_zr_detail)
class ZhuangRongDetailFragment : BaseFragment(), RVInterface, BmpUtils {

    companion object {
        fun newInstance(id: Int, isAttachDetailActivity:Boolean = false): ZhuangRongDetailFragment {
            return ZhuangRongDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt("id", id)
                    putBoolean("isAttachDetailActivity", isAttachDetailActivity)
                }
            }
        }
    }

    override fun init() {

        val id = arguments!!.getInt("id")

        Req.getZhuangRongFenLeiXiangQing(id){

            showBitmap(getAct(), ivZrDetail, it.data.bigImg)

            tvZrDesc.text = it.data.purposeDesc

//            Req.getShangPinFenYeLieBiao(purposeId = it.data.id.toString()){
//                if(it.data.isNotEmpty()){
//                    tvRelationProducts.show()
//                    rvRelationProducts.show()
//                    rvRelationProducts.wrap.managerHorizontal().rvAdapter(it.data,
//                        {
//                            holder, pos ->
//                            // 关联产品
//                            showBitmap(getAct(), holder.iv(R.id.ivRelProducts), it.data[pos].imgCover)
//                            holder.itemClick {v->
//                                goTo<ProductDetailActivity>("id" to it.data[pos].id,
//                                    "type" to "Product")
//                            }
//                        }, R.layout.item_glcp)
//                }
//            }

            Req.getLinkProducts(it.data.id){
                if(it.data.isNotEmpty()){
                    tvRelationProducts.show()
                    rvRelationProducts.show()
                    rvRelationProducts.wrap.managerHorizontal().rvAdapter(it.data,
                        {
                                holder, pos ->
                            // 关联产品
                            showBitmap(getAct(), holder.iv(R.id.ivRelProducts), it.data[pos].imgCover)
                            holder.itemClick {v->
                                goTo<ProductDetailActivity>("id" to it.data[pos].linkId,
                                    "type" to "Product")
                            }
                        }, R.layout.item_glcp)
                }
            }

            // 步骤列表
            val stepList = it.data.purposeStepList

            var openIndex = -1

            rvSteps.wrap.rvAdapter(stepList,
                {
                    holder, pos ->
                    val switchView = holder.v(R.id.switchView) as SwitchView
                    switchView.setTitle(stepList[pos].stepName)
                    switchView.setContent(stepList[pos].stepContent)
                    switchView.v(R.id.flTop).click {
                        if(openIndex == pos){
                            openIndex = -1
                        } else {
                            openIndex = pos
                        }
                        rvSteps.update()
                    }
                    if(openIndex == pos){
                        switchView.open()
                    } else {
                        switchView.close()
                    }
                },R.layout.item_step)

            if(!TextUtils.isEmpty(it.data.linkVideo)){
                val videoList = it.data.videoDTOList
                titleBar2.show().setTitle("相关视频 # VIDEO")
                rvVideo.wrap.gridManager(2).rvAdapter(
                    videoList,
                    { holder, pos ->
                        showBitmap(getAct(), holder.iv(R.id.ivPreviewFrame), videoList[pos].videoUrl+"?x-oss-process=video/snapshot,t_1000,f_png,w_0,h_0,m_fast")
                        holder.tv(R.id.tvTop).text = videoList[pos].videoName
                        holder.tv(R.id.tvBtm).text = videoList[pos].videoCopywriting
                        holder.itemClick {
                            goTo<OfficeVideoActivity>("isNetVideo" to true, "path" to videoList[pos].videoUrl)
                        }
                    }, R.layout.item_xgsp
                )
            }



        }

        ivZrDetail.doLP<LLLP> {
            if(!arguments!!.getBoolean("isAttachDetailActivity")){
                it.width = getAct().srnWidth - 100.dp - 20.dp
            } else {
                it.width = getAct().srnWidth - 60.dp
            }
            it.height = it.width
        }

        titleBar2.click {
            if (!titleBar2.isOpened) {
                titleBar2.open()
                llXGSP.show()
            } else {
                titleBar2.close()
                llXGSP.gone()
            }
        }
    }
}