create or replace view vw_ele_element as
 select `dns_element`.`id` AS `id`,`dns_element`.`ele_code` AS `code`,`dns_element`.`ele_name` AS `name`,1 AS `level`,1 AS `leaf`,`dns_element`.`enabled` AS `enabled`,NULL AS `parent_id` from `dns_element` where (`dns_element`.`ele_code` <> _utf8'ELE') 
 ;

create or replace view vw_ele_uimanager AS
select `dns_uimanager`.`id` AS `id`,`dns_uimanager`.`ui_code` AS `code`,`dns_uimanager`.`ui_name` AS `name`,1 AS `level`,1 AS `leaf`,1 AS `enabled`,`dns_uimanager`.`parent_id` AS `parent_id`,`dns_uimanager`.`rg_code` AS `rg_code`,`dns_uimanager`.`id` AS `ui_id`,`dns_uimanager`.`ui_code` AS `ui_code`,`dns_uimanager`.`ui_name` AS `ui_name`,`dns_uimanager`.`xtype` AS `xtype`,`dns_uimanager`.`comp_id` AS `comp_id`,`dns_uimanager`.`servletpath` AS `servletpath`,`dns_uimanager`.`total_column` AS `total_column` from `dns_uimanager` 
;

create or replace view vw_ele_role as
select `r`.`id` AS `id`,`r`.`roleCode` AS `code`,`r`.`roleName` AS `name`,1 AS `level`,1 AS `leaf`,1 AS `enabled`,NULL AS `parent_id`,`r`.`rg_code` AS `rg_code` from `dns_role` `r` where (`r`.`builtin` = FALSE)
;
create or replace view vw_ele_menu as
select `m`.`id` AS `id`,`m`.`sequence` AS `code`,`m`.`name` AS `name`,NULL AS `parent_id`,0 AS `leaf`,1 AS `enabled`,`m`.`sequence` AS `sequence` from `dns_menugroup` `m` union all select `m`.`id` AS `id`,`m`.`menuCode` AS `code`,`m`.`menuName` AS `name`,`m`.`mg_id` AS `parent_id`,1 AS `leaf`,1 AS `enabled`,`m`.`sequence` AS `sequence` from `dns_menu` `m`
;
INSERT INTO `dns_tradedata_rule` VALUES ('1', '2017-07-09 21:54:54', '0', '2017-07-10 23:39:42', 'jsadmin', '001', '[{\"rule_name\":\"交易日期\",\"rule_rgex\":\"<=\",\"rule_value\":\"1\"},{\"rule_name\":\"成交净价\",\"rule_rgex\":\"=\",\"rule_value\":\"\"},{\"rule_name\":\"债券类型\",\"rule_rgex\":\"=\",\"rule_value\":\"\"},]', '规则一', '在同一天或间隔一天，以相同成交净价买卖同一债券');
INSERT INTO `dns_tradedata_rule` VALUES ('2', '2017-07-09 21:54:54', '0', '2017-07-10 23:39:56', 'jsadmin', '002', '[{\"rule_name\":\"交易日期\",\"rule_rgex\":\"<\",\"rule_value\":\"5\"},{\"rule_name\":\"交易对手\",\"rule_rgex\":\"=\",\"rule_value\":\"\"},{\"rule_name\":\"交易次数\",\"rule_rgex\":\">=\",\"rule_value\":\"5\"},{\"rule_name\":\"债券类型\",\"rule_rgex\":\"=\",\"rule_value\":\"\"},]', '规则二', '在同一天或多个交易日内，跟同一交易对手频繁买卖同一债券');
INSERT INTO `dns_tradedata_rule` VALUES ('3', '2017-07-09 21:54:54', '0', '2017-07-10 23:31:44', 'jsadmin', '003', '[{\"rule_name\":\"成交净价\",\"rule_rgex\":\"&gt;\",\"rule_value\":\"1\"},{\"rule_name\":\"债券类型\",\"rule_rgex\":\"=\",\"rule_value\":\"\"},]', '规则三', '买卖同一债券的成交净价相差1元及以上');

