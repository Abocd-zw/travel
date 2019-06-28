package com.yun.travel.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yun.travel.domain.ResultInfo;
import com.yun.travel.domain.User;
import com.yun.travel.service.UserService;
import com.yun.travel.service.impl.UserServiceImpl;

public class LoginUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1,校验验证码
		String  checkCode =(String)request.getSession().getAttribute("checkCode");
		String vCode = request.getParameter("check");
		ResultInfo info = new ResultInfo();
		ObjectMapper om = new ObjectMapper();
		response.setContentType("application/json;charset=utf-8");
		if(vCode!=null && !vCode.equalsIgnoreCase(checkCode) ){
			info.setFlag(false);
			info.setErrorMsg("验证码错误");
			om.writeValue(response.getOutputStream(),info);
			return;
		}
		// 校验用户名和密码
		Map<String, String[]> map = request.getParameterMap();
		User user = new User();
		try {
			BeanUtils.populate(user, map);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		UserService sevice = new UserServiceImpl();
		User u = sevice.login(user);
		if(u!=null) {
			if("Y".equals(u.getStatus())){
				info.setFlag(true);
				request.getSession().setAttribute("user", u);
			}else{
				info.setFlag(false);
				info.setErrorMsg("用户未激活，请先激活");
			}
		}else{
			info.setFlag(false);
			info.setErrorMsg("用户名或密码错误");
		}
		String loginJson = om.writeValueAsString(info);
		response.getWriter().write(loginJson);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
