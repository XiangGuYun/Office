package com.office.bean

data class ZhuangRongFenLeiXiangQing(
    val code: Int,
    val `data`: Data,
    val enmsg: String,
    val msg: String,
    val ok: Boolean
) {
    data class Data(
        val bigImg: String?,
        val cateSort: Int,
        val createTime: String,
        val enName: String,
        val id: Int,
        val img: String,
        val isLeaf: Int,
        val isRecommend: Int,
        val linkVideo: String,
        val name: String,
        val pid: Int,
        val purposeDesc: String,
        val purposeStep: String,
        val purposeStepList: List<PurposeStep>,
        val status: Int,
        val title: String,
        val updateTime: String,
        val videoDTOList: List<VideoDTO>,
        val imgCover:String
    ) {
        data class PurposeStep(
            val stepContent: String,
            val stepName: String
        )

        data class VideoDTO(
            val videoCopywriting: String,
            val videoName: String,
            val videoUrl: String
        )
    }
}