package io.renren.modules.us.util;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Li
 */
@Component
public class UsCryptoUtil {

    private static final String IV = "QWLKSMUHCIOGYCNG";
    private static final String KEY = "CNALSPXZMKQWDJTG";
    private static final String ALGORITHM = "AES";
    private static final String ALGORITHM_PATTERN = "AES/CBC/PKCS5Padding";
    private static final String CHARSET = "UTF-8";

    //将2进制转变为16进制
    private static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    //将16进制转变为2进制
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    //加密
    public static String encrypt(String content) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM_PATTERN);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(IV.getBytes()));
        byte[] encryptedData = cipher.doFinal(content.getBytes(CHARSET));
        return parseByte2HexStr(encryptedData);
    }

    //解密
    public static String decrypt(String content) throws Exception {
        byte[] contentBytes = parseHexStr2Byte(content);
        SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM_PATTERN);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(IV.getBytes()));
        byte[] result = cipher.doFinal(contentBytes);
        return new String(result, CHARSET);
    }
}
