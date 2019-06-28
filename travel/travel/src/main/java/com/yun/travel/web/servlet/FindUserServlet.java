package com.yun.travel.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yun.travel.domain.ResultInfo;
import com.yun.travel.domain.User;

public class FindUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user =(User)request.getSession().getAttribute("user");
		ResultInfo info = new ResultInfo();
		response.setContentType("application/json;charset=utf-8");
		if(user!=null) {
			info.setData(user);
			info.setFlag(true);
		}else{
			info.setFlag(false);
		}
		ObjectMapper om = new ObjectMapper();
		String userJson = om.writeValueAsString(info);
		response.getWriter().write(userJson);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
