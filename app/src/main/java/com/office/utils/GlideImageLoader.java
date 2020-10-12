package com.office.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.kotlinlib.common.DensityUtils;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader implements DensityUtils{
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
        RequestOptions options = new RequestOptions()
//                .placeholder(R.drawable.banner_default)
                .diskCacheStrategy(DiskCacheStrategy.NONE);  //禁用掉Glide缓存
        Glide.with(context.getApplicationContext())
                .load(path)
                //.override(SocializeUtils.dip2Px(context, 335),SocializeUtils.dip2Px(context, 168)) //图片的宽和高
                .centerCrop()
                .apply(options)//占位图
                .into(imageView);
    }

    public static void glideImageView(Context context, String url, ImageView imageView, int defaultResId) {
        Glide.with(context).load(url)
                .placeholder(defaultResId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}