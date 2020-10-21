package com.office.yewu

import android.view.View
import com.office.yewu.search.SearchActivity
import com.yp.baselib.BaseActivity
import com.yp.oom.R

abstract class OfficeBaseActivity : BaseActivity() {

    override fun beforeInit() {
        findViewById<View>(R.id.ivBack)?.click { finish() }
        findViewById<View>(R.id.ivSearch)?.click { goTo<SearchActivity>() }
        findViewById<View>(R.id.ivDots)?.click {
            finishAllActivitiesExcept("MainActivity")
        }
        findViewById<View>(R.id.ivOffice)?.click {
            goTo<StandbyActivity>()
        }
    }

}