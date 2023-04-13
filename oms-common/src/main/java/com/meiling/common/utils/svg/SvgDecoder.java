package com.meiling.common.utils.svg;


import androidx.annotation.NonNull;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
 
import java.io.IOException;
import java.io.InputStream;
 
public class SvgDecoder implements ResourceDecoder<InputStream, SVG> {
 
    @Override
    public boolean handles(@NonNull InputStream source, @NonNull Options options) {
        // TODO: Can we tell?
        return true;
    }
 
    public Resource<SVG> decode(@NonNull InputStream source, int width, int height,
                                @NonNull Options options)
            throws IOException {
        try {
            SVG svg = SVG.getFromInputStream(source);
            return new SimpleResource<>(svg);
        } catch (SVGParseException ex) {
            throw new IOException("Cannot load SVG from stream", ex);
        }
    }
}

//————————————————
//版权声明：本文为CSDN博主「lplj717」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//原文链接：https://blog.csdn.net/lplj717/article/details/112221888