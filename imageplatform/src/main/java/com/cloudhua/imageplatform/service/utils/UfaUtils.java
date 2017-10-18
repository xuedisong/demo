package com.cloudhua.imageplatform.service.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloudhua.imageplatform.service.log.Logger;
import com.cloudhua.imageplatform.service.exception.LogicalException;
import com.cloudhua.imageplatform.service.exception.StatusCode;
import com.cloudhua.imageplatform.service.exception.UnInitializeException;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.http.Consts.UTF_8;

/**
 * Created by yangchao on 2017/8/17.
 * 文件上传下载操作
 */
public class UfaUtils {

    public static final String ZERO_POSTSHA1 = "2jmj7l5rSw0yVb_vlWAYkK_YBwk";
    private static String uctrAddr;//网盘地址

    private static String uctrAccessId;//进入的ID

    private static String ucctrAccessSec;//进入的秒数

    private static UfaUtils ufa;//工具实例

    private static Logger logger = Logger.getInst(UfaUtils.class);

    private UfaUtils(String ufaCenter, String ufaAccessId, String ufaAccessSec) {
        UfaUtils.uctrAddr = ufaCenter;
        UfaUtils.uctrAccessId = ufaAccessId;
        UfaUtils.ucctrAccessSec = ufaAccessSec;
    }

    public static synchronized void init(String ufaCenter, String ufaAccessId, String ufaAccessSec) {
        if (ufa == null) {
            ufa = new UfaUtils(ufaCenter, ufaAccessId, ufaAccessSec);
        }
    }

    //以上是单例模式

    public UfaUtils getInst() throws UnInitializeException {
        if (ufa == null) {
            throw new UnInitializeException(StatusCode.UFA_NOT_INIT, StatusCode.UFA_NOT_INIT);
        }
        return ufa;
    }

    /**
     * 请求下载文件，获取文件链接以Range头的方式支持断点续传
     * @param stor 文件stor值
     * @param size 文件大小
     * @param name 文件名字
     * @return 文件下载链接，出现错误返回null
     */
    public static String getDownloadUrl(String stor, long size, String name) {//获得文件下载的url
        logger.debug("getDownloadUrl method params. stor:" + stor + " size:" + size + " name:" + name + " uctrAddr:" + uctrAddr + " uctrAccessId:" + uctrAccessId + " ucctrAccessSec:" + ucctrAccessSec);
        Map<String, Object> requestparams = new HashMap<>();
        requestparams.put("bucket", "default");
        requestparams.put("pos", 0);
        requestparams.put("len", size);//文件大小
        requestparams.put("key", stor);
        requestparams.put("directUrl", true);
        requestparams.put("name", name);//文件名
        String authStr = getAuthString("requestDownload", requestparams, uctrAccessId, ucctrAccessSec);//获得验证字符串
        //www.pan.cloudhua.com/requestDownload?auth=stor&bucket="default'&key=stor&directUrl=true&pos=0&len=100&name=lixin
        String url = String.format("%s/requestDownload?auth=%s&bucket=%s&key=%s&directUrl=%s&pos=0&len=%s&name=%s", uctrAddr, authStr, "default", stor, true, size, name);
        String respStr = HttpUtils.postJsonData(url, null);
        JSONObject respJson = JSONObject.parseObject(respStr);
        if (respJson.getString("stat").equalsIgnoreCase("OK")) {
            String respUrl = respJson.getString("downloadUrl");
            logger.debug("getDownloadUrl method return. respUrl:" + respUrl);
            return respUrl;
        } else {
            logger.error("request uctr return stat error. respJson:" + respJson + " stor:" + stor);
            return null;
        }
    }

    /**
     * 不判断秒传直接上传文件到ufa中
     * @param clientIp 客户端ip
     * @param content 文件内容
     * @param fileName 文件名字
     * @return 文件stor值， 出现异常信息是返回null
     * @throws IOException 操作文件流出现或者网络请求错误
     */
    public static String uploadFile(String clientIp, byte[] content, String fileName) throws IOException {//
        logger.debug("uploadFile method params. content len:" + content.length + " fileName:" + fileName + " uctrAddr:" + uctrAddr + " uctrAccessId:" + uctrAccessId + " ucctrAccessSec:" + ucctrAccessSec);
        //1. 请求上传
        Map<String, Object> requestparams = new HashMap<>();
        requestparams.put("bucket", "default");
        requestparams.put("cip", clientIp);
        requestparams.put("size", content.length);
        String authStr = getAuthString("requestUpload", requestparams, uctrAccessId, ucctrAccessSec);
        if (authStr == null) {
            return null;
        }
        String requestUploadUrl = uctrAddr + "/requestUpload?auth=" + authStr + "&bucket=default&size=" + content.length + "&cip=" + clientIp;
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("X-Forwarded-For", clientIp);
        String resp = HttpUtils.postJsonData(requestUploadUrl, null, headers);
        if (resp == null) {
            // 请求失败
            return null;
        }
        JSONObject respJson  = JSONObject.parseObject(resp);
        String stat = respJson.getString("stat");
        String fileUploadId = null;
        String nodeAddr = null;
        if (stat.equalsIgnoreCase("OK")) {
            fileUploadId = respJson.getString("fileUploadId");
            nodeAddr = respJson.getJSONArray("nodes").getJSONObject(0).getString("addr");
        } else {
            logger.error("request uctr return stat error. resp:" + resp + " fileName:" + fileName);
            return null;
        }

        if (fileUploadId == null || nodeAddr == null) {
            return null;
        }

        //2. 采用formUpload上传文件内容
        String uploadUrl = nodeAddr + "/formUpload?fileUploadId=" + fileUploadId;
        try (CloseableHttpClient httpClient = HttpClients.custom().build()) {
            HttpPost httpPost = new HttpPost(uploadUrl);
            StringBody sizeBody = new StringBody(String.valueOf(content.length), ContentType.create("text/plain", UTF_8));
            File file = new File(fileName);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(content);
            }
            FileBody fileBody = new FileBody(file);
            HttpEntity httpEntity = MultipartEntityBuilder.create()
                    .addPart("size", sizeBody)
                    .addPart("file", fileBody)
                    .build();
            httpPost.setEntity(httpEntity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            String respStr = EntityUtils.toString(httpResponse.getEntity());
            respJson = JSONObject.parseObject(respStr);
            if (respJson.getString("stat").equalsIgnoreCase("OK")) {
                // 上传成功，获取commitUploadId
                JSONArray commitUploadIds = respJson.getJSONArray("partCommitIds");

                //3. commit上传文件
                String commitUrl = uctrAddr + "/commit";
                JSONObject commitJson = new JSONObject();
                commitJson.put("fileUploadId", fileUploadId);
                commitJson.put("partCommitIds", commitUploadIds);
                String commitRespStr = HttpUtils.postJsonData(commitUrl, commitJson);
                JSONObject commitRespJson = JSONObject.parseObject(commitRespStr);
                if (commitRespJson.getString("stat").equalsIgnoreCase("OK")) {
                    String stor = commitRespJson.getString("x_etag");
                    logger.debug("uploadFile method return. stor:" + stor);
                    return stor;
                } else {
                    logger.error("upload commit file to uctr error. fileUploadId:" + fileUploadId + " fileName:" + fileName);
                    return null;
                }
            } else {
                logger.error("upload file to unode error. fileUploadId:" + fileUploadId + " fileName:" + fileName);
                return null;
            }
        } catch (IOException e) {
            logger.error("get http client error. fileName:" + fileName, e);
        }

        return null;
    }

    private static String getAuthString(String apiName, Map<String, Object> params, String uctrAccessId, String ucctrAccessSec){
        long expireTime = System.currentTimeMillis() / 1000 + 7 * 24 * 3600;// 7天过期
        String[] paramNames = params.keySet().toArray(new String[0]);
        Arrays.sort(paramNames);
        StringBuilder sb = new StringBuilder();
        for (String paramName : paramNames) {
            if (paramName.equalsIgnoreCase("auth"))
                continue;

            if (sb.length() != 0) {
                sb.append("\n");
            }

            sb.append(paramName);
            sb.append("=");
            sb.append(params.get(paramName));
        }
        String strToSgin = apiName + "\n" + expireTime + "\n2jmj7l5rSw0yVb_vlWAYkK_YBwk\n" + sb.toString();
        byte[] keyBytes = ucctrAccessSec.getBytes();
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");

        // Get an hmac_sha1 Mac instance and initialize with the signing key
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
        } catch (Exception e) {
            return null;
        }

        // Compute the hmac on input data bytes
        byte[] rawHmac = mac.doFinal(strToSgin.getBytes());

        String sign = Base64.encodeBase64URLSafeString(rawHmac);
        String authStr = "v1." + uctrAccessId + "." + expireTime + "." + sign;
        return authStr;
    }

    /**
     * 请求上传文件
     * @param clientIp 客户ip
     * @param fileName 上传文件名字
     * @param fileSize 上传文件大小
     * @param partsInfo 上传文件分块信息(不传此参数标示不检查文件是否秒传)
     * @return 返回JSONObject对象，包含信息有
     * stat: 接口请求状态 'OK'请求成功 其他请求出现异常 <br />
     * existed: 文件是否已经存在 true存在 false不存在 <br />
     *     existed=false时有效参数
     *          nodes：JSONArray数据标示上传节点地址
     *          fileUploadId：上传服务ID
     *     existed=true时有效参数
     *          sign：签名信息
     *          x_size、x_key、x_etag：检验签名用，其中x_etag标示文件stor值
     */
    public JSONObject requestUpload(String clientIp, String fileName, long fileSize, List<Map<String, Object>> partsInfo) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 获取bucket
        String bucket = "default";
        map.put("bucket", bucket);

        String apiMethod = "requestUpload";
        String postSha1 = null;
        if (partsInfo != null && partsInfo.size() > 0) {
            JSONObject jsonReq = new JSONObject();
            jsonReq.put("partsInfo", partsInfo);
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                md.update(jsonReq.toString().getBytes());
                postSha1 = Base64.encodeBase64URLSafeString(md.digest());
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        map.put("cip", clientIp);
        map.put("size", fileName);
        String authStr = getAuthString(apiMethod, map, uctrAccessId, ucctrAccessSec);
        String requestUploadUrl = uctrAddr + "/requestUpload?auth=" + authStr + "&bucket=default&size=" + fileSize + "&cip=" + clientIp;
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("X-Forwarded-For", clientIp);
        JSONObject jsonReq = new JSONObject();
        jsonReq.put("partsInfo", partsInfo);
        String resp = HttpUtils.postJsonData(requestUploadUrl, jsonReq, headers);
        if (resp == null) {
            // 请求失败
            return null;
        }

        return JSONObject.parseObject(resp);
    }

    /**
     *  提交上传文件
     * @param clientIp 客户端ip
     * @param fileUploadId 文件上传任务ID
     * @param partCommitIds 文件提交信息ID
     * @return 文件stor值
     * @throws LogicalException 提交过程中与ufa相关错误
     */
    public String commitUpload(String clientIp, String fileUploadId, List<String> partCommitIds) throws LogicalException {
        String url = uctrAddr + "/commit";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("X-Forwarded-For", clientIp);
        JSONObject jsonReq = new JSONObject();
        jsonReq.put("fileUploadId", fileUploadId);
        jsonReq.put("partCommitIds", partCommitIds);
        String resp = HttpUtils.postJsonData(url, jsonReq, headers);
        if (resp == null) {
            // 请求失败
            throw new LogicalException(StatusCode.UFA_COMMIT_SERVER_EXCEPTION, "request ufa server has no response.");
        }

        JSONObject jsonObj = JSONObject.parseObject(resp);
        if (jsonObj == null)
            throw new LogicalException(StatusCode.UFA_COMMIT_SERVER_EXCEPTION, "No json Object, Please check the UFA center service.");

        String stat = jsonObj.getString("stat");
        if (stat == null)
            throw new LogicalException(StatusCode.UFA_COMMIT_SERVER_EXCEPTION, "No stat, Please check the UFA center service.");

        if (!stat.equals("OK"))
            throw new LogicalException(StatusCode.UFA_COMMIT_SERVER_EXCEPTION, "ufa commit response is not 'OK'. commit failed.");

        String x_key = jsonObj.getString("x_key");
        if (x_key == null)
            throw new LogicalException(StatusCode.UFA_COMMIT_SERVER_EXCEPTION, "No x_key, Please check the UFA center API.");

        Long x_size = jsonObj.getLong("x_size");
        if (x_size == null)
            throw new LogicalException(StatusCode.UFA_COMMIT_SERVER_EXCEPTION, "No x_size, Please check the UFA center API.");

        String x_etag = jsonObj.getString("x_etag");
        if (x_etag == null)
            throw new LogicalException(StatusCode.UFA_COMMIT_SERVER_EXCEPTION, "No x_etag, Please check the UFA center API.");

        String sign = jsonObj.getString("sign");
        if (sign == null)
            throw new LogicalException(StatusCode.UFA_COMMIT_SERVER_EXCEPTION, "No sign, Please check the UFA center API.");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x_key", x_key);
        params.put("x_etag", x_etag);
        params.put("x_size", x_size);
        if (!checkSign(sign, "commit", params))
            throw new LogicalException(StatusCode.UFA_COMMIT_SERVER_EXCEPTION, "Invalid sign.");
        return x_etag;
    }

    private static boolean checkSign(String sign, String apiMethod, Map<String, Object> params) {
        if (sign == null || sign.length() < 1 || !sign.startsWith("r1.")) {
            return false;
        }

        // Split auth string
        String[] fields = sign.split("\\.");
        if (fields.length != 4) {
            return false;
        }

        // Check accessId
        if (!uctrAccessId.equals(fields[1])) {
            return false;
        }

        // Check expires
        String expireStr = fields[2];
        long expires;
        try {
            expires = Long.parseLong(expireStr);
        } catch (NumberFormatException e) {
            return false;
        }
        long now = System.currentTimeMillis() / 1000;
        if (expires < now)
            return false;

        // Calc signature
        StringBuilder sb = new StringBuilder();
        sb.append(apiMethod);
        sb.append("\n");
        sb.append(expireStr);
        sb.append("\n");

        String[] paramNames = params.keySet().toArray(new String[0]);
        Arrays.sort(paramNames);
        StringBuilder paramsStrBuilder = new StringBuilder();
        for (String paramName : paramNames) {
            if (paramName.equalsIgnoreCase("auth"))
                continue;

            if (paramsStrBuilder.length() != 0) {
                paramsStrBuilder.append("\n");
            }

            paramsStrBuilder.append(paramName);
            paramsStrBuilder.append("=");
            paramsStrBuilder.append(params.get(paramName));
        }


        sb.append(paramsStrBuilder.toString());
        String strToSign = sb.toString();
        byte[] keyBytes = ucctrAccessSec.getBytes();
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");

        // Get an hmac_sha1 Mac instance and initialize with the signing key
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
        } catch (Exception e) {
            return false;
        }

        // Compute the hmac on input data bytes
        byte[] rawHmac = mac.doFinal(strToSign.getBytes());
        String serverSign = Base64.encodeBase64URLSafeString(rawHmac);

        String clientSign = fields[3];
        if (!serverSign.equals(clientSign)) {
            return false;
        }

        return true;
    }
}