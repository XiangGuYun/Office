package com.kotlinlib.common.bitmap

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.MediaMetadataRetriever
import android.os.Environment
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.VideoDecoder.FRAME_OPTION
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.kotlinlib.common.DensityUtils
import com.kotlinlib.common.ResUtils
import com.yp.baselib.utils.common.bitmap.BmpCompUtils
import id.zelory.compressor.Compressor
import java.io.*
import java.nio.ByteBuffer
import java.security.MessageDigest


interface BmpUtils {
    
    /**
     * 显示网络图片
     * @receiver KotlinActivity
     * @param url String 图片地址
     * @param iv ImageView
     * @param thumbnailValue Float 缩略值
     * @param placeholderImg Int 占位图
     * @param errorLoadImg Int 错误图
     */
    fun Activity.showBmp(url: String, iv: ImageView,
                         thumbnailValue: Float = 1.0f,//缩略图
                         placeholderImg: Int = ResUtils.getMipmapId(this, "ic_launcher"),//占位图
                         errorLoadImg: Int = ResUtils.getMipmapId(this, "ic_launcher")//错误图
    ) {
        Glide.with(this)
                .load(url)
                .thumbnail(thumbnailValue)
                .apply(RequestOptions()
                        .placeholder(placeholderImg)
                        .error(errorLoadImg)
                        .override(this.resources.displayMetrics.widthPixels / 4, DensityUtils.dip2px(this, 50f))
                        .centerCrop())
                .into(iv)
    }

    /**
     * Bitmap转二进制
     */
    fun Bitmap.toBytes(isPng: Boolean = true, quality: Int = 100): ByteArray {
        val o = ByteArrayOutputStream()
        if (isPng)
            this.compress(Bitmap.CompressFormat.PNG, quality, o)
        else
            this.compress(Bitmap.CompressFormat.JPEG, quality, o)
        return o.toByteArray()
    }

    /**
     * 二进制转Bitmap
     */
    fun ByteArray.toBmp(): Bitmap {
        return BitmapFactory.decodeByteArray(this, 0, this.size)
    }

    /**
     * Drawable转Bitmap
     */
    fun Drawable.toBmp(): Bitmap {
        return (this as BitmapDrawable).bitmap
    }

    /**
     * Bitmap转Drawable
     */
    fun Bitmap.toDrawable(): Drawable {
        return BitmapDrawable(this)
    }

    /**
     * 保存Bitmap到本地
     */
    fun Bitmap.save(path: String =
                            Environment.getExternalStorageDirectory().toString(),
                    name: String = "default.jpg",
                    isJpeg: Boolean = true,
                    quality: Int = 100) {
        var file = File("$path/$name")
        if (!file.exists()) {
            file.parentFile.mkdirs()
            file.createNewFile()
        }
        val fos = FileOutputStream(file)
        this.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.flush()
        fos.close()
    }

    /**
     * 从资源文件中解析Bitmap
     */
    fun Context.bmpFromRes(id: Int, options: BitmapFactory.Options = BitmapFactory.Options()): Bitmap {
        return BitmapFactory.decodeResource(this.resources, id, options)
    }

    /**
     * 从本地路径中解析Bitmap
     * @receiver Context
     * @param path String
     * @param options BitmapFactory.Options
     * @return Bitmap
     */
    fun Context.bmpFromRes(path: String, options: BitmapFactory.Options = BitmapFactory.Options()): Bitmap {
        return BitmapFactory.decodeFile(path, options)
    }

    /**
     * 显示网络图片
     */
    @SuppressLint("CheckResult")
    fun showBitmap(ctx: Context,
                   iv: ImageView,
                   imgUrl: String?,
                   placeholderDrawable: Triple<Int, Int, Int>? = null,
                   placeholderRes: Int = -1,
                   override: Pair<Int, Int> = 0 to 0,
                   errorDrawable: Drawable? = null,
                   errorRes: Int = -1,
                   priority: Priority = Priority.NORMAL) {
        if (imgUrl == null) return
        val req = RequestOptions()
        if(placeholderDrawable != null){
            val bmp = Bitmap.createBitmap(placeholderDrawable.first,
                    placeholderDrawable.second, Bitmap.Config.RGB_565)
            val canvas = Canvas(bmp)
            canvas.drawColor(placeholderDrawable.third)
            req.placeholder(BitmapDrawable(bmp))
        }
        if(placeholderRes != -1){
            req.placeholder(placeholderRes)
        }
        if(errorDrawable != null){
            req.error(errorDrawable)
        }
        if(errorRes != -1){
            req.error(errorRes)
        }
        if(override != 0 to 0){
            req.override(override.first, override.second)
        }
        req.priority(priority)
        Glide.with(ctx).load(imgUrl).apply(req)
                .transition(DrawableTransitionOptions.withCrossFade()).addListener(object : RequestListener<Drawable> {
                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
                                                 dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        Log.d("Glide_Url", "==== " + e?.message + "===== " + e?.localizedMessage)
                        return false
                    }

                })
                .into(iv)
    }

    fun showBitmap(ctx: Context, iv: ImageView, imgUrl: String?, placeholder:Int, errorImgId:Int, override:Pair<Int,Int>, priority:Priority=Priority.NORMAL) {
        if (imgUrl == null) return
        Glide.with(ctx).load(imgUrl).apply(RequestOptions()
                .placeholder(placeholder)
                .error(errorImgId)
                .override(override.first, override.second)
                .priority(priority)
                .centerCrop())
//                .transition(DrawableTransitionOptions.withCrossFade())
                .addListener(object : RequestListener<Drawable> {
                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
                                                 dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        Log.d("Glide_Url", "==== " + e?.message + "===== " + e?.localizedMessage)
                        return false
                    }

                })
                .into(iv)
    }

    fun showBitmap(ctx: Context, iv: ImageView, imgUrl: String, errorImgId: Int) {
        Log.d("Glide_Url", imgUrl)
        Glide.with(ctx).load(imgUrl).apply(RequestOptions()
//                .placeholder(R.drawable.ic_launcher)
                .error(errorImgId)
//                .override(320, 240).
                .priority(Priority.IMMEDIATE)
                .fitCenter())
                .addListener(object : RequestListener<Drawable> {
                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
                                                 dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        Log.d("Glide_Url", "==== " + e?.message + "===== " + e?.localizedMessage)
                        return false
                    }

                })
                .into(iv)
    }

    fun showBitmap(ctx: Context, iv: ImageView, imgUrl: String, placeHolder:Int, getBitmap: (bmp: Bitmap) -> Unit) {
        Log.d("Glide_Url", imgUrl)
        Glide.with(ctx).load(imgUrl).apply(RequestOptions()
//                .placeholder(placeHolder)
//                .error(errorImgId)
//                .override(320, 240).
                .priority(Priority.IMMEDIATE)
                .fitCenter())
                .transition(DrawableTransitionOptions.withCrossFade())
                .addListener(object : RequestListener<Drawable> {
                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
                                                 dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        Log.d("Glide_Url", "==== " + e?.message + "===== " + e?.localizedMessage)
                        return false
                    }

                })
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        iv.setImageDrawable(resource)
                        getBitmap.invoke(BmpCompUtils.drawable2Bitmap(resource))
                    }
                })
    }

    fun url2Bmp(ctx: Context, imgUrl: String, getBitmap: (bmp: Bitmap) -> Unit) {
        Log.d("Glide_Url", imgUrl)
        Glide.with(ctx).load(imgUrl).apply(RequestOptions()
//                .placeholder(R.drawable.ic_launcher)
//                .error(errorImgId)
//                .override(320, 240).
                .priority(Priority.IMMEDIATE)
                .fitCenter())
                .addListener(object : RequestListener<Drawable> {
                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
                                                 dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        Log.d("Glide_Url", "==== " + e?.message + "===== " + e?.localizedMessage)
                        return false
                    }

                })
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        if(imgUrl.contains(".gif")){
                            val res = (resource as GifDrawable)
                            getBitmap.invoke(res.firstFrame)
                        } else {
                            getBitmap.invoke(BmpCompUtils.drawable2Bitmap(resource))
                        }
                    }
                })
    }


    companion object {

        fun rsBlur(context: Context, source: Bitmap, radius:Int):Bitmap{
            val inputBmp = source
            //(1)
            val renderScript =  RenderScript.create(context)

            Log.i("Test","scale size:"+inputBmp.getWidth()+"*"+inputBmp.getHeight())

            // Allocate memory for Renderscript to work with
            //(2)
            val input = Allocation.createFromBitmap(renderScript,inputBmp)
            val output = Allocation.createTyped(renderScript,input.getType())
            //(3)
            // Load up an instance of the specific script that we want to use.
            val scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
            //(4)
            scriptIntrinsicBlur.setInput(input)
            //(5)
            // Set the blur radius
            scriptIntrinsicBlur.setRadius(radius.toFloat())
            //(6)
            // Start the ScriptIntrinisicBlur
            scriptIntrinsicBlur.forEach(output)
            //(7)
            // Copy the output to the blurred bitmap
            output.copyTo(inputBmp)
            //(8)
            renderScript.destroy()

            return inputBmp
        }

        /**
         * 获取视频文件截图
         * @param path 视频文件的路径
         * @return Bitmap 返回获取的Bitmap
         */
        fun getVideoThumb(path: String, compress: Boolean = true): Bitmap {
            val media = MediaMetadataRetriever()
            media.setDataSource(path)
            val bitmap = media.frameAtTime
            if (!compress) {
                return bitmap
            }
            return BmpCompUtils.compressImage(bitmap)
        }

        fun getVideoInfo(path: String): Triple<String, String, String> {
            val media = MediaMetadataRetriever()
            media.setDataSource(path)
            val duration = media.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION)
            val width = media.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)
            val height = media.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)
            return Triple(width, height, duration)
        }

        //--------------------------------------------------------------------------------------

        /**
         * 二进制字符串转Bitmap
         * str 二进制字符串
         * @return
         */
        fun strToBitmap(str: String): Bitmap {
            val strs = str.replace("[", "").replace("]", "").split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val newBytes = ByteArray(strs.size)
            for (i in strs.indices) {
                newBytes[i] = java.lang.Byte.parseByte(strs[i])
            }
            return BitmapFactory.decodeByteArray(newBytes, 0, newBytes.size)
        }

        enum class FORMAT{
            PNG, JPG, WEBP
        }

        fun bmpToBytes(bitmap: Bitmap, quality:Int = 10, format:FORMAT = Companion.FORMAT.PNG): ByteArray? {
            val baos = ByteArrayOutputStream()
            bitmap.compress(when(format){
                Companion.FORMAT.PNG -> Bitmap.CompressFormat.PNG
                Companion.FORMAT.WEBP -> Bitmap.CompressFormat.WEBP
                else -> Bitmap.CompressFormat.JPEG
            }, quality, baos)
            return baos.toByteArray()
        }

        fun bmpToByteArr(bmp:Bitmap): ByteArray {
            val bytes = bmp.byteCount
            val buf = ByteBuffer.allocate(bytes)
            bmp.copyPixelsToBuffer(buf)
            return buf.array()
        }




        fun bmpCompress(ctx:Context, file:File): Bitmap {
            return Compressor(ctx)
                    .setQuality(1)
                    .compressToBitmap(file)
        }

        /**
         * 压缩文件路径下的图片
         * @param filePath 文件路径
         * @param targetW 想要的宽度值
         * @param targetH 想要的高度值
         * @return
         */
        fun decodeBitmap(filePath: String, targetW: Int, targetH: Int): Bitmap {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true//设置只解码图片的边框（宽高）数据，只为测出采样率
            options.inPreferredConfig = Bitmap.Config.RGB_565//设置图片像素格式的首选配置
            BitmapFactory.decodeFile(filePath, options)//预加载
            //获取图片的原始宽高
            val originalW = options.outWidth
            val originalH = options.outHeight
            //设置采样大小
            options.inSampleSize = getSimpleSize(originalW, originalH, targetW, targetH)
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeFile(filePath, options)
        }

        fun decodeBitmap(filePath: String): Bitmap {
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.RGB_565//设置图片像素格式的首选配置
            options.inSampleSize = 2//设置采样率大小
            return BitmapFactory.decodeFile(filePath, options)
        }

        fun decodeBitmap(ctx: Context, id: Int, targetW: Int, targetH: Int): Bitmap {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true//设置只解码图片的边框（宽高）数据，只为测出采样率
            options.inPreferredConfig = Bitmap.Config.RGB_565//设置图片像素格式的首选配置
            BitmapFactory.decodeResource(ctx.resources, id, options)//预加载
            //获取图片的原始宽高
            val originalW = options.outWidth
            val originalH = options.outHeight
            //设置采样大小
            options.inSampleSize = getSimpleSize(originalW, originalH, targetW, targetH)
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeResource(ctx.resources, id, options)
        }

        /**
         * 压缩Bitmap质量
         * @param bitmap
         * @param quality 1-100
         * @return
         */
        fun compressQuality(bitmap: Bitmap, quality: Int): Bitmap {
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos)
            val bytes = baos.toByteArray()
            try {
                baos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }

        fun saveBitmapToSDCard(bitmap: Bitmap, fileName: String, quality: Int) {
            val f = File(Environment.getExternalStorageDirectory().toString(), fileName)
            var out: FileOutputStream? = null
            try {
                out = FileOutputStream(f)
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)
                try {
                    out.flush()
                    out.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }

        fun saveBitmapToCache(ctx: Context, bitmap: Bitmap, fileName: String, quality: Int) {
            val f = File("/data/data/${ctx.packageName}/cache", fileName)
            var out: FileOutputStream? = null
            try {
                out = FileOutputStream(f)
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)
                try {
                    out.flush()
                    out.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }


        fun compressSize(bitmap: Bitmap, targetW: Int, targetH: Int): Bitmap {
            return Bitmap.createScaledBitmap(bitmap, targetW, targetH, true)
        }

        /**
         * 计算采样率
         */
        private fun getSimpleSize(originalW: Int, originalH: Int, targetW: Int, targetH: Int): Int {
            var sampleSize = 1
            if (originalW > originalH && originalW > targetW) {//以宽度来计算采样值
                sampleSize = originalW / targetW
            } else if (originalW < originalH && originalH > targetH) {
                sampleSize = originalH / targetH
            }
            if (sampleSize <= 0) {
                sampleSize = 1
            }
            return sampleSize
        }

    }

    fun loadVideoScreenshot(context: Context, uri: String, imageView: ImageView, frameTimeMicros: Long) {
        val requestOptions = RequestOptions.frameOf(frameTimeMicros)
        requestOptions.set(FRAME_OPTION, MediaMetadataRetriever.OPTION_CLOSEST)
        requestOptions.transform(object : BitmapTransformation() {
            override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
                return toTransform
            }

            override fun updateDiskCacheKey(messageDigest: MessageDigest) {
                try {
                    messageDigest.update((context.packageName + "RotateTransform").toByteArray(charset("utf-8")))
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        })
        Glide.with(context).load(uri).apply(requestOptions).into(imageView)
    }

}