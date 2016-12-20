/*
Navicat MySQL Data Transfer

Source Server         : fgsdf
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : yuefanba

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2015-07-30 01:29:09
*/

/*
说明：
1、每个表的设计主要按小组讨论的结果来确定。
2、在昨天讨论的基础上添加了订单明细表orderdetail，用来解决订单拆分的问题。
3、如果对设计有疑问及时提出统一修改，不要自己修改。
4、每个数据表留出了3个空白字段，以备后用。
5、目前没有考虑的问题还有没有购物车表，没有考虑储值卡消费记录，如果需要用到这些功能，请提出。
*/

/*
1、用户表
*/

drop table if exists user;
create table user(
 id 		int not null primary key auto_increment,	-- 编号，主键，自增
 userId 	varchar(50) unique not null,				-- 用户编号
 userName	varchar(50) unique not null,				-- 用户名
 password 	varchar(100) not null,				-- 密码
 userType 	tinyint not null,				-- 用户类型
 default1    	varchar(255),
 default2    	varchar(255) ,
 default3    	varchar(255)
);


/*
2、用户扩展信息表
*/
drop table if exists userinfo;
create table userinfo( 
 id 		int not null primary key auto_increment,	-- 编号，主键，自增
 userId 	varchar(50) unique not null,				-- 用户编号
 uname 		varchar(50) not null,				-- 姓名
 userAddr	varchar(100),					-- 地址
 userTel 	varchar(20),					-- 联系方式
 userMoney 	float,						-- 账户余额
  default1    	varchar(255),
  default2    	varchar(255) ,
  default3    	varchar(255)
)auto_increment=1;

/*
3、用户地址表
*/

drop table if exists addressinfo;
create table addressinfo (
  id          int not null primary key auto_increment,	-- 编号，主键，自增
  userId      varchar(50) not null,			-- 用户编号
  getName	  varchar(50),				-- 联系人
  userPhone   varchar(20) ,				-- 联系方式
  userAddress varchar(100),				-- 地址
  addTime     datetime,					-- 添加时间
  default1    varchar(255),
  default2    varchar(255) ,
  default3    varchar(255)
) auto_increment=1;


/*
4、餐厅信息表
*/

drop table if exists shopinfo;
create table shopinfo(
  id            int not null primary key auto_increment,	-- 编号，主键，自增
  shopId        varchar(50)  not null,				-- 店铺编号
  userId        varchar(50)  not null,				-- 店主编号
  hostCard      varchar(20) not null,				-- 店主身份证号
  hostName      varchar(20) not null,				-- 店主姓名
  shopName      varchar(50) not null,				-- 店名
  shopAddr     varchar(100) not null,				-- 店铺地址
  shopTel       varchar(20) not null,				-- 联系方式
  shopImg       varchar(200),					-- 店铺图片
  startPrice    float,						-- 起送价
  sendAdd       float,						-- 配送费
  sendTime		int,					-- 配送时间（指多久配送）
  shopType      varchar(20),					-- 店铺类型
  startTime     time,						-- 营业开始时间
  endTime       time ,						-- 营业结束时间
  incomeMoney   float,						-- 当前收入额
  busState      tinyint,					-- 餐厅营业状态
  shopState     tinyint,					-- 餐厅状态
  shopApplyTime datetime,					-- 店铺申请时间
  shopReplyTime datetime,					-- 店铺通过时间
  shopNotice    varchar(200),					-- 店铺公告
  orderNum      int,						-- 订单量
  default2      varchar(255),
  default3      varchar(255)
) auto_increment=1;

/*
5、菜品信息表
*/
drop table if exists foodinfo;

create table foodinfo (
  id 		int not null primary key auto_increment,	-- 编号，主键，自增
  foodId	varchar(50) not null,				-- 菜品编号
  foodName 	varchar(50) not null,				-- 菜品名称
  foodPrice 	float not null,					-- 价格
  shopId 	varchar(50) not null,				-- 店铺编号
  buyNum 	int,						-- 总销量
  foodImg 	varchar(200),					-- 菜品图片
  foodInfor 	varchar(200),					-- 菜品描述
  foodState 	tinyint,					-- 菜品状态
  foodNum 	int,						-- 每日限销量
  default1 	varchar(255),
  default2 	varchar(255),
  default3 	varchar(255)
)auto_increment=1;


/*
6、订单信息表
*/

drop table if exists orderinfo;

create table orderinfo(
  id 		int not null primary key auto_increment,	-- 编号，主键，自增
  orderId 	varchar(50) unique not null,				-- 订单编号
  userId 		varchar(50) not null,				-- 用户编号
  shopId        varchar(50) not null,				-- 店铺编号	
  getName		varchar(50),						-- 收货人（从地址表中获取）
  orderAddr 	varchar(100),					-- 订单地址
  orderTel 		varchar(20),					-- 联系方式
  totalPrice 	float,						-- 总价
  orderState 	tinyint,					-- 订单状态(支付2，未支付1)
  sendState 	tinyint,					-- 配送状态（）
  orderTime 	datetime,					-- 订单生成时间
  default1 	varchar(255),
  default2 	varchar(255)
);



/*
7、订单明细表
*/

drop table if exists orderdetail;
create table orderdetail(
  id 		int not null primary key auto_increment,	-- 编号，自增，主键
  orderId	 varchar(50) null,				    -- 订单编号
  userId 	varchar(50) not null,				-- 用户编号
  foodId 	varchar(50) not null,				-- 菜品编号
  foodName 	varchar(50) null,			        -- 菜品名称
  foodPrice 	float,						    -- 菜品单价
  foodNum 	int,						        -- 购买数量
  default2 	varchar(255),
  default3 	varchar(255)
);



/*
8、储值卡表
*/

drop table if exists valuecard;
create table valuecard(
  id 		int not null primary key auto_increment,	-- 编号，主键，自增
  userId 	varchar(50) not null,				-- 用户编号
  addMoney 	float,						-- 用户充值
  addTime 	datetime,					-- 充值时间
  default1 	varchar(255),
  default2 	varchar(255),
  default3 	varchar(255)
) auto_increment=1;


/*
9、收藏夹表
*/

drop table if exists collection;

create table collection (
  id 		int not null primary key auto_increment,	-- 编号，主键，自增
  userId 	varchar(50) not null,				-- 用户编号
  foodId 	varchar(50) not null,				-- 菜品编号
  shopId 	varchar(50) not null,				-- 餐厅编号
  collectTime 	datetime,					-- 收藏时间
  default1 	varchar(255),
  default2 	varchar(255),
  default3 	varchar(255)
) auto_increment=1;


/*
10、评价信息表
*/

drop table if exists estimateinfo;
create table estimateinfo(
  id 		int not null primary key auto_increment,	-- 编号
  orderId 	varchar(50) not null,				-- 订单编号
  userId 	varchar(50) not null,				-- 用户编号
  shopId    varchar(50) not null,				-- 餐厅编号
  estimateInfo 	varchar(200),					-- 评价内容
  estimateTime 	datetime,					-- 评价时间
  default1 	varchar(255),
  default2 	varchar(255),
  default3 	varchar(255)
)auto_increment=1;



/*
11、投诉信息表
*/
drop table if exists complaintinfo;
create table complaintinfo(
  id 		int not null primary key auto_increment,	-- 编号
  userId 	varchar(50) not null,				-- 用户编号
  orderId 	varchar(50) not null,				-- 订单编号
  shopId    varchar(50) not null,				-- 餐厅编号
  complaintQue 	varchar(200),					-- 投诉问题
  complaintAns 	varchar(200),					-- 投诉回复
  complaintState tinyint,					-- 投诉状态
  complaintTime datetime,					-- 投诉时间
  comReplyTime 	datetime,					-- 回复时间
  default1 	varchar(255),
  default2 	varchar(255),
  default3 	varchar(255)
) auto_increment=1;



/*
中国省市地区数据库
*/

/*
建立省级表
*/
drop table if exists province;
create table province(
  id int not null primary key,
  code varchar(6) not null,	-- 省份代码
  name varchar(20) not null	-- 省份名称
);

/*
创建市级表
*/
drop table if exists city;

create table city(
  id int not null primary key,		-- 编号
  code varchar(6) not null,		-- 城市代码
  name varchar(20) not null,		-- 城市名称
  provincecode varchar(6) not null	-- 所属省份代码
);




/*城市区县表*/
drop table if exists area;

create table area(
  id int not null primary key,	-- 编号
  code varchar(6) not null,	-- 地区代码
  name varchar(20) not null,	-- 地区名称
  citycode varchar(6)		-- 所属城市代码
);

drop table if exists top;

CREATE TABLE `top` (
  `shopId` varchar(50) NOT NULL,        -- 店铺编号
  `shopName` varchar(50) DEFAULT NULL,  -- 店铺名称
  `shopImg` varchar(200) DEFAULT NULL,  -- 店铺图片
  `orderNum` int(11) DEFAULT NULL,      -- 订单数量
  `orderRank` int(11) DEFAULT NULL,       -- 排名 
  `shopAddr` varchar(100) DEFAULT NULL,   -- 店铺地址
  `shopTel` varchar(20) DEFAULT NULL,    -- 店铺电话
  `startPrice` float DEFAULT NULL,    -- 起送价
  `sendAdd` float DEFAULT NULL,    -- 配送费
  `sendTime` int(11) DEFAULT NULL,    -- 配送时间
  `shopNotice` varchar(200) DEFAULT NULL,    -- 店铺公告
  PRIMARY KEY (`shopId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





