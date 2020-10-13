package com.office.yewu

import android.os.Bundle
import com.kotlinlib.common.persistence.SPUtils
import com.office.net.Req
import com.office.utils.GlideImageLoader
import com.youth.banner.Banner
import com.yp.baselib.BaseActivity
import com.yp.baselib.FullScreen
import com.yp.baselib.LayoutId
import com.yp.oom.R
import kotlinx.android.synthetic.main.activity_standby.*

/**
 * 待机页
 */
@FullScreen
@LayoutId(R.layout.activity_standby)
class StandbyActivity : BaseActivity() {
    override fun init(bundle: Bundle?) {

        Req.getBannerInfo {
            val imgs = it.data
            banner.apply {
                setDelayTime(3000)
                setOnBannerListener {
                    finish()
                }
                setImages(imgs).setImageLoader(GlideImageLoader()).start()
            }
        }

    }
}