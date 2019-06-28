package com.yun.travel.service.impl;

import com.yun.travel.dao.UserDao;
import com.yun.travel.dao.impl.UserDaoImpl;
import com.yun.travel.domain.User;
import com.yun.travel.service.UserService;
import com.yun.travel.util.MailUtils;
import com.yun.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
	private UserDao userdao = new UserDaoImpl();
	@Override
	public boolean regist(User user) {
		User u = userdao.findByUsername(user.getUsername());
		if(u!=null) {
			return false;
		}
		user.setStatus("N");
		user.setCode(UuidUtil.getUuid());
		userdao.save(user);
		String content = "点击<a href='http://192.168.43.181/travel/user/active?code="+ user.getCode()+"'>【激活】</a>您的账号";
		MailUtils.sendMail(user.getEmail(), content,"旅游网激活邮件");
		return true;
	}
	@Override
	public boolean active(String code) {
		User user = userdao.findByCode(code);
		if(user != null) {
			userdao.updateStatus(user);
			return true;
		}
		return false;
	}
	@Override
	public User login(User user) {
		return userdao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
	}
	 
}
