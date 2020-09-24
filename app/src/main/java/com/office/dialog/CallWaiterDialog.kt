package com.office.dialog

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Button
import com.kotlinlib.common.dialog.BaseDialog
import com.kotlinlib.common.dialog.DialogInfo
import com.yp.oom.R
import java.util.*

/**
 * 呼叫服务员对话框
 * @author YeXuDong
 */
@DialogInfo(320, 250, R.layout.dialog_call_waiter)
class CallWaiterDialog(val ctx: Context) : BaseDialog(ctx) {

    var countdown = 6;

    init {

        var timer:Timer? = null

        setOnShowListener {
            timer = Timer()
            timer?.schedule(object :TimerTask(){
                override fun run() {
                    (ctx as Activity).runOnUiThread {
                        dv.findViewById<Button>(R.id.btnConfirm).text = "确定(${countdown}s)"
                        countdown--
                        if(countdown == -1){
                            dismiss()
                        }
                    }
                }
            }, 0, 1000)
        }

        setOnDismissListener {
            countdown = 6
            timer?.cancel()
        }

        dv.findViewById<View>(R.id.ivClose).setOnClickListener {
            dismiss()
        }

        dv.findViewById<View>(R.id.btnConfirm).setOnClickListener {
            dismiss()
        }

        dv.findViewById<View>(R.id.ivClose).setOnClickListener {
            dismiss()
        }

    }

}