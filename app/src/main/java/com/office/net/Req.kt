package com.office.net

import android.widget.Toast
import com.office.app.MyApplication
import com.office.bean.*

/**
 * 接口请求
 */
object Req {
    /**
     * 妆容分类详情
     * @param id 妆容分类ID
     */
    fun getZhuangRongFenLeiXiangQing(id: Int, callback: (ZhuangRongFenLeiXiangQing) -> Unit) {
        OK.post<ZhuangRongFenLeiXiangQing>(
            URL.ZHUANG_RONG_FEN_LEI_XIANG_QING,
            {
                if(it.code != 0){
                    Toast.makeText(MyApplication.instance.context(), it.msg, Toast.LENGTH_SHORT).show()
                } else {
                    callback.invoke(it)
                }
            }, "id" to id.toString()
        )
    }

    /**
     * 妆容分类列表
     * @param id 父层级ID
     */
    fun getZhuangRongFenLeiLieBiao(id: Int, callback: (ZhuangRongFenLeiLieBiao) -> Unit) {
        OK.post<ZhuangRongFenLeiLieBiao>(
            URL.ZHUANG_RONG_FEN_LEI_LIE_BIAO,
            {
                callback.invoke(it)
            }, "id" to id.toString()
        )
    }

    /**
     * 推荐妆容列表
     * @param pageNo 第几页，从1开始算起
     */
    fun getTuiJianZhuangRongLieBiao(pageNo: Int, callback: (TuiJianZhuangRongLieBiao) -> Unit) {
        OK.post<TuiJianZhuangRongLieBiao>(
            URL.TUI_JIAN_ZHUANG_RONG_LIE_BIAO,
            {
                callback.invoke(it)
            }, "pageNo" to pageNo.toString(), "pageSize" to "10"
        )
    }

    /**
     * 妆容分类树
     * @param id 即父级ID,传0表示查询所有妆容分类
     */
    fun getZhuangRongFenLeiShu(id: String, callback: (ZhuangRongFenLeiShu) -> Unit) {
        OK.post<ZhuangRongFenLeiShu>(
            URL.ZHUANG_RONG_FEN_LEI_SHU,
            {
                callback.invoke(it)
            }, "id" to id
        )
    }

    /**
     * 商品详情
     * @param id 商品id
     */
    fun getShangPinXiangQing(id: Int, callback: (ShangPinXiangQing) -> Unit) {
        OK.post<ShangPinXiangQing>(
            URL.SHANG_PING_XIANG_QING,
            {
                callback.invoke(it)
            }, "id" to id.toString()
        )
    }

    /**
     * 商品分页列表
     * @param categoryId 商品分类ID
     * @param name 商品名称
     * @param pageNo 第几页，从1算起
     * @param purposeId 妆容分类ID
     */
    fun getShangPinFenYeLieBiao(
        purposeId: String = OK.OPTIONAL,
        categoryId: String = OK.OPTIONAL,
        name: String = OK.OPTIONAL,
        callback: (ShangPinFenYeLieBiao) -> Unit
    ) {
        OK.post<ShangPinFenYeLieBiao>(
            URL.SHANG_PIN_FEN_YE_LIE_BIAO,
            {
                callback.invoke(it)
            }, "categoryId" to if(categoryId != "-1") categoryId else OK.OPTIONAL,
            "name" to name, "purposeId" to if(purposeId != "-1") purposeId else OK.OPTIONAL
        )
    }

    /**
     * 待机页轮播图
     */
    fun getDaiJiYeLunBoTu(pageNo: Int, callback: (DaiJiYeLunBoTu) -> Unit) {
        OK.post<DaiJiYeLunBoTu>(
            URL.DAI_JI_YE_LUN_BO_TU,
            {
                callback.invoke(it)
            }, "pageNo" to pageNo.toString()
        )
    }

    /**
     * 商品分类详情
     */
    fun getShangPinFenLeiXiangQing(id: Int, callback: (ShangPinFenLeiXiangQing) -> Unit) {
        OK.post<ShangPinFenLeiXiangQing>(
            URL.SHANG_PIN_FEN_LEI_XIANG_QING,
            {
                callback.invoke(it)
            }, "id" to id.toString()
        )
    }

    /**
     * 商品分类列表
     */
    fun getShangPinFenLeiLieBiao(pid: Int, callback: (ShangPinFenLeiLieBiao) -> Unit) {
        OK.post<ShangPinFenLeiLieBiao>(
            URL.SHANG_PIN_FEN_LEI_LIE_BIAO,
            {
                callback.invoke(it)
            }, "pid" to pid.toString()
        )
    }

    /**
     * 商品分类树
     */
    fun getShangPinFenLeiShu(pid: Int, callback: (ShangPinFenLeiShu) -> Unit) {
        OK.post<ShangPinFenLeiShu>(
            URL.SHANG_PIN_FEN_LEI_SHU,
            {
                callback.invoke(it)
            }, "pid" to pid.toString()
        )
    }

    /**
     * 获取基本参数
     */
    fun getBaseParams(paramid:Int, callback: (BaseParams) -> Unit){
        OK.post<BaseParams>(URL.GET_BASE_PARAMS,
            {
                callback.invoke(it)
            }, "paramid" to paramid.toString())
    }

    /**
     * 获取待机页轮播图数据
     */
    fun getBannerInfo(callback: (BannerInfo) -> Unit){
        OK.post<BannerInfo>(URL.BANNER,
            {
                callback.invoke(it)
            }, "pageNo" to "1", "pageSize" to "10")
    }

    /**
     * 搜索妆容
     */
    fun searchZhuangRong(name:String, callback: (ZhuangRongSearch) -> Unit){
        OK.post<ZhuangRongSearch>(URL.SEARCH_ZHUANG_RONG,
            {
                callback.invoke(it)
            }, "name" to name)
    }

    /**
     * 关联商品列表
     */
    fun getLinkProducts(id:Int, callback: (LinkProduct) -> Unit){
        OK.post<LinkProduct>(URL.GUAN_LIAN_SHANG_PIN_LIE_BIAO,
            {
                callback.invoke(it)
            }, "id" to id.toString())
    }

}