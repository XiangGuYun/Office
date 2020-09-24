package com.office.net

import android.util.Log
import com.google.gson.Gson
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.StringCallback
import okhttp3.Call

object OK {

    const val OPTIONAL = "optional"

    inline fun <reified T> post(url: String, //URL
                                crossinline onSuccess: (data: T) -> Unit, //成功回调
                                vararg pairs: Pair<String, String>,//参数
    ) {
        Log.d("OK_Result", "url is $url");
        val builder = OkHttpUtils
            .post()
            .url("todo$url")

        pairs.forEach {
            if (it.second != OPTIONAL)
                builder.addParams(it.first, it.second)
            Log.d("OK_Result", "参数：${it.first}, ${it.second}")
        }

        builder.build()
            .connTimeOut(6000)
            .readTimeOut(6000)
            .writeTimeOut(6000)
            .execute(object : StringCallback() {

                override fun onError(call: Call?, e: Exception?, id: Int) {
                    Log.d("OK_Result", "error is ${e?.localizedMessage}");
                    call?.cancel()
                }

                override fun onResponse(response: String?, id: Int) {
                    Log.d("OK_Result", response)
                    //被reified修饰的T可以调用class.java
                    val gson = Gson()
                    onSuccess.invoke(gson.fromJson(response, T::class.java))
                }
            })

    }

}