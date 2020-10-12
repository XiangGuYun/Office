package com.office.yewu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.kotlinlib.common.Ctx
import com.yp.baselib.BaseActivity
import com.yp.baselib.FullScreen
import com.yp.baselib.LayoutId
import com.yp.oom.R
import kotlinx.android.synthetic.main.activity_video.*

@FullScreen
@LayoutId(R.layout.activity_video)
class OfficeVideoActivity : BaseActivity() {

    companion object{
        fun go(ctx: Ctx, isNetVideo:Boolean, path:String){
            ctx.startActivity(Intent(ctx, OfficeVideoActivity.javaClass)
                .putExtra("isNetVideo", isNetVideo)
                .putExtra("path", path))
        }
    }

    override fun init(bundle: Bundle?) {

        video_view.setOnPreparedListener {
            video_view.start()
        }

        val path = extraStr("path")

        if(extraBool("isNetVideo", false)){
            video_view.setVideoURI(Uri.parse(path))
        } else {
            video_view.setVideoPath(path)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        video_view.stopPlayback()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }
}