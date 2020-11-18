package com.office.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.google.gson.reflect.TypeToken
import com.kotlinlib.common.LLLP
import com.kotlinlib.common.bitmap.BmpUtils
import com.kotlinlib.view.base.ViewUtils
import com.yp.baselib.BaseActivity
import com.yp.baselib.DensityUtils
import com.yp.baselib.utils.ContextUtils
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R
import kotlinx.android.synthetic.main.switch_view.view.*

/**
 * 详情中开关视图
 */
class SwitchView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr), ViewUtils, BmpUtils, RVInterface, ContextUtils {

    private var needGone: Boolean = false
    private val contentView = LayoutInflater.from(context).inflate(R.layout.switch_view, null)

    var isOpened = false

    init {
        addView(contentView)
        llImg.doLP<LLLP> {
//            it.width = (context.srnWidth - DensityUtils.dip2px(context, 160f))/3
            it.height = (context.srnWidth - DensityUtils.dip2px(context, 160f))/3
        }
    }

    fun open(){
        isOpened = true
        llBtm.show()
        if(!needGone){
            llImg.show()
        } else {
            llImg.gone()
        }
        tvPlus.text = "-"
    }

    fun close(){
        isOpened = false
        llBtm.gone()
        llImg.gone()
        tvPlus.text = "+"
    }

    fun setTitle(text:String){
        tvTitle.text = text
    }

    fun setContent(text: String){
        tvContent.text = text
    }

    fun showImgs(imgs:String?){
        if(TextUtils.isEmpty(imgs) || imgs == "[]") {
            needGone = true
            llImg.gone()
            return
        }
        val list = BaseActivity.gson.fromJson(imgs, object : TypeToken<List<String>>() {}.type) as List<String>
        rvImg.wrap.managerHorizontal().rvAdapter(
            list,
            {
                h,p->
                h.v(R.id.fl).doLP<RecyclerView.LayoutParams> {
                    it.width = (context.srnWidth - DensityUtils.dip2px(context, 160f))/3
                }
                val pad = DensityUtils.dip2px(context, 5f)
                when {
                    list.size == 1 -> {
                        h.iv(R.id.iv).setPadding(pad, pad, pad, pad)
                    }
                    p != list.lastIndex -> {
                        h.iv(R.id.iv).setPadding(pad, pad, 0, pad)
                    }
                    else -> {
                        h.iv(R.id.iv).setPadding(pad, pad, pad, pad)
                    }
                }
                showBitmap(context, h.iv(R.id.iv), list[p])
            },
            R.layout.item_img
        )
    }


}