package com.yun.service.impl;

import com.yun.dao.UserDao;
import com.yun.domain.User;
import com.yun.service.UserService;
import com.yun.utils.MailUtils;
import com.yun.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public boolean regist(User user) {
        User u =  userDao.findByUsername(user.getUsername());
        if(u!=null) { // 用户名已存在
            return false;
        }
        //注册用户，
        user.setStatus("N");//刚注册用户，设置为未激活状态
        user.setCode(UuidUtil.getUuid());
        userDao.save(user);

        String content = "点击<a href='http://localhost/travel/user/activeUser?code="+ user.getCode()+"'>激活</a>您的账号";
        MailUtils.sendMail(user.getEmail(),content,"旅游网激活邮件");
        return true;
    }

    @Override
    public boolean active(String code) {
        //根据code查找用户
        User u = userDao.findByCode(code);
        if(u!=null) {
            userDao.updateStatus(u);
            return true;
        }
        return false;
    }

    @Override
    public User login(String username,String password) {
        User u = userDao.findByUsernameAndPassword(username,password);
        return u;
    }

    @Override
    public List<User> findByName(String name) {

       return  userDao.findByName(name);
    }
}
