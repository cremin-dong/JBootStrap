/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.47
Source Server Version : 50620
Source Host           : 192.168.1.47:3306
Source Database       : jbootstrap

Target Server Type    : MYSQL
Target Server Version : 50620
File Encoding         : 65001

Date: 2017-10-20 09:27:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for acl_resource
-- ----------------------------
DROP TABLE IF EXISTS `acl_resource`;
CREATE TABLE `acl_resource` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `parent_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT '父ID字符串',
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `href` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT '链接',
  `icon` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '图标',
  `type` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '权限类型,0：菜单，1：页面操作',
  `permission` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '权限标识',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_resource_permission_unique` (`permission`),
  KEY `sys_menu_parent_id` (`parent_id`),
  KEY `sys_menu_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单表';

-- ----------------------------
-- Records of acl_resource
-- ----------------------------
INSERT INTO `acl_resource` VALUES ('43eb6d3f-4a3c-4fdc-8f7c-548e7dloloel', null, null, '系统管理', '23', '/sys', 'fa fa-cog', '0', 'sys:view', 'xxx', '2017-08-02 15:00:21', 'xxx', '2017-08-02 15:00:23', null, '0');
INSERT INTO `acl_resource` VALUES ('795285a6-8ee3-43db-93eb-6fbf8e2d1cd9', 'a91fc95f-7be8-4483-b504-2706ef8d9eec', '43eb6d3f-4a3c-4fdc-8f7c-548e7dloloel,a91fc95f-7be8-4483-b504-2706ef8d9eec', '用户删除', '3', '/users/delete', 'fa fa-user', '1', 'sys:users:delete', 'YYYYYYYYYY', '2017-08-02 17:15:00', '0f6079c1-9225-42f9-a6cf-f7d3a61fa61c', '2017-08-16 11:36:47', null, '0');
INSERT INTO `acl_resource` VALUES ('7b27526e-ffbc-41c1-b82e-587aacc8b8f5', 'a91fc95f-7be8-4483-b504-2706ef8d9eec', '43eb6d3f-4a3c-4fdc-8f7c-548e7dloloel,a91fc95f-7be8-4483-b504-2706ef8d9eec', '用户新增&编辑', '5', '/users/save', 'fa fa-user', '1', 'sys:users:save', 'YYYYYYYYYY', '2017-08-02 17:13:47', 'YYYYYYYYYY', '2017-08-02 17:13:47', null, '0');
INSERT INTO `acl_resource` VALUES ('a91fc95f-7be8-4483-b504-2706ef8d9eec', '43eb6d3f-4a3c-4fdc-8f7c-548e7dloloel', '43eb6d3f-4a3c-4fdc-8f7c-548e7dloloel', '用户管理', '2', '/users', 'fa fa-user', '0', 'sys:users:view', 'YYYYYYYYYY', '2017-08-02 15:18:23', 'YYYYYYYYYY', '2017-08-02 15:18:23', null, '0');

-- ----------------------------
-- Table structure for acl_role
-- ----------------------------
DROP TABLE IF EXISTS `acl_role`;
CREATE TABLE `acl_role` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `description` varchar(100) COLLATE utf8_bin NOT NULL,
  `is_sys` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否系统默认（不可删除）',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_role_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色表';

-- ----------------------------
-- Records of acl_role
-- ----------------------------
INSERT INTO `acl_role` VALUES ('43eb6d3f-4a3c-4fdc-8f7c-548e7d89fbd9', 'superAdmin', '超级管理员', '1', 'xxxxxxxxxx', '2017-08-02 14:42:15', 'xxxxxxxxxx', '2017-08-02 14:42:15', null, '0');
INSERT INTO `acl_role` VALUES ('46d5d9a7-27be-4ac4-b80f-d0fc83b94238', 'fff', '测试角色', '0', '0f6079c1-9225-42f9-a6cf-f7d3a61fa61c', '2017-08-17 11:14:47', '0f6079c1-9225-42f9-a6cf-f7d3a61fa61c', '2017-08-17 11:14:47', '', '0');
INSERT INTO `acl_role` VALUES ('eb539ca1-8087-4fb9-8646-a29479ed5748', 'user', '普通用户', null, 'xxxxxxxxxx', '2017-08-02 14:43:16', '0f6079c1-9225-42f9-a6cf-f7d3a61fa61c', '2017-08-14 11:59:50', '范德萨', '0');

-- ----------------------------
-- Table structure for acl_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `acl_role_resource`;
CREATE TABLE `acl_role_resource` (
  `role_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '角色编号',
  `resource_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (`role_id`,`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色-菜单';

-- ----------------------------
-- Records of acl_role_resource
-- ----------------------------
INSERT INTO `acl_role_resource` VALUES ('43eb6d3f-4a3c-4fdc-8f7c-548e7d89fbd9', '43eb6d3f-4a3c-4fdc-8f7c-548e7dloloel');
INSERT INTO `acl_role_resource` VALUES ('43eb6d3f-4a3c-4fdc-8f7c-548e7d89fbd9', 'a91fc95f-7be8-4483-b504-2706ef8d9eec');
INSERT INTO `acl_role_resource` VALUES ('46d5d9a7-27be-4ac4-b80f-d0fc83b94238', '795285a6-8ee3-43db-93eb-6fbf8e2d1cd9');
INSERT INTO `acl_role_resource` VALUES ('46d5d9a7-27be-4ac4-b80f-d0fc83b94238', '7b27526e-ffbc-41c1-b82e-587aacc8b8f5');
INSERT INTO `acl_role_resource` VALUES ('46d5d9a7-27be-4ac4-b80f-d0fc83b94238', 'a91fc95f-7be8-4483-b504-2706ef8d9eec');
INSERT INTO `acl_role_resource` VALUES ('eb539ca1-8087-4fb9-8646-a29479ed5748', '43eb6d3f-4a3c-4fdc-8f7c-548e7dloloel');
INSERT INTO `acl_role_resource` VALUES ('eb539ca1-8087-4fb9-8646-a29479ed5748', '795285a6-8ee3-43db-93eb-6fbf8e2d1cd9');
INSERT INTO `acl_role_resource` VALUES ('eb539ca1-8087-4fb9-8646-a29479ed5748', '7b27526e-ffbc-41c1-b82e-587aacc8b8f5');
INSERT INTO `acl_role_resource` VALUES ('eb539ca1-8087-4fb9-8646-a29479ed5748', 'a91fc95f-7be8-4483-b504-2706ef8d9eec');

-- ----------------------------
-- Table structure for acl_user_role
-- ----------------------------
DROP TABLE IF EXISTS `acl_user_role`;
CREATE TABLE `acl_user_role` (
  `user_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户编号',
  `role_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户-角色';

-- ----------------------------
-- Records of acl_user_role
-- ----------------------------
INSERT INTO `acl_user_role` VALUES ('0f6079c1-9225-42f9-a6cf-f7d3a61fa61c', '43eb6d3f-4a3c-4fdc-8f7c-548e7d89fbd9');
INSERT INTO `acl_user_role` VALUES ('0f6079c1-9225-42f9-a6cf-f7d3a61fa61c', '46d5d9a7-27be-4ac4-b80f-d0fc83b94238');
INSERT INTO `acl_user_role` VALUES ('0f6079c1-9225-42f9-a6cf-f7d3a61fa61c', 'eb539ca1-8087-4fb9-8646-a29479ed5748');
INSERT INTO `acl_user_role` VALUES ('5539cd69-dd73-412e-8be1-1f540a7d8e43', 'eb539ca1-8087-4fb9-8646-a29479ed5748');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `company_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属公司',
  `office_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '归属部门',
  `username` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '登录名',
  `password` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `no` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '工号',
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `email` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `mobile` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '手机',
  `photo` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '用户头像',
  `sex` char(1) COLLATE utf8_bin DEFAULT 'm' COMMENT '性别，m:男，f:女',
  `login_ip` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `login_flag` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '是否可登录',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `create_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `del_flag` char(1) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标记：0:false,1:true',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_user_name_unique` (`username`),
  KEY `sys_user_office_id` (`office_id`),
  KEY `sys_user_login_name` (`username`),
  KEY `sys_user_company_id` (`company_id`),
  KEY `sys_user_update_date` (`update_date`),
  KEY `sys_user_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('0f6079c1-9225-42f9-a6cf-f7d3a61fa61c', null, null, 'admin', '36238535cb8ce1f4462e4b873482cc06', '11111111', 'fdsafds', 'admin@qq.com', 'fdsa', '13222222222', null, 'm', null, null, null, '', 'c5e40ebd-9c61-4634-bc34-795e9e8a70b2', '2017-08-12 16:49:03', 'c5e40ebd-9c61-4634-bc34-795e9e8a70b2', '2017-08-12 16:49:20', '0');
INSERT INTO `sys_user` VALUES ('5539cd69-dd73-412e-8be1-1f540a7d8e43', null, null, 'cremin', 'ac0f4907bbca064b42fdf92f615d3368', '10000', '23r32r23', '423423@q.com', 'r32r32', '13232333333', null, 'f', null, null, null, '', 'c5e40ebd-9c61-4634-bc34-795e9e8a70b2', '2017-08-12 16:48:07', '0f6079c1-9225-42f9-a6cf-f7d3a61fa61c', '2017-08-12 17:46:02', '0');

-- ----------------------------
-- Function structure for getSortResourceIds
-- ----------------------------
DROP FUNCTION IF EXISTS `getSortResourceIds`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `getSortResourceIds`() RETURNS text CHARSET utf8
BEGIN
	#获取排序的资源ID字符串，逗号分隔

   
 DECLARE  no_more_record INT DEFAULT 0;

 
 DECLARE  sortResourceIds text;   # 返回的有序ID 

 DECLARE  id_temp  varchar(64);  #临时资源ID
 DECLARE  child_id_temp  text;  #临时资源ID下的子资源ID



 # 查询出一级资源
 DECLARE  cur_record CURSOR FOR  
    SELECT id
    FROM acl_resource where parent_id is null ORDER BY sort;



 DECLARE  CONTINUE HANDLER FOR NOT FOUND  SET  no_more_record = 1; 

#打开游标
 OPEN  cur_record; 
 
       #循环游标
			 WHILE no_more_record =0 DO

          
					FETCH  cur_record INTO id_temp;

          #连接一级资源
          SET sortResourceIds = CONCAT_WS(',',sortResourceIds,CONCAT('\'',id_temp,'\''));

          #查询一级资源下的所有子资源ID

          SELECT
						GROUP_CONCAT(CONCAT('\'', re.id, '\'')) INTO child_id_temp
					FROM
						(
							SELECT
								id
							FROM
								acl_resource
							WHERE
								FIND_IN_SET(
										id_temp,
									parent_ids
								)
							ORDER BY
								parent_ids,
								sort
						) re;

          #连接一级资源下的所有子资源ID
          SET sortResourceIds = CONCAT(sortResourceIds,',',child_id_temp);

			 END WHILE;


 CLOSE  cur_record;  

RETURN sortResourceIds;

END
;;
DELIMITER ;
