package com.office.yewu

import android.os.Bundle
import com.yp.baselib.BaseActivity
import com.yp.baselib.LayoutId
import com.yp.baselib.StatusBarColor
import com.yp.baselib.utils.view.edittext.EditViewUtils
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R
import kotlinx.android.synthetic.main.activity_search.*
import java.io.File

@StatusBarColor("#000000")
@LayoutId(R.layout.activity_search)
class SearchActivity : BaseActivity(), EditViewUtils, RVInterface {

    override fun init(bundle: Bundle?) {

        val fileSearchHistory = File(CACHE, "search_history.txt")

        if(!fileSearchHistory.exists()){
            fileSearchHistory.createNewFile()
        }

        etSearch.onPressSearch {
            var searchHistory = fileSearchHistory.readText()
            if(searchHistory.isEmpty()){
                searchHistory = etSearch.str
            } else {
                searchHistory += ",${etSearch.str}"
            }
            fileSearchHistory.writeText(searchHistory)
        }

        val searchHistory = fileSearchHistory.readText()

        val wordList = searchHistory.split(",")

        rvSearchHistory.wrap.flexBoxManager().rvAdapter(wordList,
            {
                holder, pos ->
                holder.tv(R.id.tvSearchWord).text = wordList[pos]
            }, R.layout.item_search_word)

    }

}