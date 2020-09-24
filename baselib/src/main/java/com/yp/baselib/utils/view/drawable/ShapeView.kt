package com.kotlinlib.view.drawable

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.*
import android.util.AttributeSet
import com.yp.baselib.utils.view.base.BaseView

/**
 * 该View讲解了ShapeDrawable的用法
 *
 * 常用函数
 * 1.setBounds()：指定当前ShapeDrawable在当前控件中的显示位置。
 * 2.getPaint()：获得ShapeDrawable的自带Paint，对其进行操作后效果会直接发生作用。
 */
class ShapeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        BaseView(context, attrs, defStyleAttr){

    var shapeDrawable:ShapeDrawable
    var shapeDrawable1:ShapeDrawable
    var shapeDrawable2:ShapeDrawable
    var shapeDrawable3:ShapeDrawable
    var shapeDrawable4:ShapeDrawable

    init {
        banGPU()
        //构造矩形
        shapeDrawable = ShapeDrawable(RectShape())
        shapeDrawable.bounds = Rect(50,50,200,100)
        shapeDrawable.paint.color = Color.RED

        //构造椭圆
        shapeDrawable1 = ShapeDrawable(OvalShape())
        shapeDrawable1.bounds = Rect(50,100,200,200)
        shapeDrawable1.paint.color = Color.RED

        //构造扇形
        shapeDrawable2 = ShapeDrawable(ArcShape(0f, 300f))
        shapeDrawable2.bounds = Rect(50,200,200,300)
        shapeDrawable2.paint.color = Color.RED

        //构造圆角（镂空）矩形
        //外围矩形各个角的角度大小
        val outerR = floatArrayOf(
                12f, 12f, //左上角
                12f, 12f, //右上角
                0f, 0f, //右下角
                0f, 0f //左下角
        )
        //内部矩形与外部矩形各边的边距
        val inset = RectF(6f, 6f, 6f, 6f)
        //内部矩形各个角的角度大小
        val innerR = floatArrayOf(50f, 12f, 0f, 0f, 12f, 50f, 0f, 0f)
        shapeDrawable3 = ShapeDrawable(RoundRectShape(outerR, inset, innerR))
        shapeDrawable3.bounds = Rect(50,300,200,400)
        shapeDrawable3.paint.color = Color.RED

        //构造路径
        val path = Path().apply{
            moveTo(0f, 0f)
            lineTo(100f, 0f)
            lineTo(100f, 100f)
            lineTo(0f, 100f)
            close()
        }
        shapeDrawable4 = ShapeDrawable(PathShape(path,
                200f, //标准宽度，类似于比例尺，该值越大，那么显示的路径就越小，反之则越大。
                200f //标准高度
        ))
        shapeDrawable4.bounds = Rect(50,400,200,500)
        shapeDrawable4.paint.color = Color.GREEN
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.scale(2.5f, 2.5f)
        shapeDrawable.draw(canvas)
        shapeDrawable1.draw(canvas)
        shapeDrawable2.draw(canvas)
        shapeDrawable3.draw(canvas)
        shapeDrawable4.draw(canvas)
    }


}