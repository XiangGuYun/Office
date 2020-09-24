package com.office.bean

data class DaiJiYeLunBoTu(
    val checkOk: Boolean,
    val code: Int,
    val `data`: List<String>,
    val enmsg: String,
    val errmsg: String,
    val msg: String,
    val ok: Boolean,
    val otherMap: OtherMap
) {
    class OtherMap(
    )
}