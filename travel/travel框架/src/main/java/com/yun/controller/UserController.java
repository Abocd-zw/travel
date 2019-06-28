package com.yun.controller;

import com.yun.domain.ResultInfo;
import com.yun.domain.User;
import com.yun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/register")
    public @ResponseBody ResultInfo register(@RequestBody User user ,HttpServletRequest request){
        String check = user.getCheck();
        // 创建封装结果类，用于封装结果
        ResultInfo info = new ResultInfo();
        // 获取后台生成的验证码
        String  checkCode = (String) request.getSession().getAttribute("checkCode");
        if(check!=null && !check.equalsIgnoreCase(checkCode)){
            info.setErrorMsg("验证码错误");
            info.setFlag(false);
            return info;
        }
        //验证码正确，调用service的注册方法
        boolean flag = userService.regist(user);
        //根据返回值判断是否注册成功
        if(flag) {
            info.setFlag(flag);
        }else{
            info.setFlag(flag);
            info.setErrorMsg("用户已存在");
        }
        return info;
    }

    @RequestMapping("/activeUser")
    public void activeUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
       //1.接收激活码
        String code = request.getParameter("code");
        boolean flag;
        if(code !=null){
            flag = userService.active(code);
            PrintWriter out = response.getWriter();
            if(flag){
                out.write("激活成功，请<a href='http://localhost/travel/login.html'>登录</a>");
            }else{
                out.write("激活失败");
            }
        }
    }

    @RequestMapping("/login")
    public @ResponseBody ResultInfo login(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User u = userService.login(username,password);
        ResultInfo info = new ResultInfo();
        if(u!=null){
            if(!"Y".equals(u.getStatus())){
                info.setErrorMsg("请先激活");
                info.setFlag(false);
            }else{
                request.getSession().setAttribute("user",u);
                info.setFlag(true);
            }
        }else{
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        return info;
    }
    @RequestMapping("/find")
    public @ResponseBody ResultInfo find(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        ResultInfo info = new ResultInfo();

        if(user==null){
            info.setFlag(false);
        }else{
            info.setFlag(true);
            info.setData(user);
        }
        return info;
    }

    @RequestMapping("/exit")
    public void  exit(HttpServletRequest request,HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect("/travel/login.html");
    }
}
