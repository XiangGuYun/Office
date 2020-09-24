package com.kotlinlib.common

import android.os.Message

/**
 * Message简化
 */
interface MessageUtils {

    val msg get() = Message.obtain()

    val m get() = Message.obtain()

    val Message.w get() = msg.what

    infix fun Message.w(what: Int): Message {
        this.what = what
        return this
    }

    infix fun Message.o(any: Any): Message {
        this.obj = any
        return this
    }

    infix fun Message.a1(arg1: Int): Message {
        this.arg1 = arg1
        return this
    }

    infix fun Message.a2(arg2: Int): Message {
        this.arg2 = arg2
        return this
    }

}