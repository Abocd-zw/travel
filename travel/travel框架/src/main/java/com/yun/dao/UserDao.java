package com.yun.dao;

import com.yun.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserDao {

    @Select("select * from tab_user")
    List<User> findAll();

    @Select("select * from tab_user where username=#{username}")
    User findByUsername(String username);

    @Insert("insert into tab_user (username,password,name,birthday,sex,telephone,email,status,code) values (#{username},#{password},#{name},#{birthday},#{sex},#{telephone},#{email},#{status},#{code})")
    void save(User user);

    @Select("select * from tab_user where code=#{code}")
    User findByCode(String code);

    @Update("update tab_user set status='Y' where username=#{username}")
    void updateStatus(User u);

    @Select("select * from tab_user where username=#{username} and password=#{password}")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password")String password);

    @Select("select * from tab_user where name like concat('%',#{name},'%')")
    List<User> findByName(String name);
}
