package com.meiling.common.utils.svg;

import android.graphics.Picture;
import android.graphics.drawable.PictureDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.caverock.androidsvg.SVG;
 
public class SvgDrawableTranscoder implements ResourceTranscoder<SVG, PictureDrawable> {
    @Nullable
    @Override
    public Resource<PictureDrawable> transcode(@NonNull Resource<SVG> toTranscode,
                                               @NonNull Options options) {
        SVG svg = toTranscode.get();
        Picture picture = svg.renderToPicture();
        PictureDrawable drawable = new PictureDrawable(picture);
        return new SimpleResource<>(drawable);
    }
}
//————————————————
//版权声明：本文为CSDN博主「lplj717」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//原文链接：https://blog.csdn.net/lplj717/article/details/112221888