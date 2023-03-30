package com.meiling.common

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.GlideBuilder
import android.content.Context


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

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}