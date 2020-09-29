package com.yp.baselib.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Environment
import android.os.Parcelable
import android.support.design.widget.BottomSheetDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import base.jse.FileUtils
import com.kotlinlib.common.DensityUtils
import me.yokeyword.fragmentation.SupportActivity
import java.io.File
import java.io.Serializable
import java.util.*


/**
 * 上下文扩展类
 */
interface ContextUtils : FileUtils {

    /** 布局填充器 */
    val Context.inflater: LayoutInflater get() = LayoutInflater.from(this)

    /** SDCard路径 */
    val Context.SDCARD: String get() = Environment.getExternalStorageDirectory().toString()

    /** 内置缓存路径 */
    val Context.CACHE: String get() = "/data/data/${this.packageName}/cache"

    /**
     * 根据dp值获取px值
     */
    fun Context.dp2px(number: Number): Int {
        return DensityUtils.dip2px(this, number.toFloat())
    }

    /**
     * 获取屏幕宽度
     */
    val Context.srnWidth get() = this.resources.displayMetrics.widthPixels

    /**
     * 获取屏幕高度
     */
    val Context.srnHeight get() = this.resources.displayMetrics.heightPixels

    /**
     * 将序列化对象写入本地缓存
     */
    fun Context.writeObjToCache(t: Serializable, fileName: String) {
        val cacheFile = File(CACHE, "$fileName.txt")
        cacheFile.writeObject(t)
    }

    /**
     * 从本地缓存中读取序列化对象
     */
    fun <T> Context.readObjFromCache(fileName: String): T {
        val cacheFile = File(CACHE, "$fileName.txt")
        return cacheFile.readObject<T>()
    }

    /**
     * 获取一个Drawable
     */
    fun Context.drawable(id: Int): Drawable? {
        return resources.getDrawable(id)
    }

    /**
     * 延迟打开键盘，不依赖EditText
     * Q: 软键盘把某些布局挤上去的情况
     * A: Activity属性windowSoftInputMode
     * adjustPan:不会把底部的布局给挤上去
     * adjustResize:自适应的，会把底部的挤上去
     */
    fun Activity.openKeyboardDelay(time: Long) {
        Thread {
            Thread.sleep(time)
            runOnUiThread {
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED)
            }
        }.start()
    }

    /**
     * 延迟弹出键盘，依赖EditText
     */
    fun Context.showKeyboardDelay(et: EditText, delayedTime: Long = 1000) {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                val inputManager = et.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.showSoftInput(et, 0)
            }
        }, delayedTime)
    }

    /**
     * 延迟打开键盘，依赖EditText
     */
    fun Activity.openKeyboardDelay(et: EditText, time: Long) {
        Thread {
            Thread.sleep(time)
            runOnUiThread {
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED)
                et.requestFocus()
            }
        }.start()
    }


    /**
     * 自动弹出键盘
     */
    fun Context.showKeyboard(et: EditText) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager!!.showSoftInput(et, InputMethodManager.SHOW_FORCED)//0
    }

    /**
     * 切换键盘开关状态
     */
    fun Context.toggleKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * 关闭键盘1
     */
    fun Activity.closeKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive && currentFocus != null) {
            if (currentFocus!!.windowToken != null) {
                imm.hideSoftInputFromWindow(this.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
    }

    /**
     * 关闭键盘2
     */
    fun Activity.closeKeyboard1() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val v = window.peekDecorView()
        if (null != v) {
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    /**
     * 复制文字到剪贴板
     */
    fun Context.copyStringToChipboard(text: String) {
//        var myClipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//        var myClip: ClipData = ClipData.newPlainText("text", text)
//        myClipboard.primaryClip = myClip
    }

    /**
     * 获取剪贴板的文字信息
     */
    fun Context.getClipBoardText(): String {
        val cm = getSystemService(SupportActivity.CLIPBOARD_SERVICE) as ClipboardManager
        val data = cm.primaryClip
        val item = data!!.getItemAt(0)
        return item.text.toString()
    }

    /**
     * 获取Bundle字符串，若为null则返回空字符串
     */
    fun Activity.extraStr(name: String): String {
        return intent.getStringExtra(name) ?: ""
    }

    /**
     * 获取Bundle可空字符串
     */
    fun Activity.extraStrNull(name: String): String? {
        return intent.getStringExtra(name)
    }

    /**
     * 获取Bundle整型
     */
    fun Activity.extraInt(tag:String, defValue:Int): Int {
        return intent.getIntExtra(tag, defValue)
    }

    /**
     * 获取Bundle布尔值
     */
    fun Activity.extraBool(tag:String, defValue:Boolean): Boolean {
        return intent.getBooleanExtra(tag, defValue)
    }

    /**
     * 获取Bundle序列化1
     * 注意内部类也必须实现序列化，否则无法收到
     */
    fun Activity.extraSerial(name: String): Serializable? {
        return intent.getSerializableExtra(name)
    }

    /**
     * 获取Bundle序列化2
     */
    fun Activity.extraParcel(name: String): Parcelable? {
        return intent.getParcelableExtra(name)
    }

    /**
     * 获取底部弹窗BottomSheetDialog
     */
    fun Context.getBottomSheetDialog(viewId: Int): BottomSheetDialog {
        val dialog = BottomSheetDialog(this)
        val dialogView = LayoutInflater.from(this).inflate(viewId, null)
        dialog.setContentView(dialogView)
        dialog.delegate.findViewById<View>(android.support.design.R.id.design_bottom_sheet)
                ?.setBackgroundColor(resources.getColor(android.R.color.transparent))
        return dialog
    }

    fun <T : View> BottomSheetDialog.view(id: Int): T {
        return delegate.findViewById(id)!!
    }

    fun BottomSheetDialog.v(id: Int): View {
        return delegate.findViewById(id)!!
    }

    fun BottomSheetDialog.et(id: Int): EditText {
        return delegate.findViewById(id)!!
    }

    fun BottomSheetDialog.rb(id: Int): RadioButton {
        return delegate.findViewById(id)!!
    }

    fun BottomSheetDialog.cb(id: Int): CheckBox {
        return delegate.findViewById(id)!!
    }

    fun BottomSheetDialog.rv(id: Int): RecyclerView {
        return delegate.findViewById(id)!!
    }

}