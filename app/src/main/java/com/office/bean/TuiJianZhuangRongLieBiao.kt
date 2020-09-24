package com.office.bean

data class TuiJianZhuangRongLieBiao(
    val checkOk: Boolean,
    val code: Int,
    val `data`: List<Data>,
    val enmsg: String,
    val errmsg: String,
    val msg: String,
    val ok: Boolean,
    val otherMap: OtherMap
) {
    data class Data(
        val bigImg: String,
        val cateSort: Int,
        val createTime: String,
        val enName: String,
        val id: Int,
        val img: String,
        val isLeaf: Int,
        val isRecommend: Int,
        val linkVideo: String,
        val name: String,
        val pid: Int,
        val purposeDesc: String,
        val purposeStep: String,
        val status: Int,
        val title: String,
        val updateTime: String
    )

    class OtherMap(
    )
}