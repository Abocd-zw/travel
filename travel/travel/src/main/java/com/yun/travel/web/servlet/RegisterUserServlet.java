package com.yun.travel.web.servlet;

import java.io.IOException;
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

/**
 * 用户注册校验
 * @author 阿博茨达
 *
 */
public class RegisterUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//创建封装结果类,用于封装结果
		ResultInfo info = new ResultInfo();
		//创建ObjectMapper,用于将结果类转换成json语句;
		ObjectMapper om = new ObjectMapper();
		response.setContentType("application/json;charset=utf-8");
		//获取用户输入的校验码
		String vcode = request.getParameter("check");
		System.out.println(vcode);
		//获取后台生成传给前台页面的校验码
		String  checkCode = (String)request.getSession().getAttribute("checkCode");
		if(vcode!=null && !vcode.equalsIgnoreCase(checkCode)){
			info.setFlag(false);
			info.setErrorMsg("验证码错误");
			String infoJson = om.writeValueAsString(info);
			System.out.println(infoJson);
			response.getWriter().write(infoJson);
			 return;
		}
		//接收用户数据
		Map<String, String[]> map = request.getParameterMap();
		//将用户数据封装到user中
		User user = new User();
		
		try {
			BeanUtils.populate(user, map);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.out.println(user);
		//调用service的注册方法
		UserService service = new UserServiceImpl();
		boolean flag = service.regist(user);
		if(flag) {
			info.setFlag(flag);
		}else {
			info.setFlag(flag);
			info.setErrorMsg("用户名已存在");
		}
		String infoJson = om.writeValueAsString(info);
		System.out.println(infoJson);
		response.getWriter().write(infoJson);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
