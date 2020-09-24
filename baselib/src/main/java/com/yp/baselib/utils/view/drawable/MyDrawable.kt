package com.kotlinlib.view.drawable

import android.graphics.*
import android.graphics.drawable.Drawable

class MyDrawable(bmp:Bitmap) : Drawable() {

    var bitmap: Bitmap = bmp
    var paint = Paint().apply { isAntiAlias = true }
    lateinit var bounds:RectF
    lateinit var bmpShader: BitmapShader

    override fun draw(canvas: Canvas) {
        canvas.drawRoundRect(bounds, 20f, 20f, paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getOpacity(): Int {
        //返回可能具有透明度的标识
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    //以下为非必须实现的方法************************************************

    /**
     * 给Drawable设定边界，即这块Drawable画布的大小
     */
    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
        //根据边界创建一个与Drawable相同大小的Bitmap作为Drawable的Shader
        bmpShader = BitmapShader(
                Bitmap.createScaledBitmap(bitmap, right-left, bottom - top, true),
                Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP)
        paint.shader = bmpShader
        bounds = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
    }

    /**
     * 设置MyDrawable的默认高度
     */
    override fun getIntrinsicHeight(): Int {
        return bitmap.height
    }

    /**
     * 设置MyDrawable的默认宽度
     */
    override fun getIntrinsicWidth(): Int {
        return bitmap.width
    }
}