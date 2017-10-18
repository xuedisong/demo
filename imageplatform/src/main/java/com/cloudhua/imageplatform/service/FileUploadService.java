package com.cloudhua.imageplatform.service;

import com.cloudhua.imageplatform.service.exception.ParamException;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by lixin on 2017/9/18.
 */

@RestController
@RequestMapping("/fileupload")
public class FileUploadService {

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String processUpload(HttpServletRequest request, @RequestBody byte[] requestBody) throws ParamException, IOException {
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String fileLocation = "D://test";
        String fileName = "/" + name + "." + type;
        String filePath = fileLocation + fileName;
        System.out.println(filePath);
        File f = new File(filePath);
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(requestBody);
        System.out.println("Ok!");
        fos.close();
        return "upload success!";
    }

    /**
     * 将上传文件分块存储至本地磁盘目录
     *
     * @param request
     * @param requestBody
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/block", method = RequestMethod.POST)
    @ResponseBody
    public String blockUpload(HttpServletRequest request, @RequestBody byte[] requestBody) throws IOException {
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String fileLocation = "D://test" + "/" + name;
        //**
        String recoverFilePath="D://test/"+name+"."+type;
        File rf = new File(recoverFilePath);
        try {
            if (!rf.exists()) {
                //先创建文件夹所在目录
                rf.getParentFile().mkdirs();
                //创建新文件
                rf.createNewFile();
            }
            System.out.println(recoverFilePath);
        } catch (Exception e) {
            System.out.println("创建恢复文件时出现了错误。");
            e.printStackTrace();
        }
        //20kb为一个块，用"草图,png"来进行测试。
        byte[] block = new byte[20 * 1000];//20KB的数组
        ByteArrayInputStream is = new ByteArrayInputStream(requestBody);
        int count = 0;
        while (is.read(block) != -1) {
            String fileName = name + "__" + count + "." + type;
            File f = new File(fileLocation, fileName);
            try {
                if (!f.exists()) {
                    //先创建文件夹所在目录
                    f.getParentFile().mkdirs();
                    //创建新文件
                    f.createNewFile();
                }
            } catch (Exception e) {
                System.out.println("创建新文件时出现了错误。");
                e.printStackTrace();
            }
            FileOutputStream fos = new FileOutputStream(fileLocation + "/" + fileName);
            fos.write(block);
            FileOutputStream recover =new FileOutputStream(recoverFilePath);
            recover.write(block);

            //fos.close();
            count++;
        }
        return "ok";
    }

    /**
     * 下载文件
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/recover", method = RequestMethod.GET)
    @ResponseBody
    public String blockRecover(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");

        return "recover ok";
    }
}