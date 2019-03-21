alter table commuLog add type1 int(8) DEFAULT NULL COMMENT '操作类型：1,打开设备2,关闭设备3,修改密码4,设置密码失效5,修改临时密码6,设置临时密码失效7,修改用户密码8,设置用户密码失效9,设置密码10,设置卡用户11,删除用户';
alter table commuLog add type2 int(8) DEFAULT NULL COMMENT '结果类型：0，失败；1，成功';

alter table openDeviceLog add type varchar(40) DEFAULT NULL COMMENT '操作类型';
alter table openDeviceLog add type2 int(8) DEFAULT NULL COMMENT '1:用户开门；2:临时密码开门；3:用户密码开门，密码编号%s；4:网络开门；5:设备关闭；6:自定义';
alter table openDeviceLog add msg1 varchar(100) DEFAULT NULL;

alter table operateLog add msg1 varchar(1000) DEFAULT NULL COMMENT '存小区、房间等国际化所需信息';
alter table operateLog add msg2 varchar(1000) DEFAULT NULL;
alter table operateLog add msg3 varchar(1000) DEFAULT NULL;

alter table device add productor varchar(32) DEFAULT NULL;

alter table person add countrycode varchar(8) default null after id;
alter table roomprivilege add countrycode varchar(8) default null after id;

INSERT INTO city (provinceCode,cityName,cityCode,firstPinyin)VALUE('810000','香港','810100','x');
INSERT INTO city (provinceCode,cityName,cityCode,firstPinyin)VALUE('820000','澳门','820100','a');
INSERT INTO areas (cityCode,areasName,areasCode)VALUE('810100','香港岛','810101');
INSERT INTO areas (cityCode,areasName,areasCode)VALUE('810100','新界半岛','810102');
INSERT INTO areas (cityCode,areasName,areasCode)VALUE('810100','九龙','810103');
INSERT INTO areas (cityCode,areasName,areasCode)VALUE('820100','澳门半岛','820101');
INSERT INTO areas (cityCode,areasName,areasCode)VALUE('820100','氹仔','820102');
INSERT INTO areas (cityCode,areasName,areasCode)VALUE('820100','路环','820103');

CREATE TABLE `customer` (
  `customerid` int(9) NOT NULL AUTO_INCREMENT,
  `personid` bigint(20) NOT NULL COMMENT '本条客户记录属于哪个账户',
  `name` varchar(256) NOT NULL COMMENT '客户姓名',
  `sex` int(9) DEFAULT NULL COMMENT '客户性别 0：女1：男',
  `countrycode` varchar(64) DEFAULT NULL COMMENT '国际区号，默认为中国，86',
  `phonenumber` varchar(256) DEFAULT NULL COMMENT '手机号',
  `identitytype` int(9) DEFAULT NULL COMMENT '身份证件类型，默认为1 居民身份证',
  `identity` varchar(256) DEFAULT NULL COMMENT '身份证件号码',
  `mail` varchar(256) DEFAULT NULL COMMENT '邮箱',
  `title` int(9) DEFAULT NULL COMMENT '职务 1：列车长 2：乘务员',
  `address` varchar(256) DEFAULT NULL COMMENT '住址',
  `fingerprint` varchar(2048) DEFAULT NULL COMMENT '指纹信息',
  `cardnumber` varchar(256) DEFAULT NULL COMMENT '卡号',
  `password` varchar(256) DEFAULT NULL COMMENT '密码',
  `label` varchar(1024) DEFAULT NULL COMMENT '标签',
  `customerinfo` varchar(2048) DEFAULT NULL COMMENT '客户信息组合，方便检索',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`customerid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `customer` */

CREATE TABLE `systemparameter` (
  `strkey` varchar(32) COLLATE utf8_bin NOT NULL,
  `strvalue` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `intvalue` int(9) DEFAULT NULL,
  PRIMARY KEY (`strkey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

ALTER TABLE lockpassword ADD COLUMN WEEKDAY INT(9) DEFAULT 0;
ALTER TABLE lockpassword ADD COLUMN starttime VARCHAR(32) DEFAULT NULL;
ALTER TABLE lockpassword ADD COLUMN endtime VARCHAR(32) DEFAULT NULL;

ALTER TABLE lockpassword ADD COLUMN `password` VARCHAR(64) DEFAULT NULL; 

alter table lockpassword add column 'third_send_id' varchar(256) default null;
alter table lockpassword add column 'customerid' int(11) default null;

/*二级管理员分级管理*/
ALTER TABLE person ADD COLUMN `type` INT(9) NOT NULL DEFAULT '0' COMMENT '0:一级管理员 1：二级管理员';
ALTER TABLE person ADD COLUMN `superpersonid` INT(9) DEFAULT NULL COMMENT '上级管理员id';
ALTER TABLE person ADD COLUMN `status` INT(9) NOT NULL DEFAULT '1' COMMENT '1:正常 2:冻结 9:删除';
CREATE TABLE `dataprivilegegrant` (
  `dataprivilegegrantid` int(9) NOT NULL AUTO_INCREMENT,
  `personid` int(9) NOT NULL COMMENT '被授权的二级管理员id',
  `districtid` int(9) NOT NULL COMMENT '被授权的小区id',
  `createtime` datetime NOT NULL,
  `status` int(9) NOT NULL COMMENT '1:正常 9：删除',
  `deletetime` datetime DEFAULT NULL,
  PRIMARY KEY (`dataprivilegegrantid`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;
ALTER TABLE customer ADD COLUMN `typeinpersonid` INT(9) NOT NULL COMMENT '录入员id';
ALTER TABLE manager ADD COLUMN `typeinpersonid` INT(9) NOT NULL COMMENT '建立本管理员的管理员id';
ALTER TABLE room ADD COLUMN `typeinpersonid` INT(9) NOT NULL COMMENT '建立房间的管理员id';
ALTER TABLE operateLog ADD COLUMN `operatepersonid` INT(9) DEFAULT NULL COMMENT '操作者id';
ALTER TABLE commuLog ADD COLUMN `operatepersonid` INT(9) DEFAULT NULL COMMENT '操作者id';
ALTER TABLE openDeviceLog ADD COLUMN `operatepersonid` INT(9) DEFAULT NULL COMMENT '操作者id';

--自动密码下发
CREATE TABLE `doorlockpasswordrule` (
  `doorlockpasswordruleid` int(9) NOT NULL AUTO_INCREMENT,
  `districtid` int(9) NOT NULL,
  `isuse` int(9) NOT NULL DEFAULT '0',
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`doorlockpasswordruleid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8
CREATE TABLE `doorlockpasswordruledtl` (
  `doorlockpasswordruledtlid` int(11) NOT NULL AUTO_INCREMENT,
  `doorlockpasswordruleid` int(9) NOT NULL,
  `starttime` varchar(32) NOT NULL,
  `duration` int(9) NOT NULL,
  `weekday` int(9) NOT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`doorlockpasswordruledtlid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

alter table lockpassword add column `usestatus` int(9) not null default 0 after `status`;
alter table lockpassword add column `synstatus` int(9) not null default 0 after `usestatus`;
alter table lockpassword add column `passwordtype` int (9) not null default 0 after `synstatus`;
UPDATE lockpassword SET usestatus =1,synstatus=1;

ALTER TABLE doorlockpasswordrule ADD COLUMN typeinpersonid INT (9) DEFAULT NULL AFTER isuse ;
ALTER TABLE doorlockpasswordruledtl ADD COLUMN typeinpersonid INT (9) DEFAULT NULL AFTER doorlockpasswordruleid ;

ALTER TABLE device ADD COLUMN `signalstrength` INT(9) DEFAULT 0 AFTER personId;
