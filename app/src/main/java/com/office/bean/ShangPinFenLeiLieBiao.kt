package com.office.bean

data class ShangPinFenLeiLieBiao(
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
        val cateSort: Int,
        val createTime: String,
        val id: Int,
        val img: String,
        val isLeaf: Int,
        val name: String,
        val pid: Int,
        val status: Int,
        val updateTime: String
    )

    class OtherMap(
    )
}