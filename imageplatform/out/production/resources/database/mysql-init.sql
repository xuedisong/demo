# 仅在程序第一次安装运行的时候执行此初始化数据sql
# 初始化数据
INSERT INTO `role` (`roleId`, `name`, `displayName`, `right`) VALUES (1, 'superAdmin', '超级管理员', 127);
INSERT INTO `role` (`roleId`, `name`, `displayName`, `right`) VALUES (2, 'uploader', '上传人员', 9);
INSERT INTO `role` (`roleId`, `name`, `displayName`, `right`) VALUES (3, 'distributor', '分发人员', 21);
INSERT INTO `role` (`roleId`, `name`, `displayName`, `right`) VALUES (4, 'admin', '管理员', 121);
INSERT INTO `role` (`roleId`, `name`, `displayName`, `right`) VALUES (5, 'employee', '员工', 1);
INSERT INTO `role` (`roleId`, `name`, `displayName`, `right`) VALUES (6, 'groupMember', '班组人员', 3);

# 插入超级管理员
INSERT INTO `user` (`uid`,`name`, `nickName`, `password`, `status`, `ctime`, `mtime`) VALUES (1, 'superAdmin', 'superAdmin', '{SSHA}Bf50YcYHwzIpdy1AJQVgEBan0OqEJRrv5pHwXID3', 1, 0, 0);
INSERT INTO `role_user` (`roleId`, `uid`) VALUES (1, 1);