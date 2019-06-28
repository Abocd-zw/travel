package com.yun.travel.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.yun.travel.dao.FavoriteDao;
import com.yun.travel.dao.RouteDao;
import com.yun.travel.dao.impl.FavoriteDaoImpl;
import com.yun.travel.dao.impl.RouteDaoImpl;
import com.yun.travel.domain.PageBean;
import com.yun.travel.domain.Route;
import com.yun.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
	private FavoriteDao favoriteDao = new FavoriteDaoImpl();
	private RouteDao routeDao = new RouteDaoImpl();
	@Override
	public void doFavorite(int rid, int uid) {
		//向favorite表中插入数据;
		favoriteDao.add(rid,uid);
		
		//根据rid在favorite表中查询该路线的收藏总次数
		int count = favoriteDao.queryCountByRid(rid);
		
		//修改route表中的收藏次数count;
		RouteDao routeDao = new RouteDaoImpl();
		routeDao.updateCountByRid(count,rid);
	}
	@Override
	public PageBean<Route> findMyFavorite(int uid, int currentPage, int pageSize) {
		
		//通过uid查询用户收藏的rid
		List<Integer> listRid = favoriteDao.findByUid(uid);
		
		//创建PageBean 对象
		PageBean<Route> pb = new PageBean<>();
		
		//总记录数为listRid的长度
		int totalCount = listRid.size();
		pb.setTotalCount(totalCount);
		
		//总页数
		int totalPage =  totalCount%pageSize == 0 ? totalCount/pageSize : totalCount/pageSize+1;
		pb.setTotalPage(totalPage);
		
		int start = (currentPage-1)*pageSize;
		return pb;
		
	}
	
	@Override
	public PageBean<Route> pageQueryFavorite(int currentPage, int pageSize) {
		PageBean<Route> pb = new PageBean<>();
		//设置总记录为100，查询前一百的收藏数;
		pb.setTotalCount(100);
		
		//总页数
		int totalPage = 100%pageSize ==0 ? 100/pageSize :100/pageSize +1;
		pb.setTotalPage(totalPage);
		pb.setCurrentPage(currentPage);
		pb.setPageSize(pageSize);
		//根据currentPage,pageSize 获取分页后的查询记录
		List<Route> list = routeDao.pageQueryFavorite(currentPage,pageSize);
		pb.setList(list);
		return pb;
	}}
