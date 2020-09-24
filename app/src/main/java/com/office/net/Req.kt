package com.office.net

import com.office.bean.*

/**
 * 接口请求
 */
object Req {
    /**
     * 妆容分类详情
     * @param id 妆容分类ID
     */
    fun getZhuangRongFenLeiXiangQing(id:Int, callback:(ZhuangRongFenLeiXiangQing)->Unit){
        OK.post<ZhuangRongFenLeiXiangQing>(URL.ZHUANG_RONG_FEN_LEI_XIANG_QING,
            {
                callback.invoke(it)
            }, "id" to id.toString())
    }

    /**
     * 妆容分类列表
     * @param id 父层级ID
     */
    fun getZhuangRongFenLeiLieBiao(id:Int, callback:(ZhuangRongFenLeiLieBiao)->Unit){
        OK.post<ZhuangRongFenLeiLieBiao>(URL.ZHUANG_RONG_FEN_LEI_LIE_BIAO,
            {
                callback.invoke(it)
            }, "id" to id.toString())
    }

    /**
     * 推荐妆容列表
     * @param pageNo 第几页，从1开始算起
     */
    fun getTuiJianZhuangRongLieBiao(pageNo:Int, callback:(TuiJianZhuangRongLieBiao)->Unit){
        OK.post<TuiJianZhuangRongLieBiao>(URL.TUI_JIAN_ZHUANG_RONG_LIE_BIAO,
            {
                callback.invoke(it)
            }, "pageNo" to pageNo.toString())
    }

    /**
     * 妆容分类树
     * @param id 即父级ID,传0表示查询所有妆容分类
     */
    fun getZhuangRongFenLeiShu(id: Int, callback:(ZhuangRongFenLeiShu)->Unit){
        OK.post<ZhuangRongFenLeiShu>(URL.ZHUANG_RONG_FEN_LEI_SHU,
            {
                callback.invoke(it)
            }, "id" to id.toString())
    }

    /**
     * 商品详情
     * @param id 商品id
     */
    fun getShangPinXiangQing(id: Int, callback:(ShangPinXiangQing)->Unit){
        OK.post<ShangPinXiangQing>(URL.SHANG_PING_XIANG_QING,
            {
                callback.invoke(it)
            }, "id" to id.toString())
    }

    /**
     * 商品分页列表
     * @param categoryId 商品分类ID
     * @param name 商品名称
     * @param pageNo 第几页，从1算起
     * @param purposeId 妆容分类ID
     */
    fun getShangPinFenYeLieBiao(categoryId:Int, name:String, pageNo:Int, purposeId:Int,
                                callback:(ShangPinFenYeLieBiao)->Unit){
        OK.post<ShangPinFenYeLieBiao>(URL.SHANG_PIN_FEN_YE_LIE_BIAO,
            {
                callback.invoke(it)
            }, "categoryId" to categoryId.toString(),
        "name" to name, "pageNo" to pageNo.toString(), "purposeId" to purposeId.toString())
    }

    /**
     * 待机页轮播图
     */
    fun getDaiJiYeLunBoTu(pageNo:Int, callback:(DaiJiYeLunBoTu)->Unit){
        OK.post<DaiJiYeLunBoTu>(URL.DAI_JI_YE_LUN_BO_TU,
            {
                callback.invoke(it)
            }, "pageNo" to pageNo.toString())
    }

    /**
     * 商品分类详情
     */
    fun getShangPinFenLeiXiangQing(id: Int, callback:(ShangPinFenLeiXiangQing)->Unit){
        OK.post<ShangPinFenLeiXiangQing>(URL.SHANG_PIN_FEN_LEI_XIANG_QING,
            {
                callback.invoke(it)
            }, "id" to id.toString())
    }

    /**
     * 商品分类列表
     */
    fun getShangPinFenLeiLieBiao(pid:Int, callback:(ShangPinFenLeiLieBiao)->Unit){
        OK.post<ShangPinFenLeiLieBiao>(URL.SHANG_PIN_FEN_LEI_LIE_BIAO,
            {
                callback.invoke(it)
            }, "pid" to pid.toString())
    }

    /**
     * 商品分类树
     */
    fun getShangPinFenLeiShu(pid:Int, callback:(ShangPinFenLeiShu)->Unit){
        OK.post<ShangPinFenLeiShu>(URL.SHANG_PIN_FEN_LEI_SHU,
            {
                callback.invoke(it)
            }, "pid" to pid.toString())
    }

}