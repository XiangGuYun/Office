package com.office.yewu.search

import android.os.Bundle
import com.office.yewu.OfficeBaseActivity
import com.yp.baselib.LayoutId
import com.yp.baselib.StatusBarColor
import com.yp.baselib.utils.view.edittext.EditViewUtils
import com.yp.baselib.utils.view.recyclerview.RVInterface
import com.yp.oom.R
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.header.*
import java.io.File

/**
 * 搜索页
 */
@StatusBarColor("#000000")
@LayoutId(R.layout.activity_search)
class SearchActivity : OfficeBaseActivity(), EditViewUtils, RVInterface {

    private lateinit var wordList: java.util.ArrayList<String>

    private lateinit var fileSearchHistory:File

    override fun init(bundle: Bundle?) {

        ivSearch.hide()

        fileSearchHistory = File(CACHE, "search_history.txt")

        if(!fileSearchHistory.exists()){
            fileSearchHistory.createNewFile()
        }

        etSearch.onPressSearch {
            doSearch()
        }

        ivSearchIcon.click {
            doSearch()
        }

        val searchHistory = fileSearchHistory.readText()

        wordList = ArrayList(searchHistory.split(","))
        wordList.removeAll {
            it.isEmpty()
        }

        // 搜索历史
        rvSearchHistory.wrap.flexBoxManager().rvAdapter(wordList,
            {
                holder, pos ->
                holder.tv(R.id.tvSearchWord).text = wordList[pos]
                holder.itemClick {
                    goTo<SearchResultActivity>("search" to wordList[pos])
                }
            }, R.layout.item_search_word)

    }

    private fun doSearch() {
        if(etSearch.str.isEmpty()) return
        var searchHistory = fileSearchHistory.readText()
        if(searchHistory.isEmpty()){
            searchHistory = etSearch.str
        } else {
            searchHistory += ",${etSearch.str}"
        }
        fileSearchHistory.writeText(searchHistory)

        wordList.add(etSearch.str)

        rvSearchHistory.update()

        goTo<SearchResultActivity>("search" to etSearch.str)
    }

}