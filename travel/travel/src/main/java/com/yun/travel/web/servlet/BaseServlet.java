package com.yun.travel.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取请求的路径资源
		String uri = request.getRequestURI();
		System.out.println(uri);
		
		String methodName = uri.substring(uri.lastIndexOf("/")+1);
		//使用反射创建方法对象
		Method method=null;
		try {
			 method = this.getClass().getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			 method.invoke(this, request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	}
	public void writeValue(HttpServletResponse response,Object obj){
		ObjectMapper om = new ObjectMapper();
		response.setContentType("application/json;charset=utf-8");
		try {
			om.writeValue(response.getOutputStream(), obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String writeValueAsString(Object obj){
		ObjectMapper om = new ObjectMapper();
		 String json="";
		try {
			json = om.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		 return json;
	}

}
