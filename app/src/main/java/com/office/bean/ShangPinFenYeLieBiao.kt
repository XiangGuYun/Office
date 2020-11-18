package com.office.bean

import java.io.Serializable

data class ShangPinFenYeLieBiao(
    val code: Int,
    val `data`: List<Data>? = null,
    val enmsg: String,
    val msg: String,
    val ok: Boolean
) : Serializable {
    data class Data(
        val enName: String,
        val id: Int,
        val imgCover: String,
        val name: String,
        val productModel: String,
        val titleList: String
    ) : Serializable
}