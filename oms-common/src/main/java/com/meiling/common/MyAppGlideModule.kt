package com.meiling.common

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.GlideBuilder
import android.content.Context
import android.graphics.drawable.PictureDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.caverock.androidsvg.SVG
import com.meiling.common.utils.svg.SvgDecoder
import com.meiling.common.utils.svg.SvgDrawableTranscoder
import java.io.InputStream


@GlideModule
class MyAppGlideModule : AppGlideModule() {


    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val memoryCacheSizeBytes = 1024 * 1024 * 20
        builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes.toLong()))
        builder.setDiskCache(
            ExternalPreferredCacheDiskCacheFactory(
                context, "COMUTUONE",
                (memoryCacheSizeBytes * 10).toLong()
            )
        )
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.register(SVG::class.java, PictureDrawable::class.java, SvgDrawableTranscoder())
            .append(InputStream::class.java, SVG::class.java, SvgDecoder());

    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}