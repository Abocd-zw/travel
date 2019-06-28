package com.yun.travel.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yun.travel.domain.Category;
import com.yun.travel.domain.PageBean;
import com.yun.travel.domain.Route;
import com.yun.travel.service.CategoryService;
import com.yun.travel.service.impl.CategoryServiceImpl;

public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private CategoryService service = new CategoryServiceImpl();
	public  void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 List<Category> list = service.findAll();
		 System.out.println(writeValueAsString(list));
		 writeValue(response,list);
	}
	public void pageQueryRoutes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 //1.获取从前台页面传回的数据
		String cidStr = request.getParameter("cid");
		String currentPageStr = request.getParameter("currentPage");
		String pageSizeStr = request.getParameter("pageSize");
		
		//补充
		String rname = request.getParameter("rname");
		rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
		System.out.println(rname);
		if("null".equals(rname)){
			rname=null;
		}
		//2.转换数据
		
		int cid=0;
		if(cidStr !=null && cidStr.length()>0 && !"null".equals(cidStr)){
			cid=Integer.parseInt(cidStr);
		}
		
		int currentPage = 0;
		if(currentPageStr !=null && currentPageStr.length()>0){
			currentPage=Integer.parseInt(currentPageStr);
		}else{
			currentPage =1;
		}
		
		int pageSize = 0;
		if(pageSizeStr !=null && pageSizeStr.length()>0){
			pageSize=Integer.parseInt(pageSizeStr);
		}else{
			pageSize =5;
		}
		//3.调用service的封装pagebean方法
		PageBean<Route> pb = service.getPageBean(cid,currentPage,pageSize,rname);
		
		//4.将pagebean序列化为json响应回去
		System.out.println(writeValueAsString(pb));
		writeValue(response,pb);
	}
}
