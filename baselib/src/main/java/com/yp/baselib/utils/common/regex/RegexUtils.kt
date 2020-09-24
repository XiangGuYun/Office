package com.yp.baselib.utils.common.regex

import java.util.regex.Pattern

class RegexUtils {

    companion object {
        /***
         * 手机号码检测
         */
        infix fun checkPhoneNum(num: String): Boolean {
            val p = Pattern.compile("^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$")
            val m = p.matcher(num)
            return m.matches()
        }

        /**
         * 身份证号码检测
         */
        infix fun checkIdNum(num: String): Boolean {
            return IDCardCheck.check(num)
        }
    }

}