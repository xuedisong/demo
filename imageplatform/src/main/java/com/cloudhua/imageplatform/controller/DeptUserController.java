package com.cloudhua.imageplatform.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloudhua.imageplatform.domain.HttpEnv;
import com.cloudhua.imageplatform.persistence.DeptMapper;
import com.cloudhua.imageplatform.service.exception.LogicalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门中用户的增删改查操作
 * Created by yangchao on 2017/8/19.
 */
@RestController
@RequestMapping("/deptuser")
public class DeptUserController {

    @Autowired
    private DeptMapper deptMapper;

    /**
     * 将用户添加到部门
     * @param httpEnv
     * @param requestBody
     * @throws IOException
     * @throws LogicalException
     */
    @RequestMapping(method = RequestMethod.POST)
    public void insert(HttpEnv httpEnv, @RequestBody byte[] requestBody) throws IOException, LogicalException {
        List<Map<String,String>> DeptUserList = httpEnv.paramGetList("DeptUserId", true, true, Map.class);
        for (Map<String,String> id : DeptUserList) {
            Long did = Long.valueOf((String) id.get("did"));
            Long uid = Long.valueOf((String) id.get("uid"));
            deptMapper.addDeptUser(did, uid);
        }
    }

    /**
     * 删除一个部门中的所有用户
     * @param httpEnv
     * @param requestBody
     * @throws IOException
     * @throws LogicalException
     */
    @RequestMapping(value = "/dept", method = RequestMethod.DELETE)
    public void deleteByDid(HttpEnv httpEnv, @RequestBody byte[] requestBody) throws IOException, LogicalException {
        long did = httpEnv.paramGetLong("did", false);
        deptMapper.deleteDeptUsers(did);
    }

    /**
     * 将一个用户从所有部门中移除
     * @param httpEnv
     * @param requestBody
     * @throws IOException
     * @throws LogicalException
     */
    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public void deleteByUid(HttpEnv httpEnv, @RequestBody byte[] requestBody) throws IOException, LogicalException {
        long uid = httpEnv.paramGetLong("uid", false);
        deptMapper.deleteUserDepts(uid);
    }

    /**
     * 删除一个部门下的一个用户
     * @param httpEnv
     * @param requestBody
     * @throws IOException
     * @throws LogicalException
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(HttpEnv httpEnv, @RequestBody byte[] requestBody) throws IOException, LogicalException {
        long did = httpEnv.paramGetLong("did", false);
        long uid = httpEnv.paramGetLong("uid", false);
        deptMapper.deleteDeptUser(did, uid);
    }

    /**
     * 查询一个部门下的所有用户ID
     * @param did 部门ID
     * @param httpEnv
     * @throws IllegalAccessException
     * @throws IOException
     */
    @RequestMapping(value = "/did/{did}", method = RequestMethod.GET)
    public void getByDid(@PathVariable(name = "did") Long did, HttpEnv httpEnv) throws IllegalAccessException, IOException {
        List userIds = deptMapper.getDeptUserIds(did);
        Map<String,Object> limitedInfo = new HashMap<String, Object>();
        limitedInfo.put("userIds", userIds);
        String resp = JSONObject.toJSONString(limitedInfo);
        HttpServletResponse response = httpEnv.getResponse();
        response.getWriter().write(resp);
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     * 查询一个用户所在的多个部门
     * @param uid 用户ID
     * @param httpEnv
     * @throws IllegalAccessException
     * @throws IOException
     */
    @RequestMapping(value = "/uid/{uid}", method = RequestMethod.GET)
    public void getByUid(@PathVariable(name = "uid") Long uid, HttpEnv httpEnv) throws IllegalAccessException, IOException {
        List deptIds = deptMapper.getUserDeptIds(uid);
        Map<String, Object> limitedInfo = new HashMap<String, Object>();
        limitedInfo.put("deptIds", deptIds);
        String resp = JSONObject.toJSONString(limitedInfo);
        HttpServletResponse response = httpEnv.getResponse();
        response.getWriter().write(resp);
        response.getWriter().flush();
        response.getWriter().close();
    }

}
