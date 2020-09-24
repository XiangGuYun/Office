package com.office.bean

data class ShangPinFenYeLieBiao(
    val code: Int,
    val enmsg: String,
    val errmsg: String,
    val msg: String,
    val ok: Boolean,
    val otherMap: OtherMap,
    val pageNo: Int,
    val pageSize: Int,
    val pages: Int,
    val rows: List<Row>,
    val total: Int
) {
    class OtherMap(
    )

    data class Row(
        val enName: String,
        val id: Int,
        val imgCover: String,
        val name: String,
        val productModel: String,
        val titleList: String
    )
}