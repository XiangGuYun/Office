package com.office.yewu.products

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.LayoutInflater
import android.widget.ImageView
import com.kotlinlib.common.LLLP
import com.kotlinlib.common.bitmap.BmpUtils
import com.kotlinlib.common.listener.OnPageChange
import com.office.constant.MsgWhat
import com.office.net.Req
import com.yp.baselib.BaseFragment
import com.yp.baselib.LayoutId
import com.yp.baselib.utils.BusUtils
import com.yp.baselib.utils.fragment.old.FragPagerUtils
import com.yp.oom.R
import kotlinx.android.synthetic.main.fragment_new_products.*
import kotlinx.android.synthetic.main.fragment_zhuang_rong_sub.*


/**
 * 新品上市或MAKEUP
 */
@LayoutId(R.layout.fragment_new_products)
class NewProductFragment : BaseFragment(), BmpUtils {

    override fun init() {
       initViewPager()
    }

    companion object{
        fun newInstance(isMakeUp:Boolean): NewProductFragment {
            return NewProductFragment().apply {
                arguments = Bundle().apply {
                    putBoolean("isMakeUp", isMakeUp)
                }
            }
        }
    }

    override fun onDestroyView() {
        vpNewProduct.stopAutoScroll()
        super.onDestroyView()
    }

    private fun initViewPager() {

        val isMakeUp = arguments!!.getBoolean("isMakeUp")

        if(isMakeUp){
            doMakeUp()
        } else {
            doNewProduct()
        }

        flNewProduct.doLP<LLLP> {
            val width = getAct().srnWidth - 140.dp - 20.dp
            it.width = width
            it.height = width * 350 / 260
        }

        tabLayout.tabMode = TabLayout.MODE_FIXED //设置固定Tab模式

        vpNewProduct.startAutoScroll()
        vpNewProduct.interval = 3000
        vpNewProduct.setScrollDurationFactor(4.0)
    }

    private fun doMakeUp() {
        Req.getTuiJianZhuangRongLieBiao(pageNo = 1){
            val data = it.data
            vpNewProduct.setViewAdapter(data.size){
                val iv = getAct().inflate(R.layout.iv_gallery) as ImageView
                showBitmap(getAct(), iv, data[it].img)
                iv.click {v->
                    (getAct() as ProductsActivity).isDetailPage = false
                    BusUtils.post(MsgWhat.SWITCH_TO_DETAIL_PAGE, data[it].id)
                }
                iv
            }

            if(data.isNotEmpty()){
                tvTitle.text = data[0].purposeDesc
                tvName.text = data[0].name
                tvEnName.text = data[0].enName
            }

            vpNewProduct.setOnPageChangeListener(object :OnPageChange{
                override fun onPageSelected(position: Int) {
                    tvTitle.text = data[position].purposeDesc
                    tvName.text = data[position].name
                    tvEnName.text = data[position].enName
                }
            })

            for (i in 1..data.size) {
                val tab = tabLayout.newTab()
                tabLayout.addTab(tab)
            }

            if(data.size > 1){
                //将TabLayout和ViewPager关联起来
                tabLayout.setupWithViewPager(vpNewProduct, true)

                tabLayout.setSelectedTabIndicatorColor(Color.WHITE)
                tabLayout.setSelectedTabIndicatorHeight(1.5.dp)
            } else {
                tabLayout.hide()
            }

        }
    }

    private fun doNewProduct() {
        Req.getShangPinFenYeLieBiao{
            val data = it.data ?: return@getShangPinFenYeLieBiao
            vpNewProduct.setViewAdapter(data.size) {
                val iv = getAct().inflate(R.layout.iv_gallery) as ImageView
                showBitmap(getAct(), iv, data[it].imgCover)
                iv.click {v->
                    (getAct() as ProductsActivity).isDetailPage = true
                    BusUtils.post(MsgWhat.SWITCH_TO_DETAIL_PAGE, data[it].id)
                }
                iv
            }

            tvTitle.text = data[0].titleList
            tvName.text = data[0].name
            tvEnName.text = data[0].enName

            vpNewProduct.setOnPageChangeListener(object :OnPageChange{
                override fun onPageSelected(position: Int) {
                    tvTitle.text = data[position].titleList
                    tvName.text = data[position].name
                    tvEnName.text = data[position].enName
                }
            })

            for (i in 1..data.size) {
                val tab = tabLayout.newTab()
                tabLayout.addTab(tab)
            }

            //将TabLayout和ViewPager关联起来
            tabLayout.setupWithViewPager(vpNewProduct, true)

            tabLayout.setSelectedTabIndicatorColor(Color.WHITE)
            tabLayout.setSelectedTabIndicatorHeight(1.5.dp)
        }
    }

}