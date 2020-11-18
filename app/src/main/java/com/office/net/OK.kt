package com.office.net

import android.os.Environment
import android.util.Log
import com.google.gson.Gson
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.StringCallback
import okhttp3.Call
import okhttp3.MediaType
import java.io.File

/**
 * 接口请求工具类
 */
object OK {

    /**
     * 表示所传参数被忽略
     */
    const val OPTIONAL = "optional"

    val TAG = "OK_Result"

    const val BASE_URL = "http://124.70.156.186:8001"

    const val MEDIA_TYPE = "application/json; charset=utf-8"

    /**
     * post请求
     */
    inline fun <reified T> post(
        url: String, //URL
        crossinline onSuccess: (data: T) -> Unit, //成功回调
        vararg pairs: Pair<String, String>//参数
    ) {

        val mapJson = Gson().toJson(HashMap(pairs.toMap()).filterValues { it != OPTIONAL })
        val builder = OkHttpUtils
            .postString()
            .url("$BASE_URL$url")
            .content(mapJson)
            .mediaType(MediaType.parse(MEDIA_TYPE))


        builder.build()
            .connTimeOut(6000)
            .readTimeOut(6000)
            .writeTimeOut(6000)
            .execute(object : StringCallback() {

                override fun onError(call: Call?, e: Exception?, id: Int) {
                    Log.e(TAG, ".......................................................................................................................................")
                    Log.e(TAG, "×                                                                                                                                  ×")
                    Log.e(TAG, "                                                               请求失败（POST）                                                       ")
                    Log.e(TAG, "×                                                                                                                                  ×")
                    Log.e(TAG, ".......................................................................................................................................")
                    Log.e(
                        TAG,
                        " URL：$url\nJSON：$mapJson\nMessage：${e?.localizedMessage}"
                    );
                    call?.cancel()
                }

                override fun onResponse(response: String?, id: Int) {
                    Log.d(TAG, ".......................................................................................................................................")
                    Log.d(TAG, "√                                                                                                                                  √")
                    Log.d(TAG, "                                                               请求成功（POST）                                                       ")
                    Log.d(TAG, "√                                                                                                                                  √")
                    Log.d(TAG, ".......................................................................................................................................")
                    Log.d(TAG, " URL：$url\nJSON：$mapJson\nRESPONSE：$response")
                    onSuccess.invoke(Gson().fromJson(response, T::class.java))
                }
            })

    }

}

