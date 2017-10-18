package com.cloudhua.imageplatform.service.exception;

import com.alibaba.fastjson.JSONObject;
import com.cloudhua.imageplatform.service.log.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Created by yangchao on 2017/8/19.
 * 服务端返回客户端错误请求信息
 */
public class RespJsonErrorMsg {

    private static JSONObject respDescritionJson;

    private static Logger logger = Logger.getInst(RespJsonErrorMsg.class);

    // 加载返回错误配置文件
    static {
        tryReloadJson();
    }

    public static void sendErrorMsg(String language,  HttpServletResponse httpServletResponse, String errorStatCode) throws IOException {
        sendErrorMsg(500, language, httpServletResponse, errorStatCode);
    }

    public static void sendErrorMsg(int httpCode, String language,  HttpServletResponse httpServletResponse, String errorStatCode) throws IOException {
        String errorMsg;

        boolean reloadOk = true;
        if (respDescritionJson == null) {
            reloadOk = tryReloadJson();
        }
        if (reloadOk) {
            JSONObject languageJson = respDescritionJson.getJSONObject(language);

            if (languageJson == null) {
                // 没有配置该类型语言，默认返回errorStatCode
                errorMsg = errorStatCode;
            } else {
                String tmp = languageJson.getString("errorStatCode");
                if (tmp == null) {
                    errorMsg = errorStatCode;
                } else {
                    errorMsg = tmp;
                }
            }
        } else {
            errorMsg = errorStatCode;
        }
        httpServletResponse.setStatus(httpCode);
        try (PrintWriter pw = httpServletResponse.getWriter()) {
            JSONObject jsonResp = new JSONObject();
            jsonResp.put("status", httpCode);
            jsonResp.put("error", errorStatCode);
            jsonResp.put("message", errorMsg);
            pw.write(jsonResp.toJSONString());
            pw.flush();
        }
    }

    public static boolean tryReloadJson() {
        try {
            ClassPathResource resource = new ClassPathResource("/RespDescription.json");
            if (resource.isReadable()) {
                try (InputStream is = resource.getInputStream()) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    IOUtils.copy(is, baos);
                    String jsonString = new String(baos.toByteArray(), "UTF-8");
                    respDescritionJson = JSONObject.parseObject(jsonString);
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("load RespDescrition.json failed.", e);
            return false;
        }
        return true;
    }
}
