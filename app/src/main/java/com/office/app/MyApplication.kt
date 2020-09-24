package com.office.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.yp.baselib.net.HttpsHelp
import com.zhy.http.okhttp.OkHttpUtils
import me.yokeyword.fragmentation.BuildConfig
import me.yokeyword.fragmentation.Fragmentation
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * 项目Application
 * @author YeXuDong
 */
class MyApplication : Application() {

    public override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base)
    }

    //--------------------------
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: MyApplication
    }

    private lateinit var _context: Context

    override fun onCreate() {
        super.onCreate()
        instance = this
        _context = applicationContext
        initOKHttp()
        HttpsHelp.handleSSLHandshake()
//        initFragmentation()
    }

    private fun initFragmentation() {
        // 栈视图等功能，建议在Application里初始化
        Fragmentation.builder()
            // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
            .stackViewMode(Fragmentation.BUBBLE)
            .debug(BuildConfig.DEBUG).install()
    }

    @Synchronized
    fun context(): MyApplication {
        return _context as MyApplication
    }

    private fun initOKHttp() {
        val okHttpClient = OkHttpClient.Builder()
            //                .addInterceptor(new LoggerInterceptor("TAG"))
            .connectTimeout(10000L, TimeUnit.MILLISECONDS)
            .readTimeout(10000L, TimeUnit.MILLISECONDS)
            .sslSocketFactory(HttpsHelp.createSSLSocketFactory())
            .hostnameVerifier { p0, p1 -> true }
            .retryOnConnectionFailure(true)
            //其他配置
            .build()
        OkHttpUtils.initClient(okHttpClient)
    }

}