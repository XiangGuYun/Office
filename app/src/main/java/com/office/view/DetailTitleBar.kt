package com.office.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.yp.oom.R

class DetailTitleBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr) {

    var isOpened = false

    val view = LayoutInflater.from(context).inflate(R.layout.detail_title_bar, null)

    init {
        addView(view)
    }

    fun setTitle(title:String){
        view.findViewById<TextView>(R.id.tvTitle).text = title
    }

    fun open(){
        isOpened = true
        view.findViewById<TextView>(R.id.tvPlus).text = "-"
        view.findViewById<View>(R.id.line1_1).visibility = View.GONE
        view.findViewById<View>(R.id.line1_2).visibility = View.GONE
    }

    fun close(){
        isOpened = false
        view.findViewById<TextView>(R.id.tvPlus).text = "+"
        view.findViewById<View>(R.id.line1_1).visibility = View.VISIBLE
        view.findViewById<View>(R.id.line1_2).visibility = View.VISIBLE
    }


}