package com.office.yewu.products

import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.google.gson.Gson
import com.kotlinlib.common.LLLP
import com.kotlinlib.common.bitmap.BmpUtils
import com.office.bean.ShangPinFenYeLieBiao
import com.office.bean.ZhuangRongFenLeiShu
import com.office.constant.Id
import com.office.constant.MsgWhat
import com.office.net.OK
import com.office.net.Req
import com.yp.baselib.BaseFragment
import com.yp.baselib.LayoutId
import com.yp.baselib.listener.VpChangeListener
import com.yp.baselib.utils.BusUtils
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R
import kotlinx.android.synthetic.main.fragment_zhuang_rong_sub.*
import java.io.File
import kotlin.math.min

@LayoutId(R.layout.fragment_zhuang_rong_sub)
class ZhuangRongSubFragment : BaseFragment(), RVInterface, BmpUtils {

    companion object {



        fun newInstance(id: Int): ZhuangRongSubFragment {
            return ZhuangRongSubFragment().apply {
                arguments = Bundle().apply {
                    putInt("id", id)
                }
            }
        }
    }

    val list = ArrayList<ArrayList<ZhuangRongFenLeiShu.Data.Child>>()

    val list1 = ArrayList<ArrayList<ZhuangRongFenLeiShu.Data>>()


    override fun init() {}

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        val id = arguments!!.getInt("id")
        Req.getZhuangRongFenLeiShu(if (id == -1) OK.OPTIONAL else id.toString()) {
            if (id == Id.CHANG_JING_ZHUANG_RONG || id == Id.JU_BU_ZHUANG_RONG) {
                // 场景妆容
                val childList = arrayListOf<ZhuangRongFenLeiShu.Data.Child>()

                it.data.forEach {
                    val cl = it.childList
                    childList.addAll(cl)
                }

                val pageSize = (childList.size + 5) / 6

                for (i in 0 until pageSize) {
                    list.add(ArrayList())
                }

                list.forEachIndexed { index, list ->
                    list.addAll(childList.subList(6 * index, min(6 * index + 6, childList.size)))
                }

                vp.setViewAdapter(pageSize) {
                    val view = getAct().inflate(R.layout.fragment_products_sub1)
                    view.rv(R.id.rvZR).wrap.gridManager(2).rvAdapter(
                        list[it],
                        { holder, pos ->

                            holder.v(R.id.flImg).post {
                                holder.v(R.id.flImg).doLP<LLLP> {
                                    it.width =
                                        (getAct().srnWidth - 140.dp - 30.dp - 30.dp - 15.dp) / 2
                                    it.height = (getAct().srnWidth - 140.dp -30.dp - 30.dp - 15.dp) / 2
                                }
                                showBitmap(getAct(), holder.iv(R.id.ivZR), list[it][pos].img)
                            }

                            holder.tv(R.id.tvName).text = list[it][pos].name

                            holder.tv(R.id.tvEnName).text = list[it][pos].enName

                            holder.itemClick { v ->
                                BusUtils.post(MsgWhat.SWITCH_TO_DETAIL_PAGE, list[it][pos].id)
                            }
                        },
                        R.layout.item_zr
                    )
                    view
                }

                var whiteDotIndex = 0

                if(list.size > 1){
                    rvDots.wrap.managerHorizontal().rvMultiAdapter(
                        list,
                        { holder, pos ->

                        },
                        {
                            if (whiteDotIndex == it) 0 else 1
                        }, R.layout.item_dot_white, R.layout.item_dot_dark
                    )

                    vp.setOnPageChangeListener(object : VpChangeListener {
                        override fun onPageSelected(p0: Int) {
                            whiteDotIndex = p0
                            rvDots.update()
                        }
                    })
                }


            } else {
                // 场景妆容
                val childList1 = arrayListOf<ZhuangRongFenLeiShu.Data>()

                childList1.addAll(it.data)

                val pageSize = (childList1.size + 5) / 6

                for (i in 0 until pageSize) {
                    list1.add(ArrayList())
                }

                list1.forEachIndexed { index, list ->
                    list.addAll(childList1.subList(6 * index, min(6 * index + 6, childList1.size)))
                }

                vp.setViewAdapter(pageSize) {
                    val view = getAct().inflate(R.layout.fragment_products_sub1)
                    view.rv(R.id.rvZR).wrap.gridManager(2).rvAdapter(
                        list1[it],
                        { holder, pos ->

                            holder.v(R.id.flImg).post {
                                holder.v(R.id.flImg).doLP<LLLP> {
                                    it.width = (getAct().srnWidth - 140.dp - 30.dp - 30.dp - 15.dp) / 2
                                    it.height = (getAct().srnWidth - 140.dp -30.dp - 30.dp - 15.dp) / 2
                                }
                                showBitmap(getAct(), holder.iv(R.id.ivZR), list1[it][pos].img)
                            }

                            holder.tv(R.id.tvName).text = list1[it][pos].name

                            holder.tv(R.id.tvEnName).text = list1[it][pos].enName

                            holder.itemClick { v ->
                                BusUtils.post(MsgWhat.SWITCH_TO_DETAIL_PAGE, list1[it][pos].id)
                            }
                        },
                        R.layout.item_zr
                    )
                    view
                }

                var whiteDotIndex = 0

                if(list1.size > 1) {
                    rvDots.wrap.managerHorizontal().rvMultiAdapter(
                        list1,
                        { holder, pos ->

                        },
                        {
                            if (whiteDotIndex == it) 0 else 1
                        }, R.layout.item_dot_white, R.layout.item_dot_dark
                    )

                    vp.setOnPageChangeListener(object : VpChangeListener {
                        override fun onPageSelected(p0: Int) {
                            whiteDotIndex = p0
                            rvDots.update()
                        }
                    })
                }


            }

        }
    }

}