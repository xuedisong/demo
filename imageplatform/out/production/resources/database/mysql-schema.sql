CREATE DATABASE IF NOT EXISTS `imageplatform`;

USE `imageplatform`;

CREATE TABLE IF NOT EXISTS `sysconfig` (
  `id` BIGINT(20) AUTO_INCREMENT NOT NULL COMMENT '主键ID',
  `key` VARCHAR(255) NOT NULL COMMENT '配置项键',
  `value` VARCHAR(255) NOT NULL COMMENT '配置项值',
  `attr` VARCHAR(10249) DEFAULT NULL COMMENT '额外属性',
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`key`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `tower` (
  `towerId` BIGINT(20) AUTO_INCREMENT NOT NULL COMMENT 'tower主键',
  `name` VARCHAR(255) NOT NULL COMMENT '塔名字',
  `parentId` BIGINT(20) NOT NULL COMMENT '塔父节点ID指的是线路ID',
  `status` INT(11) NOT NULL COMMENT '表述该记录标示塔还是线路',
  `typeId` BIGINT(20) DEFAULT NULL COMMENT '塔类型ID type表的逻辑外键',
  `owerId` BIGINT(20) DEFAULT NULL COMMENT '该塔的拥有者ID 线路无此ID',
  `longitude` BIGINT(20) DEFAULT NULL COMMENT '塔的经度',
  `latitude` BIGINT(20) DEFAULT NULL COMMENT '塔的纬度',
  `attr` VARCHAR(10240) DEFAULT NULL COMMENT '额外属性',
  PRIMARY KEY (`towerId`) ,
  UNIQUE KEY `parent_name` (`parentId`, `name`) USING BTREE ,
  KEY `status` (`status`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `type` (
  `typeId` BIGINT(20) AUTO_INCREMENT NOT NULL COMMENT '塔类型ID',
  `name` VARCHAR(255) NOT NULL COMMENT '类型名称',
  `attr` VARCHAR(10240) DEFAULT NULL COMMENT '额外属性值',
  PRIMARY KEY (`typeId`),
  UNIQUE KEY `name` (`name`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `image` (
  `imgId` BIGINT(20) AUTO_INCREMENT NOT NULL COMMENT '图片ID',
  `name` VARCHAR(255) NOT NULL COMMENT '图片名称',
  `formatName` VARCHAR(255) NOT NULL COMMENT '格式化名字 线路+杆塔+日期',
  `size` BIGINT(20) NOT NULL COMMENT '图片存储大小',
  `length` BIGINT(20) NOT NULL COMMENT '图片长度',
  `width` BIGINT(20) NOT NULL COMMENT '图片宽度',
  `stor` VARCHAR(255) NOT NULL COMMENT '图片存储在ufa中store值',
  `stat` INT(11) NOT NULL COMMENT '图片状态 初始 审查无缺陷 审查有缺陷',
  `ctime` BIGINT(20) NOT NULL COMMENT '上传时间',
  `creator` BIGINT(20) NOT NULL COMMENT '上传者ID',
  `towerId` BIGINT(20) NOT NULL COMMENT '该图片所属的塔ID',
  `attr` VARCHAR(10240) DEFAULT NULL COMMENT '额外属性值',
  PRIMARY KEY (`imgid`),
  KEY `towerId` (`towerId`) USING BTREE,
  KEY `creator` (`creator`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `role` (
  `roleId` BIGINT(20) AUTO_INCREMENT NOT NULL COMMENT '角色ID',
  `name` VARCHAR(255) NOT NULL COMMENT '角色名称',
  `displayName` VARCHAR(255) NOT NULL COMMENT '角色客户端显示名称',
  `right` BIGINT(20) NOT NULL COMMENT '角色对应权限值',
  `attr` VARCHAR(10240) DEFAULT NULL COMMENT '额外数据字段',
  PRIMARY KEY (`roleId`) USING BTREE ,
  UNIQUE KEY `name` (`name`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `role_user` (
  `roleId` BIGINT(20) NOT NULL COMMENT '角色ID',
  `uid` BIGINT(20) NOT NULL COMMENT '用户ID',
  `attr` VARCHAR(10240) DEFAULT NULL COMMENT '额外数据字段',
  UNIQUE KEY `rolId_uid` (`roleId`, `uid`) USING BTREE ,
  KEY `rightId` (`uid`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `token` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'token表主键ID',
  `token` VARCHAR(255) NOT NULL COMMENT '用户登录令牌',
  `uid` BIGINT(20) unsigned NOT NULL COMMENT '用户ID',
  `expires` BIGINT(20) NOT NULL COMMENT 'token过期时间时间戳精确到毫秒',
  `ctime` BIGINT(20) NOT NULL COMMENT 'token创建时间时间戳毫秒',
  `clientIp` VARCHAR(64) NOT NULL COMMENT '登录客户端IP',
  `agent` VARCHAR(255) DEFAULT NULL COMMENT '代理',
  `device` VARCHAR(64) DEFAULT NULL COMMENT '登录设备类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `token` (`token`) USING BTREE ,
  KEY `uid` (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `user` (
  `uid` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `name` VARCHAR(255) NOT NULL COMMENT '用户登录名',
  `nickName` VARCHAR(255) DEFAULT NULL COMMENT '用户展示名',
  `email` VARCHAR(255) DEFAULT NULL COMMENT '用户邮箱',
  `phone` VARCHAR(255) DEFAULT NULL COMMENT '用户手机号码',
  `password` VARCHAR(255) NOT NULL COMMENT '用户登录密码',
  `position` INT(11) DEFAULT 0 COMMENT '用户职位ID',
  `status` INT(11) NOT NULL COMMENT '用户状态',
  `order` BIGINT(20) DEFAULT NULL COMMENT '排序值',
  `attr` VARCHAR(10240) DEFAULT NULL COMMENT '额外扩展属性',
  `ctime` BIGINT(20) DEFAULT NULL COMMENT '创建时间',
  `mtime` BIGINT(20) DEFAULT NULL COMMENT '信息修改时间',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uk_n` (`name`) USING BTREE,
  KEY `uk_e` (`email`) USING BTREE,
  KEY `uk_p` (`phone`) USING BTREE,
  KEY `status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `position` (
  `position` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '职位ID',
  `name` VARCHAR(255) NOT NULL COMMENT '职位名字',
  `attr` VARCHAR(10240) DEFAULT NULL COMMENT '扩展属性',
  PRIMARY KEY (`position`),
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `dept` (
  `did` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `pdid` bigint(20) NOT NULL COMMENT '父部门ID',
  `name` varchar(255) NOT NULL COMMENT '部门名称',
  `order` BIGINT(20) DEFAULT NULL COMMENT '排序值',
  `status` INT(11) NOT NULL COMMENT '部门状态',
  `attr` varchar(10240) DEFAULT NULL COMMENT '额外扩展属性',
  `ctime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `mtime` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`did`),
  KEY `status` (`status`) USING BTREE ,
  UNIQUE KEY `uk_p_n` (`pdid`,`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `dept_user` (
  `did` bigint(20) NOT NULL COMMENT '部门ID',
  `uid` bigint(20) NOT NULL COMMENT '用户ID',
  UNIQUE KEY `uk_d_u` (`did`,`uid`) USING BTREE,
  KEY `norm_u` (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



