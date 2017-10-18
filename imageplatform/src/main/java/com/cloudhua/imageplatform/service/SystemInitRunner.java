//JDBC连接到数据库，并执行初始化的建表操作
package com.cloudhua.imageplatform.service;

import com.cloudhua.imageplatform.persistence.DataBaseInitMapper;
import com.cloudhua.imageplatform.service.exception.LogicalException;
import com.cloudhua.imageplatform.service.log.Logger;
import com.cloudhua.imageplatform.service.exception.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by yangchao on 2017/8/17.
 */
@Component
public class SystemInitRunner implements CommandLineRunner{//系统初始运行类*********Com==

    private static Logger logger = Logger.getInst(SystemInitRunner.class);//日志

    @Autowired
    private DataBaseInitMapper dataBaseInitMapper;

    @Value("${jdbc.driver}")//获得配置项的内容
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.db}")
    private String db;

    @Value("${jdbc.params}")
    private String params;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    public void initSystem() throws Exception {//初始化系统就是初始化数据库
        initDB();
    }

    private void initDB() throws Exception {//初始化数据库
        long serverVersion = getServerVersion();//获得服务器版本
        Class clazz = Class.forName(driver);//反射原理，根据类的完全包名来加载该类——————————————————driver?—————————配置项内容
        try (Connection conn = DriverManager.getConnection(url + "?" + params, username, password)) {//连接数据库的三个参数：url?params,username,password
            String sql = "SELECT COUNT(*) FROM information_schema.SCHEMATA where SCHEMA_NAME='imageplatform'";//查询行数sql语句
            ResultSet rs = conn.prepareStatement(sql).executeQuery();//执行sql语句的结果
            if (!rs.next()) {
                throw new LogicalException("", "");
            }
            int db = rs.getInt(1);
            if (db <= 0 || dataBaseInitMapper.getTableCount() <= 0) {
                // 初始化数据库
                initTable(conn);
            } else {
                // 更新数据
                updateTable(conn, serverVersion);
            }

            if (!this.isDBDataInit(conn)) {
                this.createInitData(conn);
            }
        }
        // 更新数据库完成，设置serverVersion
        dataBaseInitMapper.updateConfig("dbversion", String.valueOf(serverVersion));

    }

    // 检查数据库是否已经初始化过了，这里暂时简单检查是否存在superAdmin用户
    private boolean isDBDataInit(Connection connection) throws SQLException {
        String sql = "SELECT * FROM `imageplatform`.`user` WHERE `name` = 'superAdmin'";
        ResultSet rs = connection.createStatement().executeQuery(sql);
        if (rs.next())
            return true;
        else
            return false;
    }

    private void createInitData(Connection connection) throws LogicalException, IOException, SQLException {//创建初始数据
        Resource resource = new ClassPathResource("/database/mysql-init.sql");//资源路径
        if (!resource.exists()) {
            throw new LogicalException(StatusCode.RESOURCE_NOT_FOUND, "classpath:/database/mysql-init.sql file not found.");
        }
        try (InputStream is = resource.getInputStream()) {//如果资源存在,resource过于庞大时，BufferRead
            try (InputStreamReader isr = new InputStreamReader(is)) {
                try (BufferedReader reader = new BufferedReader(isr)) {
                    String line;
                    connection.createStatement().execute("USE `imageplatform`;");
                    while ((line = reader.readLine()) != null) {
                        if (!line.trim().equals("") && !line.startsWith("#")) {
                            try {
                                connection.setAutoCommit(false);//先将事务设为手动提交
                                connection.createStatement().execute(line);//执行sql语句
                                connection.commit();
                            } catch (Exception e) {
                                logger.error("execute sql error. sql:" + line, e);
                                connection.rollback();//roll back due to exception occured
                            }
                        }
                    }
                }
            }
        }
    }

    private void updateTable(Connection connection, long serverVersion) throws Exception {//更新表，其实就是执行sql文件———updata.sql
        Resource resource = new ClassPathResource("/database/mysql-update.sql");//资源路径
        if (!resource.exists()) {
            throw new LogicalException(StatusCode.RESOURCE_NOT_FOUND, "classpath:/database/mysql-update.sql file not found.");
        }
        Long currentDBVersion = Long.parseLong(dataBaseInitMapper.getDBVersionConfig());//当前DB版本
        try (InputStream is = resource.getInputStream()) {
            try (InputStreamReader isr = new InputStreamReader(is)) {
                try (BufferedReader reader = new BufferedReader(isr)) {
                    // sql文件格式是#+svn版本号，换行执行的sql语句，示例：
                    // # 21582
                    // ALTER TABLE `test` ADD COLUMN `id` BIGINT(20) DEFAULT NULL
                    String line;
                    long svnVersion = 0;
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("#--")) {
                            // 注释行，用于存放svn版本信息
                            try {
                                svnVersion = Long.parseLong(line.substring(3).trim());//去掉字符序列左边或右边的空格
                            } catch (Exception e) {
                                throw new LogicalException(StatusCode.GET_XSERVER_REVERSION_ERROR, StatusCode.GET_XSERVER_REVERSION_ERROR);
                            }
                            if (svnVersion > currentDBVersion && svnVersion <= serverVersion) {//svn版本号大于当前版本，或者svn版本号小于服务版本号时
                                String executeSql = reader.readLine();
                                try {
                                    connection.setAutoCommit(false);
                                    connection.createStatement().execute(executeSql);
                                    connection.commit();
                                } catch (Exception e) {
                                    logger.error("execute sql error. sql:" + executeSql, e);
                                    connection.rollback();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static long getServerVersion() throws Exception {
        // 获取当前服务端版本号，获取classPath中README.txt文件中获取当前服务端版本，如果该文件不存在则使用svn命令查看本地代码版本
        Resource resource = new ClassPathResource("/REAME.txt");
        if (!resource.exists()) {
            return getSvnReversion();
        }
        try (InputStream is = resource.getInputStream()) {
            Properties prop = new Properties();
            prop.load(is);
            String version = prop.getProperty("version");
            if (version == null) {
                // 查看本机svn信息可否获取
                return getSvnReversion();
            } else {
                try {
                    return Long.parseLong(version);
                } catch (Exception e) {
                    throw new LogicalException(StatusCode.GET_XSERVER_REVERSION_ERROR, StatusCode.GET_XSERVER_REVERSION_ERROR);
                }
            }
        }
    }

    private static long getSvnReversion() throws Exception {
        Process p = Runtime.getRuntime().exec("svn info .");
        p.waitFor();
        try (InputStream processInputStream = p.getInputStream()) {
            try (InputStreamReader isr = new InputStreamReader(processInputStream)) {
                try (BufferedReader reader = new BufferedReader(isr)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.contains("Revision:")) {
                            String reversionStr = line.substring(line.indexOf(":") + 1);
                            try {
                                return Long.parseLong(reversionStr.trim());
                            } catch (Exception e) {
                                throw new LogicalException(StatusCode.GET_XSERVER_REVERSION_ERROR, StatusCode.GET_XSERVER_REVERSION_ERROR);
                            }
                        }
                    }
                    // 找不到resversion信息抛出错误
                    throw new LogicalException(StatusCode.GET_XSERVER_REVERSION_ERROR, StatusCode.GET_XSERVER_REVERSION_ERROR);
                }
            }
        }
    }

    private void initTable(Connection connection) throws Exception {
        Resource resource = new ClassPathResource("/database/mysql-schema.sql");
        if (!resource.exists()) {
            throw new LogicalException(StatusCode.RESOURCE_NOT_FOUND, "classpath:/database/mysql-schema.sql file not found.");
        }
        try (InputStream is = resource.getInputStream()) {
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[10240];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, len));
            }

            List<String> sqlList = new ArrayList<String>();
                String[] sqlArr = sb.toString().split("(;\\s*\\r\\n)|(;\\s*\\n)");
                for (int i = 0; i < sqlArr.length; i++) {
                    String sql = sqlArr[i].replaceAll("--.*", "").trim();
                    if (!sql.equals("")) {
                        sqlList.add(sql);
                    }
                }
                for (String sql : sqlArr
                        ) {
                    connection.createStatement().execute(sql);
            }
            dataBaseInitMapper.addConfig("dbversion", "0");
        }
    }

    @Override
    public void run(String... args) throws Exception {
        initSystem();
    }
}
