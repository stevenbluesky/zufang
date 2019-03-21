alter table lockpassword add column `typeinpersonid` int(9) default NULL after `usercode`;
alter table roomprivilege add column `typeinpersonid` int(9) default NULL after `roomId`;
