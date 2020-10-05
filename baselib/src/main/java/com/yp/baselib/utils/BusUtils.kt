package com.yp.baselib.utils

import android.os.Message
import org.greenrobot.eventbus.EventBus

object BusUtils {

    fun post(what:Int, obj:Any?){
        val msg = Message.obtain()
        msg.what = what
        if(obj != null) msg.obj = obj
        EventBus.getDefault().post(msg)
    }

}