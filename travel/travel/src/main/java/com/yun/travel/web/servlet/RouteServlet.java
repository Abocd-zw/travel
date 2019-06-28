package com.yun.travel.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yun.travel.domain.Route;
import com.yun.travel.domain.User;
import com.yun.travel.service.RouteService;
import com.yun.travel.service.impl.RouteServiceImpl;


public class RouteServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private RouteService service = new RouteServiceImpl();
	public void findRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取到rid
		String ridStr = request.getParameter("rid");
		int rid=0;
		if(ridStr!=null && !"".equals(ridStr)) {
			  rid = Integer.parseInt(ridStr);
		}
		Route route = service.findRoute(rid);
		
		writeValue(response,route);
	}
	public void isRed(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取到rid
		String ridStr = request.getParameter("rid");
		int rid=0;
		if(ridStr!=null && !"".equals(ridStr)) {
			  rid = Integer.parseInt(ridStr);
		}
		boolean isRed;
		//判断用户是否登录
		HttpSession session = request.getSession();
		User user =(User) session.getAttribute("user");
		if(user!=null) {
			int uid = user.getUid();
			isRed = service.isRed(rid,uid);
		}else{
			isRed = true;
		}
		writeValue(response,isRed);
	}

}
