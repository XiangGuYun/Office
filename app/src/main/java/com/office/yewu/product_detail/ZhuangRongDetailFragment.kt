package com.office.yewu.product_detail

import android.os.Bundle
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
        fun newInstance(id: Int): ZhuangRongDetailFragment {
            return ZhuangRongDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt("id", id)
                }
            }
        }
    }

    override fun init() {

        val id = arguments!!.getInt("id")

        Req.getZhuangRongFenLeiXiangQing(id){
            showBitmap(getAct(), ivZrDetail, it.data.bigImg)

            tvZrDesc.text = it.data.purposeDesc

            Req.getShangPinFenYeLieBiao(purposeId = it.data.id.toString()){
                if(it.data.isNotEmpty()){
                    tvRelationProducts.show()
                    rvRelationProducts.show()
                    rvRelationProducts.wrap.managerHorizontal().rvAdapter(it.data,
                        {
                            holder, pos ->
                            // 关联产品
                            showBitmap(getAct(), holder.iv(R.id.ivRelProducts), it.data[pos].imgCover)
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

            if(it.data.linkVideo.isNotEmpty()){
                val videoUrlList = it.data.linkVideo.split(",")
                titleBar2.show().setTitle("相关视频 # VIDEO")
                rvVideo.wrap.gridManager(2).rvAdapter(
                    videoUrlList,
                    { holder, pos ->
                        holder.itemClick {
//                            OfficeVideoActivity.go(getAct(), true, videoUrlList[pos])
                            goTo<OfficeVideoActivity>("isNetVideo" to true, "path" to videoUrlList[pos])
                        }
//                        loadVideoScreenshot(getAct(), videoUrlList[pos], holder.iv(R.id.ivPreviewFrame), 0)
                    }, R.layout.item_xgsp
                )
            }



        }

        ivZrDetail.doLP<LLLP> {
            it.width = getAct().srnWidth - 100.dp - 20.dp
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