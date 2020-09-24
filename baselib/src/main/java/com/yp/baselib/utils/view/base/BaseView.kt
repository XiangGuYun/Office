package com.yp.baselib.utils.view.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.kotlinlib.view.base.ViewUtils

/**
 * 基础View
 */
open class BaseView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        View(context, attrs, defStyleAttr), ViewUtils {

}