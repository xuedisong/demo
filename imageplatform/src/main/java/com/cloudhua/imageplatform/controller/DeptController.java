package com.cloudhua.imageplatform.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloudhua.imageplatform.domain.Dept;
import com.cloudhua.imageplatform.domain.HttpEnv;
import com.cloudhua.imageplatform.persistence.DeptMapper;
import com.cloudhua.imageplatform.service.exception.LogicalException;
import com.cloudhua.imageplatform.service.exception.ParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门的增删改查
 * Created by yangchao on 2017/8/19.
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptMapper deptMapper;

    /**
     * 插入部门
     * @param httpEnv
     * @param requestBody
     * @throws IOException
     * @throws LogicalException
     */
    @RequestMapping(method = RequestMethod.POST)
    public void insert(HttpEnv httpEnv, @RequestBody byte[] requestBody) throws IOException, LogicalException {
        List<Dept> userList = httpEnv.paramGetList("depts", true, true, Dept.class);
        for (Dept dept : userList) {
            deptMapper.addDept(dept);
            deptMapper.setDeptCtime(dept.getDid(), System.currentTimeMillis());
            deptMapper.setDeptMtime(dept.getDid(), System.currentTimeMillis());
        }
    }

    /**
     * 删除部门
     * @param httpEnv
     * @param requestBody
     * @throws IOException
     * @throws ParamException
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteByDid(HttpEnv httpEnv, @RequestBody byte[] requestBody) throws IOException, ParamException {
        List<Long> Dids = httpEnv.paramGetList("dids", true, true, Long.class);
        for (Long Did : Dids) {
            deptMapper.deleteDept(Did);
            deptMapper.setDeptMtime(Did, System.currentTimeMillis());
        }
    }

    /**
     * 查询部门
     * @param did 部门ID
     * @param httpEnv
     * @throws IllegalAccessException
     * @throws IOException
     */
    @RequestMapping(value = "/{did}", method = RequestMethod.GET)
    public void getByDid(@PathVariable(name = "did") Long did, HttpEnv httpEnv) throws IllegalAccessException, IOException {
        Dept dept = deptMapper.getDeptByDid(did);
        Map<String,Object> limitedInfo = new HashMap<String, Object>();
        limitedInfo.put("did", dept.getDid());
        limitedInfo.put("pdid", dept.getPdid());
        limitedInfo.put("name", dept.getName());
        String resp = JSONObject.toJSONString(limitedInfo);
        HttpServletResponse response = httpEnv.getResponse();
        response.getWriter().write(resp);
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     * 修改部门名称
     * @param httpEnv
     * @param requestBody
     * @throws IOException
     * @throws LogicalException
     */
    @RequestMapping(method = RequestMethod.PUT)
    public void setByDid(HttpEnv httpEnv, @RequestBody byte[] requestBody) throws IOException, LogicalException {
        Long did = Long.valueOf(httpEnv.paramGetString("did", true));
        String name = httpEnv.paramGetString("name", false);
        Long order = httpEnv.paramGetLong("order", false);
        Dept dept = deptMapper.getDeptByDid(did);
        if (dept != null) {
            if (name != null) {
                deptMapper.setDeptName(did, name);
            }
            if (order != null) {
                deptMapper.setDeptOrder(did, order);
            }
            deptMapper.setDeptMtime(did, System.currentTimeMillis());
        } else {
            throw new ParamException("did not found!");
        }
    }

}


