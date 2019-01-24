package com.icezhg.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.util.Base64Utils;

/**
 * Created by zhongjibing on 2019/01/24
 */
public class DESUtil {
    private static final String ALGORITHM = "DES";
    private static final String DES_SECRET = "12345678";

    private static class EncryptCipher {
        private static Cipher instance;

        static {
            try {
                instance = Cipher.getInstance(ALGORITHM);
                instance.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(DES_SECRET.getBytes(), ALGORITHM));
            } catch (Exception ignored) {
            }
        }

        private static byte[] encrypt(byte[] bytes) throws Exception {
            return instance.doFinal(instance.doFinal(bytes));
        }
    }

    private static class DecryptCipher {
        private static Cipher instance;

        static {
            try {
                instance = Cipher.getInstance(ALGORITHM);
                instance.init(Cipher.DECRYPT_MODE, new SecretKeySpec(DES_SECRET.getBytes(), ALGORITHM));
            } catch (Exception ignored) {
            }
        }

        private static byte[] decrypt(byte[] bytes) throws Exception {
            return instance.doFinal(instance.doFinal(bytes));
        }
    }

    public static String encrypt(String str) throws Exception {
        return new String(Base64Utils.encode(EncryptCipher.encrypt(str.getBytes())));
    }

    public static String decrypt(String str) throws Exception {
        return new String(DecryptCipher.decrypt(Base64Utils.decode(str.getBytes())));
    }

}
