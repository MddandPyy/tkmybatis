package com.mybatis.tkmybatis.test;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class Aestest {
    private String strDefaultKey = "ysyj-20190710123";
    private byte[] key;
    private static final String AES = "AES";
    private static final String AES_CBC = "AES/CBC/PKCS5Padding";
    private static final String HMACSHA1 = "HmacSHA1";
    private static final int DEFAULT_HMACSHA1_KEYSIZE = 160;
    private static final int DEFAULT_AES_KEYSIZE = 128;
    private static final int DEFAULT_IVSIZE = 16;
    private static SecureRandom random = new SecureRandom();

    public Aestest() {
        this.key = this.strDefaultKey.getBytes();
    }

    public Aestest(String strDefaultKey) {
        this.key = this.strDefaultKey.getBytes();
        this.strDefaultKey = strDefaultKey;
        this.key = strDefaultKey.getBytes();
    }

    public static byte[] hmacSha1(byte[] input, byte[] key) throws Exception {
        try {
            SecretKey secretKey = new SecretKeySpec(key, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKey);
            return mac.doFinal(input);
        } catch (GeneralSecurityException var4) {
            var4.printStackTrace();
            //throw new BusinessException("1101,使用HMAC-SHA1进行消息签名出错：", var4);
            throw new Exception();
        }
    }

    public static boolean isMacValid(byte[] expected, byte[] input, byte[] key) throws Exception {
        byte[] actual = hmacSha1(input, key);
        return Arrays.equals(expected, actual);
    }

    public static byte[] generateHmacSha1Key() throws Exception {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA1");
            keyGenerator.init(160);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (GeneralSecurityException var2) {
            var2.printStackTrace();
            //throw new BusinessException("1101,生成HMAC-SHA1密钥出错：", var2);
            throw new Exception();
        }
    }

    public byte[] aesEncrypt(byte[] input) throws Exception {
        if (this.key.length != 16) {
            throw new Exception("Key长度不是16位");
        } else {
            return aes(input, this.key, 1);
        }
    }

    public String aesEncrypt(String input) throws Exception {
        try {
            byte[] encryptResult = this.aesEncrypt(input.getBytes("utf-8"));
            return encodeHex(encryptResult);
        } catch (Exception var3) {
            var3.printStackTrace();
            //throw new BusinessException("1101,生成HMAC-SHA1密钥出错：", var3);
            throw new Exception();
        }
    }

    public static byte[] aesEncrypt(byte[] input, byte[] key, byte[] iv) throws Exception {
        return aes(input, key, iv, 1);
    }

    public String aesDecrypt(byte[] input) throws Exception {
        if (this.key.length != 16) {
            throw new Exception("Key长度不是16位");
        } else {
            byte[] decryptResult = aes(input, this.key, 2);
            return new String(decryptResult, "utf-8");
        }
    }

    public String aesDecrypt(String input) throws Exception {
        try {
            return this.aesDecrypt(decodeHex(input));
        } catch (Exception var3) {
            var3.printStackTrace();
           // throw new BusinessException("1101,解密出错：", var3);
            throw new Exception();
        }
    }

    public static String aesDecrypt(byte[] input, byte[] key, byte[] iv) throws Exception {
        byte[] decryptResult = aes(input, key, iv, 2);
        return new String(decryptResult, "utf-8");
    }

    private static byte[] aes(byte[] input, byte[] key, int mode) throws Exception {
        try {
            SecretKey secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(mode, secretKey);
            return cipher.doFinal(input);
        } catch (GeneralSecurityException var5) {
            var5.printStackTrace();
          //  throw new BusinessException("1101,使用AES加密或解密无编码的原始字节数组出错：", var5);
            throw new Exception();
        }
    }

    private static byte[] aes(byte[] input, byte[] key, byte[] iv, int mode) throws Exception {
        try {
            SecretKey secretKey = new SecretKeySpec(key, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(mode, secretKey, ivSpec);
            return cipher.doFinal(input);
        } catch (GeneralSecurityException var7) {
            var7.printStackTrace();
          //  throw new BusinessException("1101,使用AES加密或解密无编码的原始字节数组：", var7);
            throw new Exception();
        }
    }

    public static byte[] generateAesKey() throws Exception {
        return generateAesKey(128);
    }

    public static byte[] generateAesKey(int keysize) throws Exception {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(keysize);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (GeneralSecurityException var3) {
            var3.printStackTrace();
        //    throw new BusinessException("1102,生成AES密钥出错：", var3);
            throw new Exception();
        }
    }

    public static byte[] generateIV() {
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        return bytes;
    }

    public byte[] getKey() {
        return this.key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public static String encodeHex(byte[] input) {
        return Hex.encodeHexString(input);
    }

    public static byte[] decodeHex(String input) throws Exception {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException var2) {
        //    throw new BusinessException("1103,Hex解码出错：", var2);
            throw new Exception();
        }
    }

}
