package com.office.bean

data class ShangPinFenLeiShu(
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
        val childList: List<Child>,
        val id: Int,
        val img: String,
        val isLeaf: Int,
        val name: String
    ) {
        data class Child(
            val childList: List<Child>,
            val id: Int,
            val img: String,
            val isLeaf: Int,
            val name: String
        ) {
            class Child(
            )
        }
    }

    class OtherMap(
    )
}

data class BaseParams(
    val code: Int,
    val `data`: String,
    val enmsg: String,
    val msg: String,
    val ok: Boolean
)

data class BannerInfo(
    val code: Int,
    val `data`: List<String>,
    val enmsg: String,
    val msg: String,
    val ok: Boolean
)