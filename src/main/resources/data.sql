INSERT INTO `provinces` (`code`, `chinese_name`, `english_name`) VALUES
('CN-AH', '安徽省', 'Anhui Sheng'),
('CN-BJ', '北京市', 'Beijing Shi'),
('CN-CQ', '重庆市', 'Chongqing Shi'),
('CN-FJ', '福建省', 'Fujian Sheng'),
('CN-GD', '广东省', 'Guangdong Sheng'),
('CN-GS', '甘肃省', 'Gansu Sheng'),
('CN-GX', '广西壮族自治区', 'Guangxi Zhuang Autonomous Region'),
('CN-GZ', '贵州省', 'Guizhou Sheng'),
('CN-HA', '河南省', 'Henan Sheng'),
('CN-HB', '湖北省', 'Hubei Sheng'),
('CN-HE', '河北省', 'Hebei Sheng'),
('CN-HI', '海南省', 'Hainan Sheng'),
('CN-HK', '香港特别行政区', 'Hong Kong Special Administrative Region'),
('CN-HL', '黑龙江省', 'Heilongjiang Sheng'),
('CN-HN', '湖南省', 'Hunan Sheng'),
('CN-JL', '吉林省', 'Jilin Sheng'),
('CN-JS', '江苏省', 'Jiangsu Sheng'),
('CN-JX', '江西省', 'Jiangxi Sheng'),
('CN-LN', '辽宁省', 'Liaoning Sheng'),
('CN-MO', '澳门特别行政区', 'Macau Special Administrative Region'),
('CN-NM', '內蒙古自治区', 'Inner Mongolia Autonomous Region'),
('CN-NX', '宁夏回族自治区', 'Ningxia Hui Autonomous Region'),
('CN-QH', '青海省', 'Qinghai Sheng'),
('CN-SC', '四川省', 'Sichuan Sheng'),
('CN-SD', '山东省', 'Shandong Sheng'),
('CN-SH', '上海市', 'Shanghai Shi'),
('CN-SN', '陕西省', 'Shaanxi Sheng'),
('CN-SX', '山西省', 'Shanxi Sheng'),
('CN-TJ', '天津市', 'Tianjin Shi'),
('CN-XJ', '新疆维吾尔自治区', 'Xinjiang Uyghur Autonomous Region'),
('CN-XZ', '西藏自治区', 'Tibet Autonomous Region'),
('CN-YN', '云南省', 'Yunnan Sheng'),
('CN-ZJ', '浙江省', 'Zhejiang Sheng');

INSERT INTO `provinces` (`code`, `chinese_name`, `english_name`) VALUES
('IE-CW', '卡娄郡', 'Country Carlow'),
('IE-CN', '卡文郡', 'Country Cavan'),
('IE-CE', '克莱尔郡', 'Country Clare'),
('IE-CO', '科克郡', 'Country Cork'),
('IE-DL', '当尼戈尔郡', 'Country Donegal'),
('IE-D', '都柏林郡', 'Country Dublin'),
('IE-G', '高维郡', 'Country Galway'),
('IE-KY', '凯瑞郡', 'Country Kerry'),
('IE-KE', '基尔代尔郡', 'Country Kildare'),
('IE-KK', '基尔肯尼郡', 'Country Kilkenny'),
('IE-LS', '利施郡', 'Country Laois'),
('IE-LM', '利特里姆郡', 'Country Leitrim'),
('IE-LK', '利默里克郡', 'Country Limerick'),
('IE-LD', '朗福德郡', 'Country Longford'),
('IE-LH', '劳斯郡', 'Country Louth'),
('IE-MO', '梅欧郡', 'Country Mayo'),
('IE-MH', '米斯郡', 'Country Meath'),
('IE-MN', '莫纳亨郡', 'Country Monaghan'),
('IE-OY', '奥法利郡', 'Country Offaly'),
('IE-RN', '罗斯康门郡', 'Country Roscommon'),
('IE-SO', '斯莱戈郡', 'Country Sligo'),
('IE-TA', '蒂珀雷里郡', 'Country Tipperary'),
('IE-WD', '沃特福德郡', 'Country Waterford'),
('IE-WH', '西米斯郡', 'Country Westmeath'),
('IE-WX', '韦克斯福德郡', 'Country Wexford'),
('IE-WW', '威克娄郡', 'Country Wicklow');
INSERT INTO `app_group` (`id`, `gp_privilege_id`, `group_name`) VALUES ('1', '80', 'Customers');
INSERT INTO `app_group` (`id`, `gp_privilege_id`, `group_name`) VALUES ('2', '60', 'Employees');
INSERT INTO `customer_details` (`id`, `address`, `address2`, `first_name`, `id_number`, `last_name`, `phone_number`, `province`, `zip_code`) VALUES
(1, 'No.100 Pingleyuan Chaoyang Distinct', '', 'Tong', '1234567890', 'Ge', '+8615501101001', 'CN-BJ', '102049');
INSERT INTO `customer_details` (`id`, `address`, `address2`, `first_name`, `id_number`, `last_name`, `phone_number`, `province`, `zip_code`) VALUES
(2, 'No.100 Pingleyuan Chaoyang Distinct', '', 'Tong', '1234567899', 'Ge', '+8618645068587', 'CN-BJ', '102049');
INSERT INTO `app_user` (`id`, `display_name`, `email`, `last_login`, `password`, `salt`, `state_id`, `user_name`, `details_id`) VALUES ('1', 'customer0', 'customer0@hibernia-sino.com', CURRENT_TIMESTAMP, '9C65F0D2A545B128C5CDF12B9F522E0B', '2A6F3275294956C46F038BBC365E2561', '1', 'customer0', '1');
INSERT INTO `app_user` (`id`, `display_name`, `email`, `last_login`, `password`, `salt`, `state_id`, `user_name`, `details_id`) VALUES ('2', 'staff0', 'staff0@hibernia-sino.com', CURRENT_TIMESTAMP, '368D087BA8E014EBACBF2901F0F9474F', 'D1F60DC68055AC247A129553737AED78', '1', 'staff0','2');
INSERT INTO `user_group` (`appuser_id`, `appgroup_id`) VALUES ('1', '1');
INSERT INTO `user_group` (`appuser_id`, `appgroup_id`) VALUES ('2', '2');

INSERT INTO `insurance_policy_products` (`id`, `agreement_path`, `insurance_abstract`, `insurance_abstract_chinese`, `insurance_chinese_name`, `insurance_name`) VALUES ('1', NULL, 'Luggage insurance', '旅游行李险', '旅游行李险', 'Luggage insurance');
INSERT INTO `insurance_policy_record` (`id`, `destination`, `end_datetime`, `start_datetime`, `total_paid`, `customer_id`, `insurance_product_id`) VALUES ('1', 'Ireland', '2019-06-01 00:00:00', '2019-03-01 00:00:00', '1234', '1', '1');
INSERT INTO `insurance_claim` (`id`, `policy_id`, `user_id`, `claim_step`, `result`, `date_time`) VALUES ('1', '1','1','4','success','2019-04-27 13:04:51');
INSERT INTO `insurance_claim` (`id`, `policy_id`, `user_id`, `claim_step`, `result`, `date_time`) VALUES ('2', '1','1','4','fail','2019-04-26 15:24:11');
INSERT INTO `insurance_claim` (`id`, `policy_id`, `user_id`, `claim_step`, `result`, `date_time`) VALUES ('3', '1','1','3','','2019-04-27 17:43:33');
INSERT INTO `insurance_claim` (`id`, `policy_id`, `user_id`, `claim_step`, `result`, `date_time`) VALUES ('4', '1','1','2','','2019-04-27 12:35:43');
INSERT INTO `insurance_claim` (`id`, `policy_id`, `user_id`, `claim_step`, `result`, `date_time`) VALUES ('5', '1','1','4','success','2019-04-27 13:04:51');