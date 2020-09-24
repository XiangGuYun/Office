package com.office.yewu

import android.os.Bundle
import android.os.CountDownTimer
import com.yp.baselib.BaseActivity
import com.yp.baselib.FullScreen
import com.yp.baselib.LayoutId
import com.yp.oom.R
import com.office.yewu.products.ProductsActivity

/**
 * 启动页
 */
@FullScreen
@LayoutId(R.layout.activity_splash)
class SplashActivity : BaseActivity() {
    lateinit var timerTask:CountDownTimer
    override fun init(bundle: Bundle?) {
        timerTask = object : CountDownTimer(1000, 1000){
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                goTo<ProductsActivity>()
                finish()
            }
        }
        timerTask.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timerTask.cancel()
    }

}