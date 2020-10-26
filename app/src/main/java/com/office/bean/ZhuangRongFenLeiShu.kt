package com.office.bean

import java.io.Serializable

data class ZhuangRongFenLeiShu(
    val checkOk: Boolean,
    val code: Int,
    val `data`: List<Data>,
    val enmsg: String,
    val errmsg: String,
    val msg: String,
    val ok: Boolean,
    val otherMap: OtherMap
)  : Serializable {
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
    )  : Serializable {
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
        )  : Serializable{
            class Child(
            ) : Serializable
        }
    }

    class OtherMap(
    )
}

//data class ZhuangRongFenLeiShu(
//    val checkOk: Boolean,
//    val code: Int,
//    val `data`: List<Data>,
//    val enmsg: String,
//    val msg: String,
//    val ok: Boolean
//) {
//    data class Data(
//        val bigImg: String,
//        val childList: List<Any>,
//        val enName: String,
//        val id: Int,
//        val img: String,
//        val isLeaf: Int,
//        val linkVideo: String,
//        val name: String,
//        val pid: Int,
//        val purposeDesc: String,
//        val purposeStep: String
//    )
//}