package com.meiling.oms;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;

public class DesUtils {

    private final static String HEX = "0123456789ABCDEF";
    private final static String TRANSFORMATION = "DES/CBC/PKCS5Padding";//DES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
//    private final static String IVPARAMETERSPEC = "";////初始化向量参数，AES 为16bytes. DES 为8bytes.
    private final static byte[] IVPARAMETERSPEC = {1, 2, 3, 4, 5, 6, 7, 8};////初始化向量参数，AES 为16bytes. DES 为8bytes.
    private final static String ALGORITHM = "DES";//DES是加密方式
    private static final String SHA1PRNG = "SHA1PRNG";//// SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法
    private static String key = "9588028820109132570743325311898426347857298773549468758875018579537757772163084478873699447306034466200616411960574122434059469100235892702736860872901247123456";
    /*
     * 生成随机数，可以当做动态的密钥 加密和解密的密钥必须一致，不然将不能解密
     */
    public static String generateKey() {
        try {
            SecureRandom localSecureRandom = SecureRandom.getInstance(SHA1PRNG);
            byte[] bytes_key = new byte[20];
            localSecureRandom.nextBytes(bytes_key);
            String str_key = toHex(bytes_key);
            return str_key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //二进制转字符
    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    // 对密钥进行处理
//    private static Key getRawKey(String key) throws Exception {
//        KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
//        //for android
//        SecureRandom sr = null;
//        // 在4.2以上版本中，SecureRandom获取方式发生了改变
//        if (android.os.Build.VERSION.SDK_INT >= 17) {
//            sr = SecureRandom.getInstance(SHA1PRNG, "Crypto");
//        } else {
//            sr = SecureRandom.getInstance(SHA1PRNG);
//        }
//        // for Java
//        // secureRandom = SecureRandom.getInstance(SHA1PRNG);
//        sr.setSeed(key.getBytes());
//        kgen.init(64, sr); //DES固定格式为64bits，即8bytes。
//        SecretKey skey = kgen.generateKey();
//        byte[] raw = skey.getEncoded();
//        return new SecretKeySpec(raw, ALGORITHM);
//    }

    // 对密钥进行处理
    private static Key getRawKey(String key) throws Exception {
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        return keyFactory.generateSecret(dks);
    }

    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     *  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     */
    public static String encode(String data) {
        return encode(key, data.getBytes(StandardCharsets.UTF_8));
    }

    private static final String transformation = "DES/CBC/PKCS5Padding";
    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     */
    public static String encode(String key, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            IvParameterSpec iv = new IvParameterSpec(IVPARAMETERSPEC);
            cipher.init(Cipher.ENCRYPT_MODE, getRawKey(key), iv);
            byte[] bytes = cipher.doFinal(data);
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
            return null;
        }
    }
    private static byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};
    /**
     * 加密
     */
    public static String encrypt(String text) {
        byte[] datasource = null;
        datasource = text.getBytes(StandardCharsets.UTF_8);
        try {
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            // SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance(transformation);
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, zeroIv);
            // 现在，获取数据并加密
            // 正式执行加密操作
            return Base64.encodeToString(cipher.doFinal(datasource), Base64.NO_WRAP);

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取编码后的值
     *
     * @param key
     * @param data
     * @return
     */
    public static String decode(String key, String data) {
        return decode(key, Base64.decode(data, Base64.DEFAULT));
    }

    /**
     * DES算法，解密
     *
     * @param data 待解密字符串
     * @param key  解密私钥，长度不能够小于8位
     * @return 解密后的字节数组
     */
    public static String decode(String key, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            IvParameterSpec iv = new IvParameterSpec(IVPARAMETERSPEC);
            cipher.init(Cipher.DECRYPT_MODE, getRawKey(key), iv);
            byte[] original = cipher.doFinal(data);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            return null;
        }
    }


    public static void main(String[] args) {
        System.out.println( encrypt("123456"));
        System.out.println(decode(key, "9vWr+4qVy78="));
    }
}