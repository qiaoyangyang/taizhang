package com.meiling.common.utils;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCodeUtils {


    /**
     * 传入一个字符串，生成一个二维码
     *
     * @param str
     * @return
     */
    public static Bitmap encodeAsBitmap(String str) {
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, 600, 600);
            return barcodeEncoder.createBitmap(result);
        } catch (WriterException | IllegalArgumentException e) {
            return null;
        }
    }
}
