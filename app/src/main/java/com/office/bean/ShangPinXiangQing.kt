package com.office.bean

data class ShangPinXiangQing(
    val checkOk: Boolean,
    val code: Int,
    val `data`: Data,
    val enmsg: String,
    val errmsg: String,
    val msg: String,
    val ok: Boolean,
    val otherMap: OtherMap
) {
    data class Data(
        val bannerImg: String,
        val enName: String,
        val id: Int,
        val imgCover: String,
        val mallSkuDetailList: List<MallSkuDetail>,
        val name: String,
        val productCharacter: String,
        val productModel: String,
        val productParam: String,
        val productVideo: String,
        val titleList: String,
        val skuType: Int,
        val videoDTOList: List<VideoDTO>
    ) {
        data class MallSkuDetail(
            val id: Int,
            val productImg: String,
            val productModel: String,
            val skuIcon: String
        )
        data class VideoDTO(
            val videoCopywriting: String,
            val videoName: String,
            val videoUrl: String
        )
    }

    class OtherMap(
    )
}