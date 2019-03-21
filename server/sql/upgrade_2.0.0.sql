ALTER TABLE `deviceDegreesHourLog` CHANGE `hour` `hour` VARCHAR(32) NULL COMMENT '时间 2016052501'; 

alter table room add column `rentfee` int(11) DEFAULT 0 after `roomImg`;
alter table room add column `balance` int(11) DEFAULT 0 after `rentfee`;
alter table room add column `arrearage` int(11) DEFAULT 0 after `balance`;

--alter table district add column `autofee` int(11) DEFAULT '0' after `shareAmount`;
alter table roomfee add column `rentfee` int(9) NOT NULL DEFAULT 0  after `pooledfee`;
alter table roomfee add column `status` int(9) NOT NULL DEFAULT 0  after `totalamount`;

/*
alter table district add column `wexinpayappid` varchar(128) DEFAULT NULL after `autofee`;
alter table district add column `wexinpaymchid` varchar(128) DEFAULT NULL after `wexinpayappid`;
alter table district add column `wexinpayapikey` varchar(128) DEFAULT NULL after `wexinpaymchid`;
alter table district add column `wexinpayappname` varchar(128) DEFAULT NULL after `wexinpayapikey`;
  */

CREATE TABLE `smshistory` (
  `smshistoryid` int(9) NOT NULL auto_increment,
  `countrycode` varchar(32) collate utf8_bin NOT NULL default '86',
  `phonenumber` varchar(64) collate utf8_bin NOT NULL,
  `personId` bigint(20) NOT NULL COMMENT '用户id',
  `districtId` bigint(20) DEFAULT NULL COMMENT '小区id',
  `districtName` varchar(100) DEFAULT NULL COMMENT '小区名称',
  `roomName` varchar(100) DEFAULT NULL COMMENT '房间名称',
  `build` varchar(20) DEFAULT NULL COMMENT '栋',
  `unit` varchar(20) DEFAULT NULL COMMENT '单元',
  `floor` varchar(20) DEFAULT NULL COMMENT '楼',  
  `message` varchar(512) collate utf8_bin NOT NULL,
  `status` int(9) NOT NULL default '1',
  `createtime` datetime NOT NULL,
  `sendtime` datetime default NULL,
  PRIMARY KEY  (`smshistoryid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `transaction` (
  `transactionid` int(9) NOT NULL auto_increment,
  `roomid` int(9) NOT NULL,
  `transactiontype` int(9) NOT NULL,
  `description` varchar(256) collate utf8_bin NOT NULL,
  `amount` int(9) NOT NULL,
  `prebalance` int(9) default NULL,
  `postbalance` int(9) default NULL,
  `status` int(9) NOT NULL,
  `createtime` datetime NOT NULL,
  `finishtime` datetime default NULL,
  `tptransactionid` varchar(256) collate utf8_bin default NULL,
  PRIMARY KEY  (`transactionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `districtpaymentsetting` (
  `districtpaymentsettingid` int(9) NOT NULL auto_increment,
  `districtid` int(9) default NULL,
  `autofee` int(9) default '0',
  `wexinpayappid` varchar(128) collate utf8_bin default NULL,
  `wexinpaymchid` varchar(128) collate utf8_bin default NULL,
  `wexinpayapikey` varchar(128) collate utf8_bin default NULL,
  `wexinpayappname` varchar(128) collate utf8_bin default NULL,
  PRIMARY KEY  (`districtpaymentsettingid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
