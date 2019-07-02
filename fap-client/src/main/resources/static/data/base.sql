/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50019
Source Host           : localhost:3306
Source Database       : pbcnew

Target Server Type    : MYSQL
Target Server Version : 50019
File Encoding         : 65001

Date: 2017-06-09 18:05:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dns_accessory
-- ----------------------------
DROP TABLE IF EXISTS `dns_accessory`;
CREATE TABLE `dns_accessory` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `ext` varchar(255) default NULL,
  `height` int(11) NOT NULL,
  `info` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `path` varchar(255) default NULL,
  `real_name` varchar(255) default NULL,
  `size` decimal(12,2) default NULL,
  `width` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_accessory
-- ----------------------------
INSERT INTO `dns_accessory` VALUES ('5', null, '0', null, '0', null, 'member.jpg', 'resources/style/common/images', null, null, '0');
INSERT INTO `dns_accessory` VALUES ('6', null, '0', null, '0', null, 'logo.ico', 'resources/style/common/images', null, null, '0');

-- ----------------------------
-- Table structure for dns_annual_review
-- ----------------------------
DROP TABLE IF EXISTS `dns_annual_review`;
CREATE TABLE `dns_annual_review` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `annual_review_user` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  `rg_code` varchar(255) default NULL,
  `status` int(11) default NULL,
  `year` varchar(255) default NULL,
  `annual_review_file_id` bigint(20) default NULL,
  `pbc_org_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKbyysoxwyvcyxmcs97r4bvb0e2` (`annual_review_file_id`),
  KEY `FKst97lx2e455yeu6fugteivwe4` (`pbc_org_id`),
  CONSTRAINT `FKst97lx2e455yeu6fugteivwe4` FOREIGN KEY (`pbc_org_id`) REFERENCES `dns_ele_pbc_org` (`id`),
  CONSTRAINT `FKbyysoxwyvcyxmcs97r4bvb0e2` FOREIGN KEY (`annual_review_file_id`) REFERENCES `dns_accessory` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_annual_review
-- ----------------------------

-- ----------------------------
-- Table structure for dns_app
-- ----------------------------
DROP TABLE IF EXISTS `dns_app`;
CREATE TABLE `dns_app` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `dispname` varchar(255) default NULL,
  `menuname` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `sequence` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_app
-- ----------------------------
INSERT INTO `dns_app` VALUES ('12', '2017-06-09 17:20:57', '0', '系统管理', '系统管理', '系统管理', '0');
INSERT INTO `dns_app` VALUES ('141', '2017-06-09 17:21:04', '0', '统计检查', '统计检查', '统计检查', '21');
INSERT INTO `dns_app` VALUES ('175', '2017-06-09 17:21:05', '0', '数据管理', '数据管理', '数据管理', '30');

-- ----------------------------
-- Table structure for dns_area
-- ----------------------------
DROP TABLE IF EXISTS `dns_area`;
CREATE TABLE `dns_area` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `areaName` varchar(255) default NULL,
  `common` bit(1) default '\0',
  `level` int(11) NOT NULL,
  `sequence` int(11) NOT NULL,
  `parent_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKl0mnesiex6oe5n1ao2i70pffm` (`parent_id`),
  CONSTRAINT `FKl0mnesiex6oe5n1ao2i70pffm` FOREIGN KEY (`parent_id`) REFERENCES `dns_area` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_area
-- ----------------------------
INSERT INTO `dns_area` VALUES ('110000', null, '0', '北京市', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('110100', null, '0', '北京(市辖区)', '\0', '2', '1', '110000');
INSERT INTO `dns_area` VALUES ('110101', null, '0', '东城区', '\0', '3', '1', '110100');
INSERT INTO `dns_area` VALUES ('110102', null, '0', '西城区', '\0', '3', '1', '110100');
INSERT INTO `dns_area` VALUES ('110103', null, '0', '崇文区', '\0', '3', '1', '110100');
INSERT INTO `dns_area` VALUES ('110104', null, '0', '宣武区', '\0', '3', '1', '110100');
INSERT INTO `dns_area` VALUES ('110105', null, '0', '朝阳区', '\0', '3', '1', '110100');
INSERT INTO `dns_area` VALUES ('110106', null, '0', '丰台区', '\0', '3', '1', '110100');
INSERT INTO `dns_area` VALUES ('110107', null, '0', '石景山区', '\0', '3', '1', '110100');
INSERT INTO `dns_area` VALUES ('110108', null, '0', '海淀区', '\0', '3', '1', '110100');
INSERT INTO `dns_area` VALUES ('110109', null, '0', '门头沟区', '\0', '3', '1', '110100');
INSERT INTO `dns_area` VALUES ('110111', null, '0', '房山区', '\0', '3', '1', '110100');
INSERT INTO `dns_area` VALUES ('110112', null, '0', '通州区', '\0', '3', '1', '110100');
INSERT INTO `dns_area` VALUES ('110113', null, '0', '顺义区', '\0', '3', '1', '110100');
INSERT INTO `dns_area` VALUES ('110114', null, '0', '昌平区', '\0', '3', '1', '110100');
INSERT INTO `dns_area` VALUES ('110115', null, '0', '大兴区', '\0', '3', '1', '110100');
INSERT INTO `dns_area` VALUES ('110116', null, '0', '怀柔区', '\0', '3', '1', '110100');
INSERT INTO `dns_area` VALUES ('110117', null, '0', '平谷区', '\0', '3', '1', '110100');
INSERT INTO `dns_area` VALUES ('110200', null, '0', '北京(县)', '\0', '2', '1', '110000');
INSERT INTO `dns_area` VALUES ('110228', null, '0', '密云县', '\0', '3', '1', '110200');
INSERT INTO `dns_area` VALUES ('110229', null, '0', '延庆县', '\0', '3', '1', '110200');
INSERT INTO `dns_area` VALUES ('120000', null, '0', '天津市', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('120100', null, '0', '天津(市辖区)', '\0', '2', '1', '120000');
INSERT INTO `dns_area` VALUES ('120101', null, '0', '和平区', '\0', '3', '1', '120100');
INSERT INTO `dns_area` VALUES ('120102', null, '0', '河东区', '\0', '3', '1', '120100');
INSERT INTO `dns_area` VALUES ('120103', null, '0', '河西区', '\0', '3', '1', '120100');
INSERT INTO `dns_area` VALUES ('120104', null, '0', '南开区', '\0', '3', '1', '120100');
INSERT INTO `dns_area` VALUES ('120105', null, '0', '河北区', '\0', '3', '1', '120100');
INSERT INTO `dns_area` VALUES ('120106', null, '0', '红桥区', '\0', '3', '1', '120100');
INSERT INTO `dns_area` VALUES ('120107', null, '0', '塘沽区', '\0', '3', '1', '120100');
INSERT INTO `dns_area` VALUES ('120108', null, '0', '汉沽区', '\0', '3', '1', '120100');
INSERT INTO `dns_area` VALUES ('120109', null, '0', '大港区', '\0', '3', '1', '120100');
INSERT INTO `dns_area` VALUES ('120110', null, '0', '东丽区', '\0', '3', '1', '120100');
INSERT INTO `dns_area` VALUES ('120111', null, '0', '西青区', '\0', '3', '1', '120100');
INSERT INTO `dns_area` VALUES ('120112', null, '0', '津南区', '\0', '3', '1', '120100');
INSERT INTO `dns_area` VALUES ('120113', null, '0', '北辰区', '\0', '3', '1', '120100');
INSERT INTO `dns_area` VALUES ('120114', null, '0', '武清区', '\0', '3', '1', '120100');
INSERT INTO `dns_area` VALUES ('120115', null, '0', '宝坻区', '\0', '3', '1', '120100');
INSERT INTO `dns_area` VALUES ('120200', null, '0', '天津(县)', '\0', '2', '1', '120000');
INSERT INTO `dns_area` VALUES ('120221', null, '0', '宁河县', '\0', '3', '1', '120200');
INSERT INTO `dns_area` VALUES ('120223', null, '0', '静海县', '\0', '3', '1', '120200');
INSERT INTO `dns_area` VALUES ('120225', null, '0', '蓟　县', '\0', '3', '1', '120200');
INSERT INTO `dns_area` VALUES ('130000', null, '0', '河北省', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('130100', null, '0', '石家庄市', '\0', '2', '1', '130000');
INSERT INTO `dns_area` VALUES ('130101', null, '0', '市辖区', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130102', null, '0', '长安区', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130103', null, '0', '桥东区', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130104', null, '0', '桥西区', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130105', null, '0', '新华区', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130107', null, '0', '井陉矿区', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130108', null, '0', '裕华区', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130121', null, '0', '井陉县', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130123', null, '0', '正定县', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130124', null, '0', '栾城县', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130125', null, '0', '行唐县', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130126', null, '0', '灵寿县', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130127', null, '0', '高邑县', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130128', null, '0', '深泽县', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130129', null, '0', '赞皇县', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130130', null, '0', '无极县', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130131', null, '0', '平山县', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130132', null, '0', '元氏县', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130133', null, '0', '赵　县', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130181', null, '0', '辛集市', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130182', null, '0', '藁城市', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130183', null, '0', '晋州市', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130184', null, '0', '新乐市', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130185', null, '0', '鹿泉市', '\0', '3', '1', '130100');
INSERT INTO `dns_area` VALUES ('130200', null, '0', '唐山市', '\0', '2', '1', '130000');
INSERT INTO `dns_area` VALUES ('130201', null, '0', '市辖区', '\0', '3', '1', '130200');
INSERT INTO `dns_area` VALUES ('130202', null, '0', '路南区', '\0', '3', '1', '130200');
INSERT INTO `dns_area` VALUES ('130203', null, '0', '路北区', '\0', '3', '1', '130200');
INSERT INTO `dns_area` VALUES ('130204', null, '0', '古冶区', '\0', '3', '1', '130200');
INSERT INTO `dns_area` VALUES ('130205', null, '0', '开平区', '\0', '3', '1', '130200');
INSERT INTO `dns_area` VALUES ('130207', null, '0', '丰南区', '\0', '3', '1', '130200');
INSERT INTO `dns_area` VALUES ('130208', null, '0', '丰润区', '\0', '3', '1', '130200');
INSERT INTO `dns_area` VALUES ('130223', null, '0', '滦　县', '\0', '3', '1', '130200');
INSERT INTO `dns_area` VALUES ('130224', null, '0', '滦南县', '\0', '3', '1', '130200');
INSERT INTO `dns_area` VALUES ('130225', null, '0', '乐亭县', '\0', '3', '1', '130200');
INSERT INTO `dns_area` VALUES ('130227', null, '0', '迁西县', '\0', '3', '1', '130200');
INSERT INTO `dns_area` VALUES ('130229', null, '0', '玉田县', '\0', '3', '1', '130200');
INSERT INTO `dns_area` VALUES ('130230', null, '0', '唐海县', '\0', '3', '1', '130200');
INSERT INTO `dns_area` VALUES ('130281', null, '0', '遵化市', '\0', '3', '1', '130200');
INSERT INTO `dns_area` VALUES ('130283', null, '0', '迁安市', '\0', '3', '1', '130200');
INSERT INTO `dns_area` VALUES ('130300', null, '0', '秦皇岛市', '\0', '2', '1', '130000');
INSERT INTO `dns_area` VALUES ('130301', null, '0', '市辖区', '\0', '3', '1', '130300');
INSERT INTO `dns_area` VALUES ('130302', null, '0', '海港区', '\0', '3', '1', '130300');
INSERT INTO `dns_area` VALUES ('130303', null, '0', '山海关区', '\0', '3', '1', '130300');
INSERT INTO `dns_area` VALUES ('130304', null, '0', '北戴河区', '\0', '3', '1', '130300');
INSERT INTO `dns_area` VALUES ('130321', null, '0', '青龙满族自治县', '\0', '3', '1', '130300');
INSERT INTO `dns_area` VALUES ('130322', null, '0', '昌黎县', '\0', '3', '1', '130300');
INSERT INTO `dns_area` VALUES ('130323', null, '0', '抚宁县', '\0', '3', '1', '130300');
INSERT INTO `dns_area` VALUES ('130324', null, '0', '卢龙县', '\0', '3', '1', '130300');
INSERT INTO `dns_area` VALUES ('130400', null, '0', '邯郸市', '\0', '2', '1', '130000');
INSERT INTO `dns_area` VALUES ('130401', null, '0', '市辖区', '\0', '3', '1', '130400');
INSERT INTO `dns_area` VALUES ('130402', null, '0', '邯山区', '\0', '3', '1', '130400');
INSERT INTO `dns_area` VALUES ('130403', null, '0', '丛台区', '\0', '3', '1', '130400');
INSERT INTO `dns_area` VALUES ('130404', null, '0', '复兴区', '\0', '3', '1', '130400');
INSERT INTO `dns_area` VALUES ('130406', null, '0', '峰峰矿区', '\0', '3', '1', '130400');
INSERT INTO `dns_area` VALUES ('130421', null, '0', '邯郸县', '\0', '3', '1', '130400');
INSERT INTO `dns_area` VALUES ('130423', null, '0', '临漳县', '\0', '3', '1', '130400');
INSERT INTO `dns_area` VALUES ('130424', null, '0', '成安县', '\0', '3', '1', '130400');
INSERT INTO `dns_area` VALUES ('130425', null, '0', '大名县', '\0', '3', '1', '130400');
INSERT INTO `dns_area` VALUES ('130426', null, '0', '涉　县', '\0', '3', '1', '130400');
INSERT INTO `dns_area` VALUES ('130427', null, '0', '磁　县', '\0', '3', '1', '130400');
INSERT INTO `dns_area` VALUES ('130428', null, '0', '肥乡县', '\0', '3', '1', '130400');
INSERT INTO `dns_area` VALUES ('130429', null, '0', '永年县', '\0', '3', '1', '130400');
INSERT INTO `dns_area` VALUES ('130430', null, '0', '邱　县', '\0', '3', '1', '130400');
INSERT INTO `dns_area` VALUES ('130431', null, '0', '鸡泽县', '\0', '3', '1', '130400');
INSERT INTO `dns_area` VALUES ('130432', null, '0', '广平县', '\0', '3', '1', '130400');
INSERT INTO `dns_area` VALUES ('130433', null, '0', '馆陶县', '\0', '3', '1', '130400');
INSERT INTO `dns_area` VALUES ('130434', null, '0', '魏　县', '\0', '3', '1', '130400');
INSERT INTO `dns_area` VALUES ('130435', null, '0', '曲周县', '\0', '3', '1', '130400');
INSERT INTO `dns_area` VALUES ('130481', null, '0', '武安市', '\0', '3', '1', '130400');
INSERT INTO `dns_area` VALUES ('130500', null, '0', '邢台市', '\0', '2', '1', '130000');
INSERT INTO `dns_area` VALUES ('130501', null, '0', '市辖区', '\0', '3', '1', '130500');
INSERT INTO `dns_area` VALUES ('130502', null, '0', '桥东区', '\0', '3', '1', '130500');
INSERT INTO `dns_area` VALUES ('130503', null, '0', '桥西区', '\0', '3', '1', '130500');
INSERT INTO `dns_area` VALUES ('130521', null, '0', '邢台县', '\0', '3', '1', '130500');
INSERT INTO `dns_area` VALUES ('130522', null, '0', '临城县', '\0', '3', '1', '130500');
INSERT INTO `dns_area` VALUES ('130523', null, '0', '内丘县', '\0', '3', '1', '130500');
INSERT INTO `dns_area` VALUES ('130524', null, '0', '柏乡县', '\0', '3', '1', '130500');
INSERT INTO `dns_area` VALUES ('130525', null, '0', '隆尧县', '\0', '3', '1', '130500');
INSERT INTO `dns_area` VALUES ('130526', null, '0', '任　县', '\0', '3', '1', '130500');
INSERT INTO `dns_area` VALUES ('130527', null, '0', '南和县', '\0', '3', '1', '130500');
INSERT INTO `dns_area` VALUES ('130528', null, '0', '宁晋县', '\0', '3', '1', '130500');
INSERT INTO `dns_area` VALUES ('130529', null, '0', '巨鹿县', '\0', '3', '1', '130500');
INSERT INTO `dns_area` VALUES ('130530', null, '0', '新河县', '\0', '3', '1', '130500');
INSERT INTO `dns_area` VALUES ('130531', null, '0', '广宗县', '\0', '3', '1', '130500');
INSERT INTO `dns_area` VALUES ('130532', null, '0', '平乡县', '\0', '3', '1', '130500');
INSERT INTO `dns_area` VALUES ('130533', null, '0', '威　县', '\0', '3', '1', '130500');
INSERT INTO `dns_area` VALUES ('130534', null, '0', '清河县', '\0', '3', '1', '130500');
INSERT INTO `dns_area` VALUES ('130535', null, '0', '临西县', '\0', '3', '1', '130500');
INSERT INTO `dns_area` VALUES ('130581', null, '0', '南宫市', '\0', '3', '1', '130500');
INSERT INTO `dns_area` VALUES ('130582', null, '0', '沙河市', '\0', '3', '1', '130500');
INSERT INTO `dns_area` VALUES ('130600', null, '0', '保定市', '\0', '2', '1', '130000');
INSERT INTO `dns_area` VALUES ('130601', null, '0', '市辖区', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130602', null, '0', '新市区', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130603', null, '0', '北市区', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130604', null, '0', '南市区', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130621', null, '0', '满城县', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130622', null, '0', '清苑县', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130623', null, '0', '涞水县', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130624', null, '0', '阜平县', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130625', null, '0', '徐水县', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130626', null, '0', '定兴县', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130627', null, '0', '唐　县', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130628', null, '0', '高阳县', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130629', null, '0', '容城县', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130630', null, '0', '涞源县', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130631', null, '0', '望都县', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130632', null, '0', '安新县', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130633', null, '0', '易　县', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130634', null, '0', '曲阳县', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130635', null, '0', '蠡　县', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130636', null, '0', '顺平县', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130637', null, '0', '博野县', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130638', null, '0', '雄　县', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130681', null, '0', '涿州市', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130682', null, '0', '定州市', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130683', null, '0', '安国市', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130684', null, '0', '高碑店市', '\0', '3', '1', '130600');
INSERT INTO `dns_area` VALUES ('130700', null, '0', '张家口市', '\0', '2', '1', '130000');
INSERT INTO `dns_area` VALUES ('130701', null, '0', '市辖区', '\0', '3', '1', '130700');
INSERT INTO `dns_area` VALUES ('130702', null, '0', '桥东区', '\0', '3', '1', '130700');
INSERT INTO `dns_area` VALUES ('130703', null, '0', '桥西区', '\0', '3', '1', '130700');
INSERT INTO `dns_area` VALUES ('130705', null, '0', '宣化区', '\0', '3', '1', '130700');
INSERT INTO `dns_area` VALUES ('130706', null, '0', '下花园区', '\0', '3', '1', '130700');
INSERT INTO `dns_area` VALUES ('130721', null, '0', '宣化县', '\0', '3', '1', '130700');
INSERT INTO `dns_area` VALUES ('130722', null, '0', '张北县', '\0', '3', '1', '130700');
INSERT INTO `dns_area` VALUES ('130723', null, '0', '康保县', '\0', '3', '1', '130700');
INSERT INTO `dns_area` VALUES ('130724', null, '0', '沽源县', '\0', '3', '1', '130700');
INSERT INTO `dns_area` VALUES ('130725', null, '0', '尚义县', '\0', '3', '1', '130700');
INSERT INTO `dns_area` VALUES ('130726', null, '0', '蔚　县', '\0', '3', '1', '130700');
INSERT INTO `dns_area` VALUES ('130727', null, '0', '阳原县', '\0', '3', '1', '130700');
INSERT INTO `dns_area` VALUES ('130728', null, '0', '怀安县', '\0', '3', '1', '130700');
INSERT INTO `dns_area` VALUES ('130729', null, '0', '万全县', '\0', '3', '1', '130700');
INSERT INTO `dns_area` VALUES ('130730', null, '0', '怀来县', '\0', '3', '1', '130700');
INSERT INTO `dns_area` VALUES ('130731', null, '0', '涿鹿县', '\0', '3', '1', '130700');
INSERT INTO `dns_area` VALUES ('130732', null, '0', '赤城县', '\0', '3', '1', '130700');
INSERT INTO `dns_area` VALUES ('130733', null, '0', '崇礼县', '\0', '3', '1', '130700');
INSERT INTO `dns_area` VALUES ('130800', null, '0', '承德市', '\0', '2', '1', '130000');
INSERT INTO `dns_area` VALUES ('130801', null, '0', '市辖区', '\0', '3', '1', '130800');
INSERT INTO `dns_area` VALUES ('130802', null, '0', '双桥区', '\0', '3', '1', '130800');
INSERT INTO `dns_area` VALUES ('130803', null, '0', '双滦区', '\0', '3', '1', '130800');
INSERT INTO `dns_area` VALUES ('130804', null, '0', '鹰手营子矿区', '\0', '3', '1', '130800');
INSERT INTO `dns_area` VALUES ('130821', null, '0', '承德县', '\0', '3', '1', '130800');
INSERT INTO `dns_area` VALUES ('130822', null, '0', '兴隆县', '\0', '3', '1', '130800');
INSERT INTO `dns_area` VALUES ('130823', null, '0', '平泉县', '\0', '3', '1', '130800');
INSERT INTO `dns_area` VALUES ('130824', null, '0', '滦平县', '\0', '3', '1', '130800');
INSERT INTO `dns_area` VALUES ('130825', null, '0', '隆化县', '\0', '3', '1', '130800');
INSERT INTO `dns_area` VALUES ('130826', null, '0', '丰宁满族自治县', '\0', '3', '1', '130800');
INSERT INTO `dns_area` VALUES ('130827', null, '0', '宽城满族自治县', '\0', '3', '1', '130800');
INSERT INTO `dns_area` VALUES ('130828', null, '0', '围场满族蒙古族自治县', '\0', '3', '1', '130800');
INSERT INTO `dns_area` VALUES ('130900', null, '0', '沧州市', '\0', '2', '1', '130000');
INSERT INTO `dns_area` VALUES ('130901', null, '0', '市辖区', '\0', '3', '1', '130900');
INSERT INTO `dns_area` VALUES ('130902', null, '0', '新华区', '\0', '3', '1', '130900');
INSERT INTO `dns_area` VALUES ('130903', null, '0', '运河区', '\0', '3', '1', '130900');
INSERT INTO `dns_area` VALUES ('130921', null, '0', '沧　县', '\0', '3', '1', '130900');
INSERT INTO `dns_area` VALUES ('130922', null, '0', '青　县', '\0', '3', '1', '130900');
INSERT INTO `dns_area` VALUES ('130923', null, '0', '东光县', '\0', '3', '1', '130900');
INSERT INTO `dns_area` VALUES ('130924', null, '0', '海兴县', '\0', '3', '1', '130900');
INSERT INTO `dns_area` VALUES ('130925', null, '0', '盐山县', '\0', '3', '1', '130900');
INSERT INTO `dns_area` VALUES ('130926', null, '0', '肃宁县', '\0', '3', '1', '130900');
INSERT INTO `dns_area` VALUES ('130927', null, '0', '南皮县', '\0', '3', '1', '130900');
INSERT INTO `dns_area` VALUES ('130928', null, '0', '吴桥县', '\0', '3', '1', '130900');
INSERT INTO `dns_area` VALUES ('130929', null, '0', '献　县', '\0', '3', '1', '130900');
INSERT INTO `dns_area` VALUES ('130930', null, '0', '孟村回族自治县', '\0', '3', '1', '130900');
INSERT INTO `dns_area` VALUES ('130981', null, '0', '泊头市', '\0', '3', '1', '130900');
INSERT INTO `dns_area` VALUES ('130982', null, '0', '任丘市', '\0', '3', '1', '130900');
INSERT INTO `dns_area` VALUES ('130983', null, '0', '黄骅市', '\0', '3', '1', '130900');
INSERT INTO `dns_area` VALUES ('130984', null, '0', '河间市', '\0', '3', '1', '130900');
INSERT INTO `dns_area` VALUES ('131000', null, '0', '廊坊市', '\0', '2', '1', '130000');
INSERT INTO `dns_area` VALUES ('131001', null, '0', '市辖区', '\0', '3', '1', '131000');
INSERT INTO `dns_area` VALUES ('131002', null, '0', '安次区', '\0', '3', '1', '131000');
INSERT INTO `dns_area` VALUES ('131003', null, '0', '广阳区', '\0', '3', '1', '131000');
INSERT INTO `dns_area` VALUES ('131022', null, '0', '固安县', '\0', '3', '1', '131000');
INSERT INTO `dns_area` VALUES ('131023', null, '0', '永清县', '\0', '3', '1', '131000');
INSERT INTO `dns_area` VALUES ('131024', null, '0', '香河县', '\0', '3', '1', '131000');
INSERT INTO `dns_area` VALUES ('131025', null, '0', '大城县', '\0', '3', '1', '131000');
INSERT INTO `dns_area` VALUES ('131026', null, '0', '文安县', '\0', '3', '1', '131000');
INSERT INTO `dns_area` VALUES ('131028', null, '0', '大厂回族自治县', '\0', '3', '1', '131000');
INSERT INTO `dns_area` VALUES ('131081', null, '0', '霸州市', '\0', '3', '1', '131000');
INSERT INTO `dns_area` VALUES ('131082', null, '0', '三河市', '\0', '3', '1', '131000');
INSERT INTO `dns_area` VALUES ('131100', null, '0', '衡水市', '\0', '2', '1', '130000');
INSERT INTO `dns_area` VALUES ('131101', null, '0', '市辖区', '\0', '3', '1', '131100');
INSERT INTO `dns_area` VALUES ('131102', null, '0', '桃城区', '\0', '3', '1', '131100');
INSERT INTO `dns_area` VALUES ('131121', null, '0', '枣强县', '\0', '3', '1', '131100');
INSERT INTO `dns_area` VALUES ('131122', null, '0', '武邑县', '\0', '3', '1', '131100');
INSERT INTO `dns_area` VALUES ('131123', null, '0', '武强县', '\0', '3', '1', '131100');
INSERT INTO `dns_area` VALUES ('131124', null, '0', '饶阳县', '\0', '3', '1', '131100');
INSERT INTO `dns_area` VALUES ('131125', null, '0', '安平县', '\0', '3', '1', '131100');
INSERT INTO `dns_area` VALUES ('131126', null, '0', '故城县', '\0', '3', '1', '131100');
INSERT INTO `dns_area` VALUES ('131127', null, '0', '景　县', '\0', '3', '1', '131100');
INSERT INTO `dns_area` VALUES ('131128', null, '0', '阜城县', '\0', '3', '1', '131100');
INSERT INTO `dns_area` VALUES ('131181', null, '0', '冀州市', '\0', '3', '1', '131100');
INSERT INTO `dns_area` VALUES ('131182', null, '0', '深州市', '\0', '3', '1', '131100');
INSERT INTO `dns_area` VALUES ('140000', null, '0', '山西省', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('140100', null, '0', '太原市', '\0', '2', '1', '140000');
INSERT INTO `dns_area` VALUES ('140101', null, '0', '市辖区', '\0', '3', '1', '140100');
INSERT INTO `dns_area` VALUES ('140105', null, '0', '小店区', '\0', '3', '1', '140100');
INSERT INTO `dns_area` VALUES ('140106', null, '0', '迎泽区', '\0', '3', '1', '140100');
INSERT INTO `dns_area` VALUES ('140107', null, '0', '杏花岭区', '\0', '3', '1', '140100');
INSERT INTO `dns_area` VALUES ('140108', null, '0', '尖草坪区', '\0', '3', '1', '140100');
INSERT INTO `dns_area` VALUES ('140109', null, '0', '万柏林区', '\0', '3', '1', '140100');
INSERT INTO `dns_area` VALUES ('140110', null, '0', '晋源区', '\0', '3', '1', '140100');
INSERT INTO `dns_area` VALUES ('140121', null, '0', '清徐县', '\0', '3', '1', '140100');
INSERT INTO `dns_area` VALUES ('140122', null, '0', '阳曲县', '\0', '3', '1', '140100');
INSERT INTO `dns_area` VALUES ('140123', null, '0', '娄烦县', '\0', '3', '1', '140100');
INSERT INTO `dns_area` VALUES ('140181', null, '0', '古交市', '\0', '3', '1', '140100');
INSERT INTO `dns_area` VALUES ('140200', null, '0', '大同市', '\0', '2', '1', '140000');
INSERT INTO `dns_area` VALUES ('140201', null, '0', '市辖区', '\0', '3', '1', '140200');
INSERT INTO `dns_area` VALUES ('140202', null, '0', '城　区', '\0', '3', '1', '140200');
INSERT INTO `dns_area` VALUES ('140203', null, '0', '矿　区', '\0', '3', '1', '140200');
INSERT INTO `dns_area` VALUES ('140211', null, '0', '南郊区', '\0', '3', '1', '140200');
INSERT INTO `dns_area` VALUES ('140212', null, '0', '新荣区', '\0', '3', '1', '140200');
INSERT INTO `dns_area` VALUES ('140221', null, '0', '阳高县', '\0', '3', '1', '140200');
INSERT INTO `dns_area` VALUES ('140222', null, '0', '天镇县', '\0', '3', '1', '140200');
INSERT INTO `dns_area` VALUES ('140223', null, '0', '广灵县', '\0', '3', '1', '140200');
INSERT INTO `dns_area` VALUES ('140224', null, '0', '灵丘县', '\0', '3', '1', '140200');
INSERT INTO `dns_area` VALUES ('140225', null, '0', '浑源县', '\0', '3', '1', '140200');
INSERT INTO `dns_area` VALUES ('140226', null, '0', '左云县', '\0', '3', '1', '140200');
INSERT INTO `dns_area` VALUES ('140227', null, '0', '大同县', '\0', '3', '1', '140200');
INSERT INTO `dns_area` VALUES ('140300', null, '0', '阳泉市', '\0', '2', '1', '140000');
INSERT INTO `dns_area` VALUES ('140301', null, '0', '市辖区', '\0', '3', '1', '140300');
INSERT INTO `dns_area` VALUES ('140302', null, '0', '城　区', '\0', '3', '1', '140300');
INSERT INTO `dns_area` VALUES ('140303', null, '0', '矿　区', '\0', '3', '1', '140300');
INSERT INTO `dns_area` VALUES ('140311', null, '0', '郊　区', '\0', '3', '1', '140300');
INSERT INTO `dns_area` VALUES ('140321', null, '0', '平定县', '\0', '3', '1', '140300');
INSERT INTO `dns_area` VALUES ('140322', null, '0', '盂　县', '\0', '3', '1', '140300');
INSERT INTO `dns_area` VALUES ('140400', null, '0', '长治市', '\0', '2', '1', '140000');
INSERT INTO `dns_area` VALUES ('140401', null, '0', '市辖区', '\0', '3', '1', '140400');
INSERT INTO `dns_area` VALUES ('140402', null, '0', '城　区', '\0', '3', '1', '140400');
INSERT INTO `dns_area` VALUES ('140411', null, '0', '郊　区', '\0', '3', '1', '140400');
INSERT INTO `dns_area` VALUES ('140421', null, '0', '长治县', '\0', '3', '1', '140400');
INSERT INTO `dns_area` VALUES ('140423', null, '0', '襄垣县', '\0', '3', '1', '140400');
INSERT INTO `dns_area` VALUES ('140424', null, '0', '屯留县', '\0', '3', '1', '140400');
INSERT INTO `dns_area` VALUES ('140425', null, '0', '平顺县', '\0', '3', '1', '140400');
INSERT INTO `dns_area` VALUES ('140426', null, '0', '黎城县', '\0', '3', '1', '140400');
INSERT INTO `dns_area` VALUES ('140427', null, '0', '壶关县', '\0', '3', '1', '140400');
INSERT INTO `dns_area` VALUES ('140428', null, '0', '长子县', '\0', '3', '1', '140400');
INSERT INTO `dns_area` VALUES ('140429', null, '0', '武乡县', '\0', '3', '1', '140400');
INSERT INTO `dns_area` VALUES ('140430', null, '0', '沁　县', '\0', '3', '1', '140400');
INSERT INTO `dns_area` VALUES ('140431', null, '0', '沁源县', '\0', '3', '1', '140400');
INSERT INTO `dns_area` VALUES ('140481', null, '0', '潞城市', '\0', '3', '1', '140400');
INSERT INTO `dns_area` VALUES ('140500', null, '0', '晋城市', '\0', '2', '1', '140000');
INSERT INTO `dns_area` VALUES ('140501', null, '0', '市辖区', '\0', '3', '1', '140500');
INSERT INTO `dns_area` VALUES ('140502', null, '0', '城　区', '\0', '3', '1', '140500');
INSERT INTO `dns_area` VALUES ('140521', null, '0', '沁水县', '\0', '3', '1', '140500');
INSERT INTO `dns_area` VALUES ('140522', null, '0', '阳城县', '\0', '3', '1', '140500');
INSERT INTO `dns_area` VALUES ('140524', null, '0', '陵川县', '\0', '3', '1', '140500');
INSERT INTO `dns_area` VALUES ('140525', null, '0', '泽州县', '\0', '3', '1', '140500');
INSERT INTO `dns_area` VALUES ('140581', null, '0', '高平市', '\0', '3', '1', '140500');
INSERT INTO `dns_area` VALUES ('140600', null, '0', '朔州市', '\0', '2', '1', '140000');
INSERT INTO `dns_area` VALUES ('140601', null, '0', '市辖区', '\0', '3', '1', '140600');
INSERT INTO `dns_area` VALUES ('140602', null, '0', '朔城区', '\0', '3', '1', '140600');
INSERT INTO `dns_area` VALUES ('140603', null, '0', '平鲁区', '\0', '3', '1', '140600');
INSERT INTO `dns_area` VALUES ('140621', null, '0', '山阴县', '\0', '3', '1', '140600');
INSERT INTO `dns_area` VALUES ('140622', null, '0', '应　县', '\0', '3', '1', '140600');
INSERT INTO `dns_area` VALUES ('140623', null, '0', '右玉县', '\0', '3', '1', '140600');
INSERT INTO `dns_area` VALUES ('140624', null, '0', '怀仁县', '\0', '3', '1', '140600');
INSERT INTO `dns_area` VALUES ('140700', null, '0', '晋中市', '\0', '2', '1', '140000');
INSERT INTO `dns_area` VALUES ('140701', null, '0', '市辖区', '\0', '3', '1', '140700');
INSERT INTO `dns_area` VALUES ('140702', null, '0', '榆次区', '\0', '3', '1', '140700');
INSERT INTO `dns_area` VALUES ('140721', null, '0', '榆社县', '\0', '3', '1', '140700');
INSERT INTO `dns_area` VALUES ('140722', null, '0', '左权县', '\0', '3', '1', '140700');
INSERT INTO `dns_area` VALUES ('140723', null, '0', '和顺县', '\0', '3', '1', '140700');
INSERT INTO `dns_area` VALUES ('140724', null, '0', '昔阳县', '\0', '3', '1', '140700');
INSERT INTO `dns_area` VALUES ('140725', null, '0', '寿阳县', '\0', '3', '1', '140700');
INSERT INTO `dns_area` VALUES ('140726', null, '0', '太谷县', '\0', '3', '1', '140700');
INSERT INTO `dns_area` VALUES ('140727', null, '0', '祁　县', '\0', '3', '1', '140700');
INSERT INTO `dns_area` VALUES ('140728', null, '0', '平遥县', '\0', '3', '1', '140700');
INSERT INTO `dns_area` VALUES ('140729', null, '0', '灵石县', '\0', '3', '1', '140700');
INSERT INTO `dns_area` VALUES ('140781', null, '0', '介休市', '\0', '3', '1', '140700');
INSERT INTO `dns_area` VALUES ('140800', null, '0', '运城市', '\0', '2', '1', '140000');
INSERT INTO `dns_area` VALUES ('140801', null, '0', '市辖区', '\0', '3', '1', '140800');
INSERT INTO `dns_area` VALUES ('140802', null, '0', '盐湖区', '\0', '3', '1', '140800');
INSERT INTO `dns_area` VALUES ('140821', null, '0', '临猗县', '\0', '3', '1', '140800');
INSERT INTO `dns_area` VALUES ('140822', null, '0', '万荣县', '\0', '3', '1', '140800');
INSERT INTO `dns_area` VALUES ('140823', null, '0', '闻喜县', '\0', '3', '1', '140800');
INSERT INTO `dns_area` VALUES ('140824', null, '0', '稷山县', '\0', '3', '1', '140800');
INSERT INTO `dns_area` VALUES ('140825', null, '0', '新绛县', '\0', '3', '1', '140800');
INSERT INTO `dns_area` VALUES ('140826', null, '0', '绛　县', '\0', '3', '1', '140800');
INSERT INTO `dns_area` VALUES ('140827', null, '0', '垣曲县', '\0', '3', '1', '140800');
INSERT INTO `dns_area` VALUES ('140828', null, '0', '夏　县', '\0', '3', '1', '140800');
INSERT INTO `dns_area` VALUES ('140829', null, '0', '平陆县', '\0', '3', '1', '140800');
INSERT INTO `dns_area` VALUES ('140830', null, '0', '芮城县', '\0', '3', '1', '140800');
INSERT INTO `dns_area` VALUES ('140881', null, '0', '永济市', '\0', '3', '1', '140800');
INSERT INTO `dns_area` VALUES ('140882', null, '0', '河津市', '\0', '3', '1', '140800');
INSERT INTO `dns_area` VALUES ('140900', null, '0', '忻州市', '\0', '2', '1', '140000');
INSERT INTO `dns_area` VALUES ('140901', null, '0', '市辖区', '\0', '3', '1', '140900');
INSERT INTO `dns_area` VALUES ('140902', null, '0', '忻府区', '\0', '3', '1', '140900');
INSERT INTO `dns_area` VALUES ('140921', null, '0', '定襄县', '\0', '3', '1', '140900');
INSERT INTO `dns_area` VALUES ('140922', null, '0', '五台县', '\0', '3', '1', '140900');
INSERT INTO `dns_area` VALUES ('140923', null, '0', '代　县', '\0', '3', '1', '140900');
INSERT INTO `dns_area` VALUES ('140924', null, '0', '繁峙县', '\0', '3', '1', '140900');
INSERT INTO `dns_area` VALUES ('140925', null, '0', '宁武县', '\0', '3', '1', '140900');
INSERT INTO `dns_area` VALUES ('140926', null, '0', '静乐县', '\0', '3', '1', '140900');
INSERT INTO `dns_area` VALUES ('140927', null, '0', '神池县', '\0', '3', '1', '140900');
INSERT INTO `dns_area` VALUES ('140928', null, '0', '五寨县', '\0', '3', '1', '140900');
INSERT INTO `dns_area` VALUES ('140929', null, '0', '岢岚县', '\0', '3', '1', '140900');
INSERT INTO `dns_area` VALUES ('140930', null, '0', '河曲县', '\0', '3', '1', '140900');
INSERT INTO `dns_area` VALUES ('140931', null, '0', '保德县', '\0', '3', '1', '140900');
INSERT INTO `dns_area` VALUES ('140932', null, '0', '偏关县', '\0', '3', '1', '140900');
INSERT INTO `dns_area` VALUES ('140981', null, '0', '原平市', '\0', '3', '1', '140900');
INSERT INTO `dns_area` VALUES ('141000', null, '0', '临汾市', '\0', '2', '1', '140000');
INSERT INTO `dns_area` VALUES ('141001', null, '0', '市辖区', '\0', '3', '1', '141000');
INSERT INTO `dns_area` VALUES ('141002', null, '0', '尧都区', '\0', '3', '1', '141000');
INSERT INTO `dns_area` VALUES ('141021', null, '0', '曲沃县', '\0', '3', '1', '141000');
INSERT INTO `dns_area` VALUES ('141022', null, '0', '翼城县', '\0', '3', '1', '141000');
INSERT INTO `dns_area` VALUES ('141023', null, '0', '襄汾县', '\0', '3', '1', '141000');
INSERT INTO `dns_area` VALUES ('141024', null, '0', '洪洞县', '\0', '3', '1', '141000');
INSERT INTO `dns_area` VALUES ('141025', null, '0', '古　县', '\0', '3', '1', '141000');
INSERT INTO `dns_area` VALUES ('141026', null, '0', '安泽县', '\0', '3', '1', '141000');
INSERT INTO `dns_area` VALUES ('141027', null, '0', '浮山县', '\0', '3', '1', '141000');
INSERT INTO `dns_area` VALUES ('141028', null, '0', '吉　县', '\0', '3', '1', '141000');
INSERT INTO `dns_area` VALUES ('141029', null, '0', '乡宁县', '\0', '3', '1', '141000');
INSERT INTO `dns_area` VALUES ('141030', null, '0', '大宁县', '\0', '3', '1', '141000');
INSERT INTO `dns_area` VALUES ('141031', null, '0', '隰　县', '\0', '3', '1', '141000');
INSERT INTO `dns_area` VALUES ('141032', null, '0', '永和县', '\0', '3', '1', '141000');
INSERT INTO `dns_area` VALUES ('141033', null, '0', '蒲　县', '\0', '3', '1', '141000');
INSERT INTO `dns_area` VALUES ('141034', null, '0', '汾西县', '\0', '3', '1', '141000');
INSERT INTO `dns_area` VALUES ('141081', null, '0', '侯马市', '\0', '3', '1', '141000');
INSERT INTO `dns_area` VALUES ('141082', null, '0', '霍州市', '\0', '3', '1', '141000');
INSERT INTO `dns_area` VALUES ('141100', null, '0', '吕梁市', '\0', '2', '1', '140000');
INSERT INTO `dns_area` VALUES ('141101', null, '0', '市辖区', '\0', '3', '1', '141100');
INSERT INTO `dns_area` VALUES ('141102', null, '0', '离石区', '\0', '3', '1', '141100');
INSERT INTO `dns_area` VALUES ('141121', null, '0', '文水县', '\0', '3', '1', '141100');
INSERT INTO `dns_area` VALUES ('141122', null, '0', '交城县', '\0', '3', '1', '141100');
INSERT INTO `dns_area` VALUES ('141123', null, '0', '兴　县', '\0', '3', '1', '141100');
INSERT INTO `dns_area` VALUES ('141124', null, '0', '临　县', '\0', '3', '1', '141100');
INSERT INTO `dns_area` VALUES ('141125', null, '0', '柳林县', '\0', '3', '1', '141100');
INSERT INTO `dns_area` VALUES ('141126', null, '0', '石楼县', '\0', '3', '1', '141100');
INSERT INTO `dns_area` VALUES ('141127', null, '0', '岚　县', '\0', '3', '1', '141100');
INSERT INTO `dns_area` VALUES ('141128', null, '0', '方山县', '\0', '3', '1', '141100');
INSERT INTO `dns_area` VALUES ('141129', null, '0', '中阳县', '\0', '3', '1', '141100');
INSERT INTO `dns_area` VALUES ('141130', null, '0', '交口县', '\0', '3', '1', '141100');
INSERT INTO `dns_area` VALUES ('141181', null, '0', '孝义市', '\0', '3', '1', '141100');
INSERT INTO `dns_area` VALUES ('141182', null, '0', '汾阳市', '\0', '3', '1', '141100');
INSERT INTO `dns_area` VALUES ('150000', null, '0', '内蒙古自治区', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('150100', null, '0', '呼和浩特市', '\0', '2', '1', '150000');
INSERT INTO `dns_area` VALUES ('150101', null, '0', '市辖区', '\0', '3', '1', '150100');
INSERT INTO `dns_area` VALUES ('150102', null, '0', '新城区', '\0', '3', '1', '150100');
INSERT INTO `dns_area` VALUES ('150103', null, '0', '回民区', '\0', '3', '1', '150100');
INSERT INTO `dns_area` VALUES ('150104', null, '0', '玉泉区', '\0', '3', '1', '150100');
INSERT INTO `dns_area` VALUES ('150105', null, '0', '赛罕区', '\0', '3', '1', '150100');
INSERT INTO `dns_area` VALUES ('150121', null, '0', '土默特左旗', '\0', '3', '1', '150100');
INSERT INTO `dns_area` VALUES ('150122', null, '0', '托克托县', '\0', '3', '1', '150100');
INSERT INTO `dns_area` VALUES ('150123', null, '0', '和林格尔县', '\0', '3', '1', '150100');
INSERT INTO `dns_area` VALUES ('150124', null, '0', '清水河县', '\0', '3', '1', '150100');
INSERT INTO `dns_area` VALUES ('150125', null, '0', '武川县', '\0', '3', '1', '150100');
INSERT INTO `dns_area` VALUES ('150200', null, '0', '包头市', '\0', '2', '1', '150000');
INSERT INTO `dns_area` VALUES ('150201', null, '0', '市辖区', '\0', '3', '1', '150200');
INSERT INTO `dns_area` VALUES ('150202', null, '0', '东河区', '\0', '3', '1', '150200');
INSERT INTO `dns_area` VALUES ('150203', null, '0', '昆都仑区', '\0', '3', '1', '150200');
INSERT INTO `dns_area` VALUES ('150204', null, '0', '青山区', '\0', '3', '1', '150200');
INSERT INTO `dns_area` VALUES ('150205', null, '0', '石拐区', '\0', '3', '1', '150200');
INSERT INTO `dns_area` VALUES ('150206', null, '0', '白云矿区', '\0', '3', '1', '150200');
INSERT INTO `dns_area` VALUES ('150207', null, '0', '九原区', '\0', '3', '1', '150200');
INSERT INTO `dns_area` VALUES ('150221', null, '0', '土默特右旗', '\0', '3', '1', '150200');
INSERT INTO `dns_area` VALUES ('150222', null, '0', '固阳县', '\0', '3', '1', '150200');
INSERT INTO `dns_area` VALUES ('150223', null, '0', '达尔罕茂明安联合旗', '\0', '3', '1', '150200');
INSERT INTO `dns_area` VALUES ('150300', null, '0', '乌海市', '\0', '2', '1', '150000');
INSERT INTO `dns_area` VALUES ('150301', null, '0', '市辖区', '\0', '3', '1', '150300');
INSERT INTO `dns_area` VALUES ('150302', null, '0', '海勃湾区', '\0', '3', '1', '150300');
INSERT INTO `dns_area` VALUES ('150303', null, '0', '海南区', '\0', '3', '1', '150300');
INSERT INTO `dns_area` VALUES ('150304', null, '0', '乌达区', '\0', '3', '1', '150300');
INSERT INTO `dns_area` VALUES ('150400', null, '0', '赤峰市', '\0', '2', '1', '150000');
INSERT INTO `dns_area` VALUES ('150401', null, '0', '市辖区', '\0', '3', '1', '150400');
INSERT INTO `dns_area` VALUES ('150402', null, '0', '红山区', '\0', '3', '1', '150400');
INSERT INTO `dns_area` VALUES ('150403', null, '0', '元宝山区', '\0', '3', '1', '150400');
INSERT INTO `dns_area` VALUES ('150404', null, '0', '松山区', '\0', '3', '1', '150400');
INSERT INTO `dns_area` VALUES ('150421', null, '0', '阿鲁科尔沁旗', '\0', '3', '1', '150400');
INSERT INTO `dns_area` VALUES ('150422', null, '0', '巴林左旗', '\0', '3', '1', '150400');
INSERT INTO `dns_area` VALUES ('150423', null, '0', '巴林右旗', '\0', '3', '1', '150400');
INSERT INTO `dns_area` VALUES ('150424', null, '0', '林西县', '\0', '3', '1', '150400');
INSERT INTO `dns_area` VALUES ('150425', null, '0', '克什克腾旗', '\0', '3', '1', '150400');
INSERT INTO `dns_area` VALUES ('150426', null, '0', '翁牛特旗', '\0', '3', '1', '150400');
INSERT INTO `dns_area` VALUES ('150428', null, '0', '喀喇沁旗', '\0', '3', '1', '150400');
INSERT INTO `dns_area` VALUES ('150429', null, '0', '宁城县', '\0', '3', '1', '150400');
INSERT INTO `dns_area` VALUES ('150430', null, '0', '敖汉旗', '\0', '3', '1', '150400');
INSERT INTO `dns_area` VALUES ('150500', null, '0', '通辽市', '\0', '2', '1', '150000');
INSERT INTO `dns_area` VALUES ('150501', null, '0', '市辖区', '\0', '3', '1', '150500');
INSERT INTO `dns_area` VALUES ('150502', null, '0', '科尔沁区', '\0', '3', '1', '150500');
INSERT INTO `dns_area` VALUES ('150521', null, '0', '科尔沁左翼中旗', '\0', '3', '1', '150500');
INSERT INTO `dns_area` VALUES ('150522', null, '0', '科尔沁左翼后旗', '\0', '3', '1', '150500');
INSERT INTO `dns_area` VALUES ('150523', null, '0', '开鲁县', '\0', '3', '1', '150500');
INSERT INTO `dns_area` VALUES ('150524', null, '0', '库伦旗', '\0', '3', '1', '150500');
INSERT INTO `dns_area` VALUES ('150525', null, '0', '奈曼旗', '\0', '3', '1', '150500');
INSERT INTO `dns_area` VALUES ('150526', null, '0', '扎鲁特旗', '\0', '3', '1', '150500');
INSERT INTO `dns_area` VALUES ('150581', null, '0', '霍林郭勒市', '\0', '3', '1', '150500');
INSERT INTO `dns_area` VALUES ('150600', null, '0', '鄂尔多斯市', '\0', '2', '1', '150000');
INSERT INTO `dns_area` VALUES ('150602', null, '0', '东胜区', '\0', '3', '1', '150600');
INSERT INTO `dns_area` VALUES ('150621', null, '0', '达拉特旗', '\0', '3', '1', '150600');
INSERT INTO `dns_area` VALUES ('150622', null, '0', '准格尔旗', '\0', '3', '1', '150600');
INSERT INTO `dns_area` VALUES ('150623', null, '0', '鄂托克前旗', '\0', '3', '1', '150600');
INSERT INTO `dns_area` VALUES ('150624', null, '0', '鄂托克旗', '\0', '3', '1', '150600');
INSERT INTO `dns_area` VALUES ('150625', null, '0', '杭锦旗', '\0', '3', '1', '150600');
INSERT INTO `dns_area` VALUES ('150626', null, '0', '乌审旗', '\0', '3', '1', '150600');
INSERT INTO `dns_area` VALUES ('150627', null, '0', '伊金霍洛旗', '\0', '3', '1', '150600');
INSERT INTO `dns_area` VALUES ('150700', null, '0', '呼伦贝尔市', '\0', '2', '1', '150000');
INSERT INTO `dns_area` VALUES ('150701', null, '0', '市辖区', '\0', '3', '1', '150700');
INSERT INTO `dns_area` VALUES ('150702', null, '0', '海拉尔区', '\0', '3', '1', '150700');
INSERT INTO `dns_area` VALUES ('150721', null, '0', '阿荣旗', '\0', '3', '1', '150700');
INSERT INTO `dns_area` VALUES ('150722', null, '0', '莫力达瓦达斡尔族自治旗', '\0', '3', '1', '150700');
INSERT INTO `dns_area` VALUES ('150723', null, '0', '鄂伦春自治旗', '\0', '3', '1', '150700');
INSERT INTO `dns_area` VALUES ('150724', null, '0', '鄂温克族自治旗', '\0', '3', '1', '150700');
INSERT INTO `dns_area` VALUES ('150725', null, '0', '陈巴尔虎旗', '\0', '3', '1', '150700');
INSERT INTO `dns_area` VALUES ('150726', null, '0', '新巴尔虎左旗', '\0', '3', '1', '150700');
INSERT INTO `dns_area` VALUES ('150727', null, '0', '新巴尔虎右旗', '\0', '3', '1', '150700');
INSERT INTO `dns_area` VALUES ('150781', null, '0', '满洲里市', '\0', '3', '1', '150700');
INSERT INTO `dns_area` VALUES ('150782', null, '0', '牙克石市', '\0', '3', '1', '150700');
INSERT INTO `dns_area` VALUES ('150783', null, '0', '扎兰屯市', '\0', '3', '1', '150700');
INSERT INTO `dns_area` VALUES ('150784', null, '0', '额尔古纳市', '\0', '3', '1', '150700');
INSERT INTO `dns_area` VALUES ('150785', null, '0', '根河市', '\0', '3', '1', '150700');
INSERT INTO `dns_area` VALUES ('150800', null, '0', '巴彦淖尔市', '\0', '2', '1', '150000');
INSERT INTO `dns_area` VALUES ('150801', null, '0', '市辖区', '\0', '3', '1', '150800');
INSERT INTO `dns_area` VALUES ('150802', null, '0', '临河区', '\0', '3', '1', '150800');
INSERT INTO `dns_area` VALUES ('150821', null, '0', '五原县', '\0', '3', '1', '150800');
INSERT INTO `dns_area` VALUES ('150822', null, '0', '磴口县', '\0', '3', '1', '150800');
INSERT INTO `dns_area` VALUES ('150823', null, '0', '乌拉特前旗', '\0', '3', '1', '150800');
INSERT INTO `dns_area` VALUES ('150824', null, '0', '乌拉特中旗', '\0', '3', '1', '150800');
INSERT INTO `dns_area` VALUES ('150825', null, '0', '乌拉特后旗', '\0', '3', '1', '150800');
INSERT INTO `dns_area` VALUES ('150826', null, '0', '杭锦后旗', '\0', '3', '1', '150800');
INSERT INTO `dns_area` VALUES ('150900', null, '0', '乌兰察布市', '\0', '2', '1', '150000');
INSERT INTO `dns_area` VALUES ('150901', null, '0', '市辖区', '\0', '3', '1', '150900');
INSERT INTO `dns_area` VALUES ('150902', null, '0', '集宁区', '\0', '3', '1', '150900');
INSERT INTO `dns_area` VALUES ('150921', null, '0', '卓资县', '\0', '3', '1', '150900');
INSERT INTO `dns_area` VALUES ('150922', null, '0', '化德县', '\0', '3', '1', '150900');
INSERT INTO `dns_area` VALUES ('150923', null, '0', '商都县', '\0', '3', '1', '150900');
INSERT INTO `dns_area` VALUES ('150924', null, '0', '兴和县', '\0', '3', '1', '150900');
INSERT INTO `dns_area` VALUES ('150925', null, '0', '凉城县', '\0', '3', '1', '150900');
INSERT INTO `dns_area` VALUES ('150926', null, '0', '察哈尔右翼前旗', '\0', '3', '1', '150900');
INSERT INTO `dns_area` VALUES ('150927', null, '0', '察哈尔右翼中旗', '\0', '3', '1', '150900');
INSERT INTO `dns_area` VALUES ('150928', null, '0', '察哈尔右翼后旗', '\0', '3', '1', '150900');
INSERT INTO `dns_area` VALUES ('150929', null, '0', '四子王旗', '\0', '3', '1', '150900');
INSERT INTO `dns_area` VALUES ('150981', null, '0', '丰镇市', '\0', '3', '1', '150900');
INSERT INTO `dns_area` VALUES ('152200', null, '0', '兴安盟', '\0', '2', '1', '150000');
INSERT INTO `dns_area` VALUES ('152201', null, '0', '乌兰浩特市', '\0', '3', '1', '152200');
INSERT INTO `dns_area` VALUES ('152202', null, '0', '阿尔山市', '\0', '3', '1', '152200');
INSERT INTO `dns_area` VALUES ('152221', null, '0', '科尔沁右翼前旗', '\0', '3', '1', '152200');
INSERT INTO `dns_area` VALUES ('152222', null, '0', '科尔沁右翼中旗', '\0', '3', '1', '152200');
INSERT INTO `dns_area` VALUES ('152223', null, '0', '扎赉特旗', '\0', '3', '1', '152200');
INSERT INTO `dns_area` VALUES ('152224', null, '0', '突泉县', '\0', '3', '1', '152200');
INSERT INTO `dns_area` VALUES ('152500', null, '0', '锡林郭勒盟', '\0', '2', '1', '150000');
INSERT INTO `dns_area` VALUES ('152501', null, '0', '二连浩特市', '\0', '3', '1', '152500');
INSERT INTO `dns_area` VALUES ('152502', null, '0', '锡林浩特市', '\0', '3', '1', '152500');
INSERT INTO `dns_area` VALUES ('152522', null, '0', '阿巴嘎旗', '\0', '3', '1', '152500');
INSERT INTO `dns_area` VALUES ('152523', null, '0', '苏尼特左旗', '\0', '3', '1', '152500');
INSERT INTO `dns_area` VALUES ('152524', null, '0', '苏尼特右旗', '\0', '3', '1', '152500');
INSERT INTO `dns_area` VALUES ('152525', null, '0', '东乌珠穆沁旗', '\0', '3', '1', '152500');
INSERT INTO `dns_area` VALUES ('152526', null, '0', '西乌珠穆沁旗', '\0', '3', '1', '152500');
INSERT INTO `dns_area` VALUES ('152527', null, '0', '太仆寺旗', '\0', '3', '1', '152500');
INSERT INTO `dns_area` VALUES ('152528', null, '0', '镶黄旗', '\0', '3', '1', '152500');
INSERT INTO `dns_area` VALUES ('152529', null, '0', '正镶白旗', '\0', '3', '1', '152500');
INSERT INTO `dns_area` VALUES ('152530', null, '0', '正蓝旗', '\0', '3', '1', '152500');
INSERT INTO `dns_area` VALUES ('152531', null, '0', '多伦县', '\0', '3', '1', '152500');
INSERT INTO `dns_area` VALUES ('152900', null, '0', '阿拉善盟', '\0', '2', '1', '150000');
INSERT INTO `dns_area` VALUES ('152921', null, '0', '阿拉善左旗', '\0', '3', '1', '152900');
INSERT INTO `dns_area` VALUES ('152922', null, '0', '阿拉善右旗', '\0', '3', '1', '152900');
INSERT INTO `dns_area` VALUES ('152923', null, '0', '额济纳旗', '\0', '3', '1', '152900');
INSERT INTO `dns_area` VALUES ('210000', null, '0', '辽宁省', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('210100', null, '0', '沈阳市', '\0', '2', '1', '210000');
INSERT INTO `dns_area` VALUES ('210101', null, '0', '市辖区', '\0', '3', '1', '210100');
INSERT INTO `dns_area` VALUES ('210102', null, '0', '和平区', '\0', '3', '1', '210100');
INSERT INTO `dns_area` VALUES ('210103', null, '0', '沈河区', '\0', '3', '1', '210100');
INSERT INTO `dns_area` VALUES ('210104', null, '0', '大东区', '\0', '3', '1', '210100');
INSERT INTO `dns_area` VALUES ('210105', null, '0', '皇姑区', '\0', '3', '1', '210100');
INSERT INTO `dns_area` VALUES ('210106', null, '0', '铁西区', '\0', '3', '1', '210100');
INSERT INTO `dns_area` VALUES ('210111', null, '0', '苏家屯区', '\0', '3', '1', '210100');
INSERT INTO `dns_area` VALUES ('210112', null, '0', '东陵区', '\0', '3', '1', '210100');
INSERT INTO `dns_area` VALUES ('210113', null, '0', '新城子区', '\0', '3', '1', '210100');
INSERT INTO `dns_area` VALUES ('210114', null, '0', '于洪区', '\0', '3', '1', '210100');
INSERT INTO `dns_area` VALUES ('210122', null, '0', '辽中县', '\0', '3', '1', '210100');
INSERT INTO `dns_area` VALUES ('210123', null, '0', '康平县', '\0', '3', '1', '210100');
INSERT INTO `dns_area` VALUES ('210124', null, '0', '法库县', '\0', '3', '1', '210100');
INSERT INTO `dns_area` VALUES ('210181', null, '0', '新民市', '\0', '3', '1', '210100');
INSERT INTO `dns_area` VALUES ('210200', null, '0', '大连市', '\0', '2', '1', '210000');
INSERT INTO `dns_area` VALUES ('210201', null, '0', '市辖区', '\0', '3', '1', '210200');
INSERT INTO `dns_area` VALUES ('210202', null, '0', '中山区', '\0', '3', '1', '210200');
INSERT INTO `dns_area` VALUES ('210203', null, '0', '西岗区', '\0', '3', '1', '210200');
INSERT INTO `dns_area` VALUES ('210204', null, '0', '沙河口区', '\0', '3', '1', '210200');
INSERT INTO `dns_area` VALUES ('210211', null, '0', '甘井子区', '\0', '3', '1', '210200');
INSERT INTO `dns_area` VALUES ('210212', null, '0', '旅顺口区', '\0', '3', '1', '210200');
INSERT INTO `dns_area` VALUES ('210213', null, '0', '金州区', '\0', '3', '1', '210200');
INSERT INTO `dns_area` VALUES ('210224', null, '0', '长海县', '\0', '3', '1', '210200');
INSERT INTO `dns_area` VALUES ('210281', null, '0', '瓦房店市', '\0', '3', '1', '210200');
INSERT INTO `dns_area` VALUES ('210282', null, '0', '普兰店市', '\0', '3', '1', '210200');
INSERT INTO `dns_area` VALUES ('210283', null, '0', '庄河市', '\0', '3', '1', '210200');
INSERT INTO `dns_area` VALUES ('210300', null, '0', '鞍山市', '\0', '2', '1', '210000');
INSERT INTO `dns_area` VALUES ('210301', null, '0', '市辖区', '\0', '3', '1', '210300');
INSERT INTO `dns_area` VALUES ('210302', null, '0', '铁东区', '\0', '3', '1', '210300');
INSERT INTO `dns_area` VALUES ('210303', null, '0', '铁西区', '\0', '3', '1', '210300');
INSERT INTO `dns_area` VALUES ('210304', null, '0', '立山区', '\0', '3', '1', '210300');
INSERT INTO `dns_area` VALUES ('210311', null, '0', '千山区', '\0', '3', '1', '210300');
INSERT INTO `dns_area` VALUES ('210321', null, '0', '台安县', '\0', '3', '1', '210300');
INSERT INTO `dns_area` VALUES ('210323', null, '0', '岫岩满族自治县', '\0', '3', '1', '210300');
INSERT INTO `dns_area` VALUES ('210381', null, '0', '海城市', '\0', '3', '1', '210300');
INSERT INTO `dns_area` VALUES ('210400', null, '0', '抚顺市', '\0', '2', '1', '210000');
INSERT INTO `dns_area` VALUES ('210401', null, '0', '市辖区', '\0', '3', '1', '210400');
INSERT INTO `dns_area` VALUES ('210402', null, '0', '新抚区', '\0', '3', '1', '210400');
INSERT INTO `dns_area` VALUES ('210403', null, '0', '东洲区', '\0', '3', '1', '210400');
INSERT INTO `dns_area` VALUES ('210404', null, '0', '望花区', '\0', '3', '1', '210400');
INSERT INTO `dns_area` VALUES ('210411', null, '0', '顺城区', '\0', '3', '1', '210400');
INSERT INTO `dns_area` VALUES ('210421', null, '0', '抚顺县', '\0', '3', '1', '210400');
INSERT INTO `dns_area` VALUES ('210422', null, '0', '新宾满族自治县', '\0', '3', '1', '210400');
INSERT INTO `dns_area` VALUES ('210423', null, '0', '清原满族自治县', '\0', '3', '1', '210400');
INSERT INTO `dns_area` VALUES ('210500', null, '0', '本溪市', '\0', '2', '1', '210000');
INSERT INTO `dns_area` VALUES ('210501', null, '0', '市辖区', '\0', '3', '1', '210500');
INSERT INTO `dns_area` VALUES ('210502', null, '0', '平山区', '\0', '3', '1', '210500');
INSERT INTO `dns_area` VALUES ('210503', null, '0', '溪湖区', '\0', '3', '1', '210500');
INSERT INTO `dns_area` VALUES ('210504', null, '0', '明山区', '\0', '3', '1', '210500');
INSERT INTO `dns_area` VALUES ('210505', null, '0', '南芬区', '\0', '3', '1', '210500');
INSERT INTO `dns_area` VALUES ('210521', null, '0', '本溪满族自治县', '\0', '3', '1', '210500');
INSERT INTO `dns_area` VALUES ('210522', null, '0', '桓仁满族自治县', '\0', '3', '1', '210500');
INSERT INTO `dns_area` VALUES ('210600', null, '0', '丹东市', '\0', '2', '1', '210000');
INSERT INTO `dns_area` VALUES ('210601', null, '0', '市辖区', '\0', '3', '1', '210600');
INSERT INTO `dns_area` VALUES ('210602', null, '0', '元宝区', '\0', '3', '1', '210600');
INSERT INTO `dns_area` VALUES ('210603', null, '0', '振兴区', '\0', '3', '1', '210600');
INSERT INTO `dns_area` VALUES ('210604', null, '0', '振安区', '\0', '3', '1', '210600');
INSERT INTO `dns_area` VALUES ('210624', null, '0', '宽甸满族自治县', '\0', '3', '1', '210600');
INSERT INTO `dns_area` VALUES ('210681', null, '0', '东港市', '\0', '3', '1', '210600');
INSERT INTO `dns_area` VALUES ('210682', null, '0', '凤城市', '\0', '3', '1', '210600');
INSERT INTO `dns_area` VALUES ('210700', null, '0', '锦州市', '\0', '2', '1', '210000');
INSERT INTO `dns_area` VALUES ('210701', null, '0', '市辖区', '\0', '3', '1', '210700');
INSERT INTO `dns_area` VALUES ('210702', null, '0', '古塔区', '\0', '3', '1', '210700');
INSERT INTO `dns_area` VALUES ('210703', null, '0', '凌河区', '\0', '3', '1', '210700');
INSERT INTO `dns_area` VALUES ('210711', null, '0', '太和区', '\0', '3', '1', '210700');
INSERT INTO `dns_area` VALUES ('210726', null, '0', '黑山县', '\0', '3', '1', '210700');
INSERT INTO `dns_area` VALUES ('210727', null, '0', '义　县', '\0', '3', '1', '210700');
INSERT INTO `dns_area` VALUES ('210781', null, '0', '凌海市', '\0', '3', '1', '210700');
INSERT INTO `dns_area` VALUES ('210782', null, '0', '北宁市', '\0', '3', '1', '210700');
INSERT INTO `dns_area` VALUES ('210800', null, '0', '营口市', '\0', '2', '1', '210000');
INSERT INTO `dns_area` VALUES ('210801', null, '0', '市辖区', '\0', '3', '1', '210800');
INSERT INTO `dns_area` VALUES ('210802', null, '0', '站前区', '\0', '3', '1', '210800');
INSERT INTO `dns_area` VALUES ('210803', null, '0', '西市区', '\0', '3', '1', '210800');
INSERT INTO `dns_area` VALUES ('210804', null, '0', '鲅鱼圈区', '\0', '3', '1', '210800');
INSERT INTO `dns_area` VALUES ('210811', null, '0', '老边区', '\0', '3', '1', '210800');
INSERT INTO `dns_area` VALUES ('210881', null, '0', '盖州市', '\0', '3', '1', '210800');
INSERT INTO `dns_area` VALUES ('210882', null, '0', '大石桥市', '\0', '3', '1', '210800');
INSERT INTO `dns_area` VALUES ('210900', null, '0', '阜新市', '\0', '2', '1', '210000');
INSERT INTO `dns_area` VALUES ('210901', null, '0', '市辖区', '\0', '3', '1', '210900');
INSERT INTO `dns_area` VALUES ('210902', null, '0', '海州区', '\0', '3', '1', '210900');
INSERT INTO `dns_area` VALUES ('210903', null, '0', '新邱区', '\0', '3', '1', '210900');
INSERT INTO `dns_area` VALUES ('210904', null, '0', '太平区', '\0', '3', '1', '210900');
INSERT INTO `dns_area` VALUES ('210905', null, '0', '清河门区', '\0', '3', '1', '210900');
INSERT INTO `dns_area` VALUES ('210911', null, '0', '细河区', '\0', '3', '1', '210900');
INSERT INTO `dns_area` VALUES ('210921', null, '0', '阜新蒙古族自治县', '\0', '3', '1', '210900');
INSERT INTO `dns_area` VALUES ('210922', null, '0', '彰武县', '\0', '3', '1', '210900');
INSERT INTO `dns_area` VALUES ('211000', null, '0', '辽阳市', '\0', '2', '1', '210000');
INSERT INTO `dns_area` VALUES ('211001', null, '0', '市辖区', '\0', '3', '1', '211000');
INSERT INTO `dns_area` VALUES ('211002', null, '0', '白塔区', '\0', '3', '1', '211000');
INSERT INTO `dns_area` VALUES ('211003', null, '0', '文圣区', '\0', '3', '1', '211000');
INSERT INTO `dns_area` VALUES ('211004', null, '0', '宏伟区', '\0', '3', '1', '211000');
INSERT INTO `dns_area` VALUES ('211005', null, '0', '弓长岭区', '\0', '3', '1', '211000');
INSERT INTO `dns_area` VALUES ('211011', null, '0', '太子河区', '\0', '3', '1', '211000');
INSERT INTO `dns_area` VALUES ('211021', null, '0', '辽阳县', '\0', '3', '1', '211000');
INSERT INTO `dns_area` VALUES ('211081', null, '0', '灯塔市', '\0', '3', '1', '211000');
INSERT INTO `dns_area` VALUES ('211100', null, '0', '盘锦市', '\0', '2', '1', '210000');
INSERT INTO `dns_area` VALUES ('211101', null, '0', '市辖区', '\0', '3', '1', '211100');
INSERT INTO `dns_area` VALUES ('211102', null, '0', '双台子区', '\0', '3', '1', '211100');
INSERT INTO `dns_area` VALUES ('211103', null, '0', '兴隆台区', '\0', '3', '1', '211100');
INSERT INTO `dns_area` VALUES ('211121', null, '0', '大洼县', '\0', '3', '1', '211100');
INSERT INTO `dns_area` VALUES ('211122', null, '0', '盘山县', '\0', '3', '1', '211100');
INSERT INTO `dns_area` VALUES ('211200', null, '0', '铁岭市', '\0', '2', '1', '210000');
INSERT INTO `dns_area` VALUES ('211201', null, '0', '市辖区', '\0', '3', '1', '211200');
INSERT INTO `dns_area` VALUES ('211202', null, '0', '银州区', '\0', '3', '1', '211200');
INSERT INTO `dns_area` VALUES ('211204', null, '0', '清河区', '\0', '3', '1', '211200');
INSERT INTO `dns_area` VALUES ('211221', null, '0', '铁岭县', '\0', '3', '1', '211200');
INSERT INTO `dns_area` VALUES ('211223', null, '0', '西丰县', '\0', '3', '1', '211200');
INSERT INTO `dns_area` VALUES ('211224', null, '0', '昌图县', '\0', '3', '1', '211200');
INSERT INTO `dns_area` VALUES ('211281', null, '0', '调兵山市', '\0', '3', '1', '211200');
INSERT INTO `dns_area` VALUES ('211282', null, '0', '开原市', '\0', '3', '1', '211200');
INSERT INTO `dns_area` VALUES ('211300', null, '0', '朝阳市', '\0', '2', '1', '210000');
INSERT INTO `dns_area` VALUES ('211301', null, '0', '市辖区', '\0', '3', '1', '211300');
INSERT INTO `dns_area` VALUES ('211302', null, '0', '双塔区', '\0', '3', '1', '211300');
INSERT INTO `dns_area` VALUES ('211303', null, '0', '龙城区', '\0', '3', '1', '211300');
INSERT INTO `dns_area` VALUES ('211321', null, '0', '朝阳县', '\0', '3', '1', '211300');
INSERT INTO `dns_area` VALUES ('211322', null, '0', '建平县', '\0', '3', '1', '211300');
INSERT INTO `dns_area` VALUES ('211324', null, '0', '喀喇沁左翼蒙古族自治县', '\0', '3', '1', '211300');
INSERT INTO `dns_area` VALUES ('211381', null, '0', '北票市', '\0', '3', '1', '211300');
INSERT INTO `dns_area` VALUES ('211382', null, '0', '凌源市', '\0', '3', '1', '211300');
INSERT INTO `dns_area` VALUES ('211400', null, '0', '葫芦岛市', '\0', '2', '1', '210000');
INSERT INTO `dns_area` VALUES ('211401', null, '0', '市辖区', '\0', '3', '1', '211400');
INSERT INTO `dns_area` VALUES ('211402', null, '0', '连山区', '\0', '3', '1', '211400');
INSERT INTO `dns_area` VALUES ('211403', null, '0', '龙港区', '\0', '3', '1', '211400');
INSERT INTO `dns_area` VALUES ('211404', null, '0', '南票区', '\0', '3', '1', '211400');
INSERT INTO `dns_area` VALUES ('211421', null, '0', '绥中县', '\0', '3', '1', '211400');
INSERT INTO `dns_area` VALUES ('211422', null, '0', '建昌县', '\0', '3', '1', '211400');
INSERT INTO `dns_area` VALUES ('211481', null, '0', '兴城市', '\0', '3', '1', '211400');
INSERT INTO `dns_area` VALUES ('220000', null, '0', '吉林省', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('220100', null, '0', '长春市', '\0', '2', '1', '220000');
INSERT INTO `dns_area` VALUES ('220101', null, '0', '市辖区', '\0', '3', '1', '220100');
INSERT INTO `dns_area` VALUES ('220102', null, '0', '南关区', '\0', '3', '1', '220100');
INSERT INTO `dns_area` VALUES ('220103', null, '0', '宽城区', '\0', '3', '1', '220100');
INSERT INTO `dns_area` VALUES ('220104', null, '0', '朝阳区', '\0', '3', '1', '220100');
INSERT INTO `dns_area` VALUES ('220105', null, '0', '二道区', '\0', '3', '1', '220100');
INSERT INTO `dns_area` VALUES ('220106', null, '0', '绿园区', '\0', '3', '1', '220100');
INSERT INTO `dns_area` VALUES ('220112', null, '0', '双阳区', '\0', '3', '1', '220100');
INSERT INTO `dns_area` VALUES ('220122', null, '0', '农安县', '\0', '3', '1', '220100');
INSERT INTO `dns_area` VALUES ('220181', null, '0', '九台市', '\0', '3', '1', '220100');
INSERT INTO `dns_area` VALUES ('220182', null, '0', '榆树市', '\0', '3', '1', '220100');
INSERT INTO `dns_area` VALUES ('220183', null, '0', '德惠市', '\0', '3', '1', '220100');
INSERT INTO `dns_area` VALUES ('220200', null, '0', '吉林市', '\0', '2', '1', '220000');
INSERT INTO `dns_area` VALUES ('220201', null, '0', '市辖区', '\0', '3', '1', '220200');
INSERT INTO `dns_area` VALUES ('220202', null, '0', '昌邑区', '\0', '3', '1', '220200');
INSERT INTO `dns_area` VALUES ('220203', null, '0', '龙潭区', '\0', '3', '1', '220200');
INSERT INTO `dns_area` VALUES ('220204', null, '0', '船营区', '\0', '3', '1', '220200');
INSERT INTO `dns_area` VALUES ('220211', null, '0', '丰满区', '\0', '3', '1', '220200');
INSERT INTO `dns_area` VALUES ('220221', null, '0', '永吉县', '\0', '3', '1', '220200');
INSERT INTO `dns_area` VALUES ('220281', null, '0', '蛟河市', '\0', '3', '1', '220200');
INSERT INTO `dns_area` VALUES ('220282', null, '0', '桦甸市', '\0', '3', '1', '220200');
INSERT INTO `dns_area` VALUES ('220283', null, '0', '舒兰市', '\0', '3', '1', '220200');
INSERT INTO `dns_area` VALUES ('220284', null, '0', '磐石市', '\0', '3', '1', '220200');
INSERT INTO `dns_area` VALUES ('220300', null, '0', '四平市', '\0', '2', '1', '220000');
INSERT INTO `dns_area` VALUES ('220301', null, '0', '市辖区', '\0', '3', '1', '220300');
INSERT INTO `dns_area` VALUES ('220302', null, '0', '铁西区', '\0', '3', '1', '220300');
INSERT INTO `dns_area` VALUES ('220303', null, '0', '铁东区', '\0', '3', '1', '220300');
INSERT INTO `dns_area` VALUES ('220322', null, '0', '梨树县', '\0', '3', '1', '220300');
INSERT INTO `dns_area` VALUES ('220323', null, '0', '伊通满族自治县', '\0', '3', '1', '220300');
INSERT INTO `dns_area` VALUES ('220381', null, '0', '公主岭市', '\0', '3', '1', '220300');
INSERT INTO `dns_area` VALUES ('220382', null, '0', '双辽市', '\0', '3', '1', '220300');
INSERT INTO `dns_area` VALUES ('220400', null, '0', '辽源市', '\0', '2', '1', '220000');
INSERT INTO `dns_area` VALUES ('220401', null, '0', '市辖区', '\0', '3', '1', '220400');
INSERT INTO `dns_area` VALUES ('220402', null, '0', '龙山区', '\0', '3', '1', '220400');
INSERT INTO `dns_area` VALUES ('220403', null, '0', '西安区', '\0', '3', '1', '220400');
INSERT INTO `dns_area` VALUES ('220421', null, '0', '东丰县', '\0', '3', '1', '220400');
INSERT INTO `dns_area` VALUES ('220422', null, '0', '东辽县', '\0', '3', '1', '220400');
INSERT INTO `dns_area` VALUES ('220500', null, '0', '通化市', '\0', '2', '1', '220000');
INSERT INTO `dns_area` VALUES ('220501', null, '0', '市辖区', '\0', '3', '1', '220500');
INSERT INTO `dns_area` VALUES ('220502', null, '0', '东昌区', '\0', '3', '1', '220500');
INSERT INTO `dns_area` VALUES ('220503', null, '0', '二道江区', '\0', '3', '1', '220500');
INSERT INTO `dns_area` VALUES ('220521', null, '0', '通化县', '\0', '3', '1', '220500');
INSERT INTO `dns_area` VALUES ('220523', null, '0', '辉南县', '\0', '3', '1', '220500');
INSERT INTO `dns_area` VALUES ('220524', null, '0', '柳河县', '\0', '3', '1', '220500');
INSERT INTO `dns_area` VALUES ('220581', null, '0', '梅河口市', '\0', '3', '1', '220500');
INSERT INTO `dns_area` VALUES ('220582', null, '0', '集安市', '\0', '3', '1', '220500');
INSERT INTO `dns_area` VALUES ('220600', null, '0', '白山市', '\0', '2', '1', '220000');
INSERT INTO `dns_area` VALUES ('220601', null, '0', '市辖区', '\0', '3', '1', '220600');
INSERT INTO `dns_area` VALUES ('220602', null, '0', '八道江区', '\0', '3', '1', '220600');
INSERT INTO `dns_area` VALUES ('220621', null, '0', '抚松县', '\0', '3', '1', '220600');
INSERT INTO `dns_area` VALUES ('220622', null, '0', '靖宇县', '\0', '3', '1', '220600');
INSERT INTO `dns_area` VALUES ('220623', null, '0', '长白朝鲜族自治县', '\0', '3', '1', '220600');
INSERT INTO `dns_area` VALUES ('220625', null, '0', '江源县', '\0', '3', '1', '220600');
INSERT INTO `dns_area` VALUES ('220681', null, '0', '临江市', '\0', '3', '1', '220600');
INSERT INTO `dns_area` VALUES ('220700', null, '0', '松原市', '\0', '2', '1', '220000');
INSERT INTO `dns_area` VALUES ('220701', null, '0', '市辖区', '\0', '3', '1', '220700');
INSERT INTO `dns_area` VALUES ('220702', null, '0', '宁江区', '\0', '3', '1', '220700');
INSERT INTO `dns_area` VALUES ('220721', null, '0', '前郭尔罗斯蒙古族自治县', '\0', '3', '1', '220700');
INSERT INTO `dns_area` VALUES ('220722', null, '0', '长岭县', '\0', '3', '1', '220700');
INSERT INTO `dns_area` VALUES ('220723', null, '0', '乾安县', '\0', '3', '1', '220700');
INSERT INTO `dns_area` VALUES ('220724', null, '0', '扶余县', '\0', '3', '1', '220700');
INSERT INTO `dns_area` VALUES ('220800', null, '0', '白城市', '\0', '2', '1', '220000');
INSERT INTO `dns_area` VALUES ('220801', null, '0', '市辖区', '\0', '3', '1', '220800');
INSERT INTO `dns_area` VALUES ('220802', null, '0', '洮北区', '\0', '3', '1', '220800');
INSERT INTO `dns_area` VALUES ('220821', null, '0', '镇赉县', '\0', '3', '1', '220800');
INSERT INTO `dns_area` VALUES ('220822', null, '0', '通榆县', '\0', '3', '1', '220800');
INSERT INTO `dns_area` VALUES ('220881', null, '0', '洮南市', '\0', '3', '1', '220800');
INSERT INTO `dns_area` VALUES ('220882', null, '0', '大安市', '\0', '3', '1', '220800');
INSERT INTO `dns_area` VALUES ('222400', null, '0', '延边朝鲜族自治州', '\0', '2', '1', '220000');
INSERT INTO `dns_area` VALUES ('222401', null, '0', '延吉市', '\0', '3', '1', '222400');
INSERT INTO `dns_area` VALUES ('222402', null, '0', '图们市', '\0', '3', '1', '222400');
INSERT INTO `dns_area` VALUES ('222403', null, '0', '敦化市', '\0', '3', '1', '222400');
INSERT INTO `dns_area` VALUES ('222404', null, '0', '珲春市', '\0', '3', '1', '222400');
INSERT INTO `dns_area` VALUES ('222405', null, '0', '龙井市', '\0', '3', '1', '222400');
INSERT INTO `dns_area` VALUES ('222406', null, '0', '和龙市', '\0', '3', '1', '222400');
INSERT INTO `dns_area` VALUES ('222424', null, '0', '汪清县', '\0', '3', '1', '222400');
INSERT INTO `dns_area` VALUES ('222426', null, '0', '安图县', '\0', '3', '1', '222400');
INSERT INTO `dns_area` VALUES ('230000', null, '0', '黑龙江省', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('230100', null, '0', '哈尔滨市', '\0', '2', '1', '230000');
INSERT INTO `dns_area` VALUES ('230101', null, '0', '市辖区', '\0', '3', '1', '230100');
INSERT INTO `dns_area` VALUES ('230102', null, '0', '道里区', '\0', '3', '1', '230100');
INSERT INTO `dns_area` VALUES ('230103', null, '0', '南岗区', '\0', '3', '1', '230100');
INSERT INTO `dns_area` VALUES ('230104', null, '0', '道外区', '\0', '3', '1', '230100');
INSERT INTO `dns_area` VALUES ('230106', null, '0', '香坊区', '\0', '3', '1', '230100');
INSERT INTO `dns_area` VALUES ('230107', null, '0', '动力区', '\0', '3', '1', '230100');
INSERT INTO `dns_area` VALUES ('230108', null, '0', '平房区', '\0', '3', '1', '230100');
INSERT INTO `dns_area` VALUES ('230109', null, '0', '松北区', '\0', '3', '1', '230100');
INSERT INTO `dns_area` VALUES ('230111', null, '0', '呼兰区', '\0', '3', '1', '230100');
INSERT INTO `dns_area` VALUES ('230123', null, '0', '依兰县', '\0', '3', '1', '230100');
INSERT INTO `dns_area` VALUES ('230124', null, '0', '方正县', '\0', '3', '1', '230100');
INSERT INTO `dns_area` VALUES ('230125', null, '0', '宾　县', '\0', '3', '1', '230100');
INSERT INTO `dns_area` VALUES ('230126', null, '0', '巴彦县', '\0', '3', '1', '230100');
INSERT INTO `dns_area` VALUES ('230127', null, '0', '木兰县', '\0', '3', '1', '230100');
INSERT INTO `dns_area` VALUES ('230128', null, '0', '通河县', '\0', '3', '1', '230100');
INSERT INTO `dns_area` VALUES ('230129', null, '0', '延寿县', '\0', '3', '1', '230100');
INSERT INTO `dns_area` VALUES ('230181', null, '0', '阿城市', '\0', '3', '1', '230100');
INSERT INTO `dns_area` VALUES ('230182', null, '0', '双城市', '\0', '3', '1', '230100');
INSERT INTO `dns_area` VALUES ('230183', null, '0', '尚志市', '\0', '3', '1', '230100');
INSERT INTO `dns_area` VALUES ('230184', null, '0', '五常市', '\0', '3', '1', '230100');
INSERT INTO `dns_area` VALUES ('230200', null, '0', '齐齐哈尔市', '\0', '2', '1', '230000');
INSERT INTO `dns_area` VALUES ('230201', null, '0', '市辖区', '\0', '3', '1', '230200');
INSERT INTO `dns_area` VALUES ('230202', null, '0', '龙沙区', '\0', '3', '1', '230200');
INSERT INTO `dns_area` VALUES ('230203', null, '0', '建华区', '\0', '3', '1', '230200');
INSERT INTO `dns_area` VALUES ('230204', null, '0', '铁锋区', '\0', '3', '1', '230200');
INSERT INTO `dns_area` VALUES ('230205', null, '0', '昂昂溪区', '\0', '3', '1', '230200');
INSERT INTO `dns_area` VALUES ('230206', null, '0', '富拉尔基区', '\0', '3', '1', '230200');
INSERT INTO `dns_area` VALUES ('230207', null, '0', '碾子山区', '\0', '3', '1', '230200');
INSERT INTO `dns_area` VALUES ('230208', null, '0', '梅里斯达斡尔族区', '\0', '3', '1', '230200');
INSERT INTO `dns_area` VALUES ('230221', null, '0', '龙江县', '\0', '3', '1', '230200');
INSERT INTO `dns_area` VALUES ('230223', null, '0', '依安县', '\0', '3', '1', '230200');
INSERT INTO `dns_area` VALUES ('230224', null, '0', '泰来县', '\0', '3', '1', '230200');
INSERT INTO `dns_area` VALUES ('230225', null, '0', '甘南县', '\0', '3', '1', '230200');
INSERT INTO `dns_area` VALUES ('230227', null, '0', '富裕县', '\0', '3', '1', '230200');
INSERT INTO `dns_area` VALUES ('230229', null, '0', '克山县', '\0', '3', '1', '230200');
INSERT INTO `dns_area` VALUES ('230230', null, '0', '克东县', '\0', '3', '1', '230200');
INSERT INTO `dns_area` VALUES ('230231', null, '0', '拜泉县', '\0', '3', '1', '230200');
INSERT INTO `dns_area` VALUES ('230281', null, '0', '讷河市', '\0', '3', '1', '230200');
INSERT INTO `dns_area` VALUES ('230300', null, '0', '鸡西市', '\0', '2', '1', '230000');
INSERT INTO `dns_area` VALUES ('230301', null, '0', '市辖区', '\0', '3', '1', '230300');
INSERT INTO `dns_area` VALUES ('230302', null, '0', '鸡冠区', '\0', '3', '1', '230300');
INSERT INTO `dns_area` VALUES ('230303', null, '0', '恒山区', '\0', '3', '1', '230300');
INSERT INTO `dns_area` VALUES ('230304', null, '0', '滴道区', '\0', '3', '1', '230300');
INSERT INTO `dns_area` VALUES ('230305', null, '0', '梨树区', '\0', '3', '1', '230300');
INSERT INTO `dns_area` VALUES ('230306', null, '0', '城子河区', '\0', '3', '1', '230300');
INSERT INTO `dns_area` VALUES ('230307', null, '0', '麻山区', '\0', '3', '1', '230300');
INSERT INTO `dns_area` VALUES ('230321', null, '0', '鸡东县', '\0', '3', '1', '230300');
INSERT INTO `dns_area` VALUES ('230381', null, '0', '虎林市', '\0', '3', '1', '230300');
INSERT INTO `dns_area` VALUES ('230382', null, '0', '密山市', '\0', '3', '1', '230300');
INSERT INTO `dns_area` VALUES ('230400', null, '0', '鹤岗市', '\0', '2', '1', '230000');
INSERT INTO `dns_area` VALUES ('230401', null, '0', '市辖区', '\0', '3', '1', '230400');
INSERT INTO `dns_area` VALUES ('230402', null, '0', '向阳区', '\0', '3', '1', '230400');
INSERT INTO `dns_area` VALUES ('230403', null, '0', '工农区', '\0', '3', '1', '230400');
INSERT INTO `dns_area` VALUES ('230404', null, '0', '南山区', '\0', '3', '1', '230400');
INSERT INTO `dns_area` VALUES ('230405', null, '0', '兴安区', '\0', '3', '1', '230400');
INSERT INTO `dns_area` VALUES ('230406', null, '0', '东山区', '\0', '3', '1', '230400');
INSERT INTO `dns_area` VALUES ('230407', null, '0', '兴山区', '\0', '3', '1', '230400');
INSERT INTO `dns_area` VALUES ('230421', null, '0', '萝北县', '\0', '3', '1', '230400');
INSERT INTO `dns_area` VALUES ('230422', null, '0', '绥滨县', '\0', '3', '1', '230400');
INSERT INTO `dns_area` VALUES ('230500', null, '0', '双鸭山市', '\0', '2', '1', '230000');
INSERT INTO `dns_area` VALUES ('230501', null, '0', '市辖区', '\0', '3', '1', '230500');
INSERT INTO `dns_area` VALUES ('230502', null, '0', '尖山区', '\0', '3', '1', '230500');
INSERT INTO `dns_area` VALUES ('230503', null, '0', '岭东区', '\0', '3', '1', '230500');
INSERT INTO `dns_area` VALUES ('230505', null, '0', '四方台区', '\0', '3', '1', '230500');
INSERT INTO `dns_area` VALUES ('230506', null, '0', '宝山区', '\0', '3', '1', '230500');
INSERT INTO `dns_area` VALUES ('230521', null, '0', '集贤县', '\0', '3', '1', '230500');
INSERT INTO `dns_area` VALUES ('230522', null, '0', '友谊县', '\0', '3', '1', '230500');
INSERT INTO `dns_area` VALUES ('230523', null, '0', '宝清县', '\0', '3', '1', '230500');
INSERT INTO `dns_area` VALUES ('230524', null, '0', '饶河县', '\0', '3', '1', '230500');
INSERT INTO `dns_area` VALUES ('230600', null, '0', '大庆市', '\0', '2', '1', '230000');
INSERT INTO `dns_area` VALUES ('230601', null, '0', '市辖区', '\0', '3', '1', '230600');
INSERT INTO `dns_area` VALUES ('230602', null, '0', '萨尔图区', '\0', '3', '1', '230600');
INSERT INTO `dns_area` VALUES ('230603', null, '0', '龙凤区', '\0', '3', '1', '230600');
INSERT INTO `dns_area` VALUES ('230604', null, '0', '让胡路区', '\0', '3', '1', '230600');
INSERT INTO `dns_area` VALUES ('230605', null, '0', '红岗区', '\0', '3', '1', '230600');
INSERT INTO `dns_area` VALUES ('230606', null, '0', '大同区', '\0', '3', '1', '230600');
INSERT INTO `dns_area` VALUES ('230621', null, '0', '肇州县', '\0', '3', '1', '230600');
INSERT INTO `dns_area` VALUES ('230622', null, '0', '肇源县', '\0', '3', '1', '230600');
INSERT INTO `dns_area` VALUES ('230623', null, '0', '林甸县', '\0', '3', '1', '230600');
INSERT INTO `dns_area` VALUES ('230624', null, '0', '杜尔伯特蒙古族自治县', '\0', '3', '1', '230600');
INSERT INTO `dns_area` VALUES ('230700', null, '0', '伊春市', '\0', '2', '1', '230000');
INSERT INTO `dns_area` VALUES ('230701', null, '0', '市辖区', '\0', '3', '1', '230700');
INSERT INTO `dns_area` VALUES ('230702', null, '0', '伊春区', '\0', '3', '1', '230700');
INSERT INTO `dns_area` VALUES ('230703', null, '0', '南岔区', '\0', '3', '1', '230700');
INSERT INTO `dns_area` VALUES ('230704', null, '0', '友好区', '\0', '3', '1', '230700');
INSERT INTO `dns_area` VALUES ('230705', null, '0', '西林区', '\0', '3', '1', '230700');
INSERT INTO `dns_area` VALUES ('230706', null, '0', '翠峦区', '\0', '3', '1', '230700');
INSERT INTO `dns_area` VALUES ('230707', null, '0', '新青区', '\0', '3', '1', '230700');
INSERT INTO `dns_area` VALUES ('230708', null, '0', '美溪区', '\0', '3', '1', '230700');
INSERT INTO `dns_area` VALUES ('230709', null, '0', '金山屯区', '\0', '3', '1', '230700');
INSERT INTO `dns_area` VALUES ('230710', null, '0', '五营区', '\0', '3', '1', '230700');
INSERT INTO `dns_area` VALUES ('230711', null, '0', '乌马河区', '\0', '3', '1', '230700');
INSERT INTO `dns_area` VALUES ('230712', null, '0', '汤旺河区', '\0', '3', '1', '230700');
INSERT INTO `dns_area` VALUES ('230713', null, '0', '带岭区', '\0', '3', '1', '230700');
INSERT INTO `dns_area` VALUES ('230714', null, '0', '乌伊岭区', '\0', '3', '1', '230700');
INSERT INTO `dns_area` VALUES ('230715', null, '0', '红星区', '\0', '3', '1', '230700');
INSERT INTO `dns_area` VALUES ('230716', null, '0', '上甘岭区', '\0', '3', '1', '230700');
INSERT INTO `dns_area` VALUES ('230722', null, '0', '嘉荫县', '\0', '3', '1', '230700');
INSERT INTO `dns_area` VALUES ('230781', null, '0', '铁力市', '\0', '3', '1', '230700');
INSERT INTO `dns_area` VALUES ('230800', null, '0', '佳木斯市', '\0', '2', '1', '230000');
INSERT INTO `dns_area` VALUES ('230801', null, '0', '市辖区', '\0', '3', '1', '230800');
INSERT INTO `dns_area` VALUES ('230802', null, '0', '永红区', '\0', '3', '1', '230800');
INSERT INTO `dns_area` VALUES ('230803', null, '0', '向阳区', '\0', '3', '1', '230800');
INSERT INTO `dns_area` VALUES ('230804', null, '0', '前进区', '\0', '3', '1', '230800');
INSERT INTO `dns_area` VALUES ('230805', null, '0', '东风区', '\0', '3', '1', '230800');
INSERT INTO `dns_area` VALUES ('230811', null, '0', '郊　区', '\0', '3', '1', '230800');
INSERT INTO `dns_area` VALUES ('230822', null, '0', '桦南县', '\0', '3', '1', '230800');
INSERT INTO `dns_area` VALUES ('230826', null, '0', '桦川县', '\0', '3', '1', '230800');
INSERT INTO `dns_area` VALUES ('230828', null, '0', '汤原县', '\0', '3', '1', '230800');
INSERT INTO `dns_area` VALUES ('230833', null, '0', '抚远县', '\0', '3', '1', '230800');
INSERT INTO `dns_area` VALUES ('230881', null, '0', '同江市', '\0', '3', '1', '230800');
INSERT INTO `dns_area` VALUES ('230882', null, '0', '富锦市', '\0', '3', '1', '230800');
INSERT INTO `dns_area` VALUES ('230900', null, '0', '七台河市', '\0', '2', '1', '230000');
INSERT INTO `dns_area` VALUES ('230901', null, '0', '市辖区', '\0', '3', '1', '230900');
INSERT INTO `dns_area` VALUES ('230902', null, '0', '新兴区', '\0', '3', '1', '230900');
INSERT INTO `dns_area` VALUES ('230903', null, '0', '桃山区', '\0', '3', '1', '230900');
INSERT INTO `dns_area` VALUES ('230904', null, '0', '茄子河区', '\0', '3', '1', '230900');
INSERT INTO `dns_area` VALUES ('230921', null, '0', '勃利县', '\0', '3', '1', '230900');
INSERT INTO `dns_area` VALUES ('231000', null, '0', '牡丹江市', '\0', '2', '1', '230000');
INSERT INTO `dns_area` VALUES ('231001', null, '0', '市辖区', '\0', '3', '1', '231000');
INSERT INTO `dns_area` VALUES ('231002', null, '0', '东安区', '\0', '3', '1', '231000');
INSERT INTO `dns_area` VALUES ('231003', null, '0', '阳明区', '\0', '3', '1', '231000');
INSERT INTO `dns_area` VALUES ('231004', null, '0', '爱民区', '\0', '3', '1', '231000');
INSERT INTO `dns_area` VALUES ('231005', null, '0', '西安区', '\0', '3', '1', '231000');
INSERT INTO `dns_area` VALUES ('231024', null, '0', '东宁县', '\0', '3', '1', '231000');
INSERT INTO `dns_area` VALUES ('231025', null, '0', '林口县', '\0', '3', '1', '231000');
INSERT INTO `dns_area` VALUES ('231081', null, '0', '绥芬河市', '\0', '3', '1', '231000');
INSERT INTO `dns_area` VALUES ('231083', null, '0', '海林市', '\0', '3', '1', '231000');
INSERT INTO `dns_area` VALUES ('231084', null, '0', '宁安市', '\0', '3', '1', '231000');
INSERT INTO `dns_area` VALUES ('231085', null, '0', '穆棱市', '\0', '3', '1', '231000');
INSERT INTO `dns_area` VALUES ('231100', null, '0', '黑河市', '\0', '2', '1', '230000');
INSERT INTO `dns_area` VALUES ('231101', null, '0', '市辖区', '\0', '3', '1', '231100');
INSERT INTO `dns_area` VALUES ('231102', null, '0', '爱辉区', '\0', '3', '1', '231100');
INSERT INTO `dns_area` VALUES ('231121', null, '0', '嫩江县', '\0', '3', '1', '231100');
INSERT INTO `dns_area` VALUES ('231123', null, '0', '逊克县', '\0', '3', '1', '231100');
INSERT INTO `dns_area` VALUES ('231124', null, '0', '孙吴县', '\0', '3', '1', '231100');
INSERT INTO `dns_area` VALUES ('231181', null, '0', '北安市', '\0', '3', '1', '231100');
INSERT INTO `dns_area` VALUES ('231182', null, '0', '五大连池市', '\0', '3', '1', '231100');
INSERT INTO `dns_area` VALUES ('231200', null, '0', '绥化市', '\0', '2', '1', '230000');
INSERT INTO `dns_area` VALUES ('231201', null, '0', '市辖区', '\0', '3', '1', '231200');
INSERT INTO `dns_area` VALUES ('231202', null, '0', '北林区', '\0', '3', '1', '231200');
INSERT INTO `dns_area` VALUES ('231221', null, '0', '望奎县', '\0', '3', '1', '231200');
INSERT INTO `dns_area` VALUES ('231222', null, '0', '兰西县', '\0', '3', '1', '231200');
INSERT INTO `dns_area` VALUES ('231223', null, '0', '青冈县', '\0', '3', '1', '231200');
INSERT INTO `dns_area` VALUES ('231224', null, '0', '庆安县', '\0', '3', '1', '231200');
INSERT INTO `dns_area` VALUES ('231225', null, '0', '明水县', '\0', '3', '1', '231200');
INSERT INTO `dns_area` VALUES ('231226', null, '0', '绥棱县', '\0', '3', '1', '231200');
INSERT INTO `dns_area` VALUES ('231281', null, '0', '安达市', '\0', '3', '1', '231200');
INSERT INTO `dns_area` VALUES ('231282', null, '0', '肇东市', '\0', '3', '1', '231200');
INSERT INTO `dns_area` VALUES ('231283', null, '0', '海伦市', '\0', '3', '1', '231200');
INSERT INTO `dns_area` VALUES ('232700', null, '0', '大兴安岭地区', '\0', '2', '1', '230000');
INSERT INTO `dns_area` VALUES ('232721', null, '0', '呼玛县', '\0', '3', '1', '232700');
INSERT INTO `dns_area` VALUES ('232722', null, '0', '塔河县', '\0', '3', '1', '232700');
INSERT INTO `dns_area` VALUES ('232723', null, '0', '漠河县', '\0', '3', '1', '232700');
INSERT INTO `dns_area` VALUES ('310000', null, '0', '上海市', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('310100', null, '0', '上海(市辖区)', '\0', '2', '1', '310000');
INSERT INTO `dns_area` VALUES ('310101', null, '0', '黄浦区', '\0', '3', '1', '310100');
INSERT INTO `dns_area` VALUES ('310103', null, '0', '卢湾区', '\0', '3', '1', '310100');
INSERT INTO `dns_area` VALUES ('310104', null, '0', '徐汇区', '\0', '3', '1', '310100');
INSERT INTO `dns_area` VALUES ('310105', null, '0', '长宁区', '\0', '3', '1', '310100');
INSERT INTO `dns_area` VALUES ('310106', null, '0', '静安区', '\0', '3', '1', '310100');
INSERT INTO `dns_area` VALUES ('310107', null, '0', '普陀区', '\0', '3', '1', '310100');
INSERT INTO `dns_area` VALUES ('310108', null, '0', '闸北区', '\0', '3', '1', '310100');
INSERT INTO `dns_area` VALUES ('310109', null, '0', '虹口区', '\0', '3', '1', '310100');
INSERT INTO `dns_area` VALUES ('310110', null, '0', '杨浦区', '\0', '3', '1', '310100');
INSERT INTO `dns_area` VALUES ('310112', null, '0', '闵行区', '\0', '3', '1', '310100');
INSERT INTO `dns_area` VALUES ('310113', null, '0', '宝山区', '\0', '3', '1', '310100');
INSERT INTO `dns_area` VALUES ('310114', null, '0', '嘉定区', '\0', '3', '1', '310100');
INSERT INTO `dns_area` VALUES ('310115', null, '0', '浦东新区', '\0', '3', '1', '310100');
INSERT INTO `dns_area` VALUES ('310116', null, '0', '金山区', '\0', '3', '1', '310100');
INSERT INTO `dns_area` VALUES ('310117', null, '0', '松江区', '\0', '3', '1', '310100');
INSERT INTO `dns_area` VALUES ('310118', null, '0', '青浦区', '\0', '3', '1', '310100');
INSERT INTO `dns_area` VALUES ('310119', null, '0', '南汇区', '\0', '3', '1', '310100');
INSERT INTO `dns_area` VALUES ('310120', null, '0', '奉贤区', '\0', '3', '1', '310100');
INSERT INTO `dns_area` VALUES ('310200', null, '0', '上海(县)', '\0', '2', '1', '310000');
INSERT INTO `dns_area` VALUES ('310230', null, '0', '崇明县', '\0', '3', '1', '310200');
INSERT INTO `dns_area` VALUES ('320000', null, '0', '江苏省', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('320100', null, '0', '南京市', '\0', '2', '1', '320000');
INSERT INTO `dns_area` VALUES ('320101', null, '0', '市辖区', '\0', '3', '1', '320100');
INSERT INTO `dns_area` VALUES ('320102', null, '0', '玄武区', '\0', '3', '1', '320100');
INSERT INTO `dns_area` VALUES ('320103', null, '0', '白下区', '\0', '3', '1', '320100');
INSERT INTO `dns_area` VALUES ('320104', null, '0', '秦淮区', '\0', '3', '1', '320100');
INSERT INTO `dns_area` VALUES ('320105', null, '0', '建邺区', '\0', '3', '1', '320100');
INSERT INTO `dns_area` VALUES ('320106', null, '0', '鼓楼区', '\0', '3', '1', '320100');
INSERT INTO `dns_area` VALUES ('320107', null, '0', '下关区', '\0', '3', '1', '320100');
INSERT INTO `dns_area` VALUES ('320111', null, '0', '浦口区', '\0', '3', '1', '320100');
INSERT INTO `dns_area` VALUES ('320113', null, '0', '栖霞区', '\0', '3', '1', '320100');
INSERT INTO `dns_area` VALUES ('320114', null, '0', '雨花台区', '\0', '3', '1', '320100');
INSERT INTO `dns_area` VALUES ('320115', null, '0', '江宁区', '\0', '3', '1', '320100');
INSERT INTO `dns_area` VALUES ('320116', null, '0', '六合区', '\0', '3', '1', '320100');
INSERT INTO `dns_area` VALUES ('320124', null, '0', '溧水县', '\0', '3', '1', '320100');
INSERT INTO `dns_area` VALUES ('320125', null, '0', '高淳县', '\0', '3', '1', '320100');
INSERT INTO `dns_area` VALUES ('320200', null, '0', '无锡市', '\0', '2', '1', '320000');
INSERT INTO `dns_area` VALUES ('320201', null, '0', '市辖区', '\0', '3', '1', '320200');
INSERT INTO `dns_area` VALUES ('320202', null, '0', '崇安区', '\0', '3', '1', '320200');
INSERT INTO `dns_area` VALUES ('320203', null, '0', '南长区', '\0', '3', '1', '320200');
INSERT INTO `dns_area` VALUES ('320204', null, '0', '北塘区', '\0', '3', '1', '320200');
INSERT INTO `dns_area` VALUES ('320205', null, '0', '锡山区', '\0', '3', '1', '320200');
INSERT INTO `dns_area` VALUES ('320206', null, '0', '惠山区', '\0', '3', '1', '320200');
INSERT INTO `dns_area` VALUES ('320211', null, '0', '滨湖区', '\0', '3', '1', '320200');
INSERT INTO `dns_area` VALUES ('320281', null, '0', '江阴市', '\0', '3', '1', '320200');
INSERT INTO `dns_area` VALUES ('320282', null, '0', '宜兴市', '\0', '3', '1', '320200');
INSERT INTO `dns_area` VALUES ('320300', null, '0', '徐州市', '\0', '2', '1', '320000');
INSERT INTO `dns_area` VALUES ('320301', null, '0', '市辖区', '\0', '3', '1', '320300');
INSERT INTO `dns_area` VALUES ('320302', null, '0', '鼓楼区', '\0', '3', '1', '320300');
INSERT INTO `dns_area` VALUES ('320303', null, '0', '云龙区', '\0', '3', '1', '320300');
INSERT INTO `dns_area` VALUES ('320304', null, '0', '九里区', '\0', '3', '1', '320300');
INSERT INTO `dns_area` VALUES ('320305', null, '0', '贾汪区', '\0', '3', '1', '320300');
INSERT INTO `dns_area` VALUES ('320311', null, '0', '泉山区', '\0', '3', '1', '320300');
INSERT INTO `dns_area` VALUES ('320321', null, '0', '丰　县', '\0', '3', '1', '320300');
INSERT INTO `dns_area` VALUES ('320322', null, '0', '沛　县', '\0', '3', '1', '320300');
INSERT INTO `dns_area` VALUES ('320323', null, '0', '铜山县', '\0', '3', '1', '320300');
INSERT INTO `dns_area` VALUES ('320324', null, '0', '睢宁县', '\0', '3', '1', '320300');
INSERT INTO `dns_area` VALUES ('320381', null, '0', '新沂市', '\0', '3', '1', '320300');
INSERT INTO `dns_area` VALUES ('320382', null, '0', '邳州市', '\0', '3', '1', '320300');
INSERT INTO `dns_area` VALUES ('320400', null, '0', '常州市', '\0', '2', '1', '320000');
INSERT INTO `dns_area` VALUES ('320401', null, '0', '市辖区', '\0', '3', '1', '320400');
INSERT INTO `dns_area` VALUES ('320402', null, '0', '天宁区', '\0', '3', '1', '320400');
INSERT INTO `dns_area` VALUES ('320404', null, '0', '钟楼区', '\0', '3', '1', '320400');
INSERT INTO `dns_area` VALUES ('320405', null, '0', '戚墅堰区', '\0', '3', '1', '320400');
INSERT INTO `dns_area` VALUES ('320411', null, '0', '新北区', '\0', '3', '1', '320400');
INSERT INTO `dns_area` VALUES ('320412', null, '0', '武进区', '\0', '3', '1', '320400');
INSERT INTO `dns_area` VALUES ('320481', null, '0', '溧阳市', '\0', '3', '1', '320400');
INSERT INTO `dns_area` VALUES ('320482', null, '0', '金坛市', '\0', '3', '1', '320400');
INSERT INTO `dns_area` VALUES ('320500', null, '0', '苏州市', '\0', '2', '1', '320000');
INSERT INTO `dns_area` VALUES ('320501', null, '0', '市辖区', '\0', '3', '1', '320500');
INSERT INTO `dns_area` VALUES ('320502', null, '0', '沧浪区', '\0', '3', '1', '320500');
INSERT INTO `dns_area` VALUES ('320503', null, '0', '平江区', '\0', '3', '1', '320500');
INSERT INTO `dns_area` VALUES ('320504', null, '0', '金阊区', '\0', '3', '1', '320500');
INSERT INTO `dns_area` VALUES ('320505', null, '0', '虎丘区', '\0', '3', '1', '320500');
INSERT INTO `dns_area` VALUES ('320506', null, '0', '吴中区', '\0', '3', '1', '320500');
INSERT INTO `dns_area` VALUES ('320507', null, '0', '相城区', '\0', '3', '1', '320500');
INSERT INTO `dns_area` VALUES ('320581', null, '0', '常熟市', '\0', '3', '1', '320500');
INSERT INTO `dns_area` VALUES ('320582', null, '0', '张家港市', '\0', '3', '1', '320500');
INSERT INTO `dns_area` VALUES ('320583', null, '0', '昆山市', '\0', '3', '1', '320500');
INSERT INTO `dns_area` VALUES ('320584', null, '0', '吴江市', '\0', '3', '1', '320500');
INSERT INTO `dns_area` VALUES ('320585', null, '0', '太仓市', '\0', '3', '1', '320500');
INSERT INTO `dns_area` VALUES ('320600', null, '0', '南通市', '\0', '2', '1', '320000');
INSERT INTO `dns_area` VALUES ('320601', null, '0', '市辖区', '\0', '3', '1', '320600');
INSERT INTO `dns_area` VALUES ('320602', null, '0', '崇川区', '\0', '3', '1', '320600');
INSERT INTO `dns_area` VALUES ('320611', null, '0', '港闸区', '\0', '3', '1', '320600');
INSERT INTO `dns_area` VALUES ('320621', null, '0', '海安县', '\0', '3', '1', '320600');
INSERT INTO `dns_area` VALUES ('320623', null, '0', '如东县', '\0', '3', '1', '320600');
INSERT INTO `dns_area` VALUES ('320681', null, '0', '启东市', '\0', '3', '1', '320600');
INSERT INTO `dns_area` VALUES ('320682', null, '0', '如皋市', '\0', '3', '1', '320600');
INSERT INTO `dns_area` VALUES ('320683', null, '0', '通州市', '\0', '3', '1', '320600');
INSERT INTO `dns_area` VALUES ('320684', null, '0', '海门市', '\0', '3', '1', '320600');
INSERT INTO `dns_area` VALUES ('320700', null, '0', '连云港市', '\0', '2', '1', '320000');
INSERT INTO `dns_area` VALUES ('320701', null, '0', '市辖区', '\0', '3', '1', '320700');
INSERT INTO `dns_area` VALUES ('320703', null, '0', '连云区', '\0', '3', '1', '320700');
INSERT INTO `dns_area` VALUES ('320705', null, '0', '新浦区', '\0', '3', '1', '320700');
INSERT INTO `dns_area` VALUES ('320706', null, '0', '海州区', '\0', '3', '1', '320700');
INSERT INTO `dns_area` VALUES ('320721', null, '0', '赣榆县', '\0', '3', '1', '320700');
INSERT INTO `dns_area` VALUES ('320722', null, '0', '东海县', '\0', '3', '1', '320700');
INSERT INTO `dns_area` VALUES ('320723', null, '0', '灌云县', '\0', '3', '1', '320700');
INSERT INTO `dns_area` VALUES ('320724', null, '0', '灌南县', '\0', '3', '1', '320700');
INSERT INTO `dns_area` VALUES ('320800', null, '0', '淮安市', '\0', '2', '1', '320000');
INSERT INTO `dns_area` VALUES ('320801', null, '0', '市辖区', '\0', '3', '1', '320800');
INSERT INTO `dns_area` VALUES ('320802', null, '0', '清河区', '\0', '3', '1', '320800');
INSERT INTO `dns_area` VALUES ('320803', null, '0', '楚州区', '\0', '3', '1', '320800');
INSERT INTO `dns_area` VALUES ('320804', null, '0', '淮阴区', '\0', '3', '1', '320800');
INSERT INTO `dns_area` VALUES ('320811', null, '0', '清浦区', '\0', '3', '1', '320800');
INSERT INTO `dns_area` VALUES ('320826', null, '0', '涟水县', '\0', '3', '1', '320800');
INSERT INTO `dns_area` VALUES ('320829', null, '0', '洪泽县', '\0', '3', '1', '320800');
INSERT INTO `dns_area` VALUES ('320830', null, '0', '盱眙县', '\0', '3', '1', '320800');
INSERT INTO `dns_area` VALUES ('320831', null, '0', '金湖县', '\0', '3', '1', '320800');
INSERT INTO `dns_area` VALUES ('320900', null, '0', '盐城市', '\0', '2', '1', '320000');
INSERT INTO `dns_area` VALUES ('320901', null, '0', '市辖区', '\0', '3', '1', '320900');
INSERT INTO `dns_area` VALUES ('320902', null, '0', '亭湖区', '\0', '3', '1', '320900');
INSERT INTO `dns_area` VALUES ('320903', null, '0', '盐都区', '\0', '3', '1', '320900');
INSERT INTO `dns_area` VALUES ('320921', null, '0', '响水县', '\0', '3', '1', '320900');
INSERT INTO `dns_area` VALUES ('320922', null, '0', '滨海县', '\0', '3', '1', '320900');
INSERT INTO `dns_area` VALUES ('320923', null, '0', '阜宁县', '\0', '3', '1', '320900');
INSERT INTO `dns_area` VALUES ('320924', null, '0', '射阳县', '\0', '3', '1', '320900');
INSERT INTO `dns_area` VALUES ('320925', null, '0', '建湖县', '\0', '3', '1', '320900');
INSERT INTO `dns_area` VALUES ('320981', null, '0', '东台市', '\0', '3', '1', '320900');
INSERT INTO `dns_area` VALUES ('320982', null, '0', '大丰市', '\0', '3', '1', '320900');
INSERT INTO `dns_area` VALUES ('321000', null, '0', '扬州市', '\0', '2', '1', '320000');
INSERT INTO `dns_area` VALUES ('321001', null, '0', '市辖区', '\0', '3', '1', '321000');
INSERT INTO `dns_area` VALUES ('321002', null, '0', '广陵区', '\0', '3', '1', '321000');
INSERT INTO `dns_area` VALUES ('321003', null, '0', '邗江区', '\0', '3', '1', '321000');
INSERT INTO `dns_area` VALUES ('321011', null, '0', '郊　区', '\0', '3', '1', '321000');
INSERT INTO `dns_area` VALUES ('321023', null, '0', '宝应县', '\0', '3', '1', '321000');
INSERT INTO `dns_area` VALUES ('321081', null, '0', '仪征市', '\0', '3', '1', '321000');
INSERT INTO `dns_area` VALUES ('321084', null, '0', '高邮市', '\0', '3', '1', '321000');
INSERT INTO `dns_area` VALUES ('321088', null, '0', '江都市', '\0', '3', '1', '321000');
INSERT INTO `dns_area` VALUES ('321100', null, '0', '镇江市', '\0', '2', '1', '320000');
INSERT INTO `dns_area` VALUES ('321101', null, '0', '市辖区', '\0', '3', '1', '321100');
INSERT INTO `dns_area` VALUES ('321102', null, '0', '京口区', '\0', '3', '1', '321100');
INSERT INTO `dns_area` VALUES ('321111', null, '0', '润州区', '\0', '3', '1', '321100');
INSERT INTO `dns_area` VALUES ('321112', null, '0', '丹徒区', '\0', '3', '1', '321100');
INSERT INTO `dns_area` VALUES ('321181', null, '0', '丹阳市', '\0', '3', '1', '321100');
INSERT INTO `dns_area` VALUES ('321182', null, '0', '扬中市', '\0', '3', '1', '321100');
INSERT INTO `dns_area` VALUES ('321183', null, '0', '句容市', '\0', '3', '1', '321100');
INSERT INTO `dns_area` VALUES ('321200', null, '0', '泰州市', '\0', '2', '1', '320000');
INSERT INTO `dns_area` VALUES ('321201', null, '0', '市辖区', '\0', '3', '1', '321200');
INSERT INTO `dns_area` VALUES ('321202', null, '0', '海陵区', '\0', '3', '1', '321200');
INSERT INTO `dns_area` VALUES ('321203', null, '0', '高港区', '\0', '3', '1', '321200');
INSERT INTO `dns_area` VALUES ('321281', null, '0', '兴化市', '\0', '3', '1', '321200');
INSERT INTO `dns_area` VALUES ('321282', null, '0', '靖江市', '\0', '3', '1', '321200');
INSERT INTO `dns_area` VALUES ('321283', null, '0', '泰兴市', '\0', '3', '1', '321200');
INSERT INTO `dns_area` VALUES ('321284', null, '0', '姜堰市', '\0', '3', '1', '321200');
INSERT INTO `dns_area` VALUES ('321300', null, '0', '宿迁市', '\0', '2', '1', '320000');
INSERT INTO `dns_area` VALUES ('321301', null, '0', '市辖区', '\0', '3', '1', '321300');
INSERT INTO `dns_area` VALUES ('321302', null, '0', '宿城区', '\0', '3', '1', '321300');
INSERT INTO `dns_area` VALUES ('321311', null, '0', '宿豫区', '\0', '3', '1', '321300');
INSERT INTO `dns_area` VALUES ('321322', null, '0', '沭阳县', '\0', '3', '1', '321300');
INSERT INTO `dns_area` VALUES ('321323', null, '0', '泗阳县', '\0', '3', '1', '321300');
INSERT INTO `dns_area` VALUES ('321324', null, '0', '泗洪县', '\0', '3', '1', '321300');
INSERT INTO `dns_area` VALUES ('330000', null, '0', '浙江省', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('330100', null, '0', '杭州市', '\0', '2', '1', '330000');
INSERT INTO `dns_area` VALUES ('330101', null, '0', '市辖区', '\0', '3', '1', '330100');
INSERT INTO `dns_area` VALUES ('330102', null, '0', '上城区', '\0', '3', '1', '330100');
INSERT INTO `dns_area` VALUES ('330103', null, '0', '下城区', '\0', '3', '1', '330100');
INSERT INTO `dns_area` VALUES ('330104', null, '0', '江干区', '\0', '3', '1', '330100');
INSERT INTO `dns_area` VALUES ('330105', null, '0', '拱墅区', '\0', '3', '1', '330100');
INSERT INTO `dns_area` VALUES ('330106', null, '0', '西湖区', '\0', '3', '1', '330100');
INSERT INTO `dns_area` VALUES ('330108', null, '0', '滨江区', '\0', '3', '1', '330100');
INSERT INTO `dns_area` VALUES ('330109', null, '0', '萧山区', '\0', '3', '1', '330100');
INSERT INTO `dns_area` VALUES ('330110', null, '0', '余杭区', '\0', '3', '1', '330100');
INSERT INTO `dns_area` VALUES ('330122', null, '0', '桐庐县', '\0', '3', '1', '330100');
INSERT INTO `dns_area` VALUES ('330127', null, '0', '淳安县', '\0', '3', '1', '330100');
INSERT INTO `dns_area` VALUES ('330182', null, '0', '建德市', '\0', '3', '1', '330100');
INSERT INTO `dns_area` VALUES ('330183', null, '0', '富阳市', '\0', '3', '1', '330100');
INSERT INTO `dns_area` VALUES ('330185', null, '0', '临安市', '\0', '3', '1', '330100');
INSERT INTO `dns_area` VALUES ('330200', null, '0', '宁波市', '\0', '2', '1', '330000');
INSERT INTO `dns_area` VALUES ('330201', null, '0', '市辖区', '\0', '3', '1', '330200');
INSERT INTO `dns_area` VALUES ('330203', null, '0', '海曙区', '\0', '3', '1', '330200');
INSERT INTO `dns_area` VALUES ('330204', null, '0', '江东区', '\0', '3', '1', '330200');
INSERT INTO `dns_area` VALUES ('330205', null, '0', '江北区', '\0', '3', '1', '330200');
INSERT INTO `dns_area` VALUES ('330206', null, '0', '北仑区', '\0', '3', '1', '330200');
INSERT INTO `dns_area` VALUES ('330211', null, '0', '镇海区', '\0', '3', '1', '330200');
INSERT INTO `dns_area` VALUES ('330212', null, '0', '鄞州区', '\0', '3', '1', '330200');
INSERT INTO `dns_area` VALUES ('330225', null, '0', '象山县', '\0', '3', '1', '330200');
INSERT INTO `dns_area` VALUES ('330226', null, '0', '宁海县', '\0', '3', '1', '330200');
INSERT INTO `dns_area` VALUES ('330281', null, '0', '余姚市', '\0', '3', '1', '330200');
INSERT INTO `dns_area` VALUES ('330282', null, '0', '慈溪市', '\0', '3', '1', '330200');
INSERT INTO `dns_area` VALUES ('330283', null, '0', '奉化市', '\0', '3', '1', '330200');
INSERT INTO `dns_area` VALUES ('330300', null, '0', '温州市', '\0', '2', '1', '330000');
INSERT INTO `dns_area` VALUES ('330301', null, '0', '市辖区', '\0', '3', '1', '330300');
INSERT INTO `dns_area` VALUES ('330302', null, '0', '鹿城区', '\0', '3', '1', '330300');
INSERT INTO `dns_area` VALUES ('330303', null, '0', '龙湾区', '\0', '3', '1', '330300');
INSERT INTO `dns_area` VALUES ('330304', null, '0', '瓯海区', '\0', '3', '1', '330300');
INSERT INTO `dns_area` VALUES ('330322', null, '0', '洞头县', '\0', '3', '1', '330300');
INSERT INTO `dns_area` VALUES ('330324', null, '0', '永嘉县', '\0', '3', '1', '330300');
INSERT INTO `dns_area` VALUES ('330326', null, '0', '平阳县', '\0', '3', '1', '330300');
INSERT INTO `dns_area` VALUES ('330327', null, '0', '苍南县', '\0', '3', '1', '330300');
INSERT INTO `dns_area` VALUES ('330328', null, '0', '文成县', '\0', '3', '1', '330300');
INSERT INTO `dns_area` VALUES ('330329', null, '0', '泰顺县', '\0', '3', '1', '330300');
INSERT INTO `dns_area` VALUES ('330381', null, '0', '瑞安市', '\0', '3', '1', '330300');
INSERT INTO `dns_area` VALUES ('330382', null, '0', '乐清市', '\0', '3', '1', '330300');
INSERT INTO `dns_area` VALUES ('330400', null, '0', '嘉兴市', '\0', '2', '1', '330000');
INSERT INTO `dns_area` VALUES ('330401', null, '0', '市辖区', '\0', '3', '1', '330400');
INSERT INTO `dns_area` VALUES ('330402', null, '0', '秀城区', '\0', '3', '1', '330400');
INSERT INTO `dns_area` VALUES ('330411', null, '0', '秀洲区', '\0', '3', '1', '330400');
INSERT INTO `dns_area` VALUES ('330421', null, '0', '嘉善县', '\0', '3', '1', '330400');
INSERT INTO `dns_area` VALUES ('330424', null, '0', '海盐县', '\0', '3', '1', '330400');
INSERT INTO `dns_area` VALUES ('330481', null, '0', '海宁市', '\0', '3', '1', '330400');
INSERT INTO `dns_area` VALUES ('330482', null, '0', '平湖市', '\0', '3', '1', '330400');
INSERT INTO `dns_area` VALUES ('330483', null, '0', '桐乡市', '\0', '3', '1', '330400');
INSERT INTO `dns_area` VALUES ('330500', null, '0', '湖州市', '\0', '2', '1', '330000');
INSERT INTO `dns_area` VALUES ('330501', null, '0', '市辖区', '\0', '3', '1', '330500');
INSERT INTO `dns_area` VALUES ('330502', null, '0', '吴兴区', '\0', '3', '1', '330500');
INSERT INTO `dns_area` VALUES ('330503', null, '0', '南浔区', '\0', '3', '1', '330500');
INSERT INTO `dns_area` VALUES ('330521', null, '0', '德清县', '\0', '3', '1', '330500');
INSERT INTO `dns_area` VALUES ('330522', null, '0', '长兴县', '\0', '3', '1', '330500');
INSERT INTO `dns_area` VALUES ('330523', null, '0', '安吉县', '\0', '3', '1', '330500');
INSERT INTO `dns_area` VALUES ('330600', null, '0', '绍兴市', '\0', '2', '1', '330000');
INSERT INTO `dns_area` VALUES ('330601', null, '0', '市辖区', '\0', '3', '1', '330600');
INSERT INTO `dns_area` VALUES ('330602', null, '0', '越城区', '\0', '3', '1', '330600');
INSERT INTO `dns_area` VALUES ('330621', null, '0', '绍兴县', '\0', '3', '1', '330600');
INSERT INTO `dns_area` VALUES ('330624', null, '0', '新昌县', '\0', '3', '1', '330600');
INSERT INTO `dns_area` VALUES ('330681', null, '0', '诸暨市', '\0', '3', '1', '330600');
INSERT INTO `dns_area` VALUES ('330682', null, '0', '上虞市', '\0', '3', '1', '330600');
INSERT INTO `dns_area` VALUES ('330683', null, '0', '嵊州市', '\0', '3', '1', '330600');
INSERT INTO `dns_area` VALUES ('330700', null, '0', '金华市', '\0', '2', '1', '330000');
INSERT INTO `dns_area` VALUES ('330701', null, '0', '市辖区', '\0', '3', '1', '330700');
INSERT INTO `dns_area` VALUES ('330702', null, '0', '婺城区', '\0', '3', '1', '330700');
INSERT INTO `dns_area` VALUES ('330703', null, '0', '金东区', '\0', '3', '1', '330700');
INSERT INTO `dns_area` VALUES ('330723', null, '0', '武义县', '\0', '3', '1', '330700');
INSERT INTO `dns_area` VALUES ('330726', null, '0', '浦江县', '\0', '3', '1', '330700');
INSERT INTO `dns_area` VALUES ('330727', null, '0', '磐安县', '\0', '3', '1', '330700');
INSERT INTO `dns_area` VALUES ('330781', null, '0', '兰溪市', '\0', '3', '1', '330700');
INSERT INTO `dns_area` VALUES ('330782', null, '0', '义乌市', '\0', '3', '1', '330700');
INSERT INTO `dns_area` VALUES ('330783', null, '0', '东阳市', '\0', '3', '1', '330700');
INSERT INTO `dns_area` VALUES ('330784', null, '0', '永康市', '\0', '3', '1', '330700');
INSERT INTO `dns_area` VALUES ('330800', null, '0', '衢州市', '\0', '2', '1', '330000');
INSERT INTO `dns_area` VALUES ('330801', null, '0', '市辖区', '\0', '3', '1', '330800');
INSERT INTO `dns_area` VALUES ('330802', null, '0', '柯城区', '\0', '3', '1', '330800');
INSERT INTO `dns_area` VALUES ('330803', null, '0', '衢江区', '\0', '3', '1', '330800');
INSERT INTO `dns_area` VALUES ('330822', null, '0', '常山县', '\0', '3', '1', '330800');
INSERT INTO `dns_area` VALUES ('330824', null, '0', '开化县', '\0', '3', '1', '330800');
INSERT INTO `dns_area` VALUES ('330825', null, '0', '龙游县', '\0', '3', '1', '330800');
INSERT INTO `dns_area` VALUES ('330881', null, '0', '江山市', '\0', '3', '1', '330800');
INSERT INTO `dns_area` VALUES ('330900', null, '0', '舟山市', '\0', '2', '1', '330000');
INSERT INTO `dns_area` VALUES ('330901', null, '0', '市辖区', '\0', '3', '1', '330900');
INSERT INTO `dns_area` VALUES ('330902', null, '0', '定海区', '\0', '3', '1', '330900');
INSERT INTO `dns_area` VALUES ('330903', null, '0', '普陀区', '\0', '3', '1', '330900');
INSERT INTO `dns_area` VALUES ('330921', null, '0', '岱山县', '\0', '3', '1', '330900');
INSERT INTO `dns_area` VALUES ('330922', null, '0', '嵊泗县', '\0', '3', '1', '330900');
INSERT INTO `dns_area` VALUES ('331000', null, '0', '台州市', '\0', '2', '1', '330000');
INSERT INTO `dns_area` VALUES ('331001', null, '0', '市辖区', '\0', '3', '1', '331000');
INSERT INTO `dns_area` VALUES ('331002', null, '0', '椒江区', '\0', '3', '1', '331000');
INSERT INTO `dns_area` VALUES ('331003', null, '0', '黄岩区', '\0', '3', '1', '331000');
INSERT INTO `dns_area` VALUES ('331004', null, '0', '路桥区', '\0', '3', '1', '331000');
INSERT INTO `dns_area` VALUES ('331021', null, '0', '玉环县', '\0', '3', '1', '331000');
INSERT INTO `dns_area` VALUES ('331022', null, '0', '三门县', '\0', '3', '1', '331000');
INSERT INTO `dns_area` VALUES ('331023', null, '0', '天台县', '\0', '3', '1', '331000');
INSERT INTO `dns_area` VALUES ('331024', null, '0', '仙居县', '\0', '3', '1', '331000');
INSERT INTO `dns_area` VALUES ('331081', null, '0', '温岭市', '\0', '3', '1', '331000');
INSERT INTO `dns_area` VALUES ('331082', null, '0', '临海市', '\0', '3', '1', '331000');
INSERT INTO `dns_area` VALUES ('331100', null, '0', '丽水市', '\0', '2', '1', '330000');
INSERT INTO `dns_area` VALUES ('331101', null, '0', '市辖区', '\0', '3', '1', '331100');
INSERT INTO `dns_area` VALUES ('331102', null, '0', '莲都区', '\0', '3', '1', '331100');
INSERT INTO `dns_area` VALUES ('331121', null, '0', '青田县', '\0', '3', '1', '331100');
INSERT INTO `dns_area` VALUES ('331122', null, '0', '缙云县', '\0', '3', '1', '331100');
INSERT INTO `dns_area` VALUES ('331123', null, '0', '遂昌县', '\0', '3', '1', '331100');
INSERT INTO `dns_area` VALUES ('331124', null, '0', '松阳县', '\0', '3', '1', '331100');
INSERT INTO `dns_area` VALUES ('331125', null, '0', '云和县', '\0', '3', '1', '331100');
INSERT INTO `dns_area` VALUES ('331126', null, '0', '庆元县', '\0', '3', '1', '331100');
INSERT INTO `dns_area` VALUES ('331127', null, '0', '景宁畲族自治县', '\0', '3', '1', '331100');
INSERT INTO `dns_area` VALUES ('331181', null, '0', '龙泉市', '\0', '3', '1', '331100');
INSERT INTO `dns_area` VALUES ('340000', null, '0', '安徽省', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('340100', null, '0', '合肥市', '\0', '2', '1', '340000');
INSERT INTO `dns_area` VALUES ('340101', null, '0', '市辖区', '\0', '3', '1', '340100');
INSERT INTO `dns_area` VALUES ('340102', null, '0', '瑶海区', '\0', '3', '1', '340100');
INSERT INTO `dns_area` VALUES ('340103', null, '0', '庐阳区', '\0', '3', '1', '340100');
INSERT INTO `dns_area` VALUES ('340104', null, '0', '蜀山区', '\0', '3', '1', '340100');
INSERT INTO `dns_area` VALUES ('340111', null, '0', '包河区', '\0', '3', '1', '340100');
INSERT INTO `dns_area` VALUES ('340121', null, '0', '长丰县', '\0', '3', '1', '340100');
INSERT INTO `dns_area` VALUES ('340122', null, '0', '肥东县', '\0', '3', '1', '340100');
INSERT INTO `dns_area` VALUES ('340123', null, '0', '肥西县', '\0', '3', '1', '340100');
INSERT INTO `dns_area` VALUES ('340200', null, '0', '芜湖市', '\0', '2', '1', '340000');
INSERT INTO `dns_area` VALUES ('340201', null, '0', '市辖区', '\0', '3', '1', '340200');
INSERT INTO `dns_area` VALUES ('340202', null, '0', '镜湖区', '\0', '3', '1', '340200');
INSERT INTO `dns_area` VALUES ('340203', null, '0', '马塘区', '\0', '3', '1', '340200');
INSERT INTO `dns_area` VALUES ('340204', null, '0', '新芜区', '\0', '3', '1', '340200');
INSERT INTO `dns_area` VALUES ('340207', null, '0', '鸠江区', '\0', '3', '1', '340200');
INSERT INTO `dns_area` VALUES ('340221', null, '0', '芜湖县', '\0', '3', '1', '340200');
INSERT INTO `dns_area` VALUES ('340222', null, '0', '繁昌县', '\0', '3', '1', '340200');
INSERT INTO `dns_area` VALUES ('340223', null, '0', '南陵县', '\0', '3', '1', '340200');
INSERT INTO `dns_area` VALUES ('340300', null, '0', '蚌埠市', '\0', '2', '1', '340000');
INSERT INTO `dns_area` VALUES ('340301', null, '0', '市辖区', '\0', '3', '1', '340300');
INSERT INTO `dns_area` VALUES ('340302', null, '0', '龙子湖区', '\0', '3', '1', '340300');
INSERT INTO `dns_area` VALUES ('340303', null, '0', '蚌山区', '\0', '3', '1', '340300');
INSERT INTO `dns_area` VALUES ('340304', null, '0', '禹会区', '\0', '3', '1', '340300');
INSERT INTO `dns_area` VALUES ('340311', null, '0', '淮上区', '\0', '3', '1', '340300');
INSERT INTO `dns_area` VALUES ('340321', null, '0', '怀远县', '\0', '3', '1', '340300');
INSERT INTO `dns_area` VALUES ('340322', null, '0', '五河县', '\0', '3', '1', '340300');
INSERT INTO `dns_area` VALUES ('340323', null, '0', '固镇县', '\0', '3', '1', '340300');
INSERT INTO `dns_area` VALUES ('340400', null, '0', '淮南市', '\0', '2', '1', '340000');
INSERT INTO `dns_area` VALUES ('340401', null, '0', '市辖区', '\0', '3', '1', '340400');
INSERT INTO `dns_area` VALUES ('340402', null, '0', '大通区', '\0', '3', '1', '340400');
INSERT INTO `dns_area` VALUES ('340403', null, '0', '田家庵区', '\0', '3', '1', '340400');
INSERT INTO `dns_area` VALUES ('340404', null, '0', '谢家集区', '\0', '3', '1', '340400');
INSERT INTO `dns_area` VALUES ('340405', null, '0', '八公山区', '\0', '3', '1', '340400');
INSERT INTO `dns_area` VALUES ('340406', null, '0', '潘集区', '\0', '3', '1', '340400');
INSERT INTO `dns_area` VALUES ('340421', null, '0', '凤台县', '\0', '3', '1', '340400');
INSERT INTO `dns_area` VALUES ('340500', null, '0', '马鞍山市', '\0', '2', '1', '340000');
INSERT INTO `dns_area` VALUES ('340501', null, '0', '市辖区', '\0', '3', '1', '340500');
INSERT INTO `dns_area` VALUES ('340502', null, '0', '金家庄区', '\0', '3', '1', '340500');
INSERT INTO `dns_area` VALUES ('340503', null, '0', '花山区', '\0', '3', '1', '340500');
INSERT INTO `dns_area` VALUES ('340504', null, '0', '雨山区', '\0', '3', '1', '340500');
INSERT INTO `dns_area` VALUES ('340521', null, '0', '当涂县', '\0', '3', '1', '340500');
INSERT INTO `dns_area` VALUES ('340600', null, '0', '淮北市', '\0', '2', '1', '340000');
INSERT INTO `dns_area` VALUES ('340601', null, '0', '市辖区', '\0', '3', '1', '340600');
INSERT INTO `dns_area` VALUES ('340602', null, '0', '杜集区', '\0', '3', '1', '340600');
INSERT INTO `dns_area` VALUES ('340603', null, '0', '相山区', '\0', '3', '1', '340600');
INSERT INTO `dns_area` VALUES ('340604', null, '0', '烈山区', '\0', '3', '1', '340600');
INSERT INTO `dns_area` VALUES ('340621', null, '0', '濉溪县', '\0', '3', '1', '340600');
INSERT INTO `dns_area` VALUES ('340700', null, '0', '铜陵市', '\0', '2', '1', '340000');
INSERT INTO `dns_area` VALUES ('340701', null, '0', '市辖区', '\0', '3', '1', '340700');
INSERT INTO `dns_area` VALUES ('340702', null, '0', '铜官山区', '\0', '3', '1', '340700');
INSERT INTO `dns_area` VALUES ('340703', null, '0', '狮子山区', '\0', '3', '1', '340700');
INSERT INTO `dns_area` VALUES ('340711', null, '0', '郊　区', '\0', '3', '1', '340700');
INSERT INTO `dns_area` VALUES ('340721', null, '0', '铜陵县', '\0', '3', '1', '340700');
INSERT INTO `dns_area` VALUES ('340800', null, '0', '安庆市', '\0', '2', '1', '340000');
INSERT INTO `dns_area` VALUES ('340801', null, '0', '市辖区', '\0', '3', '1', '340800');
INSERT INTO `dns_area` VALUES ('340802', null, '0', '迎江区', '\0', '3', '1', '340800');
INSERT INTO `dns_area` VALUES ('340803', null, '0', '大观区', '\0', '3', '1', '340800');
INSERT INTO `dns_area` VALUES ('340811', null, '0', '郊　区', '\0', '3', '1', '340800');
INSERT INTO `dns_area` VALUES ('340822', null, '0', '怀宁县', '\0', '3', '1', '340800');
INSERT INTO `dns_area` VALUES ('340823', null, '0', '枞阳县', '\0', '3', '1', '340800');
INSERT INTO `dns_area` VALUES ('340824', null, '0', '潜山县', '\0', '3', '1', '340800');
INSERT INTO `dns_area` VALUES ('340825', null, '0', '太湖县', '\0', '3', '1', '340800');
INSERT INTO `dns_area` VALUES ('340826', null, '0', '宿松县', '\0', '3', '1', '340800');
INSERT INTO `dns_area` VALUES ('340827', null, '0', '望江县', '\0', '3', '1', '340800');
INSERT INTO `dns_area` VALUES ('340828', null, '0', '岳西县', '\0', '3', '1', '340800');
INSERT INTO `dns_area` VALUES ('340881', null, '0', '桐城市', '\0', '3', '1', '340800');
INSERT INTO `dns_area` VALUES ('341000', null, '0', '黄山市', '\0', '2', '1', '340000');
INSERT INTO `dns_area` VALUES ('341001', null, '0', '市辖区', '\0', '3', '1', '341000');
INSERT INTO `dns_area` VALUES ('341002', null, '0', '屯溪区', '\0', '3', '1', '341000');
INSERT INTO `dns_area` VALUES ('341003', null, '0', '黄山区', '\0', '3', '1', '341000');
INSERT INTO `dns_area` VALUES ('341004', null, '0', '徽州区', '\0', '3', '1', '341000');
INSERT INTO `dns_area` VALUES ('341021', null, '0', '歙　县', '\0', '3', '1', '341000');
INSERT INTO `dns_area` VALUES ('341022', null, '0', '休宁县', '\0', '3', '1', '341000');
INSERT INTO `dns_area` VALUES ('341023', null, '0', '黟　县', '\0', '3', '1', '341000');
INSERT INTO `dns_area` VALUES ('341024', null, '0', '祁门县', '\0', '3', '1', '341000');
INSERT INTO `dns_area` VALUES ('341100', null, '0', '滁州市', '\0', '2', '1', '340000');
INSERT INTO `dns_area` VALUES ('341101', null, '0', '市辖区', '\0', '3', '1', '341100');
INSERT INTO `dns_area` VALUES ('341102', null, '0', '琅琊区', '\0', '3', '1', '341100');
INSERT INTO `dns_area` VALUES ('341103', null, '0', '南谯区', '\0', '3', '1', '341100');
INSERT INTO `dns_area` VALUES ('341122', null, '0', '来安县', '\0', '3', '1', '341100');
INSERT INTO `dns_area` VALUES ('341124', null, '0', '全椒县', '\0', '3', '1', '341100');
INSERT INTO `dns_area` VALUES ('341125', null, '0', '定远县', '\0', '3', '1', '341100');
INSERT INTO `dns_area` VALUES ('341126', null, '0', '凤阳县', '\0', '3', '1', '341100');
INSERT INTO `dns_area` VALUES ('341181', null, '0', '天长市', '\0', '3', '1', '341100');
INSERT INTO `dns_area` VALUES ('341182', null, '0', '明光市', '\0', '3', '1', '341100');
INSERT INTO `dns_area` VALUES ('341200', null, '0', '阜阳市', '\0', '2', '1', '340000');
INSERT INTO `dns_area` VALUES ('341201', null, '0', '市辖区', '\0', '3', '1', '341200');
INSERT INTO `dns_area` VALUES ('341202', null, '0', '颍州区', '\0', '3', '1', '341200');
INSERT INTO `dns_area` VALUES ('341203', null, '0', '颍东区', '\0', '3', '1', '341200');
INSERT INTO `dns_area` VALUES ('341204', null, '0', '颍泉区', '\0', '3', '1', '341200');
INSERT INTO `dns_area` VALUES ('341221', null, '0', '临泉县', '\0', '3', '1', '341200');
INSERT INTO `dns_area` VALUES ('341222', null, '0', '太和县', '\0', '3', '1', '341200');
INSERT INTO `dns_area` VALUES ('341225', null, '0', '阜南县', '\0', '3', '1', '341200');
INSERT INTO `dns_area` VALUES ('341226', null, '0', '颍上县', '\0', '3', '1', '341200');
INSERT INTO `dns_area` VALUES ('341282', null, '0', '界首市', '\0', '3', '1', '341200');
INSERT INTO `dns_area` VALUES ('341300', null, '0', '宿州市', '\0', '2', '1', '340000');
INSERT INTO `dns_area` VALUES ('341301', null, '0', '市辖区', '\0', '3', '1', '341300');
INSERT INTO `dns_area` VALUES ('341302', null, '0', '墉桥区', '\0', '3', '1', '341300');
INSERT INTO `dns_area` VALUES ('341321', null, '0', '砀山县', '\0', '3', '1', '341300');
INSERT INTO `dns_area` VALUES ('341322', null, '0', '萧　县', '\0', '3', '1', '341300');
INSERT INTO `dns_area` VALUES ('341323', null, '0', '灵璧县', '\0', '3', '1', '341300');
INSERT INTO `dns_area` VALUES ('341324', null, '0', '泗　县', '\0', '3', '1', '341300');
INSERT INTO `dns_area` VALUES ('341400', null, '0', '巢湖市', '\0', '2', '1', '340000');
INSERT INTO `dns_area` VALUES ('341401', null, '0', '市辖区', '\0', '3', '1', '341400');
INSERT INTO `dns_area` VALUES ('341402', null, '0', '居巢区', '\0', '3', '1', '341400');
INSERT INTO `dns_area` VALUES ('341421', null, '0', '庐江县', '\0', '3', '1', '341400');
INSERT INTO `dns_area` VALUES ('341422', null, '0', '无为县', '\0', '3', '1', '341400');
INSERT INTO `dns_area` VALUES ('341423', null, '0', '含山县', '\0', '3', '1', '341400');
INSERT INTO `dns_area` VALUES ('341424', null, '0', '和　县', '\0', '3', '1', '341400');
INSERT INTO `dns_area` VALUES ('341500', null, '0', '六安市', '\0', '2', '1', '340000');
INSERT INTO `dns_area` VALUES ('341501', null, '0', '市辖区', '\0', '3', '1', '341500');
INSERT INTO `dns_area` VALUES ('341502', null, '0', '金安区', '\0', '3', '1', '341500');
INSERT INTO `dns_area` VALUES ('341503', null, '0', '裕安区', '\0', '3', '1', '341500');
INSERT INTO `dns_area` VALUES ('341521', null, '0', '寿　县', '\0', '3', '1', '341500');
INSERT INTO `dns_area` VALUES ('341522', null, '0', '霍邱县', '\0', '3', '1', '341500');
INSERT INTO `dns_area` VALUES ('341523', null, '0', '舒城县', '\0', '3', '1', '341500');
INSERT INTO `dns_area` VALUES ('341524', null, '0', '金寨县', '\0', '3', '1', '341500');
INSERT INTO `dns_area` VALUES ('341525', null, '0', '霍山县', '\0', '3', '1', '341500');
INSERT INTO `dns_area` VALUES ('341600', null, '0', '亳州市', '\0', '2', '1', '340000');
INSERT INTO `dns_area` VALUES ('341601', null, '0', '市辖区', '\0', '3', '1', '341600');
INSERT INTO `dns_area` VALUES ('341602', null, '0', '谯城区', '\0', '3', '1', '341600');
INSERT INTO `dns_area` VALUES ('341621', null, '0', '涡阳县', '\0', '3', '1', '341600');
INSERT INTO `dns_area` VALUES ('341622', null, '0', '蒙城县', '\0', '3', '1', '341600');
INSERT INTO `dns_area` VALUES ('341623', null, '0', '利辛县', '\0', '3', '1', '341600');
INSERT INTO `dns_area` VALUES ('341700', null, '0', '池州市', '\0', '2', '1', '340000');
INSERT INTO `dns_area` VALUES ('341701', null, '0', '市辖区', '\0', '3', '1', '341700');
INSERT INTO `dns_area` VALUES ('341702', null, '0', '贵池区', '\0', '3', '1', '341700');
INSERT INTO `dns_area` VALUES ('341721', null, '0', '东至县', '\0', '3', '1', '341700');
INSERT INTO `dns_area` VALUES ('341722', null, '0', '石台县', '\0', '3', '1', '341700');
INSERT INTO `dns_area` VALUES ('341723', null, '0', '青阳县', '\0', '3', '1', '341700');
INSERT INTO `dns_area` VALUES ('341800', null, '0', '宣城市', '\0', '2', '1', '340000');
INSERT INTO `dns_area` VALUES ('341801', null, '0', '市辖区', '\0', '3', '1', '341800');
INSERT INTO `dns_area` VALUES ('341802', null, '0', '宣州区', '\0', '3', '1', '341800');
INSERT INTO `dns_area` VALUES ('341821', null, '0', '郎溪县', '\0', '3', '1', '341800');
INSERT INTO `dns_area` VALUES ('341822', null, '0', '广德县', '\0', '3', '1', '341800');
INSERT INTO `dns_area` VALUES ('341823', null, '0', '泾　县', '\0', '3', '1', '341800');
INSERT INTO `dns_area` VALUES ('341824', null, '0', '绩溪县', '\0', '3', '1', '341800');
INSERT INTO `dns_area` VALUES ('341825', null, '0', '旌德县', '\0', '3', '1', '341800');
INSERT INTO `dns_area` VALUES ('341881', null, '0', '宁国市', '\0', '3', '1', '341800');
INSERT INTO `dns_area` VALUES ('350000', null, '0', '福建省', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('350100', null, '0', '福州市', '\0', '2', '1', '350000');
INSERT INTO `dns_area` VALUES ('350101', null, '0', '市辖区', '\0', '3', '1', '350100');
INSERT INTO `dns_area` VALUES ('350102', null, '0', '鼓楼区', '\0', '3', '1', '350100');
INSERT INTO `dns_area` VALUES ('350103', null, '0', '台江区', '\0', '3', '1', '350100');
INSERT INTO `dns_area` VALUES ('350104', null, '0', '仓山区', '\0', '3', '1', '350100');
INSERT INTO `dns_area` VALUES ('350105', null, '0', '马尾区', '\0', '3', '1', '350100');
INSERT INTO `dns_area` VALUES ('350111', null, '0', '晋安区', '\0', '3', '1', '350100');
INSERT INTO `dns_area` VALUES ('350121', null, '0', '闽侯县', '\0', '3', '1', '350100');
INSERT INTO `dns_area` VALUES ('350122', null, '0', '连江县', '\0', '3', '1', '350100');
INSERT INTO `dns_area` VALUES ('350123', null, '0', '罗源县', '\0', '3', '1', '350100');
INSERT INTO `dns_area` VALUES ('350124', null, '0', '闽清县', '\0', '3', '1', '350100');
INSERT INTO `dns_area` VALUES ('350125', null, '0', '永泰县', '\0', '3', '1', '350100');
INSERT INTO `dns_area` VALUES ('350128', null, '0', '平潭县', '\0', '3', '1', '350100');
INSERT INTO `dns_area` VALUES ('350181', null, '0', '福清市', '\0', '3', '1', '350100');
INSERT INTO `dns_area` VALUES ('350182', null, '0', '长乐市', '\0', '3', '1', '350100');
INSERT INTO `dns_area` VALUES ('350200', null, '0', '厦门市', '\0', '2', '1', '350000');
INSERT INTO `dns_area` VALUES ('350201', null, '0', '市辖区', '\0', '3', '1', '350200');
INSERT INTO `dns_area` VALUES ('350203', null, '0', '思明区', '\0', '3', '1', '350200');
INSERT INTO `dns_area` VALUES ('350205', null, '0', '海沧区', '\0', '3', '1', '350200');
INSERT INTO `dns_area` VALUES ('350206', null, '0', '湖里区', '\0', '3', '1', '350200');
INSERT INTO `dns_area` VALUES ('350211', null, '0', '集美区', '\0', '3', '1', '350200');
INSERT INTO `dns_area` VALUES ('350212', null, '0', '同安区', '\0', '3', '1', '350200');
INSERT INTO `dns_area` VALUES ('350213', null, '0', '翔安区', '\0', '3', '1', '350200');
INSERT INTO `dns_area` VALUES ('350300', null, '0', '莆田市', '\0', '2', '1', '350000');
INSERT INTO `dns_area` VALUES ('350301', null, '0', '市辖区', '\0', '3', '1', '350300');
INSERT INTO `dns_area` VALUES ('350302', null, '0', '城厢区', '\0', '3', '1', '350300');
INSERT INTO `dns_area` VALUES ('350303', null, '0', '涵江区', '\0', '3', '1', '350300');
INSERT INTO `dns_area` VALUES ('350304', null, '0', '荔城区', '\0', '3', '1', '350300');
INSERT INTO `dns_area` VALUES ('350305', null, '0', '秀屿区', '\0', '3', '1', '350300');
INSERT INTO `dns_area` VALUES ('350322', null, '0', '仙游县', '\0', '3', '1', '350300');
INSERT INTO `dns_area` VALUES ('350400', null, '0', '三明市', '\0', '2', '1', '350000');
INSERT INTO `dns_area` VALUES ('350401', null, '0', '市辖区', '\0', '3', '1', '350400');
INSERT INTO `dns_area` VALUES ('350402', null, '0', '梅列区', '\0', '3', '1', '350400');
INSERT INTO `dns_area` VALUES ('350403', null, '0', '三元区', '\0', '3', '1', '350400');
INSERT INTO `dns_area` VALUES ('350421', null, '0', '明溪县', '\0', '3', '1', '350400');
INSERT INTO `dns_area` VALUES ('350423', null, '0', '清流县', '\0', '3', '1', '350400');
INSERT INTO `dns_area` VALUES ('350424', null, '0', '宁化县', '\0', '3', '1', '350400');
INSERT INTO `dns_area` VALUES ('350425', null, '0', '大田县', '\0', '3', '1', '350400');
INSERT INTO `dns_area` VALUES ('350426', null, '0', '尤溪县', '\0', '3', '1', '350400');
INSERT INTO `dns_area` VALUES ('350427', null, '0', '沙　县', '\0', '3', '1', '350400');
INSERT INTO `dns_area` VALUES ('350428', null, '0', '将乐县', '\0', '3', '1', '350400');
INSERT INTO `dns_area` VALUES ('350429', null, '0', '泰宁县', '\0', '3', '1', '350400');
INSERT INTO `dns_area` VALUES ('350430', null, '0', '建宁县', '\0', '3', '1', '350400');
INSERT INTO `dns_area` VALUES ('350481', null, '0', '永安市', '\0', '3', '1', '350400');
INSERT INTO `dns_area` VALUES ('350500', null, '0', '泉州市', '\0', '2', '1', '350000');
INSERT INTO `dns_area` VALUES ('350501', null, '0', '市辖区', '\0', '3', '1', '350500');
INSERT INTO `dns_area` VALUES ('350502', null, '0', '鲤城区', '\0', '3', '1', '350500');
INSERT INTO `dns_area` VALUES ('350503', null, '0', '丰泽区', '\0', '3', '1', '350500');
INSERT INTO `dns_area` VALUES ('350504', null, '0', '洛江区', '\0', '3', '1', '350500');
INSERT INTO `dns_area` VALUES ('350505', null, '0', '泉港区', '\0', '3', '1', '350500');
INSERT INTO `dns_area` VALUES ('350521', null, '0', '惠安县', '\0', '3', '1', '350500');
INSERT INTO `dns_area` VALUES ('350524', null, '0', '安溪县', '\0', '3', '1', '350500');
INSERT INTO `dns_area` VALUES ('350525', null, '0', '永春县', '\0', '3', '1', '350500');
INSERT INTO `dns_area` VALUES ('350526', null, '0', '德化县', '\0', '3', '1', '350500');
INSERT INTO `dns_area` VALUES ('350527', null, '0', '金门县', '\0', '3', '1', '350500');
INSERT INTO `dns_area` VALUES ('350581', null, '0', '石狮市', '\0', '3', '1', '350500');
INSERT INTO `dns_area` VALUES ('350582', null, '0', '晋江市', '\0', '3', '1', '350500');
INSERT INTO `dns_area` VALUES ('350583', null, '0', '南安市', '\0', '3', '1', '350500');
INSERT INTO `dns_area` VALUES ('350600', null, '0', '漳州市', '\0', '2', '1', '350000');
INSERT INTO `dns_area` VALUES ('350601', null, '0', '市辖区', '\0', '3', '1', '350600');
INSERT INTO `dns_area` VALUES ('350602', null, '0', '芗城区', '\0', '3', '1', '350600');
INSERT INTO `dns_area` VALUES ('350603', null, '0', '龙文区', '\0', '3', '1', '350600');
INSERT INTO `dns_area` VALUES ('350622', null, '0', '云霄县', '\0', '3', '1', '350600');
INSERT INTO `dns_area` VALUES ('350623', null, '0', '漳浦县', '\0', '3', '1', '350600');
INSERT INTO `dns_area` VALUES ('350624', null, '0', '诏安县', '\0', '3', '1', '350600');
INSERT INTO `dns_area` VALUES ('350625', null, '0', '长泰县', '\0', '3', '1', '350600');
INSERT INTO `dns_area` VALUES ('350626', null, '0', '东山县', '\0', '3', '1', '350600');
INSERT INTO `dns_area` VALUES ('350627', null, '0', '南靖县', '\0', '3', '1', '350600');
INSERT INTO `dns_area` VALUES ('350628', null, '0', '平和县', '\0', '3', '1', '350600');
INSERT INTO `dns_area` VALUES ('350629', null, '0', '华安县', '\0', '3', '1', '350600');
INSERT INTO `dns_area` VALUES ('350681', null, '0', '龙海市', '\0', '3', '1', '350600');
INSERT INTO `dns_area` VALUES ('350700', null, '0', '南平市', '\0', '2', '1', '350000');
INSERT INTO `dns_area` VALUES ('350701', null, '0', '市辖区', '\0', '3', '1', '350700');
INSERT INTO `dns_area` VALUES ('350702', null, '0', '延平区', '\0', '3', '1', '350700');
INSERT INTO `dns_area` VALUES ('350721', null, '0', '顺昌县', '\0', '3', '1', '350700');
INSERT INTO `dns_area` VALUES ('350722', null, '0', '浦城县', '\0', '3', '1', '350700');
INSERT INTO `dns_area` VALUES ('350723', null, '0', '光泽县', '\0', '3', '1', '350700');
INSERT INTO `dns_area` VALUES ('350724', null, '0', '松溪县', '\0', '3', '1', '350700');
INSERT INTO `dns_area` VALUES ('350725', null, '0', '政和县', '\0', '3', '1', '350700');
INSERT INTO `dns_area` VALUES ('350781', null, '0', '邵武市', '\0', '3', '1', '350700');
INSERT INTO `dns_area` VALUES ('350782', null, '0', '武夷山市', '\0', '3', '1', '350700');
INSERT INTO `dns_area` VALUES ('350783', null, '0', '建瓯市', '\0', '3', '1', '350700');
INSERT INTO `dns_area` VALUES ('350784', null, '0', '建阳市', '\0', '3', '1', '350700');
INSERT INTO `dns_area` VALUES ('350800', null, '0', '龙岩市', '\0', '2', '1', '350000');
INSERT INTO `dns_area` VALUES ('350801', null, '0', '市辖区', '\0', '3', '1', '350800');
INSERT INTO `dns_area` VALUES ('350802', null, '0', '新罗区', '\0', '3', '1', '350800');
INSERT INTO `dns_area` VALUES ('350821', null, '0', '长汀县', '\0', '3', '1', '350800');
INSERT INTO `dns_area` VALUES ('350822', null, '0', '永定县', '\0', '3', '1', '350800');
INSERT INTO `dns_area` VALUES ('350823', null, '0', '上杭县', '\0', '3', '1', '350800');
INSERT INTO `dns_area` VALUES ('350824', null, '0', '武平县', '\0', '3', '1', '350800');
INSERT INTO `dns_area` VALUES ('350825', null, '0', '连城县', '\0', '3', '1', '350800');
INSERT INTO `dns_area` VALUES ('350881', null, '0', '漳平市', '\0', '3', '1', '350800');
INSERT INTO `dns_area` VALUES ('350900', null, '0', '宁德市', '\0', '2', '1', '350000');
INSERT INTO `dns_area` VALUES ('350901', null, '0', '市辖区', '\0', '3', '1', '350900');
INSERT INTO `dns_area` VALUES ('350902', null, '0', '蕉城区', '\0', '3', '1', '350900');
INSERT INTO `dns_area` VALUES ('350921', null, '0', '霞浦县', '\0', '3', '1', '350900');
INSERT INTO `dns_area` VALUES ('350922', null, '0', '古田县', '\0', '3', '1', '350900');
INSERT INTO `dns_area` VALUES ('350923', null, '0', '屏南县', '\0', '3', '1', '350900');
INSERT INTO `dns_area` VALUES ('350924', null, '0', '寿宁县', '\0', '3', '1', '350900');
INSERT INTO `dns_area` VALUES ('350925', null, '0', '周宁县', '\0', '3', '1', '350900');
INSERT INTO `dns_area` VALUES ('350926', null, '0', '柘荣县', '\0', '3', '1', '350900');
INSERT INTO `dns_area` VALUES ('350981', null, '0', '福安市', '\0', '3', '1', '350900');
INSERT INTO `dns_area` VALUES ('350982', null, '0', '福鼎市', '\0', '3', '1', '350900');
INSERT INTO `dns_area` VALUES ('360000', null, '0', '江西省', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('360100', null, '0', '南昌市', '\0', '2', '1', '360000');
INSERT INTO `dns_area` VALUES ('360101', null, '0', '市辖区', '\0', '3', '1', '360100');
INSERT INTO `dns_area` VALUES ('360102', null, '0', '东湖区', '\0', '3', '1', '360100');
INSERT INTO `dns_area` VALUES ('360103', null, '0', '西湖区', '\0', '3', '1', '360100');
INSERT INTO `dns_area` VALUES ('360104', null, '0', '青云谱区', '\0', '3', '1', '360100');
INSERT INTO `dns_area` VALUES ('360105', null, '0', '湾里区', '\0', '3', '1', '360100');
INSERT INTO `dns_area` VALUES ('360111', null, '0', '青山湖区', '\0', '3', '1', '360100');
INSERT INTO `dns_area` VALUES ('360121', null, '0', '南昌县', '\0', '3', '1', '360100');
INSERT INTO `dns_area` VALUES ('360122', null, '0', '新建县', '\0', '3', '1', '360100');
INSERT INTO `dns_area` VALUES ('360123', null, '0', '安义县', '\0', '3', '1', '360100');
INSERT INTO `dns_area` VALUES ('360124', null, '0', '进贤县', '\0', '3', '1', '360100');
INSERT INTO `dns_area` VALUES ('360200', null, '0', '景德镇市', '\0', '2', '1', '360000');
INSERT INTO `dns_area` VALUES ('360201', null, '0', '市辖区', '\0', '3', '1', '360200');
INSERT INTO `dns_area` VALUES ('360202', null, '0', '昌江区', '\0', '3', '1', '360200');
INSERT INTO `dns_area` VALUES ('360203', null, '0', '珠山区', '\0', '3', '1', '360200');
INSERT INTO `dns_area` VALUES ('360222', null, '0', '浮梁县', '\0', '3', '1', '360200');
INSERT INTO `dns_area` VALUES ('360281', null, '0', '乐平市', '\0', '3', '1', '360200');
INSERT INTO `dns_area` VALUES ('360300', null, '0', '萍乡市', '\0', '2', '1', '360000');
INSERT INTO `dns_area` VALUES ('360301', null, '0', '市辖区', '\0', '3', '1', '360300');
INSERT INTO `dns_area` VALUES ('360302', null, '0', '安源区', '\0', '3', '1', '360300');
INSERT INTO `dns_area` VALUES ('360313', null, '0', '湘东区', '\0', '3', '1', '360300');
INSERT INTO `dns_area` VALUES ('360321', null, '0', '莲花县', '\0', '3', '1', '360300');
INSERT INTO `dns_area` VALUES ('360322', null, '0', '上栗县', '\0', '3', '1', '360300');
INSERT INTO `dns_area` VALUES ('360323', null, '0', '芦溪县', '\0', '3', '1', '360300');
INSERT INTO `dns_area` VALUES ('360400', null, '0', '九江市', '\0', '2', '1', '360000');
INSERT INTO `dns_area` VALUES ('360401', null, '0', '市辖区', '\0', '3', '1', '360400');
INSERT INTO `dns_area` VALUES ('360402', null, '0', '庐山区', '\0', '3', '1', '360400');
INSERT INTO `dns_area` VALUES ('360403', null, '0', '浔阳区', '\0', '3', '1', '360400');
INSERT INTO `dns_area` VALUES ('360421', null, '0', '九江县', '\0', '3', '1', '360400');
INSERT INTO `dns_area` VALUES ('360423', null, '0', '武宁县', '\0', '3', '1', '360400');
INSERT INTO `dns_area` VALUES ('360424', null, '0', '修水县', '\0', '3', '1', '360400');
INSERT INTO `dns_area` VALUES ('360425', null, '0', '永修县', '\0', '3', '1', '360400');
INSERT INTO `dns_area` VALUES ('360426', null, '0', '德安县', '\0', '3', '1', '360400');
INSERT INTO `dns_area` VALUES ('360427', null, '0', '星子县', '\0', '3', '1', '360400');
INSERT INTO `dns_area` VALUES ('360428', null, '0', '都昌县', '\0', '3', '1', '360400');
INSERT INTO `dns_area` VALUES ('360429', null, '0', '湖口县', '\0', '3', '1', '360400');
INSERT INTO `dns_area` VALUES ('360430', null, '0', '彭泽县', '\0', '3', '1', '360400');
INSERT INTO `dns_area` VALUES ('360481', null, '0', '瑞昌市', '\0', '3', '1', '360400');
INSERT INTO `dns_area` VALUES ('360500', null, '0', '新余市', '\0', '2', '1', '360000');
INSERT INTO `dns_area` VALUES ('360501', null, '0', '市辖区', '\0', '3', '1', '360500');
INSERT INTO `dns_area` VALUES ('360502', null, '0', '渝水区', '\0', '3', '1', '360500');
INSERT INTO `dns_area` VALUES ('360521', null, '0', '分宜县', '\0', '3', '1', '360500');
INSERT INTO `dns_area` VALUES ('360600', null, '0', '鹰潭市', '\0', '2', '1', '360000');
INSERT INTO `dns_area` VALUES ('360601', null, '0', '市辖区', '\0', '3', '1', '360600');
INSERT INTO `dns_area` VALUES ('360602', null, '0', '月湖区', '\0', '3', '1', '360600');
INSERT INTO `dns_area` VALUES ('360622', null, '0', '余江县', '\0', '3', '1', '360600');
INSERT INTO `dns_area` VALUES ('360681', null, '0', '贵溪市', '\0', '3', '1', '360600');
INSERT INTO `dns_area` VALUES ('360700', null, '0', '赣州市', '\0', '2', '1', '360000');
INSERT INTO `dns_area` VALUES ('360701', null, '0', '市辖区', '\0', '3', '1', '360700');
INSERT INTO `dns_area` VALUES ('360702', null, '0', '章贡区', '\0', '3', '1', '360700');
INSERT INTO `dns_area` VALUES ('360721', null, '0', '赣　县', '\0', '3', '1', '360700');
INSERT INTO `dns_area` VALUES ('360722', null, '0', '信丰县', '\0', '3', '1', '360700');
INSERT INTO `dns_area` VALUES ('360723', null, '0', '大余县', '\0', '3', '1', '360700');
INSERT INTO `dns_area` VALUES ('360724', null, '0', '上犹县', '\0', '3', '1', '360700');
INSERT INTO `dns_area` VALUES ('360725', null, '0', '崇义县', '\0', '3', '1', '360700');
INSERT INTO `dns_area` VALUES ('360726', null, '0', '安远县', '\0', '3', '1', '360700');
INSERT INTO `dns_area` VALUES ('360727', null, '0', '龙南县', '\0', '3', '1', '360700');
INSERT INTO `dns_area` VALUES ('360728', null, '0', '定南县', '\0', '3', '1', '360700');
INSERT INTO `dns_area` VALUES ('360729', null, '0', '全南县', '\0', '3', '1', '360700');
INSERT INTO `dns_area` VALUES ('360730', null, '0', '宁都县', '\0', '3', '1', '360700');
INSERT INTO `dns_area` VALUES ('360731', null, '0', '于都县', '\0', '3', '1', '360700');
INSERT INTO `dns_area` VALUES ('360732', null, '0', '兴国县', '\0', '3', '1', '360700');
INSERT INTO `dns_area` VALUES ('360733', null, '0', '会昌县', '\0', '3', '1', '360700');
INSERT INTO `dns_area` VALUES ('360734', null, '0', '寻乌县', '\0', '3', '1', '360700');
INSERT INTO `dns_area` VALUES ('360735', null, '0', '石城县', '\0', '3', '1', '360700');
INSERT INTO `dns_area` VALUES ('360781', null, '0', '瑞金市', '\0', '3', '1', '360700');
INSERT INTO `dns_area` VALUES ('360782', null, '0', '南康市', '\0', '3', '1', '360700');
INSERT INTO `dns_area` VALUES ('360800', null, '0', '吉安市', '\0', '2', '1', '360000');
INSERT INTO `dns_area` VALUES ('360801', null, '0', '市辖区', '\0', '3', '1', '360800');
INSERT INTO `dns_area` VALUES ('360802', null, '0', '吉州区', '\0', '3', '1', '360800');
INSERT INTO `dns_area` VALUES ('360803', null, '0', '青原区', '\0', '3', '1', '360800');
INSERT INTO `dns_area` VALUES ('360821', null, '0', '吉安县', '\0', '3', '1', '360800');
INSERT INTO `dns_area` VALUES ('360822', null, '0', '吉水县', '\0', '3', '1', '360800');
INSERT INTO `dns_area` VALUES ('360823', null, '0', '峡江县', '\0', '3', '1', '360800');
INSERT INTO `dns_area` VALUES ('360824', null, '0', '新干县', '\0', '3', '1', '360800');
INSERT INTO `dns_area` VALUES ('360825', null, '0', '永丰县', '\0', '3', '1', '360800');
INSERT INTO `dns_area` VALUES ('360826', null, '0', '泰和县', '\0', '3', '1', '360800');
INSERT INTO `dns_area` VALUES ('360827', null, '0', '遂川县', '\0', '3', '1', '360800');
INSERT INTO `dns_area` VALUES ('360828', null, '0', '万安县', '\0', '3', '1', '360800');
INSERT INTO `dns_area` VALUES ('360829', null, '0', '安福县', '\0', '3', '1', '360800');
INSERT INTO `dns_area` VALUES ('360830', null, '0', '永新县', '\0', '3', '1', '360800');
INSERT INTO `dns_area` VALUES ('360881', null, '0', '井冈山市', '\0', '3', '1', '360800');
INSERT INTO `dns_area` VALUES ('360900', null, '0', '宜春市', '\0', '2', '1', '360000');
INSERT INTO `dns_area` VALUES ('360901', null, '0', '市辖区', '\0', '3', '1', '360900');
INSERT INTO `dns_area` VALUES ('360902', null, '0', '袁州区', '\0', '3', '1', '360900');
INSERT INTO `dns_area` VALUES ('360921', null, '0', '奉新县', '\0', '3', '1', '360900');
INSERT INTO `dns_area` VALUES ('360922', null, '0', '万载县', '\0', '3', '1', '360900');
INSERT INTO `dns_area` VALUES ('360923', null, '0', '上高县', '\0', '3', '1', '360900');
INSERT INTO `dns_area` VALUES ('360924', null, '0', '宜丰县', '\0', '3', '1', '360900');
INSERT INTO `dns_area` VALUES ('360925', null, '0', '靖安县', '\0', '3', '1', '360900');
INSERT INTO `dns_area` VALUES ('360926', null, '0', '铜鼓县', '\0', '3', '1', '360900');
INSERT INTO `dns_area` VALUES ('360981', null, '0', '丰城市', '\0', '3', '1', '360900');
INSERT INTO `dns_area` VALUES ('360982', null, '0', '樟树市', '\0', '3', '1', '360900');
INSERT INTO `dns_area` VALUES ('360983', null, '0', '高安市', '\0', '3', '1', '360900');
INSERT INTO `dns_area` VALUES ('361000', null, '0', '抚州市', '\0', '2', '1', '360000');
INSERT INTO `dns_area` VALUES ('361001', null, '0', '市辖区', '\0', '3', '1', '361000');
INSERT INTO `dns_area` VALUES ('361002', null, '0', '临川区', '\0', '3', '1', '361000');
INSERT INTO `dns_area` VALUES ('361021', null, '0', '南城县', '\0', '3', '1', '361000');
INSERT INTO `dns_area` VALUES ('361022', null, '0', '黎川县', '\0', '3', '1', '361000');
INSERT INTO `dns_area` VALUES ('361023', null, '0', '南丰县', '\0', '3', '1', '361000');
INSERT INTO `dns_area` VALUES ('361024', null, '0', '崇仁县', '\0', '3', '1', '361000');
INSERT INTO `dns_area` VALUES ('361025', null, '0', '乐安县', '\0', '3', '1', '361000');
INSERT INTO `dns_area` VALUES ('361026', null, '0', '宜黄县', '\0', '3', '1', '361000');
INSERT INTO `dns_area` VALUES ('361027', null, '0', '金溪县', '\0', '3', '1', '361000');
INSERT INTO `dns_area` VALUES ('361028', null, '0', '资溪县', '\0', '3', '1', '361000');
INSERT INTO `dns_area` VALUES ('361029', null, '0', '东乡县', '\0', '3', '1', '361000');
INSERT INTO `dns_area` VALUES ('361030', null, '0', '广昌县', '\0', '3', '1', '361000');
INSERT INTO `dns_area` VALUES ('361100', null, '0', '上饶市', '\0', '2', '1', '360000');
INSERT INTO `dns_area` VALUES ('361101', null, '0', '市辖区', '\0', '3', '1', '361100');
INSERT INTO `dns_area` VALUES ('361102', null, '0', '信州区', '\0', '3', '1', '361100');
INSERT INTO `dns_area` VALUES ('361121', null, '0', '上饶县', '\0', '3', '1', '361100');
INSERT INTO `dns_area` VALUES ('361122', null, '0', '广丰县', '\0', '3', '1', '361100');
INSERT INTO `dns_area` VALUES ('361123', null, '0', '玉山县', '\0', '3', '1', '361100');
INSERT INTO `dns_area` VALUES ('361124', null, '0', '铅山县', '\0', '3', '1', '361100');
INSERT INTO `dns_area` VALUES ('361125', null, '0', '横峰县', '\0', '3', '1', '361100');
INSERT INTO `dns_area` VALUES ('361126', null, '0', '弋阳县', '\0', '3', '1', '361100');
INSERT INTO `dns_area` VALUES ('361127', null, '0', '余干县', '\0', '3', '1', '361100');
INSERT INTO `dns_area` VALUES ('361128', null, '0', '鄱阳县', '\0', '3', '1', '361100');
INSERT INTO `dns_area` VALUES ('361129', null, '0', '万年县', '\0', '3', '1', '361100');
INSERT INTO `dns_area` VALUES ('361130', null, '0', '婺源县', '\0', '3', '1', '361100');
INSERT INTO `dns_area` VALUES ('361181', null, '0', '德兴市', '\0', '3', '1', '361100');
INSERT INTO `dns_area` VALUES ('370000', null, '0', '山东省', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('370100', null, '0', '济南市', '\0', '2', '1', '370000');
INSERT INTO `dns_area` VALUES ('370101', null, '0', '市辖区', '\0', '3', '1', '370100');
INSERT INTO `dns_area` VALUES ('370102', null, '0', '历下区', '\0', '3', '1', '370100');
INSERT INTO `dns_area` VALUES ('370103', null, '0', '市中区', '\0', '3', '1', '370100');
INSERT INTO `dns_area` VALUES ('370104', null, '0', '槐荫区', '\0', '3', '1', '370100');
INSERT INTO `dns_area` VALUES ('370105', null, '0', '天桥区', '\0', '3', '1', '370100');
INSERT INTO `dns_area` VALUES ('370112', null, '0', '历城区', '\0', '3', '1', '370100');
INSERT INTO `dns_area` VALUES ('370113', null, '0', '长清区', '\0', '3', '1', '370100');
INSERT INTO `dns_area` VALUES ('370124', null, '0', '平阴县', '\0', '3', '1', '370100');
INSERT INTO `dns_area` VALUES ('370125', null, '0', '济阳县', '\0', '3', '1', '370100');
INSERT INTO `dns_area` VALUES ('370126', null, '0', '商河县', '\0', '3', '1', '370100');
INSERT INTO `dns_area` VALUES ('370181', null, '0', '章丘市', '\0', '3', '1', '370100');
INSERT INTO `dns_area` VALUES ('370200', null, '0', '青岛市', '\0', '2', '1', '370000');
INSERT INTO `dns_area` VALUES ('370201', null, '0', '市辖区', '\0', '3', '1', '370200');
INSERT INTO `dns_area` VALUES ('370202', null, '0', '市南区', '\0', '3', '1', '370200');
INSERT INTO `dns_area` VALUES ('370203', null, '0', '市北区', '\0', '3', '1', '370200');
INSERT INTO `dns_area` VALUES ('370205', null, '0', '四方区', '\0', '3', '1', '370200');
INSERT INTO `dns_area` VALUES ('370211', null, '0', '黄岛区', '\0', '3', '1', '370200');
INSERT INTO `dns_area` VALUES ('370212', null, '0', '崂山区', '\0', '3', '1', '370200');
INSERT INTO `dns_area` VALUES ('370213', null, '0', '李沧区', '\0', '3', '1', '370200');
INSERT INTO `dns_area` VALUES ('370214', null, '0', '城阳区', '\0', '3', '1', '370200');
INSERT INTO `dns_area` VALUES ('370281', null, '0', '胶州市', '\0', '3', '1', '370200');
INSERT INTO `dns_area` VALUES ('370282', null, '0', '即墨市', '\0', '3', '1', '370200');
INSERT INTO `dns_area` VALUES ('370283', null, '0', '平度市', '\0', '3', '1', '370200');
INSERT INTO `dns_area` VALUES ('370284', null, '0', '胶南市', '\0', '3', '1', '370200');
INSERT INTO `dns_area` VALUES ('370285', null, '0', '莱西市', '\0', '3', '1', '370200');
INSERT INTO `dns_area` VALUES ('370300', null, '0', '淄博市', '\0', '2', '1', '370000');
INSERT INTO `dns_area` VALUES ('370301', null, '0', '市辖区', '\0', '3', '1', '370300');
INSERT INTO `dns_area` VALUES ('370302', null, '0', '淄川区', '\0', '3', '1', '370300');
INSERT INTO `dns_area` VALUES ('370303', null, '0', '张店区', '\0', '3', '1', '370300');
INSERT INTO `dns_area` VALUES ('370304', null, '0', '博山区', '\0', '3', '1', '370300');
INSERT INTO `dns_area` VALUES ('370305', null, '0', '临淄区', '\0', '3', '1', '370300');
INSERT INTO `dns_area` VALUES ('370306', null, '0', '周村区', '\0', '3', '1', '370300');
INSERT INTO `dns_area` VALUES ('370321', null, '0', '桓台县', '\0', '3', '1', '370300');
INSERT INTO `dns_area` VALUES ('370322', null, '0', '高青县', '\0', '3', '1', '370300');
INSERT INTO `dns_area` VALUES ('370323', null, '0', '沂源县', '\0', '3', '1', '370300');
INSERT INTO `dns_area` VALUES ('370400', null, '0', '枣庄市', '\0', '2', '1', '370000');
INSERT INTO `dns_area` VALUES ('370401', null, '0', '市辖区', '\0', '3', '1', '370400');
INSERT INTO `dns_area` VALUES ('370402', null, '0', '市中区', '\0', '3', '1', '370400');
INSERT INTO `dns_area` VALUES ('370403', null, '0', '薛城区', '\0', '3', '1', '370400');
INSERT INTO `dns_area` VALUES ('370404', null, '0', '峄城区', '\0', '3', '1', '370400');
INSERT INTO `dns_area` VALUES ('370405', null, '0', '台儿庄区', '\0', '3', '1', '370400');
INSERT INTO `dns_area` VALUES ('370406', null, '0', '山亭区', '\0', '3', '1', '370400');
INSERT INTO `dns_area` VALUES ('370481', null, '0', '滕州市', '\0', '3', '1', '370400');
INSERT INTO `dns_area` VALUES ('370500', null, '0', '东营市', '\0', '2', '1', '370000');
INSERT INTO `dns_area` VALUES ('370501', null, '0', '市辖区', '\0', '3', '1', '370500');
INSERT INTO `dns_area` VALUES ('370502', null, '0', '东营区', '\0', '3', '1', '370500');
INSERT INTO `dns_area` VALUES ('370503', null, '0', '河口区', '\0', '3', '1', '370500');
INSERT INTO `dns_area` VALUES ('370521', null, '0', '垦利县', '\0', '3', '1', '370500');
INSERT INTO `dns_area` VALUES ('370522', null, '0', '利津县', '\0', '3', '1', '370500');
INSERT INTO `dns_area` VALUES ('370523', null, '0', '广饶县', '\0', '3', '1', '370500');
INSERT INTO `dns_area` VALUES ('370600', null, '0', '烟台市', '\0', '2', '1', '370000');
INSERT INTO `dns_area` VALUES ('370601', null, '0', '市辖区', '\0', '3', '1', '370600');
INSERT INTO `dns_area` VALUES ('370602', null, '0', '芝罘区', '\0', '3', '1', '370600');
INSERT INTO `dns_area` VALUES ('370611', null, '0', '福山区', '\0', '3', '1', '370600');
INSERT INTO `dns_area` VALUES ('370612', null, '0', '牟平区', '\0', '3', '1', '370600');
INSERT INTO `dns_area` VALUES ('370613', null, '0', '莱山区', '\0', '3', '1', '370600');
INSERT INTO `dns_area` VALUES ('370634', null, '0', '长岛县', '\0', '3', '1', '370600');
INSERT INTO `dns_area` VALUES ('370681', null, '0', '龙口市', '\0', '3', '1', '370600');
INSERT INTO `dns_area` VALUES ('370682', null, '0', '莱阳市', '\0', '3', '1', '370600');
INSERT INTO `dns_area` VALUES ('370683', null, '0', '莱州市', '\0', '3', '1', '370600');
INSERT INTO `dns_area` VALUES ('370684', null, '0', '蓬莱市', '\0', '3', '1', '370600');
INSERT INTO `dns_area` VALUES ('370685', null, '0', '招远市', '\0', '3', '1', '370600');
INSERT INTO `dns_area` VALUES ('370686', null, '0', '栖霞市', '\0', '3', '1', '370600');
INSERT INTO `dns_area` VALUES ('370687', null, '0', '海阳市', '\0', '3', '1', '370600');
INSERT INTO `dns_area` VALUES ('370700', null, '0', '潍坊市', '\0', '2', '1', '370000');
INSERT INTO `dns_area` VALUES ('370701', null, '0', '市辖区', '\0', '3', '1', '370700');
INSERT INTO `dns_area` VALUES ('370702', null, '0', '潍城区', '\0', '3', '1', '370700');
INSERT INTO `dns_area` VALUES ('370703', null, '0', '寒亭区', '\0', '3', '1', '370700');
INSERT INTO `dns_area` VALUES ('370704', null, '0', '坊子区', '\0', '3', '1', '370700');
INSERT INTO `dns_area` VALUES ('370705', null, '0', '奎文区', '\0', '3', '1', '370700');
INSERT INTO `dns_area` VALUES ('370724', null, '0', '临朐县', '\0', '3', '1', '370700');
INSERT INTO `dns_area` VALUES ('370725', null, '0', '昌乐县', '\0', '3', '1', '370700');
INSERT INTO `dns_area` VALUES ('370781', null, '0', '青州市', '\0', '3', '1', '370700');
INSERT INTO `dns_area` VALUES ('370782', null, '0', '诸城市', '\0', '3', '1', '370700');
INSERT INTO `dns_area` VALUES ('370783', null, '0', '寿光市', '\0', '3', '1', '370700');
INSERT INTO `dns_area` VALUES ('370784', null, '0', '安丘市', '\0', '3', '1', '370700');
INSERT INTO `dns_area` VALUES ('370785', null, '0', '高密市', '\0', '3', '1', '370700');
INSERT INTO `dns_area` VALUES ('370786', null, '0', '昌邑市', '\0', '3', '1', '370700');
INSERT INTO `dns_area` VALUES ('370800', null, '0', '济宁市', '\0', '2', '1', '370000');
INSERT INTO `dns_area` VALUES ('370801', null, '0', '市辖区', '\0', '3', '1', '370800');
INSERT INTO `dns_area` VALUES ('370802', null, '0', '市中区', '\0', '3', '1', '370800');
INSERT INTO `dns_area` VALUES ('370811', null, '0', '任城区', '\0', '3', '1', '370800');
INSERT INTO `dns_area` VALUES ('370826', null, '0', '微山县', '\0', '3', '1', '370800');
INSERT INTO `dns_area` VALUES ('370827', null, '0', '鱼台县', '\0', '3', '1', '370800');
INSERT INTO `dns_area` VALUES ('370828', null, '0', '金乡县', '\0', '3', '1', '370800');
INSERT INTO `dns_area` VALUES ('370829', null, '0', '嘉祥县', '\0', '3', '1', '370800');
INSERT INTO `dns_area` VALUES ('370830', null, '0', '汶上县', '\0', '3', '1', '370800');
INSERT INTO `dns_area` VALUES ('370831', null, '0', '泗水县', '\0', '3', '1', '370800');
INSERT INTO `dns_area` VALUES ('370832', null, '0', '梁山县', '\0', '3', '1', '370800');
INSERT INTO `dns_area` VALUES ('370881', null, '0', '曲阜市', '\0', '3', '1', '370800');
INSERT INTO `dns_area` VALUES ('370882', null, '0', '兖州市', '\0', '3', '1', '370800');
INSERT INTO `dns_area` VALUES ('370883', null, '0', '邹城市', '\0', '3', '1', '370800');
INSERT INTO `dns_area` VALUES ('370900', null, '0', '泰安市', '\0', '2', '1', '370000');
INSERT INTO `dns_area` VALUES ('370901', null, '0', '市辖区', '\0', '3', '1', '370900');
INSERT INTO `dns_area` VALUES ('370902', null, '0', '泰山区', '\0', '3', '1', '370900');
INSERT INTO `dns_area` VALUES ('370903', null, '0', '岱岳区', '\0', '3', '1', '370900');
INSERT INTO `dns_area` VALUES ('370921', null, '0', '宁阳县', '\0', '3', '1', '370900');
INSERT INTO `dns_area` VALUES ('370923', null, '0', '东平县', '\0', '3', '1', '370900');
INSERT INTO `dns_area` VALUES ('370982', null, '0', '新泰市', '\0', '3', '1', '370900');
INSERT INTO `dns_area` VALUES ('370983', null, '0', '肥城市', '\0', '3', '1', '370900');
INSERT INTO `dns_area` VALUES ('371000', null, '0', '威海市', '\0', '2', '1', '370000');
INSERT INTO `dns_area` VALUES ('371001', null, '0', '市辖区', '\0', '3', '1', '371000');
INSERT INTO `dns_area` VALUES ('371002', null, '0', '环翠区', '\0', '3', '1', '371000');
INSERT INTO `dns_area` VALUES ('371081', null, '0', '文登市', '\0', '3', '1', '371000');
INSERT INTO `dns_area` VALUES ('371082', null, '0', '荣成市', '\0', '3', '1', '371000');
INSERT INTO `dns_area` VALUES ('371083', null, '0', '乳山市', '\0', '3', '1', '371000');
INSERT INTO `dns_area` VALUES ('371100', null, '0', '日照市', '\0', '2', '1', '370000');
INSERT INTO `dns_area` VALUES ('371101', null, '0', '市辖区', '\0', '3', '1', '371100');
INSERT INTO `dns_area` VALUES ('371102', null, '0', '东港区', '\0', '3', '1', '371100');
INSERT INTO `dns_area` VALUES ('371103', null, '0', '岚山区', '\0', '3', '1', '371100');
INSERT INTO `dns_area` VALUES ('371121', null, '0', '五莲县', '\0', '3', '1', '371100');
INSERT INTO `dns_area` VALUES ('371122', null, '0', '莒　县', '\0', '3', '1', '371100');
INSERT INTO `dns_area` VALUES ('371200', null, '0', '莱芜市', '\0', '2', '1', '370000');
INSERT INTO `dns_area` VALUES ('371201', null, '0', '市辖区', '\0', '3', '1', '371200');
INSERT INTO `dns_area` VALUES ('371202', null, '0', '莱城区', '\0', '3', '1', '371200');
INSERT INTO `dns_area` VALUES ('371203', null, '0', '钢城区', '\0', '3', '1', '371200');
INSERT INTO `dns_area` VALUES ('371300', null, '0', '临沂市', '\0', '2', '1', '370000');
INSERT INTO `dns_area` VALUES ('371301', null, '0', '市辖区', '\0', '3', '1', '371300');
INSERT INTO `dns_area` VALUES ('371302', null, '0', '兰山区', '\0', '3', '1', '371300');
INSERT INTO `dns_area` VALUES ('371311', null, '0', '罗庄区', '\0', '3', '1', '371300');
INSERT INTO `dns_area` VALUES ('371312', null, '0', '河东区', '\0', '3', '1', '371300');
INSERT INTO `dns_area` VALUES ('371321', null, '0', '沂南县', '\0', '3', '1', '371300');
INSERT INTO `dns_area` VALUES ('371322', null, '0', '郯城县', '\0', '3', '1', '371300');
INSERT INTO `dns_area` VALUES ('371323', null, '0', '沂水县', '\0', '3', '1', '371300');
INSERT INTO `dns_area` VALUES ('371324', null, '0', '苍山县', '\0', '3', '1', '371300');
INSERT INTO `dns_area` VALUES ('371325', null, '0', '费　县', '\0', '3', '1', '371300');
INSERT INTO `dns_area` VALUES ('371326', null, '0', '平邑县', '\0', '3', '1', '371300');
INSERT INTO `dns_area` VALUES ('371327', null, '0', '莒南县', '\0', '3', '1', '371300');
INSERT INTO `dns_area` VALUES ('371328', null, '0', '蒙阴县', '\0', '3', '1', '371300');
INSERT INTO `dns_area` VALUES ('371329', null, '0', '临沭县', '\0', '3', '1', '371300');
INSERT INTO `dns_area` VALUES ('371400', null, '0', '德州市', '\0', '2', '1', '370000');
INSERT INTO `dns_area` VALUES ('371401', null, '0', '市辖区', '\0', '3', '1', '371400');
INSERT INTO `dns_area` VALUES ('371402', null, '0', '德城区', '\0', '3', '1', '371400');
INSERT INTO `dns_area` VALUES ('371421', null, '0', '陵　县', '\0', '3', '1', '371400');
INSERT INTO `dns_area` VALUES ('371422', null, '0', '宁津县', '\0', '3', '1', '371400');
INSERT INTO `dns_area` VALUES ('371423', null, '0', '庆云县', '\0', '3', '1', '371400');
INSERT INTO `dns_area` VALUES ('371424', null, '0', '临邑县', '\0', '3', '1', '371400');
INSERT INTO `dns_area` VALUES ('371425', null, '0', '齐河县', '\0', '3', '1', '371400');
INSERT INTO `dns_area` VALUES ('371426', null, '0', '平原县', '\0', '3', '1', '371400');
INSERT INTO `dns_area` VALUES ('371427', null, '0', '夏津县', '\0', '3', '1', '371400');
INSERT INTO `dns_area` VALUES ('371428', null, '0', '武城县', '\0', '3', '1', '371400');
INSERT INTO `dns_area` VALUES ('371481', null, '0', '乐陵市', '\0', '3', '1', '371400');
INSERT INTO `dns_area` VALUES ('371482', null, '0', '禹城市', '\0', '3', '1', '371400');
INSERT INTO `dns_area` VALUES ('371500', null, '0', '聊城市', '\0', '2', '1', '370000');
INSERT INTO `dns_area` VALUES ('371501', null, '0', '市辖区', '\0', '3', '1', '371500');
INSERT INTO `dns_area` VALUES ('371502', null, '0', '东昌府区', '\0', '3', '1', '371500');
INSERT INTO `dns_area` VALUES ('371521', null, '0', '阳谷县', '\0', '3', '1', '371500');
INSERT INTO `dns_area` VALUES ('371522', null, '0', '莘　县', '\0', '3', '1', '371500');
INSERT INTO `dns_area` VALUES ('371523', null, '0', '茌平县', '\0', '3', '1', '371500');
INSERT INTO `dns_area` VALUES ('371524', null, '0', '东阿县', '\0', '3', '1', '371500');
INSERT INTO `dns_area` VALUES ('371525', null, '0', '冠　县', '\0', '3', '1', '371500');
INSERT INTO `dns_area` VALUES ('371526', null, '0', '高唐县', '\0', '3', '1', '371500');
INSERT INTO `dns_area` VALUES ('371581', null, '0', '临清市', '\0', '3', '1', '371500');
INSERT INTO `dns_area` VALUES ('371600', null, '0', '滨州市', '\0', '2', '1', '370000');
INSERT INTO `dns_area` VALUES ('371601', null, '0', '市辖区', '\0', '3', '1', '371600');
INSERT INTO `dns_area` VALUES ('371602', null, '0', '滨城区', '\0', '3', '1', '371600');
INSERT INTO `dns_area` VALUES ('371621', null, '0', '惠民县', '\0', '3', '1', '371600');
INSERT INTO `dns_area` VALUES ('371622', null, '0', '阳信县', '\0', '3', '1', '371600');
INSERT INTO `dns_area` VALUES ('371623', null, '0', '无棣县', '\0', '3', '1', '371600');
INSERT INTO `dns_area` VALUES ('371624', null, '0', '沾化县', '\0', '3', '1', '371600');
INSERT INTO `dns_area` VALUES ('371625', null, '0', '博兴县', '\0', '3', '1', '371600');
INSERT INTO `dns_area` VALUES ('371626', null, '0', '邹平县', '\0', '3', '1', '371600');
INSERT INTO `dns_area` VALUES ('371700', null, '0', '荷泽市', '\0', '2', '1', '370000');
INSERT INTO `dns_area` VALUES ('371701', null, '0', '市辖区', '\0', '3', '1', '371700');
INSERT INTO `dns_area` VALUES ('371702', null, '0', '牡丹区', '\0', '3', '1', '371700');
INSERT INTO `dns_area` VALUES ('371721', null, '0', '曹　县', '\0', '3', '1', '371700');
INSERT INTO `dns_area` VALUES ('371722', null, '0', '单　县', '\0', '3', '1', '371700');
INSERT INTO `dns_area` VALUES ('371723', null, '0', '成武县', '\0', '3', '1', '371700');
INSERT INTO `dns_area` VALUES ('371724', null, '0', '巨野县', '\0', '3', '1', '371700');
INSERT INTO `dns_area` VALUES ('371725', null, '0', '郓城县', '\0', '3', '1', '371700');
INSERT INTO `dns_area` VALUES ('371726', null, '0', '鄄城县', '\0', '3', '1', '371700');
INSERT INTO `dns_area` VALUES ('371727', null, '0', '定陶县', '\0', '3', '1', '371700');
INSERT INTO `dns_area` VALUES ('371728', null, '0', '东明县', '\0', '3', '1', '371700');
INSERT INTO `dns_area` VALUES ('410000', null, '0', '河南省', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('410100', null, '0', '郑州市', '\0', '2', '1', '410000');
INSERT INTO `dns_area` VALUES ('410101', null, '0', '市辖区', '\0', '3', '1', '410100');
INSERT INTO `dns_area` VALUES ('410102', null, '0', '中原区', '\0', '3', '1', '410100');
INSERT INTO `dns_area` VALUES ('410103', null, '0', '二七区', '\0', '3', '1', '410100');
INSERT INTO `dns_area` VALUES ('410104', null, '0', '管城回族区', '\0', '3', '1', '410100');
INSERT INTO `dns_area` VALUES ('410105', null, '0', '金水区', '\0', '3', '1', '410100');
INSERT INTO `dns_area` VALUES ('410106', null, '0', '上街区', '\0', '3', '1', '410100');
INSERT INTO `dns_area` VALUES ('410108', null, '0', '邙山区', '\0', '3', '1', '410100');
INSERT INTO `dns_area` VALUES ('410122', null, '0', '中牟县', '\0', '3', '1', '410100');
INSERT INTO `dns_area` VALUES ('410181', null, '0', '巩义市', '\0', '3', '1', '410100');
INSERT INTO `dns_area` VALUES ('410182', null, '0', '荥阳市', '\0', '3', '1', '410100');
INSERT INTO `dns_area` VALUES ('410183', null, '0', '新密市', '\0', '3', '1', '410100');
INSERT INTO `dns_area` VALUES ('410184', null, '0', '新郑市', '\0', '3', '1', '410100');
INSERT INTO `dns_area` VALUES ('410185', null, '0', '登封市', '\0', '3', '1', '410100');
INSERT INTO `dns_area` VALUES ('410200', null, '0', '开封市', '\0', '2', '1', '410000');
INSERT INTO `dns_area` VALUES ('410201', null, '0', '市辖区', '\0', '3', '1', '410200');
INSERT INTO `dns_area` VALUES ('410202', null, '0', '龙亭区', '\0', '3', '1', '410200');
INSERT INTO `dns_area` VALUES ('410203', null, '0', '顺河回族区', '\0', '3', '1', '410200');
INSERT INTO `dns_area` VALUES ('410204', null, '0', '鼓楼区', '\0', '3', '1', '410200');
INSERT INTO `dns_area` VALUES ('410205', null, '0', '南关区', '\0', '3', '1', '410200');
INSERT INTO `dns_area` VALUES ('410211', null, '0', '郊　区', '\0', '3', '1', '410200');
INSERT INTO `dns_area` VALUES ('410221', null, '0', '杞　县', '\0', '3', '1', '410200');
INSERT INTO `dns_area` VALUES ('410222', null, '0', '通许县', '\0', '3', '1', '410200');
INSERT INTO `dns_area` VALUES ('410223', null, '0', '尉氏县', '\0', '3', '1', '410200');
INSERT INTO `dns_area` VALUES ('410224', null, '0', '开封县', '\0', '3', '1', '410200');
INSERT INTO `dns_area` VALUES ('410225', null, '0', '兰考县', '\0', '3', '1', '410200');
INSERT INTO `dns_area` VALUES ('410300', null, '0', '洛阳市', '\0', '2', '1', '410000');
INSERT INTO `dns_area` VALUES ('410301', null, '0', '市辖区', '\0', '3', '1', '410300');
INSERT INTO `dns_area` VALUES ('410302', null, '0', '老城区', '\0', '3', '1', '410300');
INSERT INTO `dns_area` VALUES ('410303', null, '0', '西工区', '\0', '3', '1', '410300');
INSERT INTO `dns_area` VALUES ('410304', null, '0', '廛河回族区', '\0', '3', '1', '410300');
INSERT INTO `dns_area` VALUES ('410305', null, '0', '涧西区', '\0', '3', '1', '410300');
INSERT INTO `dns_area` VALUES ('410306', null, '0', '吉利区', '\0', '3', '1', '410300');
INSERT INTO `dns_area` VALUES ('410307', null, '0', '洛龙区', '\0', '3', '1', '410300');
INSERT INTO `dns_area` VALUES ('410322', null, '0', '孟津县', '\0', '3', '1', '410300');
INSERT INTO `dns_area` VALUES ('410323', null, '0', '新安县', '\0', '3', '1', '410300');
INSERT INTO `dns_area` VALUES ('410324', null, '0', '栾川县', '\0', '3', '1', '410300');
INSERT INTO `dns_area` VALUES ('410325', null, '0', '嵩　县', '\0', '3', '1', '410300');
INSERT INTO `dns_area` VALUES ('410326', null, '0', '汝阳县', '\0', '3', '1', '410300');
INSERT INTO `dns_area` VALUES ('410327', null, '0', '宜阳县', '\0', '3', '1', '410300');
INSERT INTO `dns_area` VALUES ('410328', null, '0', '洛宁县', '\0', '3', '1', '410300');
INSERT INTO `dns_area` VALUES ('410329', null, '0', '伊川县', '\0', '3', '1', '410300');
INSERT INTO `dns_area` VALUES ('410381', null, '0', '偃师市', '\0', '3', '1', '410300');
INSERT INTO `dns_area` VALUES ('410400', null, '0', '平顶山市', '\0', '2', '1', '410000');
INSERT INTO `dns_area` VALUES ('410401', null, '0', '市辖区', '\0', '3', '1', '410400');
INSERT INTO `dns_area` VALUES ('410402', null, '0', '新华区', '\0', '3', '1', '410400');
INSERT INTO `dns_area` VALUES ('410403', null, '0', '卫东区', '\0', '3', '1', '410400');
INSERT INTO `dns_area` VALUES ('410404', null, '0', '石龙区', '\0', '3', '1', '410400');
INSERT INTO `dns_area` VALUES ('410411', null, '0', '湛河区', '\0', '3', '1', '410400');
INSERT INTO `dns_area` VALUES ('410421', null, '0', '宝丰县', '\0', '3', '1', '410400');
INSERT INTO `dns_area` VALUES ('410422', null, '0', '叶　县', '\0', '3', '1', '410400');
INSERT INTO `dns_area` VALUES ('410423', null, '0', '鲁山县', '\0', '3', '1', '410400');
INSERT INTO `dns_area` VALUES ('410425', null, '0', '郏　县', '\0', '3', '1', '410400');
INSERT INTO `dns_area` VALUES ('410481', null, '0', '舞钢市', '\0', '3', '1', '410400');
INSERT INTO `dns_area` VALUES ('410482', null, '0', '汝州市', '\0', '3', '1', '410400');
INSERT INTO `dns_area` VALUES ('410500', null, '0', '安阳市', '\0', '2', '1', '410000');
INSERT INTO `dns_area` VALUES ('410501', null, '0', '市辖区', '\0', '3', '1', '410500');
INSERT INTO `dns_area` VALUES ('410502', null, '0', '文峰区', '\0', '3', '1', '410500');
INSERT INTO `dns_area` VALUES ('410503', null, '0', '北关区', '\0', '3', '1', '410500');
INSERT INTO `dns_area` VALUES ('410505', null, '0', '殷都区', '\0', '3', '1', '410500');
INSERT INTO `dns_area` VALUES ('410506', null, '0', '龙安区', '\0', '3', '1', '410500');
INSERT INTO `dns_area` VALUES ('410522', null, '0', '安阳县', '\0', '3', '1', '410500');
INSERT INTO `dns_area` VALUES ('410523', null, '0', '汤阴县', '\0', '3', '1', '410500');
INSERT INTO `dns_area` VALUES ('410526', null, '0', '滑　县', '\0', '3', '1', '410500');
INSERT INTO `dns_area` VALUES ('410527', null, '0', '内黄县', '\0', '3', '1', '410500');
INSERT INTO `dns_area` VALUES ('410581', null, '0', '林州市', '\0', '3', '1', '410500');
INSERT INTO `dns_area` VALUES ('410600', null, '0', '鹤壁市', '\0', '2', '1', '410000');
INSERT INTO `dns_area` VALUES ('410601', null, '0', '市辖区', '\0', '3', '1', '410600');
INSERT INTO `dns_area` VALUES ('410602', null, '0', '鹤山区', '\0', '3', '1', '410600');
INSERT INTO `dns_area` VALUES ('410603', null, '0', '山城区', '\0', '3', '1', '410600');
INSERT INTO `dns_area` VALUES ('410611', null, '0', '淇滨区', '\0', '3', '1', '410600');
INSERT INTO `dns_area` VALUES ('410621', null, '0', '浚　县', '\0', '3', '1', '410600');
INSERT INTO `dns_area` VALUES ('410622', null, '0', '淇　县', '\0', '3', '1', '410600');
INSERT INTO `dns_area` VALUES ('410700', null, '0', '新乡市', '\0', '2', '1', '410000');
INSERT INTO `dns_area` VALUES ('410701', null, '0', '市辖区', '\0', '3', '1', '410700');
INSERT INTO `dns_area` VALUES ('410702', null, '0', '红旗区', '\0', '3', '1', '410700');
INSERT INTO `dns_area` VALUES ('410703', null, '0', '卫滨区', '\0', '3', '1', '410700');
INSERT INTO `dns_area` VALUES ('410704', null, '0', '凤泉区', '\0', '3', '1', '410700');
INSERT INTO `dns_area` VALUES ('410711', null, '0', '牧野区', '\0', '3', '1', '410700');
INSERT INTO `dns_area` VALUES ('410721', null, '0', '新乡县', '\0', '3', '1', '410700');
INSERT INTO `dns_area` VALUES ('410724', null, '0', '获嘉县', '\0', '3', '1', '410700');
INSERT INTO `dns_area` VALUES ('410725', null, '0', '原阳县', '\0', '3', '1', '410700');
INSERT INTO `dns_area` VALUES ('410726', null, '0', '延津县', '\0', '3', '1', '410700');
INSERT INTO `dns_area` VALUES ('410727', null, '0', '封丘县', '\0', '3', '1', '410700');
INSERT INTO `dns_area` VALUES ('410728', null, '0', '长垣县', '\0', '3', '1', '410700');
INSERT INTO `dns_area` VALUES ('410781', null, '0', '卫辉市', '\0', '3', '1', '410700');
INSERT INTO `dns_area` VALUES ('410782', null, '0', '辉县市', '\0', '3', '1', '410700');
INSERT INTO `dns_area` VALUES ('410800', null, '0', '焦作市', '\0', '2', '1', '410000');
INSERT INTO `dns_area` VALUES ('410801', null, '0', '市辖区', '\0', '3', '1', '410800');
INSERT INTO `dns_area` VALUES ('410802', null, '0', '解放区', '\0', '3', '1', '410800');
INSERT INTO `dns_area` VALUES ('410803', null, '0', '中站区', '\0', '3', '1', '410800');
INSERT INTO `dns_area` VALUES ('410804', null, '0', '马村区', '\0', '3', '1', '410800');
INSERT INTO `dns_area` VALUES ('410811', null, '0', '山阳区', '\0', '3', '1', '410800');
INSERT INTO `dns_area` VALUES ('410821', null, '0', '修武县', '\0', '3', '1', '410800');
INSERT INTO `dns_area` VALUES ('410822', null, '0', '博爱县', '\0', '3', '1', '410800');
INSERT INTO `dns_area` VALUES ('410823', null, '0', '武陟县', '\0', '3', '1', '410800');
INSERT INTO `dns_area` VALUES ('410825', null, '0', '温　县', '\0', '3', '1', '410800');
INSERT INTO `dns_area` VALUES ('410881', null, '0', '济源市', '\0', '3', '1', '410800');
INSERT INTO `dns_area` VALUES ('410882', null, '0', '沁阳市', '\0', '3', '1', '410800');
INSERT INTO `dns_area` VALUES ('410883', null, '0', '孟州市', '\0', '3', '1', '410800');
INSERT INTO `dns_area` VALUES ('410900', null, '0', '濮阳市', '\0', '2', '1', '410000');
INSERT INTO `dns_area` VALUES ('410901', null, '0', '市辖区', '\0', '3', '1', '410900');
INSERT INTO `dns_area` VALUES ('410902', null, '0', '华龙区', '\0', '3', '1', '410900');
INSERT INTO `dns_area` VALUES ('410922', null, '0', '清丰县', '\0', '3', '1', '410900');
INSERT INTO `dns_area` VALUES ('410923', null, '0', '南乐县', '\0', '3', '1', '410900');
INSERT INTO `dns_area` VALUES ('410926', null, '0', '范　县', '\0', '3', '1', '410900');
INSERT INTO `dns_area` VALUES ('410927', null, '0', '台前县', '\0', '3', '1', '410900');
INSERT INTO `dns_area` VALUES ('410928', null, '0', '濮阳县', '\0', '3', '1', '410900');
INSERT INTO `dns_area` VALUES ('411000', null, '0', '许昌市', '\0', '2', '1', '410000');
INSERT INTO `dns_area` VALUES ('411001', null, '0', '市辖区', '\0', '3', '1', '411000');
INSERT INTO `dns_area` VALUES ('411002', null, '0', '魏都区', '\0', '3', '1', '411000');
INSERT INTO `dns_area` VALUES ('411023', null, '0', '许昌县', '\0', '3', '1', '411000');
INSERT INTO `dns_area` VALUES ('411024', null, '0', '鄢陵县', '\0', '3', '1', '411000');
INSERT INTO `dns_area` VALUES ('411025', null, '0', '襄城县', '\0', '3', '1', '411000');
INSERT INTO `dns_area` VALUES ('411081', null, '0', '禹州市', '\0', '3', '1', '411000');
INSERT INTO `dns_area` VALUES ('411082', null, '0', '长葛市', '\0', '3', '1', '411000');
INSERT INTO `dns_area` VALUES ('411100', null, '0', '漯河市', '\0', '2', '1', '410000');
INSERT INTO `dns_area` VALUES ('411101', null, '0', '市辖区', '\0', '3', '1', '411100');
INSERT INTO `dns_area` VALUES ('411102', null, '0', '源汇区', '\0', '3', '1', '411100');
INSERT INTO `dns_area` VALUES ('411103', null, '0', '郾城区', '\0', '3', '1', '411100');
INSERT INTO `dns_area` VALUES ('411104', null, '0', '召陵区', '\0', '3', '1', '411100');
INSERT INTO `dns_area` VALUES ('411121', null, '0', '舞阳县', '\0', '3', '1', '411100');
INSERT INTO `dns_area` VALUES ('411122', null, '0', '临颍县', '\0', '3', '1', '411100');
INSERT INTO `dns_area` VALUES ('411200', null, '0', '三门峡市', '\0', '2', '1', '410000');
INSERT INTO `dns_area` VALUES ('411201', null, '0', '市辖区', '\0', '3', '1', '411200');
INSERT INTO `dns_area` VALUES ('411202', null, '0', '湖滨区', '\0', '3', '1', '411200');
INSERT INTO `dns_area` VALUES ('411221', null, '0', '渑池县', '\0', '3', '1', '411200');
INSERT INTO `dns_area` VALUES ('411222', null, '0', '陕　县', '\0', '3', '1', '411200');
INSERT INTO `dns_area` VALUES ('411224', null, '0', '卢氏县', '\0', '3', '1', '411200');
INSERT INTO `dns_area` VALUES ('411281', null, '0', '义马市', '\0', '3', '1', '411200');
INSERT INTO `dns_area` VALUES ('411282', null, '0', '灵宝市', '\0', '3', '1', '411200');
INSERT INTO `dns_area` VALUES ('411300', null, '0', '南阳市', '\0', '2', '1', '410000');
INSERT INTO `dns_area` VALUES ('411301', null, '0', '市辖区', '\0', '3', '1', '411300');
INSERT INTO `dns_area` VALUES ('411302', null, '0', '宛城区', '\0', '3', '1', '411300');
INSERT INTO `dns_area` VALUES ('411303', null, '0', '卧龙区', '\0', '3', '1', '411300');
INSERT INTO `dns_area` VALUES ('411321', null, '0', '南召县', '\0', '3', '1', '411300');
INSERT INTO `dns_area` VALUES ('411322', null, '0', '方城县', '\0', '3', '1', '411300');
INSERT INTO `dns_area` VALUES ('411323', null, '0', '西峡县', '\0', '3', '1', '411300');
INSERT INTO `dns_area` VALUES ('411324', null, '0', '镇平县', '\0', '3', '1', '411300');
INSERT INTO `dns_area` VALUES ('411325', null, '0', '内乡县', '\0', '3', '1', '411300');
INSERT INTO `dns_area` VALUES ('411326', null, '0', '淅川县', '\0', '3', '1', '411300');
INSERT INTO `dns_area` VALUES ('411327', null, '0', '社旗县', '\0', '3', '1', '411300');
INSERT INTO `dns_area` VALUES ('411328', null, '0', '唐河县', '\0', '3', '1', '411300');
INSERT INTO `dns_area` VALUES ('411329', null, '0', '新野县', '\0', '3', '1', '411300');
INSERT INTO `dns_area` VALUES ('411330', null, '0', '桐柏县', '\0', '3', '1', '411300');
INSERT INTO `dns_area` VALUES ('411381', null, '0', '邓州市', '\0', '3', '1', '411300');
INSERT INTO `dns_area` VALUES ('411400', null, '0', '商丘市', '\0', '2', '1', '410000');
INSERT INTO `dns_area` VALUES ('411401', null, '0', '市辖区', '\0', '3', '1', '411400');
INSERT INTO `dns_area` VALUES ('411402', null, '0', '梁园区', '\0', '3', '1', '411400');
INSERT INTO `dns_area` VALUES ('411403', null, '0', '睢阳区', '\0', '3', '1', '411400');
INSERT INTO `dns_area` VALUES ('411421', null, '0', '民权县', '\0', '3', '1', '411400');
INSERT INTO `dns_area` VALUES ('411422', null, '0', '睢　县', '\0', '3', '1', '411400');
INSERT INTO `dns_area` VALUES ('411423', null, '0', '宁陵县', '\0', '3', '1', '411400');
INSERT INTO `dns_area` VALUES ('411424', null, '0', '柘城县', '\0', '3', '1', '411400');
INSERT INTO `dns_area` VALUES ('411425', null, '0', '虞城县', '\0', '3', '1', '411400');
INSERT INTO `dns_area` VALUES ('411426', null, '0', '夏邑县', '\0', '3', '1', '411400');
INSERT INTO `dns_area` VALUES ('411481', null, '0', '永城市', '\0', '3', '1', '411400');
INSERT INTO `dns_area` VALUES ('411500', null, '0', '信阳市', '\0', '2', '1', '410000');
INSERT INTO `dns_area` VALUES ('411501', null, '0', '市辖区', '\0', '3', '1', '411500');
INSERT INTO `dns_area` VALUES ('411502', null, '0', '师河区', '\0', '3', '1', '411500');
INSERT INTO `dns_area` VALUES ('411503', null, '0', '平桥区', '\0', '3', '1', '411500');
INSERT INTO `dns_area` VALUES ('411521', null, '0', '罗山县', '\0', '3', '1', '411500');
INSERT INTO `dns_area` VALUES ('411522', null, '0', '光山县', '\0', '3', '1', '411500');
INSERT INTO `dns_area` VALUES ('411523', null, '0', '新　县', '\0', '3', '1', '411500');
INSERT INTO `dns_area` VALUES ('411524', null, '0', '商城县', '\0', '3', '1', '411500');
INSERT INTO `dns_area` VALUES ('411525', null, '0', '固始县', '\0', '3', '1', '411500');
INSERT INTO `dns_area` VALUES ('411526', null, '0', '潢川县', '\0', '3', '1', '411500');
INSERT INTO `dns_area` VALUES ('411527', null, '0', '淮滨县', '\0', '3', '1', '411500');
INSERT INTO `dns_area` VALUES ('411528', null, '0', '息　县', '\0', '3', '1', '411500');
INSERT INTO `dns_area` VALUES ('411600', null, '0', '周口市', '\0', '2', '1', '410000');
INSERT INTO `dns_area` VALUES ('411601', null, '0', '市辖区', '\0', '3', '1', '411600');
INSERT INTO `dns_area` VALUES ('411602', null, '0', '川汇区', '\0', '3', '1', '411600');
INSERT INTO `dns_area` VALUES ('411621', null, '0', '扶沟县', '\0', '3', '1', '411600');
INSERT INTO `dns_area` VALUES ('411622', null, '0', '西华县', '\0', '3', '1', '411600');
INSERT INTO `dns_area` VALUES ('411623', null, '0', '商水县', '\0', '3', '1', '411600');
INSERT INTO `dns_area` VALUES ('411624', null, '0', '沈丘县', '\0', '3', '1', '411600');
INSERT INTO `dns_area` VALUES ('411625', null, '0', '郸城县', '\0', '3', '1', '411600');
INSERT INTO `dns_area` VALUES ('411626', null, '0', '淮阳县', '\0', '3', '1', '411600');
INSERT INTO `dns_area` VALUES ('411627', null, '0', '太康县', '\0', '3', '1', '411600');
INSERT INTO `dns_area` VALUES ('411628', null, '0', '鹿邑县', '\0', '3', '1', '411600');
INSERT INTO `dns_area` VALUES ('411681', null, '0', '项城市', '\0', '3', '1', '411600');
INSERT INTO `dns_area` VALUES ('411700', null, '0', '驻马店市', '\0', '2', '1', '410000');
INSERT INTO `dns_area` VALUES ('411701', null, '0', '市辖区', '\0', '3', '1', '411700');
INSERT INTO `dns_area` VALUES ('411702', null, '0', '驿城区', '\0', '3', '1', '411700');
INSERT INTO `dns_area` VALUES ('411721', null, '0', '西平县', '\0', '3', '1', '411700');
INSERT INTO `dns_area` VALUES ('411722', null, '0', '上蔡县', '\0', '3', '1', '411700');
INSERT INTO `dns_area` VALUES ('411723', null, '0', '平舆县', '\0', '3', '1', '411700');
INSERT INTO `dns_area` VALUES ('411724', null, '0', '正阳县', '\0', '3', '1', '411700');
INSERT INTO `dns_area` VALUES ('411725', null, '0', '确山县', '\0', '3', '1', '411700');
INSERT INTO `dns_area` VALUES ('411726', null, '0', '泌阳县', '\0', '3', '1', '411700');
INSERT INTO `dns_area` VALUES ('411727', null, '0', '汝南县', '\0', '3', '1', '411700');
INSERT INTO `dns_area` VALUES ('411728', null, '0', '遂平县', '\0', '3', '1', '411700');
INSERT INTO `dns_area` VALUES ('411729', null, '0', '新蔡县', '\0', '3', '1', '411700');
INSERT INTO `dns_area` VALUES ('420000', null, '0', '湖北省', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('420100', null, '0', '武汉市', '\0', '2', '1', '420000');
INSERT INTO `dns_area` VALUES ('420101', null, '0', '市辖区', '\0', '3', '1', '420100');
INSERT INTO `dns_area` VALUES ('420102', null, '0', '江岸区', '\0', '3', '1', '420100');
INSERT INTO `dns_area` VALUES ('420103', null, '0', '江汉区', '\0', '3', '1', '420100');
INSERT INTO `dns_area` VALUES ('420104', null, '0', '乔口区', '\0', '3', '1', '420100');
INSERT INTO `dns_area` VALUES ('420105', null, '0', '汉阳区', '\0', '3', '1', '420100');
INSERT INTO `dns_area` VALUES ('420106', null, '0', '武昌区', '\0', '3', '1', '420100');
INSERT INTO `dns_area` VALUES ('420107', null, '0', '青山区', '\0', '3', '1', '420100');
INSERT INTO `dns_area` VALUES ('420111', null, '0', '洪山区', '\0', '3', '1', '420100');
INSERT INTO `dns_area` VALUES ('420112', null, '0', '东西湖区', '\0', '3', '1', '420100');
INSERT INTO `dns_area` VALUES ('420113', null, '0', '汉南区', '\0', '3', '1', '420100');
INSERT INTO `dns_area` VALUES ('420114', null, '0', '蔡甸区', '\0', '3', '1', '420100');
INSERT INTO `dns_area` VALUES ('420115', null, '0', '江夏区', '\0', '3', '1', '420100');
INSERT INTO `dns_area` VALUES ('420116', null, '0', '黄陂区', '\0', '3', '1', '420100');
INSERT INTO `dns_area` VALUES ('420117', null, '0', '新洲区', '\0', '3', '1', '420100');
INSERT INTO `dns_area` VALUES ('420200', null, '0', '黄石市', '\0', '2', '1', '420000');
INSERT INTO `dns_area` VALUES ('420201', null, '0', '市辖区', '\0', '3', '1', '420200');
INSERT INTO `dns_area` VALUES ('420202', null, '0', '黄石港区', '\0', '3', '1', '420200');
INSERT INTO `dns_area` VALUES ('420203', null, '0', '西塞山区', '\0', '3', '1', '420200');
INSERT INTO `dns_area` VALUES ('420204', null, '0', '下陆区', '\0', '3', '1', '420200');
INSERT INTO `dns_area` VALUES ('420205', null, '0', '铁山区', '\0', '3', '1', '420200');
INSERT INTO `dns_area` VALUES ('420222', null, '0', '阳新县', '\0', '3', '1', '420200');
INSERT INTO `dns_area` VALUES ('420281', null, '0', '大冶市', '\0', '3', '1', '420200');
INSERT INTO `dns_area` VALUES ('420300', null, '0', '十堰市', '\0', '2', '1', '420000');
INSERT INTO `dns_area` VALUES ('420301', null, '0', '市辖区', '\0', '3', '1', '420300');
INSERT INTO `dns_area` VALUES ('420302', null, '0', '茅箭区', '\0', '3', '1', '420300');
INSERT INTO `dns_area` VALUES ('420303', null, '0', '张湾区', '\0', '3', '1', '420300');
INSERT INTO `dns_area` VALUES ('420321', null, '0', '郧　县', '\0', '3', '1', '420300');
INSERT INTO `dns_area` VALUES ('420322', null, '0', '郧西县', '\0', '3', '1', '420300');
INSERT INTO `dns_area` VALUES ('420323', null, '0', '竹山县', '\0', '3', '1', '420300');
INSERT INTO `dns_area` VALUES ('420324', null, '0', '竹溪县', '\0', '3', '1', '420300');
INSERT INTO `dns_area` VALUES ('420325', null, '0', '房　县', '\0', '3', '1', '420300');
INSERT INTO `dns_area` VALUES ('420381', null, '0', '丹江口市', '\0', '3', '1', '420300');
INSERT INTO `dns_area` VALUES ('420500', null, '0', '宜昌市', '\0', '2', '1', '420000');
INSERT INTO `dns_area` VALUES ('420501', null, '0', '市辖区', '\0', '3', '1', '420500');
INSERT INTO `dns_area` VALUES ('420502', null, '0', '西陵区', '\0', '3', '1', '420500');
INSERT INTO `dns_area` VALUES ('420503', null, '0', '伍家岗区', '\0', '3', '1', '420500');
INSERT INTO `dns_area` VALUES ('420504', null, '0', '点军区', '\0', '3', '1', '420500');
INSERT INTO `dns_area` VALUES ('420505', null, '0', '猇亭区', '\0', '3', '1', '420500');
INSERT INTO `dns_area` VALUES ('420506', null, '0', '夷陵区', '\0', '3', '1', '420500');
INSERT INTO `dns_area` VALUES ('420525', null, '0', '远安县', '\0', '3', '1', '420500');
INSERT INTO `dns_area` VALUES ('420526', null, '0', '兴山县', '\0', '3', '1', '420500');
INSERT INTO `dns_area` VALUES ('420527', null, '0', '秭归县', '\0', '3', '1', '420500');
INSERT INTO `dns_area` VALUES ('420528', null, '0', '长阳土家族自治县', '\0', '3', '1', '420500');
INSERT INTO `dns_area` VALUES ('420529', null, '0', '五峰土家族自治县', '\0', '3', '1', '420500');
INSERT INTO `dns_area` VALUES ('420581', null, '0', '宜都市', '\0', '3', '1', '420500');
INSERT INTO `dns_area` VALUES ('420582', null, '0', '当阳市', '\0', '3', '1', '420500');
INSERT INTO `dns_area` VALUES ('420583', null, '0', '枝江市', '\0', '3', '1', '420500');
INSERT INTO `dns_area` VALUES ('420600', null, '0', '襄樊市', '\0', '2', '1', '420000');
INSERT INTO `dns_area` VALUES ('420601', null, '0', '市辖区', '\0', '3', '1', '420600');
INSERT INTO `dns_area` VALUES ('420602', null, '0', '襄城区', '\0', '3', '1', '420600');
INSERT INTO `dns_area` VALUES ('420606', null, '0', '樊城区', '\0', '3', '1', '420600');
INSERT INTO `dns_area` VALUES ('420607', null, '0', '襄阳区', '\0', '3', '1', '420600');
INSERT INTO `dns_area` VALUES ('420624', null, '0', '南漳县', '\0', '3', '1', '420600');
INSERT INTO `dns_area` VALUES ('420625', null, '0', '谷城县', '\0', '3', '1', '420600');
INSERT INTO `dns_area` VALUES ('420626', null, '0', '保康县', '\0', '3', '1', '420600');
INSERT INTO `dns_area` VALUES ('420682', null, '0', '老河口市', '\0', '3', '1', '420600');
INSERT INTO `dns_area` VALUES ('420683', null, '0', '枣阳市', '\0', '3', '1', '420600');
INSERT INTO `dns_area` VALUES ('420684', null, '0', '宜城市', '\0', '3', '1', '420600');
INSERT INTO `dns_area` VALUES ('420700', null, '0', '鄂州市', '\0', '2', '1', '420000');
INSERT INTO `dns_area` VALUES ('420701', null, '0', '市辖区', '\0', '3', '1', '420700');
INSERT INTO `dns_area` VALUES ('420702', null, '0', '梁子湖区', '\0', '3', '1', '420700');
INSERT INTO `dns_area` VALUES ('420703', null, '0', '华容区', '\0', '3', '1', '420700');
INSERT INTO `dns_area` VALUES ('420704', null, '0', '鄂城区', '\0', '3', '1', '420700');
INSERT INTO `dns_area` VALUES ('420800', null, '0', '荆门市', '\0', '2', '1', '420000');
INSERT INTO `dns_area` VALUES ('420801', null, '0', '市辖区', '\0', '3', '1', '420800');
INSERT INTO `dns_area` VALUES ('420802', null, '0', '东宝区', '\0', '3', '1', '420800');
INSERT INTO `dns_area` VALUES ('420804', null, '0', '掇刀区', '\0', '3', '1', '420800');
INSERT INTO `dns_area` VALUES ('420821', null, '0', '京山县', '\0', '3', '1', '420800');
INSERT INTO `dns_area` VALUES ('420822', null, '0', '沙洋县', '\0', '3', '1', '420800');
INSERT INTO `dns_area` VALUES ('420881', null, '0', '钟祥市', '\0', '3', '1', '420800');
INSERT INTO `dns_area` VALUES ('420900', null, '0', '孝感市', '\0', '2', '1', '420000');
INSERT INTO `dns_area` VALUES ('420901', null, '0', '市辖区', '\0', '3', '1', '420900');
INSERT INTO `dns_area` VALUES ('420902', null, '0', '孝南区', '\0', '3', '1', '420900');
INSERT INTO `dns_area` VALUES ('420921', null, '0', '孝昌县', '\0', '3', '1', '420900');
INSERT INTO `dns_area` VALUES ('420922', null, '0', '大悟县', '\0', '3', '1', '420900');
INSERT INTO `dns_area` VALUES ('420923', null, '0', '云梦县', '\0', '3', '1', '420900');
INSERT INTO `dns_area` VALUES ('420981', null, '0', '应城市', '\0', '3', '1', '420900');
INSERT INTO `dns_area` VALUES ('420982', null, '0', '安陆市', '\0', '3', '1', '420900');
INSERT INTO `dns_area` VALUES ('420984', null, '0', '汉川市', '\0', '3', '1', '420900');
INSERT INTO `dns_area` VALUES ('421000', null, '0', '荆州市', '\0', '2', '1', '420000');
INSERT INTO `dns_area` VALUES ('421001', null, '0', '市辖区', '\0', '3', '1', '421000');
INSERT INTO `dns_area` VALUES ('421002', null, '0', '沙市区', '\0', '3', '1', '421000');
INSERT INTO `dns_area` VALUES ('421003', null, '0', '荆州区', '\0', '3', '1', '421000');
INSERT INTO `dns_area` VALUES ('421022', null, '0', '公安县', '\0', '3', '1', '421000');
INSERT INTO `dns_area` VALUES ('421023', null, '0', '监利县', '\0', '3', '1', '421000');
INSERT INTO `dns_area` VALUES ('421024', null, '0', '江陵县', '\0', '3', '1', '421000');
INSERT INTO `dns_area` VALUES ('421081', null, '0', '石首市', '\0', '3', '1', '421000');
INSERT INTO `dns_area` VALUES ('421083', null, '0', '洪湖市', '\0', '3', '1', '421000');
INSERT INTO `dns_area` VALUES ('421087', null, '0', '松滋市', '\0', '3', '1', '421000');
INSERT INTO `dns_area` VALUES ('421100', null, '0', '黄冈市', '\0', '2', '1', '420000');
INSERT INTO `dns_area` VALUES ('421101', null, '0', '市辖区', '\0', '3', '1', '421100');
INSERT INTO `dns_area` VALUES ('421102', null, '0', '黄州区', '\0', '3', '1', '421100');
INSERT INTO `dns_area` VALUES ('421121', null, '0', '团风县', '\0', '3', '1', '421100');
INSERT INTO `dns_area` VALUES ('421122', null, '0', '红安县', '\0', '3', '1', '421100');
INSERT INTO `dns_area` VALUES ('421123', null, '0', '罗田县', '\0', '3', '1', '421100');
INSERT INTO `dns_area` VALUES ('421124', null, '0', '英山县', '\0', '3', '1', '421100');
INSERT INTO `dns_area` VALUES ('421125', null, '0', '浠水县', '\0', '3', '1', '421100');
INSERT INTO `dns_area` VALUES ('421126', null, '0', '蕲春县', '\0', '3', '1', '421100');
INSERT INTO `dns_area` VALUES ('421127', null, '0', '黄梅县', '\0', '3', '1', '421100');
INSERT INTO `dns_area` VALUES ('421181', null, '0', '麻城市', '\0', '3', '1', '421100');
INSERT INTO `dns_area` VALUES ('421182', null, '0', '武穴市', '\0', '3', '1', '421100');
INSERT INTO `dns_area` VALUES ('421200', null, '0', '咸宁市', '\0', '2', '1', '420000');
INSERT INTO `dns_area` VALUES ('421201', null, '0', '市辖区', '\0', '3', '1', '421200');
INSERT INTO `dns_area` VALUES ('421202', null, '0', '咸安区', '\0', '3', '1', '421200');
INSERT INTO `dns_area` VALUES ('421221', null, '0', '嘉鱼县', '\0', '3', '1', '421200');
INSERT INTO `dns_area` VALUES ('421222', null, '0', '通城县', '\0', '3', '1', '421200');
INSERT INTO `dns_area` VALUES ('421223', null, '0', '崇阳县', '\0', '3', '1', '421200');
INSERT INTO `dns_area` VALUES ('421224', null, '0', '通山县', '\0', '3', '1', '421200');
INSERT INTO `dns_area` VALUES ('421281', null, '0', '赤壁市', '\0', '3', '1', '421200');
INSERT INTO `dns_area` VALUES ('421300', null, '0', '随州市', '\0', '2', '1', '420000');
INSERT INTO `dns_area` VALUES ('421301', null, '0', '市辖区', '\0', '3', '1', '421300');
INSERT INTO `dns_area` VALUES ('421302', null, '0', '曾都区', '\0', '3', '1', '421300');
INSERT INTO `dns_area` VALUES ('421381', null, '0', '广水市', '\0', '3', '1', '421300');
INSERT INTO `dns_area` VALUES ('422800', null, '0', '恩施土家族苗族自治州', '\0', '2', '1', '420000');
INSERT INTO `dns_area` VALUES ('422801', null, '0', '恩施市', '\0', '3', '1', '422800');
INSERT INTO `dns_area` VALUES ('422802', null, '0', '利川市', '\0', '3', '1', '422800');
INSERT INTO `dns_area` VALUES ('422822', null, '0', '建始县', '\0', '3', '1', '422800');
INSERT INTO `dns_area` VALUES ('422823', null, '0', '巴东县', '\0', '3', '1', '422800');
INSERT INTO `dns_area` VALUES ('422825', null, '0', '宣恩县', '\0', '3', '1', '422800');
INSERT INTO `dns_area` VALUES ('422826', null, '0', '咸丰县', '\0', '3', '1', '422800');
INSERT INTO `dns_area` VALUES ('422827', null, '0', '来凤县', '\0', '3', '1', '422800');
INSERT INTO `dns_area` VALUES ('422828', null, '0', '鹤峰县', '\0', '3', '1', '422800');
INSERT INTO `dns_area` VALUES ('429000', null, '0', '省直辖行政单位', '\0', '2', '1', '420000');
INSERT INTO `dns_area` VALUES ('429004', null, '0', '仙桃市', '\0', '3', '1', '429000');
INSERT INTO `dns_area` VALUES ('429005', null, '0', '潜江市', '\0', '3', '1', '429000');
INSERT INTO `dns_area` VALUES ('429006', null, '0', '天门市', '\0', '3', '1', '429000');
INSERT INTO `dns_area` VALUES ('429021', null, '0', '神农架林区', '\0', '3', '1', '429000');
INSERT INTO `dns_area` VALUES ('430000', null, '0', '湖南省', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('430100', null, '0', '长沙市', '\0', '2', '1', '430000');
INSERT INTO `dns_area` VALUES ('430101', null, '0', '市辖区', '\0', '3', '1', '430100');
INSERT INTO `dns_area` VALUES ('430102', null, '0', '芙蓉区', '\0', '3', '1', '430100');
INSERT INTO `dns_area` VALUES ('430103', null, '0', '天心区', '\0', '3', '1', '430100');
INSERT INTO `dns_area` VALUES ('430104', null, '0', '岳麓区', '\0', '3', '1', '430100');
INSERT INTO `dns_area` VALUES ('430105', null, '0', '开福区', '\0', '3', '1', '430100');
INSERT INTO `dns_area` VALUES ('430111', null, '0', '雨花区', '\0', '3', '1', '430100');
INSERT INTO `dns_area` VALUES ('430121', null, '0', '长沙县', '\0', '3', '1', '430100');
INSERT INTO `dns_area` VALUES ('430122', null, '0', '望城县', '\0', '3', '1', '430100');
INSERT INTO `dns_area` VALUES ('430124', null, '0', '宁乡县', '\0', '3', '1', '430100');
INSERT INTO `dns_area` VALUES ('430181', null, '0', '浏阳市', '\0', '3', '1', '430100');
INSERT INTO `dns_area` VALUES ('430200', null, '0', '株洲市', '\0', '2', '1', '430000');
INSERT INTO `dns_area` VALUES ('430201', null, '0', '市辖区', '\0', '3', '1', '430200');
INSERT INTO `dns_area` VALUES ('430202', null, '0', '荷塘区', '\0', '3', '1', '430200');
INSERT INTO `dns_area` VALUES ('430203', null, '0', '芦淞区', '\0', '3', '1', '430200');
INSERT INTO `dns_area` VALUES ('430204', null, '0', '石峰区', '\0', '3', '1', '430200');
INSERT INTO `dns_area` VALUES ('430211', null, '0', '天元区', '\0', '3', '1', '430200');
INSERT INTO `dns_area` VALUES ('430221', null, '0', '株洲县', '\0', '3', '1', '430200');
INSERT INTO `dns_area` VALUES ('430223', null, '0', '攸　县', '\0', '3', '1', '430200');
INSERT INTO `dns_area` VALUES ('430224', null, '0', '茶陵县', '\0', '3', '1', '430200');
INSERT INTO `dns_area` VALUES ('430225', null, '0', '炎陵县', '\0', '3', '1', '430200');
INSERT INTO `dns_area` VALUES ('430281', null, '0', '醴陵市', '\0', '3', '1', '430200');
INSERT INTO `dns_area` VALUES ('430300', null, '0', '湘潭市', '\0', '2', '1', '430000');
INSERT INTO `dns_area` VALUES ('430301', null, '0', '市辖区', '\0', '3', '1', '430300');
INSERT INTO `dns_area` VALUES ('430302', null, '0', '雨湖区', '\0', '3', '1', '430300');
INSERT INTO `dns_area` VALUES ('430304', null, '0', '岳塘区', '\0', '3', '1', '430300');
INSERT INTO `dns_area` VALUES ('430321', null, '0', '湘潭县', '\0', '3', '1', '430300');
INSERT INTO `dns_area` VALUES ('430381', null, '0', '湘乡市', '\0', '3', '1', '430300');
INSERT INTO `dns_area` VALUES ('430382', null, '0', '韶山市', '\0', '3', '1', '430300');
INSERT INTO `dns_area` VALUES ('430400', null, '0', '衡阳市', '\0', '2', '1', '430000');
INSERT INTO `dns_area` VALUES ('430401', null, '0', '市辖区', '\0', '3', '1', '430400');
INSERT INTO `dns_area` VALUES ('430405', null, '0', '珠晖区', '\0', '3', '1', '430400');
INSERT INTO `dns_area` VALUES ('430406', null, '0', '雁峰区', '\0', '3', '1', '430400');
INSERT INTO `dns_area` VALUES ('430407', null, '0', '石鼓区', '\0', '3', '1', '430400');
INSERT INTO `dns_area` VALUES ('430408', null, '0', '蒸湘区', '\0', '3', '1', '430400');
INSERT INTO `dns_area` VALUES ('430412', null, '0', '南岳区', '\0', '3', '1', '430400');
INSERT INTO `dns_area` VALUES ('430421', null, '0', '衡阳县', '\0', '3', '1', '430400');
INSERT INTO `dns_area` VALUES ('430422', null, '0', '衡南县', '\0', '3', '1', '430400');
INSERT INTO `dns_area` VALUES ('430423', null, '0', '衡山县', '\0', '3', '1', '430400');
INSERT INTO `dns_area` VALUES ('430424', null, '0', '衡东县', '\0', '3', '1', '430400');
INSERT INTO `dns_area` VALUES ('430426', null, '0', '祁东县', '\0', '3', '1', '430400');
INSERT INTO `dns_area` VALUES ('430481', null, '0', '耒阳市', '\0', '3', '1', '430400');
INSERT INTO `dns_area` VALUES ('430482', null, '0', '常宁市', '\0', '3', '1', '430400');
INSERT INTO `dns_area` VALUES ('430500', null, '0', '邵阳市', '\0', '2', '1', '430000');
INSERT INTO `dns_area` VALUES ('430501', null, '0', '市辖区', '\0', '3', '1', '430500');
INSERT INTO `dns_area` VALUES ('430502', null, '0', '双清区', '\0', '3', '1', '430500');
INSERT INTO `dns_area` VALUES ('430503', null, '0', '大祥区', '\0', '3', '1', '430500');
INSERT INTO `dns_area` VALUES ('430511', null, '0', '北塔区', '\0', '3', '1', '430500');
INSERT INTO `dns_area` VALUES ('430521', null, '0', '邵东县', '\0', '3', '1', '430500');
INSERT INTO `dns_area` VALUES ('430522', null, '0', '新邵县', '\0', '3', '1', '430500');
INSERT INTO `dns_area` VALUES ('430523', null, '0', '邵阳县', '\0', '3', '1', '430500');
INSERT INTO `dns_area` VALUES ('430524', null, '0', '隆回县', '\0', '3', '1', '430500');
INSERT INTO `dns_area` VALUES ('430525', null, '0', '洞口县', '\0', '3', '1', '430500');
INSERT INTO `dns_area` VALUES ('430527', null, '0', '绥宁县', '\0', '3', '1', '430500');
INSERT INTO `dns_area` VALUES ('430528', null, '0', '新宁县', '\0', '3', '1', '430500');
INSERT INTO `dns_area` VALUES ('430529', null, '0', '城步苗族自治县', '\0', '3', '1', '430500');
INSERT INTO `dns_area` VALUES ('430581', null, '0', '武冈市', '\0', '3', '1', '430500');
INSERT INTO `dns_area` VALUES ('430600', null, '0', '岳阳市', '\0', '2', '1', '430000');
INSERT INTO `dns_area` VALUES ('430601', null, '0', '市辖区', '\0', '3', '1', '430600');
INSERT INTO `dns_area` VALUES ('430602', null, '0', '岳阳楼区', '\0', '3', '1', '430600');
INSERT INTO `dns_area` VALUES ('430603', null, '0', '云溪区', '\0', '3', '1', '430600');
INSERT INTO `dns_area` VALUES ('430611', null, '0', '君山区', '\0', '3', '1', '430600');
INSERT INTO `dns_area` VALUES ('430621', null, '0', '岳阳县', '\0', '3', '1', '430600');
INSERT INTO `dns_area` VALUES ('430623', null, '0', '华容县', '\0', '3', '1', '430600');
INSERT INTO `dns_area` VALUES ('430624', null, '0', '湘阴县', '\0', '3', '1', '430600');
INSERT INTO `dns_area` VALUES ('430626', null, '0', '平江县', '\0', '3', '1', '430600');
INSERT INTO `dns_area` VALUES ('430681', null, '0', '汨罗市', '\0', '3', '1', '430600');
INSERT INTO `dns_area` VALUES ('430682', null, '0', '临湘市', '\0', '3', '1', '430600');
INSERT INTO `dns_area` VALUES ('430700', null, '0', '常德市', '\0', '2', '1', '430000');
INSERT INTO `dns_area` VALUES ('430701', null, '0', '市辖区', '\0', '3', '1', '430700');
INSERT INTO `dns_area` VALUES ('430702', null, '0', '武陵区', '\0', '3', '1', '430700');
INSERT INTO `dns_area` VALUES ('430703', null, '0', '鼎城区', '\0', '3', '1', '430700');
INSERT INTO `dns_area` VALUES ('430721', null, '0', '安乡县', '\0', '3', '1', '430700');
INSERT INTO `dns_area` VALUES ('430722', null, '0', '汉寿县', '\0', '3', '1', '430700');
INSERT INTO `dns_area` VALUES ('430723', null, '0', '澧　县', '\0', '3', '1', '430700');
INSERT INTO `dns_area` VALUES ('430724', null, '0', '临澧县', '\0', '3', '1', '430700');
INSERT INTO `dns_area` VALUES ('430725', null, '0', '桃源县', '\0', '3', '1', '430700');
INSERT INTO `dns_area` VALUES ('430726', null, '0', '石门县', '\0', '3', '1', '430700');
INSERT INTO `dns_area` VALUES ('430781', null, '0', '津市市', '\0', '3', '1', '430700');
INSERT INTO `dns_area` VALUES ('430800', null, '0', '张家界市', '\0', '2', '1', '430000');
INSERT INTO `dns_area` VALUES ('430801', null, '0', '市辖区', '\0', '3', '1', '430800');
INSERT INTO `dns_area` VALUES ('430802', null, '0', '永定区', '\0', '3', '1', '430800');
INSERT INTO `dns_area` VALUES ('430811', null, '0', '武陵源区', '\0', '3', '1', '430800');
INSERT INTO `dns_area` VALUES ('430821', null, '0', '慈利县', '\0', '3', '1', '430800');
INSERT INTO `dns_area` VALUES ('430822', null, '0', '桑植县', '\0', '3', '1', '430800');
INSERT INTO `dns_area` VALUES ('430900', null, '0', '益阳市', '\0', '2', '1', '430000');
INSERT INTO `dns_area` VALUES ('430901', null, '0', '市辖区', '\0', '3', '1', '430900');
INSERT INTO `dns_area` VALUES ('430902', null, '0', '资阳区', '\0', '3', '1', '430900');
INSERT INTO `dns_area` VALUES ('430903', null, '0', '赫山区', '\0', '3', '1', '430900');
INSERT INTO `dns_area` VALUES ('430921', null, '0', '南　县', '\0', '3', '1', '430900');
INSERT INTO `dns_area` VALUES ('430922', null, '0', '桃江县', '\0', '3', '1', '430900');
INSERT INTO `dns_area` VALUES ('430923', null, '0', '安化县', '\0', '3', '1', '430900');
INSERT INTO `dns_area` VALUES ('430981', null, '0', '沅江市', '\0', '3', '1', '430900');
INSERT INTO `dns_area` VALUES ('431000', null, '0', '郴州市', '\0', '2', '1', '430000');
INSERT INTO `dns_area` VALUES ('431001', null, '0', '市辖区', '\0', '3', '1', '431000');
INSERT INTO `dns_area` VALUES ('431002', null, '0', '北湖区', '\0', '3', '1', '431000');
INSERT INTO `dns_area` VALUES ('431003', null, '0', '苏仙区', '\0', '3', '1', '431000');
INSERT INTO `dns_area` VALUES ('431021', null, '0', '桂阳县', '\0', '3', '1', '431000');
INSERT INTO `dns_area` VALUES ('431022', null, '0', '宜章县', '\0', '3', '1', '431000');
INSERT INTO `dns_area` VALUES ('431023', null, '0', '永兴县', '\0', '3', '1', '431000');
INSERT INTO `dns_area` VALUES ('431024', null, '0', '嘉禾县', '\0', '3', '1', '431000');
INSERT INTO `dns_area` VALUES ('431025', null, '0', '临武县', '\0', '3', '1', '431000');
INSERT INTO `dns_area` VALUES ('431026', null, '0', '汝城县', '\0', '3', '1', '431000');
INSERT INTO `dns_area` VALUES ('431027', null, '0', '桂东县', '\0', '3', '1', '431000');
INSERT INTO `dns_area` VALUES ('431028', null, '0', '安仁县', '\0', '3', '1', '431000');
INSERT INTO `dns_area` VALUES ('431081', null, '0', '资兴市', '\0', '3', '1', '431000');
INSERT INTO `dns_area` VALUES ('431100', null, '0', '永州市', '\0', '2', '1', '430000');
INSERT INTO `dns_area` VALUES ('431101', null, '0', '市辖区', '\0', '3', '1', '431100');
INSERT INTO `dns_area` VALUES ('431102', null, '0', '芝山区', '\0', '3', '1', '431100');
INSERT INTO `dns_area` VALUES ('431103', null, '0', '冷水滩区', '\0', '3', '1', '431100');
INSERT INTO `dns_area` VALUES ('431121', null, '0', '祁阳县', '\0', '3', '1', '431100');
INSERT INTO `dns_area` VALUES ('431122', null, '0', '东安县', '\0', '3', '1', '431100');
INSERT INTO `dns_area` VALUES ('431123', null, '0', '双牌县', '\0', '3', '1', '431100');
INSERT INTO `dns_area` VALUES ('431124', null, '0', '道　县', '\0', '3', '1', '431100');
INSERT INTO `dns_area` VALUES ('431125', null, '0', '江永县', '\0', '3', '1', '431100');
INSERT INTO `dns_area` VALUES ('431126', null, '0', '宁远县', '\0', '3', '1', '431100');
INSERT INTO `dns_area` VALUES ('431127', null, '0', '蓝山县', '\0', '3', '1', '431100');
INSERT INTO `dns_area` VALUES ('431128', null, '0', '新田县', '\0', '3', '1', '431100');
INSERT INTO `dns_area` VALUES ('431129', null, '0', '江华瑶族自治县', '\0', '3', '1', '431100');
INSERT INTO `dns_area` VALUES ('431200', null, '0', '怀化市', '\0', '2', '1', '430000');
INSERT INTO `dns_area` VALUES ('431201', null, '0', '市辖区', '\0', '3', '1', '431200');
INSERT INTO `dns_area` VALUES ('431202', null, '0', '鹤城区', '\0', '3', '1', '431200');
INSERT INTO `dns_area` VALUES ('431221', null, '0', '中方县', '\0', '3', '1', '431200');
INSERT INTO `dns_area` VALUES ('431222', null, '0', '沅陵县', '\0', '3', '1', '431200');
INSERT INTO `dns_area` VALUES ('431223', null, '0', '辰溪县', '\0', '3', '1', '431200');
INSERT INTO `dns_area` VALUES ('431224', null, '0', '溆浦县', '\0', '3', '1', '431200');
INSERT INTO `dns_area` VALUES ('431225', null, '0', '会同县', '\0', '3', '1', '431200');
INSERT INTO `dns_area` VALUES ('431226', null, '0', '麻阳苗族自治县', '\0', '3', '1', '431200');
INSERT INTO `dns_area` VALUES ('431227', null, '0', '新晃侗族自治县', '\0', '3', '1', '431200');
INSERT INTO `dns_area` VALUES ('431228', null, '0', '芷江侗族自治县', '\0', '3', '1', '431200');
INSERT INTO `dns_area` VALUES ('431229', null, '0', '靖州苗族侗族自治县', '\0', '3', '1', '431200');
INSERT INTO `dns_area` VALUES ('431230', null, '0', '通道侗族自治县', '\0', '3', '1', '431200');
INSERT INTO `dns_area` VALUES ('431281', null, '0', '洪江市', '\0', '3', '1', '431200');
INSERT INTO `dns_area` VALUES ('431300', null, '0', '娄底市', '\0', '2', '1', '430000');
INSERT INTO `dns_area` VALUES ('431301', null, '0', '市辖区', '\0', '3', '1', '431300');
INSERT INTO `dns_area` VALUES ('431302', null, '0', '娄星区', '\0', '3', '1', '431300');
INSERT INTO `dns_area` VALUES ('431321', null, '0', '双峰县', '\0', '3', '1', '431300');
INSERT INTO `dns_area` VALUES ('431322', null, '0', '新化县', '\0', '3', '1', '431300');
INSERT INTO `dns_area` VALUES ('431381', null, '0', '冷水江市', '\0', '3', '1', '431300');
INSERT INTO `dns_area` VALUES ('431382', null, '0', '涟源市', '\0', '3', '1', '431300');
INSERT INTO `dns_area` VALUES ('433100', null, '0', '湘西土家族苗族自治州', '\0', '2', '1', '430000');
INSERT INTO `dns_area` VALUES ('433101', null, '0', '吉首市', '\0', '3', '1', '433100');
INSERT INTO `dns_area` VALUES ('433122', null, '0', '泸溪县', '\0', '3', '1', '433100');
INSERT INTO `dns_area` VALUES ('433123', null, '0', '凤凰县', '\0', '3', '1', '433100');
INSERT INTO `dns_area` VALUES ('433124', null, '0', '花垣县', '\0', '3', '1', '433100');
INSERT INTO `dns_area` VALUES ('433125', null, '0', '保靖县', '\0', '3', '1', '433100');
INSERT INTO `dns_area` VALUES ('433126', null, '0', '古丈县', '\0', '3', '1', '433100');
INSERT INTO `dns_area` VALUES ('433127', null, '0', '永顺县', '\0', '3', '1', '433100');
INSERT INTO `dns_area` VALUES ('433130', null, '0', '龙山县', '\0', '3', '1', '433100');
INSERT INTO `dns_area` VALUES ('440000', null, '0', '广东省', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('440100', null, '0', '广州市', '\0', '2', '1', '440000');
INSERT INTO `dns_area` VALUES ('440101', null, '0', '市辖区', '\0', '3', '1', '440100');
INSERT INTO `dns_area` VALUES ('440102', null, '0', '东山区', '\0', '3', '1', '440100');
INSERT INTO `dns_area` VALUES ('440103', null, '0', '荔湾区', '\0', '3', '1', '440100');
INSERT INTO `dns_area` VALUES ('440104', null, '0', '越秀区', '\0', '3', '1', '440100');
INSERT INTO `dns_area` VALUES ('440105', null, '0', '海珠区', '\0', '3', '1', '440100');
INSERT INTO `dns_area` VALUES ('440106', null, '0', '天河区', '\0', '3', '1', '440100');
INSERT INTO `dns_area` VALUES ('440107', null, '0', '芳村区', '\0', '3', '1', '440100');
INSERT INTO `dns_area` VALUES ('440111', null, '0', '白云区', '\0', '3', '1', '440100');
INSERT INTO `dns_area` VALUES ('440112', null, '0', '黄埔区', '\0', '3', '1', '440100');
INSERT INTO `dns_area` VALUES ('440113', null, '0', '番禺区', '\0', '3', '1', '440100');
INSERT INTO `dns_area` VALUES ('440114', null, '0', '花都区', '\0', '3', '1', '440100');
INSERT INTO `dns_area` VALUES ('440183', null, '0', '增城市', '\0', '3', '1', '440100');
INSERT INTO `dns_area` VALUES ('440184', null, '0', '从化市', '\0', '3', '1', '440100');
INSERT INTO `dns_area` VALUES ('440200', null, '0', '韶关市', '\0', '2', '1', '440000');
INSERT INTO `dns_area` VALUES ('440201', null, '0', '市辖区', '\0', '3', '1', '440200');
INSERT INTO `dns_area` VALUES ('440203', null, '0', '武江区', '\0', '3', '1', '440200');
INSERT INTO `dns_area` VALUES ('440204', null, '0', '浈江区', '\0', '3', '1', '440200');
INSERT INTO `dns_area` VALUES ('440205', null, '0', '曲江区', '\0', '3', '1', '440200');
INSERT INTO `dns_area` VALUES ('440222', null, '0', '始兴县', '\0', '3', '1', '440200');
INSERT INTO `dns_area` VALUES ('440224', null, '0', '仁化县', '\0', '3', '1', '440200');
INSERT INTO `dns_area` VALUES ('440229', null, '0', '翁源县', '\0', '3', '1', '440200');
INSERT INTO `dns_area` VALUES ('440232', null, '0', '乳源瑶族自治县', '\0', '3', '1', '440200');
INSERT INTO `dns_area` VALUES ('440233', null, '0', '新丰县', '\0', '3', '1', '440200');
INSERT INTO `dns_area` VALUES ('440281', null, '0', '乐昌市', '\0', '3', '1', '440200');
INSERT INTO `dns_area` VALUES ('440282', null, '0', '南雄市', '\0', '3', '1', '440200');
INSERT INTO `dns_area` VALUES ('440300', null, '0', '深圳市', '\0', '2', '1', '440000');
INSERT INTO `dns_area` VALUES ('440301', null, '0', '市辖区', '\0', '3', '1', '440300');
INSERT INTO `dns_area` VALUES ('440303', null, '0', '罗湖区', '\0', '3', '1', '440300');
INSERT INTO `dns_area` VALUES ('440304', null, '0', '福田区', '\0', '3', '1', '440300');
INSERT INTO `dns_area` VALUES ('440305', null, '0', '南山区', '\0', '3', '1', '440300');
INSERT INTO `dns_area` VALUES ('440306', null, '0', '宝安区', '\0', '3', '1', '440300');
INSERT INTO `dns_area` VALUES ('440307', null, '0', '龙岗区', '\0', '3', '1', '440300');
INSERT INTO `dns_area` VALUES ('440308', null, '0', '盐田区', '\0', '3', '1', '440300');
INSERT INTO `dns_area` VALUES ('440400', null, '0', '珠海市', '\0', '2', '1', '440000');
INSERT INTO `dns_area` VALUES ('440401', null, '0', '市辖区', '\0', '3', '1', '440400');
INSERT INTO `dns_area` VALUES ('440402', null, '0', '香洲区', '\0', '3', '1', '440400');
INSERT INTO `dns_area` VALUES ('440403', null, '0', '斗门区', '\0', '3', '1', '440400');
INSERT INTO `dns_area` VALUES ('440404', null, '0', '金湾区', '\0', '3', '1', '440400');
INSERT INTO `dns_area` VALUES ('440500', null, '0', '汕头市', '\0', '2', '1', '440000');
INSERT INTO `dns_area` VALUES ('440501', null, '0', '市辖区', '\0', '3', '1', '440500');
INSERT INTO `dns_area` VALUES ('440507', null, '0', '龙湖区', '\0', '3', '1', '440500');
INSERT INTO `dns_area` VALUES ('440511', null, '0', '金平区', '\0', '3', '1', '440500');
INSERT INTO `dns_area` VALUES ('440512', null, '0', '濠江区', '\0', '3', '1', '440500');
INSERT INTO `dns_area` VALUES ('440513', null, '0', '潮阳区', '\0', '3', '1', '440500');
INSERT INTO `dns_area` VALUES ('440514', null, '0', '潮南区', '\0', '3', '1', '440500');
INSERT INTO `dns_area` VALUES ('440515', null, '0', '澄海区', '\0', '3', '1', '440500');
INSERT INTO `dns_area` VALUES ('440523', null, '0', '南澳县', '\0', '3', '1', '440500');
INSERT INTO `dns_area` VALUES ('440600', null, '0', '佛山市', '\0', '2', '1', '440000');
INSERT INTO `dns_area` VALUES ('440601', null, '0', '市辖区', '\0', '3', '1', '440600');
INSERT INTO `dns_area` VALUES ('440604', null, '0', '禅城区', '\0', '3', '1', '440600');
INSERT INTO `dns_area` VALUES ('440605', null, '0', '南海区', '\0', '3', '1', '440600');
INSERT INTO `dns_area` VALUES ('440606', null, '0', '顺德区', '\0', '3', '1', '440600');
INSERT INTO `dns_area` VALUES ('440607', null, '0', '三水区', '\0', '3', '1', '440600');
INSERT INTO `dns_area` VALUES ('440608', null, '0', '高明区', '\0', '3', '1', '440600');
INSERT INTO `dns_area` VALUES ('440700', null, '0', '江门市', '\0', '2', '1', '440000');
INSERT INTO `dns_area` VALUES ('440701', null, '0', '市辖区', '\0', '3', '1', '440700');
INSERT INTO `dns_area` VALUES ('440703', null, '0', '蓬江区', '\0', '3', '1', '440700');
INSERT INTO `dns_area` VALUES ('440704', null, '0', '江海区', '\0', '3', '1', '440700');
INSERT INTO `dns_area` VALUES ('440705', null, '0', '新会区', '\0', '3', '1', '440700');
INSERT INTO `dns_area` VALUES ('440781', null, '0', '台山市', '\0', '3', '1', '440700');
INSERT INTO `dns_area` VALUES ('440783', null, '0', '开平市', '\0', '3', '1', '440700');
INSERT INTO `dns_area` VALUES ('440784', null, '0', '鹤山市', '\0', '3', '1', '440700');
INSERT INTO `dns_area` VALUES ('440785', null, '0', '恩平市', '\0', '3', '1', '440700');
INSERT INTO `dns_area` VALUES ('440800', null, '0', '湛江市', '\0', '2', '1', '440000');
INSERT INTO `dns_area` VALUES ('440801', null, '0', '市辖区', '\0', '3', '1', '440800');
INSERT INTO `dns_area` VALUES ('440802', null, '0', '赤坎区', '\0', '3', '1', '440800');
INSERT INTO `dns_area` VALUES ('440803', null, '0', '霞山区', '\0', '3', '1', '440800');
INSERT INTO `dns_area` VALUES ('440804', null, '0', '坡头区', '\0', '3', '1', '440800');
INSERT INTO `dns_area` VALUES ('440811', null, '0', '麻章区', '\0', '3', '1', '440800');
INSERT INTO `dns_area` VALUES ('440823', null, '0', '遂溪县', '\0', '3', '1', '440800');
INSERT INTO `dns_area` VALUES ('440825', null, '0', '徐闻县', '\0', '3', '1', '440800');
INSERT INTO `dns_area` VALUES ('440881', null, '0', '廉江市', '\0', '3', '1', '440800');
INSERT INTO `dns_area` VALUES ('440882', null, '0', '雷州市', '\0', '3', '1', '440800');
INSERT INTO `dns_area` VALUES ('440883', null, '0', '吴川市', '\0', '3', '1', '440800');
INSERT INTO `dns_area` VALUES ('440900', null, '0', '茂名市', '\0', '2', '1', '440000');
INSERT INTO `dns_area` VALUES ('440901', null, '0', '市辖区', '\0', '3', '1', '440900');
INSERT INTO `dns_area` VALUES ('440902', null, '0', '茂南区', '\0', '3', '1', '440900');
INSERT INTO `dns_area` VALUES ('440903', null, '0', '茂港区', '\0', '3', '1', '440900');
INSERT INTO `dns_area` VALUES ('440923', null, '0', '电白县', '\0', '3', '1', '440900');
INSERT INTO `dns_area` VALUES ('440981', null, '0', '高州市', '\0', '3', '1', '440900');
INSERT INTO `dns_area` VALUES ('440982', null, '0', '化州市', '\0', '3', '1', '440900');
INSERT INTO `dns_area` VALUES ('440983', null, '0', '信宜市', '\0', '3', '1', '440900');
INSERT INTO `dns_area` VALUES ('441200', null, '0', '肇庆市', '\0', '2', '1', '440000');
INSERT INTO `dns_area` VALUES ('441201', null, '0', '市辖区', '\0', '3', '1', '441200');
INSERT INTO `dns_area` VALUES ('441202', null, '0', '端州区', '\0', '3', '1', '441200');
INSERT INTO `dns_area` VALUES ('441203', null, '0', '鼎湖区', '\0', '3', '1', '441200');
INSERT INTO `dns_area` VALUES ('441223', null, '0', '广宁县', '\0', '3', '1', '441200');
INSERT INTO `dns_area` VALUES ('441224', null, '0', '怀集县', '\0', '3', '1', '441200');
INSERT INTO `dns_area` VALUES ('441225', null, '0', '封开县', '\0', '3', '1', '441200');
INSERT INTO `dns_area` VALUES ('441226', null, '0', '德庆县', '\0', '3', '1', '441200');
INSERT INTO `dns_area` VALUES ('441283', null, '0', '高要市', '\0', '3', '1', '441200');
INSERT INTO `dns_area` VALUES ('441284', null, '0', '四会市', '\0', '3', '1', '441200');
INSERT INTO `dns_area` VALUES ('441300', null, '0', '惠州市', '\0', '2', '1', '440000');
INSERT INTO `dns_area` VALUES ('441301', null, '0', '市辖区', '\0', '3', '1', '441300');
INSERT INTO `dns_area` VALUES ('441302', null, '0', '惠城区', '\0', '3', '1', '441300');
INSERT INTO `dns_area` VALUES ('441303', null, '0', '惠阳区', '\0', '3', '1', '441300');
INSERT INTO `dns_area` VALUES ('441322', null, '0', '博罗县', '\0', '3', '1', '441300');
INSERT INTO `dns_area` VALUES ('441323', null, '0', '惠东县', '\0', '3', '1', '441300');
INSERT INTO `dns_area` VALUES ('441324', null, '0', '龙门县', '\0', '3', '1', '441300');
INSERT INTO `dns_area` VALUES ('441400', null, '0', '梅州市', '\0', '2', '1', '440000');
INSERT INTO `dns_area` VALUES ('441401', null, '0', '市辖区', '\0', '3', '1', '441400');
INSERT INTO `dns_area` VALUES ('441402', null, '0', '梅江区', '\0', '3', '1', '441400');
INSERT INTO `dns_area` VALUES ('441421', null, '0', '梅　县', '\0', '3', '1', '441400');
INSERT INTO `dns_area` VALUES ('441422', null, '0', '大埔县', '\0', '3', '1', '441400');
INSERT INTO `dns_area` VALUES ('441423', null, '0', '丰顺县', '\0', '3', '1', '441400');
INSERT INTO `dns_area` VALUES ('441424', null, '0', '五华县', '\0', '3', '1', '441400');
INSERT INTO `dns_area` VALUES ('441426', null, '0', '平远县', '\0', '3', '1', '441400');
INSERT INTO `dns_area` VALUES ('441427', null, '0', '蕉岭县', '\0', '3', '1', '441400');
INSERT INTO `dns_area` VALUES ('441481', null, '0', '兴宁市', '\0', '3', '1', '441400');
INSERT INTO `dns_area` VALUES ('441500', null, '0', '汕尾市', '\0', '2', '1', '440000');
INSERT INTO `dns_area` VALUES ('441501', null, '0', '市辖区', '\0', '3', '1', '441500');
INSERT INTO `dns_area` VALUES ('441502', null, '0', '城　区', '\0', '3', '1', '441500');
INSERT INTO `dns_area` VALUES ('441521', null, '0', '海丰县', '\0', '3', '1', '441500');
INSERT INTO `dns_area` VALUES ('441523', null, '0', '陆河县', '\0', '3', '1', '441500');
INSERT INTO `dns_area` VALUES ('441581', null, '0', '陆丰市', '\0', '3', '1', '441500');
INSERT INTO `dns_area` VALUES ('441600', null, '0', '河源市', '\0', '2', '1', '440000');
INSERT INTO `dns_area` VALUES ('441601', null, '0', '市辖区', '\0', '3', '1', '441600');
INSERT INTO `dns_area` VALUES ('441602', null, '0', '源城区', '\0', '3', '1', '441600');
INSERT INTO `dns_area` VALUES ('441621', null, '0', '紫金县', '\0', '3', '1', '441600');
INSERT INTO `dns_area` VALUES ('441622', null, '0', '龙川县', '\0', '3', '1', '441600');
INSERT INTO `dns_area` VALUES ('441623', null, '0', '连平县', '\0', '3', '1', '441600');
INSERT INTO `dns_area` VALUES ('441624', null, '0', '和平县', '\0', '3', '1', '441600');
INSERT INTO `dns_area` VALUES ('441625', null, '0', '东源县', '\0', '3', '1', '441600');
INSERT INTO `dns_area` VALUES ('441700', null, '0', '阳江市', '\0', '2', '1', '440000');
INSERT INTO `dns_area` VALUES ('441701', null, '0', '市辖区', '\0', '3', '1', '441700');
INSERT INTO `dns_area` VALUES ('441702', null, '0', '江城区', '\0', '3', '1', '441700');
INSERT INTO `dns_area` VALUES ('441721', null, '0', '阳西县', '\0', '3', '1', '441700');
INSERT INTO `dns_area` VALUES ('441723', null, '0', '阳东县', '\0', '3', '1', '441700');
INSERT INTO `dns_area` VALUES ('441781', null, '0', '阳春市', '\0', '3', '1', '441700');
INSERT INTO `dns_area` VALUES ('441800', null, '0', '清远市', '\0', '2', '1', '440000');
INSERT INTO `dns_area` VALUES ('441801', null, '0', '市辖区', '\0', '3', '1', '441800');
INSERT INTO `dns_area` VALUES ('441802', null, '0', '清城区', '\0', '3', '1', '441800');
INSERT INTO `dns_area` VALUES ('441821', null, '0', '佛冈县', '\0', '3', '1', '441800');
INSERT INTO `dns_area` VALUES ('441823', null, '0', '阳山县', '\0', '3', '1', '441800');
INSERT INTO `dns_area` VALUES ('441825', null, '0', '连山壮族瑶族自治县', '\0', '3', '1', '441800');
INSERT INTO `dns_area` VALUES ('441826', null, '0', '连南瑶族自治县', '\0', '3', '1', '441800');
INSERT INTO `dns_area` VALUES ('441827', null, '0', '清新县', '\0', '3', '1', '441800');
INSERT INTO `dns_area` VALUES ('441881', null, '0', '英德市', '\0', '3', '1', '441800');
INSERT INTO `dns_area` VALUES ('441882', null, '0', '连州市', '\0', '3', '1', '441800');
INSERT INTO `dns_area` VALUES ('441900', null, '0', '东莞市', '\0', '2', '1', '440000');
INSERT INTO `dns_area` VALUES ('442000', null, '0', '中山市', '\0', '2', '1', '440000');
INSERT INTO `dns_area` VALUES ('445100', null, '0', '潮州市', '\0', '2', '1', '440000');
INSERT INTO `dns_area` VALUES ('445101', null, '0', '市辖区', '\0', '3', '1', '445100');
INSERT INTO `dns_area` VALUES ('445102', null, '0', '湘桥区', '\0', '3', '1', '445100');
INSERT INTO `dns_area` VALUES ('445121', null, '0', '潮安县', '\0', '3', '1', '445100');
INSERT INTO `dns_area` VALUES ('445122', null, '0', '饶平县', '\0', '3', '1', '445100');
INSERT INTO `dns_area` VALUES ('445200', null, '0', '揭阳市', '\0', '2', '1', '440000');
INSERT INTO `dns_area` VALUES ('445201', null, '0', '市辖区', '\0', '3', '1', '445200');
INSERT INTO `dns_area` VALUES ('445202', null, '0', '榕城区', '\0', '3', '1', '445200');
INSERT INTO `dns_area` VALUES ('445221', null, '0', '揭东县', '\0', '3', '1', '445200');
INSERT INTO `dns_area` VALUES ('445222', null, '0', '揭西县', '\0', '3', '1', '445200');
INSERT INTO `dns_area` VALUES ('445224', null, '0', '惠来县', '\0', '3', '1', '445200');
INSERT INTO `dns_area` VALUES ('445281', null, '0', '普宁市', '\0', '3', '1', '445200');
INSERT INTO `dns_area` VALUES ('445300', null, '0', '云浮市', '\0', '2', '1', '440000');
INSERT INTO `dns_area` VALUES ('445301', null, '0', '市辖区', '\0', '3', '1', '445300');
INSERT INTO `dns_area` VALUES ('445302', null, '0', '云城区', '\0', '3', '1', '445300');
INSERT INTO `dns_area` VALUES ('445321', null, '0', '新兴县', '\0', '3', '1', '445300');
INSERT INTO `dns_area` VALUES ('445322', null, '0', '郁南县', '\0', '3', '1', '445300');
INSERT INTO `dns_area` VALUES ('445323', null, '0', '云安县', '\0', '3', '1', '445300');
INSERT INTO `dns_area` VALUES ('445381', null, '0', '罗定市', '\0', '3', '1', '445300');
INSERT INTO `dns_area` VALUES ('450000', null, '0', '广西壮族自治区', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('450100', null, '0', '南宁市', '\0', '2', '1', '450000');
INSERT INTO `dns_area` VALUES ('450101', null, '0', '市辖区', '\0', '3', '1', '450100');
INSERT INTO `dns_area` VALUES ('450102', null, '0', '兴宁区', '\0', '3', '1', '450100');
INSERT INTO `dns_area` VALUES ('450103', null, '0', '青秀区', '\0', '3', '1', '450100');
INSERT INTO `dns_area` VALUES ('450105', null, '0', '江南区', '\0', '3', '1', '450100');
INSERT INTO `dns_area` VALUES ('450107', null, '0', '西乡塘区', '\0', '3', '1', '450100');
INSERT INTO `dns_area` VALUES ('450108', null, '0', '良庆区', '\0', '3', '1', '450100');
INSERT INTO `dns_area` VALUES ('450109', null, '0', '邕宁区', '\0', '3', '1', '450100');
INSERT INTO `dns_area` VALUES ('450122', null, '0', '武鸣县', '\0', '3', '1', '450100');
INSERT INTO `dns_area` VALUES ('450123', null, '0', '隆安县', '\0', '3', '1', '450100');
INSERT INTO `dns_area` VALUES ('450124', null, '0', '马山县', '\0', '3', '1', '450100');
INSERT INTO `dns_area` VALUES ('450125', null, '0', '上林县', '\0', '3', '1', '450100');
INSERT INTO `dns_area` VALUES ('450126', null, '0', '宾阳县', '\0', '3', '1', '450100');
INSERT INTO `dns_area` VALUES ('450127', null, '0', '横　县', '\0', '3', '1', '450100');
INSERT INTO `dns_area` VALUES ('450200', null, '0', '柳州市', '\0', '2', '1', '450000');
INSERT INTO `dns_area` VALUES ('450201', null, '0', '市辖区', '\0', '3', '1', '450200');
INSERT INTO `dns_area` VALUES ('450202', null, '0', '城中区', '\0', '3', '1', '450200');
INSERT INTO `dns_area` VALUES ('450203', null, '0', '鱼峰区', '\0', '3', '1', '450200');
INSERT INTO `dns_area` VALUES ('450204', null, '0', '柳南区', '\0', '3', '1', '450200');
INSERT INTO `dns_area` VALUES ('450205', null, '0', '柳北区', '\0', '3', '1', '450200');
INSERT INTO `dns_area` VALUES ('450221', null, '0', '柳江县', '\0', '3', '1', '450200');
INSERT INTO `dns_area` VALUES ('450222', null, '0', '柳城县', '\0', '3', '1', '450200');
INSERT INTO `dns_area` VALUES ('450223', null, '0', '鹿寨县', '\0', '3', '1', '450200');
INSERT INTO `dns_area` VALUES ('450224', null, '0', '融安县', '\0', '3', '1', '450200');
INSERT INTO `dns_area` VALUES ('450225', null, '0', '融水苗族自治县', '\0', '3', '1', '450200');
INSERT INTO `dns_area` VALUES ('450226', null, '0', '三江侗族自治县', '\0', '3', '1', '450200');
INSERT INTO `dns_area` VALUES ('450300', null, '0', '桂林市', '\0', '2', '1', '450000');
INSERT INTO `dns_area` VALUES ('450301', null, '0', '市辖区', '\0', '3', '1', '450300');
INSERT INTO `dns_area` VALUES ('450302', null, '0', '秀峰区', '\0', '3', '1', '450300');
INSERT INTO `dns_area` VALUES ('450303', null, '0', '叠彩区', '\0', '3', '1', '450300');
INSERT INTO `dns_area` VALUES ('450304', null, '0', '象山区', '\0', '3', '1', '450300');
INSERT INTO `dns_area` VALUES ('450305', null, '0', '七星区', '\0', '3', '1', '450300');
INSERT INTO `dns_area` VALUES ('450311', null, '0', '雁山区', '\0', '3', '1', '450300');
INSERT INTO `dns_area` VALUES ('450321', null, '0', '阳朔县', '\0', '3', '1', '450300');
INSERT INTO `dns_area` VALUES ('450322', null, '0', '临桂县', '\0', '3', '1', '450300');
INSERT INTO `dns_area` VALUES ('450323', null, '0', '灵川县', '\0', '3', '1', '450300');
INSERT INTO `dns_area` VALUES ('450324', null, '0', '全州县', '\0', '3', '1', '450300');
INSERT INTO `dns_area` VALUES ('450325', null, '0', '兴安县', '\0', '3', '1', '450300');
INSERT INTO `dns_area` VALUES ('450326', null, '0', '永福县', '\0', '3', '1', '450300');
INSERT INTO `dns_area` VALUES ('450327', null, '0', '灌阳县', '\0', '3', '1', '450300');
INSERT INTO `dns_area` VALUES ('450328', null, '0', '龙胜各族自治县', '\0', '3', '1', '450300');
INSERT INTO `dns_area` VALUES ('450329', null, '0', '资源县', '\0', '3', '1', '450300');
INSERT INTO `dns_area` VALUES ('450330', null, '0', '平乐县', '\0', '3', '1', '450300');
INSERT INTO `dns_area` VALUES ('450331', null, '0', '荔蒲县', '\0', '3', '1', '450300');
INSERT INTO `dns_area` VALUES ('450332', null, '0', '恭城瑶族自治县', '\0', '3', '1', '450300');
INSERT INTO `dns_area` VALUES ('450400', null, '0', '梧州市', '\0', '2', '1', '450000');
INSERT INTO `dns_area` VALUES ('450401', null, '0', '市辖区', '\0', '3', '1', '450400');
INSERT INTO `dns_area` VALUES ('450403', null, '0', '万秀区', '\0', '3', '1', '450400');
INSERT INTO `dns_area` VALUES ('450404', null, '0', '蝶山区', '\0', '3', '1', '450400');
INSERT INTO `dns_area` VALUES ('450405', null, '0', '长洲区', '\0', '3', '1', '450400');
INSERT INTO `dns_area` VALUES ('450421', null, '0', '苍梧县', '\0', '3', '1', '450400');
INSERT INTO `dns_area` VALUES ('450422', null, '0', '藤　县', '\0', '3', '1', '450400');
INSERT INTO `dns_area` VALUES ('450423', null, '0', '蒙山县', '\0', '3', '1', '450400');
INSERT INTO `dns_area` VALUES ('450481', null, '0', '岑溪市', '\0', '3', '1', '450400');
INSERT INTO `dns_area` VALUES ('450500', null, '0', '北海市', '\0', '2', '1', '450000');
INSERT INTO `dns_area` VALUES ('450501', null, '0', '市辖区', '\0', '3', '1', '450500');
INSERT INTO `dns_area` VALUES ('450502', null, '0', '海城区', '\0', '3', '1', '450500');
INSERT INTO `dns_area` VALUES ('450503', null, '0', '银海区', '\0', '3', '1', '450500');
INSERT INTO `dns_area` VALUES ('450512', null, '0', '铁山港区', '\0', '3', '1', '450500');
INSERT INTO `dns_area` VALUES ('450521', null, '0', '合浦县', '\0', '3', '1', '450500');
INSERT INTO `dns_area` VALUES ('450600', null, '0', '防城港市', '\0', '2', '1', '450000');
INSERT INTO `dns_area` VALUES ('450601', null, '0', '市辖区', '\0', '3', '1', '450600');
INSERT INTO `dns_area` VALUES ('450602', null, '0', '港口区', '\0', '3', '1', '450600');
INSERT INTO `dns_area` VALUES ('450603', null, '0', '防城区', '\0', '3', '1', '450600');
INSERT INTO `dns_area` VALUES ('450621', null, '0', '上思县', '\0', '3', '1', '450600');
INSERT INTO `dns_area` VALUES ('450681', null, '0', '东兴市', '\0', '3', '1', '450600');
INSERT INTO `dns_area` VALUES ('450700', null, '0', '钦州市', '\0', '2', '1', '450000');
INSERT INTO `dns_area` VALUES ('450701', null, '0', '市辖区', '\0', '3', '1', '450700');
INSERT INTO `dns_area` VALUES ('450702', null, '0', '钦南区', '\0', '3', '1', '450700');
INSERT INTO `dns_area` VALUES ('450703', null, '0', '钦北区', '\0', '3', '1', '450700');
INSERT INTO `dns_area` VALUES ('450721', null, '0', '灵山县', '\0', '3', '1', '450700');
INSERT INTO `dns_area` VALUES ('450722', null, '0', '浦北县', '\0', '3', '1', '450700');
INSERT INTO `dns_area` VALUES ('450800', null, '0', '贵港市', '\0', '2', '1', '450000');
INSERT INTO `dns_area` VALUES ('450801', null, '0', '市辖区', '\0', '3', '1', '450800');
INSERT INTO `dns_area` VALUES ('450802', null, '0', '港北区', '\0', '3', '1', '450800');
INSERT INTO `dns_area` VALUES ('450803', null, '0', '港南区', '\0', '3', '1', '450800');
INSERT INTO `dns_area` VALUES ('450804', null, '0', '覃塘区', '\0', '3', '1', '450800');
INSERT INTO `dns_area` VALUES ('450821', null, '0', '平南县', '\0', '3', '1', '450800');
INSERT INTO `dns_area` VALUES ('450881', null, '0', '桂平市', '\0', '3', '1', '450800');
INSERT INTO `dns_area` VALUES ('450900', null, '0', '玉林市', '\0', '2', '1', '450000');
INSERT INTO `dns_area` VALUES ('450901', null, '0', '市辖区', '\0', '3', '1', '450900');
INSERT INTO `dns_area` VALUES ('450902', null, '0', '玉州区', '\0', '3', '1', '450900');
INSERT INTO `dns_area` VALUES ('450921', null, '0', '容　县', '\0', '3', '1', '450900');
INSERT INTO `dns_area` VALUES ('450922', null, '0', '陆川县', '\0', '3', '1', '450900');
INSERT INTO `dns_area` VALUES ('450923', null, '0', '博白县', '\0', '3', '1', '450900');
INSERT INTO `dns_area` VALUES ('450924', null, '0', '兴业县', '\0', '3', '1', '450900');
INSERT INTO `dns_area` VALUES ('450981', null, '0', '北流市', '\0', '3', '1', '450900');
INSERT INTO `dns_area` VALUES ('451000', null, '0', '百色市', '\0', '2', '1', '450000');
INSERT INTO `dns_area` VALUES ('451001', null, '0', '市辖区', '\0', '3', '1', '451000');
INSERT INTO `dns_area` VALUES ('451002', null, '0', '右江区', '\0', '3', '1', '451000');
INSERT INTO `dns_area` VALUES ('451021', null, '0', '田阳县', '\0', '3', '1', '451000');
INSERT INTO `dns_area` VALUES ('451022', null, '0', '田东县', '\0', '3', '1', '451000');
INSERT INTO `dns_area` VALUES ('451023', null, '0', '平果县', '\0', '3', '1', '451000');
INSERT INTO `dns_area` VALUES ('451024', null, '0', '德保县', '\0', '3', '1', '451000');
INSERT INTO `dns_area` VALUES ('451025', null, '0', '靖西县', '\0', '3', '1', '451000');
INSERT INTO `dns_area` VALUES ('451026', null, '0', '那坡县', '\0', '3', '1', '451000');
INSERT INTO `dns_area` VALUES ('451027', null, '0', '凌云县', '\0', '3', '1', '451000');
INSERT INTO `dns_area` VALUES ('451028', null, '0', '乐业县', '\0', '3', '1', '451000');
INSERT INTO `dns_area` VALUES ('451029', null, '0', '田林县', '\0', '3', '1', '451000');
INSERT INTO `dns_area` VALUES ('451030', null, '0', '西林县', '\0', '3', '1', '451000');
INSERT INTO `dns_area` VALUES ('451031', null, '0', '隆林各族自治县', '\0', '3', '1', '451000');
INSERT INTO `dns_area` VALUES ('451100', null, '0', '贺州市', '\0', '2', '1', '450000');
INSERT INTO `dns_area` VALUES ('451101', null, '0', '市辖区', '\0', '3', '1', '451100');
INSERT INTO `dns_area` VALUES ('451102', null, '0', '八步区', '\0', '3', '1', '451100');
INSERT INTO `dns_area` VALUES ('451121', null, '0', '昭平县', '\0', '3', '1', '451100');
INSERT INTO `dns_area` VALUES ('451122', null, '0', '钟山县', '\0', '3', '1', '451100');
INSERT INTO `dns_area` VALUES ('451123', null, '0', '富川瑶族自治县', '\0', '3', '1', '451100');
INSERT INTO `dns_area` VALUES ('451200', null, '0', '河池市', '\0', '2', '1', '450000');
INSERT INTO `dns_area` VALUES ('451201', null, '0', '市辖区', '\0', '3', '1', '451200');
INSERT INTO `dns_area` VALUES ('451202', null, '0', '金城江区', '\0', '3', '1', '451200');
INSERT INTO `dns_area` VALUES ('451221', null, '0', '南丹县', '\0', '3', '1', '451200');
INSERT INTO `dns_area` VALUES ('451222', null, '0', '天峨县', '\0', '3', '1', '451200');
INSERT INTO `dns_area` VALUES ('451223', null, '0', '凤山县', '\0', '3', '1', '451200');
INSERT INTO `dns_area` VALUES ('451224', null, '0', '东兰县', '\0', '3', '1', '451200');
INSERT INTO `dns_area` VALUES ('451225', null, '0', '罗城仫佬族自治县', '\0', '3', '1', '451200');
INSERT INTO `dns_area` VALUES ('451226', null, '0', '环江毛南族自治县', '\0', '3', '1', '451200');
INSERT INTO `dns_area` VALUES ('451227', null, '0', '巴马瑶族自治县', '\0', '3', '1', '451200');
INSERT INTO `dns_area` VALUES ('451228', null, '0', '都安瑶族自治县', '\0', '3', '1', '451200');
INSERT INTO `dns_area` VALUES ('451229', null, '0', '大化瑶族自治县', '\0', '3', '1', '451200');
INSERT INTO `dns_area` VALUES ('451281', null, '0', '宜州市', '\0', '3', '1', '451200');
INSERT INTO `dns_area` VALUES ('451300', null, '0', '来宾市', '\0', '2', '1', '450000');
INSERT INTO `dns_area` VALUES ('451301', null, '0', '市辖区', '\0', '3', '1', '451300');
INSERT INTO `dns_area` VALUES ('451302', null, '0', '兴宾区', '\0', '3', '1', '451300');
INSERT INTO `dns_area` VALUES ('451321', null, '0', '忻城县', '\0', '3', '1', '451300');
INSERT INTO `dns_area` VALUES ('451322', null, '0', '象州县', '\0', '3', '1', '451300');
INSERT INTO `dns_area` VALUES ('451323', null, '0', '武宣县', '\0', '3', '1', '451300');
INSERT INTO `dns_area` VALUES ('451324', null, '0', '金秀瑶族自治县', '\0', '3', '1', '451300');
INSERT INTO `dns_area` VALUES ('451381', null, '0', '合山市', '\0', '3', '1', '451300');
INSERT INTO `dns_area` VALUES ('451400', null, '0', '崇左市', '\0', '2', '1', '450000');
INSERT INTO `dns_area` VALUES ('451401', null, '0', '市辖区', '\0', '3', '1', '451400');
INSERT INTO `dns_area` VALUES ('451402', null, '0', '江洲区', '\0', '3', '1', '451400');
INSERT INTO `dns_area` VALUES ('451421', null, '0', '扶绥县', '\0', '3', '1', '451400');
INSERT INTO `dns_area` VALUES ('451422', null, '0', '宁明县', '\0', '3', '1', '451400');
INSERT INTO `dns_area` VALUES ('451423', null, '0', '龙州县', '\0', '3', '1', '451400');
INSERT INTO `dns_area` VALUES ('451424', null, '0', '大新县', '\0', '3', '1', '451400');
INSERT INTO `dns_area` VALUES ('451425', null, '0', '天等县', '\0', '3', '1', '451400');
INSERT INTO `dns_area` VALUES ('451481', null, '0', '凭祥市', '\0', '3', '1', '451400');
INSERT INTO `dns_area` VALUES ('460000', null, '0', '海南省', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('460100', null, '0', '海口市', '\0', '2', '1', '460000');
INSERT INTO `dns_area` VALUES ('460101', null, '0', '市辖区', '\0', '3', '1', '460100');
INSERT INTO `dns_area` VALUES ('460105', null, '0', '秀英区', '\0', '3', '1', '460100');
INSERT INTO `dns_area` VALUES ('460106', null, '0', '龙华区', '\0', '3', '1', '460100');
INSERT INTO `dns_area` VALUES ('460107', null, '0', '琼山区', '\0', '3', '1', '460100');
INSERT INTO `dns_area` VALUES ('460108', null, '0', '美兰区', '\0', '3', '1', '460100');
INSERT INTO `dns_area` VALUES ('460200', null, '0', '三亚市', '\0', '2', '1', '460000');
INSERT INTO `dns_area` VALUES ('460201', null, '0', '市辖区', '\0', '3', '1', '460200');
INSERT INTO `dns_area` VALUES ('469000', null, '0', '省直辖县级行政单位', '\0', '2', '1', '460000');
INSERT INTO `dns_area` VALUES ('469001', null, '0', '五指山市', '\0', '3', '1', '469000');
INSERT INTO `dns_area` VALUES ('469002', null, '0', '琼海市', '\0', '3', '1', '469000');
INSERT INTO `dns_area` VALUES ('469003', null, '0', '儋州市', '\0', '3', '1', '469000');
INSERT INTO `dns_area` VALUES ('469005', null, '0', '文昌市', '\0', '3', '1', '469000');
INSERT INTO `dns_area` VALUES ('469006', null, '0', '万宁市', '\0', '3', '1', '469000');
INSERT INTO `dns_area` VALUES ('469007', null, '0', '东方市', '\0', '3', '1', '469000');
INSERT INTO `dns_area` VALUES ('469025', null, '0', '定安县', '\0', '3', '1', '469000');
INSERT INTO `dns_area` VALUES ('469026', null, '0', '屯昌县', '\0', '3', '1', '469000');
INSERT INTO `dns_area` VALUES ('469027', null, '0', '澄迈县', '\0', '3', '1', '469000');
INSERT INTO `dns_area` VALUES ('469028', null, '0', '临高县', '\0', '3', '1', '469000');
INSERT INTO `dns_area` VALUES ('469030', null, '0', '白沙黎族自治县', '\0', '3', '1', '469000');
INSERT INTO `dns_area` VALUES ('469031', null, '0', '昌江黎族自治县', '\0', '3', '1', '469000');
INSERT INTO `dns_area` VALUES ('469033', null, '0', '乐东黎族自治县', '\0', '3', '1', '469000');
INSERT INTO `dns_area` VALUES ('469034', null, '0', '陵水黎族自治县', '\0', '3', '1', '469000');
INSERT INTO `dns_area` VALUES ('469035', null, '0', '保亭黎族苗族自治县', '\0', '3', '1', '469000');
INSERT INTO `dns_area` VALUES ('469036', null, '0', '琼中黎族苗族自治县', '\0', '3', '1', '469000');
INSERT INTO `dns_area` VALUES ('469037', null, '0', '西沙群岛', '\0', '3', '1', '469000');
INSERT INTO `dns_area` VALUES ('469038', null, '0', '南沙群岛', '\0', '3', '1', '469000');
INSERT INTO `dns_area` VALUES ('469039', null, '0', '中沙群岛的岛礁及其海域', '\0', '3', '1', '469000');
INSERT INTO `dns_area` VALUES ('500000', null, '0', '重庆市', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('500100', null, '0', '重庆(市辖区)', '\0', '2', '1', '500000');
INSERT INTO `dns_area` VALUES ('500101', null, '0', '万州区', '\0', '3', '1', '500100');
INSERT INTO `dns_area` VALUES ('500102', null, '0', '涪陵区', '\0', '3', '1', '500100');
INSERT INTO `dns_area` VALUES ('500103', null, '0', '渝中区', '\0', '3', '1', '500100');
INSERT INTO `dns_area` VALUES ('500104', null, '0', '大渡口区', '\0', '3', '1', '500100');
INSERT INTO `dns_area` VALUES ('500105', null, '0', '江北区', '\0', '3', '1', '500100');
INSERT INTO `dns_area` VALUES ('500106', null, '0', '沙坪坝区', '\0', '3', '1', '500100');
INSERT INTO `dns_area` VALUES ('500107', null, '0', '九龙坡区', '\0', '3', '1', '500100');
INSERT INTO `dns_area` VALUES ('500108', null, '0', '南岸区', '\0', '3', '1', '500100');
INSERT INTO `dns_area` VALUES ('500109', null, '0', '北碚区', '\0', '3', '1', '500100');
INSERT INTO `dns_area` VALUES ('500110', null, '0', '万盛区', '\0', '3', '1', '500100');
INSERT INTO `dns_area` VALUES ('500111', null, '0', '双桥区', '\0', '3', '1', '500100');
INSERT INTO `dns_area` VALUES ('500112', null, '0', '渝北区', '\0', '3', '1', '500100');
INSERT INTO `dns_area` VALUES ('500113', null, '0', '巴南区', '\0', '3', '1', '500100');
INSERT INTO `dns_area` VALUES ('500114', null, '0', '黔江区', '\0', '3', '1', '500100');
INSERT INTO `dns_area` VALUES ('500115', null, '0', '长寿区', '\0', '3', '1', '500100');
INSERT INTO `dns_area` VALUES ('500200', null, '0', '重庆(县)', '\0', '2', '1', '500000');
INSERT INTO `dns_area` VALUES ('500222', null, '0', '綦江县', '\0', '3', '1', '500200');
INSERT INTO `dns_area` VALUES ('500223', null, '0', '潼南县', '\0', '3', '1', '500200');
INSERT INTO `dns_area` VALUES ('500224', null, '0', '铜梁县', '\0', '3', '1', '500200');
INSERT INTO `dns_area` VALUES ('500225', null, '0', '大足县', '\0', '3', '1', '500200');
INSERT INTO `dns_area` VALUES ('500226', null, '0', '荣昌县', '\0', '3', '1', '500200');
INSERT INTO `dns_area` VALUES ('500227', null, '0', '璧山县', '\0', '3', '1', '500200');
INSERT INTO `dns_area` VALUES ('500228', null, '0', '梁平县', '\0', '3', '1', '500200');
INSERT INTO `dns_area` VALUES ('500229', null, '0', '城口县', '\0', '3', '1', '500200');
INSERT INTO `dns_area` VALUES ('500230', null, '0', '丰都县', '\0', '3', '1', '500200');
INSERT INTO `dns_area` VALUES ('500231', null, '0', '垫江县', '\0', '3', '1', '500200');
INSERT INTO `dns_area` VALUES ('500232', null, '0', '武隆县', '\0', '3', '1', '500200');
INSERT INTO `dns_area` VALUES ('500233', null, '0', '忠　县', '\0', '3', '1', '500200');
INSERT INTO `dns_area` VALUES ('500234', null, '0', '开　县', '\0', '3', '1', '500200');
INSERT INTO `dns_area` VALUES ('500235', null, '0', '云阳县', '\0', '3', '1', '500200');
INSERT INTO `dns_area` VALUES ('500236', null, '0', '奉节县', '\0', '3', '1', '500200');
INSERT INTO `dns_area` VALUES ('500237', null, '0', '巫山县', '\0', '3', '1', '500200');
INSERT INTO `dns_area` VALUES ('500238', null, '0', '巫溪县', '\0', '3', '1', '500200');
INSERT INTO `dns_area` VALUES ('500240', null, '0', '石柱土家族自治县', '\0', '3', '1', '500200');
INSERT INTO `dns_area` VALUES ('500241', null, '0', '秀山土家族苗族自治县', '\0', '3', '1', '500200');
INSERT INTO `dns_area` VALUES ('500242', null, '0', '酉阳土家族苗族自治县', '\0', '3', '1', '500200');
INSERT INTO `dns_area` VALUES ('500243', null, '0', '彭水苗族土家族自治县', '\0', '3', '1', '500200');
INSERT INTO `dns_area` VALUES ('500300', null, '0', '重庆(市)', '\0', '2', '1', '500000');
INSERT INTO `dns_area` VALUES ('500381', null, '0', '江津市', '\0', '3', '1', '500300');
INSERT INTO `dns_area` VALUES ('500382', null, '0', '合川市', '\0', '3', '1', '500300');
INSERT INTO `dns_area` VALUES ('500383', null, '0', '永川市', '\0', '3', '1', '500300');
INSERT INTO `dns_area` VALUES ('500384', null, '0', '南川市', '\0', '3', '1', '500300');
INSERT INTO `dns_area` VALUES ('510000', null, '0', '四川省', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('510100', null, '0', '成都市', '\0', '2', '1', '510000');
INSERT INTO `dns_area` VALUES ('510101', null, '0', '市辖区', '\0', '3', '1', '510100');
INSERT INTO `dns_area` VALUES ('510104', null, '0', '锦江区', '\0', '3', '1', '510100');
INSERT INTO `dns_area` VALUES ('510105', null, '0', '青羊区', '\0', '3', '1', '510100');
INSERT INTO `dns_area` VALUES ('510106', null, '0', '金牛区', '\0', '3', '1', '510100');
INSERT INTO `dns_area` VALUES ('510107', null, '0', '武侯区', '\0', '3', '1', '510100');
INSERT INTO `dns_area` VALUES ('510108', null, '0', '成华区', '\0', '3', '1', '510100');
INSERT INTO `dns_area` VALUES ('510112', null, '0', '龙泉驿区', '\0', '3', '1', '510100');
INSERT INTO `dns_area` VALUES ('510113', null, '0', '青白江区', '\0', '3', '1', '510100');
INSERT INTO `dns_area` VALUES ('510114', null, '0', '新都区', '\0', '3', '1', '510100');
INSERT INTO `dns_area` VALUES ('510115', null, '0', '温江县', '\0', '3', '1', '510100');
INSERT INTO `dns_area` VALUES ('510121', null, '0', '金堂县', '\0', '3', '1', '510100');
INSERT INTO `dns_area` VALUES ('510122', null, '0', '双流县', '\0', '3', '1', '510100');
INSERT INTO `dns_area` VALUES ('510124', null, '0', '郫　县', '\0', '3', '1', '510100');
INSERT INTO `dns_area` VALUES ('510129', null, '0', '大邑县', '\0', '3', '1', '510100');
INSERT INTO `dns_area` VALUES ('510131', null, '0', '蒲江县', '\0', '3', '1', '510100');
INSERT INTO `dns_area` VALUES ('510132', null, '0', '新津县', '\0', '3', '1', '510100');
INSERT INTO `dns_area` VALUES ('510181', null, '0', '都江堰市', '\0', '3', '1', '510100');
INSERT INTO `dns_area` VALUES ('510182', null, '0', '彭州市', '\0', '3', '1', '510100');
INSERT INTO `dns_area` VALUES ('510183', null, '0', '邛崃市', '\0', '3', '1', '510100');
INSERT INTO `dns_area` VALUES ('510184', null, '0', '崇州市', '\0', '3', '1', '510100');
INSERT INTO `dns_area` VALUES ('510300', null, '0', '自贡市', '\0', '2', '1', '510000');
INSERT INTO `dns_area` VALUES ('510301', null, '0', '市辖区', '\0', '3', '1', '510300');
INSERT INTO `dns_area` VALUES ('510302', null, '0', '自流井区', '\0', '3', '1', '510300');
INSERT INTO `dns_area` VALUES ('510303', null, '0', '贡井区', '\0', '3', '1', '510300');
INSERT INTO `dns_area` VALUES ('510304', null, '0', '大安区', '\0', '3', '1', '510300');
INSERT INTO `dns_area` VALUES ('510311', null, '0', '沿滩区', '\0', '3', '1', '510300');
INSERT INTO `dns_area` VALUES ('510321', null, '0', '荣　县', '\0', '3', '1', '510300');
INSERT INTO `dns_area` VALUES ('510322', null, '0', '富顺县', '\0', '3', '1', '510300');
INSERT INTO `dns_area` VALUES ('510400', null, '0', '攀枝花市', '\0', '2', '1', '510000');
INSERT INTO `dns_area` VALUES ('510401', null, '0', '市辖区', '\0', '3', '1', '510400');
INSERT INTO `dns_area` VALUES ('510402', null, '0', '东　区', '\0', '3', '1', '510400');
INSERT INTO `dns_area` VALUES ('510403', null, '0', '西　区', '\0', '3', '1', '510400');
INSERT INTO `dns_area` VALUES ('510411', null, '0', '仁和区', '\0', '3', '1', '510400');
INSERT INTO `dns_area` VALUES ('510421', null, '0', '米易县', '\0', '3', '1', '510400');
INSERT INTO `dns_area` VALUES ('510422', null, '0', '盐边县', '\0', '3', '1', '510400');
INSERT INTO `dns_area` VALUES ('510500', null, '0', '泸州市', '\0', '2', '1', '510000');
INSERT INTO `dns_area` VALUES ('510501', null, '0', '市辖区', '\0', '3', '1', '510500');
INSERT INTO `dns_area` VALUES ('510502', null, '0', '江阳区', '\0', '3', '1', '510500');
INSERT INTO `dns_area` VALUES ('510503', null, '0', '纳溪区', '\0', '3', '1', '510500');
INSERT INTO `dns_area` VALUES ('510504', null, '0', '龙马潭区', '\0', '3', '1', '510500');
INSERT INTO `dns_area` VALUES ('510521', null, '0', '泸　县', '\0', '3', '1', '510500');
INSERT INTO `dns_area` VALUES ('510522', null, '0', '合江县', '\0', '3', '1', '510500');
INSERT INTO `dns_area` VALUES ('510524', null, '0', '叙永县', '\0', '3', '1', '510500');
INSERT INTO `dns_area` VALUES ('510525', null, '0', '古蔺县', '\0', '3', '1', '510500');
INSERT INTO `dns_area` VALUES ('510600', null, '0', '德阳市', '\0', '2', '1', '510000');
INSERT INTO `dns_area` VALUES ('510601', null, '0', '市辖区', '\0', '3', '1', '510600');
INSERT INTO `dns_area` VALUES ('510603', null, '0', '旌阳区', '\0', '3', '1', '510600');
INSERT INTO `dns_area` VALUES ('510623', null, '0', '中江县', '\0', '3', '1', '510600');
INSERT INTO `dns_area` VALUES ('510626', null, '0', '罗江县', '\0', '3', '1', '510600');
INSERT INTO `dns_area` VALUES ('510681', null, '0', '广汉市', '\0', '3', '1', '510600');
INSERT INTO `dns_area` VALUES ('510682', null, '0', '什邡市', '\0', '3', '1', '510600');
INSERT INTO `dns_area` VALUES ('510683', null, '0', '绵竹市', '\0', '3', '1', '510600');
INSERT INTO `dns_area` VALUES ('510700', null, '0', '绵阳市', '\0', '2', '1', '510000');
INSERT INTO `dns_area` VALUES ('510701', null, '0', '市辖区', '\0', '3', '1', '510700');
INSERT INTO `dns_area` VALUES ('510703', null, '0', '涪城区', '\0', '3', '1', '510700');
INSERT INTO `dns_area` VALUES ('510704', null, '0', '游仙区', '\0', '3', '1', '510700');
INSERT INTO `dns_area` VALUES ('510722', null, '0', '三台县', '\0', '3', '1', '510700');
INSERT INTO `dns_area` VALUES ('510723', null, '0', '盐亭县', '\0', '3', '1', '510700');
INSERT INTO `dns_area` VALUES ('510724', null, '0', '安　县', '\0', '3', '1', '510700');
INSERT INTO `dns_area` VALUES ('510725', null, '0', '梓潼县', '\0', '3', '1', '510700');
INSERT INTO `dns_area` VALUES ('510726', null, '0', '北川羌族自治县', '\0', '3', '1', '510700');
INSERT INTO `dns_area` VALUES ('510727', null, '0', '平武县', '\0', '3', '1', '510700');
INSERT INTO `dns_area` VALUES ('510781', null, '0', '江油市', '\0', '3', '1', '510700');
INSERT INTO `dns_area` VALUES ('510800', null, '0', '广元市', '\0', '2', '1', '510000');
INSERT INTO `dns_area` VALUES ('510801', null, '0', '市辖区', '\0', '3', '1', '510800');
INSERT INTO `dns_area` VALUES ('510802', null, '0', '市中区', '\0', '3', '1', '510800');
INSERT INTO `dns_area` VALUES ('510811', null, '0', '元坝区', '\0', '3', '1', '510800');
INSERT INTO `dns_area` VALUES ('510812', null, '0', '朝天区', '\0', '3', '1', '510800');
INSERT INTO `dns_area` VALUES ('510821', null, '0', '旺苍县', '\0', '3', '1', '510800');
INSERT INTO `dns_area` VALUES ('510822', null, '0', '青川县', '\0', '3', '1', '510800');
INSERT INTO `dns_area` VALUES ('510823', null, '0', '剑阁县', '\0', '3', '1', '510800');
INSERT INTO `dns_area` VALUES ('510824', null, '0', '苍溪县', '\0', '3', '1', '510800');
INSERT INTO `dns_area` VALUES ('510900', null, '0', '遂宁市', '\0', '2', '1', '510000');
INSERT INTO `dns_area` VALUES ('510901', null, '0', '市辖区', '\0', '3', '1', '510900');
INSERT INTO `dns_area` VALUES ('510903', null, '0', '船山区', '\0', '3', '1', '510900');
INSERT INTO `dns_area` VALUES ('510904', null, '0', '安居区', '\0', '3', '1', '510900');
INSERT INTO `dns_area` VALUES ('510921', null, '0', '蓬溪县', '\0', '3', '1', '510900');
INSERT INTO `dns_area` VALUES ('510922', null, '0', '射洪县', '\0', '3', '1', '510900');
INSERT INTO `dns_area` VALUES ('510923', null, '0', '大英县', '\0', '3', '1', '510900');
INSERT INTO `dns_area` VALUES ('511000', null, '0', '内江市', '\0', '2', '1', '510000');
INSERT INTO `dns_area` VALUES ('511001', null, '0', '市辖区', '\0', '3', '1', '511000');
INSERT INTO `dns_area` VALUES ('511002', null, '0', '市中区', '\0', '3', '1', '511000');
INSERT INTO `dns_area` VALUES ('511011', null, '0', '东兴区', '\0', '3', '1', '511000');
INSERT INTO `dns_area` VALUES ('511024', null, '0', '威远县', '\0', '3', '1', '511000');
INSERT INTO `dns_area` VALUES ('511025', null, '0', '资中县', '\0', '3', '1', '511000');
INSERT INTO `dns_area` VALUES ('511028', null, '0', '隆昌县', '\0', '3', '1', '511000');
INSERT INTO `dns_area` VALUES ('511100', null, '0', '乐山市', '\0', '2', '1', '510000');
INSERT INTO `dns_area` VALUES ('511101', null, '0', '市辖区', '\0', '3', '1', '511100');
INSERT INTO `dns_area` VALUES ('511102', null, '0', '市中区', '\0', '3', '1', '511100');
INSERT INTO `dns_area` VALUES ('511111', null, '0', '沙湾区', '\0', '3', '1', '511100');
INSERT INTO `dns_area` VALUES ('511112', null, '0', '五通桥区', '\0', '3', '1', '511100');
INSERT INTO `dns_area` VALUES ('511113', null, '0', '金口河区', '\0', '3', '1', '511100');
INSERT INTO `dns_area` VALUES ('511123', null, '0', '犍为县', '\0', '3', '1', '511100');
INSERT INTO `dns_area` VALUES ('511124', null, '0', '井研县', '\0', '3', '1', '511100');
INSERT INTO `dns_area` VALUES ('511126', null, '0', '夹江县', '\0', '3', '1', '511100');
INSERT INTO `dns_area` VALUES ('511129', null, '0', '沐川县', '\0', '3', '1', '511100');
INSERT INTO `dns_area` VALUES ('511132', null, '0', '峨边彝族自治县', '\0', '3', '1', '511100');
INSERT INTO `dns_area` VALUES ('511133', null, '0', '马边彝族自治县', '\0', '3', '1', '511100');
INSERT INTO `dns_area` VALUES ('511181', null, '0', '峨眉山市', '\0', '3', '1', '511100');
INSERT INTO `dns_area` VALUES ('511300', null, '0', '南充市', '\0', '2', '1', '510000');
INSERT INTO `dns_area` VALUES ('511301', null, '0', '市辖区', '\0', '3', '1', '511300');
INSERT INTO `dns_area` VALUES ('511302', null, '0', '顺庆区', '\0', '3', '1', '511300');
INSERT INTO `dns_area` VALUES ('511303', null, '0', '高坪区', '\0', '3', '1', '511300');
INSERT INTO `dns_area` VALUES ('511304', null, '0', '嘉陵区', '\0', '3', '1', '511300');
INSERT INTO `dns_area` VALUES ('511321', null, '0', '南部县', '\0', '3', '1', '511300');
INSERT INTO `dns_area` VALUES ('511322', null, '0', '营山县', '\0', '3', '1', '511300');
INSERT INTO `dns_area` VALUES ('511323', null, '0', '蓬安县', '\0', '3', '1', '511300');
INSERT INTO `dns_area` VALUES ('511324', null, '0', '仪陇县', '\0', '3', '1', '511300');
INSERT INTO `dns_area` VALUES ('511325', null, '0', '西充县', '\0', '3', '1', '511300');
INSERT INTO `dns_area` VALUES ('511381', null, '0', '阆中市', '\0', '3', '1', '511300');
INSERT INTO `dns_area` VALUES ('511400', null, '0', '眉山市', '\0', '2', '1', '510000');
INSERT INTO `dns_area` VALUES ('511401', null, '0', '市辖区', '\0', '3', '1', '511400');
INSERT INTO `dns_area` VALUES ('511402', null, '0', '东坡区', '\0', '3', '1', '511400');
INSERT INTO `dns_area` VALUES ('511421', null, '0', '仁寿县', '\0', '3', '1', '511400');
INSERT INTO `dns_area` VALUES ('511422', null, '0', '彭山县', '\0', '3', '1', '511400');
INSERT INTO `dns_area` VALUES ('511423', null, '0', '洪雅县', '\0', '3', '1', '511400');
INSERT INTO `dns_area` VALUES ('511424', null, '0', '丹棱县', '\0', '3', '1', '511400');
INSERT INTO `dns_area` VALUES ('511425', null, '0', '青神县', '\0', '3', '1', '511400');
INSERT INTO `dns_area` VALUES ('511500', null, '0', '宜宾市', '\0', '2', '1', '510000');
INSERT INTO `dns_area` VALUES ('511501', null, '0', '市辖区', '\0', '3', '1', '511500');
INSERT INTO `dns_area` VALUES ('511502', null, '0', '翠屏区', '\0', '3', '1', '511500');
INSERT INTO `dns_area` VALUES ('511521', null, '0', '宜宾县', '\0', '3', '1', '511500');
INSERT INTO `dns_area` VALUES ('511522', null, '0', '南溪县', '\0', '3', '1', '511500');
INSERT INTO `dns_area` VALUES ('511523', null, '0', '江安县', '\0', '3', '1', '511500');
INSERT INTO `dns_area` VALUES ('511524', null, '0', '长宁县', '\0', '3', '1', '511500');
INSERT INTO `dns_area` VALUES ('511525', null, '0', '高　县', '\0', '3', '1', '511500');
INSERT INTO `dns_area` VALUES ('511526', null, '0', '珙　县', '\0', '3', '1', '511500');
INSERT INTO `dns_area` VALUES ('511527', null, '0', '筠连县', '\0', '3', '1', '511500');
INSERT INTO `dns_area` VALUES ('511528', null, '0', '兴文县', '\0', '3', '1', '511500');
INSERT INTO `dns_area` VALUES ('511529', null, '0', '屏山县', '\0', '3', '1', '511500');
INSERT INTO `dns_area` VALUES ('511600', null, '0', '广安市', '\0', '2', '1', '510000');
INSERT INTO `dns_area` VALUES ('511601', null, '0', '市辖区', '\0', '3', '1', '511600');
INSERT INTO `dns_area` VALUES ('511602', null, '0', '广安区', '\0', '3', '1', '511600');
INSERT INTO `dns_area` VALUES ('511621', null, '0', '岳池县', '\0', '3', '1', '511600');
INSERT INTO `dns_area` VALUES ('511622', null, '0', '武胜县', '\0', '3', '1', '511600');
INSERT INTO `dns_area` VALUES ('511623', null, '0', '邻水县', '\0', '3', '1', '511600');
INSERT INTO `dns_area` VALUES ('511681', null, '0', '华莹市', '\0', '3', '1', '511600');
INSERT INTO `dns_area` VALUES ('511700', null, '0', '达州市', '\0', '2', '1', '510000');
INSERT INTO `dns_area` VALUES ('511701', null, '0', '市辖区', '\0', '3', '1', '511700');
INSERT INTO `dns_area` VALUES ('511702', null, '0', '通川区', '\0', '3', '1', '511700');
INSERT INTO `dns_area` VALUES ('511721', null, '0', '达　县', '\0', '3', '1', '511700');
INSERT INTO `dns_area` VALUES ('511722', null, '0', '宣汉县', '\0', '3', '1', '511700');
INSERT INTO `dns_area` VALUES ('511723', null, '0', '开江县', '\0', '3', '1', '511700');
INSERT INTO `dns_area` VALUES ('511724', null, '0', '大竹县', '\0', '3', '1', '511700');
INSERT INTO `dns_area` VALUES ('511725', null, '0', '渠　县', '\0', '3', '1', '511700');
INSERT INTO `dns_area` VALUES ('511781', null, '0', '万源市', '\0', '3', '1', '511700');
INSERT INTO `dns_area` VALUES ('511800', null, '0', '雅安市', '\0', '2', '1', '510000');
INSERT INTO `dns_area` VALUES ('511801', null, '0', '市辖区', '\0', '3', '1', '511800');
INSERT INTO `dns_area` VALUES ('511802', null, '0', '雨城区', '\0', '3', '1', '511800');
INSERT INTO `dns_area` VALUES ('511821', null, '0', '名山县', '\0', '3', '1', '511800');
INSERT INTO `dns_area` VALUES ('511822', null, '0', '荥经县', '\0', '3', '1', '511800');
INSERT INTO `dns_area` VALUES ('511823', null, '0', '汉源县', '\0', '3', '1', '511800');
INSERT INTO `dns_area` VALUES ('511824', null, '0', '石棉县', '\0', '3', '1', '511800');
INSERT INTO `dns_area` VALUES ('511825', null, '0', '天全县', '\0', '3', '1', '511800');
INSERT INTO `dns_area` VALUES ('511826', null, '0', '芦山县', '\0', '3', '1', '511800');
INSERT INTO `dns_area` VALUES ('511827', null, '0', '宝兴县', '\0', '3', '1', '511800');
INSERT INTO `dns_area` VALUES ('511900', null, '0', '巴中市', '\0', '2', '1', '510000');
INSERT INTO `dns_area` VALUES ('511901', null, '0', '市辖区', '\0', '3', '1', '511900');
INSERT INTO `dns_area` VALUES ('511902', null, '0', '巴州区', '\0', '3', '1', '511900');
INSERT INTO `dns_area` VALUES ('511921', null, '0', '通江县', '\0', '3', '1', '511900');
INSERT INTO `dns_area` VALUES ('511922', null, '0', '南江县', '\0', '3', '1', '511900');
INSERT INTO `dns_area` VALUES ('511923', null, '0', '平昌县', '\0', '3', '1', '511900');
INSERT INTO `dns_area` VALUES ('512000', null, '0', '资阳市', '\0', '2', '1', '510000');
INSERT INTO `dns_area` VALUES ('512001', null, '0', '市辖区', '\0', '3', '1', '512000');
INSERT INTO `dns_area` VALUES ('512002', null, '0', '雁江区', '\0', '3', '1', '512000');
INSERT INTO `dns_area` VALUES ('512021', null, '0', '安岳县', '\0', '3', '1', '512000');
INSERT INTO `dns_area` VALUES ('512022', null, '0', '乐至县', '\0', '3', '1', '512000');
INSERT INTO `dns_area` VALUES ('512081', null, '0', '简阳市', '\0', '3', '1', '512000');
INSERT INTO `dns_area` VALUES ('513200', null, '0', '阿坝藏族羌族自治州', '\0', '2', '1', '510000');
INSERT INTO `dns_area` VALUES ('513221', null, '0', '汶川县', '\0', '3', '1', '513200');
INSERT INTO `dns_area` VALUES ('513222', null, '0', '理　县', '\0', '3', '1', '513200');
INSERT INTO `dns_area` VALUES ('513223', null, '0', '茂　县', '\0', '3', '1', '513200');
INSERT INTO `dns_area` VALUES ('513224', null, '0', '松潘县', '\0', '3', '1', '513200');
INSERT INTO `dns_area` VALUES ('513225', null, '0', '九寨沟县', '\0', '3', '1', '513200');
INSERT INTO `dns_area` VALUES ('513226', null, '0', '金川县', '\0', '3', '1', '513200');
INSERT INTO `dns_area` VALUES ('513227', null, '0', '小金县', '\0', '3', '1', '513200');
INSERT INTO `dns_area` VALUES ('513228', null, '0', '黑水县', '\0', '3', '1', '513200');
INSERT INTO `dns_area` VALUES ('513229', null, '0', '马尔康县', '\0', '3', '1', '513200');
INSERT INTO `dns_area` VALUES ('513230', null, '0', '壤塘县', '\0', '3', '1', '513200');
INSERT INTO `dns_area` VALUES ('513231', null, '0', '阿坝县', '\0', '3', '1', '513200');
INSERT INTO `dns_area` VALUES ('513232', null, '0', '若尔盖县', '\0', '3', '1', '513200');
INSERT INTO `dns_area` VALUES ('513233', null, '0', '红原县', '\0', '3', '1', '513200');
INSERT INTO `dns_area` VALUES ('513300', null, '0', '甘孜藏族自治州', '\0', '2', '1', '510000');
INSERT INTO `dns_area` VALUES ('513321', null, '0', '康定县', '\0', '3', '1', '513300');
INSERT INTO `dns_area` VALUES ('513322', null, '0', '泸定县', '\0', '3', '1', '513300');
INSERT INTO `dns_area` VALUES ('513323', null, '0', '丹巴县', '\0', '3', '1', '513300');
INSERT INTO `dns_area` VALUES ('513324', null, '0', '九龙县', '\0', '3', '1', '513300');
INSERT INTO `dns_area` VALUES ('513325', null, '0', '雅江县', '\0', '3', '1', '513300');
INSERT INTO `dns_area` VALUES ('513326', null, '0', '道孚县', '\0', '3', '1', '513300');
INSERT INTO `dns_area` VALUES ('513327', null, '0', '炉霍县', '\0', '3', '1', '513300');
INSERT INTO `dns_area` VALUES ('513328', null, '0', '甘孜县', '\0', '3', '1', '513300');
INSERT INTO `dns_area` VALUES ('513329', null, '0', '新龙县', '\0', '3', '1', '513300');
INSERT INTO `dns_area` VALUES ('513330', null, '0', '德格县', '\0', '3', '1', '513300');
INSERT INTO `dns_area` VALUES ('513331', null, '0', '白玉县', '\0', '3', '1', '513300');
INSERT INTO `dns_area` VALUES ('513332', null, '0', '石渠县', '\0', '3', '1', '513300');
INSERT INTO `dns_area` VALUES ('513333', null, '0', '色达县', '\0', '3', '1', '513300');
INSERT INTO `dns_area` VALUES ('513334', null, '0', '理塘县', '\0', '3', '1', '513300');
INSERT INTO `dns_area` VALUES ('513335', null, '0', '巴塘县', '\0', '3', '1', '513300');
INSERT INTO `dns_area` VALUES ('513336', null, '0', '乡城县', '\0', '3', '1', '513300');
INSERT INTO `dns_area` VALUES ('513337', null, '0', '稻城县', '\0', '3', '1', '513300');
INSERT INTO `dns_area` VALUES ('513338', null, '0', '得荣县', '\0', '3', '1', '513300');
INSERT INTO `dns_area` VALUES ('513400', null, '0', '凉山彝族自治州', '\0', '2', '1', '510000');
INSERT INTO `dns_area` VALUES ('513401', null, '0', '西昌市', '\0', '3', '1', '513400');
INSERT INTO `dns_area` VALUES ('513422', null, '0', '木里藏族自治县', '\0', '3', '1', '513400');
INSERT INTO `dns_area` VALUES ('513423', null, '0', '盐源县', '\0', '3', '1', '513400');
INSERT INTO `dns_area` VALUES ('513424', null, '0', '德昌县', '\0', '3', '1', '513400');
INSERT INTO `dns_area` VALUES ('513425', null, '0', '会理县', '\0', '3', '1', '513400');
INSERT INTO `dns_area` VALUES ('513426', null, '0', '会东县', '\0', '3', '1', '513400');
INSERT INTO `dns_area` VALUES ('513427', null, '0', '宁南县', '\0', '3', '1', '513400');
INSERT INTO `dns_area` VALUES ('513428', null, '0', '普格县', '\0', '3', '1', '513400');
INSERT INTO `dns_area` VALUES ('513429', null, '0', '布拖县', '\0', '3', '1', '513400');
INSERT INTO `dns_area` VALUES ('513430', null, '0', '金阳县', '\0', '3', '1', '513400');
INSERT INTO `dns_area` VALUES ('513431', null, '0', '昭觉县', '\0', '3', '1', '513400');
INSERT INTO `dns_area` VALUES ('513432', null, '0', '喜德县', '\0', '3', '1', '513400');
INSERT INTO `dns_area` VALUES ('513433', null, '0', '冕宁县', '\0', '3', '1', '513400');
INSERT INTO `dns_area` VALUES ('513434', null, '0', '越西县', '\0', '3', '1', '513400');
INSERT INTO `dns_area` VALUES ('513435', null, '0', '甘洛县', '\0', '3', '1', '513400');
INSERT INTO `dns_area` VALUES ('513436', null, '0', '美姑县', '\0', '3', '1', '513400');
INSERT INTO `dns_area` VALUES ('513437', null, '0', '雷波县', '\0', '3', '1', '513400');
INSERT INTO `dns_area` VALUES ('520000', null, '0', '贵州省', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('520100', null, '0', '贵阳市', '\0', '2', '1', '520000');
INSERT INTO `dns_area` VALUES ('520101', null, '0', '市辖区', '\0', '3', '1', '520100');
INSERT INTO `dns_area` VALUES ('520102', null, '0', '南明区', '\0', '3', '1', '520100');
INSERT INTO `dns_area` VALUES ('520103', null, '0', '云岩区', '\0', '3', '1', '520100');
INSERT INTO `dns_area` VALUES ('520111', null, '0', '花溪区', '\0', '3', '1', '520100');
INSERT INTO `dns_area` VALUES ('520112', null, '0', '乌当区', '\0', '3', '1', '520100');
INSERT INTO `dns_area` VALUES ('520113', null, '0', '白云区', '\0', '3', '1', '520100');
INSERT INTO `dns_area` VALUES ('520114', null, '0', '小河区', '\0', '3', '1', '520100');
INSERT INTO `dns_area` VALUES ('520121', null, '0', '开阳县', '\0', '3', '1', '520100');
INSERT INTO `dns_area` VALUES ('520122', null, '0', '息烽县', '\0', '3', '1', '520100');
INSERT INTO `dns_area` VALUES ('520123', null, '0', '修文县', '\0', '3', '1', '520100');
INSERT INTO `dns_area` VALUES ('520181', null, '0', '清镇市', '\0', '3', '1', '520100');
INSERT INTO `dns_area` VALUES ('520200', null, '0', '六盘水市', '\0', '2', '1', '520000');
INSERT INTO `dns_area` VALUES ('520201', null, '0', '钟山区', '\0', '3', '1', '520200');
INSERT INTO `dns_area` VALUES ('520203', null, '0', '六枝特区', '\0', '3', '1', '520200');
INSERT INTO `dns_area` VALUES ('520221', null, '0', '水城县', '\0', '3', '1', '520200');
INSERT INTO `dns_area` VALUES ('520222', null, '0', '盘　县', '\0', '3', '1', '520200');
INSERT INTO `dns_area` VALUES ('520300', null, '0', '遵义市', '\0', '2', '1', '520000');
INSERT INTO `dns_area` VALUES ('520301', null, '0', '市辖区', '\0', '3', '1', '520300');
INSERT INTO `dns_area` VALUES ('520302', null, '0', '红花岗区', '\0', '3', '1', '520300');
INSERT INTO `dns_area` VALUES ('520303', null, '0', '汇川区', '\0', '3', '1', '520300');
INSERT INTO `dns_area` VALUES ('520321', null, '0', '遵义县', '\0', '3', '1', '520300');
INSERT INTO `dns_area` VALUES ('520322', null, '0', '桐梓县', '\0', '3', '1', '520300');
INSERT INTO `dns_area` VALUES ('520323', null, '0', '绥阳县', '\0', '3', '1', '520300');
INSERT INTO `dns_area` VALUES ('520324', null, '0', '正安县', '\0', '3', '1', '520300');
INSERT INTO `dns_area` VALUES ('520325', null, '0', '道真仡佬族苗族自治县', '\0', '3', '1', '520300');
INSERT INTO `dns_area` VALUES ('520326', null, '0', '务川仡佬族苗族自治县', '\0', '3', '1', '520300');
INSERT INTO `dns_area` VALUES ('520327', null, '0', '凤冈县', '\0', '3', '1', '520300');
INSERT INTO `dns_area` VALUES ('520328', null, '0', '湄潭县', '\0', '3', '1', '520300');
INSERT INTO `dns_area` VALUES ('520329', null, '0', '余庆县', '\0', '3', '1', '520300');
INSERT INTO `dns_area` VALUES ('520330', null, '0', '习水县', '\0', '3', '1', '520300');
INSERT INTO `dns_area` VALUES ('520381', null, '0', '赤水市', '\0', '3', '1', '520300');
INSERT INTO `dns_area` VALUES ('520382', null, '0', '仁怀市', '\0', '3', '1', '520300');
INSERT INTO `dns_area` VALUES ('520400', null, '0', '安顺市', '\0', '2', '1', '520000');
INSERT INTO `dns_area` VALUES ('520401', null, '0', '市辖区', '\0', '3', '1', '520400');
INSERT INTO `dns_area` VALUES ('520402', null, '0', '西秀区', '\0', '3', '1', '520400');
INSERT INTO `dns_area` VALUES ('520421', null, '0', '平坝县', '\0', '3', '1', '520400');
INSERT INTO `dns_area` VALUES ('520422', null, '0', '普定县', '\0', '3', '1', '520400');
INSERT INTO `dns_area` VALUES ('520423', null, '0', '镇宁布依族苗族自治县', '\0', '3', '1', '520400');
INSERT INTO `dns_area` VALUES ('520424', null, '0', '关岭布依族苗族自治县', '\0', '3', '1', '520400');
INSERT INTO `dns_area` VALUES ('520425', null, '0', '紫云苗族布依族自治县', '\0', '3', '1', '520400');
INSERT INTO `dns_area` VALUES ('522200', null, '0', '铜仁地区', '\0', '2', '1', '520000');
INSERT INTO `dns_area` VALUES ('522201', null, '0', '铜仁市', '\0', '3', '1', '522200');
INSERT INTO `dns_area` VALUES ('522222', null, '0', '江口县', '\0', '3', '1', '522200');
INSERT INTO `dns_area` VALUES ('522223', null, '0', '玉屏侗族自治县', '\0', '3', '1', '522200');
INSERT INTO `dns_area` VALUES ('522224', null, '0', '石阡县', '\0', '3', '1', '522200');
INSERT INTO `dns_area` VALUES ('522225', null, '0', '思南县', '\0', '3', '1', '522200');
INSERT INTO `dns_area` VALUES ('522226', null, '0', '印江土家族苗族自治县', '\0', '3', '1', '522200');
INSERT INTO `dns_area` VALUES ('522227', null, '0', '德江县', '\0', '3', '1', '522200');
INSERT INTO `dns_area` VALUES ('522228', null, '0', '沿河土家族自治县', '\0', '3', '1', '522200');
INSERT INTO `dns_area` VALUES ('522229', null, '0', '松桃苗族自治县', '\0', '3', '1', '522200');
INSERT INTO `dns_area` VALUES ('522230', null, '0', '万山特区', '\0', '3', '1', '522200');
INSERT INTO `dns_area` VALUES ('522300', null, '0', '黔西南布依族苗族自治州', '\0', '2', '1', '520000');
INSERT INTO `dns_area` VALUES ('522301', null, '0', '兴义市', '\0', '3', '1', '522300');
INSERT INTO `dns_area` VALUES ('522322', null, '0', '兴仁县', '\0', '3', '1', '522300');
INSERT INTO `dns_area` VALUES ('522323', null, '0', '普安县', '\0', '3', '1', '522300');
INSERT INTO `dns_area` VALUES ('522324', null, '0', '晴隆县', '\0', '3', '1', '522300');
INSERT INTO `dns_area` VALUES ('522325', null, '0', '贞丰县', '\0', '3', '1', '522300');
INSERT INTO `dns_area` VALUES ('522326', null, '0', '望谟县', '\0', '3', '1', '522300');
INSERT INTO `dns_area` VALUES ('522327', null, '0', '册亨县', '\0', '3', '1', '522300');
INSERT INTO `dns_area` VALUES ('522328', null, '0', '安龙县', '\0', '3', '1', '522300');
INSERT INTO `dns_area` VALUES ('522400', null, '0', '毕节地区', '\0', '2', '1', '520000');
INSERT INTO `dns_area` VALUES ('522401', null, '0', '毕节市', '\0', '3', '1', '522400');
INSERT INTO `dns_area` VALUES ('522422', null, '0', '大方县', '\0', '3', '1', '522400');
INSERT INTO `dns_area` VALUES ('522423', null, '0', '黔西县', '\0', '3', '1', '522400');
INSERT INTO `dns_area` VALUES ('522424', null, '0', '金沙县', '\0', '3', '1', '522400');
INSERT INTO `dns_area` VALUES ('522425', null, '0', '织金县', '\0', '3', '1', '522400');
INSERT INTO `dns_area` VALUES ('522426', null, '0', '纳雍县', '\0', '3', '1', '522400');
INSERT INTO `dns_area` VALUES ('522427', null, '0', '威宁彝族回族苗族自治县', '\0', '3', '1', '522400');
INSERT INTO `dns_area` VALUES ('522428', null, '0', '赫章县', '\0', '3', '1', '522400');
INSERT INTO `dns_area` VALUES ('522600', null, '0', '黔东南苗族侗族自治州', '\0', '2', '1', '520000');
INSERT INTO `dns_area` VALUES ('522601', null, '0', '凯里市', '\0', '3', '1', '522600');
INSERT INTO `dns_area` VALUES ('522622', null, '0', '黄平县', '\0', '3', '1', '522600');
INSERT INTO `dns_area` VALUES ('522623', null, '0', '施秉县', '\0', '3', '1', '522600');
INSERT INTO `dns_area` VALUES ('522624', null, '0', '三穗县', '\0', '3', '1', '522600');
INSERT INTO `dns_area` VALUES ('522625', null, '0', '镇远县', '\0', '3', '1', '522600');
INSERT INTO `dns_area` VALUES ('522626', null, '0', '岑巩县', '\0', '3', '1', '522600');
INSERT INTO `dns_area` VALUES ('522627', null, '0', '天柱县', '\0', '3', '1', '522600');
INSERT INTO `dns_area` VALUES ('522628', null, '0', '锦屏县', '\0', '3', '1', '522600');
INSERT INTO `dns_area` VALUES ('522629', null, '0', '剑河县', '\0', '3', '1', '522600');
INSERT INTO `dns_area` VALUES ('522630', null, '0', '台江县', '\0', '3', '1', '522600');
INSERT INTO `dns_area` VALUES ('522631', null, '0', '黎平县', '\0', '3', '1', '522600');
INSERT INTO `dns_area` VALUES ('522632', null, '0', '榕江县', '\0', '3', '1', '522600');
INSERT INTO `dns_area` VALUES ('522633', null, '0', '从江县', '\0', '3', '1', '522600');
INSERT INTO `dns_area` VALUES ('522634', null, '0', '雷山县', '\0', '3', '1', '522600');
INSERT INTO `dns_area` VALUES ('522635', null, '0', '麻江县', '\0', '3', '1', '522600');
INSERT INTO `dns_area` VALUES ('522636', null, '0', '丹寨县', '\0', '3', '1', '522600');
INSERT INTO `dns_area` VALUES ('522700', null, '0', '黔南布依族苗族自治州', '\0', '2', '1', '520000');
INSERT INTO `dns_area` VALUES ('522701', null, '0', '都匀市', '\0', '3', '1', '522700');
INSERT INTO `dns_area` VALUES ('522702', null, '0', '福泉市', '\0', '3', '1', '522700');
INSERT INTO `dns_area` VALUES ('522722', null, '0', '荔波县', '\0', '3', '1', '522700');
INSERT INTO `dns_area` VALUES ('522723', null, '0', '贵定县', '\0', '3', '1', '522700');
INSERT INTO `dns_area` VALUES ('522725', null, '0', '瓮安县', '\0', '3', '1', '522700');
INSERT INTO `dns_area` VALUES ('522726', null, '0', '独山县', '\0', '3', '1', '522700');
INSERT INTO `dns_area` VALUES ('522727', null, '0', '平塘县', '\0', '3', '1', '522700');
INSERT INTO `dns_area` VALUES ('522728', null, '0', '罗甸县', '\0', '3', '1', '522700');
INSERT INTO `dns_area` VALUES ('522729', null, '0', '长顺县', '\0', '3', '1', '522700');
INSERT INTO `dns_area` VALUES ('522730', null, '0', '龙里县', '\0', '3', '1', '522700');
INSERT INTO `dns_area` VALUES ('522731', null, '0', '惠水县', '\0', '3', '1', '522700');
INSERT INTO `dns_area` VALUES ('522732', null, '0', '三都水族自治县', '\0', '3', '1', '522700');
INSERT INTO `dns_area` VALUES ('530000', null, '0', '云南省', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('530100', null, '0', '昆明市', '\0', '2', '1', '530000');
INSERT INTO `dns_area` VALUES ('530101', null, '0', '市辖区', '\0', '3', '1', '530100');
INSERT INTO `dns_area` VALUES ('530102', null, '0', '五华区', '\0', '3', '1', '530100');
INSERT INTO `dns_area` VALUES ('530103', null, '0', '盘龙区', '\0', '3', '1', '530100');
INSERT INTO `dns_area` VALUES ('530111', null, '0', '官渡区', '\0', '3', '1', '530100');
INSERT INTO `dns_area` VALUES ('530112', null, '0', '西山区', '\0', '3', '1', '530100');
INSERT INTO `dns_area` VALUES ('530113', null, '0', '东川区', '\0', '3', '1', '530100');
INSERT INTO `dns_area` VALUES ('530121', null, '0', '呈贡县', '\0', '3', '1', '530100');
INSERT INTO `dns_area` VALUES ('530122', null, '0', '晋宁县', '\0', '3', '1', '530100');
INSERT INTO `dns_area` VALUES ('530124', null, '0', '富民县', '\0', '3', '1', '530100');
INSERT INTO `dns_area` VALUES ('530125', null, '0', '宜良县', '\0', '3', '1', '530100');
INSERT INTO `dns_area` VALUES ('530126', null, '0', '石林彝族自治县', '\0', '3', '1', '530100');
INSERT INTO `dns_area` VALUES ('530127', null, '0', '嵩明县', '\0', '3', '1', '530100');
INSERT INTO `dns_area` VALUES ('530128', null, '0', '禄劝彝族苗族自治县', '\0', '3', '1', '530100');
INSERT INTO `dns_area` VALUES ('530129', null, '0', '寻甸回族彝族自治县', '\0', '3', '1', '530100');
INSERT INTO `dns_area` VALUES ('530181', null, '0', '安宁市', '\0', '3', '1', '530100');
INSERT INTO `dns_area` VALUES ('530300', null, '0', '曲靖市', '\0', '2', '1', '530000');
INSERT INTO `dns_area` VALUES ('530301', null, '0', '市辖区', '\0', '3', '1', '530300');
INSERT INTO `dns_area` VALUES ('530302', null, '0', '麒麟区', '\0', '3', '1', '530300');
INSERT INTO `dns_area` VALUES ('530321', null, '0', '马龙县', '\0', '3', '1', '530300');
INSERT INTO `dns_area` VALUES ('530322', null, '0', '陆良县', '\0', '3', '1', '530300');
INSERT INTO `dns_area` VALUES ('530323', null, '0', '师宗县', '\0', '3', '1', '530300');
INSERT INTO `dns_area` VALUES ('530324', null, '0', '罗平县', '\0', '3', '1', '530300');
INSERT INTO `dns_area` VALUES ('530325', null, '0', '富源县', '\0', '3', '1', '530300');
INSERT INTO `dns_area` VALUES ('530326', null, '0', '会泽县', '\0', '3', '1', '530300');
INSERT INTO `dns_area` VALUES ('530328', null, '0', '沾益县', '\0', '3', '1', '530300');
INSERT INTO `dns_area` VALUES ('530381', null, '0', '宣威市', '\0', '3', '1', '530300');
INSERT INTO `dns_area` VALUES ('530400', null, '0', '玉溪市', '\0', '2', '1', '530000');
INSERT INTO `dns_area` VALUES ('530401', null, '0', '市辖区', '\0', '3', '1', '530400');
INSERT INTO `dns_area` VALUES ('530402', null, '0', '红塔区', '\0', '3', '1', '530400');
INSERT INTO `dns_area` VALUES ('530421', null, '0', '江川县', '\0', '3', '1', '530400');
INSERT INTO `dns_area` VALUES ('530422', null, '0', '澄江县', '\0', '3', '1', '530400');
INSERT INTO `dns_area` VALUES ('530423', null, '0', '通海县', '\0', '3', '1', '530400');
INSERT INTO `dns_area` VALUES ('530424', null, '0', '华宁县', '\0', '3', '1', '530400');
INSERT INTO `dns_area` VALUES ('530425', null, '0', '易门县', '\0', '3', '1', '530400');
INSERT INTO `dns_area` VALUES ('530426', null, '0', '峨山彝族自治县', '\0', '3', '1', '530400');
INSERT INTO `dns_area` VALUES ('530427', null, '0', '新平彝族傣族自治县', '\0', '3', '1', '530400');
INSERT INTO `dns_area` VALUES ('530428', null, '0', '元江哈尼族彝族傣族自治县', '\0', '3', '1', '530400');
INSERT INTO `dns_area` VALUES ('530500', null, '0', '保山市', '\0', '2', '1', '530000');
INSERT INTO `dns_area` VALUES ('530501', null, '0', '市辖区', '\0', '3', '1', '530500');
INSERT INTO `dns_area` VALUES ('530502', null, '0', '隆阳区', '\0', '3', '1', '530500');
INSERT INTO `dns_area` VALUES ('530521', null, '0', '施甸县', '\0', '3', '1', '530500');
INSERT INTO `dns_area` VALUES ('530522', null, '0', '腾冲县', '\0', '3', '1', '530500');
INSERT INTO `dns_area` VALUES ('530523', null, '0', '龙陵县', '\0', '3', '1', '530500');
INSERT INTO `dns_area` VALUES ('530524', null, '0', '昌宁县', '\0', '3', '1', '530500');
INSERT INTO `dns_area` VALUES ('530600', null, '0', '昭通市', '\0', '2', '1', '530000');
INSERT INTO `dns_area` VALUES ('530601', null, '0', '市辖区', '\0', '3', '1', '530600');
INSERT INTO `dns_area` VALUES ('530602', null, '0', '昭阳区', '\0', '3', '1', '530600');
INSERT INTO `dns_area` VALUES ('530621', null, '0', '鲁甸县', '\0', '3', '1', '530600');
INSERT INTO `dns_area` VALUES ('530622', null, '0', '巧家县', '\0', '3', '1', '530600');
INSERT INTO `dns_area` VALUES ('530623', null, '0', '盐津县', '\0', '3', '1', '530600');
INSERT INTO `dns_area` VALUES ('530624', null, '0', '大关县', '\0', '3', '1', '530600');
INSERT INTO `dns_area` VALUES ('530625', null, '0', '永善县', '\0', '3', '1', '530600');
INSERT INTO `dns_area` VALUES ('530626', null, '0', '绥江县', '\0', '3', '1', '530600');
INSERT INTO `dns_area` VALUES ('530627', null, '0', '镇雄县', '\0', '3', '1', '530600');
INSERT INTO `dns_area` VALUES ('530628', null, '0', '彝良县', '\0', '3', '1', '530600');
INSERT INTO `dns_area` VALUES ('530629', null, '0', '威信县', '\0', '3', '1', '530600');
INSERT INTO `dns_area` VALUES ('530630', null, '0', '水富县', '\0', '3', '1', '530600');
INSERT INTO `dns_area` VALUES ('530700', null, '0', '丽江市', '\0', '2', '1', '530000');
INSERT INTO `dns_area` VALUES ('530701', null, '0', '市辖区', '\0', '3', '1', '530700');
INSERT INTO `dns_area` VALUES ('530702', null, '0', '古城区', '\0', '3', '1', '530700');
INSERT INTO `dns_area` VALUES ('530721', null, '0', '玉龙纳西族自治县', '\0', '3', '1', '530700');
INSERT INTO `dns_area` VALUES ('530722', null, '0', '永胜县', '\0', '3', '1', '530700');
INSERT INTO `dns_area` VALUES ('530723', null, '0', '华坪县', '\0', '3', '1', '530700');
INSERT INTO `dns_area` VALUES ('530724', null, '0', '宁蒗彝族自治县', '\0', '3', '1', '530700');
INSERT INTO `dns_area` VALUES ('530800', null, '0', '思茅市', '\0', '2', '1', '530000');
INSERT INTO `dns_area` VALUES ('530801', null, '0', '市辖区', '\0', '3', '1', '530800');
INSERT INTO `dns_area` VALUES ('530802', null, '0', '翠云区', '\0', '3', '1', '530800');
INSERT INTO `dns_area` VALUES ('530821', null, '0', '普洱哈尼族彝族自治县', '\0', '3', '1', '530800');
INSERT INTO `dns_area` VALUES ('530822', null, '0', '墨江哈尼族自治县', '\0', '3', '1', '530800');
INSERT INTO `dns_area` VALUES ('530823', null, '0', '景东彝族自治县', '\0', '3', '1', '530800');
INSERT INTO `dns_area` VALUES ('530824', null, '0', '景谷傣族彝族自治县', '\0', '3', '1', '530800');
INSERT INTO `dns_area` VALUES ('530825', null, '0', '镇沅彝族哈尼族拉祜族自治县', '\0', '3', '1', '530800');
INSERT INTO `dns_area` VALUES ('530826', null, '0', '江城哈尼族彝族自治县', '\0', '3', '1', '530800');
INSERT INTO `dns_area` VALUES ('530827', null, '0', '孟连傣族拉祜族佤族自治县', '\0', '3', '1', '530800');
INSERT INTO `dns_area` VALUES ('530828', null, '0', '澜沧拉祜族自治县', '\0', '3', '1', '530800');
INSERT INTO `dns_area` VALUES ('530829', null, '0', '西盟佤族自治县', '\0', '3', '1', '530800');
INSERT INTO `dns_area` VALUES ('530900', null, '0', '临沧市', '\0', '2', '1', '530000');
INSERT INTO `dns_area` VALUES ('530901', null, '0', '市辖区', '\0', '3', '1', '530900');
INSERT INTO `dns_area` VALUES ('530902', null, '0', '临翔区', '\0', '3', '1', '530900');
INSERT INTO `dns_area` VALUES ('530921', null, '0', '凤庆县', '\0', '3', '1', '530900');
INSERT INTO `dns_area` VALUES ('530922', null, '0', '云　县', '\0', '3', '1', '530900');
INSERT INTO `dns_area` VALUES ('530923', null, '0', '永德县', '\0', '3', '1', '530900');
INSERT INTO `dns_area` VALUES ('530924', null, '0', '镇康县', '\0', '3', '1', '530900');
INSERT INTO `dns_area` VALUES ('530925', null, '0', '双江拉祜族佤族布朗族傣族自治县', '\0', '3', '1', '530900');
INSERT INTO `dns_area` VALUES ('530926', null, '0', '耿马傣族佤族自治县', '\0', '3', '1', '530900');
INSERT INTO `dns_area` VALUES ('530927', null, '0', '沧源佤族自治县', '\0', '3', '1', '530900');
INSERT INTO `dns_area` VALUES ('532300', null, '0', '楚雄彝族自治州', '\0', '2', '1', '530000');
INSERT INTO `dns_area` VALUES ('532301', null, '0', '楚雄市', '\0', '3', '1', '532300');
INSERT INTO `dns_area` VALUES ('532322', null, '0', '双柏县', '\0', '3', '1', '532300');
INSERT INTO `dns_area` VALUES ('532323', null, '0', '牟定县', '\0', '3', '1', '532300');
INSERT INTO `dns_area` VALUES ('532324', null, '0', '南华县', '\0', '3', '1', '532300');
INSERT INTO `dns_area` VALUES ('532325', null, '0', '姚安县', '\0', '3', '1', '532300');
INSERT INTO `dns_area` VALUES ('532326', null, '0', '大姚县', '\0', '3', '1', '532300');
INSERT INTO `dns_area` VALUES ('532327', null, '0', '永仁县', '\0', '3', '1', '532300');
INSERT INTO `dns_area` VALUES ('532328', null, '0', '元谋县', '\0', '3', '1', '532300');
INSERT INTO `dns_area` VALUES ('532329', null, '0', '武定县', '\0', '3', '1', '532300');
INSERT INTO `dns_area` VALUES ('532331', null, '0', '禄丰县', '\0', '3', '1', '532300');
INSERT INTO `dns_area` VALUES ('532500', null, '0', '红河哈尼族彝族自治州', '\0', '2', '1', '530000');
INSERT INTO `dns_area` VALUES ('532501', null, '0', '个旧市', '\0', '3', '1', '532500');
INSERT INTO `dns_area` VALUES ('532502', null, '0', '开远市', '\0', '3', '1', '532500');
INSERT INTO `dns_area` VALUES ('532522', null, '0', '蒙自县', '\0', '3', '1', '532500');
INSERT INTO `dns_area` VALUES ('532523', null, '0', '屏边苗族自治县', '\0', '3', '1', '532500');
INSERT INTO `dns_area` VALUES ('532524', null, '0', '建水县', '\0', '3', '1', '532500');
INSERT INTO `dns_area` VALUES ('532525', null, '0', '石屏县', '\0', '3', '1', '532500');
INSERT INTO `dns_area` VALUES ('532526', null, '0', '弥勒县', '\0', '3', '1', '532500');
INSERT INTO `dns_area` VALUES ('532527', null, '0', '泸西县', '\0', '3', '1', '532500');
INSERT INTO `dns_area` VALUES ('532528', null, '0', '元阳县', '\0', '3', '1', '532500');
INSERT INTO `dns_area` VALUES ('532529', null, '0', '红河县', '\0', '3', '1', '532500');
INSERT INTO `dns_area` VALUES ('532530', null, '0', '金平苗族瑶族傣族自治县', '\0', '3', '1', '532500');
INSERT INTO `dns_area` VALUES ('532531', null, '0', '绿春县', '\0', '3', '1', '532500');
INSERT INTO `dns_area` VALUES ('532532', null, '0', '河口瑶族自治县', '\0', '3', '1', '532500');
INSERT INTO `dns_area` VALUES ('532600', null, '0', '文山壮族苗族自治州', '\0', '2', '1', '530000');
INSERT INTO `dns_area` VALUES ('532621', null, '0', '文山县', '\0', '3', '1', '532600');
INSERT INTO `dns_area` VALUES ('532622', null, '0', '砚山县', '\0', '3', '1', '532600');
INSERT INTO `dns_area` VALUES ('532623', null, '0', '西畴县', '\0', '3', '1', '532600');
INSERT INTO `dns_area` VALUES ('532624', null, '0', '麻栗坡县', '\0', '3', '1', '532600');
INSERT INTO `dns_area` VALUES ('532625', null, '0', '马关县', '\0', '3', '1', '532600');
INSERT INTO `dns_area` VALUES ('532626', null, '0', '丘北县', '\0', '3', '1', '532600');
INSERT INTO `dns_area` VALUES ('532627', null, '0', '广南县', '\0', '3', '1', '532600');
INSERT INTO `dns_area` VALUES ('532628', null, '0', '富宁县', '\0', '3', '1', '532600');
INSERT INTO `dns_area` VALUES ('532800', null, '0', '西双版纳傣族自治州', '\0', '2', '1', '530000');
INSERT INTO `dns_area` VALUES ('532801', null, '0', '景洪市', '\0', '3', '1', '532800');
INSERT INTO `dns_area` VALUES ('532822', null, '0', '勐海县', '\0', '3', '1', '532800');
INSERT INTO `dns_area` VALUES ('532823', null, '0', '勐腊县', '\0', '3', '1', '532800');
INSERT INTO `dns_area` VALUES ('532900', null, '0', '大理白族自治州', '\0', '2', '1', '530000');
INSERT INTO `dns_area` VALUES ('532901', null, '0', '大理市', '\0', '3', '1', '532900');
INSERT INTO `dns_area` VALUES ('532922', null, '0', '漾濞彝族自治县', '\0', '3', '1', '532900');
INSERT INTO `dns_area` VALUES ('532923', null, '0', '祥云县', '\0', '3', '1', '532900');
INSERT INTO `dns_area` VALUES ('532924', null, '0', '宾川县', '\0', '3', '1', '532900');
INSERT INTO `dns_area` VALUES ('532925', null, '0', '弥渡县', '\0', '3', '1', '532900');
INSERT INTO `dns_area` VALUES ('532926', null, '0', '南涧彝族自治县', '\0', '3', '1', '532900');
INSERT INTO `dns_area` VALUES ('532927', null, '0', '巍山彝族回族自治县', '\0', '3', '1', '532900');
INSERT INTO `dns_area` VALUES ('532928', null, '0', '永平县', '\0', '3', '1', '532900');
INSERT INTO `dns_area` VALUES ('532929', null, '0', '云龙县', '\0', '3', '1', '532900');
INSERT INTO `dns_area` VALUES ('532930', null, '0', '洱源县', '\0', '3', '1', '532900');
INSERT INTO `dns_area` VALUES ('532931', null, '0', '剑川县', '\0', '3', '1', '532900');
INSERT INTO `dns_area` VALUES ('532932', null, '0', '鹤庆县', '\0', '3', '1', '532900');
INSERT INTO `dns_area` VALUES ('533100', null, '0', '德宏傣族景颇族自治州', '\0', '2', '1', '530000');
INSERT INTO `dns_area` VALUES ('533102', null, '0', '瑞丽市', '\0', '3', '1', '533100');
INSERT INTO `dns_area` VALUES ('533103', null, '0', '潞西市', '\0', '3', '1', '533100');
INSERT INTO `dns_area` VALUES ('533122', null, '0', '梁河县', '\0', '3', '1', '533100');
INSERT INTO `dns_area` VALUES ('533123', null, '0', '盈江县', '\0', '3', '1', '533100');
INSERT INTO `dns_area` VALUES ('533124', null, '0', '陇川县', '\0', '3', '1', '533100');
INSERT INTO `dns_area` VALUES ('533300', null, '0', '怒江傈僳族自治州', '\0', '2', '1', '530000');
INSERT INTO `dns_area` VALUES ('533321', null, '0', '泸水县', '\0', '3', '1', '533300');
INSERT INTO `dns_area` VALUES ('533323', null, '0', '福贡县', '\0', '3', '1', '533300');
INSERT INTO `dns_area` VALUES ('533324', null, '0', '贡山独龙族怒族自治县', '\0', '3', '1', '533300');
INSERT INTO `dns_area` VALUES ('533325', null, '0', '兰坪白族普米族自治县', '\0', '3', '1', '533300');
INSERT INTO `dns_area` VALUES ('533400', null, '0', '迪庆藏族自治州', '\0', '2', '1', '530000');
INSERT INTO `dns_area` VALUES ('533421', null, '0', '香格里拉县', '\0', '3', '1', '533400');
INSERT INTO `dns_area` VALUES ('533422', null, '0', '德钦县', '\0', '3', '1', '533400');
INSERT INTO `dns_area` VALUES ('533423', null, '0', '维西傈僳族自治县', '\0', '3', '1', '533400');
INSERT INTO `dns_area` VALUES ('540000', null, '0', '西藏自治区', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('540100', null, '0', '拉萨市', '\0', '2', '1', '540000');
INSERT INTO `dns_area` VALUES ('540101', null, '0', '市辖区', '\0', '3', '1', '540100');
INSERT INTO `dns_area` VALUES ('540102', null, '0', '城关区', '\0', '3', '1', '540100');
INSERT INTO `dns_area` VALUES ('540121', null, '0', '林周县', '\0', '3', '1', '540100');
INSERT INTO `dns_area` VALUES ('540122', null, '0', '当雄县', '\0', '3', '1', '540100');
INSERT INTO `dns_area` VALUES ('540123', null, '0', '尼木县', '\0', '3', '1', '540100');
INSERT INTO `dns_area` VALUES ('540124', null, '0', '曲水县', '\0', '3', '1', '540100');
INSERT INTO `dns_area` VALUES ('540125', null, '0', '堆龙德庆县', '\0', '3', '1', '540100');
INSERT INTO `dns_area` VALUES ('540126', null, '0', '达孜县', '\0', '3', '1', '540100');
INSERT INTO `dns_area` VALUES ('540127', null, '0', '墨竹工卡县', '\0', '3', '1', '540100');
INSERT INTO `dns_area` VALUES ('542100', null, '0', '昌都地区', '\0', '2', '1', '540000');
INSERT INTO `dns_area` VALUES ('542121', null, '0', '昌都县', '\0', '3', '1', '542100');
INSERT INTO `dns_area` VALUES ('542122', null, '0', '江达县', '\0', '3', '1', '542100');
INSERT INTO `dns_area` VALUES ('542123', null, '0', '贡觉县', '\0', '3', '1', '542100');
INSERT INTO `dns_area` VALUES ('542124', null, '0', '类乌齐县', '\0', '3', '1', '542100');
INSERT INTO `dns_area` VALUES ('542125', null, '0', '丁青县', '\0', '3', '1', '542100');
INSERT INTO `dns_area` VALUES ('542126', null, '0', '察雅县', '\0', '3', '1', '542100');
INSERT INTO `dns_area` VALUES ('542127', null, '0', '八宿县', '\0', '3', '1', '542100');
INSERT INTO `dns_area` VALUES ('542128', null, '0', '左贡县', '\0', '3', '1', '542100');
INSERT INTO `dns_area` VALUES ('542129', null, '0', '芒康县', '\0', '3', '1', '542100');
INSERT INTO `dns_area` VALUES ('542132', null, '0', '洛隆县', '\0', '3', '1', '542100');
INSERT INTO `dns_area` VALUES ('542133', null, '0', '边坝县', '\0', '3', '1', '542100');
INSERT INTO `dns_area` VALUES ('542200', null, '0', '山南地区', '\0', '2', '1', '540000');
INSERT INTO `dns_area` VALUES ('542221', null, '0', '乃东县', '\0', '3', '1', '542200');
INSERT INTO `dns_area` VALUES ('542222', null, '0', '扎囊县', '\0', '3', '1', '542200');
INSERT INTO `dns_area` VALUES ('542223', null, '0', '贡嘎县', '\0', '3', '1', '542200');
INSERT INTO `dns_area` VALUES ('542224', null, '0', '桑日县', '\0', '3', '1', '542200');
INSERT INTO `dns_area` VALUES ('542225', null, '0', '琼结县', '\0', '3', '1', '542200');
INSERT INTO `dns_area` VALUES ('542226', null, '0', '曲松县', '\0', '3', '1', '542200');
INSERT INTO `dns_area` VALUES ('542227', null, '0', '措美县', '\0', '3', '1', '542200');
INSERT INTO `dns_area` VALUES ('542228', null, '0', '洛扎县', '\0', '3', '1', '542200');
INSERT INTO `dns_area` VALUES ('542229', null, '0', '加查县', '\0', '3', '1', '542200');
INSERT INTO `dns_area` VALUES ('542231', null, '0', '隆子县', '\0', '3', '1', '542200');
INSERT INTO `dns_area` VALUES ('542232', null, '0', '错那县', '\0', '3', '1', '542200');
INSERT INTO `dns_area` VALUES ('542233', null, '0', '浪卡子县', '\0', '3', '1', '542200');
INSERT INTO `dns_area` VALUES ('542300', null, '0', '日喀则地区', '\0', '2', '1', '540000');
INSERT INTO `dns_area` VALUES ('542301', null, '0', '日喀则市', '\0', '3', '1', '542300');
INSERT INTO `dns_area` VALUES ('542322', null, '0', '南木林县', '\0', '3', '1', '542300');
INSERT INTO `dns_area` VALUES ('542323', null, '0', '江孜县', '\0', '3', '1', '542300');
INSERT INTO `dns_area` VALUES ('542324', null, '0', '定日县', '\0', '3', '1', '542300');
INSERT INTO `dns_area` VALUES ('542325', null, '0', '萨迦县', '\0', '3', '1', '542300');
INSERT INTO `dns_area` VALUES ('542326', null, '0', '拉孜县', '\0', '3', '1', '542300');
INSERT INTO `dns_area` VALUES ('542327', null, '0', '昂仁县', '\0', '3', '1', '542300');
INSERT INTO `dns_area` VALUES ('542328', null, '0', '谢通门县', '\0', '3', '1', '542300');
INSERT INTO `dns_area` VALUES ('542329', null, '0', '白朗县', '\0', '3', '1', '542300');
INSERT INTO `dns_area` VALUES ('542330', null, '0', '仁布县', '\0', '3', '1', '542300');
INSERT INTO `dns_area` VALUES ('542331', null, '0', '康马县', '\0', '3', '1', '542300');
INSERT INTO `dns_area` VALUES ('542332', null, '0', '定结县', '\0', '3', '1', '542300');
INSERT INTO `dns_area` VALUES ('542333', null, '0', '仲巴县', '\0', '3', '1', '542300');
INSERT INTO `dns_area` VALUES ('542334', null, '0', '亚东县', '\0', '3', '1', '542300');
INSERT INTO `dns_area` VALUES ('542335', null, '0', '吉隆县', '\0', '3', '1', '542300');
INSERT INTO `dns_area` VALUES ('542336', null, '0', '聂拉木县', '\0', '3', '1', '542300');
INSERT INTO `dns_area` VALUES ('542337', null, '0', '萨嘎县', '\0', '3', '1', '542300');
INSERT INTO `dns_area` VALUES ('542338', null, '0', '岗巴县', '\0', '3', '1', '542300');
INSERT INTO `dns_area` VALUES ('542400', null, '0', '那曲地区', '\0', '2', '1', '540000');
INSERT INTO `dns_area` VALUES ('542421', null, '0', '那曲县', '\0', '3', '1', '542400');
INSERT INTO `dns_area` VALUES ('542422', null, '0', '嘉黎县', '\0', '3', '1', '542400');
INSERT INTO `dns_area` VALUES ('542423', null, '0', '比如县', '\0', '3', '1', '542400');
INSERT INTO `dns_area` VALUES ('542424', null, '0', '聂荣县', '\0', '3', '1', '542400');
INSERT INTO `dns_area` VALUES ('542425', null, '0', '安多县', '\0', '3', '1', '542400');
INSERT INTO `dns_area` VALUES ('542426', null, '0', '申扎县', '\0', '3', '1', '542400');
INSERT INTO `dns_area` VALUES ('542427', null, '0', '索　县', '\0', '3', '1', '542400');
INSERT INTO `dns_area` VALUES ('542428', null, '0', '班戈县', '\0', '3', '1', '542400');
INSERT INTO `dns_area` VALUES ('542429', null, '0', '巴青县', '\0', '3', '1', '542400');
INSERT INTO `dns_area` VALUES ('542430', null, '0', '尼玛县', '\0', '3', '1', '542400');
INSERT INTO `dns_area` VALUES ('542500', null, '0', '阿里地区', '\0', '2', '1', '540000');
INSERT INTO `dns_area` VALUES ('542521', null, '0', '普兰县', '\0', '3', '1', '542500');
INSERT INTO `dns_area` VALUES ('542522', null, '0', '札达县', '\0', '3', '1', '542500');
INSERT INTO `dns_area` VALUES ('542523', null, '0', '噶尔县', '\0', '3', '1', '542500');
INSERT INTO `dns_area` VALUES ('542524', null, '0', '日土县', '\0', '3', '1', '542500');
INSERT INTO `dns_area` VALUES ('542525', null, '0', '革吉县', '\0', '3', '1', '542500');
INSERT INTO `dns_area` VALUES ('542526', null, '0', '改则县', '\0', '3', '1', '542500');
INSERT INTO `dns_area` VALUES ('542527', null, '0', '措勤县', '\0', '3', '1', '542500');
INSERT INTO `dns_area` VALUES ('542600', null, '0', '林芝地区', '\0', '2', '1', '540000');
INSERT INTO `dns_area` VALUES ('542621', null, '0', '林芝县', '\0', '3', '1', '542600');
INSERT INTO `dns_area` VALUES ('542622', null, '0', '工布江达县', '\0', '3', '1', '542600');
INSERT INTO `dns_area` VALUES ('542623', null, '0', '米林县', '\0', '3', '1', '542600');
INSERT INTO `dns_area` VALUES ('542624', null, '0', '墨脱县', '\0', '3', '1', '542600');
INSERT INTO `dns_area` VALUES ('542625', null, '0', '波密县', '\0', '3', '1', '542600');
INSERT INTO `dns_area` VALUES ('542626', null, '0', '察隅县', '\0', '3', '1', '542600');
INSERT INTO `dns_area` VALUES ('542627', null, '0', '朗　县', '\0', '3', '1', '542600');
INSERT INTO `dns_area` VALUES ('610000', null, '0', '陕西省', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('610100', null, '0', '西安市', '\0', '2', '1', '610000');
INSERT INTO `dns_area` VALUES ('610101', null, '0', '市辖区', '\0', '3', '1', '610100');
INSERT INTO `dns_area` VALUES ('610102', null, '0', '新城区', '\0', '3', '1', '610100');
INSERT INTO `dns_area` VALUES ('610103', null, '0', '碑林区', '\0', '3', '1', '610100');
INSERT INTO `dns_area` VALUES ('610104', null, '0', '莲湖区', '\0', '3', '1', '610100');
INSERT INTO `dns_area` VALUES ('610111', null, '0', '灞桥区', '\0', '3', '1', '610100');
INSERT INTO `dns_area` VALUES ('610112', null, '0', '未央区', '\0', '3', '1', '610100');
INSERT INTO `dns_area` VALUES ('610113', null, '0', '雁塔区', '\0', '3', '1', '610100');
INSERT INTO `dns_area` VALUES ('610114', null, '0', '阎良区', '\0', '3', '1', '610100');
INSERT INTO `dns_area` VALUES ('610115', null, '0', '临潼区', '\0', '3', '1', '610100');
INSERT INTO `dns_area` VALUES ('610116', null, '0', '长安区', '\0', '3', '1', '610100');
INSERT INTO `dns_area` VALUES ('610122', null, '0', '蓝田县', '\0', '3', '1', '610100');
INSERT INTO `dns_area` VALUES ('610124', null, '0', '周至县', '\0', '3', '1', '610100');
INSERT INTO `dns_area` VALUES ('610125', null, '0', '户　县', '\0', '3', '1', '610100');
INSERT INTO `dns_area` VALUES ('610126', null, '0', '高陵县', '\0', '3', '1', '610100');
INSERT INTO `dns_area` VALUES ('610200', null, '0', '铜川市', '\0', '2', '1', '610000');
INSERT INTO `dns_area` VALUES ('610201', null, '0', '市辖区', '\0', '3', '1', '610200');
INSERT INTO `dns_area` VALUES ('610202', null, '0', '王益区', '\0', '3', '1', '610200');
INSERT INTO `dns_area` VALUES ('610203', null, '0', '印台区', '\0', '3', '1', '610200');
INSERT INTO `dns_area` VALUES ('610204', null, '0', '耀州区', '\0', '3', '1', '610200');
INSERT INTO `dns_area` VALUES ('610222', null, '0', '宜君县', '\0', '3', '1', '610200');
INSERT INTO `dns_area` VALUES ('610300', null, '0', '宝鸡市', '\0', '2', '1', '610000');
INSERT INTO `dns_area` VALUES ('610301', null, '0', '市辖区', '\0', '3', '1', '610300');
INSERT INTO `dns_area` VALUES ('610302', null, '0', '渭滨区', '\0', '3', '1', '610300');
INSERT INTO `dns_area` VALUES ('610303', null, '0', '金台区', '\0', '3', '1', '610300');
INSERT INTO `dns_area` VALUES ('610304', null, '0', '陈仓区', '\0', '3', '1', '610300');
INSERT INTO `dns_area` VALUES ('610322', null, '0', '凤翔县', '\0', '3', '1', '610300');
INSERT INTO `dns_area` VALUES ('610323', null, '0', '岐山县', '\0', '3', '1', '610300');
INSERT INTO `dns_area` VALUES ('610324', null, '0', '扶风县', '\0', '3', '1', '610300');
INSERT INTO `dns_area` VALUES ('610326', null, '0', '眉　县', '\0', '3', '1', '610300');
INSERT INTO `dns_area` VALUES ('610327', null, '0', '陇　县', '\0', '3', '1', '610300');
INSERT INTO `dns_area` VALUES ('610328', null, '0', '千阳县', '\0', '3', '1', '610300');
INSERT INTO `dns_area` VALUES ('610329', null, '0', '麟游县', '\0', '3', '1', '610300');
INSERT INTO `dns_area` VALUES ('610330', null, '0', '凤　县', '\0', '3', '1', '610300');
INSERT INTO `dns_area` VALUES ('610331', null, '0', '太白县', '\0', '3', '1', '610300');
INSERT INTO `dns_area` VALUES ('610400', null, '0', '咸阳市', '\0', '2', '1', '610000');
INSERT INTO `dns_area` VALUES ('610401', null, '0', '市辖区', '\0', '3', '1', '610400');
INSERT INTO `dns_area` VALUES ('610402', null, '0', '秦都区', '\0', '3', '1', '610400');
INSERT INTO `dns_area` VALUES ('610403', null, '0', '杨凌区', '\0', '3', '1', '610400');
INSERT INTO `dns_area` VALUES ('610404', null, '0', '渭城区', '\0', '3', '1', '610400');
INSERT INTO `dns_area` VALUES ('610422', null, '0', '三原县', '\0', '3', '1', '610400');
INSERT INTO `dns_area` VALUES ('610423', null, '0', '泾阳县', '\0', '3', '1', '610400');
INSERT INTO `dns_area` VALUES ('610424', null, '0', '乾　县', '\0', '3', '1', '610400');
INSERT INTO `dns_area` VALUES ('610425', null, '0', '礼泉县', '\0', '3', '1', '610400');
INSERT INTO `dns_area` VALUES ('610426', null, '0', '永寿县', '\0', '3', '1', '610400');
INSERT INTO `dns_area` VALUES ('610427', null, '0', '彬　县', '\0', '3', '1', '610400');
INSERT INTO `dns_area` VALUES ('610428', null, '0', '长武县', '\0', '3', '1', '610400');
INSERT INTO `dns_area` VALUES ('610429', null, '0', '旬邑县', '\0', '3', '1', '610400');
INSERT INTO `dns_area` VALUES ('610430', null, '0', '淳化县', '\0', '3', '1', '610400');
INSERT INTO `dns_area` VALUES ('610431', null, '0', '武功县', '\0', '3', '1', '610400');
INSERT INTO `dns_area` VALUES ('610481', null, '0', '兴平市', '\0', '3', '1', '610400');
INSERT INTO `dns_area` VALUES ('610500', null, '0', '渭南市', '\0', '2', '1', '610000');
INSERT INTO `dns_area` VALUES ('610501', null, '0', '市辖区', '\0', '3', '1', '610500');
INSERT INTO `dns_area` VALUES ('610502', null, '0', '临渭区', '\0', '3', '1', '610500');
INSERT INTO `dns_area` VALUES ('610521', null, '0', '华　县', '\0', '3', '1', '610500');
INSERT INTO `dns_area` VALUES ('610522', null, '0', '潼关县', '\0', '3', '1', '610500');
INSERT INTO `dns_area` VALUES ('610523', null, '0', '大荔县', '\0', '3', '1', '610500');
INSERT INTO `dns_area` VALUES ('610524', null, '0', '合阳县', '\0', '3', '1', '610500');
INSERT INTO `dns_area` VALUES ('610525', null, '0', '澄城县', '\0', '3', '1', '610500');
INSERT INTO `dns_area` VALUES ('610526', null, '0', '蒲城县', '\0', '3', '1', '610500');
INSERT INTO `dns_area` VALUES ('610527', null, '0', '白水县', '\0', '3', '1', '610500');
INSERT INTO `dns_area` VALUES ('610528', null, '0', '富平县', '\0', '3', '1', '610500');
INSERT INTO `dns_area` VALUES ('610581', null, '0', '韩城市', '\0', '3', '1', '610500');
INSERT INTO `dns_area` VALUES ('610582', null, '0', '华阴市', '\0', '3', '1', '610500');
INSERT INTO `dns_area` VALUES ('610600', null, '0', '延安市', '\0', '2', '1', '610000');
INSERT INTO `dns_area` VALUES ('610601', null, '0', '市辖区', '\0', '3', '1', '610600');
INSERT INTO `dns_area` VALUES ('610602', null, '0', '宝塔区', '\0', '3', '1', '610600');
INSERT INTO `dns_area` VALUES ('610621', null, '0', '延长县', '\0', '3', '1', '610600');
INSERT INTO `dns_area` VALUES ('610622', null, '0', '延川县', '\0', '3', '1', '610600');
INSERT INTO `dns_area` VALUES ('610623', null, '0', '子长县', '\0', '3', '1', '610600');
INSERT INTO `dns_area` VALUES ('610624', null, '0', '安塞县', '\0', '3', '1', '610600');
INSERT INTO `dns_area` VALUES ('610625', null, '0', '志丹县', '\0', '3', '1', '610600');
INSERT INTO `dns_area` VALUES ('610626', null, '0', '吴旗县', '\0', '3', '1', '610600');
INSERT INTO `dns_area` VALUES ('610627', null, '0', '甘泉县', '\0', '3', '1', '610600');
INSERT INTO `dns_area` VALUES ('610628', null, '0', '富　县', '\0', '3', '1', '610600');
INSERT INTO `dns_area` VALUES ('610629', null, '0', '洛川县', '\0', '3', '1', '610600');
INSERT INTO `dns_area` VALUES ('610630', null, '0', '宜川县', '\0', '3', '1', '610600');
INSERT INTO `dns_area` VALUES ('610631', null, '0', '黄龙县', '\0', '3', '1', '610600');
INSERT INTO `dns_area` VALUES ('610632', null, '0', '黄陵县', '\0', '3', '1', '610600');
INSERT INTO `dns_area` VALUES ('610700', null, '0', '汉中市', '\0', '2', '1', '610000');
INSERT INTO `dns_area` VALUES ('610701', null, '0', '市辖区', '\0', '3', '1', '610700');
INSERT INTO `dns_area` VALUES ('610702', null, '0', '汉台区', '\0', '3', '1', '610700');
INSERT INTO `dns_area` VALUES ('610721', null, '0', '南郑县', '\0', '3', '1', '610700');
INSERT INTO `dns_area` VALUES ('610722', null, '0', '城固县', '\0', '3', '1', '610700');
INSERT INTO `dns_area` VALUES ('610723', null, '0', '洋　县', '\0', '3', '1', '610700');
INSERT INTO `dns_area` VALUES ('610724', null, '0', '西乡县', '\0', '3', '1', '610700');
INSERT INTO `dns_area` VALUES ('610725', null, '0', '勉　县', '\0', '3', '1', '610700');
INSERT INTO `dns_area` VALUES ('610726', null, '0', '宁强县', '\0', '3', '1', '610700');
INSERT INTO `dns_area` VALUES ('610727', null, '0', '略阳县', '\0', '3', '1', '610700');
INSERT INTO `dns_area` VALUES ('610728', null, '0', '镇巴县', '\0', '3', '1', '610700');
INSERT INTO `dns_area` VALUES ('610729', null, '0', '留坝县', '\0', '3', '1', '610700');
INSERT INTO `dns_area` VALUES ('610730', null, '0', '佛坪县', '\0', '3', '1', '610700');
INSERT INTO `dns_area` VALUES ('610800', null, '0', '榆林市', '\0', '2', '1', '610000');
INSERT INTO `dns_area` VALUES ('610801', null, '0', '市辖区', '\0', '3', '1', '610800');
INSERT INTO `dns_area` VALUES ('610802', null, '0', '榆阳区', '\0', '3', '1', '610800');
INSERT INTO `dns_area` VALUES ('610821', null, '0', '神木县', '\0', '3', '1', '610800');
INSERT INTO `dns_area` VALUES ('610822', null, '0', '府谷县', '\0', '3', '1', '610800');
INSERT INTO `dns_area` VALUES ('610823', null, '0', '横山县', '\0', '3', '1', '610800');
INSERT INTO `dns_area` VALUES ('610824', null, '0', '靖边县', '\0', '3', '1', '610800');
INSERT INTO `dns_area` VALUES ('610825', null, '0', '定边县', '\0', '3', '1', '610800');
INSERT INTO `dns_area` VALUES ('610826', null, '0', '绥德县', '\0', '3', '1', '610800');
INSERT INTO `dns_area` VALUES ('610827', null, '0', '米脂县', '\0', '3', '1', '610800');
INSERT INTO `dns_area` VALUES ('610828', null, '0', '佳　县', '\0', '3', '1', '610800');
INSERT INTO `dns_area` VALUES ('610829', null, '0', '吴堡县', '\0', '3', '1', '610800');
INSERT INTO `dns_area` VALUES ('610830', null, '0', '清涧县', '\0', '3', '1', '610800');
INSERT INTO `dns_area` VALUES ('610831', null, '0', '子洲县', '\0', '3', '1', '610800');
INSERT INTO `dns_area` VALUES ('610900', null, '0', '安康市', '\0', '2', '1', '610000');
INSERT INTO `dns_area` VALUES ('610901', null, '0', '市辖区', '\0', '3', '1', '610900');
INSERT INTO `dns_area` VALUES ('610902', null, '0', '汉滨区', '\0', '3', '1', '610900');
INSERT INTO `dns_area` VALUES ('610921', null, '0', '汉阴县', '\0', '3', '1', '610900');
INSERT INTO `dns_area` VALUES ('610922', null, '0', '石泉县', '\0', '3', '1', '610900');
INSERT INTO `dns_area` VALUES ('610923', null, '0', '宁陕县', '\0', '3', '1', '610900');
INSERT INTO `dns_area` VALUES ('610924', null, '0', '紫阳县', '\0', '3', '1', '610900');
INSERT INTO `dns_area` VALUES ('610925', null, '0', '岚皋县', '\0', '3', '1', '610900');
INSERT INTO `dns_area` VALUES ('610926', null, '0', '平利县', '\0', '3', '1', '610900');
INSERT INTO `dns_area` VALUES ('610927', null, '0', '镇坪县', '\0', '3', '1', '610900');
INSERT INTO `dns_area` VALUES ('610928', null, '0', '旬阳县', '\0', '3', '1', '610900');
INSERT INTO `dns_area` VALUES ('610929', null, '0', '白河县', '\0', '3', '1', '610900');
INSERT INTO `dns_area` VALUES ('611000', null, '0', '商洛市', '\0', '2', '1', '610000');
INSERT INTO `dns_area` VALUES ('611001', null, '0', '市辖区', '\0', '3', '1', '611000');
INSERT INTO `dns_area` VALUES ('611002', null, '0', '商州区', '\0', '3', '1', '611000');
INSERT INTO `dns_area` VALUES ('611021', null, '0', '洛南县', '\0', '3', '1', '611000');
INSERT INTO `dns_area` VALUES ('611022', null, '0', '丹凤县', '\0', '3', '1', '611000');
INSERT INTO `dns_area` VALUES ('611023', null, '0', '商南县', '\0', '3', '1', '611000');
INSERT INTO `dns_area` VALUES ('611024', null, '0', '山阳县', '\0', '3', '1', '611000');
INSERT INTO `dns_area` VALUES ('611025', null, '0', '镇安县', '\0', '3', '1', '611000');
INSERT INTO `dns_area` VALUES ('611026', null, '0', '柞水县', '\0', '3', '1', '611000');
INSERT INTO `dns_area` VALUES ('620000', null, '0', '甘肃省', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('620100', null, '0', '兰州市', '\0', '2', '1', '620000');
INSERT INTO `dns_area` VALUES ('620101', null, '0', '市辖区', '\0', '3', '1', '620100');
INSERT INTO `dns_area` VALUES ('620102', null, '0', '城关区', '\0', '3', '1', '620100');
INSERT INTO `dns_area` VALUES ('620103', null, '0', '七里河区', '\0', '3', '1', '620100');
INSERT INTO `dns_area` VALUES ('620104', null, '0', '西固区', '\0', '3', '1', '620100');
INSERT INTO `dns_area` VALUES ('620105', null, '0', '安宁区', '\0', '3', '1', '620100');
INSERT INTO `dns_area` VALUES ('620111', null, '0', '红古区', '\0', '3', '1', '620100');
INSERT INTO `dns_area` VALUES ('620121', null, '0', '永登县', '\0', '3', '1', '620100');
INSERT INTO `dns_area` VALUES ('620122', null, '0', '皋兰县', '\0', '3', '1', '620100');
INSERT INTO `dns_area` VALUES ('620123', null, '0', '榆中县', '\0', '3', '1', '620100');
INSERT INTO `dns_area` VALUES ('620200', null, '0', '嘉峪关市', '\0', '2', '1', '620000');
INSERT INTO `dns_area` VALUES ('620201', null, '0', '市辖区', '\0', '3', '1', '620200');
INSERT INTO `dns_area` VALUES ('620300', null, '0', '金昌市', '\0', '2', '1', '620000');
INSERT INTO `dns_area` VALUES ('620301', null, '0', '市辖区', '\0', '3', '1', '620300');
INSERT INTO `dns_area` VALUES ('620302', null, '0', '金川区', '\0', '3', '1', '620300');
INSERT INTO `dns_area` VALUES ('620321', null, '0', '永昌县', '\0', '3', '1', '620300');
INSERT INTO `dns_area` VALUES ('620400', null, '0', '白银市', '\0', '2', '1', '620000');
INSERT INTO `dns_area` VALUES ('620401', null, '0', '市辖区', '\0', '3', '1', '620400');
INSERT INTO `dns_area` VALUES ('620402', null, '0', '白银区', '\0', '3', '1', '620400');
INSERT INTO `dns_area` VALUES ('620403', null, '0', '平川区', '\0', '3', '1', '620400');
INSERT INTO `dns_area` VALUES ('620421', null, '0', '靖远县', '\0', '3', '1', '620400');
INSERT INTO `dns_area` VALUES ('620422', null, '0', '会宁县', '\0', '3', '1', '620400');
INSERT INTO `dns_area` VALUES ('620423', null, '0', '景泰县', '\0', '3', '1', '620400');
INSERT INTO `dns_area` VALUES ('620500', null, '0', '天水市', '\0', '2', '1', '620000');
INSERT INTO `dns_area` VALUES ('620501', null, '0', '市辖区', '\0', '3', '1', '620500');
INSERT INTO `dns_area` VALUES ('620502', null, '0', '秦城区', '\0', '3', '1', '620500');
INSERT INTO `dns_area` VALUES ('620503', null, '0', '北道区', '\0', '3', '1', '620500');
INSERT INTO `dns_area` VALUES ('620521', null, '0', '清水县', '\0', '3', '1', '620500');
INSERT INTO `dns_area` VALUES ('620522', null, '0', '秦安县', '\0', '3', '1', '620500');
INSERT INTO `dns_area` VALUES ('620523', null, '0', '甘谷县', '\0', '3', '1', '620500');
INSERT INTO `dns_area` VALUES ('620524', null, '0', '武山县', '\0', '3', '1', '620500');
INSERT INTO `dns_area` VALUES ('620525', null, '0', '张家川回族自治县', '\0', '3', '1', '620500');
INSERT INTO `dns_area` VALUES ('620600', null, '0', '武威市', '\0', '2', '1', '620000');
INSERT INTO `dns_area` VALUES ('620601', null, '0', '市辖区', '\0', '3', '1', '620600');
INSERT INTO `dns_area` VALUES ('620602', null, '0', '凉州区', '\0', '3', '1', '620600');
INSERT INTO `dns_area` VALUES ('620621', null, '0', '民勤县', '\0', '3', '1', '620600');
INSERT INTO `dns_area` VALUES ('620622', null, '0', '古浪县', '\0', '3', '1', '620600');
INSERT INTO `dns_area` VALUES ('620623', null, '0', '天祝藏族自治县', '\0', '3', '1', '620600');
INSERT INTO `dns_area` VALUES ('620700', null, '0', '张掖市', '\0', '2', '1', '620000');
INSERT INTO `dns_area` VALUES ('620701', null, '0', '市辖区', '\0', '3', '1', '620700');
INSERT INTO `dns_area` VALUES ('620702', null, '0', '甘州区', '\0', '3', '1', '620700');
INSERT INTO `dns_area` VALUES ('620721', null, '0', '肃南裕固族自治县', '\0', '3', '1', '620700');
INSERT INTO `dns_area` VALUES ('620722', null, '0', '民乐县', '\0', '3', '1', '620700');
INSERT INTO `dns_area` VALUES ('620723', null, '0', '临泽县', '\0', '3', '1', '620700');
INSERT INTO `dns_area` VALUES ('620724', null, '0', '高台县', '\0', '3', '1', '620700');
INSERT INTO `dns_area` VALUES ('620725', null, '0', '山丹县', '\0', '3', '1', '620700');
INSERT INTO `dns_area` VALUES ('620800', null, '0', '平凉市', '\0', '2', '1', '620000');
INSERT INTO `dns_area` VALUES ('620801', null, '0', '市辖区', '\0', '3', '1', '620800');
INSERT INTO `dns_area` VALUES ('620802', null, '0', '崆峒区', '\0', '3', '1', '620800');
INSERT INTO `dns_area` VALUES ('620821', null, '0', '泾川县', '\0', '3', '1', '620800');
INSERT INTO `dns_area` VALUES ('620822', null, '0', '灵台县', '\0', '3', '1', '620800');
INSERT INTO `dns_area` VALUES ('620823', null, '0', '崇信县', '\0', '3', '1', '620800');
INSERT INTO `dns_area` VALUES ('620824', null, '0', '华亭县', '\0', '3', '1', '620800');
INSERT INTO `dns_area` VALUES ('620825', null, '0', '庄浪县', '\0', '3', '1', '620800');
INSERT INTO `dns_area` VALUES ('620826', null, '0', '静宁县', '\0', '3', '1', '620800');
INSERT INTO `dns_area` VALUES ('620900', null, '0', '酒泉市', '\0', '2', '1', '620000');
INSERT INTO `dns_area` VALUES ('620901', null, '0', '市辖区', '\0', '3', '1', '620900');
INSERT INTO `dns_area` VALUES ('620902', null, '0', '肃州区', '\0', '3', '1', '620900');
INSERT INTO `dns_area` VALUES ('620921', null, '0', '金塔县', '\0', '3', '1', '620900');
INSERT INTO `dns_area` VALUES ('620922', null, '0', '安西县', '\0', '3', '1', '620900');
INSERT INTO `dns_area` VALUES ('620923', null, '0', '肃北蒙古族自治县', '\0', '3', '1', '620900');
INSERT INTO `dns_area` VALUES ('620924', null, '0', '阿克塞哈萨克族自治县', '\0', '3', '1', '620900');
INSERT INTO `dns_area` VALUES ('620981', null, '0', '玉门市', '\0', '3', '1', '620900');
INSERT INTO `dns_area` VALUES ('620982', null, '0', '敦煌市', '\0', '3', '1', '620900');
INSERT INTO `dns_area` VALUES ('621000', null, '0', '庆阳市', '\0', '2', '1', '620000');
INSERT INTO `dns_area` VALUES ('621001', null, '0', '市辖区', '\0', '3', '1', '621000');
INSERT INTO `dns_area` VALUES ('621002', null, '0', '西峰区', '\0', '3', '1', '621000');
INSERT INTO `dns_area` VALUES ('621021', null, '0', '庆城县', '\0', '3', '1', '621000');
INSERT INTO `dns_area` VALUES ('621022', null, '0', '环　县', '\0', '3', '1', '621000');
INSERT INTO `dns_area` VALUES ('621023', null, '0', '华池县', '\0', '3', '1', '621000');
INSERT INTO `dns_area` VALUES ('621024', null, '0', '合水县', '\0', '3', '1', '621000');
INSERT INTO `dns_area` VALUES ('621025', null, '0', '正宁县', '\0', '3', '1', '621000');
INSERT INTO `dns_area` VALUES ('621026', null, '0', '宁　县', '\0', '3', '1', '621000');
INSERT INTO `dns_area` VALUES ('621027', null, '0', '镇原县', '\0', '3', '1', '621000');
INSERT INTO `dns_area` VALUES ('621100', null, '0', '定西市', '\0', '2', '1', '620000');
INSERT INTO `dns_area` VALUES ('621101', null, '0', '市辖区', '\0', '3', '1', '621100');
INSERT INTO `dns_area` VALUES ('621102', null, '0', '安定区', '\0', '3', '1', '621100');
INSERT INTO `dns_area` VALUES ('621121', null, '0', '通渭县', '\0', '3', '1', '621100');
INSERT INTO `dns_area` VALUES ('621122', null, '0', '陇西县', '\0', '3', '1', '621100');
INSERT INTO `dns_area` VALUES ('621123', null, '0', '渭源县', '\0', '3', '1', '621100');
INSERT INTO `dns_area` VALUES ('621124', null, '0', '临洮县', '\0', '3', '1', '621100');
INSERT INTO `dns_area` VALUES ('621125', null, '0', '漳　县', '\0', '3', '1', '621100');
INSERT INTO `dns_area` VALUES ('621126', null, '0', '岷　县', '\0', '3', '1', '621100');
INSERT INTO `dns_area` VALUES ('621200', null, '0', '陇南市', '\0', '2', '1', '620000');
INSERT INTO `dns_area` VALUES ('621201', null, '0', '市辖区', '\0', '3', '1', '621200');
INSERT INTO `dns_area` VALUES ('621202', null, '0', '武都区', '\0', '3', '1', '621200');
INSERT INTO `dns_area` VALUES ('621221', null, '0', '成　县', '\0', '3', '1', '621200');
INSERT INTO `dns_area` VALUES ('621222', null, '0', '文　县', '\0', '3', '1', '621200');
INSERT INTO `dns_area` VALUES ('621223', null, '0', '宕昌县', '\0', '3', '1', '621200');
INSERT INTO `dns_area` VALUES ('621224', null, '0', '康　县', '\0', '3', '1', '621200');
INSERT INTO `dns_area` VALUES ('621225', null, '0', '西和县', '\0', '3', '1', '621200');
INSERT INTO `dns_area` VALUES ('621226', null, '0', '礼　县', '\0', '3', '1', '621200');
INSERT INTO `dns_area` VALUES ('621227', null, '0', '徽　县', '\0', '3', '1', '621200');
INSERT INTO `dns_area` VALUES ('621228', null, '0', '两当县', '\0', '3', '1', '621200');
INSERT INTO `dns_area` VALUES ('622900', null, '0', '临夏回族自治州', '\0', '2', '1', '620000');
INSERT INTO `dns_area` VALUES ('622901', null, '0', '临夏市', '\0', '3', '1', '622900');
INSERT INTO `dns_area` VALUES ('622921', null, '0', '临夏县', '\0', '3', '1', '622900');
INSERT INTO `dns_area` VALUES ('622922', null, '0', '康乐县', '\0', '3', '1', '622900');
INSERT INTO `dns_area` VALUES ('622923', null, '0', '永靖县', '\0', '3', '1', '622900');
INSERT INTO `dns_area` VALUES ('622924', null, '0', '广河县', '\0', '3', '1', '622900');
INSERT INTO `dns_area` VALUES ('622925', null, '0', '和政县', '\0', '3', '1', '622900');
INSERT INTO `dns_area` VALUES ('622926', null, '0', '东乡族自治县', '\0', '3', '1', '622900');
INSERT INTO `dns_area` VALUES ('622927', null, '0', '积石山保安族东乡族撒拉族自治县', '\0', '3', '1', '622900');
INSERT INTO `dns_area` VALUES ('623000', null, '0', '甘南藏族自治州', '\0', '2', '1', '620000');
INSERT INTO `dns_area` VALUES ('623001', null, '0', '合作市', '\0', '3', '1', '623000');
INSERT INTO `dns_area` VALUES ('623021', null, '0', '临潭县', '\0', '3', '1', '623000');
INSERT INTO `dns_area` VALUES ('623022', null, '0', '卓尼县', '\0', '3', '1', '623000');
INSERT INTO `dns_area` VALUES ('623023', null, '0', '舟曲县', '\0', '3', '1', '623000');
INSERT INTO `dns_area` VALUES ('623024', null, '0', '迭部县', '\0', '3', '1', '623000');
INSERT INTO `dns_area` VALUES ('623025', null, '0', '玛曲县', '\0', '3', '1', '623000');
INSERT INTO `dns_area` VALUES ('623026', null, '0', '碌曲县', '\0', '3', '1', '623000');
INSERT INTO `dns_area` VALUES ('623027', null, '0', '夏河县', '\0', '3', '1', '623000');
INSERT INTO `dns_area` VALUES ('630000', null, '0', '青海省', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('630100', null, '0', '西宁市', '\0', '2', '1', '630000');
INSERT INTO `dns_area` VALUES ('630101', null, '0', '市辖区', '\0', '3', '1', '630100');
INSERT INTO `dns_area` VALUES ('630102', null, '0', '城东区', '\0', '3', '1', '630100');
INSERT INTO `dns_area` VALUES ('630103', null, '0', '城中区', '\0', '3', '1', '630100');
INSERT INTO `dns_area` VALUES ('630104', null, '0', '城西区', '\0', '3', '1', '630100');
INSERT INTO `dns_area` VALUES ('630105', null, '0', '城北区', '\0', '3', '1', '630100');
INSERT INTO `dns_area` VALUES ('630121', null, '0', '大通回族土族自治县', '\0', '3', '1', '630100');
INSERT INTO `dns_area` VALUES ('630122', null, '0', '湟中县', '\0', '3', '1', '630100');
INSERT INTO `dns_area` VALUES ('630123', null, '0', '湟源县', '\0', '3', '1', '630100');
INSERT INTO `dns_area` VALUES ('632100', null, '0', '海东地区', '\0', '2', '1', '630000');
INSERT INTO `dns_area` VALUES ('632121', null, '0', '平安县', '\0', '3', '1', '632100');
INSERT INTO `dns_area` VALUES ('632122', null, '0', '民和回族土族自治县', '\0', '3', '1', '632100');
INSERT INTO `dns_area` VALUES ('632123', null, '0', '乐都县', '\0', '3', '1', '632100');
INSERT INTO `dns_area` VALUES ('632126', null, '0', '互助土族自治县', '\0', '3', '1', '632100');
INSERT INTO `dns_area` VALUES ('632127', null, '0', '化隆回族自治县', '\0', '3', '1', '632100');
INSERT INTO `dns_area` VALUES ('632128', null, '0', '循化撒拉族自治县', '\0', '3', '1', '632100');
INSERT INTO `dns_area` VALUES ('632200', null, '0', '海北藏族自治州', '\0', '2', '1', '630000');
INSERT INTO `dns_area` VALUES ('632221', null, '0', '门源回族自治县', '\0', '3', '1', '632200');
INSERT INTO `dns_area` VALUES ('632222', null, '0', '祁连县', '\0', '3', '1', '632200');
INSERT INTO `dns_area` VALUES ('632223', null, '0', '海晏县', '\0', '3', '1', '632200');
INSERT INTO `dns_area` VALUES ('632224', null, '0', '刚察县', '\0', '3', '1', '632200');
INSERT INTO `dns_area` VALUES ('632300', null, '0', '黄南藏族自治州', '\0', '2', '1', '630000');
INSERT INTO `dns_area` VALUES ('632321', null, '0', '同仁县', '\0', '3', '1', '632300');
INSERT INTO `dns_area` VALUES ('632322', null, '0', '尖扎县', '\0', '3', '1', '632300');
INSERT INTO `dns_area` VALUES ('632323', null, '0', '泽库县', '\0', '3', '1', '632300');
INSERT INTO `dns_area` VALUES ('632324', null, '0', '河南蒙古族自治县', '\0', '3', '1', '632300');
INSERT INTO `dns_area` VALUES ('632500', null, '0', '海南藏族自治州', '\0', '2', '1', '630000');
INSERT INTO `dns_area` VALUES ('632521', null, '0', '共和县', '\0', '3', '1', '632500');
INSERT INTO `dns_area` VALUES ('632522', null, '0', '同德县', '\0', '3', '1', '632500');
INSERT INTO `dns_area` VALUES ('632523', null, '0', '贵德县', '\0', '3', '1', '632500');
INSERT INTO `dns_area` VALUES ('632524', null, '0', '兴海县', '\0', '3', '1', '632500');
INSERT INTO `dns_area` VALUES ('632525', null, '0', '贵南县', '\0', '3', '1', '632500');
INSERT INTO `dns_area` VALUES ('632600', null, '0', '果洛藏族自治州', '\0', '2', '1', '630000');
INSERT INTO `dns_area` VALUES ('632621', null, '0', '玛沁县', '\0', '3', '1', '632600');
INSERT INTO `dns_area` VALUES ('632622', null, '0', '班玛县', '\0', '3', '1', '632600');
INSERT INTO `dns_area` VALUES ('632623', null, '0', '甘德县', '\0', '3', '1', '632600');
INSERT INTO `dns_area` VALUES ('632624', null, '0', '达日县', '\0', '3', '1', '632600');
INSERT INTO `dns_area` VALUES ('632625', null, '0', '久治县', '\0', '3', '1', '632600');
INSERT INTO `dns_area` VALUES ('632626', null, '0', '玛多县', '\0', '3', '1', '632600');
INSERT INTO `dns_area` VALUES ('632700', null, '0', '玉树藏族自治州', '\0', '2', '1', '630000');
INSERT INTO `dns_area` VALUES ('632721', null, '0', '玉树县', '\0', '3', '1', '632700');
INSERT INTO `dns_area` VALUES ('632722', null, '0', '杂多县', '\0', '3', '1', '632700');
INSERT INTO `dns_area` VALUES ('632723', null, '0', '称多县', '\0', '3', '1', '632700');
INSERT INTO `dns_area` VALUES ('632724', null, '0', '治多县', '\0', '3', '1', '632700');
INSERT INTO `dns_area` VALUES ('632725', null, '0', '囊谦县', '\0', '3', '1', '632700');
INSERT INTO `dns_area` VALUES ('632726', null, '0', '曲麻莱县', '\0', '3', '1', '632700');
INSERT INTO `dns_area` VALUES ('632800', null, '0', '海西蒙古族藏族自治州', '\0', '2', '1', '630000');
INSERT INTO `dns_area` VALUES ('632801', null, '0', '格尔木市', '\0', '3', '1', '632800');
INSERT INTO `dns_area` VALUES ('632802', null, '0', '德令哈市', '\0', '3', '1', '632800');
INSERT INTO `dns_area` VALUES ('632821', null, '0', '乌兰县', '\0', '3', '1', '632800');
INSERT INTO `dns_area` VALUES ('632822', null, '0', '都兰县', '\0', '3', '1', '632800');
INSERT INTO `dns_area` VALUES ('632823', null, '0', '天峻县', '\0', '3', '1', '632800');
INSERT INTO `dns_area` VALUES ('640000', null, '0', '宁夏回族自治区', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('640100', null, '0', '银川市', '\0', '2', '1', '640000');
INSERT INTO `dns_area` VALUES ('640101', null, '0', '市辖区', '\0', '3', '1', '640100');
INSERT INTO `dns_area` VALUES ('640104', null, '0', '兴庆区', '\0', '3', '1', '640100');
INSERT INTO `dns_area` VALUES ('640105', null, '0', '西夏区', '\0', '3', '1', '640100');
INSERT INTO `dns_area` VALUES ('640106', null, '0', '金凤区', '\0', '3', '1', '640100');
INSERT INTO `dns_area` VALUES ('640121', null, '0', '永宁县', '\0', '3', '1', '640100');
INSERT INTO `dns_area` VALUES ('640122', null, '0', '贺兰县', '\0', '3', '1', '640100');
INSERT INTO `dns_area` VALUES ('640181', null, '0', '灵武市', '\0', '3', '1', '640100');
INSERT INTO `dns_area` VALUES ('640200', null, '0', '石嘴山市', '\0', '2', '1', '640000');
INSERT INTO `dns_area` VALUES ('640201', null, '0', '市辖区', '\0', '3', '1', '640200');
INSERT INTO `dns_area` VALUES ('640202', null, '0', '大武口区', '\0', '3', '1', '640200');
INSERT INTO `dns_area` VALUES ('640205', null, '0', '惠农区', '\0', '3', '1', '640200');
INSERT INTO `dns_area` VALUES ('640221', null, '0', '平罗县', '\0', '3', '1', '640200');
INSERT INTO `dns_area` VALUES ('640300', null, '0', '吴忠市', '\0', '2', '1', '640000');
INSERT INTO `dns_area` VALUES ('640301', null, '0', '市辖区', '\0', '3', '1', '640300');
INSERT INTO `dns_area` VALUES ('640302', null, '0', '利通区', '\0', '3', '1', '640300');
INSERT INTO `dns_area` VALUES ('640323', null, '0', '盐池县', '\0', '3', '1', '640300');
INSERT INTO `dns_area` VALUES ('640324', null, '0', '同心县', '\0', '3', '1', '640300');
INSERT INTO `dns_area` VALUES ('640381', null, '0', '青铜峡市', '\0', '3', '1', '640300');
INSERT INTO `dns_area` VALUES ('640400', null, '0', '固原市', '\0', '2', '1', '640000');
INSERT INTO `dns_area` VALUES ('640401', null, '0', '市辖区', '\0', '3', '1', '640400');
INSERT INTO `dns_area` VALUES ('640402', null, '0', '原州区', '\0', '3', '1', '640400');
INSERT INTO `dns_area` VALUES ('640422', null, '0', '西吉县', '\0', '3', '1', '640400');
INSERT INTO `dns_area` VALUES ('640423', null, '0', '隆德县', '\0', '3', '1', '640400');
INSERT INTO `dns_area` VALUES ('640424', null, '0', '泾源县', '\0', '3', '1', '640400');
INSERT INTO `dns_area` VALUES ('640425', null, '0', '彭阳县', '\0', '3', '1', '640400');
INSERT INTO `dns_area` VALUES ('640500', null, '0', '中卫市', '\0', '2', '1', '640000');
INSERT INTO `dns_area` VALUES ('640501', null, '0', '市辖区', '\0', '3', '1', '640500');
INSERT INTO `dns_area` VALUES ('640502', null, '0', '沙坡头区', '\0', '3', '1', '640500');
INSERT INTO `dns_area` VALUES ('640521', null, '0', '中宁县', '\0', '3', '1', '640500');
INSERT INTO `dns_area` VALUES ('640522', null, '0', '海原县', '\0', '3', '1', '640400');
INSERT INTO `dns_area` VALUES ('650000', null, '0', '新疆维吾尔自治区', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('650100', null, '0', '乌鲁木齐市', '\0', '2', '1', '650000');
INSERT INTO `dns_area` VALUES ('650101', null, '0', '市辖区', '\0', '3', '1', '650100');
INSERT INTO `dns_area` VALUES ('650102', null, '0', '天山区', '\0', '3', '1', '650100');
INSERT INTO `dns_area` VALUES ('650103', null, '0', '沙依巴克区', '\0', '3', '1', '650100');
INSERT INTO `dns_area` VALUES ('650104', null, '0', '新市区', '\0', '3', '1', '650100');
INSERT INTO `dns_area` VALUES ('650105', null, '0', '水磨沟区', '\0', '3', '1', '650100');
INSERT INTO `dns_area` VALUES ('650106', null, '0', '头屯河区', '\0', '3', '1', '650100');
INSERT INTO `dns_area` VALUES ('650107', null, '0', '达坂城区', '\0', '3', '1', '650100');
INSERT INTO `dns_area` VALUES ('650108', null, '0', '东山区', '\0', '3', '1', '650100');
INSERT INTO `dns_area` VALUES ('650121', null, '0', '乌鲁木齐县', '\0', '3', '1', '650100');
INSERT INTO `dns_area` VALUES ('650200', null, '0', '克拉玛依市', '\0', '2', '1', '650000');
INSERT INTO `dns_area` VALUES ('650201', null, '0', '市辖区', '\0', '3', '1', '650200');
INSERT INTO `dns_area` VALUES ('650202', null, '0', '独山子区', '\0', '3', '1', '650200');
INSERT INTO `dns_area` VALUES ('650203', null, '0', '克拉玛依区', '\0', '3', '1', '650200');
INSERT INTO `dns_area` VALUES ('650204', null, '0', '白碱滩区', '\0', '3', '1', '650200');
INSERT INTO `dns_area` VALUES ('650205', null, '0', '乌尔禾区', '\0', '3', '1', '650200');
INSERT INTO `dns_area` VALUES ('652100', null, '0', '吐鲁番地区', '\0', '2', '1', '650000');
INSERT INTO `dns_area` VALUES ('652101', null, '0', '吐鲁番市', '\0', '3', '1', '652100');
INSERT INTO `dns_area` VALUES ('652122', null, '0', '鄯善县', '\0', '3', '1', '652100');
INSERT INTO `dns_area` VALUES ('652123', null, '0', '托克逊县', '\0', '3', '1', '652100');
INSERT INTO `dns_area` VALUES ('652200', null, '0', '哈密地区', '\0', '2', '1', '650000');
INSERT INTO `dns_area` VALUES ('652201', null, '0', '哈密市', '\0', '3', '1', '652200');
INSERT INTO `dns_area` VALUES ('652222', null, '0', '巴里坤哈萨克自治县', '\0', '3', '1', '652200');
INSERT INTO `dns_area` VALUES ('652223', null, '0', '伊吾县', '\0', '3', '1', '652200');
INSERT INTO `dns_area` VALUES ('652300', null, '0', '昌吉回族自治州', '\0', '2', '1', '650000');
INSERT INTO `dns_area` VALUES ('652301', null, '0', '昌吉市', '\0', '3', '1', '652300');
INSERT INTO `dns_area` VALUES ('652302', null, '0', '阜康市', '\0', '3', '1', '652300');
INSERT INTO `dns_area` VALUES ('652303', null, '0', '米泉市', '\0', '3', '1', '652300');
INSERT INTO `dns_area` VALUES ('652323', null, '0', '呼图壁县', '\0', '3', '1', '652300');
INSERT INTO `dns_area` VALUES ('652324', null, '0', '玛纳斯县', '\0', '3', '1', '652300');
INSERT INTO `dns_area` VALUES ('652325', null, '0', '奇台县', '\0', '3', '1', '652300');
INSERT INTO `dns_area` VALUES ('652327', null, '0', '吉木萨尔县', '\0', '3', '1', '652300');
INSERT INTO `dns_area` VALUES ('652328', null, '0', '木垒哈萨克自治县', '\0', '3', '1', '652300');
INSERT INTO `dns_area` VALUES ('652700', null, '0', '博尔塔拉蒙古自治州', '\0', '2', '1', '650000');
INSERT INTO `dns_area` VALUES ('652701', null, '0', '博乐市', '\0', '3', '1', '652700');
INSERT INTO `dns_area` VALUES ('652722', null, '0', '精河县', '\0', '3', '1', '652700');
INSERT INTO `dns_area` VALUES ('652723', null, '0', '温泉县', '\0', '3', '1', '652700');
INSERT INTO `dns_area` VALUES ('652800', null, '0', '巴音郭楞蒙古自治州', '\0', '2', '1', '650000');
INSERT INTO `dns_area` VALUES ('652801', null, '0', '库尔勒市', '\0', '3', '1', '652800');
INSERT INTO `dns_area` VALUES ('652822', null, '0', '轮台县', '\0', '3', '1', '652800');
INSERT INTO `dns_area` VALUES ('652823', null, '0', '尉犁县', '\0', '3', '1', '652800');
INSERT INTO `dns_area` VALUES ('652824', null, '0', '若羌县', '\0', '3', '1', '652800');
INSERT INTO `dns_area` VALUES ('652825', null, '0', '且末县', '\0', '3', '1', '652800');
INSERT INTO `dns_area` VALUES ('652826', null, '0', '焉耆回族自治县', '\0', '3', '1', '652800');
INSERT INTO `dns_area` VALUES ('652827', null, '0', '和静县', '\0', '3', '1', '652800');
INSERT INTO `dns_area` VALUES ('652828', null, '0', '和硕县', '\0', '3', '1', '652800');
INSERT INTO `dns_area` VALUES ('652829', null, '0', '博湖县', '\0', '3', '1', '652800');
INSERT INTO `dns_area` VALUES ('652900', null, '0', '阿克苏地区', '\0', '2', '1', '650000');
INSERT INTO `dns_area` VALUES ('652901', null, '0', '阿克苏市', '\0', '3', '1', '652900');
INSERT INTO `dns_area` VALUES ('652922', null, '0', '温宿县', '\0', '3', '1', '652900');
INSERT INTO `dns_area` VALUES ('652923', null, '0', '库车县', '\0', '3', '1', '652900');
INSERT INTO `dns_area` VALUES ('652924', null, '0', '沙雅县', '\0', '3', '1', '652900');
INSERT INTO `dns_area` VALUES ('652925', null, '0', '新和县', '\0', '3', '1', '652900');
INSERT INTO `dns_area` VALUES ('652926', null, '0', '拜城县', '\0', '3', '1', '652900');
INSERT INTO `dns_area` VALUES ('652927', null, '0', '乌什县', '\0', '3', '1', '652900');
INSERT INTO `dns_area` VALUES ('652928', null, '0', '阿瓦提县', '\0', '3', '1', '652900');
INSERT INTO `dns_area` VALUES ('652929', null, '0', '柯坪县', '\0', '3', '1', '652900');
INSERT INTO `dns_area` VALUES ('653000', null, '0', '克孜勒苏柯尔克孜自治州', '\0', '2', '1', '650000');
INSERT INTO `dns_area` VALUES ('653001', null, '0', '阿图什市', '\0', '3', '1', '653000');
INSERT INTO `dns_area` VALUES ('653022', null, '0', '阿克陶县', '\0', '3', '1', '653000');
INSERT INTO `dns_area` VALUES ('653023', null, '0', '阿合奇县', '\0', '3', '1', '653000');
INSERT INTO `dns_area` VALUES ('653024', null, '0', '乌恰县', '\0', '3', '1', '653000');
INSERT INTO `dns_area` VALUES ('653100', null, '0', '喀什地区', '\0', '2', '1', '650000');
INSERT INTO `dns_area` VALUES ('653101', null, '0', '喀什市', '\0', '3', '1', '653100');
INSERT INTO `dns_area` VALUES ('653121', null, '0', '疏附县', '\0', '3', '1', '653100');
INSERT INTO `dns_area` VALUES ('653122', null, '0', '疏勒县', '\0', '3', '1', '653100');
INSERT INTO `dns_area` VALUES ('653123', null, '0', '英吉沙县', '\0', '3', '1', '653100');
INSERT INTO `dns_area` VALUES ('653124', null, '0', '泽普县', '\0', '3', '1', '653100');
INSERT INTO `dns_area` VALUES ('653125', null, '0', '莎车县', '\0', '3', '1', '653100');
INSERT INTO `dns_area` VALUES ('653126', null, '0', '叶城县', '\0', '3', '1', '653100');
INSERT INTO `dns_area` VALUES ('653127', null, '0', '麦盖提县', '\0', '3', '1', '653100');
INSERT INTO `dns_area` VALUES ('653128', null, '0', '岳普湖县', '\0', '3', '1', '653100');
INSERT INTO `dns_area` VALUES ('653129', null, '0', '伽师县', '\0', '3', '1', '653100');
INSERT INTO `dns_area` VALUES ('653130', null, '0', '巴楚县', '\0', '3', '1', '653100');
INSERT INTO `dns_area` VALUES ('653131', null, '0', '塔什库尔干塔吉克自治县', '\0', '3', '1', '653100');
INSERT INTO `dns_area` VALUES ('653200', null, '0', '和田地区', '\0', '2', '1', '650000');
INSERT INTO `dns_area` VALUES ('653201', null, '0', '和田市', '\0', '3', '1', '653200');
INSERT INTO `dns_area` VALUES ('653221', null, '0', '和田县', '\0', '3', '1', '653200');
INSERT INTO `dns_area` VALUES ('653222', null, '0', '墨玉县', '\0', '3', '1', '653200');
INSERT INTO `dns_area` VALUES ('653223', null, '0', '皮山县', '\0', '3', '1', '653200');
INSERT INTO `dns_area` VALUES ('653224', null, '0', '洛浦县', '\0', '3', '1', '653200');
INSERT INTO `dns_area` VALUES ('653225', null, '0', '策勒县', '\0', '3', '1', '653200');
INSERT INTO `dns_area` VALUES ('653226', null, '0', '于田县', '\0', '3', '1', '653200');
INSERT INTO `dns_area` VALUES ('653227', null, '0', '民丰县', '\0', '3', '1', '653200');
INSERT INTO `dns_area` VALUES ('654000', null, '0', '伊犁哈萨克自治州', '\0', '2', '1', '650000');
INSERT INTO `dns_area` VALUES ('654002', null, '0', '伊宁市', '\0', '3', '1', '654000');
INSERT INTO `dns_area` VALUES ('654003', null, '0', '奎屯市', '\0', '3', '1', '654000');
INSERT INTO `dns_area` VALUES ('654021', null, '0', '伊宁县', '\0', '3', '1', '654000');
INSERT INTO `dns_area` VALUES ('654022', null, '0', '察布查尔锡伯自治县', '\0', '3', '1', '654000');
INSERT INTO `dns_area` VALUES ('654023', null, '0', '霍城县', '\0', '3', '1', '654000');
INSERT INTO `dns_area` VALUES ('654024', null, '0', '巩留县', '\0', '3', '1', '654000');
INSERT INTO `dns_area` VALUES ('654025', null, '0', '新源县', '\0', '3', '1', '654000');
INSERT INTO `dns_area` VALUES ('654026', null, '0', '昭苏县', '\0', '3', '1', '654000');
INSERT INTO `dns_area` VALUES ('654027', null, '0', '特克斯县', '\0', '3', '1', '654000');
INSERT INTO `dns_area` VALUES ('654028', null, '0', '尼勒克县', '\0', '3', '1', '654000');
INSERT INTO `dns_area` VALUES ('654200', null, '0', '塔城地区', '\0', '2', '1', '650000');
INSERT INTO `dns_area` VALUES ('654201', null, '0', '塔城市', '\0', '3', '1', '654200');
INSERT INTO `dns_area` VALUES ('654202', null, '0', '乌苏市', '\0', '3', '1', '654200');
INSERT INTO `dns_area` VALUES ('654221', null, '0', '额敏县', '\0', '3', '1', '654200');
INSERT INTO `dns_area` VALUES ('654223', null, '0', '沙湾县', '\0', '3', '1', '654200');
INSERT INTO `dns_area` VALUES ('654224', null, '0', '托里县', '\0', '3', '1', '654200');
INSERT INTO `dns_area` VALUES ('654225', null, '0', '裕民县', '\0', '3', '1', '654200');
INSERT INTO `dns_area` VALUES ('654226', null, '0', '和布克赛尔蒙古自治县', '\0', '3', '1', '654200');
INSERT INTO `dns_area` VALUES ('654300', null, '0', '阿勒泰地区', '\0', '2', '1', '650000');
INSERT INTO `dns_area` VALUES ('654301', null, '0', '阿勒泰市', '\0', '3', '1', '654300');
INSERT INTO `dns_area` VALUES ('654321', null, '0', '布尔津县', '\0', '3', '1', '654300');
INSERT INTO `dns_area` VALUES ('654322', null, '0', '富蕴县', '\0', '3', '1', '654300');
INSERT INTO `dns_area` VALUES ('654323', null, '0', '福海县', '\0', '3', '1', '654300');
INSERT INTO `dns_area` VALUES ('654324', null, '0', '哈巴河县', '\0', '3', '1', '654300');
INSERT INTO `dns_area` VALUES ('654325', null, '0', '青河县', '\0', '3', '1', '654300');
INSERT INTO `dns_area` VALUES ('654326', null, '0', '吉木乃县', '\0', '3', '1', '654300');
INSERT INTO `dns_area` VALUES ('659000', null, '0', '省直辖行政单位', '\0', '2', '1', '650000');
INSERT INTO `dns_area` VALUES ('659001', null, '0', '石河子市', '\0', '3', '1', '659000');
INSERT INTO `dns_area` VALUES ('659002', null, '0', '阿拉尔市', '\0', '3', '1', '659000');
INSERT INTO `dns_area` VALUES ('659003', null, '0', '图木舒克市', '\0', '3', '1', '659000');
INSERT INTO `dns_area` VALUES ('659004', null, '0', '五家渠市', '\0', '3', '1', '659000');
INSERT INTO `dns_area` VALUES ('710000', null, '0', '台湾省', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('810000', null, '0', '香港特别行政区', '\0', '1', '1', null);
INSERT INTO `dns_area` VALUES ('820000', null, '0', '澳门特别行政区', '\0', '1', '1', null);

-- ----------------------------
-- Table structure for dns_bond_type_bank
-- ----------------------------
DROP TABLE IF EXISTS `dns_bond_type_bank`;
CREATE TABLE `dns_bond_type_bank` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(42) default NULL,
  `create_date` datetime default NULL,
  `create_user` bigint(20) default NULL,
  `enabled` bit(1) default '',
  `last_ver` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` bigint(20) default NULL,
  `leaf` bit(1) default '',
  `level` tinyint(4) default '1',
  `name` varchar(42) default NULL,
  `set_year` int(11) default NULL,
  `treepath` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  `parent_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKg73ykk7sux1oa4dbpamlkry8w` (`parent_id`),
  CONSTRAINT `FKg73ykk7sux1oa4dbpamlkry8w` FOREIGN KEY (`parent_id`) REFERENCES `dns_bond_type_bank` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_bond_type_bank
-- ----------------------------

-- ----------------------------
-- Table structure for dns_bond_type_enterprise
-- ----------------------------
DROP TABLE IF EXISTS `dns_bond_type_enterprise`;
CREATE TABLE `dns_bond_type_enterprise` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(42) default NULL,
  `create_date` datetime default NULL,
  `create_user` bigint(20) default NULL,
  `enabled` bit(1) default '',
  `last_ver` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` bigint(20) default NULL,
  `leaf` bit(1) default '',
  `level` tinyint(4) default '1',
  `name` varchar(42) default NULL,
  `set_year` int(11) default NULL,
  `treepath` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  `parent_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKpfp4bi8lytp2ynwqf9mwhwh7y` (`parent_id`),
  CONSTRAINT `FKpfp4bi8lytp2ynwqf9mwhwh7y` FOREIGN KEY (`parent_id`) REFERENCES `dns_bond_type_enterprise` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_bond_type_enterprise
-- ----------------------------

-- ----------------------------
-- Table structure for dns_casecomment
-- ----------------------------
DROP TABLE IF EXISTS `dns_casecomment`;
CREATE TABLE `dns_casecomment` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `commentcontent` longtext,
  `commentdate` datetime default NULL,
  `commentname` varchar(255) default NULL,
  `opposecounts` int(11) default NULL,
  `supportcounts` int(11) default NULL,
  `casedata_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKanh9dcu554bgrneriuhq9kho3` (`casedata_id`),
  CONSTRAINT `FKanh9dcu554bgrneriuhq9kho3` FOREIGN KEY (`casedata_id`) REFERENCES `dns_casedata` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_casecomment
-- ----------------------------

-- ----------------------------
-- Table structure for dns_casedata
-- ----------------------------
DROP TABLE IF EXISTS `dns_casedata`;
CREATE TABLE `dns_casedata` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `accordingto` varchar(255) default NULL,
  `caseintroduce` longtext,
  `casetitle` varchar(255) default NULL,
  `checkdate` varchar(255) default NULL,
  `entrydate` datetime default NULL,
  `entryperson` varchar(255) default NULL,
  `errorindicator` varchar(255) default NULL,
  `form` varchar(255) default NULL,
  `isopen` int(11) default NULL,
  `issubmit` int(11) default NULL,
  `rg_code` varchar(255) default NULL,
  `sortnumber` int(11) default NULL,
  `status` int(11) default '0',
  `becheckorg_id` bigint(20) default NULL,
  `elePbcOrg_id` bigint(20) default NULL,
  `errorcause_id` bigint(20) default NULL,
  `wrongperson_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKbcmpp0m31gos4hs1o4lsy3uqb` (`becheckorg_id`),
  KEY `FK94e8i9wa7nt31c6p7bw275kwa` (`elePbcOrg_id`),
  KEY `FKepiiia1sk4nk9g809yvl5gijv` (`errorcause_id`),
  KEY `FKinqmp32napqn5947gcsh0x2i5` (`wrongperson_id`),
  CONSTRAINT `FKinqmp32napqn5947gcsh0x2i5` FOREIGN KEY (`wrongperson_id`) REFERENCES `dns_lawenforcementofficers` (`id`),
  CONSTRAINT `FK94e8i9wa7nt31c6p7bw275kwa` FOREIGN KEY (`elePbcOrg_id`) REFERENCES `dns_ele_pbc_org` (`id`),
  CONSTRAINT `FKbcmpp0m31gos4hs1o4lsy3uqb` FOREIGN KEY (`becheckorg_id`) REFERENCES `dns_ele_finance_org` (`id`),
  CONSTRAINT `FKepiiia1sk4nk9g809yvl5gijv` FOREIGN KEY (`errorcause_id`) REFERENCES `dns_ele_errorsclass` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_casedata
-- ----------------------------

-- ----------------------------
-- Table structure for dns_currency
-- ----------------------------
DROP TABLE IF EXISTS `dns_currency`;
CREATE TABLE `dns_currency` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(42) default NULL,
  `create_date` datetime default NULL,
  `create_user` bigint(20) default NULL,
  `enabled` bit(1) default '',
  `last_ver` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` bigint(20) default NULL,
  `leaf` bit(1) default '',
  `level` tinyint(4) default '1',
  `name` varchar(42) default NULL,
  `set_year` int(11) default NULL,
  `treepath` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_currency
-- ----------------------------

-- ----------------------------
-- Table structure for dns_data_attribute
-- ----------------------------
DROP TABLE IF EXISTS `dns_data_attribute`;
CREATE TABLE `dns_data_attribute` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(42) default NULL,
  `create_date` datetime default NULL,
  `create_user` bigint(20) default NULL,
  `enabled` bit(1) default '',
  `last_ver` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` bigint(20) default NULL,
  `leaf` bit(1) default '',
  `level` tinyint(4) default '1',
  `name` varchar(42) default NULL,
  `set_year` int(11) default NULL,
  `treepath` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_data_attribute
-- ----------------------------

-- ----------------------------
-- Table structure for dns_date_manage
-- ----------------------------
DROP TABLE IF EXISTS `dns_date_manage`;
CREATE TABLE `dns_date_manage` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `bond_level` varchar(255) default NULL,
  `bond_type` varchar(255) default NULL,
  `coupon_rate` varchar(255) default NULL,
  `deadline` varchar(255) default NULL,
  `deal_code` varchar(255) default NULL,
  `due_date` datetime default NULL,
  `full_name` varchar(255) default NULL,
  `guarantee` varchar(255) default NULL,
  `guarantee_level` varchar(255) default NULL,
  `location` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  `scale` varchar(255) default NULL,
  `special_deadline` varchar(255) default NULL,
  `start_date` datetime default NULL,
  `status` int(11) default NULL,
  `subject_level` varchar(255) default NULL,
  `value_date` datetime default NULL,
  `datamanage_file_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKnspna28nyo00d1olv7x3egv5m` (`datamanage_file_id`),
  CONSTRAINT `FKnspna28nyo00d1olv7x3egv5m` FOREIGN KEY (`datamanage_file_id`) REFERENCES `dns_accessory` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_date_manage
-- ----------------------------

-- ----------------------------
-- Table structure for dns_element
-- ----------------------------
DROP TABLE IF EXISTS `dns_element`;
CREATE TABLE `dns_element` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `class_name` varchar(255) default NULL,
  `code_rule` varchar(255) default NULL,
  `dispmode` int(11) default '0',
  `ele_code` varchar(42) NOT NULL,
  `ele_name` varchar(42) default NULL,
  `ele_source` varchar(42) NOT NULL,
  `enabled` bit(1) default '',
  `from_rg` bit(1) default '\0',
  `max_level` int(11) default '99',
  `set_year` int(11) default NULL,
  `ui_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `UK_lu0ismnoxdpqu3m7chp81i0jk` (`ele_code`),
  KEY `FKr2jv85o6x6dldfhby3eoy2l7i` (`ui_id`),
  CONSTRAINT `FKr2jv85o6x6dldfhby3eoy2l7i` FOREIGN KEY (`ui_id`) REFERENCES `dns_uimanager` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_element
-- ----------------------------
INSERT INTO `dns_element` VALUES ('160', '2017-03-24 23:26:43', '0', '', '', '0', 'ELE', '系统要素', 'VW_ELE_ELEMENT', '', '\0', '0', '0', null);
INSERT INTO `dns_element` VALUES ('162', '2017-03-24 23:27:03', '0', '', '', '0', 'MENU', '菜单信息', 'VW_ELE_MENU', '', '\0', '0', '0', null);
INSERT INTO `dns_element` VALUES ('164', '2017-03-24 23:27:21', '0', '', '', '0', 'ROLE', '角色信息', 'VW_ELE_ROLE', '', '', '0', '0', null);
INSERT INTO `dns_element` VALUES ('166', '2017-03-24 23:27:48', '0', '', '', '0', 'UI', '界面视图', 'VW_ELE_UIMANAGER', '', '\0', '0', '0', null);
INSERT INTO `dns_element` VALUES ('168', '2017-03-24 23:35:22', '0', 'com.aeon.foundation.table.ElePbcOrg', '', '0', 'PBC_ORG', '人行机构', 'dns_ele_pbc_org', '', '', '0', '0', null);
INSERT INTO `dns_element` VALUES ('170', '2017-03-24 23:36:20', '0', 'com.aeon.foundation.table.EleFinanceOrg', '', '0', 'FINANCE_ORG', '金融机构', 'dns_ele_finance_org', '', '', '0', '0', null);
INSERT INTO `dns_element` VALUES ('764', '2017-05-16 15:44:58', '0', 'com.aeon.foundation.table.EleBondTypeBank', '', '0', 'BOND_BANK', '银行债券', 'dns_bond_type_bank', '', '\0', '0', '0', null);
INSERT INTO `dns_element` VALUES ('766', '2017-05-16 15:45:07', '0', 'com.aeon.foundation.table.EleBondTypeEnterprise', '', '0', 'BOND_ENTERPRISE', '企业债券', 'dns_bond_type_enterprise', '', '\0', '0', '0', null);
INSERT INTO `dns_element` VALUES ('770', '2017-05-16 15:49:58', '0', 'com.aeon.foundation.table.EleIssureEnterprise', '', '0', 'ISSURE_ENTERPRISE', '发行人行业', 'dns_issuer_enterprise', '', '\0', '0', '0', null);
INSERT INTO `dns_element` VALUES ('773', '2017-05-16 15:50:29', '0', 'com.aeon.foundation.table.EleEnterpriseNature', '', '0', 'ISSURE_NATURE', '发行人企业性质', 'dns_ele_issuerenterprisenature', '', '\0', '0', '0', null);
INSERT INTO `dns_element` VALUES ('777', '2017-05-16 15:51:55', '0', 'com.aeon.foundation.table.EleErrorsClass', '', '0', 'ERRCLS', '错误原因分类', 'dns_ele_errorsclass', '', '\0', '0', '0', null);
INSERT INTO `dns_element` VALUES ('779', '2017-05-16 15:51:59', '0', 'com.aeon.foundation.table.EleBondRating', '', '0', 'BOND_RATE', '债券评级', 'dns_ele_bondrating', '', '\0', '0', '0', null);
INSERT INTO `dns_element` VALUES ('789', '2017-05-16 15:56:13', '0', 'com.aeon.foundation.table.EleErrorProperty', '', '0', 'ERRPROPERTY', '错误性质', 'dns_ele_errorproperty', '', '\0', '0', '0', null);
INSERT INTO `dns_element` VALUES ('801', '2017-05-16 16:01:25', '0', 'com.aeon.foundation.table.EleSubjectiveRating', '', '0', 'SUB_RATING', '主体评级', 'dns_ele_subjectiverating', '', '\0', '0', '0', null);
INSERT INTO `dns_element` VALUES ('815', '2017-05-16 16:05:58', '0', 'com.aeon.foundation.table.NonfinancialBusiness', '', '0', 'NONFINANCIALBUSS', '非金融企业', 'dns_nonfinancialbusiness', '', '\0', '0', '0', null);
INSERT INTO `dns_element` VALUES ('833', '2017-05-16 16:09:15', '0', 'com.aeon.foundation.table.LawEnforcementOfficers', '', '0', 'LAWENOFFICERS', '人行执法人员', 'dns_lawenforcementofficers', '', '\0', '0', '0', null);
INSERT INTO `dns_element` VALUES ('835', '2017-05-16 16:12:07', '0', 'com.aeon.foundation.table.EleGuarantorRating', '', '0', 'GUA_RATING', '担保人评级', 'dns_ele_guarantorrating', '', '\0', '0', '0', null);
INSERT INTO `dns_element` VALUES ('1423', '2017-05-25 20:39:02', '0', 'com.aeon.foundation.table.InspectType', '', '0', 'INS_TYPE', '检查方式', 'dns_inspect_type', '', '\0', '0', '0', null);
INSERT INTO `dns_element` VALUES ('1450', '2017-05-26 10:27:46', '0', 'com.aeon.foundation.table.FormClassification', '', '0', 'FORM_CLASS', '表单分类', 'dns_form_classification', '', '\0', '0', '0', null);
INSERT INTO `dns_element` VALUES ('1944', '2017-05-27 20:43:51', '0', 'com.aeon.foundation.table.ElePbcOrg', '', '0', 'PBC', '人行机构', 'dns_ele_pbc_org', '', '', '0', '0', null);
INSERT INTO `dns_element` VALUES ('1946', '2017-05-27 20:44:47', '0', 'com.aeon.foundation.table.EleFinanceOrg', '', '0', 'FINANCE', '金融机构', 'dns_ele_finance_org', '', '', '0', '0', null);
INSERT INTO `dns_element` VALUES ('2453', '2017-06-02 18:01:20', '0', 'com.aeon.foundation.table.EleMonitorMode', '', '0', 'Monitor_Mode', '监督方式', 'dns_ele_monitormode', '', '\0', '0', '0', null);
INSERT INTO `dns_element` VALUES ('2710', '2017-06-04 16:02:24', '0', 'com.aeon.foundation.table.ElePbcOrg', '', '0', 'PBC_EXT', '人行机构', 'dns_ele_pbc_org', '', '', '0', '0', null);
INSERT INTO `dns_element` VALUES ('2713', '2017-06-04 16:03:34', '0', 'com.aeon.foundation.table.EleFinanceOrg', '', '0', 'FINANCE_EXT', '金融机构', 'dns_ele_finance_org', '', '', '0', '0', null);

-- ----------------------------
-- Table structure for dns_ele_bankclassification
-- ----------------------------
DROP TABLE IF EXISTS `dns_ele_bankclassification`;
CREATE TABLE `dns_ele_bankclassification` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(42) default NULL,
  `create_date` datetime default NULL,
  `create_user` bigint(20) default NULL,
  `enabled` bit(1) default '',
  `last_ver` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` bigint(20) default NULL,
  `leaf` bit(1) default '',
  `level` tinyint(4) default '1',
  `name` varchar(42) default NULL,
  `set_year` int(11) default NULL,
  `treepath` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  `parent_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKl7v13bavxee3lvx3akm8gfw83` (`parent_id`),
  CONSTRAINT `FKl7v13bavxee3lvx3akm8gfw83` FOREIGN KEY (`parent_id`) REFERENCES `dns_ele_bankclassification` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_ele_bankclassification
-- ----------------------------

-- ----------------------------
-- Table structure for dns_ele_bondrating
-- ----------------------------
DROP TABLE IF EXISTS `dns_ele_bondrating`;
CREATE TABLE `dns_ele_bondrating` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(42) default NULL,
  `create_date` datetime default NULL,
  `create_user` bigint(20) default NULL,
  `enabled` bit(1) default '',
  `last_ver` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` bigint(20) default NULL,
  `leaf` bit(1) default '',
  `level` tinyint(4) default '1',
  `name` varchar(42) default NULL,
  `set_year` int(11) default NULL,
  `treepath` varchar(255) default NULL,
  `remark` longtext,
  `parent_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKrqy7ngemhn7x5ok8ve8htgvub` (`parent_id`),
  CONSTRAINT `FKrqy7ngemhn7x5ok8ve8htgvub` FOREIGN KEY (`parent_id`) REFERENCES `dns_ele_bondrating` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_ele_bondrating
-- ----------------------------

-- ----------------------------
-- Table structure for dns_ele_department
-- ----------------------------
DROP TABLE IF EXISTS `dns_ele_department`;
CREATE TABLE `dns_ele_department` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(42) default NULL,
  `create_date` datetime default NULL,
  `create_user` bigint(20) default NULL,
  `enabled` bit(1) default '',
  `last_ver` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` bigint(20) default NULL,
  `leaf` bit(1) default '',
  `level` tinyint(4) default '1',
  `name` varchar(42) default NULL,
  `set_year` int(11) default NULL,
  `treepath` varchar(255) default NULL,
  `parent_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK3w2t731l8vv69r5nlr7khsa7k` (`parent_id`),
  CONSTRAINT `FK3w2t731l8vv69r5nlr7khsa7k` FOREIGN KEY (`parent_id`) REFERENCES `dns_ele_department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_ele_department
-- ----------------------------

-- ----------------------------
-- Table structure for dns_ele_enterprise
-- ----------------------------
DROP TABLE IF EXISTS `dns_ele_enterprise`;
CREATE TABLE `dns_ele_enterprise` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(42) default NULL,
  `create_date` datetime default NULL,
  `create_user` bigint(20) default NULL,
  `enabled` bit(1) default '',
  `last_ver` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` bigint(20) default NULL,
  `leaf` bit(1) default '',
  `level` tinyint(4) default '1',
  `name` varchar(42) default NULL,
  `set_year` int(11) default NULL,
  `treepath` varchar(255) default NULL,
  `parent_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKosevv2tr050rrvp50ml9d7p50` (`parent_id`),
  CONSTRAINT `FKosevv2tr050rrvp50ml9d7p50` FOREIGN KEY (`parent_id`) REFERENCES `dns_ele_enterprise` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_ele_enterprise
-- ----------------------------

-- ----------------------------
-- Table structure for dns_ele_errorproperty
-- ----------------------------
DROP TABLE IF EXISTS `dns_ele_errorproperty`;
CREATE TABLE `dns_ele_errorproperty` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(42) default NULL,
  `create_date` datetime default NULL,
  `create_user` bigint(20) default NULL,
  `enabled` bit(1) default '',
  `last_ver` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` bigint(20) default NULL,
  `leaf` bit(1) default '',
  `level` tinyint(4) default '1',
  `name` varchar(42) default NULL,
  `set_year` int(11) default NULL,
  `treepath` varchar(255) default NULL,
  `remark` longtext,
  `parent_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK8bd1ejvvlhakdj6fxulvv3cyc` (`parent_id`),
  CONSTRAINT `FK8bd1ejvvlhakdj6fxulvv3cyc` FOREIGN KEY (`parent_id`) REFERENCES `dns_ele_errorproperty` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_ele_errorproperty
-- ----------------------------

-- ----------------------------
-- Table structure for dns_ele_errorsclass
-- ----------------------------
DROP TABLE IF EXISTS `dns_ele_errorsclass`;
CREATE TABLE `dns_ele_errorsclass` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(42) default NULL,
  `create_date` datetime default NULL,
  `create_user` bigint(20) default NULL,
  `enabled` bit(1) default '',
  `last_ver` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` bigint(20) default NULL,
  `leaf` bit(1) default '',
  `level` tinyint(4) default '1',
  `name` varchar(42) default NULL,
  `set_year` int(11) default NULL,
  `treepath` varchar(255) default NULL,
  `remark` longtext,
  `parent_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKdtxw7eo4s5jcpbvjbut3hpe7o` (`parent_id`),
  CONSTRAINT `FKdtxw7eo4s5jcpbvjbut3hpe7o` FOREIGN KEY (`parent_id`) REFERENCES `dns_ele_errorsclass` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_ele_errorsclass
-- ----------------------------

-- ----------------------------
-- Table structure for dns_ele_finance_org
-- ----------------------------
DROP TABLE IF EXISTS `dns_ele_finance_org`;
CREATE TABLE `dns_ele_finance_org` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(42) default NULL,
  `create_date` datetime default NULL,
  `create_user` bigint(20) default NULL,
  `enabled` bit(1) default '',
  `last_ver` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` bigint(20) default NULL,
  `leaf` bit(1) default '',
  `level` tinyint(4) default '1',
  `name` varchar(42) default NULL,
  `set_year` int(11) default NULL,
  `treepath` varchar(255) default NULL,
  `contact_addr` varchar(255) default NULL,
  `contact_mail` varchar(255) default NULL,
  `contact_tel` varchar(255) default NULL,
  `contact_user` varchar(255) default NULL,
  `contcat_zipcode` varchar(255) default NULL,
  `full_name` varchar(255) default NULL,
  `legal_user` varchar(255) default NULL,
  `org_code` varchar(255) default NULL,
  `rg_code` varchar(42) NOT NULL default '000000',
  `area_id` bigint(20) default NULL,
  `parent_id` bigint(20) default NULL,
  `pbc_org_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKt58f2liwwkv39cn1eeimmvsq7` (`area_id`),
  KEY `FK378mdc6rbnl2j44ikuj4xq74r` (`parent_id`),
  KEY `FKi59i73fgwm5bhsbkf5j1sg8wi` (`pbc_org_id`),
  CONSTRAINT `FKi59i73fgwm5bhsbkf5j1sg8wi` FOREIGN KEY (`pbc_org_id`) REFERENCES `dns_ele_pbc_org` (`id`),
  CONSTRAINT `FK378mdc6rbnl2j44ikuj4xq74r` FOREIGN KEY (`parent_id`) REFERENCES `dns_ele_finance_org` (`id`),
  CONSTRAINT `FKt58f2liwwkv39cn1eeimmvsq7` FOREIGN KEY (`area_id`) REFERENCES `dns_area` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_ele_finance_org
-- ----------------------------

-- ----------------------------
-- Table structure for dns_ele_guarantorrating
-- ----------------------------
DROP TABLE IF EXISTS `dns_ele_guarantorrating`;
CREATE TABLE `dns_ele_guarantorrating` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(42) default NULL,
  `create_date` datetime default NULL,
  `create_user` bigint(20) default NULL,
  `enabled` bit(1) default '',
  `last_ver` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` bigint(20) default NULL,
  `leaf` bit(1) default '',
  `level` tinyint(4) default '1',
  `name` varchar(42) default NULL,
  `set_year` int(11) default NULL,
  `treepath` varchar(255) default NULL,
  `remark` longtext,
  `parent_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK2ep6tahvxmnc3o47tjcjjii1y` (`parent_id`),
  CONSTRAINT `FK2ep6tahvxmnc3o47tjcjjii1y` FOREIGN KEY (`parent_id`) REFERENCES `dns_ele_guarantorrating` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_ele_guarantorrating
-- ----------------------------

-- ----------------------------
-- Table structure for dns_ele_issuerenterprisenature
-- ----------------------------
DROP TABLE IF EXISTS `dns_ele_issuerenterprisenature`;
CREATE TABLE `dns_ele_issuerenterprisenature` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(42) default NULL,
  `create_date` datetime default NULL,
  `create_user` bigint(20) default NULL,
  `enabled` bit(1) default '',
  `last_ver` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` bigint(20) default NULL,
  `leaf` bit(1) default '',
  `level` tinyint(4) default '1',
  `name` varchar(42) default NULL,
  `set_year` int(11) default NULL,
  `treepath` varchar(255) default NULL,
  `remark` longtext,
  `parent_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKqo2rn427pcmvsrna7igue5cy` (`parent_id`),
  CONSTRAINT `FKqo2rn427pcmvsrna7igue5cy` FOREIGN KEY (`parent_id`) REFERENCES `dns_ele_issuerenterprisenature` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_ele_issuerenterprisenature
-- ----------------------------

-- ----------------------------
-- Table structure for dns_ele_monitormode
-- ----------------------------
DROP TABLE IF EXISTS `dns_ele_monitormode`;
CREATE TABLE `dns_ele_monitormode` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(42) default NULL,
  `create_date` datetime default NULL,
  `create_user` bigint(20) default NULL,
  `enabled` bit(1) default '',
  `last_ver` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` bigint(20) default NULL,
  `leaf` bit(1) default '',
  `level` tinyint(4) default '1',
  `name` varchar(42) default NULL,
  `set_year` int(11) default NULL,
  `treepath` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  `parent_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKlftma1qn9fl4wocoiumfffc91` (`parent_id`),
  CONSTRAINT `FKlftma1qn9fl4wocoiumfffc91` FOREIGN KEY (`parent_id`) REFERENCES `dns_ele_monitormode` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_ele_monitormode
-- ----------------------------

-- ----------------------------
-- Table structure for dns_ele_pbc_org
-- ----------------------------
DROP TABLE IF EXISTS `dns_ele_pbc_org`;
CREATE TABLE `dns_ele_pbc_org` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(42) default NULL,
  `create_date` datetime default NULL,
  `create_user` bigint(20) default NULL,
  `enabled` bit(1) default '',
  `last_ver` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` bigint(20) default NULL,
  `leaf` bit(1) default '',
  `level` tinyint(4) default '1',
  `name` varchar(42) default NULL,
  `set_year` int(11) default NULL,
  `treepath` varchar(255) default NULL,
  `contact_addr` varchar(255) default NULL,
  `contact_mail` varchar(255) default NULL,
  `contact_tel` varchar(255) default NULL,
  `contact_user` varchar(255) default NULL,
  `full_name` varchar(255) default NULL,
  `rg_code` varchar(42) NOT NULL default '000000',
  `area_id` bigint(20) default NULL,
  `parent_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKfhlaaf6busesmckd0g51dqpin` (`area_id`),
  KEY `FKeqxce5bu957uqfaa9h3uv2oio` (`parent_id`),
  CONSTRAINT `FKeqxce5bu957uqfaa9h3uv2oio` FOREIGN KEY (`parent_id`) REFERENCES `dns_ele_pbc_org` (`id`),
  CONSTRAINT `FKfhlaaf6busesmckd0g51dqpin` FOREIGN KEY (`area_id`) REFERENCES `dns_area` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_ele_pbc_org
-- ----------------------------

-- ----------------------------
-- Table structure for dns_ele_subjectiverating
-- ----------------------------
DROP TABLE IF EXISTS `dns_ele_subjectiverating`;
CREATE TABLE `dns_ele_subjectiverating` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(42) default NULL,
  `create_date` datetime default NULL,
  `create_user` bigint(20) default NULL,
  `enabled` bit(1) default '',
  `last_ver` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` bigint(20) default NULL,
  `leaf` bit(1) default '',
  `level` tinyint(4) default '1',
  `name` varchar(42) default NULL,
  `set_year` int(11) default NULL,
  `treepath` varchar(255) default NULL,
  `remark` longtext,
  `parent_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKtbcvieijjgitq85lcenldj4x8` (`parent_id`),
  CONSTRAINT `FKtbcvieijjgitq85lcenldj4x8` FOREIGN KEY (`parent_id`) REFERENCES `dns_ele_subjectiverating` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_ele_subjectiverating
-- ----------------------------

-- ----------------------------
-- Table structure for dns_enforce_material
-- ----------------------------
DROP TABLE IF EXISTS `dns_enforce_material`;
CREATE TABLE `dns_enforce_material` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `create_date` datetime default NULL,
  `create_user` varchar(255) default NULL,
  `enforceName` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  `EnforceModel_id` bigint(20) default NULL,
  `inspectProject_id` bigint(20) default NULL,
  `modelFile_id` bigint(20) default NULL,
  `processFile_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK8swwj5jw2x1m2t4bn4t1y5jx6` (`EnforceModel_id`),
  KEY `FKf2bfi4nkghiwu355owaux3cyw` (`inspectProject_id`),
  KEY `FKhatq2rjl2wclq0mefl1ab4o42` (`modelFile_id`),
  KEY `FK3ehot8x1i2j8yp7ndp2ccm0sa` (`processFile_id`),
  CONSTRAINT `FK3ehot8x1i2j8yp7ndp2ccm0sa` FOREIGN KEY (`processFile_id`) REFERENCES `dns_accessory` (`id`),
  CONSTRAINT `FK8swwj5jw2x1m2t4bn4t1y5jx6` FOREIGN KEY (`EnforceModel_id`) REFERENCES `dns_enforce_model` (`id`),
  CONSTRAINT `FKf2bfi4nkghiwu355owaux3cyw` FOREIGN KEY (`inspectProject_id`) REFERENCES `dns_inspect_project` (`id`),
  CONSTRAINT `FKhatq2rjl2wclq0mefl1ab4o42` FOREIGN KEY (`modelFile_id`) REFERENCES `dns_accessory` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_enforce_material
-- ----------------------------

-- ----------------------------
-- Table structure for dns_enforce_model
-- ----------------------------
DROP TABLE IF EXISTS `dns_enforce_model`;
CREATE TABLE `dns_enforce_model` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `enforceModelCode` varchar(255) default NULL,
  `input` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  `modelFile_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKpflbovtfc5xjnha61jto29l2w` (`modelFile_id`),
  CONSTRAINT `FKpflbovtfc5xjnha61jto29l2w` FOREIGN KEY (`modelFile_id`) REFERENCES `dns_accessory` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_enforce_model
-- ----------------------------

-- ----------------------------
-- Table structure for dns_enumerate
-- ----------------------------
DROP TABLE IF EXISTS `dns_enumerate`;
CREATE TABLE `dns_enumerate` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(42) default NULL,
  `codedesc` varchar(255) default NULL,
  `field` varchar(42) default NULL,
  `fieldname` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  `sortno` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_enumerate
-- ----------------------------
INSERT INTO `dns_enumerate` VALUES ('1', null, '0', '0', '未知', 'SEX', '性别', '', '1');
INSERT INTO `dns_enumerate` VALUES ('2', null, '0', '1', '男', 'SEX', '性别', '', '2');
INSERT INTO `dns_enumerate` VALUES ('3', null, '0', '2', '女', 'SEX', '性别', '', '3');
INSERT INTO `dns_enumerate` VALUES ('4', null, '0', '0', '激活', 'LOCKED', '锁定', '', '2');
INSERT INTO `dns_enumerate` VALUES ('5', null, '0', '1', '锁定', 'LOCKED', '锁定', '', '1');
INSERT INTO `dns_enumerate` VALUES ('10', null, '0', '0', '只读', 'EDITMODE', '编辑模式', '', '1');
INSERT INTO `dns_enumerate` VALUES ('11', null, '0', '1', '可编辑', 'EDITMODE', '编辑模式', '', '2');
INSERT INTO `dns_enumerate` VALUES ('12', null, '0', '0', '禁用', 'ENABLED', '启用状态', '', '1');
INSERT INTO `dns_enumerate` VALUES ('13', null, '0', '1', '启用', 'ENABLED', '启用状态', '', '2');
INSERT INTO `dns_enumerate` VALUES ('17', null, '0', '1', '系统菜单', 'MENUTYPE', '菜单类型', '', '1');
INSERT INTO `dns_enumerate` VALUES ('18', null, '0', '0', '业务菜单', 'MENUTYPE', '菜单类型', '', '2');
INSERT INTO `dns_enumerate` VALUES ('24', null, '0', '1', '按钮', 'CMPTYPE', 'UI元素类型', '', '1');
INSERT INTO `dns_enumerate` VALUES ('25', null, '0', '2', '表单输入(textField|textArea|comboBox|checkBox|radioBox|htmlEditor)', 'CMPTYPE', 'UI元素类型', '', '2');
INSERT INTO `dns_enumerate` VALUES ('26', null, '0', '3', '面板容器(Panel|TabPanel|GridPanel|FormPanel|TreePanel)', 'CMPTYPE', 'UI组件类型', '', '3');
INSERT INTO `dns_enumerate` VALUES ('27', null, '0', '1', '禁用', 'PARTAUTHTYPE', 'UI元素授权类型', '', '2');
INSERT INTO `dns_enumerate` VALUES ('28', null, '0', '3', '只读', 'PARTAUTHTYPE', 'UI元素授权类型', '', '3');
INSERT INTO `dns_enumerate` VALUES ('29', null, '0', '4', '编辑', 'PARTAUTHTYPE', 'UI元素授权类型', '', '4');
INSERT INTO `dns_enumerate` VALUES ('30', null, '0', '5', '显示', 'PARTAUTHTYPE', 'UI元素授权类型', '', '5');
INSERT INTO `dns_enumerate` VALUES ('31', null, '0', '6', '隐藏', 'PARTAUTHTYPE', 'UI元素授权类型', '', '6');
INSERT INTO `dns_enumerate` VALUES ('32', null, '0', '0', '默认', 'PARTAUTHTYPE', 'UI元素授权类型', '', '1');
INSERT INTO `dns_enumerate` VALUES ('36', null, '0', '4', 'HTML元素', 'CMPTYPE', 'UI元素类型', 'jQuery用', '4');
INSERT INTO `dns_enumerate` VALUES ('37', null, '0', '0', '否', 'YESNO', '是否', '', '1');
INSERT INTO `dns_enumerate` VALUES ('38', null, '0', '1', '是', 'YESNO', '是否', '', '2');
INSERT INTO `dns_enumerate` VALUES ('39', null, '0', 'textfield', '文本框', 'XTYPE', '控件类型', '', '1');
INSERT INTO `dns_enumerate` VALUES ('40', null, '0', 'combofield', '下拉列表框', 'XTYPE', '控件类型', '', '2');
INSERT INTO `dns_enumerate` VALUES ('41', null, '0', 'treefield', '下拉树', 'XTYPE', '控件类型', '', '3');
INSERT INTO `dns_enumerate` VALUES ('42', null, '0', 'chkfield', '复选框', 'XTYPE', '控件类型', '', '4');
INSERT INTO `dns_enumerate` VALUES ('43', null, '0', 'radiofield', '单选框', 'XTYPE', '控件类型', '', '5');
INSERT INTO `dns_enumerate` VALUES ('44', null, '0', 'datefield', '日期选择框', 'XTYPE', '控件类型', '', '6');
INSERT INTO `dns_enumerate` VALUES ('45', null, '0', 'timefield', '时间录入框', 'XTYPE', '控件类型', '', '7');
INSERT INTO `dns_enumerate` VALUES ('46', null, '0', 'textarea', '多行文本框', 'XTYPE', '控件类型', '', '8');
INSERT INTO `dns_enumerate` VALUES ('47', null, '0', 'label', '标题标签', 'XTYPE', '控件类型', '', '9');
INSERT INTO `dns_enumerate` VALUES ('48', null, '0', 'custnumberfield', '数字编辑框', 'XTYPE', '控件类型', '', '10');
INSERT INTO `dns_enumerate` VALUES ('49', null, '0', 'moneyfield', '货币数字编辑框', 'XTYPE', '控件类型', '', '11');
INSERT INTO `dns_enumerate` VALUES ('50', null, '0', 'percentfield', '百分比数字编辑框', 'XTYPE', '控件类型', '', '12');
INSERT INTO `dns_enumerate` VALUES ('51', null, '0', 'hidden', '隐藏字段', 'XTYPE', '控件类型', '', '13');
INSERT INTO `dns_enumerate` VALUES ('52', null, '0', 'button', '按钮', 'XTYPE', '控件类型', '', '15');
INSERT INTO `dns_enumerate` VALUES ('53', null, '0', 'colmodel', '表格列', 'XTYPE', '控件类型', '', '20');
INSERT INTO `dns_enumerate` VALUES ('256', '2017-03-30 18:13:58', '0', '2016', '2016年', 'YEAR', '年度', null, '0');
INSERT INTO `dns_enumerate` VALUES ('258', '2017-03-30 18:14:09', '0', '2017', '2017年', 'YEAR', '年度', null, '1');
INSERT INTO `dns_enumerate` VALUES ('260', '2017-03-30 18:14:25', '0', '2018', '2018年', 'YEAR', '年度', null, '2');
INSERT INTO `dns_enumerate` VALUES ('2427', '2017-06-02 16:47:05', '0', '0', '否', 'ISOPEN', '是否公开', null, '0');
INSERT INTO `dns_enumerate` VALUES ('2429', '2017-06-02 16:47:36', '0', '1', '是', 'ISOPEN', '是否公开', null, '1');

-- ----------------------------
-- Table structure for dns_error_rate
-- ----------------------------
DROP TABLE IF EXISTS `dns_error_rate`;
CREATE TABLE `dns_error_rate` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `avgErrorMoney` double default NULL,
  `create_date` datetime default NULL,
  `create_user` varchar(255) default NULL,
  `form_code` varchar(255) default NULL,
  `form_name` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` varchar(255) default NULL,
  `quotaNum` int(11) default NULL,
  `sumErrorMoney` double default NULL,
  `inspectProject_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKmbv3yl3l8eqv5uerc07ds6fy7` (`inspectProject_id`),
  CONSTRAINT `FKmbv3yl3l8eqv5uerc07ds6fy7` FOREIGN KEY (`inspectProject_id`) REFERENCES `dns_inspect_project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_error_rate
-- ----------------------------

-- ----------------------------
-- Table structure for dns_error_rate_detail
-- ----------------------------
DROP TABLE IF EXISTS `dns_error_rate_detail`;
CREATE TABLE `dns_error_rate_detail` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `all_count` int(11) default NULL,
  `checked_data` varchar(255) default NULL,
  `currency` varchar(255) default NULL,
  `data_attribute` varchar(255) default NULL,
  `error_count` int(11) default NULL,
  `error_rate` varchar(255) default NULL,
  `index_code` varchar(255) default NULL,
  `index_name` varchar(255) default NULL,
  `is_exclusion` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  `select_count` int(11) default NULL,
  `uncheck_data` varchar(255) default NULL,
  `wrong_amount` varchar(255) default NULL,
  `errorrate_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK4l8sh6bwg7tx5uw7pa6fuso99` (`errorrate_id`),
  CONSTRAINT `FK4l8sh6bwg7tx5uw7pa6fuso99` FOREIGN KEY (`errorrate_id`) REFERENCES `dns_error_rate` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_error_rate_detail
-- ----------------------------

-- ----------------------------
-- Table structure for dns_evaluation_score
-- ----------------------------
DROP TABLE IF EXISTS `dns_evaluation_score`;
CREATE TABLE `dns_evaluation_score` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `business_development` double default NULL,
  `business_innovate` double default NULL,
  `business_manage` double default NULL,
  `external_evaluation` double default NULL,
  `financial_name` varchar(255) default NULL,
  `humanDecency_evaluation` double default NULL,
  `score_sum` double default NULL,
  `type` varchar(255) default NULL,
  `year` int(11) default NULL,
  `zoning` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_evaluation_score
-- ----------------------------

-- ----------------------------
-- Table structure for dns_evalution_score_detail
-- ----------------------------
DROP TABLE IF EXISTS `dns_evalution_score_detail`;
CREATE TABLE `dns_evalution_score_detail` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `bond_level` varchar(255) default NULL,
  `bond_type` varchar(255) default NULL,
  `coupon_rate` varchar(255) default NULL,
  `deadline` varchar(255) default NULL,
  `deal_code` varchar(255) default NULL,
  `due_date` datetime default NULL,
  `enterprise_nature` varchar(255) default NULL,
  `full_name` varchar(255) default NULL,
  `fund_use` varchar(255) default NULL,
  `industry` varchar(255) default NULL,
  `location` varchar(255) default NULL,
  `main_money` varchar(255) default NULL,
  `main_underwriter` varchar(255) default NULL,
  `scale` varchar(255) default NULL,
  `secondary_money` varchar(255) default NULL,
  `secondary_underwriter` varchar(255) default NULL,
  `special_deadline` varchar(255) default NULL,
  `start_date` datetime default NULL,
  `status` int(11) default NULL,
  `subject_level` varchar(255) default NULL,
  `value_date` datetime default NULL,
  `datamanage_file_id` bigint(20) default NULL,
  `evaluationScore_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK8ac0ke16vo1ytgdhm755i53un` (`datamanage_file_id`),
  KEY `FK7vlpohtrbk2n5g13bnar55p4s` (`evaluationScore_id`),
  CONSTRAINT `FK7vlpohtrbk2n5g13bnar55p4s` FOREIGN KEY (`evaluationScore_id`) REFERENCES `dns_evaluation_score` (`id`),
  CONSTRAINT `FK8ac0ke16vo1ytgdhm755i53un` FOREIGN KEY (`datamanage_file_id`) REFERENCES `dns_accessory` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_evalution_score_detail
-- ----------------------------

-- ----------------------------
-- Table structure for dns_exist_problem
-- ----------------------------
DROP TABLE IF EXISTS `dns_exist_problem`;
CREATE TABLE `dns_exist_problem` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `checkMan` varchar(255) default NULL,
  `existProblem` bit(1) default NULL,
  `form_model_code` varchar(255) default NULL,
  `inputMan` varchar(255) default NULL,
  `problemDescribe` varchar(255) default NULL,
  `problemNumber` varchar(255) default NULL,
  `inspectProject_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKj70l6rpp87qx84kbe1k8ramhn` (`inspectProject_id`),
  CONSTRAINT `FKj70l6rpp87qx84kbe1k8ramhn` FOREIGN KEY (`inspectProject_id`) REFERENCES `dns_inspect_project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_exist_problem
-- ----------------------------

-- ----------------------------
-- Table structure for dns_filemodel
-- ----------------------------
DROP TABLE IF EXISTS `dns_filemodel`;
CREATE TABLE `dns_filemodel` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `fileModelCode` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  `file_model_file_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK3i3k5dgk4cknmunqkp35crxid` (`file_model_file_id`),
  CONSTRAINT `FK3i3k5dgk4cknmunqkp35crxid` FOREIGN KEY (`file_model_file_id`) REFERENCES `dns_accessory` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_filemodel
-- ----------------------------

-- ----------------------------
-- Table structure for dns_form_classification
-- ----------------------------
DROP TABLE IF EXISTS `dns_form_classification`;
CREATE TABLE `dns_form_classification` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(42) default NULL,
  `create_date` datetime default NULL,
  `create_user` bigint(20) default NULL,
  `enabled` bit(1) default '',
  `last_ver` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` bigint(20) default NULL,
  `leaf` bit(1) default '',
  `level` tinyint(4) default '1',
  `name` varchar(42) default NULL,
  `set_year` int(11) default NULL,
  `treepath` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_form_classification
-- ----------------------------

-- ----------------------------
-- Table structure for dns_form_detail
-- ----------------------------
DROP TABLE IF EXISTS `dns_form_detail`;
CREATE TABLE `dns_form_detail` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `all_count` int(11) default NULL,
  `checked_date` varchar(255) default NULL,
  `currency` varchar(255) default NULL,
  `date_attribute` varchar(255) default NULL,
  `error_count` int(11) default NULL,
  `error_rate` varchar(255) default NULL,
  `form_code` varchar(255) default NULL,
  `index_code` varchar(255) default NULL,
  `index_name` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  `safe` varchar(255) default NULL,
  `select_count` int(11) default NULL,
  `status` bit(1) default NULL,
  `uncheck_date` varchar(255) default NULL,
  `wrong_amount` varchar(255) default NULL,
  `form_model_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKidh18mqmfx9qwmkgdd2rl3eic` (`form_model_id`),
  CONSTRAINT `FKidh18mqmfx9qwmkgdd2rl3eic` FOREIGN KEY (`form_model_id`) REFERENCES `dns_form_model` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_form_detail
-- ----------------------------

-- ----------------------------
-- Table structure for dns_form_model
-- ----------------------------
DROP TABLE IF EXISTS `dns_form_model`;
CREATE TABLE `dns_form_model` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `form_code` varchar(255) default NULL,
  `form_name` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  `formClassification_id` bigint(20) default NULL,
  `form_model_file_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKfgbpanikiwvoq6v5k1q59p8on` (`formClassification_id`),
  KEY `FKqar4j0tnn5va569ukeil6s4in` (`form_model_file_id`),
  CONSTRAINT `FKqar4j0tnn5va569ukeil6s4in` FOREIGN KEY (`form_model_file_id`) REFERENCES `dns_accessory` (`id`),
  CONSTRAINT `FKfgbpanikiwvoq6v5k1q59p8on` FOREIGN KEY (`formClassification_id`) REFERENCES `dns_form_classification` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_form_model
-- ----------------------------

-- ----------------------------
-- Table structure for dns_informationdisclosure
-- ----------------------------
DROP TABLE IF EXISTS `dns_informationdisclosure`;
CREATE TABLE `dns_informationdisclosure` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `release_date` datetime default NULL,
  `release_people` varchar(255) default NULL,
  `release_unit` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  `sendee` varchar(255) default NULL,
  `status_check` int(11) default NULL,
  `status_submit` int(11) default NULL,
  `title` varchar(255) default NULL,
  `accessory_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK2nibie9tf18mr2y8law3f6cob` (`accessory_id`),
  CONSTRAINT `FK2nibie9tf18mr2y8law3f6cob` FOREIGN KEY (`accessory_id`) REFERENCES `dns_accessory` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_informationdisclosure
-- ----------------------------

-- ----------------------------
-- Table structure for dns_information_classification
-- ----------------------------
DROP TABLE IF EXISTS `dns_information_classification`;
CREATE TABLE `dns_information_classification` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_information_classification
-- ----------------------------

-- ----------------------------
-- Table structure for dns_inspect_plan
-- ----------------------------
DROP TABLE IF EXISTS `dns_inspect_plan`;
CREATE TABLE `dns_inspect_plan` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `bgn_date` datetime default NULL,
  `create_date` datetime default NULL,
  `create_user` varchar(255) default NULL,
  `end_date` datetime default NULL,
  `inspect_num` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` varchar(255) default NULL,
  `plan_status` int(11) default '1',
  `remark` varchar(255) default NULL,
  `rg_code` varchar(255) default NULL,
  `set_year` varchar(255) default NULL,
  `pbc_org_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK2obhfhajwkoq3c7asllmnq0br` (`pbc_org_id`),
  CONSTRAINT `FK2obhfhajwkoq3c7asllmnq0br` FOREIGN KEY (`pbc_org_id`) REFERENCES `dns_ele_pbc_org` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_inspect_plan
-- ----------------------------

-- ----------------------------
-- Table structure for dns_inspect_plan_detail
-- ----------------------------
DROP TABLE IF EXISTS `dns_inspect_plan_detail`;
CREATE TABLE `dns_inspect_plan_detail` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `remark` varchar(255) default NULL,
  `fin_org_id` bigint(20) default NULL,
  `inspect_type_id` bigint(20) default NULL,
  `plan_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK5ykqpl3l4hfuwetu3m3nvnoob` (`fin_org_id`),
  KEY `FK7cqktds86tnr7gyjtah2f2i08` (`inspect_type_id`),
  KEY `FKjxqfvwjqib9ffd4bof67kcm5l` (`plan_id`),
  CONSTRAINT `FKjxqfvwjqib9ffd4bof67kcm5l` FOREIGN KEY (`plan_id`) REFERENCES `dns_inspect_plan` (`id`),
  CONSTRAINT `FK5ykqpl3l4hfuwetu3m3nvnoob` FOREIGN KEY (`fin_org_id`) REFERENCES `dns_ele_finance_org` (`id`),
  CONSTRAINT `FK7cqktds86tnr7gyjtah2f2i08` FOREIGN KEY (`inspect_type_id`) REFERENCES `dns_inspect_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_inspect_plan_detail
-- ----------------------------

-- ----------------------------
-- Table structure for dns_inspect_project
-- ----------------------------
DROP TABLE IF EXISTS `dns_inspect_project`;
CREATE TABLE `dns_inspect_project` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `bgn_date` datetime default NULL,
  `changed` int(11) default NULL,
  `create_date` datetime default NULL,
  `dataTime` datetime default NULL,
  `end_date` datetime default NULL,
  `form_model` varchar(255) default NULL,
  `form_model_id` varchar(255) default NULL,
  `inspect_leader` varchar(255) default NULL,
  `inspect_main` varchar(255) default NULL,
  `inspect_user` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `plan_detail_id` bigint(20) default NULL,
  `punish` int(11) default NULL,
  `rg_code` varchar(255) default NULL,
  `status` int(11) default NULL,
  `fin_org_id` bigint(20) default NULL,
  `inspectPlan_id` bigint(20) default NULL,
  `inspect_type_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK59wmtp0wtgc29ppa8xph0coi4` (`fin_org_id`),
  KEY `FKtix95n8pl6qaq28mtu36pvrs` (`inspectPlan_id`),
  KEY `FK12vu48hurgyj5ouuxsr81ot9l` (`inspect_type_id`),
  CONSTRAINT `FK12vu48hurgyj5ouuxsr81ot9l` FOREIGN KEY (`inspect_type_id`) REFERENCES `dns_inspect_type` (`id`),
  CONSTRAINT `FK59wmtp0wtgc29ppa8xph0coi4` FOREIGN KEY (`fin_org_id`) REFERENCES `dns_ele_finance_org` (`id`),
  CONSTRAINT `FKtix95n8pl6qaq28mtu36pvrs` FOREIGN KEY (`inspectPlan_id`) REFERENCES `dns_inspect_plan` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_inspect_project
-- ----------------------------

-- ----------------------------
-- Table structure for dns_inspect_type
-- ----------------------------
DROP TABLE IF EXISTS `dns_inspect_type`;
CREATE TABLE `dns_inspect_type` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(42) default NULL,
  `create_date` datetime default NULL,
  `create_user` bigint(20) default NULL,
  `enabled` bit(1) default '',
  `last_ver` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` bigint(20) default NULL,
  `leaf` bit(1) default '',
  `level` tinyint(4) default '1',
  `name` varchar(42) default NULL,
  `set_year` int(11) default NULL,
  `treepath` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_inspect_type
-- ----------------------------

-- ----------------------------
-- Table structure for dns_issuer_enterprise
-- ----------------------------
DROP TABLE IF EXISTS `dns_issuer_enterprise`;
CREATE TABLE `dns_issuer_enterprise` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(42) default NULL,
  `create_date` datetime default NULL,
  `create_user` bigint(20) default NULL,
  `enabled` bit(1) default '',
  `last_ver` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` bigint(20) default NULL,
  `leaf` bit(1) default '',
  `level` tinyint(4) default '1',
  `name` varchar(42) default NULL,
  `set_year` int(11) default NULL,
  `treepath` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  `parent_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKb41c22t7uggqgmftwt89kqk7j` (`parent_id`),
  CONSTRAINT `FKb41c22t7uggqgmftwt89kqk7j` FOREIGN KEY (`parent_id`) REFERENCES `dns_issuer_enterprise` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_issuer_enterprise
-- ----------------------------

-- ----------------------------
-- Table structure for dns_lawenforcementofficers
-- ----------------------------
DROP TABLE IF EXISTS `dns_lawenforcementofficers`;
CREATE TABLE `dns_lawenforcementofficers` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(42) default NULL,
  `create_date` datetime default NULL,
  `create_user` bigint(20) default NULL,
  `enabled` bit(1) default '',
  `last_ver` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` bigint(20) default NULL,
  `leaf` bit(1) default '',
  `level` tinyint(4) default '1',
  `name` varchar(42) default NULL,
  `set_year` int(11) default NULL,
  `treepath` varchar(255) default NULL,
  `contactway` varchar(255) default NULL,
  `institution` varchar(255) default NULL,
  `lawEnforcementCertificate` int(11) default NULL,
  `post` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  `rg_code` varchar(255) default NULL,
  `status` int(11) default NULL,
  `parent_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK8469k88evun0yrmdwpehrfd10` (`parent_id`),
  CONSTRAINT `FK8469k88evun0yrmdwpehrfd10` FOREIGN KEY (`parent_id`) REFERENCES `dns_lawenforcementofficers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_lawenforcementofficers
-- ----------------------------

-- ----------------------------
-- Table structure for dns_menu
-- ----------------------------
DROP TABLE IF EXISTS `dns_menu`;
CREATE TABLE `dns_menu` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `dispName` varchar(255) default NULL,
  `display` bit(1) default '',
  `info` varchar(255) default NULL,
  `manageTypes` varchar(42) default '0,1',
  `menuCode` varchar(255) default NULL,
  `menuName` varchar(255) default NULL,
  `sequence` int(11) NOT NULL,
  `type` varchar(255) default NULL,
  `mg_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `UK_9sej13rj4uxyl4f7775l1s0mc` (`menuCode`),
  KEY `FKjbc7iil91jetd12aimv2u9g19` (`mg_id`),
  CONSTRAINT `FKjbc7iil91jetd12aimv2u9g19` FOREIGN KEY (`mg_id`) REFERENCES `dns_menugroup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_menu
-- ----------------------------
INSERT INTO `dns_menu` VALUES ('14', '2017-06-09 17:20:57', '0', '欢迎页面', '\0', null, '0,1', 'ROLE_ADMIN_INDEX', '系统首页', '0', 'MANAGE', '13');
INSERT INTO `dns_menu` VALUES ('16', '2017-06-09 17:20:57', '0', '系统提示页', '\0', null, '0,1', 'ROLE_ADMIN_SYS_TIP', '系统提示页', '0', 'MANAGE', '13');
INSERT INTO `dns_menu` VALUES ('23', '2017-06-09 17:20:57', '0', '区域用户功能授权', '', null, '0,1', 'ROLE_REGION_OPERATE_MANAGE', '管理员操作授权', '2', 'MANAGE', '22');
INSERT INTO `dns_menu` VALUES ('30', '2017-06-09 17:20:58', '0', '区划关系授权', '', null, '0,1', 'ROLE_REGION_RELATION_MANAGE', '区划关系授权', '9', 'MANAGE', '22');
INSERT INTO `dns_menu` VALUES ('33', '2017-06-09 17:20:58', '0', '区划启用及管理员配置', '', null, '0,1', 'ROLE_REGION_MANAGE', '管理员维护', '1', 'MANAGE', '22');
INSERT INTO `dns_menu` VALUES ('38', '2017-06-09 17:20:58', '0', '菜单管理', '', null, '0,1', 'ROLE_MENU_MANAGE', '菜单管理', '5', 'MANAGE', '22');
INSERT INTO `dns_menu` VALUES ('42', '2017-06-09 17:20:58', '0', '界面视图管理', '', null, '0,1', 'ROLE_UI_MANAGE', '界面视图管理', '1', 'MANAGE', '41');
INSERT INTO `dns_menu` VALUES ('55', '2017-06-09 17:20:59', '0', '角色功能授权', '', null, '0,1', 'ROLE_ROLE_MENU', '角色菜单授权', '7', 'MANAGE', '22');
INSERT INTO `dns_menu` VALUES ('58', '2017-06-09 17:20:59', '0', '角色管理', '', null, '0,1', 'ROLE_ROLE_MANAGE', '角色管理', '4', 'MANAGE', '22');
INSERT INTO `dns_menu` VALUES ('66', '2017-06-09 17:21:00', '0', '业务用户管理', '', null, '0,1', 'ROLE_USER_MANAGE', '用户管理', '3', 'MANAGE', '22');
INSERT INTO `dns_menu` VALUES ('72', '2017-06-09 17:21:00', '0', '操作管理', '', null, '0,1', 'ROLE_OPERATE_MANAGE', '操作管理', '6', 'MANAGE', '22');
INSERT INTO `dns_menu` VALUES ('77', '2017-06-09 17:21:00', '0', '机构管理', '', null, '0,1', 'ROLE_ORGTYPE_MANAGE', '机构管理', '10', 'MANAGE', '22');
INSERT INTO `dns_menu` VALUES ('83', '2017-06-09 17:21:01', '0', '权限组管理', '', null, '0,1', 'ROLE_PART_MANAGE', '权限组管理', '11', 'MANAGE', '22');
INSERT INTO `dns_menu` VALUES ('86', '2017-06-09 17:21:01', '0', '枚举值管理', '', null, '0,1', 'ROLE_ENUMERATE_MANAGE', '枚举值管理', '1', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('92', '2017-06-09 17:21:01', '0', '金融机构', '', null, '0,1', 'ROLE_FINANCE_ORG_MANAGE', '金融机构管理', '3', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('98', '2017-06-09 17:21:01', '0', '系统要素管理', '', null, '0,1', 'ROLE_ELEMENT_MANAGE', '系统要素管理', '3', 'MANAGE', '41');
INSERT INTO `dns_menu` VALUES ('105', '2017-06-09 17:21:02', '0', '人行机构', '', null, '0,1', 'ROLE_PBC_ORG_MANAGE', '人行机构管理', '2', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('111', '2017-06-09 17:21:02', '0', '银行债券', '', null, '0,1', 'ROLE_BONDTYPE_BANK_MANAGE', '银行债券管理', '31', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('117', '2017-06-09 17:21:02', '0', '企业债券', '', null, '0,1', 'ROLE_BONDTYPE_ENTERPRISE_MANAGE', '企业债券管理', '32', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('123', '2017-06-09 17:21:03', '0', '执法材料模板库', '', null, '0,1', 'ROLE_ENFORCEMODEL_MANAGE', '执法模板库', '8', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('126', '2017-06-09 17:21:03', '0', '论坛', '', null, '0,1', 'ROLE_CASEDATA_MAINTENANCE_MANAGE', '案例维护', '0', 'MANAGE', '125');
INSERT INTO `dns_menu` VALUES ('129', '2017-06-09 17:21:03', '0', '案例维护及论坛', '', null, '0,1', 'ROLE_CASEDATA_MANAGE', '案例库登记', '0', 'MANAGE', '125');
INSERT INTO `dns_menu` VALUES ('134', '2017-06-09 17:21:03', '0', '文档模板库', '', null, '0,1', 'ROLE_FILEMODEL_MANAGE', '文档模板库', '5', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('139', '2017-06-09 17:21:03', '0', '检查表单模板库', '', null, '0,1', 'ROLE_FORMMODEL_MANAGE', '表单模板库', '6', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('143', '2017-06-09 17:21:04', '0', '制度库', '', null, '0,1', 'ROLE_SYSTEM_LIBRARY_MANAGE', '制度库', '0', 'MANAGE', '142');
INSERT INTO `dns_menu` VALUES ('149', '2017-06-09 17:21:04', '0', '年度总结', '', null, '0,1', 'ROLE_ANNUAL_REVIEW_MANAGE', '年度总结', '2', 'MANAGE', '148');
INSERT INTO `dns_menu` VALUES ('151', '2017-06-09 17:21:04', '0', '人行业务处室', '', null, '0,1', 'ROLE_DEPARTMENT_MANAGE', '部门管理', '4', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('154', '2017-06-09 17:21:04', '0', '材料管理', '', null, '0,1', 'ROLE_ENFORCEMATERIAL_MANAGE', '材料管理', '5', 'MANAGE', '153');
INSERT INTO `dns_menu` VALUES ('156', '2017-06-09 17:21:04', '0', '检查项目', '', null, '0,1', 'ROLE_INSPECTPROJECT_MANAGE', '检查项目', '3', 'MANAGE', '153');
INSERT INTO `dns_menu` VALUES ('158', '2017-06-09 17:21:04', '0', '检查计划', '', null, '0,1', 'ROLE_INSPECTPLAN_MANAGE', '检查计划', '1', 'MANAGE', '153');
INSERT INTO `dns_menu` VALUES ('160', '2017-06-09 17:21:04', '0', '自查报告', '', null, '0,1', 'ROLE_SELFREPORT_MANAGE', '自查报告', '1', 'MANAGE', '148');
INSERT INTO `dns_menu` VALUES ('166', '2017-06-09 17:21:04', '0', '非现场监督项目', '', null, '0,1', 'ROLE_OFFSITESURVEILLANCE_MANAGE', '非现场监督项目', '1', 'MANAGE', '165');
INSERT INTO `dns_menu` VALUES ('168', '2017-06-09 17:21:04', '0', '审核非现场监督项目', '', null, '0,1', 'ROLE_OFFSITESURVEILLANCE_CHECK_MANAGE', '审核非现场监督项目', '3', 'MANAGE', '165');
INSERT INTO `dns_menu` VALUES ('173', '2017-06-09 17:21:05', '0', '非现场监督项目材料录入', '', null, '0,1', 'ROLE_OFFSITESURVEILLANCE_ADDMATERIALS_MANAGE', '非现场监督项目材料录入', '5', 'MANAGE', '165');
INSERT INTO `dns_menu` VALUES ('177', '2017-06-09 17:21:05', '0', '数据管理', '', null, '0,1', 'ROLE_DATA_MANAGE', '数据管理', '0', 'MANAGE', '176');
INSERT INTO `dns_menu` VALUES ('180', '2017-06-09 17:21:05', '0', '数据管理', '', null, '0,1', 'ROLE_NONFINANCIALDATA_MANAGE', '数据管理', '0', 'MANAGE', '179');
INSERT INTO `dns_menu` VALUES ('182', '2017-06-09 17:21:05', '0', '债券评级', '', null, '0,1', 'ROLE_BOND_RATING_MANAGE', '债券评级管理', '33', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('188', '2017-06-09 17:21:05', '0', '担保人评级', '', null, '0,1', 'ROLE_GUARANTOR_RATING_MANAGE', '担保人评级管理', '34', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('194', '2017-06-09 17:21:06', '0', '主体评级', '', null, '0,1', 'ROLE_SUBJECTIVE_RATING_MANAGE', '主体评级管理', '35', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('200', '2017-06-09 17:21:06', '0', '发行人企业性质', '', null, '0,1', 'ROLE_ISSUERENTERPRISENATURE_MANAGE', '发行人企业性质管理', '36', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('207', '2017-06-09 17:21:06', '0', '交易数据管理', '', null, '0,1', 'ROLE_TRANSACTIONDATA_MANAGE', '交易数据管理', '1', 'MANAGE', '206');
INSERT INTO `dns_menu` VALUES ('212', '2017-06-09 17:21:06', '0', '监测表审核', '', null, '0,1', 'ROLE_RISKMONITOR_CHECK_MANAGE', '监测表审核', '3', 'MANAGE', '211');
INSERT INTO `dns_menu` VALUES ('214', '2017-06-09 17:21:06', '0', '监测表查询', '', null, '0,1', 'ROLE_RISKMONITOR_SELECT_MANAGE', '监测表查询', '5', 'MANAGE', '211');
INSERT INTO `dns_menu` VALUES ('216', '2017-06-09 17:21:06', '0', '监测表导入', '', null, '0,1', 'ROLE_RISKMONITOR_MANAGE', '监测表导入', '1', 'MANAGE', '211');
INSERT INTO `dns_menu` VALUES ('221', '2017-06-09 17:21:07', '0', '处罚材料模板库', '', null, '0,1', 'ROLE_PUNISHMODEL_MANAGE', '处罚模板库', '9', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('223', '2017-06-09 17:21:07', '0', '发行人行业', '', null, '0,1', 'ROLE_ISSUER_ENTERPRISE_MANAGE', '发行人行业管理', '37', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('230', '2017-06-09 17:21:07', '0', '信息披露登记', '', null, '0,1', 'ROLE_INFORMATIONDISCLOSURE_MANAGE', '信息披露', '0', 'MANAGE', '229');
INSERT INTO `dns_menu` VALUES ('232', '2017-06-09 17:21:07', '0', '信息披露审核', '', null, '0,1', 'ROLE_INFORMATIONDISCLOSURE_CHECK_MANAGE', '信息披露审核', '0', 'MANAGE', '229');
INSERT INTO `dns_menu` VALUES ('238', '2017-06-09 17:21:07', '0', '公告栏审核', '', null, '0,1', 'ROLE_NOTICEBOARD_CHECK_MANAGE', '公告栏审核', '0', 'MANAGE', '237');
INSERT INTO `dns_menu` VALUES ('240', '2017-06-09 17:21:07', '0', '公告栏', '', null, '0,1', 'ROLE_NOTICEBOARD_MANAGE', '公告栏', '0', 'MANAGE', '237');
INSERT INTO `dns_menu` VALUES ('245', '2017-06-09 17:21:08', '0', '错误性质', '', null, '0,1', 'ROLE_ERROR_PROPERTY_MANAGE', '错误性质管理', '12', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('251', '2017-06-09 17:21:08', '0', '错误原因', '', null, '0,1', 'ROLE_ERRORS_CLASS_MANAGE', '错误原因分类', '13', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('257', '2017-06-09 17:21:08', '0', '非金融企业', '', null, '0,1', 'ROLE_NONFINANCIALBUSINESS_MANAGE', '非金融企业管理', '15', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('263', '2017-06-09 17:21:08', '0', '人行执法人员库', '', null, '0,1', 'ROLE_LAWENFORCEMENTOFFICERS_MANAGE', '人行执法人员管理', '17', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('269', '2017-06-09 17:21:09', '0', '参数设置', '', null, '0,1', 'ROLE_PREFERENCES_MANAGE', '参数设置', '0', 'MANAGE', '148');
INSERT INTO `dns_menu` VALUES ('275', '2017-06-09 17:21:10', '0', '银行分类', '', null, '0,1', 'ROLE_BANKCLASSIFICATION_MANAGE', '银行分类', '18', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('281', '2017-06-09 17:21:11', '0', '检查方式', '', null, '0,1', 'ROLE_INSPECTTYPE_MANAGE', '检查方式管理', '19', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('283', '2017-06-09 17:21:11', '0', '数据属性', '', null, '0,1', 'ROLE_DATAATTRIBUTE_MANAGE', '数据属性', '25', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('289', '2017-06-09 17:21:11', '0', '币种管理', '', null, '0,1', 'ROLE_CURRENCY_MANAGE', '币种管理', '26', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('295', '2017-06-09 17:21:11', '0', '表单分类', '', null, '0,1', 'ROLE_FORM_CLASSIFICATION_MANAGE', '表单分类', '7', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('301', '2017-06-09 17:21:11', '0', '监督方式', '', null, '0,1', 'ROLE_MONITOR_MODE_MANAGE', '监督方式', '20', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('307', '2017-06-09 17:21:12', '0', '信息分类管理', '', null, '0,1', 'ROLE_INFORMATIONCLASSIFICATION_MANAGE', '信息分类管理', '0', 'MANAGE', '85');
INSERT INTO `dns_menu` VALUES ('309', '2017-06-09 17:21:12', '0', '公告分类管理', '', null, '0,1', 'ROLE_NOTICECLASSIFICATION_MANAGE', '公告分类管理', '0', 'MANAGE', '85');

-- ----------------------------
-- Table structure for dns_menugroup
-- ----------------------------
DROP TABLE IF EXISTS `dns_menugroup`;
CREATE TABLE `dns_menugroup` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `dispname` varchar(255) default NULL,
  `icons` varchar(60) default NULL,
  `name` varchar(255) default NULL,
  `sequence` int(11) NOT NULL,
  `type` varchar(255) default NULL,
  `app_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK5xf2nuge0k6nl3ex07xtsg361` (`app_id`),
  CONSTRAINT `FK5xf2nuge0k6nl3ex07xtsg361` FOREIGN KEY (`app_id`) REFERENCES `dns_app` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_menugroup
-- ----------------------------
INSERT INTO `dns_menugroup` VALUES ('13', '2017-06-09 17:20:57', '0', '系统设置', 'fa fa-gavel', '系统设置', '0', 'MANAGE', '12');
INSERT INTO `dns_menugroup` VALUES ('22', '2017-06-09 17:20:57', '0', '用户配置', 'glyphicon glyphicon-user', '用户配置', '2', 'MANAGE', '12');
INSERT INTO `dns_menugroup` VALUES ('41', '2017-06-09 17:20:58', '0', '开发配置', 'fa fa-desktop', '开发配置', '5', 'MANAGE', '12');
INSERT INTO `dns_menugroup` VALUES ('85', '2017-06-09 17:21:01', '0', '基础数据管理', 'fa fa-book', '基础数据管理', '11', 'MANAGE', '12');
INSERT INTO `dns_menugroup` VALUES ('125', '2017-06-09 17:21:03', '0', '案例库', 'fa fa-cubes', '资料库', '18', 'MANAGE', '12');
INSERT INTO `dns_menugroup` VALUES ('142', '2017-06-09 17:21:04', '0', '制度库', 'fa fa-database', '资料库管理', '21', 'MANAGE', '141');
INSERT INTO `dns_menugroup` VALUES ('148', '2017-06-09 17:21:04', '0', '统计检查', 'fa fa-area-chart', '统计检查', '22', 'MANAGE', '12');
INSERT INTO `dns_menugroup` VALUES ('153', '2017-06-09 17:21:04', '0', '执法检查', 'fa fa-calendar-check-o', '执法检查', '24', 'MANAGE', '141');
INSERT INTO `dns_menugroup` VALUES ('165', '2017-06-09 17:21:04', '0', '非现场监督', 'glyphicon glyphicon-eye-open', '非现场监督', '28', 'MANAGE', '12');
INSERT INTO `dns_menugroup` VALUES ('176', '2017-06-09 17:21:05', '0', '金融债券', null, '金融债券', '30', 'MANAGE', '175');
INSERT INTO `dns_menugroup` VALUES ('179', '2017-06-09 17:21:05', '0', '非金融债务融资', null, '非金融债务融资', '31', 'MANAGE', '12');
INSERT INTO `dns_menugroup` VALUES ('206', '2017-06-09 17:21:06', '0', '数据审核', 'fa fa-tty', '交易数据', '36', 'MANAGE', '12');
INSERT INTO `dns_menugroup` VALUES ('211', '2017-06-09 17:21:06', '0', '风险监测', null, '风险监测', '37', 'MANAGE', '12');
INSERT INTO `dns_menugroup` VALUES ('229', '2017-06-09 17:21:07', '0', '信息披露', null, '信息披露', '40', 'MANAGE', '12');
INSERT INTO `dns_menugroup` VALUES ('237', '2017-06-09 17:21:07', '0', '公告栏', null, '公告栏', '41', 'MANAGE', '12');

-- ----------------------------
-- Table structure for dns_menu_res
-- ----------------------------
DROP TABLE IF EXISTS `dns_menu_res`;
CREATE TABLE `dns_menu_res` (
  `menu_id` bigint(20) NOT NULL,
  `res_id` bigint(20) NOT NULL,
  KEY `FKqf4m39fn0okal37prv83ifded` (`res_id`),
  KEY `FKe688lu3qs0c9jkrnej0t8dwa1` (`menu_id`),
  CONSTRAINT `FKe688lu3qs0c9jkrnej0t8dwa1` FOREIGN KEY (`menu_id`) REFERENCES `dns_menu` (`id`),
  CONSTRAINT `FKqf4m39fn0okal37prv83ifded` FOREIGN KEY (`res_id`) REFERENCES `dns_res` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_menu_res
-- ----------------------------
INSERT INTO `dns_menu_res` VALUES ('16', '15');
INSERT INTO `dns_menu_res` VALUES ('16', '17');
INSERT INTO `dns_menu_res` VALUES ('16', '18');
INSERT INTO `dns_menu_res` VALUES ('30', '29');
INSERT INTO `dns_menu_res` VALUES ('30', '31');
INSERT INTO `dns_menu_res` VALUES ('33', '32');
INSERT INTO `dns_menu_res` VALUES ('33', '34');
INSERT INTO `dns_menu_res` VALUES ('33', '35');
INSERT INTO `dns_menu_res` VALUES ('23', '21');
INSERT INTO `dns_menu_res` VALUES ('23', '24');
INSERT INTO `dns_menu_res` VALUES ('23', '25');
INSERT INTO `dns_menu_res` VALUES ('23', '26');
INSERT INTO `dns_menu_res` VALUES ('23', '27');
INSERT INTO `dns_menu_res` VALUES ('23', '28');
INSERT INTO `dns_menu_res` VALUES ('23', '36');
INSERT INTO `dns_menu_res` VALUES ('38', '37');
INSERT INTO `dns_menu_res` VALUES ('38', '39');
INSERT INTO `dns_menu_res` VALUES ('42', '40');
INSERT INTO `dns_menu_res` VALUES ('42', '43');
INSERT INTO `dns_menu_res` VALUES ('42', '44');
INSERT INTO `dns_menu_res` VALUES ('42', '45');
INSERT INTO `dns_menu_res` VALUES ('42', '46');
INSERT INTO `dns_menu_res` VALUES ('42', '47');
INSERT INTO `dns_menu_res` VALUES ('42', '48');
INSERT INTO `dns_menu_res` VALUES ('42', '49');
INSERT INTO `dns_menu_res` VALUES ('42', '50');
INSERT INTO `dns_menu_res` VALUES ('42', '51');
INSERT INTO `dns_menu_res` VALUES ('42', '52');
INSERT INTO `dns_menu_res` VALUES ('42', '53');
INSERT INTO `dns_menu_res` VALUES ('58', '57');
INSERT INTO `dns_menu_res` VALUES ('58', '59');
INSERT INTO `dns_menu_res` VALUES ('58', '60');
INSERT INTO `dns_menu_res` VALUES ('58', '61');
INSERT INTO `dns_menu_res` VALUES ('58', '62');
INSERT INTO `dns_menu_res` VALUES ('55', '54');
INSERT INTO `dns_menu_res` VALUES ('55', '56');
INSERT INTO `dns_menu_res` VALUES ('55', '63');
INSERT INTO `dns_menu_res` VALUES ('55', '64');
INSERT INTO `dns_menu_res` VALUES ('66', '65');
INSERT INTO `dns_menu_res` VALUES ('66', '67');
INSERT INTO `dns_menu_res` VALUES ('66', '68');
INSERT INTO `dns_menu_res` VALUES ('14', '11');
INSERT INTO `dns_menu_res` VALUES ('14', '19');
INSERT INTO `dns_menu_res` VALUES ('14', '20');
INSERT INTO `dns_menu_res` VALUES ('14', '69');
INSERT INTO `dns_menu_res` VALUES ('14', '70');
INSERT INTO `dns_menu_res` VALUES ('72', '71');
INSERT INTO `dns_menu_res` VALUES ('72', '73');
INSERT INTO `dns_menu_res` VALUES ('72', '74');
INSERT INTO `dns_menu_res` VALUES ('72', '75');
INSERT INTO `dns_menu_res` VALUES ('77', '76');
INSERT INTO `dns_menu_res` VALUES ('77', '78');
INSERT INTO `dns_menu_res` VALUES ('77', '79');
INSERT INTO `dns_menu_res` VALUES ('77', '80');
INSERT INTO `dns_menu_res` VALUES ('77', '81');
INSERT INTO `dns_menu_res` VALUES ('83', '82');
INSERT INTO `dns_menu_res` VALUES ('86', '84');
INSERT INTO `dns_menu_res` VALUES ('86', '87');
INSERT INTO `dns_menu_res` VALUES ('86', '88');
INSERT INTO `dns_menu_res` VALUES ('86', '89');
INSERT INTO `dns_menu_res` VALUES ('86', '90');
INSERT INTO `dns_menu_res` VALUES ('92', '91');
INSERT INTO `dns_menu_res` VALUES ('92', '93');
INSERT INTO `dns_menu_res` VALUES ('92', '94');
INSERT INTO `dns_menu_res` VALUES ('92', '95');
INSERT INTO `dns_menu_res` VALUES ('92', '96');
INSERT INTO `dns_menu_res` VALUES ('98', '97');
INSERT INTO `dns_menu_res` VALUES ('98', '99');
INSERT INTO `dns_menu_res` VALUES ('98', '100');
INSERT INTO `dns_menu_res` VALUES ('98', '101');
INSERT INTO `dns_menu_res` VALUES ('98', '102');
INSERT INTO `dns_menu_res` VALUES ('98', '103');
INSERT INTO `dns_menu_res` VALUES ('105', '104');
INSERT INTO `dns_menu_res` VALUES ('105', '106');
INSERT INTO `dns_menu_res` VALUES ('105', '107');
INSERT INTO `dns_menu_res` VALUES ('105', '108');
INSERT INTO `dns_menu_res` VALUES ('105', '109');
INSERT INTO `dns_menu_res` VALUES ('111', '110');
INSERT INTO `dns_menu_res` VALUES ('111', '112');
INSERT INTO `dns_menu_res` VALUES ('111', '113');
INSERT INTO `dns_menu_res` VALUES ('111', '114');
INSERT INTO `dns_menu_res` VALUES ('111', '115');
INSERT INTO `dns_menu_res` VALUES ('117', '116');
INSERT INTO `dns_menu_res` VALUES ('117', '118');
INSERT INTO `dns_menu_res` VALUES ('117', '119');
INSERT INTO `dns_menu_res` VALUES ('117', '120');
INSERT INTO `dns_menu_res` VALUES ('117', '121');
INSERT INTO `dns_menu_res` VALUES ('123', '122');
INSERT INTO `dns_menu_res` VALUES ('126', '124');
INSERT INTO `dns_menu_res` VALUES ('126', '127');
INSERT INTO `dns_menu_res` VALUES ('129', '128');
INSERT INTO `dns_menu_res` VALUES ('129', '130');
INSERT INTO `dns_menu_res` VALUES ('129', '131');
INSERT INTO `dns_menu_res` VALUES ('129', '132');
INSERT INTO `dns_menu_res` VALUES ('134', '133');
INSERT INTO `dns_menu_res` VALUES ('134', '135');
INSERT INTO `dns_menu_res` VALUES ('134', '136');
INSERT INTO `dns_menu_res` VALUES ('134', '137');
INSERT INTO `dns_menu_res` VALUES ('139', '138');
INSERT INTO `dns_menu_res` VALUES ('143', '140');
INSERT INTO `dns_menu_res` VALUES ('143', '144');
INSERT INTO `dns_menu_res` VALUES ('143', '145');
INSERT INTO `dns_menu_res` VALUES ('143', '146');
INSERT INTO `dns_menu_res` VALUES ('149', '147');
INSERT INTO `dns_menu_res` VALUES ('151', '150');
INSERT INTO `dns_menu_res` VALUES ('154', '152');
INSERT INTO `dns_menu_res` VALUES ('156', '155');
INSERT INTO `dns_menu_res` VALUES ('158', '157');
INSERT INTO `dns_menu_res` VALUES ('160', '159');
INSERT INTO `dns_menu_res` VALUES ('160', '161');
INSERT INTO `dns_menu_res` VALUES ('160', '162');
INSERT INTO `dns_menu_res` VALUES ('160', '163');
INSERT INTO `dns_menu_res` VALUES ('168', '167');
INSERT INTO `dns_menu_res` VALUES ('166', '164');
INSERT INTO `dns_menu_res` VALUES ('166', '169');
INSERT INTO `dns_menu_res` VALUES ('166', '170');
INSERT INTO `dns_menu_res` VALUES ('166', '171');
INSERT INTO `dns_menu_res` VALUES ('173', '172');
INSERT INTO `dns_menu_res` VALUES ('177', '174');
INSERT INTO `dns_menu_res` VALUES ('180', '178');
INSERT INTO `dns_menu_res` VALUES ('182', '181');
INSERT INTO `dns_menu_res` VALUES ('182', '183');
INSERT INTO `dns_menu_res` VALUES ('182', '184');
INSERT INTO `dns_menu_res` VALUES ('182', '185');
INSERT INTO `dns_menu_res` VALUES ('182', '186');
INSERT INTO `dns_menu_res` VALUES ('188', '187');
INSERT INTO `dns_menu_res` VALUES ('188', '189');
INSERT INTO `dns_menu_res` VALUES ('188', '190');
INSERT INTO `dns_menu_res` VALUES ('188', '191');
INSERT INTO `dns_menu_res` VALUES ('188', '192');
INSERT INTO `dns_menu_res` VALUES ('194', '193');
INSERT INTO `dns_menu_res` VALUES ('194', '195');
INSERT INTO `dns_menu_res` VALUES ('194', '196');
INSERT INTO `dns_menu_res` VALUES ('194', '197');
INSERT INTO `dns_menu_res` VALUES ('194', '198');
INSERT INTO `dns_menu_res` VALUES ('200', '199');
INSERT INTO `dns_menu_res` VALUES ('200', '201');
INSERT INTO `dns_menu_res` VALUES ('200', '202');
INSERT INTO `dns_menu_res` VALUES ('200', '203');
INSERT INTO `dns_menu_res` VALUES ('200', '204');
INSERT INTO `dns_menu_res` VALUES ('207', '205');
INSERT INTO `dns_menu_res` VALUES ('207', '208');
INSERT INTO `dns_menu_res` VALUES ('207', '209');
INSERT INTO `dns_menu_res` VALUES ('212', '210');
INSERT INTO `dns_menu_res` VALUES ('214', '213');
INSERT INTO `dns_menu_res` VALUES ('216', '215');
INSERT INTO `dns_menu_res` VALUES ('216', '217');
INSERT INTO `dns_menu_res` VALUES ('216', '218');
INSERT INTO `dns_menu_res` VALUES ('216', '219');
INSERT INTO `dns_menu_res` VALUES ('221', '220');
INSERT INTO `dns_menu_res` VALUES ('223', '222');
INSERT INTO `dns_menu_res` VALUES ('223', '224');
INSERT INTO `dns_menu_res` VALUES ('223', '225');
INSERT INTO `dns_menu_res` VALUES ('223', '226');
INSERT INTO `dns_menu_res` VALUES ('223', '227');
INSERT INTO `dns_menu_res` VALUES ('232', '231');
INSERT INTO `dns_menu_res` VALUES ('230', '228');
INSERT INTO `dns_menu_res` VALUES ('230', '233');
INSERT INTO `dns_menu_res` VALUES ('230', '234');
INSERT INTO `dns_menu_res` VALUES ('230', '235');
INSERT INTO `dns_menu_res` VALUES ('238', '236');
INSERT INTO `dns_menu_res` VALUES ('240', '239');
INSERT INTO `dns_menu_res` VALUES ('240', '241');
INSERT INTO `dns_menu_res` VALUES ('240', '242');
INSERT INTO `dns_menu_res` VALUES ('240', '243');
INSERT INTO `dns_menu_res` VALUES ('245', '244');
INSERT INTO `dns_menu_res` VALUES ('245', '246');
INSERT INTO `dns_menu_res` VALUES ('245', '247');
INSERT INTO `dns_menu_res` VALUES ('245', '248');
INSERT INTO `dns_menu_res` VALUES ('245', '249');
INSERT INTO `dns_menu_res` VALUES ('251', '250');
INSERT INTO `dns_menu_res` VALUES ('251', '252');
INSERT INTO `dns_menu_res` VALUES ('251', '253');
INSERT INTO `dns_menu_res` VALUES ('251', '254');
INSERT INTO `dns_menu_res` VALUES ('251', '255');
INSERT INTO `dns_menu_res` VALUES ('257', '256');
INSERT INTO `dns_menu_res` VALUES ('257', '258');
INSERT INTO `dns_menu_res` VALUES ('257', '259');
INSERT INTO `dns_menu_res` VALUES ('257', '260');
INSERT INTO `dns_menu_res` VALUES ('257', '261');
INSERT INTO `dns_menu_res` VALUES ('263', '262');
INSERT INTO `dns_menu_res` VALUES ('263', '264');
INSERT INTO `dns_menu_res` VALUES ('263', '265');
INSERT INTO `dns_menu_res` VALUES ('263', '266');
INSERT INTO `dns_menu_res` VALUES ('263', '267');
INSERT INTO `dns_menu_res` VALUES ('269', '268');
INSERT INTO `dns_menu_res` VALUES ('269', '270');
INSERT INTO `dns_menu_res` VALUES ('269', '271');
INSERT INTO `dns_menu_res` VALUES ('269', '272');
INSERT INTO `dns_menu_res` VALUES ('269', '273');
INSERT INTO `dns_menu_res` VALUES ('275', '274');
INSERT INTO `dns_menu_res` VALUES ('275', '276');
INSERT INTO `dns_menu_res` VALUES ('275', '277');
INSERT INTO `dns_menu_res` VALUES ('275', '278');
INSERT INTO `dns_menu_res` VALUES ('275', '279');
INSERT INTO `dns_menu_res` VALUES ('281', '280');
INSERT INTO `dns_menu_res` VALUES ('283', '282');
INSERT INTO `dns_menu_res` VALUES ('283', '284');
INSERT INTO `dns_menu_res` VALUES ('283', '285');
INSERT INTO `dns_menu_res` VALUES ('283', '286');
INSERT INTO `dns_menu_res` VALUES ('283', '287');
INSERT INTO `dns_menu_res` VALUES ('289', '288');
INSERT INTO `dns_menu_res` VALUES ('289', '290');
INSERT INTO `dns_menu_res` VALUES ('289', '291');
INSERT INTO `dns_menu_res` VALUES ('289', '292');
INSERT INTO `dns_menu_res` VALUES ('289', '293');
INSERT INTO `dns_menu_res` VALUES ('295', '294');
INSERT INTO `dns_menu_res` VALUES ('295', '296');
INSERT INTO `dns_menu_res` VALUES ('295', '297');
INSERT INTO `dns_menu_res` VALUES ('295', '298');
INSERT INTO `dns_menu_res` VALUES ('295', '299');
INSERT INTO `dns_menu_res` VALUES ('301', '300');
INSERT INTO `dns_menu_res` VALUES ('301', '302');
INSERT INTO `dns_menu_res` VALUES ('301', '303');
INSERT INTO `dns_menu_res` VALUES ('301', '304');
INSERT INTO `dns_menu_res` VALUES ('301', '305');
INSERT INTO `dns_menu_res` VALUES ('307', '306');
INSERT INTO `dns_menu_res` VALUES ('309', '308');

-- ----------------------------
-- Table structure for dns_nonfinancialbusiness
-- ----------------------------
DROP TABLE IF EXISTS `dns_nonfinancialbusiness`;
CREATE TABLE `dns_nonfinancialbusiness` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(42) default NULL,
  `create_date` datetime default NULL,
  `create_user` bigint(20) default NULL,
  `enabled` bit(1) default '',
  `last_ver` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` bigint(20) default NULL,
  `leaf` bit(1) default '',
  `level` tinyint(4) default '1',
  `name` varchar(42) default NULL,
  `set_year` int(11) default NULL,
  `treepath` varchar(255) default NULL,
  `address` varchar(255) default NULL,
  `contactnumber` varchar(255) default NULL,
  `legalPersonName` varchar(255) default NULL,
  `registered_capital` varchar(255) default NULL,
  `parent_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKe2xu76txwoh9698cue3kakfmj` (`parent_id`),
  CONSTRAINT `FKe2xu76txwoh9698cue3kakfmj` FOREIGN KEY (`parent_id`) REFERENCES `dns_nonfinancialbusiness` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_nonfinancialbusiness
-- ----------------------------

-- ----------------------------
-- Table structure for dns_nonfinancialdate_manage
-- ----------------------------
DROP TABLE IF EXISTS `dns_nonfinancialdate_manage`;
CREATE TABLE `dns_nonfinancialdate_manage` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `bond_level` varchar(255) default NULL,
  `bond_type` varchar(255) default NULL,
  `coupon_rate` varchar(255) default NULL,
  `deadline` varchar(255) default NULL,
  `deal_code` varchar(255) default NULL,
  `due_date` datetime default NULL,
  `enterprise_nature` varchar(255) default NULL,
  `full_name` varchar(255) default NULL,
  `fund_use` varchar(255) default NULL,
  `industry` varchar(255) default NULL,
  `location` varchar(255) default NULL,
  `main_money` varchar(255) default NULL,
  `main_underwriter` varchar(255) default NULL,
  `scale` varchar(255) default NULL,
  `secondary_money` varchar(255) default NULL,
  `secondary_underwriter` varchar(255) default NULL,
  `special_deadline` varchar(255) default NULL,
  `start_date` datetime default NULL,
  `status` int(11) default NULL,
  `subject_level` varchar(255) default NULL,
  `value_date` datetime default NULL,
  `datamanage_file_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKtdajnix1nopa2v2q6apl98mfd` (`datamanage_file_id`),
  CONSTRAINT `FKtdajnix1nopa2v2q6apl98mfd` FOREIGN KEY (`datamanage_file_id`) REFERENCES `dns_accessory` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_nonfinancialdate_manage
-- ----------------------------

-- ----------------------------
-- Table structure for dns_noticeboard
-- ----------------------------
DROP TABLE IF EXISTS `dns_noticeboard`;
CREATE TABLE `dns_noticeboard` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `release_date` datetime default NULL,
  `release_people` varchar(255) default NULL,
  `release_unit` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  `sendee` varchar(255) default NULL,
  `status_check` int(11) default NULL,
  `status_submit` int(11) default NULL,
  `title` varchar(255) default NULL,
  `accessory_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK9vrphmkj85gdxlleffnfrt2vn` (`accessory_id`),
  CONSTRAINT `FK9vrphmkj85gdxlleffnfrt2vn` FOREIGN KEY (`accessory_id`) REFERENCES `dns_accessory` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_noticeboard
-- ----------------------------

-- ----------------------------
-- Table structure for dns_notice_classification
-- ----------------------------
DROP TABLE IF EXISTS `dns_notice_classification`;
CREATE TABLE `dns_notice_classification` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_notice_classification
-- ----------------------------

-- ----------------------------
-- Table structure for dns_offsitesurveillance
-- ----------------------------
DROP TABLE IF EXISTS `dns_offsitesurveillance`;
CREATE TABLE `dns_offsitesurveillance` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `enterdate` datetime default NULL,
  `enterpeople` varchar(255) default NULL,
  `issubmit` int(11) default NULL,
  `partakepeopel` varchar(255) default NULL,
  `supervisedate` datetime default NULL,
  `supervisename` varchar(255) default NULL,
  `superviseremark` longtext,
  `supervisestatus` int(11) default NULL,
  `elePbcOrg_id` bigint(20) default NULL,
  `superviseobj_id` bigint(20) default NULL,
  `supervisepeople_id` bigint(20) default NULL,
  `superviseway_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK3ap4oqyp2yqari1jeuh7ri2ix` (`elePbcOrg_id`),
  KEY `FKbope5cn28gyporiqpxeu8md50` (`superviseobj_id`),
  KEY `FKqo53tpm756icm9kp7cxv6utw3` (`supervisepeople_id`),
  KEY `FKhgvq6vl5ldfku6cfrsppc4q9c` (`superviseway_id`),
  CONSTRAINT `FKhgvq6vl5ldfku6cfrsppc4q9c` FOREIGN KEY (`superviseway_id`) REFERENCES `dns_ele_monitormode` (`id`),
  CONSTRAINT `FK3ap4oqyp2yqari1jeuh7ri2ix` FOREIGN KEY (`elePbcOrg_id`) REFERENCES `dns_ele_pbc_org` (`id`),
  CONSTRAINT `FKbope5cn28gyporiqpxeu8md50` FOREIGN KEY (`superviseobj_id`) REFERENCES `dns_ele_finance_org` (`id`),
  CONSTRAINT `FKqo53tpm756icm9kp7cxv6utw3` FOREIGN KEY (`supervisepeople_id`) REFERENCES `dns_lawenforcementofficers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_offsitesurveillance
-- ----------------------------

-- ----------------------------
-- Table structure for dns_offsitesurveillance_addmaterial
-- ----------------------------
DROP TABLE IF EXISTS `dns_offsitesurveillance_addmaterial`;
CREATE TABLE `dns_offsitesurveillance_addmaterial` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `isapply` int(11) default NULL,
  `supervisecondition` varchar(255) default NULL,
  `supervisesystem` varchar(255) default NULL,
  `offsiteSurveillance_id` bigint(20) default NULL,
  `superviseA_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK17f13ihshnf91rwx6ht2hvgsg` (`offsiteSurveillance_id`),
  KEY `FKjma5q85spn7abtfjyfqbhit72` (`superviseA_id`),
  CONSTRAINT `FKjma5q85spn7abtfjyfqbhit72` FOREIGN KEY (`superviseA_id`) REFERENCES `dns_accessory` (`id`),
  CONSTRAINT `FK17f13ihshnf91rwx6ht2hvgsg` FOREIGN KEY (`offsiteSurveillance_id`) REFERENCES `dns_offsitesurveillance` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_offsitesurveillance_addmaterial
-- ----------------------------

-- ----------------------------
-- Table structure for dns_operate
-- ----------------------------
DROP TABLE IF EXISTS `dns_operate`;
CREATE TABLE `dns_operate` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `cmptype` varchar(42) default NULL,
  `name` varchar(20) default NULL,
  `remark` varchar(255) default NULL,
  `selector` varchar(255) default NULL,
  `menu_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKqbgsg8ywh72q2wvrpmnjukg4r` (`menu_id`),
  CONSTRAINT `FKqbgsg8ywh72q2wvrpmnjukg4r` FOREIGN KEY (`menu_id`) REFERENCES `dns_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_operate
-- ----------------------------
INSERT INTO `dns_operate` VALUES ('484', '2017-05-02 19:35:39', '0', '1', '新增', '', '#btn_add', '105');
INSERT INTO `dns_operate` VALUES ('485', '2017-05-02 19:35:39', '0', '1', '修改', '', '#btn_upd', '105');
INSERT INTO `dns_operate` VALUES ('571', '2017-05-06 17:12:43', '0', '1', '删除', '', '#btn_del', '105');
INSERT INTO `dns_operate` VALUES ('785', '2017-05-16 15:56:08', '0', '1', '新增', '', '#btn_add', '111');
INSERT INTO `dns_operate` VALUES ('786', '2017-05-16 15:56:08', '0', '1', '修改', '', '#btn_upd', '111');
INSERT INTO `dns_operate` VALUES ('787', '2017-05-16 15:56:08', '0', '1', '删除', '', '#btn_del', '111');
INSERT INTO `dns_operate` VALUES ('793', '2017-05-16 15:59:16', '0', '1', '新增', '', '#btn_add', '117');
INSERT INTO `dns_operate` VALUES ('794', '2017-05-16 15:59:16', '0', '1', '修改', '', '#btn_upd', '117');
INSERT INTO `dns_operate` VALUES ('795', '2017-05-16 15:59:16', '0', '1', '删除', '', '#btn_del', '117');
INSERT INTO `dns_operate` VALUES ('797', '2017-05-16 16:00:28', '0', '1', '新增', '', '#btn_add', '182');
INSERT INTO `dns_operate` VALUES ('798', '2017-05-16 16:00:28', '0', '1', '修改', '', '#btn_upd', '182');
INSERT INTO `dns_operate` VALUES ('799', '2017-05-16 16:00:28', '0', '1', '删除', '', '#btn_del', '182');
INSERT INTO `dns_operate` VALUES ('803', '2017-05-16 16:03:24', '0', '1', '新增', '', '#btn_add', '123');
INSERT INTO `dns_operate` VALUES ('804', '2017-05-16 16:03:24', '0', '1', '修改', '', '#btn_upd', '123');
INSERT INTO `dns_operate` VALUES ('805', '2017-05-16 16:03:24', '0', '1', '删除', '', '#btn_del', '123');
INSERT INTO `dns_operate` VALUES ('807', '2017-05-16 16:04:11', '0', '1', '新增', '', '#btn_add', '134');
INSERT INTO `dns_operate` VALUES ('808', '2017-05-16 16:04:11', '0', '1', '修改', '', '#btn_upd', '134');
INSERT INTO `dns_operate` VALUES ('809', '2017-05-16 16:04:11', '0', '1', '删除', '', '#btn_del', '134');
INSERT INTO `dns_operate` VALUES ('811', '2017-05-16 16:05:04', '0', '1', '新增', '', '#btn_add', '139');
INSERT INTO `dns_operate` VALUES ('812', '2017-05-16 16:05:04', '0', '1', '修改', '', '#btn_upd', '139');
INSERT INTO `dns_operate` VALUES ('813', '2017-05-16 16:05:04', '0', '1', '删除', '', '#btn_del', '139');
INSERT INTO `dns_operate` VALUES ('817', '2017-05-16 16:06:46', '0', '1', '新增', '', '#btn_add', '200');
INSERT INTO `dns_operate` VALUES ('818', '2017-05-16 16:06:46', '0', '1', '修改', '', '#btn_upd', '200');
INSERT INTO `dns_operate` VALUES ('819', '2017-05-16 16:06:46', '0', '1', '删除', '', '#btn_del', '200');
INSERT INTO `dns_operate` VALUES ('825', '2017-05-16 16:07:36', '0', '1', '新增', '', '#btn_add', '223');
INSERT INTO `dns_operate` VALUES ('826', '2017-05-16 16:07:36', '0', '1', '修改', '', '#btn_upd', '223');
INSERT INTO `dns_operate` VALUES ('827', '2017-05-16 16:07:36', '0', '1', '删除', '', '#btn_del', '223');
INSERT INTO `dns_operate` VALUES ('829', '2017-05-16 16:08:51', '0', '1', '新增', '', '#btn_add', '221');
INSERT INTO `dns_operate` VALUES ('830', '2017-05-16 16:08:51', '0', '1', '修改', '', '#btn_upd', '221');
INSERT INTO `dns_operate` VALUES ('831', '2017-05-16 16:08:51', '0', '1', '删除', '', '#btn_del', '221');
INSERT INTO `dns_operate` VALUES ('837', '2017-05-16 16:12:37', '0', '1', '导入', '', '#btn_add', '149');
INSERT INTO `dns_operate` VALUES ('838', '2017-05-16 16:12:37', '0', '1', '修改', '', '#btn_upd', '149');
INSERT INTO `dns_operate` VALUES ('839', '2017-05-16 16:12:37', '0', '1', '删除', '', '#btn_del', '149');
INSERT INTO `dns_operate` VALUES ('840', '2017-05-16 16:12:37', '0', '1', '上报', '', '#btn_sub', '149');
INSERT INTO `dns_operate` VALUES ('842', '2017-05-16 16:14:00', '0', '1', '查询', '', '#btn_que', '149');
INSERT INTO `dns_operate` VALUES ('850', '2017-05-16 16:16:33', '0', '1', '新增', '', '#btn_add', '129');
INSERT INTO `dns_operate` VALUES ('851', '2017-05-16 16:16:33', '0', '1', '修改', '', '#btn_upd', '129');
INSERT INTO `dns_operate` VALUES ('852', '2017-05-16 16:16:33', '0', '1', '删除', '', '#btn_del', '129');
INSERT INTO `dns_operate` VALUES ('853', '2017-05-16 16:16:33', '0', '1', '提交', '', '#btn_sub', '129');
INSERT INTO `dns_operate` VALUES ('854', '2017-05-16 16:16:33', '0', '1', '置顶', '', '#btn_up', '129');
INSERT INTO `dns_operate` VALUES ('856', '2017-05-16 16:17:56', '0', '1', '新增', '', '#btn_add', '166');
INSERT INTO `dns_operate` VALUES ('857', '2017-05-16 16:17:56', '0', '1', '修改', '', '#btn_upd', '166');
INSERT INTO `dns_operate` VALUES ('858', '2017-05-16 16:17:56', '0', '1', '删除', '', '#btn_del', '166');
INSERT INTO `dns_operate` VALUES ('859', '2017-05-16 16:17:56', '0', '1', '提交', '', '#btn_sub', '166');
INSERT INTO `dns_operate` VALUES ('868', '2017-05-16 16:19:18', '0', '1', '导入', '', '#btn_add', '158');
INSERT INTO `dns_operate` VALUES ('869', '2017-05-16 16:19:18', '0', '1', '修改', '', '#btn_upd', '158');
INSERT INTO `dns_operate` VALUES ('870', '2017-05-16 16:19:18', '0', '1', '删除', '', '#btn_del', '158');
INSERT INTO `dns_operate` VALUES ('871', '2017-05-16 16:19:18', '0', '1', '明细导入', '', '#btn_detailadd', '158');
INSERT INTO `dns_operate` VALUES ('872', '2017-05-16 16:19:18', '0', '1', '明细修改', '', '#btn_detailupd', '158');
INSERT INTO `dns_operate` VALUES ('873', '2017-05-16 16:19:18', '0', '1', '明细删除', '', '#btn_detaildel', '158');
INSERT INTO `dns_operate` VALUES ('874', '2017-05-16 16:19:18', '0', '1', '查询', '', '#btn_que', '158');
INSERT INTO `dns_operate` VALUES ('876', '2017-05-16 16:19:28', '0', '1', '审核', '', '#btn_che', '168');
INSERT INTO `dns_operate` VALUES ('877', '2017-05-16 16:19:28', '0', '1', '退回', '', '#btn_back', '168');
INSERT INTO `dns_operate` VALUES ('883', '2017-05-16 16:20:55', '0', '1', '新增', '', '#btn_add', '173');
INSERT INTO `dns_operate` VALUES ('884', '2017-05-16 16:20:55', '0', '1', '修改', '', '#btn_upd', '173');
INSERT INTO `dns_operate` VALUES ('885', '2017-05-16 16:20:55', '0', '1', '删除', '', '#btn_del', '173');
INSERT INTO `dns_operate` VALUES ('887', '2017-05-16 16:21:57', '0', '1', '导入', '', '#btn_add', '207');
INSERT INTO `dns_operate` VALUES ('888', '2017-05-16 16:21:57', '0', '1', '审核', '', '#btn_che', '207');
INSERT INTO `dns_operate` VALUES ('890', '2017-05-16 16:22:23', '0', '1', '主表查询', '', '#btn_que', '156');
INSERT INTO `dns_operate` VALUES ('891', '2017-05-16 16:22:23', '0', '1', '增加', '', '#btn_projectadd', '156');
INSERT INTO `dns_operate` VALUES ('892', '2017-05-16 16:22:23', '0', '1', '修改', '', '#btn_projectupd', '156');
INSERT INTO `dns_operate` VALUES ('893', '2017-05-16 16:22:23', '0', '1', '删除', '', '#btn_projectdel', '156');
INSERT INTO `dns_operate` VALUES ('894', '2017-05-16 16:22:23', '0', '1', '查询', '', '#btn_projectque', '156');
INSERT INTO `dns_operate` VALUES ('896', '2017-05-16 16:23:22', '0', '1', '新增', '', '#btn_add', '188');
INSERT INTO `dns_operate` VALUES ('897', '2017-05-16 16:23:22', '0', '1', '修改', '', '#btn_upd', '188');
INSERT INTO `dns_operate` VALUES ('898', '2017-05-16 16:23:22', '0', '1', '删除', '', '#btn_del', '188');
INSERT INTO `dns_operate` VALUES ('901', '2017-05-16 16:25:43', '0', '1', '新增', '', '#btn_add', '194');
INSERT INTO `dns_operate` VALUES ('902', '2017-05-16 16:25:43', '0', '1', '修改', '', '#btn_upd', '194');
INSERT INTO `dns_operate` VALUES ('903', '2017-05-16 16:25:43', '0', '1', '删除', '', '#btn_del', '194');
INSERT INTO `dns_operate` VALUES ('905', '2017-05-16 16:27:21', '0', '1', '导入', '', '#btn_add', '216');
INSERT INTO `dns_operate` VALUES ('906', '2017-05-16 16:27:21', '0', '1', '删除', '', '#btn_del', '216');
INSERT INTO `dns_operate` VALUES ('907', '2017-05-16 16:27:21', '0', '1', '提交', '', '#btn_sub', '216');
INSERT INTO `dns_operate` VALUES ('909', '2017-05-16 16:29:31', '0', '1', '审核', '', '#btn_che', '212');
INSERT INTO `dns_operate` VALUES ('910', '2017-05-16 16:29:31', '0', '1', '退回', '', '#btn_back', '212');
INSERT INTO `dns_operate` VALUES ('920', '2017-05-16 16:32:04', '0', '1', '新增', '', '#btn_add', '143');
INSERT INTO `dns_operate` VALUES ('921', '2017-05-16 16:32:04', '0', '1', '修改', '', '#btn_upd', '143');
INSERT INTO `dns_operate` VALUES ('922', '2017-05-16 16:32:04', '0', '1', '删除', '', '#btn_del', '143');
INSERT INTO `dns_operate` VALUES ('924', '2017-05-16 16:33:21', '0', '1', '导入', '', '#btn_add', '160');
INSERT INTO `dns_operate` VALUES ('925', '2017-05-16 16:33:21', '0', '1', '修改', '', '#btn_upd', '160');
INSERT INTO `dns_operate` VALUES ('926', '2017-05-16 16:33:21', '0', '1', '删除', '', '#btn_del', '160');
INSERT INTO `dns_operate` VALUES ('927', '2017-05-16 16:33:21', '0', '1', '批量导入', '', '#btn_adds', '160');
INSERT INTO `dns_operate` VALUES ('943', '2017-05-16 16:36:09', '0', '1', '查询', '', '#btn_que', '129');
INSERT INTO `dns_operate` VALUES ('945', '2017-05-16 16:36:58', '0', '1', '查询', '', '#btn_que', '166');
INSERT INTO `dns_operate` VALUES ('947', '2017-05-16 16:37:16', '0', '1', '主表查询', '', '#btn_que', '154');
INSERT INTO `dns_operate` VALUES ('948', '2017-05-16 16:37:16', '0', '1', '执法材料设置', '', '#btn_enforceset', '154');
INSERT INTO `dns_operate` VALUES ('949', '2017-05-16 16:37:16', '0', '1', '执法材料增加', '', '#btn_enforceadd', '154');
INSERT INTO `dns_operate` VALUES ('950', '2017-05-16 16:37:16', '0', '1', '执法材料删除', '', '#btn_enforcedel', '154');
INSERT INTO `dns_operate` VALUES ('951', '2017-05-16 16:37:16', '0', '1', '整改报告增加', '', '#btn_reportadd', '154');
INSERT INTO `dns_operate` VALUES ('952', '2017-05-16 16:37:16', '0', '1', '整改报告修改', '', '#btn_reportupd', '154');
INSERT INTO `dns_operate` VALUES ('953', '2017-05-16 16:37:16', '0', '1', '整改报告删除', '', '#btn_reportdel', '154');
INSERT INTO `dns_operate` VALUES ('954', '2017-05-16 16:37:16', '0', '1', '处罚材料设置', '', '#btn_punishadd', '154');
INSERT INTO `dns_operate` VALUES ('955', '2017-05-16 16:37:16', '0', '1', '处罚材料修改', '', '#btn_punishupd', '154');
INSERT INTO `dns_operate` VALUES ('956', '2017-05-16 16:37:16', '0', '1', '处罚材料删除', '', '#btn_punishdel', '154');
INSERT INTO `dns_operate` VALUES ('957', '2017-05-16 16:37:16', '0', '1', '时间节点增加', '', '#btn_timeadd', '154');
INSERT INTO `dns_operate` VALUES ('958', '2017-05-16 16:37:16', '0', '1', '时间节点修改', '', '#btn_timeupd', '154');
INSERT INTO `dns_operate` VALUES ('959', '2017-05-16 16:37:16', '0', '1', '时间节点删除', '', '#btn_timedel', '154');
INSERT INTO `dns_operate` VALUES ('960', '2017-05-16 16:37:16', '0', '1', '存在问题增加', '', '#btn_existadd', '154');
INSERT INTO `dns_operate` VALUES ('961', '2017-05-16 16:37:16', '0', '1', '存在问题修改', '', '#btn_existupd', '154');
INSERT INTO `dns_operate` VALUES ('962', '2017-05-16 16:37:16', '0', '1', '存在问题删除', '', '#btn_existdel', '154');
INSERT INTO `dns_operate` VALUES ('963', '2017-05-16 16:37:16', '0', '1', '错误率统计设置', '', '#btn_errorset', '154');
INSERT INTO `dns_operate` VALUES ('965', '2017-05-16 16:37:28', '0', '1', '查询', '', '#btn_que', '168');
INSERT INTO `dns_operate` VALUES ('967', '2017-05-16 16:37:41', '0', '1', '查询', '', '#btn_que', '173');
INSERT INTO `dns_operate` VALUES ('969', '2017-05-16 16:38:25', '0', '1', '查询', '', '#btn_que', '212');
INSERT INTO `dns_operate` VALUES ('971', '2017-05-16 16:38:47', '0', '1', '查询', '', '#btn_que', '216');
INSERT INTO `dns_operate` VALUES ('973', '2017-05-16 16:39:07', '0', '1', '查询', '', '#btn_que', '214');
INSERT INTO `dns_operate` VALUES ('975', '2017-05-16 16:39:28', '0', '1', '查询', '', '#btn_que', '143');
INSERT INTO `dns_operate` VALUES ('979', '2017-05-16 16:40:00', '0', '1', '查询', '', '#btn_que', '160');
INSERT INTO `dns_operate` VALUES ('2241', '2017-05-31 11:48:32', '0', '1', '新增', '', '#btn_add', '92');
INSERT INTO `dns_operate` VALUES ('2242', '2017-05-31 11:48:32', '0', '1', '修改', '', '#btn_upd', '92');
INSERT INTO `dns_operate` VALUES ('2243', '2017-05-31 11:48:32', '0', '1', '删除', '', '#btn_del', '92');
INSERT INTO `dns_operate` VALUES ('2245', '2017-05-31 11:57:06', '0', '1', '新增', '', '#btn_add', '295');
INSERT INTO `dns_operate` VALUES ('2246', '2017-05-31 11:57:06', '0', '1', '修改', '', '#btn_upd', '295');
INSERT INTO `dns_operate` VALUES ('2247', '2017-05-31 11:57:06', '0', '1', '删除', '', '#btn_del', '295');
INSERT INTO `dns_operate` VALUES ('2249', '2017-05-31 11:58:54', '0', '1', '新增', '', '#btn_add', '245');
INSERT INTO `dns_operate` VALUES ('2250', '2017-05-31 11:58:54', '0', '1', '修改', '', '#btn_upd', '245');
INSERT INTO `dns_operate` VALUES ('2251', '2017-05-31 11:58:54', '0', '1', '删除', '', '#btn_del', '245');
INSERT INTO `dns_operate` VALUES ('2257', '2017-05-31 11:59:56', '0', '1', '新增', '', '#btn_add', '251');
INSERT INTO `dns_operate` VALUES ('2258', '2017-05-31 11:59:56', '0', '1', '修改', '', '#btn_upd', '251');
INSERT INTO `dns_operate` VALUES ('2259', '2017-05-31 11:59:56', '0', '1', '删除', '', '#btn_del', '251');
INSERT INTO `dns_operate` VALUES ('2261', '2017-05-31 12:02:01', '0', '1', '新增', '', '#btn_add', '257');
INSERT INTO `dns_operate` VALUES ('2262', '2017-05-31 12:02:01', '0', '1', '修改', '', '#btn_upd', '257');
INSERT INTO `dns_operate` VALUES ('2263', '2017-05-31 12:02:01', '0', '1', '删除', '', '#btn_del', '257');
INSERT INTO `dns_operate` VALUES ('2265', '2017-05-31 12:03:57', '0', '1', '新增', '', '#btn_add', '263');
INSERT INTO `dns_operate` VALUES ('2266', '2017-05-31 12:03:57', '0', '1', '修改', '', '#btn_upd', '263');
INSERT INTO `dns_operate` VALUES ('2267', '2017-05-31 12:03:57', '0', '1', '删除', '', '#btn_del', '263');
INSERT INTO `dns_operate` VALUES ('2269', '2017-05-31 12:05:31', '0', '1', '新增', '', '#btn_add', '275');
INSERT INTO `dns_operate` VALUES ('2270', '2017-05-31 12:05:31', '0', '1', '修改', '', '#btn_upd', '275');
INSERT INTO `dns_operate` VALUES ('2271', '2017-05-31 12:05:31', '0', '1', '删除', '', '#btn_del', '275');
INSERT INTO `dns_operate` VALUES ('2273', '2017-05-31 12:07:59', '0', '1', '新增', '', '#btn_add', '281');
INSERT INTO `dns_operate` VALUES ('2274', '2017-05-31 12:07:59', '0', '1', '修改', '', '#btn_upd', '281');
INSERT INTO `dns_operate` VALUES ('2275', '2017-05-31 12:07:59', '0', '1', '删除', '', '#btn_del', '281');
INSERT INTO `dns_operate` VALUES ('2277', '2017-05-31 12:09:31', '0', '1', '新增', '', '#btn_add', '283');
INSERT INTO `dns_operate` VALUES ('2278', '2017-05-31 12:09:31', '0', '1', '修改', '', '#btn_upd', '283');
INSERT INTO `dns_operate` VALUES ('2279', '2017-05-31 12:09:31', '0', '1', '删除', '', '#btn_del', '283');
INSERT INTO `dns_operate` VALUES ('2281', '2017-05-31 14:05:29', '0', '1', '新增', '', '#btn_add', '289');
INSERT INTO `dns_operate` VALUES ('2282', '2017-05-31 14:05:29', '0', '1', '修改', '', '#btn_upd', '289');
INSERT INTO `dns_operate` VALUES ('2283', '2017-05-31 14:05:29', '0', '1', '删除', '', '#btn_del', '289');
INSERT INTO `dns_operate` VALUES ('2285', '2017-05-31 14:07:31', '0', '1', '新增', '', '#btn_add', '269');
INSERT INTO `dns_operate` VALUES ('2286', '2017-05-31 14:07:31', '0', '1', '修改', '', '#btn_upd', '269');
INSERT INTO `dns_operate` VALUES ('2287', '2017-05-31 14:07:31', '0', '1', '删除', '', '#btn_del', '269');
INSERT INTO `dns_operate` VALUES ('3047', '2017-06-05 15:32:07', '0', '1', '执法材料导出', '', '#btn_enforceout', '154');
INSERT INTO `dns_operate` VALUES ('3048', '2017-06-05 15:32:07', '0', '1', '整改报告导出', '', '#btn_reportout', '154');
INSERT INTO `dns_operate` VALUES ('3049', '2017-06-05 15:32:07', '0', '1', '处罚材料导出', '', '#btn_punishout', '154');

-- ----------------------------
-- Table structure for dns_orgtype
-- ----------------------------
DROP TABLE IF EXISTS `dns_orgtype`;
CREATE TABLE `dns_orgtype` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `eleCode` varchar(42) default NULL,
  `orgCode` varchar(42) default NULL,
  `orgName` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `UK_349cchvmvumf41ggeimwras60` (`orgCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_orgtype
-- ----------------------------
INSERT INTO `dns_orgtype` VALUES ('312', '2017-06-09 17:28:45', '0', '', '001', '系统管理员');

-- ----------------------------
-- Table structure for dns_part_detail
-- ----------------------------
DROP TABLE IF EXISTS `dns_part_detail`;
CREATE TABLE `dns_part_detail` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `eleCode` varchar(255) default NULL,
  `value` bigint(20) default NULL,
  `pg_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKhcxryyurvd2keri3oku1w4bk2` (`pg_id`),
  CONSTRAINT `FKhcxryyurvd2keri3oku1w4bk2` FOREIGN KEY (`pg_id`) REFERENCES `dns_part_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_part_detail
-- ----------------------------

-- ----------------------------
-- Table structure for dns_part_group
-- ----------------------------
DROP TABLE IF EXISTS `dns_part_group`;
CREATE TABLE `dns_part_group` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `belong_name` varchar(255) default NULL,
  `belong_source` varchar(255) default NULL,
  `rg_code` varchar(42) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_part_group
-- ----------------------------

-- ----------------------------
-- Table structure for dns_preferences
-- ----------------------------
DROP TABLE IF EXISTS `dns_preferences`;
CREATE TABLE `dns_preferences` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `closing_date` datetime default NULL,
  `imp_date` datetime default NULL,
  `imp_people` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  `type` int(11) default NULL,
  `year` varchar(255) default NULL,
  `elePbcOrg_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKbdav3ffgimdqvr3gac8gur6tq` (`elePbcOrg_id`),
  CONSTRAINT `FKbdav3ffgimdqvr3gac8gur6tq` FOREIGN KEY (`elePbcOrg_id`) REFERENCES `dns_ele_pbc_org` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_preferences
-- ----------------------------

-- ----------------------------
-- Table structure for dns_punishmodel
-- ----------------------------
DROP TABLE IF EXISTS `dns_punishmodel`;
CREATE TABLE `dns_punishmodel` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `fileModelCode` varchar(255) default NULL,
  `input` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  `modelFile_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK86m26afwlpgtvqbu9ij652g79` (`modelFile_id`),
  CONSTRAINT `FK86m26afwlpgtvqbu9ij652g79` FOREIGN KEY (`modelFile_id`) REFERENCES `dns_accessory` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_punishmodel
-- ----------------------------

-- ----------------------------
-- Table structure for dns_punish_material
-- ----------------------------
DROP TABLE IF EXISTS `dns_punish_material`;
CREATE TABLE `dns_punish_material` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `create_date` datetime default NULL,
  `create_user` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` varchar(255) default NULL,
  `punishMaterialName` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  `inspectProject_id` bigint(20) default NULL,
  `modelFile_id` bigint(20) default NULL,
  `processFile_id` bigint(20) default NULL,
  `punishModel_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKdjjs1gxre48jbyip1jb2pdg2l` (`inspectProject_id`),
  KEY `FK2i8k2eh2r1trrl3n1p40cg1s0` (`modelFile_id`),
  KEY `FK1bj7fp99h1qqaq3l1qmiwd73c` (`processFile_id`),
  KEY `FKsmsktq3kge4x6grvqjew437v5` (`punishModel_id`),
  CONSTRAINT `FKsmsktq3kge4x6grvqjew437v5` FOREIGN KEY (`punishModel_id`) REFERENCES `dns_punishmodel` (`id`),
  CONSTRAINT `FK1bj7fp99h1qqaq3l1qmiwd73c` FOREIGN KEY (`processFile_id`) REFERENCES `dns_accessory` (`id`),
  CONSTRAINT `FK2i8k2eh2r1trrl3n1p40cg1s0` FOREIGN KEY (`modelFile_id`) REFERENCES `dns_accessory` (`id`),
  CONSTRAINT `FKdjjs1gxre48jbyip1jb2pdg2l` FOREIGN KEY (`inspectProject_id`) REFERENCES `dns_inspect_project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_punish_material
-- ----------------------------

-- ----------------------------
-- Table structure for dns_rectification_report
-- ----------------------------
DROP TABLE IF EXISTS `dns_rectification_report`;
CREATE TABLE `dns_rectification_report` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `create_date` datetime default NULL,
  `create_user` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` varchar(255) default NULL,
  `rectificationReportName` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  `inspectProject_id` bigint(20) default NULL,
  `modelFile_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKd0ijotlfcipbsukwquuf78568` (`inspectProject_id`),
  KEY `FKrfo9f8ymxl3jh5fa45nn1kcd` (`modelFile_id`),
  CONSTRAINT `FKrfo9f8ymxl3jh5fa45nn1kcd` FOREIGN KEY (`modelFile_id`) REFERENCES `dns_accessory` (`id`),
  CONSTRAINT `FKd0ijotlfcipbsukwquuf78568` FOREIGN KEY (`inspectProject_id`) REFERENCES `dns_inspect_project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_rectification_report
-- ----------------------------

-- ----------------------------
-- Table structure for dns_region
-- ----------------------------
DROP TABLE IF EXISTS `dns_region`;
CREATE TABLE `dns_region` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(255) NOT NULL,
  `ename` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `area_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `UK_8355750wk016gcokliuep4s1j` (`code`),
  KEY `FK801xnm2vtshpombj73eapgxr2` (`area_id`),
  CONSTRAINT `FK801xnm2vtshpombj73eapgxr2` FOREIGN KEY (`area_id`) REFERENCES `dns_area` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_region
-- ----------------------------

-- ----------------------------
-- Table structure for dns_region_menu
-- ----------------------------
DROP TABLE IF EXISTS `dns_region_menu`;
CREATE TABLE `dns_region_menu` (
  `rg_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`rg_id`,`menu_id`),
  KEY `FKi6i1a717qgefim6qjf46rb8fs` (`menu_id`),
  CONSTRAINT `FKjdeitjchk9dtpu0rdpsamop2` FOREIGN KEY (`rg_id`) REFERENCES `dns_region` (`id`),
  CONSTRAINT `FKi6i1a717qgefim6qjf46rb8fs` FOREIGN KEY (`menu_id`) REFERENCES `dns_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_region_menu
-- ----------------------------

-- ----------------------------
-- Table structure for dns_region_operate
-- ----------------------------
DROP TABLE IF EXISTS `dns_region_operate`;
CREATE TABLE `dns_region_operate` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `authtype` varchar(42) default NULL,
  `dispname` varchar(20) default NULL,
  `priorname` varchar(20) default NULL,
  `operate_id` bigint(20) default NULL,
  `region_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKdfg0thxwjwfsgsl5f7rto6qjv` (`operate_id`),
  KEY `FK12lknrqft1oyfcdrbaplo647q` (`region_id`),
  CONSTRAINT `FK12lknrqft1oyfcdrbaplo647q` FOREIGN KEY (`region_id`) REFERENCES `dns_region` (`id`),
  CONSTRAINT `FKdfg0thxwjwfsgsl5f7rto6qjv` FOREIGN KEY (`operate_id`) REFERENCES `dns_operate` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_region_operate
-- ----------------------------

-- ----------------------------
-- Table structure for dns_region_relation
-- ----------------------------
DROP TABLE IF EXISTS `dns_region_relation`;
CREATE TABLE `dns_region_relation` (
  `primary_id` bigint(20) NOT NULL,
  `secondary_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`primary_id`,`secondary_id`),
  KEY `FKcaiqn4ipvptrm7mdomi2kq8n2` (`secondary_id`),
  CONSTRAINT `FKptn2r2mgqciedgjdi72y0y6s9` FOREIGN KEY (`primary_id`) REFERENCES `dns_region` (`id`),
  CONSTRAINT `FKcaiqn4ipvptrm7mdomi2kq8n2` FOREIGN KEY (`secondary_id`) REFERENCES `dns_region` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_region_relation
-- ----------------------------

-- ----------------------------
-- Table structure for dns_res
-- ----------------------------
DROP TABLE IF EXISTS `dns_res`;
CREATE TABLE `dns_res` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `entrance` bit(1) NOT NULL,
  `info` varchar(255) default NULL,
  `resName` varchar(255) default NULL,
  `sequence` int(11) NOT NULL,
  `type` varchar(255) default NULL,
  `value` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_res
-- ----------------------------
INSERT INTO `dns_res` VALUES ('11', '2017-06-09 17:20:57', '0', '\0', null, '欢迎页面', '0', 'URL', '/manage/welcome.htm*');
INSERT INTO `dns_res` VALUES ('15', '2017-06-09 17:20:57', '0', '\0', null, '系统提醒页', '0', 'URL', '/manage/sys_tip_list.htm*');
INSERT INTO `dns_res` VALUES ('17', '2017-06-09 17:20:57', '0', '\0', null, '系统提醒删除', '0', 'URL', '/manage/sys_tip_del.htm*');
INSERT INTO `dns_res` VALUES ('18', '2017-06-09 17:20:57', '0', '\0', null, '系统提醒处理', '0', 'URL', '/manage/sys_tip_do.htm*');
INSERT INTO `dns_res` VALUES ('19', '2017-06-09 17:20:57', '0', '\0', null, '关于我们', '0', 'URL', '/manage/aboutus.htm*');
INSERT INTO `dns_res` VALUES ('20', '2017-06-09 17:20:57', '0', '\0', null, '修改密码', '0', 'URL', '/manage/user_psw_save.htm*');
INSERT INTO `dns_res` VALUES ('21', '2017-06-09 17:20:57', '0', '', null, '管理员操作授权初始化', '0', 'URL', '/manage/region_operate_init.htm*');
INSERT INTO `dns_res` VALUES ('24', '2017-06-09 17:20:57', '0', '\0', null, '操作列表加载', '0', 'URL', '/manage/region_operate_load.htm*');
INSERT INTO `dns_res` VALUES ('25', '2017-06-09 17:20:58', '0', '\0', null, '管理员操作Ajax更新', '0', 'URL', '/manage/region_operate_ajax.htm*');
INSERT INTO `dns_res` VALUES ('26', '2017-06-09 17:20:58', '0', '\0', null, '管理员操作新增', '0', 'URL', '/manage/region_operate_add.htm*');
INSERT INTO `dns_res` VALUES ('27', '2017-06-09 17:20:58', '0', '\0', null, '管理员操作删除', '0', 'URL', '/manage/region_operate_del.htm*');
INSERT INTO `dns_res` VALUES ('28', '2017-06-09 17:20:58', '0', '\0', null, '管理员操作删除', '0', 'URL', '/manage/region_operate_batchdel.htm*');
INSERT INTO `dns_res` VALUES ('29', '2017-06-09 17:20:58', '0', '', null, '区划关系授权初始化', '0', 'URL', '/manage/region_relation_init.htm*');
INSERT INTO `dns_res` VALUES ('31', '2017-06-09 17:20:58', '0', '\0', null, '区域关系授权保存', '0', 'URL', '/manage/region_relation_save.htm*');
INSERT INTO `dns_res` VALUES ('32', '2017-06-09 17:20:58', '0', '', null, '管理员列表', '0', 'URL', '/manage/region_init.htm*');
INSERT INTO `dns_res` VALUES ('34', '2017-06-09 17:20:58', '0', '\0', null, '管理员保存', '0', 'URL', '/manage/region_save.htm*');
INSERT INTO `dns_res` VALUES ('35', '2017-06-09 17:20:58', '0', '\0', null, '管理员删除', '0', 'URL', '/manage/region_del.htm*');
INSERT INTO `dns_res` VALUES ('36', '2017-06-09 17:20:58', '0', '\0', null, '管理员菜单保存', '0', 'URL', '/manage/region_menu_save.htm*');
INSERT INTO `dns_res` VALUES ('37', '2017-06-09 17:20:58', '0', '\0', null, '菜单Ajax更新', '0', 'URL', '/manage/menu_ajax.htm*');
INSERT INTO `dns_res` VALUES ('39', '2017-06-09 17:20:58', '0', '', null, '菜单列表', '0', 'URL', '/manage/menu_list.htm*');
INSERT INTO `dns_res` VALUES ('40', '2017-06-09 17:20:58', '0', '\0', null, '更新缓冲表', '0', 'URL', '/manage/update_uiconfanddetail_cache.htm*');
INSERT INTO `dns_res` VALUES ('43', '2017-06-09 17:20:59', '0', '\0', null, '界面视图新增缓存字段', '0', 'URL', '/manage/append_uicolcache.htm*');
INSERT INTO `dns_res` VALUES ('44', '2017-06-09 17:20:59', '0', '\0', null, '界面视图删除缓存字段', '0', 'URL', '/manage/delete_uicolcache.htm*');
INSERT INTO `dns_res` VALUES ('45', '2017-06-09 17:20:59', '0', '\0', null, '界面视图属性列表', '0', 'URL', '/manage/query_uiconfdetail.htm*');
INSERT INTO `dns_res` VALUES ('46', '2017-06-09 17:20:59', '0', '\0', null, '界面视图缓存列', '0', 'URL', '/manage/cache_uiconfdetail.htm*');
INSERT INTO `dns_res` VALUES ('47', '2017-06-09 17:20:59', '0', '\0', null, '界面视图预览列表', '0', 'URL', '/manage/ui_nullstore.htm*');
INSERT INTO `dns_res` VALUES ('48', '2017-06-09 17:20:59', '0', '\0', null, '界面视图内容', '0', 'URL', '/manage/query_uimanager.htm*');
INSERT INTO `dns_res` VALUES ('49', '2017-06-09 17:20:59', '0', '\0', null, '界面视图属性列要素树初始化', '0', 'URL', '/manage/ui_detail_tree.htm*');
INSERT INTO `dns_res` VALUES ('50', '2017-06-09 17:20:59', '0', '', null, '界面视图管理页面', '0', 'URL', '/manage/ui_init.htm*');
INSERT INTO `dns_res` VALUES ('51', '2017-06-09 17:20:59', '0', '\0', null, '界面视图调序缓存字段', '0', 'URL', '/manage/swap_uicolcache.htm*');
INSERT INTO `dns_res` VALUES ('52', '2017-06-09 17:20:59', '0', '\0', null, '界面视图列表', '0', 'URL', '/manage/ui_list.htm*');
INSERT INTO `dns_res` VALUES ('53', '2017-06-09 17:20:59', '0', '\0', null, '界面视图增加', '0', 'URL', '/manage/insert_uiitem.htm*');
INSERT INTO `dns_res` VALUES ('54', '2017-06-09 17:20:59', '0', '\0', null, '操作列表加载', '0', 'URL', '/manage/role_operate_load.htm*');
INSERT INTO `dns_res` VALUES ('56', '2017-06-09 17:20:59', '0', '\0', null, '管理员操作Ajax更新', '0', 'URL', '/manage/role_operate_ajax.htm*');
INSERT INTO `dns_res` VALUES ('57', '2017-06-09 17:20:59', '0', '\0', null, '角色列表加载', '0', 'URL', '/manage/role_list_grid.htm*');
INSERT INTO `dns_res` VALUES ('59', '2017-06-09 17:21:00', '0', '\0', null, '角色保存', '0', 'URL', '/manage/role_save.htm*');
INSERT INTO `dns_res` VALUES ('60', '2017-06-09 17:21:00', '0', '', null, '角色列表', '0', 'URL', '/manage/role_list.htm*');
INSERT INTO `dns_res` VALUES ('61', '2017-06-09 17:21:00', '0', '\0', null, '角色修改', '0', 'URL', '/manage/role_update.htm*');
INSERT INTO `dns_res` VALUES ('62', '2017-06-09 17:21:00', '0', '\0', null, '角色删除', '0', 'URL', '/manage/role_del.htm*');
INSERT INTO `dns_res` VALUES ('63', '2017-06-09 17:21:00', '0', '', null, '角色菜单授权', '0', 'URL', '/manage/role_menu_list.htm*');
INSERT INTO `dns_res` VALUES ('64', '2017-06-09 17:21:00', '0', '\0', null, '角色菜单授权保存', '0', 'URL', '/manage/role_menu_save.htm*');
INSERT INTO `dns_res` VALUES ('65', '2017-06-09 17:21:00', '0', '\0', null, '用户保存', '0', 'URL', '/manage/user_save.htm*');
INSERT INTO `dns_res` VALUES ('67', '2017-06-09 17:21:00', '0', '', null, '用户列表', '0', 'URL', '/manage/user_list.htm*');
INSERT INTO `dns_res` VALUES ('68', '2017-06-09 17:21:00', '0', '\0', null, '用户删除', '0', 'URL', '/manage/user_del.htm*');
INSERT INTO `dns_res` VALUES ('69', '2017-06-09 17:21:00', '0', '\0', null, '管理员修改密码', '0', 'URL', '/manage/admin_pws.htm*');
INSERT INTO `dns_res` VALUES ('70', '2017-06-09 17:21:00', '0', '\0', null, '管理员密码保存', '0', 'URL', '/manage/admin_pws_save.htm*');
INSERT INTO `dns_res` VALUES ('71', '2017-06-09 17:21:00', '0', '\0', null, '操作列表加载', '0', 'URL', '/manage/operate_load.htm*');
INSERT INTO `dns_res` VALUES ('73', '2017-06-09 17:21:00', '0', '\0', null, '操作保存', '0', 'URL', '/manage/operate_save.htm*');
INSERT INTO `dns_res` VALUES ('74', '2017-06-09 17:21:00', '0', '', null, '操作管理初始化', '0', 'URL', '/manage/operate_init.htm*');
INSERT INTO `dns_res` VALUES ('75', '2017-06-09 17:21:00', '0', '\0', null, '操作删除', '0', 'URL', '/manage/operate_del.htm*');
INSERT INTO `dns_res` VALUES ('76', '2017-06-09 17:21:00', '0', '\0', null, '机构加载', '0', 'URL', '/manage/orgtype_list.htm*');
INSERT INTO `dns_res` VALUES ('78', '2017-06-09 17:21:00', '0', '\0', null, '机构保存', '0', 'URL', '/manage/orgtype_save.htm*');
INSERT INTO `dns_res` VALUES ('79', '2017-06-09 17:21:01', '0', '\0', null, '机构删除', '0', 'URL', '/manage/orgtype_del.htm*');
INSERT INTO `dns_res` VALUES ('80', '2017-06-09 17:21:01', '0', '\0', null, '机构修改', '0', 'URL', '/manage/orgtype_update.htm*');
INSERT INTO `dns_res` VALUES ('81', '2017-06-09 17:21:01', '0', '', null, '机构列表', '0', 'URL', '/manage/orgtype_init.htm*');
INSERT INTO `dns_res` VALUES ('82', '2017-06-09 17:21:01', '0', '', null, '权限组界面初始化', '0', 'URL', '/manage/partgroup_init.htm*');
INSERT INTO `dns_res` VALUES ('84', '2017-06-09 17:21:01', '0', '\0', null, '枚举值保存', '0', 'URL', '/manage/enumerate_save.htm*');
INSERT INTO `dns_res` VALUES ('87', '2017-06-09 17:21:01', '0', '\0', null, '枚举值grid加载', '0', 'URL', '/manage/enumerate_list.htm*');
INSERT INTO `dns_res` VALUES ('88', '2017-06-09 17:21:01', '0', '\0', null, '枚举值删除', '0', 'URL', '/manage/enumerate_del.htm*');
INSERT INTO `dns_res` VALUES ('89', '2017-06-09 17:21:01', '0', '\0', null, '枚举值修改', '0', 'URL', '/manage/enumerate_update.htm*');
INSERT INTO `dns_res` VALUES ('90', '2017-06-09 17:21:01', '0', '', null, '枚举值列表', '0', 'URL', '/manage/enumerate_init.htm*');
INSERT INTO `dns_res` VALUES ('91', '2017-06-09 17:21:01', '0', '\0', null, '金融机构修改', '0', 'URL', '/manage/finance_org_update.htm*');
INSERT INTO `dns_res` VALUES ('93', '2017-06-09 17:21:01', '0', '\0', null, '金融机构删除', '0', 'URL', '/manage/finance_org_del.htm*');
INSERT INTO `dns_res` VALUES ('94', '2017-06-09 17:21:01', '0', '\0', null, '金融机构保存', '0', 'URL', '/manage/finance_org_save.htm*');
INSERT INTO `dns_res` VALUES ('95', '2017-06-09 17:21:01', '0', '', null, '金融机构', '0', 'URL', '/manage/finance_org_init.htm*');
INSERT INTO `dns_res` VALUES ('96', '2017-06-09 17:21:01', '0', '\0', null, '金融机构加载', '0', 'URL', '/manage/finance_org_list.htm*');
INSERT INTO `dns_res` VALUES ('97', '2017-06-09 17:21:01', '0', '\0', null, '要素树初始化', '0', 'URL', '/manage/element_tree.htm*');
INSERT INTO `dns_res` VALUES ('99', '2017-06-09 17:21:01', '0', '\0', null, '要素管理页面', '0', 'URL', '/manage/element_list.htm*');
INSERT INTO `dns_res` VALUES ('100', '2017-06-09 17:21:02', '0', '', null, '要素管理页面', '0', 'URL', '/manage/element_init.htm*');
INSERT INTO `dns_res` VALUES ('101', '2017-06-09 17:21:02', '0', '\0', null, '要素保存', '0', 'URL', '/manage/element_save.htm*');
INSERT INTO `dns_res` VALUES ('102', '2017-06-09 17:21:02', '0', '\0', null, '要素修改', '0', 'URL', '/manage/element_update.htm*');
INSERT INTO `dns_res` VALUES ('103', '2017-06-09 17:21:02', '0', '\0', null, '要素删除', '0', 'URL', '/manage/element_del.htm*');
INSERT INTO `dns_res` VALUES ('104', '2017-06-09 17:21:02', '0', '', null, '人行机构', '0', 'URL', '/manage/pbc_org_init.htm*');
INSERT INTO `dns_res` VALUES ('106', '2017-06-09 17:21:02', '0', '\0', null, '人行机构加载', '0', 'URL', '/manage/pbc_org_list.htm*');
INSERT INTO `dns_res` VALUES ('107', '2017-06-09 17:21:02', '0', '\0', null, '人行机构保存', '0', 'URL', '/manage/pbc_org_save.htm*');
INSERT INTO `dns_res` VALUES ('108', '2017-06-09 17:21:02', '0', '\0', null, '人行机构修改', '0', 'URL', '/manage/pbc_org_update.htm*');
INSERT INTO `dns_res` VALUES ('109', '2017-06-09 17:21:02', '0', '\0', null, '人行机构删除', '0', 'URL', '/manage/pbc_org_del.htm*');
INSERT INTO `dns_res` VALUES ('110', '2017-06-09 17:21:02', '0', '', null, '银行债券管理', '0', 'URL', '/manage/bondType_bank_init.htm*');
INSERT INTO `dns_res` VALUES ('112', '2017-06-09 17:21:02', '0', '\0', null, '银行债券管理加载', '0', 'URL', '/manage/bondType_bank_list.htm*');
INSERT INTO `dns_res` VALUES ('113', '2017-06-09 17:21:02', '0', '\0', null, '银行债券管理保存', '0', 'URL', '/manage/bondType_bank_save.htm*');
INSERT INTO `dns_res` VALUES ('114', '2017-06-09 17:21:02', '0', '\0', null, '银行债券管理修改', '0', 'URL', '/manage/bondType_bank_upddate.htm*');
INSERT INTO `dns_res` VALUES ('115', '2017-06-09 17:21:02', '0', '\0', null, '银行债券管理删除', '0', 'URL', '/manage/bondType_bank_del.htm*');
INSERT INTO `dns_res` VALUES ('116', '2017-06-09 17:21:02', '0', '', null, '企业债券管理', '0', 'URL', '/manage/bondType_enterprise_init.htm*');
INSERT INTO `dns_res` VALUES ('118', '2017-06-09 17:21:03', '0', '\0', null, '企业债券管理加载', '0', 'URL', '/manage/bondType_enterprise_list.htm*');
INSERT INTO `dns_res` VALUES ('119', '2017-06-09 17:21:03', '0', '\0', null, '企业债券管理保存', '0', 'URL', '/manage/bondType_enterprise_save.htm*');
INSERT INTO `dns_res` VALUES ('120', '2017-06-09 17:21:03', '0', '\0', null, '企业债券管理修改', '0', 'URL', '/manage/bondType_enterprise_upddate.htm*');
INSERT INTO `dns_res` VALUES ('121', '2017-06-09 17:21:03', '0', '\0', null, '企业债券管理删除', '0', 'URL', '/manage/bondType_enterprise_del.htm*');
INSERT INTO `dns_res` VALUES ('122', '2017-06-09 17:21:03', '0', '', null, '执法模板库', '0', 'URL', '/manage/enforcemodel_init.htm*');
INSERT INTO `dns_res` VALUES ('124', '2017-06-09 17:21:03', '0', '', null, '论坛', '0', 'URL', '/manage/casedata_maintenance_init.htm*');
INSERT INTO `dns_res` VALUES ('127', '2017-06-09 17:21:03', '0', '\0', null, '案例维护加载', '0', 'URL', '/manage/casedata_maintenance_list.htm*');
INSERT INTO `dns_res` VALUES ('128', '2017-06-09 17:21:03', '0', '', null, '案例库', '0', 'URL', '/manage/casedata_init.htm*');
INSERT INTO `dns_res` VALUES ('130', '2017-06-09 17:21:03', '0', '\0', null, '案例库加载', '0', 'URL', '/manage/casedata_list.htm*');
INSERT INTO `dns_res` VALUES ('131', '2017-06-09 17:21:03', '0', '\0', null, '案例保存', '0', 'URL', '/manage/casedata_save.htm*');
INSERT INTO `dns_res` VALUES ('132', '2017-06-09 17:21:03', '0', '\0', null, '案例删除', '0', 'URL', '/manage/casedata_del.htm*');
INSERT INTO `dns_res` VALUES ('133', '2017-06-09 17:21:03', '0', '', null, '文档模板库', '0', 'URL', '/manage/filemodel_init.htm*');
INSERT INTO `dns_res` VALUES ('135', '2017-06-09 17:21:03', '0', '\0', null, '文档模板库初始化', '0', 'URL', '/manage/filemodel_list.htm*');
INSERT INTO `dns_res` VALUES ('136', '2017-06-09 17:21:03', '0', '\0', null, '文档模板库增加', '0', 'URL', '/manage/filemodel_save.htm*');
INSERT INTO `dns_res` VALUES ('137', '2017-06-09 17:21:03', '0', '\0', null, '文档模板库删除', '0', 'URL', '/manage/filemodel_del.htm*');
INSERT INTO `dns_res` VALUES ('138', '2017-06-09 17:21:03', '0', '', null, '表单模板库', '0', 'URL', '/manage/formmodel_init.htm*');
INSERT INTO `dns_res` VALUES ('140', '2017-06-09 17:21:04', '0', '', null, '制度库', '0', 'URL', '/stats/systemlibrary_init.htm*');
INSERT INTO `dns_res` VALUES ('144', '2017-06-09 17:21:04', '0', '\0', null, '制度加载', '0', 'URL', '/stats/systemlibrary_list.htm*');
INSERT INTO `dns_res` VALUES ('145', '2017-06-09 17:21:04', '0', '\0', null, '制度保存', '0', 'URL', '/stats/systemlibrary_save.htm*');
INSERT INTO `dns_res` VALUES ('146', '2017-06-09 17:21:04', '0', '\0', null, '制度删除', '0', 'URL', '/stats/systemlibrary_del.htm*');
INSERT INTO `dns_res` VALUES ('147', '2017-06-09 17:21:04', '0', '', null, '年度总结初始化', '0', 'URL', '/manage/annual_review_init.htm*');
INSERT INTO `dns_res` VALUES ('150', '2017-06-09 17:21:04', '0', '', null, '部门管理页面', '0', 'URL', '/inspect/department_init.htm*');
INSERT INTO `dns_res` VALUES ('152', '2017-06-09 17:21:04', '0', '', null, '材料管理', '0', 'URL', '/manage/enforcematerial_init.htm*');
INSERT INTO `dns_res` VALUES ('155', '2017-06-09 17:21:04', '0', '', null, '检查项目', '0', 'URL', '/manage/inspectproject_init.htm*');
INSERT INTO `dns_res` VALUES ('157', '2017-06-09 17:21:04', '0', '', null, '检查计划', '0', 'URL', '/manage/inspectplan_init.htm*');
INSERT INTO `dns_res` VALUES ('159', '2017-06-09 17:21:04', '0', '', null, '自查报告', '0', 'URL', '/manage/selfreport_init.htm*');
INSERT INTO `dns_res` VALUES ('161', '2017-06-09 17:21:04', '0', '\0', null, '自查报告删除', '0', 'URL', '/manage/selfreport_del.htm*');
INSERT INTO `dns_res` VALUES ('162', '2017-06-09 17:21:04', '0', '\0', null, '自查报告加载', '0', 'URL', '/manage/selfreport_list.htm*');
INSERT INTO `dns_res` VALUES ('163', '2017-06-09 17:21:04', '0', '\0', null, '自查报告导入', '0', 'URL', '/manage/selfreport_save.htm*');
INSERT INTO `dns_res` VALUES ('164', '2017-06-09 17:21:04', '0', '', null, '非现场监督项目', '0', 'URL', '/manage/offsitesurveillance_init.htm*');
INSERT INTO `dns_res` VALUES ('167', '2017-06-09 17:21:04', '0', '', null, '非现场监督项目', '0', 'URL', '/manage/offsitesurveillance_init_check.htm*');
INSERT INTO `dns_res` VALUES ('169', '2017-06-09 17:21:04', '0', '\0', null, '非现场监督项目加载', '0', 'URL', '/manage/offsitesurveillance_list.htm*');
INSERT INTO `dns_res` VALUES ('170', '2017-06-09 17:21:04', '0', '\0', null, '非现场监督项目保存', '0', 'URL', '/manage/offsitesurveillance_save.htm*');
INSERT INTO `dns_res` VALUES ('171', '2017-06-09 17:21:05', '0', '\0', null, '非现场监督项目删除', '0', 'URL', '/manage/offsitesurveillance_del.htm*');
INSERT INTO `dns_res` VALUES ('172', '2017-06-09 17:21:05', '0', '', null, '非现场监督项目', '0', 'URL', '/manage/offsitesurveillance_init_addmaterials.htm*');
INSERT INTO `dns_res` VALUES ('174', '2017-06-09 17:21:05', '0', '', null, '数据管理', '0', 'URL', '/manage/datamanage_init.htm*');
INSERT INTO `dns_res` VALUES ('178', '2017-06-09 17:21:05', '0', '', null, '数据管理', '0', 'URL', '/manage/nonfinancialdatamanage_init.htm*');
INSERT INTO `dns_res` VALUES ('181', '2017-06-09 17:21:05', '0', '\0', null, '债券评级修改', '0', 'URL', '/stats/bond_rating_update.htm*');
INSERT INTO `dns_res` VALUES ('183', '2017-06-09 17:21:05', '0', '', null, '债券评级', '0', 'URL', '/stats/bond_rating_init.htm*');
INSERT INTO `dns_res` VALUES ('184', '2017-06-09 17:21:05', '0', '\0', null, '债券评级加载', '0', 'URL', '/stats/bond_rating_list.htm*');
INSERT INTO `dns_res` VALUES ('185', '2017-06-09 17:21:05', '0', '\0', null, '债券评级删除', '0', 'URL', '/stats/bond_rating_del.htm*');
INSERT INTO `dns_res` VALUES ('186', '2017-06-09 17:21:05', '0', '\0', null, '债券评级保存', '0', 'URL', '/stats/bond_rating_save.htm*');
INSERT INTO `dns_res` VALUES ('187', '2017-06-09 17:21:05', '0', '', null, '担保人评级', '0', 'URL', '/stats/guarantor_rating_init.htm*');
INSERT INTO `dns_res` VALUES ('189', '2017-06-09 17:21:05', '0', '\0', null, '担保人评级加载', '0', 'URL', '/stats/guarantor_rating_list.htm*');
INSERT INTO `dns_res` VALUES ('190', '2017-06-09 17:21:05', '0', '\0', null, '担保人评级保存', '0', 'URL', '/stats/guarantor_rating_save.htm*');
INSERT INTO `dns_res` VALUES ('191', '2017-06-09 17:21:05', '0', '\0', null, '担保人评级修改', '0', 'URL', '/stats/guarantor_rating_update.htm*');
INSERT INTO `dns_res` VALUES ('192', '2017-06-09 17:21:05', '0', '\0', null, '担保人评级删除', '0', 'URL', '/stats/guarantor_rating_del.htm*');
INSERT INTO `dns_res` VALUES ('193', '2017-06-09 17:21:05', '0', '', null, '主体评级', '0', 'URL', '/stats/subjective_rating_init.htm*');
INSERT INTO `dns_res` VALUES ('195', '2017-06-09 17:21:06', '0', '\0', null, '主体评级加载', '0', 'URL', '/stats/subjective_rating_list.htm*');
INSERT INTO `dns_res` VALUES ('196', '2017-06-09 17:21:06', '0', '\0', null, '主体评级保存', '0', 'URL', '/stats/subjective_rating_save.htm*');
INSERT INTO `dns_res` VALUES ('197', '2017-06-09 17:21:06', '0', '\0', null, '主体评级修改', '0', 'URL', '/stats/subjective_rating_update.htm*');
INSERT INTO `dns_res` VALUES ('198', '2017-06-09 17:21:06', '0', '\0', null, '主体评级删除', '0', 'URL', '/stats/subjective_rating_del.htm*');
INSERT INTO `dns_res` VALUES ('199', '2017-06-09 17:21:06', '0', '', null, '发行人企业性质', '0', 'URL', '/stats/issuerenterprisenature_init.htm*');
INSERT INTO `dns_res` VALUES ('201', '2017-06-09 17:21:06', '0', '\0', null, '发行人企业性质加载', '0', 'URL', '/stats/issuerenterprisenature_list.htm*');
INSERT INTO `dns_res` VALUES ('202', '2017-06-09 17:21:06', '0', '\0', null, '发行人企业性质保存', '0', 'URL', '/stats/issuerenterprisenatureubjective_save.htm*');
INSERT INTO `dns_res` VALUES ('203', '2017-06-09 17:21:06', '0', '\0', null, '发行人企业性质修改', '0', 'URL', '/stats/issuerenterprisenature_update.htm*');
INSERT INTO `dns_res` VALUES ('204', '2017-06-09 17:21:06', '0', '\0', null, '发行人企业性质删除', '0', 'URL', '/stats/issuerenterprisenature_del.htm*');
INSERT INTO `dns_res` VALUES ('205', '2017-06-09 17:21:06', '0', '', null, '交易数据', '0', 'URL', '/stats/transactiondata_init.htm*');
INSERT INTO `dns_res` VALUES ('208', '2017-06-09 17:21:06', '0', '\0', null, '交易数据加载', '0', 'URL', '/stats/transactiondata_list.htm*');
INSERT INTO `dns_res` VALUES ('209', '2017-06-09 17:21:06', '0', '\0', null, '交易数据保存', '0', 'URL', '/stats/transactiondata_save.htm*');
INSERT INTO `dns_res` VALUES ('210', '2017-06-09 17:21:06', '0', '', null, '风险监测', '0', 'URL', '/stats/riskmonitor_check_init.htm*');
INSERT INTO `dns_res` VALUES ('213', '2017-06-09 17:21:06', '0', '', null, '风险监测', '0', 'URL', '/stats/riskmonitor_select_init.htm*');
INSERT INTO `dns_res` VALUES ('215', '2017-06-09 17:21:06', '0', '\0', null, '风险监测加载', '0', 'URL', '/stats/riskmonitor_list.htm*');
INSERT INTO `dns_res` VALUES ('217', '2017-06-09 17:21:06', '0', '\0', null, '风险监测保存', '0', 'URL', '/stats/riskmonitor_save.htm*');
INSERT INTO `dns_res` VALUES ('218', '2017-06-09 17:21:06', '0', '', null, '风险监测', '0', 'URL', '/stats/riskmonitor_init.htm*');
INSERT INTO `dns_res` VALUES ('219', '2017-06-09 17:21:06', '0', '\0', null, '风险监测删除', '0', 'URL', '/stats/riskmonitor_del.htm*');
INSERT INTO `dns_res` VALUES ('220', '2017-06-09 17:21:07', '0', '', null, '处罚模板库', '0', 'URL', '/manage/punishmodel_init.htm*');
INSERT INTO `dns_res` VALUES ('222', '2017-06-09 17:21:07', '0', '', null, '发行人行业管理', '0', 'URL', '/manage/issuer_enterprise_init.htm*');
INSERT INTO `dns_res` VALUES ('224', '2017-06-09 17:21:07', '0', '\0', null, '发行人行业管理加载', '0', 'URL', '/manage/issuer_enterprise_list.htm*');
INSERT INTO `dns_res` VALUES ('225', '2017-06-09 17:21:07', '0', '\0', null, '发行人行业管理保存', '0', 'URL', '/manage/issuer_enterprise_save.htm*');
INSERT INTO `dns_res` VALUES ('226', '2017-06-09 17:21:07', '0', '\0', null, '发行人行业管理修改', '0', 'URL', '/manage/issuer_enterprise_upddate.htm*');
INSERT INTO `dns_res` VALUES ('227', '2017-06-09 17:21:07', '0', '\0', null, '发行人行业管理删除', '0', 'URL', '/manage/issuer_enterprise_del.htm*');
INSERT INTO `dns_res` VALUES ('228', '2017-06-09 17:21:07', '0', '', null, '信息披露', '0', 'URL', '/stats/informationdisclosure_init.htm*');
INSERT INTO `dns_res` VALUES ('231', '2017-06-09 17:21:07', '0', '', null, '信息披露审核', '0', 'URL', '/stats/informationdisclosure_check_init.htm*');
INSERT INTO `dns_res` VALUES ('233', '2017-06-09 17:21:07', '0', '\0', null, '信息披露加载', '0', 'URL', '/stats/informationdisclosure_list.htm*');
INSERT INTO `dns_res` VALUES ('234', '2017-06-09 17:21:07', '0', '\0', null, '信息披露保存', '0', 'URL', '/stats/informationdisclosure_save.htm*');
INSERT INTO `dns_res` VALUES ('235', '2017-06-09 17:21:07', '0', '\0', null, '信息披露删除', '0', 'URL', '/stats/informationdisclosure_del.htm*');
INSERT INTO `dns_res` VALUES ('236', '2017-06-09 17:21:07', '0', '', null, '公告栏审核', '0', 'URL', '/stats/noticeboard_check_init.htm*');
INSERT INTO `dns_res` VALUES ('239', '2017-06-09 17:21:07', '0', '\0', null, '公告栏保存', '0', 'URL', '/stats/noticeboard_save.htm*');
INSERT INTO `dns_res` VALUES ('241', '2017-06-09 17:21:07', '0', '', null, '公告栏登记', '0', 'URL', '/stats/noticeboard_init.htm*');
INSERT INTO `dns_res` VALUES ('242', '2017-06-09 17:21:07', '0', '\0', null, '公告栏加载', '0', 'URL', '/stats/noticeboard_list.htm*');
INSERT INTO `dns_res` VALUES ('243', '2017-06-09 17:21:07', '0', '\0', null, '公告栏删除', '0', 'URL', '/stats/noticeboard_del.htm*');
INSERT INTO `dns_res` VALUES ('244', '2017-06-09 17:21:08', '0', '', null, '错误性质', '0', 'URL', '/stats/error_property_init.htm*');
INSERT INTO `dns_res` VALUES ('246', '2017-06-09 17:21:08', '0', '\0', null, '错误性质加载', '0', 'URL', '/stats/error_property_list.htm*');
INSERT INTO `dns_res` VALUES ('247', '2017-06-09 17:21:08', '0', '\0', null, '错误性质保存', '0', 'URL', '/stats/error_property_save.htm*');
INSERT INTO `dns_res` VALUES ('248', '2017-06-09 17:21:08', '0', '\0', null, '错误性质修改', '0', 'URL', '/stats/error_property_update.htm*');
INSERT INTO `dns_res` VALUES ('249', '2017-06-09 17:21:08', '0', '\0', null, '错误性质删除', '0', 'URL', '/stats/error_property_del.htm*');
INSERT INTO `dns_res` VALUES ('250', '2017-06-09 17:21:08', '0', '', null, '错误原因分类', '0', 'URL', '/stats/errors_class_init.htm*');
INSERT INTO `dns_res` VALUES ('252', '2017-06-09 17:21:08', '0', '\0', null, '错误分类加载', '0', 'URL', '/stats/errors_class_list.htm*');
INSERT INTO `dns_res` VALUES ('253', '2017-06-09 17:21:08', '0', '\0', null, '错误原因分类保存', '0', 'URL', '/stats/errors_class_save.htm*');
INSERT INTO `dns_res` VALUES ('254', '2017-06-09 17:21:08', '0', '\0', null, '错误原因分类修改', '0', 'URL', '/stats/errors_class_update.htm*');
INSERT INTO `dns_res` VALUES ('255', '2017-06-09 17:21:08', '0', '\0', null, '错误原因分类删除', '0', 'URL', '/stats/errors_class_del.htm*');
INSERT INTO `dns_res` VALUES ('256', '2017-06-09 17:21:08', '0', '', null, '非金融企业', '0', 'URL', '/stats/nonfinancialbusiness_init.htm*');
INSERT INTO `dns_res` VALUES ('258', '2017-06-09 17:21:08', '0', '\0', null, '非金融企业加载', '0', 'URL', '/stats/nonfinancialbusiness_list.htm*');
INSERT INTO `dns_res` VALUES ('259', '2017-06-09 17:21:08', '0', '\0', null, '非金融企业保存', '0', 'URL', '/stats/nonfinancialbusiness_save.htm*');
INSERT INTO `dns_res` VALUES ('260', '2017-06-09 17:21:08', '0', '\0', null, '非金融企业修改', '0', 'URL', '/stats/nonfinancialbusiness_update.htm*');
INSERT INTO `dns_res` VALUES ('261', '2017-06-09 17:21:08', '0', '\0', null, '非金融企业删除', '0', 'URL', '/stats/nonfinancialbusiness_del.htm*');
INSERT INTO `dns_res` VALUES ('262', '2017-06-09 17:21:08', '0', '', null, '人行执法人员库', '0', 'URL', '/stats/lawenforcementofficers_init.htm*');
INSERT INTO `dns_res` VALUES ('264', '2017-06-09 17:21:08', '0', '\0', null, '人行执法人员库加载', '0', 'URL', '/stats/lawenforcementofficers_list.htm*');
INSERT INTO `dns_res` VALUES ('265', '2017-06-09 17:21:08', '0', '\0', null, '人行执法人员保存', '0', 'URL', '/stats/lawenforcementofficers_save.htm*');
INSERT INTO `dns_res` VALUES ('266', '2017-06-09 17:21:08', '0', '\0', null, '人行执法人员库修改', '0', 'URL', '/stats/lawenforcementofficers_update.htm*');
INSERT INTO `dns_res` VALUES ('267', '2017-06-09 17:21:08', '0', '\0', null, '人行执法人员删除', '0', 'URL', '/stats/lawenforcementofficers_del.htm*');
INSERT INTO `dns_res` VALUES ('268', '2017-06-09 17:21:08', '0', '\0', null, '参数设置修改', '0', 'URL', '/stats/preferences_update.htm*');
INSERT INTO `dns_res` VALUES ('270', '2017-06-09 17:21:10', '0', '\0', null, '参数设置保存', '0', 'URL', '/stats/preferences_save.htm*');
INSERT INTO `dns_res` VALUES ('271', '2017-06-09 17:21:10', '0', '\0', null, '参数设置删除', '0', 'URL', '/stats/preferences_del.htm*');
INSERT INTO `dns_res` VALUES ('272', '2017-06-09 17:21:10', '0', '\0', null, '参数设置加载', '0', 'URL', '/stats/preferences_list.htm*');
INSERT INTO `dns_res` VALUES ('273', '2017-06-09 17:21:10', '0', '', null, '参数设置', '0', 'URL', '/stats/preferences_init.htm*');
INSERT INTO `dns_res` VALUES ('274', '2017-06-09 17:21:10', '0', '', null, '银行分类', '0', 'URL', '/stats/bankclassification_init.htm*');
INSERT INTO `dns_res` VALUES ('276', '2017-06-09 17:21:10', '0', '\0', null, '银行分类加载', '0', 'URL', '/stats/bankclassification_list.htm*');
INSERT INTO `dns_res` VALUES ('277', '2017-06-09 17:21:10', '0', '\0', null, '银行分类保存', '0', 'URL', '/stats/bankclassification_save.htm*');
INSERT INTO `dns_res` VALUES ('278', '2017-06-09 17:21:10', '0', '\0', null, '银行分类修改', '0', 'URL', '/stats/bankclassification_upddate.htm*');
INSERT INTO `dns_res` VALUES ('279', '2017-06-09 17:21:11', '0', '\0', null, '银行分类删除', '0', 'URL', '/stats/bankclassification_del.htm*');
INSERT INTO `dns_res` VALUES ('280', '2017-06-09 17:21:11', '0', '', null, '检查方式管理', '0', 'URL', '/manage/inspecttype_init.htm*');
INSERT INTO `dns_res` VALUES ('282', '2017-06-09 17:21:11', '0', '', null, '数据属性', '0', 'URL', '/manage/dataAttribute_init.htm*');
INSERT INTO `dns_res` VALUES ('284', '2017-06-09 17:21:11', '0', '\0', null, '数据属性加载', '0', 'URL', '/manage/dataAttribute_list.htm*');
INSERT INTO `dns_res` VALUES ('285', '2017-06-09 17:21:11', '0', '\0', null, '数据属性保存', '0', 'URL', '/manage/dataAttribute_save.htm*');
INSERT INTO `dns_res` VALUES ('286', '2017-06-09 17:21:11', '0', '\0', null, '数据属性修改', '0', 'URL', '/manage/dataAttribute_upddate.htm*');
INSERT INTO `dns_res` VALUES ('287', '2017-06-09 17:21:11', '0', '\0', null, '数据属性删除', '0', 'URL', '/manage/dataAttribute_del.htm*');
INSERT INTO `dns_res` VALUES ('288', '2017-06-09 17:21:11', '0', '\0', null, '币种管理修改', '0', 'URL', '/manage/currency_upddate.htm*');
INSERT INTO `dns_res` VALUES ('290', '2017-06-09 17:21:11', '0', '\0', null, '币种管理删除', '0', 'URL', '/manage/currency_del.htm*');
INSERT INTO `dns_res` VALUES ('291', '2017-06-09 17:21:11', '0', '\0', null, '币种管理加载', '0', 'URL', '/manage/currency_list.htm*');
INSERT INTO `dns_res` VALUES ('292', '2017-06-09 17:21:11', '0', '', null, '币种管理', '0', 'URL', '/manage/currency_init.htm*');
INSERT INTO `dns_res` VALUES ('293', '2017-06-09 17:21:11', '0', '\0', null, '币种管理保存', '0', 'URL', '/manage/currency_save.htm*');
INSERT INTO `dns_res` VALUES ('294', '2017-06-09 17:21:11', '0', '', null, '表单分类', '0', 'URL', '/manage/form_classification_init.htm*');
INSERT INTO `dns_res` VALUES ('296', '2017-06-09 17:21:11', '0', '\0', null, '表单分类加载', '0', 'URL', '/manage/form_classification_list.htm*');
INSERT INTO `dns_res` VALUES ('297', '2017-06-09 17:21:11', '0', '\0', null, '表单分类保存', '0', 'URL', '/manage/form_classification_save.htm*');
INSERT INTO `dns_res` VALUES ('298', '2017-06-09 17:21:11', '0', '\0', null, '表单分类修改', '0', 'URL', '/manage/form_classification_upddate.htm*');
INSERT INTO `dns_res` VALUES ('299', '2017-06-09 17:21:11', '0', '\0', null, '表单分类删除', '0', 'URL', '/manage/form_classification_del.htm*');
INSERT INTO `dns_res` VALUES ('300', '2017-06-09 17:21:11', '0', '', null, '监督方式', '0', 'URL', '/stats/monitor_mode_init.htm*');
INSERT INTO `dns_res` VALUES ('302', '2017-06-09 17:21:11', '0', '\0', null, '监督方式加载', '0', 'URL', '/stats/monitor_mode_list.htm*');
INSERT INTO `dns_res` VALUES ('303', '2017-06-09 17:21:12', '0', '\0', null, '监督方式保存', '0', 'URL', '/stats/monitor_mode_save.htm*');
INSERT INTO `dns_res` VALUES ('304', '2017-06-09 17:21:12', '0', '\0', null, '监督方式修改', '0', 'URL', '/stats/monitor_mode_update.htm*');
INSERT INTO `dns_res` VALUES ('305', '2017-06-09 17:21:12', '0', '\0', null, '监督方式删除', '0', 'URL', '/stats/monitor_mode_del.htm*');
INSERT INTO `dns_res` VALUES ('306', '2017-06-09 17:21:12', '0', '', null, '信息分类', '0', 'URL', '/manage/informationclassification_init.htm*');
INSERT INTO `dns_res` VALUES ('308', '2017-06-09 17:21:12', '0', '', null, '公告分类', '0', 'URL', '/manage/noticeclassification_init.htm*');

-- ----------------------------
-- Table structure for dns_riskmonitor
-- ----------------------------
DROP TABLE IF EXISTS `dns_riskmonitor`;
CREATE TABLE `dns_riskmonitor` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `company_name` varchar(255) default NULL,
  `input_date` datetime default NULL,
  `input_man` varchar(255) default NULL,
  `ischeck` int(11) default NULL,
  `issubmit` int(11) default NULL,
  `iswarning` int(11) default NULL,
  `monitoring_type` int(11) default NULL,
  `remark` varchar(255) default NULL,
  `submitting_agency` varchar(255) default NULL,
  `monitoring_file_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKaewo8abxehrhf8y1sa0jnmomc` (`monitoring_file_id`),
  CONSTRAINT `FKaewo8abxehrhf8y1sa0jnmomc` FOREIGN KEY (`monitoring_file_id`) REFERENCES `dns_accessory` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_riskmonitor
-- ----------------------------

-- ----------------------------
-- Table structure for dns_role
-- ----------------------------
DROP TABLE IF EXISTS `dns_role`;
CREATE TABLE `dns_role` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `builtin` bit(1) default '\0',
  `info` varchar(255) default NULL,
  `rg_code` varchar(42) NOT NULL default '000000',
  `roleCode` varchar(255) default NULL,
  `roleName` varchar(255) default NULL,
  `type` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_role
-- ----------------------------
INSERT INTO `dns_role` VALUES ('7', '2017-06-09 17:20:18', '0', '', null, '000000', 'ROLE_ADMIN_SUPER', '超级管理员角色', 'MANAGE');
INSERT INTO `dns_role` VALUES ('8', '2017-06-09 17:20:18', '0', '', null, '000000', 'ROLE_PUBLIC_SUPER', '前端超级角色', 'PUBLIC');
INSERT INTO `dns_role` VALUES ('310', '2017-06-09 17:23:19', '0', '\0', '', '000000', '001', 'admin', 'MANAGE');

-- ----------------------------
-- Table structure for dns_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `dns_role_menu`;
CREATE TABLE `dns_role_menu` (
  `role_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`role_id`,`menu_id`),
  KEY `FKs1j6s6x5pitbagtshe5ciakjc` (`menu_id`),
  CONSTRAINT `FK1pqaga31e3s52pylg9lvls4hj` FOREIGN KEY (`role_id`) REFERENCES `dns_role` (`id`),
  CONSTRAINT `FKs1j6s6x5pitbagtshe5ciakjc` FOREIGN KEY (`menu_id`) REFERENCES `dns_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_role_menu
-- ----------------------------
INSERT INTO `dns_role_menu` VALUES ('7', '14');
INSERT INTO `dns_role_menu` VALUES ('310', '14');
INSERT INTO `dns_role_menu` VALUES ('7', '16');
INSERT INTO `dns_role_menu` VALUES ('310', '16');
INSERT INTO `dns_role_menu` VALUES ('7', '23');
INSERT INTO `dns_role_menu` VALUES ('310', '23');
INSERT INTO `dns_role_menu` VALUES ('7', '30');
INSERT INTO `dns_role_menu` VALUES ('310', '30');
INSERT INTO `dns_role_menu` VALUES ('7', '33');
INSERT INTO `dns_role_menu` VALUES ('310', '33');
INSERT INTO `dns_role_menu` VALUES ('7', '38');
INSERT INTO `dns_role_menu` VALUES ('310', '38');
INSERT INTO `dns_role_menu` VALUES ('7', '42');
INSERT INTO `dns_role_menu` VALUES ('310', '42');
INSERT INTO `dns_role_menu` VALUES ('7', '55');
INSERT INTO `dns_role_menu` VALUES ('310', '55');
INSERT INTO `dns_role_menu` VALUES ('7', '58');
INSERT INTO `dns_role_menu` VALUES ('310', '58');
INSERT INTO `dns_role_menu` VALUES ('7', '66');
INSERT INTO `dns_role_menu` VALUES ('310', '66');
INSERT INTO `dns_role_menu` VALUES ('7', '72');
INSERT INTO `dns_role_menu` VALUES ('310', '72');
INSERT INTO `dns_role_menu` VALUES ('7', '77');
INSERT INTO `dns_role_menu` VALUES ('310', '77');
INSERT INTO `dns_role_menu` VALUES ('7', '83');
INSERT INTO `dns_role_menu` VALUES ('310', '83');
INSERT INTO `dns_role_menu` VALUES ('7', '86');
INSERT INTO `dns_role_menu` VALUES ('310', '86');
INSERT INTO `dns_role_menu` VALUES ('7', '92');
INSERT INTO `dns_role_menu` VALUES ('310', '92');
INSERT INTO `dns_role_menu` VALUES ('7', '98');
INSERT INTO `dns_role_menu` VALUES ('310', '98');
INSERT INTO `dns_role_menu` VALUES ('7', '105');
INSERT INTO `dns_role_menu` VALUES ('310', '105');
INSERT INTO `dns_role_menu` VALUES ('7', '111');
INSERT INTO `dns_role_menu` VALUES ('310', '111');
INSERT INTO `dns_role_menu` VALUES ('7', '117');
INSERT INTO `dns_role_menu` VALUES ('310', '117');
INSERT INTO `dns_role_menu` VALUES ('7', '123');
INSERT INTO `dns_role_menu` VALUES ('310', '123');
INSERT INTO `dns_role_menu` VALUES ('7', '126');
INSERT INTO `dns_role_menu` VALUES ('7', '129');
INSERT INTO `dns_role_menu` VALUES ('7', '134');
INSERT INTO `dns_role_menu` VALUES ('310', '134');
INSERT INTO `dns_role_menu` VALUES ('7', '139');
INSERT INTO `dns_role_menu` VALUES ('310', '139');
INSERT INTO `dns_role_menu` VALUES ('7', '143');
INSERT INTO `dns_role_menu` VALUES ('7', '149');
INSERT INTO `dns_role_menu` VALUES ('7', '151');
INSERT INTO `dns_role_menu` VALUES ('7', '154');
INSERT INTO `dns_role_menu` VALUES ('7', '156');
INSERT INTO `dns_role_menu` VALUES ('7', '158');
INSERT INTO `dns_role_menu` VALUES ('7', '160');
INSERT INTO `dns_role_menu` VALUES ('7', '166');
INSERT INTO `dns_role_menu` VALUES ('7', '168');
INSERT INTO `dns_role_menu` VALUES ('7', '173');
INSERT INTO `dns_role_menu` VALUES ('7', '177');
INSERT INTO `dns_role_menu` VALUES ('7', '180');
INSERT INTO `dns_role_menu` VALUES ('7', '182');
INSERT INTO `dns_role_menu` VALUES ('310', '182');
INSERT INTO `dns_role_menu` VALUES ('7', '188');
INSERT INTO `dns_role_menu` VALUES ('310', '188');
INSERT INTO `dns_role_menu` VALUES ('7', '194');
INSERT INTO `dns_role_menu` VALUES ('310', '194');
INSERT INTO `dns_role_menu` VALUES ('7', '200');
INSERT INTO `dns_role_menu` VALUES ('310', '200');
INSERT INTO `dns_role_menu` VALUES ('7', '207');
INSERT INTO `dns_role_menu` VALUES ('7', '212');
INSERT INTO `dns_role_menu` VALUES ('7', '214');
INSERT INTO `dns_role_menu` VALUES ('7', '216');
INSERT INTO `dns_role_menu` VALUES ('7', '221');
INSERT INTO `dns_role_menu` VALUES ('310', '221');
INSERT INTO `dns_role_menu` VALUES ('7', '223');
INSERT INTO `dns_role_menu` VALUES ('310', '223');
INSERT INTO `dns_role_menu` VALUES ('7', '230');
INSERT INTO `dns_role_menu` VALUES ('7', '232');
INSERT INTO `dns_role_menu` VALUES ('7', '238');
INSERT INTO `dns_role_menu` VALUES ('7', '240');
INSERT INTO `dns_role_menu` VALUES ('7', '245');
INSERT INTO `dns_role_menu` VALUES ('310', '245');
INSERT INTO `dns_role_menu` VALUES ('7', '251');
INSERT INTO `dns_role_menu` VALUES ('310', '251');
INSERT INTO `dns_role_menu` VALUES ('7', '257');
INSERT INTO `dns_role_menu` VALUES ('310', '257');
INSERT INTO `dns_role_menu` VALUES ('7', '263');
INSERT INTO `dns_role_menu` VALUES ('310', '263');
INSERT INTO `dns_role_menu` VALUES ('7', '269');
INSERT INTO `dns_role_menu` VALUES ('7', '275');
INSERT INTO `dns_role_menu` VALUES ('310', '275');
INSERT INTO `dns_role_menu` VALUES ('7', '281');
INSERT INTO `dns_role_menu` VALUES ('310', '281');
INSERT INTO `dns_role_menu` VALUES ('7', '283');
INSERT INTO `dns_role_menu` VALUES ('310', '283');
INSERT INTO `dns_role_menu` VALUES ('7', '289');
INSERT INTO `dns_role_menu` VALUES ('310', '289');
INSERT INTO `dns_role_menu` VALUES ('7', '295');
INSERT INTO `dns_role_menu` VALUES ('310', '295');
INSERT INTO `dns_role_menu` VALUES ('7', '301');
INSERT INTO `dns_role_menu` VALUES ('310', '301');
INSERT INTO `dns_role_menu` VALUES ('7', '307');
INSERT INTO `dns_role_menu` VALUES ('310', '307');
INSERT INTO `dns_role_menu` VALUES ('7', '309');
INSERT INTO `dns_role_menu` VALUES ('310', '309');

-- ----------------------------
-- Table structure for dns_role_operate
-- ----------------------------
DROP TABLE IF EXISTS `dns_role_operate`;
CREATE TABLE `dns_role_operate` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `authtype` varchar(42) default NULL,
  `dispname` varchar(20) default NULL,
  `operate_id` bigint(20) default NULL,
  `role_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKd2666jsetdqjkitkyavvs54rl` (`operate_id`),
  KEY `FKbtmgbqwktwnrf1q34hmo703a5` (`role_id`),
  CONSTRAINT `FKbtmgbqwktwnrf1q34hmo703a5` FOREIGN KEY (`role_id`) REFERENCES `dns_role` (`id`),
  CONSTRAINT `FKd2666jsetdqjkitkyavvs54rl` FOREIGN KEY (`operate_id`) REFERENCES `dns_operate` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_role_operate
-- ----------------------------

-- ----------------------------
-- Table structure for dns_selfreport
-- ----------------------------
DROP TABLE IF EXISTS `dns_selfreport`;
CREATE TABLE `dns_selfreport` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `importdate` datetime default NULL,
  `importpeople` varchar(255) default NULL,
  `remark` longtext,
  `reportdate` datetime default NULL,
  `reportstatus` int(11) default '0',
  `rg_code` varchar(255) default NULL,
  `submitcharacter` int(11) default NULL,
  `year` varchar(255) default NULL,
  `eleFinanceOrg_id` bigint(20) default NULL,
  `elePbcOrg_id` bigint(20) default NULL,
  `word_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKnu71kr61bw96qavfaf8lei770` (`eleFinanceOrg_id`),
  KEY `FK7jsyi3wgxfv1m2xb2pevawgsn` (`elePbcOrg_id`),
  KEY `FKdwihovkaocc6oi3b7njfvw142` (`word_id`),
  CONSTRAINT `FKdwihovkaocc6oi3b7njfvw142` FOREIGN KEY (`word_id`) REFERENCES `dns_accessory` (`id`),
  CONSTRAINT `FK7jsyi3wgxfv1m2xb2pevawgsn` FOREIGN KEY (`elePbcOrg_id`) REFERENCES `dns_ele_pbc_org` (`id`),
  CONSTRAINT `FKnu71kr61bw96qavfaf8lei770` FOREIGN KEY (`eleFinanceOrg_id`) REFERENCES `dns_ele_finance_org` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_selfreport
-- ----------------------------

-- ----------------------------
-- Table structure for dns_supervision_mode
-- ----------------------------
DROP TABLE IF EXISTS `dns_supervision_mode`;
CREATE TABLE `dns_supervision_mode` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `code` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_supervision_mode
-- ----------------------------

-- ----------------------------
-- Table structure for dns_supportoroppose
-- ----------------------------
DROP TABLE IF EXISTS `dns_supportoroppose`;
CREATE TABLE `dns_supportoroppose` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `attitude` int(11) default NULL,
  `name` varchar(255) default NULL,
  `opradate` datetime default NULL,
  `comment_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKd64u4fddvi9b2txnwk8rjlv1d` (`comment_id`),
  CONSTRAINT `FKd64u4fddvi9b2txnwk8rjlv1d` FOREIGN KEY (`comment_id`) REFERENCES `dns_casecomment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_supportoroppose
-- ----------------------------

-- ----------------------------
-- Table structure for dns_sysconfig
-- ----------------------------
DROP TABLE IF EXISTS `dns_sysconfig`;
CREATE TABLE `dns_sysconfig` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `address` varchar(255) default NULL,
  `bigHeight` int(11) NOT NULL,
  `bigWidth` int(11) NOT NULL,
  `closeReason` varchar(255) default NULL,
  `company_name` varchar(255) default NULL,
  `emailEnable` bit(1) NOT NULL,
  `emailHost` varchar(255) default NULL,
  `emailPort` int(11) NOT NULL,
  `emailPws` varchar(255) default NULL,
  `emailTest` varchar(255) default NULL,
  `emailUser` varchar(255) default NULL,
  `emailUserName` varchar(255) default NULL,
  `imageFilesize` int(11) NOT NULL,
  `imageSaveType` varchar(255) default NULL,
  `imageSuffix` varchar(255) default NULL,
  `imageWebServer` varchar(255) default NULL,
  `login_year` bit(1) NOT NULL,
  `middleHeight` int(11) NOT NULL,
  `middleWidth` int(11) NOT NULL,
  `second_domain_open` bit(1) default '\0',
  `securityCodeLogin` bit(1) NOT NULL,
  `securityCodeRegister` bit(1) NOT NULL,
  `securityCodeType` varchar(255) default NULL,
  `smallHeight` int(11) NOT NULL,
  `smallWidth` int(11) NOT NULL,
  `smsEnbale` bit(1) NOT NULL,
  `smsPassword` varchar(255) default NULL,
  `smsTest` varchar(255) default NULL,
  `smsURL` varchar(255) default NULL,
  `smsUserName` varchar(255) default NULL,
  `sysLanguage` varchar(255) default NULL,
  `title` varchar(255) default NULL,
  `uploadFilePath` varchar(255) default NULL,
  `websiteName` varchar(255) default NULL,
  `websiteState` bit(1) NOT NULL,
  `admin_login_logo_id` bigint(20) default NULL,
  `admin_manage_logo_id` bigint(20) default NULL,
  `memberIcon_id` bigint(20) default NULL,
  `websiteLogo_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK4k2fe4qg0pgs382j9rao8q6h2` (`admin_login_logo_id`),
  KEY `FKri97t0xm2lp1g1jhbtqixq5kx` (`admin_manage_logo_id`),
  KEY `FKtqppm70ts377uqn6cg7f9vuoo` (`memberIcon_id`),
  KEY `FKdyb2vixnjbaiaw73wkvql9794` (`websiteLogo_id`),
  CONSTRAINT `FKdyb2vixnjbaiaw73wkvql9794` FOREIGN KEY (`websiteLogo_id`) REFERENCES `dns_accessory` (`id`),
  CONSTRAINT `FK4k2fe4qg0pgs382j9rao8q6h2` FOREIGN KEY (`admin_login_logo_id`) REFERENCES `dns_accessory` (`id`),
  CONSTRAINT `FKri97t0xm2lp1g1jhbtqixq5kx` FOREIGN KEY (`admin_manage_logo_id`) REFERENCES `dns_accessory` (`id`),
  CONSTRAINT `FKtqppm70ts377uqn6cg7f9vuoo` FOREIGN KEY (`memberIcon_id`) REFERENCES `dns_accessory` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_sysconfig
-- ----------------------------
INSERT INTO `dns_sysconfig` VALUES ('1', null, '0', null, '1024', '1024', '系统维护中...', null, '', null, '0', null, null, null, null, '1024', 'sidImg', 'gif|jpg|jpeg|bmp|png|tbi', null, '\0', '300', '300', '\0', '\0', '\0', 'normal', '160', '160', '\0', null, null, 'http://service.winic.org/sys_port/gateway/', null, 'zh_cn', ' Dinosaur Workstation 定制化平台系统', 'upload', 'Dinosaur', '\0', null, null, '5', '6');
INSERT INTO `dns_sysconfig` VALUES ('4', null, '0', null, '1024', '1024', '系统维护中...', null, '', null, '0', null, null, null, null, '1024', 'sidImg', 'gif|jpg|jpeg|bmp|png|tbi', null, '\0', '300', '300', '\0', '\0', '\0', 'normal', '160', '160', '\0', null, null, 'http://service.winic.org/sys_port/gateway/', null, 'zh_cn', ' Dinosaur Workstation 定制化平台系统', 'upload', 'Dinosaur', '\0', null, null, null, null);

-- ----------------------------
-- Table structure for dns_syslog
-- ----------------------------
DROP TABLE IF EXISTS `dns_syslog`;
CREATE TABLE `dns_syslog` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `content` longtext,
  `ids` varchar(255) default NULL,
  `ip` varchar(255) default NULL,
  `ip_city` varchar(255) default NULL,
  `op_url` varchar(255) default NULL,
  `rg_code` varchar(42) NOT NULL default '000000',
  `title` varchar(255) default NULL,
  `type` int(11) NOT NULL,
  `user_id` bigint(20) default NULL,
  `user_name` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_syslog
-- ----------------------------
INSERT INTO `dns_syslog` VALUES ('311', '2017-06-09 17:23:19', '0', 'super（超级管理员）于2017-06-09 17:23:19执行角色保存操作', '', '180.102.112.181', 'IP地址库文件错误', '/manage/role_save.htm*', '000000', '角色管理', '0', '9', 'super');
INSERT INTO `dns_syslog` VALUES ('313', '2017-06-09 17:28:45', '0', 'super（超级管理员）于2017-06-09 17:28:45执行机构保存操作', '', '180.102.112.181', 'IP地址库文件错误', '/manage/orgtype_save.htm*', '000000', '机构管理', '0', '9', 'super');
INSERT INTO `dns_syslog` VALUES ('315', '2017-06-09 17:29:30', '0', 'super（超级管理员）于2017-06-09 17:29:30执行用户保存操作', '', '180.102.112.181', 'IP地址库文件错误', '/manage/user_save.htm*', '000000', '用户管理', '0', '9', 'super');
INSERT INTO `dns_syslog` VALUES ('316', '2017-06-09 17:29:52', '0', 'super（超级管理员）于2017-06-09 17:29:52执行角色菜单授权保存操作', '', '180.102.112.181', 'IP地址库文件错误', '/manage/role_menu_save.htm*', '000000', '角色菜单授权', '0', '9', 'super');
INSERT INTO `dns_syslog` VALUES ('317', '2017-06-09 17:29:57', '0', 'super（超级管理员）于2017-06-09 17:29:57执行角色菜单授权保存操作', '', '180.102.112.181', 'IP地址库文件错误', '/manage/role_menu_save.htm*', '000000', '角色菜单授权', '0', '9', 'super');
INSERT INTO `dns_syslog` VALUES ('318', '2017-06-09 17:29:58', '0', 'super（超级管理员）于2017-06-09 17:29:58执行角色菜单授权保存操作', '', '180.102.112.181', 'IP地址库文件错误', '/manage/role_menu_save.htm*', '000000', '角色菜单授权', '0', '9', 'super');
INSERT INTO `dns_syslog` VALUES ('319', '2017-06-09 17:30:13', '0', 'super（超级管理员）于2017-06-09 17:30:13执行角色菜单授权保存操作', '', '180.102.112.181', 'IP地址库文件错误', '/manage/role_menu_save.htm*', '000000', '角色菜单授权', '0', '9', 'super');
INSERT INTO `dns_syslog` VALUES ('320', '2017-06-09 17:30:26', '0', 'super（超级管理员）于2017-06-09 17:30:26执行角色菜单授权保存操作', '', '180.102.112.181', 'IP地址库文件错误', '/manage/role_menu_save.htm*', '000000', '角色菜单授权', '0', '9', 'super');
INSERT INTO `dns_syslog` VALUES ('321', '2017-06-09 17:30:27', '0', 'super（超级管理员）于2017-06-09 17:30:27执行角色菜单授权保存操作', '', '180.102.112.181', 'IP地址库文件错误', '/manage/role_menu_save.htm*', '000000', '角色菜单授权', '0', '9', 'super');
INSERT INTO `dns_syslog` VALUES ('322', '2017-06-09 17:30:30', '0', 'super（超级管理员）于2017-06-09 17:30:30执行角色菜单授权保存操作', '', '180.102.112.181', 'IP地址库文件错误', '/manage/role_menu_save.htm*', '000000', '角色菜单授权', '0', '9', 'super');
INSERT INTO `dns_syslog` VALUES ('323', '2017-06-09 17:30:30', '0', 'super（超级管理员）于2017-06-09 17:30:30执行角色菜单授权保存操作', '', '180.102.112.181', 'IP地址库文件错误', '/manage/role_menu_save.htm*', '000000', '角色菜单授权', '0', '9', 'super');
INSERT INTO `dns_syslog` VALUES ('324', '2017-06-09 17:30:52', '0', '超级管理员于2017-06-09 17:30:52退出系统', null, '180.102.112.181', null, null, '000000', '用户退出', '0', '9', 'super');
INSERT INTO `dns_syslog` VALUES ('326', '2017-06-09 17:31:45', '0', 'admin（人总行）于2017-06-09 17:31:45执行菜单Ajax更新操作。操作数据id为：33', '33', '180.102.112.181', 'IP地址库文件错误', '/manage/menu_ajax.htm*', '000000', '菜单管理', '0', '314', 'admin');
INSERT INTO `dns_syslog` VALUES ('327', '2017-06-09 17:31:58', '0', 'admin（人总行）于2017-06-09 17:31:58执行菜单Ajax更新操作。操作数据id为：33', '33', '180.102.112.181', 'IP地址库文件错误', '/manage/menu_ajax.htm*', '000000', '菜单管理', '0', '314', 'admin');
INSERT INTO `dns_syslog` VALUES ('328', '2017-06-09 17:38:04', '0', 'super（超级管理员）于2017-06-09 17:38:04执行角色菜单授权保存操作', '', '180.102.112.181', 'IP地址库文件错误', '/manage/role_menu_save.htm*', '000000', '角色菜单授权', '0', '9', 'super');
INSERT INTO `dns_syslog` VALUES ('329', '2017-06-09 17:38:11', '0', '人总行于2017-06-09 17:38:11退出系统', null, '180.102.112.181', null, null, '000000', '用户退出', '0', '314', 'admin');
INSERT INTO `dns_syslog` VALUES ('330', '2017-06-09 17:38:50', '0', '超级管理员于2017-06-09 17:38:50退出系统', null, '180.102.112.181', null, null, '000000', '用户退出', '0', '9', 'super');
INSERT INTO `dns_syslog` VALUES ('331', '2017-06-09 17:52:52', '0', 'super（超级管理员）于2017-06-09 17:52:52执行角色菜单授权保存操作', '', '180.102.112.181', 'IP地址库文件错误', '/manage/role_menu_save.htm*', '000000', '角色菜单授权', '0', '9', 'super');
INSERT INTO `dns_syslog` VALUES ('332', '2017-06-09 17:52:52', '0', 'super（超级管理员）于2017-06-09 17:52:52执行角色菜单授权保存操作', '', '180.102.112.181', 'IP地址库文件错误', '/manage/role_menu_save.htm*', '000000', '角色菜单授权', '0', '9', 'super');
INSERT INTO `dns_syslog` VALUES ('333', '2017-06-09 17:52:53', '0', 'super（超级管理员）于2017-06-09 17:52:53执行角色菜单授权保存操作', '', '180.102.112.181', 'IP地址库文件错误', '/manage/role_menu_save.htm*', '000000', '角色菜单授权', '0', '9', 'super');
INSERT INTO `dns_syslog` VALUES ('334', '2017-06-09 17:52:54', '0', 'super（超级管理员）于2017-06-09 17:52:54执行角色菜单授权保存操作', '', '180.102.112.181', 'IP地址库文件错误', '/manage/role_menu_save.htm*', '000000', '角色菜单授权', '0', '9', 'super');
INSERT INTO `dns_syslog` VALUES ('335', '2017-06-09 17:52:56', '0', 'super（超级管理员）于2017-06-09 17:52:56执行角色菜单授权保存操作', '', '180.102.112.181', 'IP地址库文件错误', '/manage/role_menu_save.htm*', '000000', '角色菜单授权', '0', '9', 'super');
INSERT INTO `dns_syslog` VALUES ('336', '2017-06-09 17:52:57', '0', 'super（超级管理员）于2017-06-09 17:52:57执行角色菜单授权保存操作', '', '180.102.112.181', 'IP地址库文件错误', '/manage/role_menu_save.htm*', '000000', '角色菜单授权', '0', '9', 'super');
INSERT INTO `dns_syslog` VALUES ('337', '2017-06-09 17:53:00', '0', '超级管理员于2017-06-09 17:53:00退出系统', null, '180.102.112.181', null, null, '000000', '用户退出', '0', '9', 'super');

-- ----------------------------
-- Table structure for dns_system_libray
-- ----------------------------
DROP TABLE IF EXISTS `dns_system_libray`;
CREATE TABLE `dns_system_libray` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `create_date` datetime default NULL,
  `create_user` bigint(20) default NULL,
  `enabled` bit(1) default '',
  `formulate_unit` varchar(255) default NULL,
  `key_explain` varchar(255) default NULL,
  `latest_op_date` datetime default NULL,
  `latest_op_user` bigint(20) default NULL,
  `reference_number` varchar(255) default NULL,
  `release_date` datetime default NULL,
  `remark` varchar(255) default NULL,
  `system_name` varchar(255) default NULL,
  `system_file_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK9nby695hfxkhwk7vovja4s9fa` (`system_file_id`),
  CONSTRAINT `FK9nby695hfxkhwk7vovja4s9fa` FOREIGN KEY (`system_file_id`) REFERENCES `dns_accessory` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_system_libray
-- ----------------------------

-- ----------------------------
-- Table structure for dns_sys_tip
-- ----------------------------
DROP TABLE IF EXISTS `dns_sys_tip`;
CREATE TABLE `dns_sys_tip` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `st_content` longtext,
  `st_level` int(11) NOT NULL,
  `st_status` int(11) NOT NULL,
  `st_title` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_sys_tip
-- ----------------------------

-- ----------------------------
-- Table structure for dns_time_node
-- ----------------------------
DROP TABLE IF EXISTS `dns_time_node`;
CREATE TABLE `dns_time_node` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `enforceDate` datetime default NULL,
  `factDate` datetime default NULL,
  `inputMan` varchar(255) default NULL,
  `leftDate` datetime default NULL,
  `remark` varchar(255) default NULL,
  `inspectProject_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK7t7o11fip29hbn1sw6n7aqm94` (`inspectProject_id`),
  CONSTRAINT `FK7t7o11fip29hbn1sw6n7aqm94` FOREIGN KEY (`inspectProject_id`) REFERENCES `dns_inspect_project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_time_node
-- ----------------------------

-- ----------------------------
-- Table structure for dns_transactiondata
-- ----------------------------
DROP TABLE IF EXISTS `dns_transactiondata`;
CREATE TABLE `dns_transactiondata` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `analogue` varchar(255) default NULL,
  `bond_code` varchar(255) default NULL,
  `bond_name` varchar(255) default NULL,
  `full_transaction_price` varchar(255) default NULL,
  `liquidation_speed` varchar(255) default NULL,
  `net_transaction_mount` varchar(255) default NULL,
  `net_turnover` varchar(255) default NULL,
  `quotation_manner` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  `thisside` varchar(255) default NULL,
  `total_transaction_amount` varchar(255) default NULL,
  `trading_direction` varchar(255) default NULL,
  `transaction_date` varchar(255) default NULL,
  `transaction_file` tinyblob,
  `transaction_hour` varchar(255) default NULL,
  `transaction_number` varchar(255) default NULL,
  `yield` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_transactiondata
-- ----------------------------

-- ----------------------------
-- Table structure for dns_uiconf
-- ----------------------------
DROP TABLE IF EXISTS `dns_uiconf`;
CREATE TABLE `dns_uiconf` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `order_no` varchar(255) default NULL,
  `ref_selmodel` varchar(255) default NULL,
  `uiconf_datatype` varchar(255) default NULL,
  `uiconf_field` varchar(255) default NULL,
  `uiconf_title` varchar(255) default NULL,
  `uiconf_type` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_uiconf
-- ----------------------------

-- ----------------------------
-- Table structure for dns_uiconf_detail
-- ----------------------------
DROP TABLE IF EXISTS `dns_uiconf_detail`;
CREATE TABLE `dns_uiconf_detail` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `uiconf_datatype` varchar(255) default NULL,
  `uiconf_field` varchar(255) default NULL,
  `uiconf_title` varchar(255) default NULL,
  `uiconf_value` varchar(255) default NULL,
  `ui_detail_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKe9w3fswcoka37vmf0cipe6jm6` (`ui_detail_id`),
  CONSTRAINT `FKe9w3fswcoka37vmf0cipe6jm6` FOREIGN KEY (`ui_detail_id`) REFERENCES `dns_uidetail` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_uiconf_detail
-- ----------------------------

-- ----------------------------
-- Table structure for dns_uidetail
-- ----------------------------
DROP TABLE IF EXISTS `dns_uidetail`;
CREATE TABLE `dns_uidetail` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `cols` tinyint(4) default NULL,
  `detail_type` tinyint(4) default NULL,
  `field_index` tinyint(4) default NULL,
  `field_name` varchar(255) default NULL,
  `field_title` varchar(255) default NULL,
  `field_type` varchar(38) default NULL,
  `hidden` tinyint(4) default NULL,
  `width` tinyint(4) default NULL,
  `parent_id` bigint(20) default NULL,
  `ui_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKj18t01vbvoye4rxsc3flk8nq3` (`parent_id`),
  KEY `FKp1r3onb8ds5ym5rgkt7xka8w9` (`ui_id`),
  CONSTRAINT `FKp1r3onb8ds5ym5rgkt7xka8w9` FOREIGN KEY (`ui_id`) REFERENCES `dns_uimanager` (`id`),
  CONSTRAINT `FKj18t01vbvoye4rxsc3flk8nq3` FOREIGN KEY (`parent_id`) REFERENCES `dns_uidetail` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_uidetail
-- ----------------------------

-- ----------------------------
-- Table structure for dns_uimanager
-- ----------------------------
DROP TABLE IF EXISTS `dns_uimanager`;
CREATE TABLE `dns_uimanager` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `comp_id` varchar(255) default NULL,
  `rg_code` varchar(42) default '000000',
  `servletpath` varchar(255) default NULL,
  `total_column` tinyint(4) default NULL,
  `ui_code` varchar(42) default NULL,
  `ui_name` varchar(255) default NULL,
  `xtype` varchar(10) default NULL,
  `parent_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKmw66cvod726uot0yqv0tcxps9` (`parent_id`),
  CONSTRAINT `FKmw66cvod726uot0yqv0tcxps9` FOREIGN KEY (`parent_id`) REFERENCES `dns_uimanager` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_uimanager
-- ----------------------------

-- ----------------------------
-- Table structure for dns_user
-- ----------------------------
DROP TABLE IF EXISTS `dns_user`;
CREATE TABLE `dns_user` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `MSN` varchar(255) default NULL,
  `QQ` varchar(255) default NULL,
  `WW` varchar(255) default NULL,
  `address` varchar(255) default NULL,
  `belong_source` varchar(255) default NULL,
  `birthday` datetime default NULL,
  `card` varchar(255) default NULL,
  `email` varchar(255) default NULL,
  `lastLoginDate` datetime default NULL,
  `lastLoginIp` varchar(255) default NULL,
  `loginCount` int(11) NOT NULL,
  `loginDate` datetime default NULL,
  `loginIp` varchar(255) default NULL,
  `manageType` int(11) default '0',
  `mobile` varchar(255) default NULL,
  `nickName` varchar(255) default NULL,
  `orgtype_ele_id` bigint(20) default NULL,
  `password` varchar(255) default NULL,
  `qq_openid` varchar(255) default NULL,
  `report` int(11) NOT NULL,
  `rg_code` varchar(42) NOT NULL default '000000',
  `sex` int(11) NOT NULL,
  `sina_openid` varchar(255) default NULL,
  `telephone` varchar(255) default NULL,
  `trueName` varchar(255) default NULL,
  `userName` varchar(255) default NULL,
  `userRole` varchar(255) default NULL,
  `weixin` varchar(255) default NULL,
  `weixin_loginid` varchar(255) default NULL,
  `weixin_pubid` varchar(255) default NULL,
  `weixin_unionid` varchar(255) default NULL,
  `years` int(11) default '0',
  `area_id` bigint(20) default NULL,
  `orgtype_id` bigint(20) default NULL,
  `pg_id` bigint(20) default NULL,
  `photo_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `UK_pmabxpv0p2vxlbku9dk40d00s` (`userName`),
  KEY `FK9onnmo44rv9qru16do77vljx2` (`area_id`),
  KEY `FKj9xro1eslc6ynxms93in0yr8g` (`orgtype_id`),
  KEY `FKdup1ei8yteo54rbf7r0dokoko` (`pg_id`),
  KEY `FKrp5rsj0vxap7h0s1cd3wd0h3d` (`photo_id`),
  CONSTRAINT `FKrp5rsj0vxap7h0s1cd3wd0h3d` FOREIGN KEY (`photo_id`) REFERENCES `dns_accessory` (`id`),
  CONSTRAINT `FK9onnmo44rv9qru16do77vljx2` FOREIGN KEY (`area_id`) REFERENCES `dns_area` (`id`),
  CONSTRAINT `FKdup1ei8yteo54rbf7r0dokoko` FOREIGN KEY (`pg_id`) REFERENCES `dns_part_group` (`id`),
  CONSTRAINT `FKj9xro1eslc6ynxms93in0yr8g` FOREIGN KEY (`orgtype_id`) REFERENCES `dns_orgtype` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_user
-- ----------------------------
INSERT INTO `dns_user` VALUES ('9', null, '0', null, null, null, null, null, null, null, null, '2017-06-09 17:52:39', '180.102.112.181', '6', '2017-06-09 18:04:34', '180.102.112.181', '1', null, null, null, 'n710oe0050a0100aan19n110d71d220n', null, '0', '000000', '0', null, null, '超级管理员', 'super', 'MANAGE', null, null, null, null, '0', null, null, null, null);
INSERT INTO `dns_user` VALUES ('314', '2017-06-09 17:29:30', '0', null, null, null, null, null, null, null, '', '2017-06-09 17:36:53', '180.102.112.181', '7', '2017-06-09 17:55:18', '180.102.112.181', '1', '', null, '-1', 'n710oe0050a0100aan19n110d71d220n', null, '0', '000000', '0', null, null, '人总行', 'admin', 'MANAGE', null, null, null, null, '0', null, '312', null, null);

-- ----------------------------
-- Table structure for dns_userbelong
-- ----------------------------
DROP TABLE IF EXISTS `dns_userbelong`;
CREATE TABLE `dns_userbelong` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `ele_code` varchar(42) default NULL,
  `ele_id` bigint(20) default NULL,
  `user_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKa132odgj1pm5l94ulgevicsc` (`user_id`),
  CONSTRAINT `FKa132odgj1pm5l94ulgevicsc` FOREIGN KEY (`user_id`) REFERENCES `dns_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_userbelong
-- ----------------------------

-- ----------------------------
-- Table structure for dns_userconfig
-- ----------------------------
DROP TABLE IF EXISTS `dns_userconfig`;
CREATE TABLE `dns_userconfig` (
  `id` bigint(20) NOT NULL,
  `addTime` datetime default NULL,
  `is_deleted` int(11) default '0',
  `layout` varchar(5) default NULL,
  `theme` varchar(20) default NULL,
  `user_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK49n6mf63kldbj835654lokfqh` (`user_id`),
  CONSTRAINT `FK49n6mf63kldbj835654lokfqh` FOREIGN KEY (`user_id`) REFERENCES `dns_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_userconfig
-- ----------------------------
INSERT INTO `dns_userconfig` VALUES ('10', '2017-06-09 17:20:48', '0', '1', 'default', '9');
INSERT INTO `dns_userconfig` VALUES ('325', '2017-06-09 17:31:04', '0', '1', 'default', '314');

-- ----------------------------
-- Table structure for dns_user_role
-- ----------------------------
DROP TABLE IF EXISTS `dns_user_role`;
CREATE TABLE `dns_user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`user_id`,`role_id`),
  KEY `FKnrwvsptlijbr6dr4149v7r9of` (`role_id`),
  CONSTRAINT `FKfobr5dtumkxjw7f13apejo0gb` FOREIGN KEY (`user_id`) REFERENCES `dns_user` (`id`),
  CONSTRAINT `FKnrwvsptlijbr6dr4149v7r9of` FOREIGN KEY (`role_id`) REFERENCES `dns_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dns_user_role
-- ----------------------------
INSERT INTO `dns_user_role` VALUES ('9', '7');
INSERT INTO `dns_user_role` VALUES ('9', '8');
INSERT INTO `dns_user_role` VALUES ('314', '310');

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');
INSERT INTO `hibernate_sequence` VALUES ('338');

-- ----------------------------
-- View structure for vw_ele_element
-- ----------------------------
DROP VIEW IF EXISTS `vw_ele_element`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `vw_ele_element` AS select sql_no_cache `dns_element`.`id` AS `id`,`dns_element`.`ele_code` AS `code`,`dns_element`.`ele_name` AS `name`,1 AS `level`,1 AS `leaf`,`dns_element`.`enabled` AS `enabled`,NULL AS `parent_id` from `dns_element` where (`dns_element`.`ele_code` <> _utf8'ELE') ;

-- ----------------------------
-- View structure for vw_ele_menu
-- ----------------------------
DROP VIEW IF EXISTS `vw_ele_menu`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `vw_ele_menu` AS select sql_no_cache `m`.`id` AS `id`,_utf8'' AS `code`,`m`.`name` AS `name`,NULL AS `parent_id`,0 AS `leaf`,1 AS `enabled` from `dns_menugroup` `m` union all select sql_no_cache `m`.`id` AS `id`,`m`.`menuCode` AS `code`,`m`.`menuName` AS `name`,`m`.`mg_id` AS `parent_id`,1 AS `leaf`,1 AS `enabled` from `dns_menu` `m` ;

-- ----------------------------
-- View structure for vw_ele_role
-- ----------------------------
DROP VIEW IF EXISTS `vw_ele_role`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `vw_ele_role` AS select sql_no_cache `r`.`id` AS `id`,`r`.`roleCode` AS `code`,`r`.`roleName` AS `name`,1 AS `level`,1 AS `leaf`,1 AS `enabled`,NULL AS `parent_id`,`r`.`rg_code` AS `rg_code` from `dns_role` `r` where (`r`.`builtin` = 0) ;

-- ----------------------------
-- View structure for vw_ele_uimanager
-- ----------------------------
DROP VIEW IF EXISTS `vw_ele_uimanager`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `vw_ele_uimanager` AS select `dns_uimanager`.`id` AS `id`,`dns_uimanager`.`ui_code` AS `code`,`dns_uimanager`.`ui_name` AS `name`,1 AS `level`,1 AS `leaf`,1 AS `enabled`,`dns_uimanager`.`parent_id` AS `parent_id`,`dns_uimanager`.`rg_code` AS `rg_code`,`dns_uimanager`.`id` AS `ui_id`,`dns_uimanager`.`ui_code` AS `ui_code`,`dns_uimanager`.`ui_name` AS `ui_name`,`dns_uimanager`.`xtype` AS `xtype`,`dns_uimanager`.`comp_id` AS `comp_id`,`dns_uimanager`.`servletpath` AS `servletpath`,`dns_uimanager`.`total_column` AS `total_column` from `dns_uimanager` ;
