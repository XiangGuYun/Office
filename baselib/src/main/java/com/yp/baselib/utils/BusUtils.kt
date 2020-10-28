package com.yp.baselib.utils

import android.os.Message
import org.greenrobot.eventbus.EventBus

object BusUtils {

    fun post(what:Int, obj:Any?, arg1:Int= -1,  arg2:Int = -1){
        val msg = Message.obtain()
        msg.what = what
        if(obj != null) msg.obj = obj
        msg.arg1 = arg1
        msg.arg2 = arg2
        EventBus.getDefault().post(msg)
    }

}