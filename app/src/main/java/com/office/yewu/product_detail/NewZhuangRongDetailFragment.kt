package com.office.yewu.product_detail

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kotlinlib.common.LLLP
import com.kotlinlib.common.bitmap.BmpUtils
import com.kotlinlib.view.textview.TextViewUtils
import com.office.net.Req
import com.office.view.Indicator
import com.office.view.SwitchView
import com.office.yewu.OfficeVideoActivity
import com.office.yewu.products.ProductsActivity
import com.yp.baselib.BaseActivity
import com.yp.baselib.BaseFragment
import com.yp.baselib.LayoutId
import com.yp.baselib.utils.BusUtils
import com.yp.baselib.utils.fragment.old.FragmentUtils
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R
import kotlinx.android.synthetic.main.fragment_zr_detail.*
import kotlinx.android.synthetic.main.fragment_zr_detail_new.*
import kotlinx.android.synthetic.main.fragment_zr_detail_new.llXGSP
import kotlinx.android.synthetic.main.fragment_zr_detail_new.rvRelationProducts
import kotlinx.android.synthetic.main.fragment_zr_detail_new.rvSteps
import kotlinx.android.synthetic.main.fragment_zr_detail_new.rvVideo
import kotlinx.android.synthetic.main.fragment_zr_detail_new.titleBar2
import kotlinx.android.synthetic.main.fragment_zr_detail_new.tvRelationProducts
import kotlinx.android.synthetic.main.fragment_zr_detail_new.tvZrDesc

/**
 * 妆容详情
 */
@LayoutId(R.layout.fragment_zr_detail_new)
class NewZhuangRongDetailFragment : BaseFragment(), RVInterface, BmpUtils, TextViewUtils {

    private lateinit var arr: List<String>
//    private lateinit var fu: FragmentUtils<NewProductGalleryFragment>

    companion object {
        fun newInstance(
            id: Int,
            isAttachDetailActivity: Boolean = false
        ): NewZhuangRongDetailFragment {
            return NewZhuangRongDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt("id", id)
                    putBoolean("isAttachDetailActivity", isAttachDetailActivity)
                }
            }
        }
    }

    override fun init() {

        val id = arguments!!.getInt("id")

        flContainerPDNew.post {
            flContainerPDNew.doLP<LLLP> {
                it.width = getAct().srnWidth - 140.dp - 20.dp
                it.height = getAct().srnWidth - 140.dp - 20.dp
            }
        }

        Req.getZhuangRongFenLeiXiangQing(id) {
            tvZrDesc.txt(it.data.purposeDesc)

            if(it.data.bannerImg != null && it.data.bannerImg.isNotEmpty() && it.data.bannerImg != "[]"){
                arr = Gson().fromJson(it.data.bannerImg, object : TypeToken<List<String>>() {}.type) as List<String>//getAct().strToJsonList<String>(it.data.bannerImg)
            } else {
                arr = listOf(it.data.img)
            }


            arr.forEach {
                Log.d("dasdasdas", it)
            }

//            "---------------${arr.size}".toast()

//            FragmentUtils(
//                getAct(),
//                ProductGalleryFragment.newInstance(
//                    appendStr(ArrayList(arr), ","){
//                        arr[it]
//                    },
//                    arguments!!.getBoolean("isAttachDetailActivity")
//                )
//                ,
//                R.id.flContainerPDNew
//            )

            val imgs = ArrayList(arr)

            flContainerPDNew.setViewAdapter(1){ p->
                getAct().inflate(R.layout.view_gallery).apply {
                    this.view<ViewPager>(R.id.vpGallery).doLP<LLLP> {
                        val size = if(!arguments!!.getBoolean("isAttachDetailActivity"))
                            getAct().srnWidth - 140.dp - 20.dp
                        else
                            getAct().srnWidth - 40.dp
                        it.width = size
                        it.height = size
                    }
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

            Req.getLinkProducts(it.data.id) {
                if (it.data.isNotEmpty()) {
                    tvRelationProducts.show()
                    rvRelationProducts.show()
                    rvRelationProducts.wrap.managerHorizontal().rvAdapter(
                        it.data,
                        { holder, pos ->
                            // 关联产品
                            showBitmap(
                                getAct(),
                                holder.iv(R.id.ivRelProducts),
                                it.data[pos].imgCover
                            )
                            holder.itemClick { v ->
                                BusUtils.post(0x1234, true, it.data[pos].linkId)
//                                goTo<ProductDetailActivity>("id" to it.data[pos].linkId,
//                                    "type" to "Product")
                            }
                        }, R.layout.item_glcp
                    )
                }
            }

            // 步骤列表
            val stepList = it.data.purposeStepList

            var openIndex = -1

            rvSteps.wrap.rvAdapter(
                stepList,
                { holder, pos ->
                    val switchView = holder.v(R.id.switchView) as SwitchView
                    if (!TextUtils.isEmpty(stepList[pos].stepName)) {
                        switchView.setTitle(stepList[pos].stepName!!)
                    }
                    switchView.setContent(stepList[pos].stepContent)
                    switchView.showImgs(stepList[pos].stepImg)
                    switchView.v(R.id.flTop).click {
                        if (openIndex == pos) {
                            openIndex = -1
                        } else {
                            openIndex = pos
                        }
                        rvSteps.update()
                    }
                    if (openIndex == pos) {
                        switchView.open()
                    } else {
                        switchView.close()
                    }
                }, R.layout.item_step
            )

            if (!TextUtils.isEmpty(it.data.linkVideo)) {
                val videoList = it.data.videoDTOList
                titleBar2.show().setTitle("相关视频 # VIDEO")
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