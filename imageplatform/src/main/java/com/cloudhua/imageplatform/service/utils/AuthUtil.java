package com.cloudhua.imageplatform.service.utils;

import com.cloudhua.imageplatform.service.exception.LogicalException;
import com.cloudhua.imageplatform.service.exception.StatusCode;
import com.cloudhua.imageplatform.service.log.Logger;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by yangchao on 2017/8/22.
 * 密码验证工具类
 */
public class AuthUtil {

    private static Logger logger = Logger.getInst(AuthUtil.class);

    /**
     * 密码加密存储
     * @param password 密码明文字符串
     * @return
     * @throws LogicalException
     */
    public static String getSaltedPassword(String password) throws LogicalException {
        if (password == null) {
            throw new LogicalException(StatusCode.PARAMS_IS_NULL, StatusCode.PARAMS_IS_NULL);
        }
        Random random = new SecureRandom();
        byte[] salt = new byte[10];
        random.nextBytes(salt);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(password.getBytes());
            byte[] pswBytes = messageDigest.digest();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(pswBytes);
            baos.write(salt);
            byte[] base64 = Base64.encodeBase64(baos.toByteArray());
            baos.close();
            return "{SSHA}" + new String(base64);
        } catch (Exception e) {
            logger.error("getSaltedPassword error.", e);
            throw new LogicalException(StatusCode.PASSWORD_ENCODE_ERROR, StatusCode.PASSWORD_ENCODE_ERROR);
        }
    }

    /**
     * 检查密码是否正确
     * @param password 密码明文字符串
     * @param dbPassword 数据库存储加密密码
     * @return
     * @throws LogicalException
     */
    public static boolean checkPassword(String password, String dbPassword) throws LogicalException {
        if (!dbPassword.startsWith("{SSHA}")) {
            throw new LogicalException(StatusCode.PASSWORD_ENCODE_ERROR, "Hash not prefixed by {SSHA}; is it really a salted hash?");
        }

        // 获取dbPassword，decode，去除前6位
        byte[] data = Base64.decodeBase64(dbPassword.substring(6).getBytes());
        byte[] passwordData = new byte[data.length - 10];
        for (int i = 0; i < passwordData.length; i++) {
            passwordData[i] = data[i];
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(password.getBytes());
            byte[] pswBytes = messageDigest.digest();

            return Arrays.equals(passwordData, pswBytes);
        } catch (Exception e) {
            logger.error("getSaltedPassword error.", e);
            throw new LogicalException(StatusCode.PASSWORD_ENCODE_ERROR, StatusCode.PASSWORD_ENCODE_ERROR);
        }
    }

    public static void main(String[] args) throws LogicalException {
        String password = "e64921766e45bf8a2f45f0a9203e6756";
        String p = AuthUtil.getSaltedPassword(password);
        System.out.println("p:" + p);
    }
}
