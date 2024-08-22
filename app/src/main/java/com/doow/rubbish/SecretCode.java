package com.doow.rubbish;


import android.os.Build;
import android.util.Base64;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

//加密工具
public class SecretCode {
    public static final String ENC_SECRET = "StimulatingGame";


    public static void main(String[] args) {
//        String code = encrypt("https://h.bettigre.com");
//        String url = decrypt(code);
//        System.out.println(code);
//        System.out.println(url);

//        String code = encodeBase64("aHR0cHM6Ly9wYW9sdXoubGluay91c2VyL3Nob3A=");
//        Log.e("encodeBase64",code);
//        System.out.println(code);
    }

    //加密
    static String encrypt(String clearText) {
        try {
            DESKeySpec keySpec = new DESKeySpec(
                    ENC_SECRET.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            String encrypedPwd = Base64.encodeToString(cipher.doFinal(clearText
                    .getBytes("UTF-8")), Base64.DEFAULT);
            return encrypedPwd;
        } catch (Exception e) {
        }
        return clearText;
    }

    //解密
    static String decrypt(String encryptedPwd) {
        try {
            DESKeySpec keySpec = new DESKeySpec(ENC_SECRET.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            byte[] encryptedWithoutB64 = Base64.decode(encryptedPwd, Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] plainTextPwdBytes = cipher.doFinal(encryptedWithoutB64);
            return new String(plainTextPwdBytes);
        } catch (Exception e) {
        }
        return encryptedPwd;
    }

    /**
     * BASE64编码
     *
     * @param content 编码数据二进制
     * @return base64编码字符串
     */
    private static String encodeBase64(byte[] content) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return new String(java.util.Base64.getEncoder().encode(content));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * BASE64编码
     *
     * @param content 原始字符串
     * @return 编码后字符串
     */
    @SuppressWarnings("all")
    public static String encodeBase64(String content) {
        try {
            return encodeBase64(content.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.out.print(e.getMessage());
            return null;
        }
    }


}
