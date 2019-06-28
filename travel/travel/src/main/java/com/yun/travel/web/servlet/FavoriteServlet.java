package com.yun.travel.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yun.travel.domain.PageBean;
import com.yun.travel.domain.Route;
import com.yun.travel.domain.User;
import com.yun.travel.service.FavoriteService;
import com.yun.travel.service.impl.FavoriteServiceImpl;

public class FavoriteServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private FavoriteService service = new FavoriteServiceImpl();
	public void doFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ridStr = request.getParameter("rid");
		String uidStr = request.getParameter("uid");
		int rid=0;
		if(ridStr!=null && !"".equals(ridStr)){
			rid = Integer.parseInt(ridStr); 
		}
		int uid=0;
		if(uidStr!=null && !"".equals(ridStr)){
			uid = Integer.parseInt(uidStr); 
		}
		
		service.doFavorite(rid,uid);
	}
	//查询用户收藏
	public void findMyFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		int uid = user.getUid();
		System.out.println(uid);
		
		String currentPageStr = request.getParameter("currentPage");
		String pageSizeStr = request.getParameter("pageSize");
		int currentPage=1;
		if(currentPageStr!=null && !"".equals(currentPageStr)){
			currentPage=Integer.parseInt(currentPageStr);
		} 
		int pageSize = 8;
		if(pageSizeStr!=null && !"".equals(pageSizeStr)){
			pageSize=Integer.parseInt(pageSizeStr);
		} 
		PageBean<Route> list = service.findMyFavorite(uid,currentPage,pageSize);
		System.out.println(writeValueAsString(list));
		writeValue(response,list);
	}
	//查询总收藏榜
	public void pageQueryFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String currentPageStr = request.getParameter("currentPage");
		String pageSizeStr = request.getParameter("pageSize");
		int currentPage = 1;
		if(currentPageStr!=null && !"".equals(currentPageStr)){
			currentPage=Integer.parseInt(currentPageStr);
		} 
		
		int pageSize = 10;
		if(pageSizeStr!=null && !"".equals(pageSizeStr)){
			pageSize=Integer.parseInt(pageSizeStr);
		} 
		PageBean<Route> pb = service.pageQueryFavorite(currentPage,pageSize);
		System.out.println(writeValueAsString(pb));
		writeValue(response,pb);
	}

}
