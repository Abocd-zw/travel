package com.yun.travel.service;

import com.yun.travel.domain.User;

public interface UserService {

	boolean regist(User user);

	boolean active(String code);

	User login(User user);
	
}
