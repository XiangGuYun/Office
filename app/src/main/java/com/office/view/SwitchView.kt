package com.office.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.kotlinlib.view.base.ViewUtils
import com.yp.oom.R
import kotlinx.android.synthetic.main.switch_view.view.*

/**
 * 详情中开关视图
 */
class SwitchView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr), ViewUtils {

    private val contentView = LayoutInflater.from(context).inflate(R.layout.switch_view, null)

    var isOpened = false

    init {
        addView(contentView)
    }

    fun open(){
        isOpened = true
        llBtm.show()
        tvPlus.text = "-"
    }

    fun close(){
        isOpened = false
        llBtm.gone()
        tvPlus.text = "+"
    }

    fun setTitle(text:String){
        tvTitle.text = text
    }

    fun setContent(text: String){
        tvContent.text = text
    }


}