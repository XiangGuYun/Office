package com.office.bean

data class Test(
    val code: Int = 0,
    val `data`: Data = Data(),
    val enmsg: String = "",
    val msg: String = "",
    val ok: Boolean = false
) {
    data class Data(
        val bannerImg: String = "",
        val cateSort: Int = 0,
        val createTime: String = "",
        val enName: String = "",
        val id: Int = 0,
        val img: String = "",
        val isLeaf: Int = 0,
        val isRecommend: Int = 0,
        val name: String = "",
        val pid: Int = 0,
        val purposeStep: String = "",
        val purposeStepList: List<Any> = listOf(),
        val status: Int = 0,
        val updateTime: String = ""
    )
}