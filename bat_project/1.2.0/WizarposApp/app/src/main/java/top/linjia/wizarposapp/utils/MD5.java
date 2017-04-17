package top.linjia.wizarposapp.utils;

import java.nio.Buffer;
import java.security.MessageDigest;

/**
 * Created by 河南巧脉信息技术 on 2016/11/3 15:39
 * 作者：陈文豪
 * 邮箱：firstwenshao@163.com
 *
 * 负责MD5加密的工具类 目前没有加盐
 */
public class MD5 {
    public static String md5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
