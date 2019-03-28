INSERT INTO `is_users` (`id`, `display_name`, `email`, `last_login`, `password`, `salt`, `state_id`, `user_name`) VALUES ('1', 'test0', 'test0@hibernia-sino.com', CURRENT_TIMESTAMP, '9C65F0D2A545B128C5CDF12B9F522E0B', '2A6F3275294956C46F038BBC365E2561', '1', 'test0');
INSERT INTO `is_groups` (`id`, `gp_privilege_id`, `group_name`) VALUES ('1', '80', 'Customers');
INSERT INTO `is_users_group` (`appuser_id`, `appgroup_id`) VALUES ('1', '1');

