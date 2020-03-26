/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : menu

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2020-03-26 12:35:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for com_info
-- ----------------------------
DROP TABLE IF EXISTS `com_info`;
CREATE TABLE `com_info` (
  `com_id` int(20) NOT NULL AUTO_INCREMENT,
  `menu_id` int(20) NOT NULL,
  `user_id` varchar(30) NOT NULL,
  `com_grade` float(10,1) NOT NULL,
  `com` varchar(1024) NOT NULL,
  PRIMARY KEY (`com_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of com_info
-- ----------------------------
INSERT INTO `com_info` VALUES ('1', '1', 'bbb', '9.0', '很好吃');
INSERT INTO `com_info` VALUES ('2', '1', 'bbb', '6.0', '一般');
INSERT INTO `com_info` VALUES ('3', '1', 'bbb', '8.0', '很好吃');
INSERT INTO `com_info` VALUES ('6', '2', 'aaa', '8.0', '很好吃');
INSERT INTO `com_info` VALUES ('7', '1', 'aaa', '9.0', '很简单的菜');
INSERT INTO `com_info` VALUES ('13', '5', 'aaa', '7.0', '很不错');
INSERT INTO `com_info` VALUES ('14', '6', 'aaa', '9.0', 'dsamdklsam');

-- ----------------------------
-- Table structure for food_in
-- ----------------------------
DROP TABLE IF EXISTS `food_in`;
CREATE TABLE `food_in` (
  `in_id` varchar(30) NOT NULL,
  `admin_id` varchar(30) DEFAULT NULL,
  `food_id` varchar(20) NOT NULL,
  `in_num` float NOT NULL,
  `in_status` varchar(20) NOT NULL,
  PRIMARY KEY (`in_id`),
  KEY `FK_�������` (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of food_in
-- ----------------------------

-- ----------------------------
-- Table structure for food_info
-- ----------------------------
DROP TABLE IF EXISTS `food_info`;
CREATE TABLE `food_info` (
  `food_id` int(20) NOT NULL AUTO_INCREMENT,
  `food_name` varchar(20) NOT NULL,
  `food_price` float(10,2) NOT NULL,
  `food_num` int(20) NOT NULL,
  `food_dsp` varchar(1024) DEFAULT NULL,
  `food_format` varchar(20) NOT NULL,
  PRIMARY KEY (`food_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of food_info
-- ----------------------------
INSERT INTO `food_info` VALUES ('1', '鸡蛋', '1.50', '30', '生活中最常用的食材', '个');
INSERT INTO `food_info` VALUES ('2', '青菜', '1.00', '2000', '常见的蔬菜', '克');
INSERT INTO `food_info` VALUES ('3', '鸡腿', '4.50', '200', '取自鸡大腿的食物', '只');
INSERT INTO `food_info` VALUES ('4', '番茄', '2.50', '1500', '酸甜可口的蔬菜也是水果', '克');
INSERT INTO `food_info` VALUES ('5', '猪肉', '20.00', '4280', '常见肉类', '克');
INSERT INTO `food_info` VALUES ('6', '鸭肉', '15.00', '6000', '禽类中第二大肉类', '克');
INSERT INTO `food_info` VALUES ('7', '葱', '0.50', '500', '菜品中最常见的添加物，用于加入葱香和增色', '克');
INSERT INTO `food_info` VALUES ('8', '牛肉', '25.00', '3000', '常用西式餐点肉类', '克');

-- ----------------------------
-- Table structure for god_info
-- ----------------------------
DROP TABLE IF EXISTS `god_info`;
CREATE TABLE `god_info` (
  `god_id` varchar(30) NOT NULL,
  `god_pwd` varchar(30) NOT NULL,
  `user_type` varchar(20) NOT NULL,
  `reg_time` datetime DEFAULT NULL,
  `del_time` datetime DEFAULT NULL,
  `god_name` varchar(30) NOT NULL,
  PRIMARY KEY (`god_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of god_info
-- ----------------------------
INSERT INTO `god_info` VALUES ('1', '1', '管理员', '2019-08-26 15:35:13', '2019-09-04 10:23:29', '1');
INSERT INTO `god_info` VALUES ('admin', 'admin', '管理员', '2019-08-24 22:17:16', null, 'admin');
INSERT INTO `god_info` VALUES ('admin2', '2', '管理员', '2019-08-24 23:10:56', '2019-09-02 19:46:01', '2');
INSERT INTO `god_info` VALUES ('admin3', 'admin3', '管理员', '2019-08-25 08:25:04', '2019-08-25 08:38:08', '3');

-- ----------------------------
-- Table structure for menu_info
-- ----------------------------
DROP TABLE IF EXISTS `menu_info`;
CREATE TABLE `menu_info` (
  `menu_id` int(20) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(30) NOT NULL,
  `menu_pic` varchar(100) NOT NULL,
  `menu_user` varchar(30) NOT NULL,
  `menu_dsp` varchar(1024) NOT NULL,
  `menu_grade` float(4,1) DEFAULT NULL,
  `menu_collect` int(11) DEFAULT NULL,
  `menu_look` int(11) DEFAULT NULL,
  PRIMARY KEY (`menu_id`),
  KEY `menu_user` (`menu_user`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu_info
-- ----------------------------
INSERT INTO `menu_info` VALUES ('1', '番茄炒蛋', 'picture/fqcd.jpg', 'bbb', '步骤：先将材料准备好。西红柿切好。\r\n打鸡蛋：将鸡蛋打入碗中，加入适量的盐搅拌均匀。\r\n小火起锅、倒入火麻油。将打好的鸡蛋倒入，炒熟盛出备用。再起锅，倒入些许火麻油，放入西红柿翻炒，加入少许生抽提鲜，加入糖和盐翻炒均匀即可出锅。可以放些葱花点缀。', '9.0', '5', '116');
INSERT INTO `menu_info` VALUES ('2', '红烧肉', 'picture/hsr.jpg', 'bbb', '五花肉切麻将大小的块，冲洗干净后，放一汤匙料酒，浸泡一小时。捞出来沥干。<br>\n锅里放油煸炒肉块到微黄。\n放入干辣椒，草果，八角，姜，炒出香味。\n放料酒两汤匙，炒几下，再放老抽，生抽，炒匀。\n放入开水，淹没肉，转入砂锅煨两个小时调入盐。\n煨到酥烂的五花肉，放冰糖大火收汁，晃动锅，不要翻动。\n到汤汁均匀的裹在肉上就好了！放点味精提味。', '8.0', '3', '67');
INSERT INTO `menu_info` VALUES ('4', '番茄炖牛肉', 'picture/fqdnr.jpg', 'aaa', '做法：\n1、将牛肉洗净切成小方块，姜切末葱切段。\n2、坐锅点火，油至5—6成热时，逐步将牛肉炸过捞出。\n3、锅中留底油放入炸过的牛肉，放水（以淹过肉为宜）加入清酱、姜末、料酒、葱段、草果、盐调味。\n4、将番茄放入开水中浸泡片刻捞出剥去皮切成月牙块放入锅中，用小火烧60分钟即可。\n注意。用大火烧开微沸炖烂，牛肉酥烂时加入调料和番茄。\n特点：肉酥烂，汁亮味美。\n提示。\n1、西红柿含有较高的蛋白质、糖类以及铁、钙、磷等多种人体所必须的矿物质和多种维生素。\n2、番茄烧牛肉有古菜“腩灸”演变而来，“腩灸”用盐豉汁，番茄烧牛肉改用番茄增色增味，使菜肴营养丰富，鲜美诱人。', '7.5', '3', '35');
INSERT INTO `menu_info` VALUES ('5', '北京烤鸭', 'E:\\java_work\\menu\\picture\\bjky.jpg', 'aaa', '1、鸭于翼底外开一孔，取出肠脏（可请鸭档代劳），洗净，以配料涂匀鸭腔。\n2、用三寸长木条纳入鸭腔内，横挡于两翅骨间，插一支管在鸭头部，吹气使鸭全向澎涨。\n3、用滚水淋鸭外皮至起凸点，煮溶淋鸭皮料，涂匀鸭皮挂在当风处吹干。\n4、用叉撑着鸭放在火炉上烧，须反覆转动及多次涂油，烧至皮脆及呈金红色上碟。\n5、若用炉烤，鸭以锡纸包着烤至八成熟，除去锡纸再烤至鸭皮呈金黄色取出，用滚油淋鸭皮至脆上碟。\n6、趁热将鸭片皮，伴以京葱、青瓜、甜面酱及薄饼同食。', '7.0', '1', '21');
INSERT INTO `menu_info` VALUES ('6', '糖醋排骨', 'E:\\java_work\\menu\\picture\\tcpg.jpg', 'aaa', '1.小排500克焯水后，煮三十分钟，肉汤可以煮面条，别倒掉了。\n2.用一汤匙料酒，一汤匙生抽，半汤匙老抽，二汤匙香醋（不是白醋）腌渍20分钟。\n3.捞出洗净控水备用，炸至金黄，油别放多，可以省油，只要翻身得勤就好了。\n4.锅内放排骨，腌排骨的水，三汤勺白糖（大胆的放糖吧，三汤匙，别怕多）。半碗肉汤大火烧开，调入半茶匙盐提味。\n5.小火焖十分钟大火收汁，收汁的时候最后加一汤匙香醋，那个酸甜口味就出来了。\n6.临出锅撒葱花芝麻，少许味精。', '8.5', '1', '7');

-- ----------------------------
-- Table structure for menu_use
-- ----------------------------
DROP TABLE IF EXISTS `menu_use`;
CREATE TABLE `menu_use` (
  `material_id` int(20) NOT NULL AUTO_INCREMENT,
  `menu_id` int(20) NOT NULL,
  `menu_name` varchar(30) NOT NULL,
  `food_id` int(20) NOT NULL,
  `food_name` varchar(20) NOT NULL,
  `use_num` int(20) NOT NULL,
  `use_format` varchar(20) NOT NULL,
  PRIMARY KEY (`material_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu_use
-- ----------------------------
INSERT INTO `menu_use` VALUES ('1', '1', '番茄炒蛋', '4', '番茄', '500', '克');
INSERT INTO `menu_use` VALUES ('2', '1', '番茄炒蛋', '1', '鸡蛋', '1', '个');
INSERT INTO `menu_use` VALUES ('3', '4', '番茄炖牛肉', '4', '番茄', '500', '克');
INSERT INTO `menu_use` VALUES ('4', '1', '番茄炒蛋', '7', '葱', '10', '克');
INSERT INTO `menu_use` VALUES ('5', '2', '红烧肉', '5', '猪肉', '720', '克');
INSERT INTO `menu_use` VALUES ('7', '4', '番茄炖牛肉', '8', '牛肉', '300', '克');

-- ----------------------------
-- Table structure for my_info
-- ----------------------------
DROP TABLE IF EXISTS `my_info`;
CREATE TABLE `my_info` (
  `collect_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(30) NOT NULL,
  `menu_id` int(20) NOT NULL,
  `collect_flag` int(2) DEFAULT NULL,
  PRIMARY KEY (`collect_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of my_info
-- ----------------------------
INSERT INTO `my_info` VALUES ('1', 'aaa', '1', '0');
INSERT INTO `my_info` VALUES ('2', 'aaa', '2', '1');
INSERT INTO `my_info` VALUES ('3', 'aaa', '4', '1');
INSERT INTO `my_info` VALUES ('4', 'aaa', '5', '1');
INSERT INTO `my_info` VALUES ('5', 'aaa', '6', '1');

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `order_id` int(20) NOT NULL,
  `orderid` int(20) NOT NULL,
  `menu_id` int(20) NOT NULL,
  `user_id` varchar(20) NOT NULL,
  `food_id` int(30) NOT NULL,
  `food_name` varchar(30) NOT NULL,
  `food_num` int(20) NOT NULL,
  `food_price` float(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES ('1', '19', '1', 'aaa', '4', '番茄', '500', '2.50');
INSERT INTO `order_detail` VALUES ('1', '19', '1', 'aaa', '1', '鸡蛋', '1', '1.50');
INSERT INTO `order_detail` VALUES ('2', '20', '1', 'aaa', '4', '番茄', '500', '2.50');
INSERT INTO `order_detail` VALUES ('2', '20', '1', 'aaa', '1', '鸡蛋', '1', '1.50');
INSERT INTO `order_detail` VALUES ('3', '21', '1', 'aaa', '4', '番茄', '500', '2.50');
INSERT INTO `order_detail` VALUES ('3', '21', '1', 'aaa', '1', '鸡蛋', '1', '1.50');
INSERT INTO `order_detail` VALUES ('4', '22', '1', 'aaa', '4', '番茄', '500', '2.50');
INSERT INTO `order_detail` VALUES ('4', '22', '1', 'aaa', '1', '鸡蛋', '1', '1.50');
INSERT INTO `order_detail` VALUES ('5', '23', '1', 'aaa', '4', '番茄', '500', '2.50');
INSERT INTO `order_detail` VALUES ('5', '23', '1', 'aaa', '1', '鸡蛋', '1', '1.50');
INSERT INTO `order_detail` VALUES ('6', '24', '1', 'aaa', '4', '番茄', '500', '2.50');
INSERT INTO `order_detail` VALUES ('6', '24', '1', 'aaa', '1', '鸡蛋', '1', '1.50');
INSERT INTO `order_detail` VALUES ('7', '25', '2', 'aaa', '5', '猪肉', '720', '20.00');
INSERT INTO `order_detail` VALUES ('8', '26', '2', 'aaa', '5', '猪肉', '720', '20.00');

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `order_id` int(20) NOT NULL AUTO_INCREMENT,
  `order_menuid` int(20) NOT NULL,
  `order_userid` varchar(30) NOT NULL,
  `order_price` float(20,2) NOT NULL,
  `order_time` datetime NOT NULL,
  `order_add` varchar(1024) NOT NULL,
  `order_tel` varchar(30) NOT NULL,
  `order_status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('18', '1', 'aaa', '4.00', '2019-09-02 23:32:10', '1231231', '41531513', '已退单');
INSERT INTO `order_info` VALUES ('19', '1', 'aaa', '4.00', '2019-09-02 22:52:50', '1534153', '153153', '已退单');
INSERT INTO `order_info` VALUES ('20', '1', 'aaa', '4.00', '2019-09-03 00:08:02', '15318531', '61853185', '已退单');
INSERT INTO `order_info` VALUES ('21', '1', 'aaa', '4.00', '2019-09-02 22:58:09', '26\n1265', '1531531', '已退单');
INSERT INTO `order_info` VALUES ('22', '1', 'aaa', '4.00', '2019-09-02 23:38:56', '153153', '153', '已退单');
INSERT INTO `order_info` VALUES ('23', '1', 'aaa', '4.00', '2019-09-02 22:59:03', '51335', '1534', '已退单');
INSERT INTO `order_info` VALUES ('24', '2', 'aaa', '20.00', '2019-09-04 08:55:43', '城市学院', '15318165531', '已退单');
INSERT INTO `order_info` VALUES ('25', '2', 'aaa', '20.00', '2019-09-04 11:10:27', 'ndcsjabncjs', 'csaninas', '已退单');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `user_id` varchar(30) NOT NULL,
  `user_name` varchar(30) NOT NULL,
  `user_sex` varchar(10) NOT NULL,
  `user_pwd` varchar(30) NOT NULL,
  `user_tel` varchar(30) DEFAULT NULL,
  `user_mail` varchar(50) DEFAULT NULL,
  `user_city` varchar(1024) DEFAULT NULL,
  `user_reg` datetime NOT NULL,
  `user_type` varchar(30) NOT NULL,
  `user_del` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `user_name` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('123', '123', '男', '456', '', '', '', '2019-08-25 10:49:43', '用户', null);
INSERT INTO `user_info` VALUES ('aaa', '小泽', '男', '123456', '', '', '', '2019-08-22 09:24:03', '用户', null);
INSERT INTO `user_info` VALUES ('bbb', 'xcc', '男', '123', '159848615618', 'csad@qq.com', '浙江省杭州市', '2019-09-03 09:43:30', '用户', null);
