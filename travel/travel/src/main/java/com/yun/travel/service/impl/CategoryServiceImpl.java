package com.yun.travel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.yun.travel.dao.CategoryDao;
import com.yun.travel.dao.impl.CategoryDaoImpl;
import com.yun.travel.domain.Category;
import com.yun.travel.domain.PageBean;
import com.yun.travel.domain.Route;
import com.yun.travel.service.CategoryService;
import com.yun.travel.util.JedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

public class CategoryServiceImpl implements CategoryService {
	private CategoryDao dao = new CategoryDaoImpl();
	private Jedis jedis = JedisUtil.getJedis();
	@Override
	public List<Category> findAll() {
		//从缓存中获取categorys数据
		Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
		List<Category> list = null;
		//判断categorys是否为空
		if(categorys!=null && categorys.size()>0){
			list = new ArrayList<>();
			for (Tuple tuple : categorys) {
				Category c = new Category();
				c.setCname(tuple.getElement());
				c.setCid((int)tuple.getScore());
				list.add(c);
			}
		}else{
			System.out.println("从mysql中读取数据");
			list = dao.findAll();
			for (Category category : list) {
				jedis.zadd("category", category.getCid(),category.getCname());
			}
		}
		return list;
	}
	
	@Override
	public PageBean<Route> getPageBean(int cid, int currentPage, int pageSize,String rname) {
		//1.创建pageBean对象
		PageBean<Route> pb = new PageBean<>();
		
		//2.封装总记录数
		int totalCount = dao.findTotalCount(cid,rname);
		pb.setTotalCount(totalCount);
		
		//3.总页数
		int totalPage = totalCount % pageSize == 0 ? totalCount/pageSize : totalCount/pageSize +1;
		pb.setTotalPage(totalPage);
		
		//4.当前页
		pb.setCurrentPage(currentPage);
		
		//5.每页条数
		pb.setPageSize(pageSize);
		
		//6.分页数据
		int start  = (currentPage -1)*pageSize;
		List<Route> list = dao.findPageRoutes(cid,start,pageSize,rname);
		pb.setList(list);
		return pb;
	}

}
