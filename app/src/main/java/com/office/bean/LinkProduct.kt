package com.office.bean

data class LinkProduct(
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
        val enName: String,
        val id: Int,
        val imgCover: String,
        val linkId: Int,
        val name: String,
        val productModel: String,
        val titleList: String
    )

    class OtherMap(
    )
}