package com.yun.travel.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yun.travel.service.impl.UserServiceImpl;


public class ActiveUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//接收激活码
		String code = request.getParameter("code");
		// 调用service 的激活方法
	UserServiceImpl service = new UserServiceImpl();
	boolean flag;
	if(code!=null) {
		flag = service.active(code);
		PrintWriter out = response.getWriter();
		if(flag) {
			out.write("激活成功,请<a href = 'http://localhost/travel/login.html'>登录</a>");
		}else {
			out.write("激活失败");
		}
	}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
