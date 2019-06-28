package com.yun.travel.dao;

import com.yun.travel.domain.User;

public interface UserDao {
	//根据用户名查找用户
	User findByUsername(String username);
	//添加用户
	void save(User user);
	//根据激活码查找用户
	User findByCode(String code);
	//更新用户激活状态
	void updateStatus(User user);
	//根据用户名和密码查找用户
	User findByUsernameAndPassword(String username, String password);
	
}
