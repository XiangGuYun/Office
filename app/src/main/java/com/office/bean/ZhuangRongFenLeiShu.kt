package com.office.bean

data class ZhuangRongFenLeiShu(
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
        val childList: List<Child>,
        val enName: String,
        val id: Int,
        val img: String,
        val isLeaf: Int,
        val linkVideo: String,
        val name: String,
        val pid: Int,
        val purposeDesc: String,
        val purposeStep: String,
        val title: String
    ) {
        data class Child(
            val bigImg: String,
            val childList: List<Child>,
            val enName: String,
            val id: Int,
            val img: String,
            val isLeaf: Int,
            val linkVideo: String,
            val name: String,
            val pid: Int,
            val purposeDesc: String,
            val purposeStep: String,
            val title: String
        ) {
            class Child(
            )
        }
    }

    class OtherMap(
    )
}