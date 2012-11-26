--
-- Table structure for table `weblog`
--

CREATE TABLE IF NOT EXISTS `weblog` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '編號',
  `data` varchar(255) NOT NULL COMMENT '傳入的資料',
  `post_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '發佈時間',
  PRIMARY KEY (`log_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='訊息記錄' AUTO_INCREMENT=1 ;