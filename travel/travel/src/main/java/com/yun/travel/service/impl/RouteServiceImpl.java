package com.yun.travel.service.impl;

import java.util.List;

import com.yun.travel.dao.CategoryDao;
import com.yun.travel.dao.FavoriteDao;
import com.yun.travel.dao.RouteDao;
import com.yun.travel.dao.RouteImgDao;
import com.yun.travel.dao.SellerDao;
import com.yun.travel.dao.impl.CategoryDaoImpl;
import com.yun.travel.dao.impl.FavoriteDaoImpl;
import com.yun.travel.dao.impl.RouteDaoImpl;
import com.yun.travel.dao.impl.RouteImgDaoImpl;
import com.yun.travel.dao.impl.SellerDaoImpl;
import com.yun.travel.domain.Category;
import com.yun.travel.domain.Favorite;
import com.yun.travel.domain.Route;
import com.yun.travel.domain.RouteImg;
import com.yun.travel.domain.Seller;
import com.yun.travel.service.RouteService;

public class RouteServiceImpl implements RouteService {
	private RouteDao routeDao = new RouteDaoImpl();
	private SellerDao sellerDao = new SellerDaoImpl();
	private RouteImgDao routeImgDao = new RouteImgDaoImpl();
	private  FavoriteDao favoriteDao = new FavoriteDaoImpl();
	private CategoryDao categoryDao = new CategoryDaoImpl();
	@Override
	public Route findRoute(int rid) {
		//1.调用dao层，通过rid获取到Route对象
		Route route = routeDao.findByRid(rid);
		if(route==null){
			return null;
		}
		//2.通过Route对象获取到商家sid
		int sid = route.getSid();
		int cid = route.getCid();
		//3.通过sid获取商家对象Seller
		Seller seller = sellerDao.findBySid(sid);
		// 将商家存入Route中
		route.setSeller(seller);
		
		//4. 通过rid获取到图片对象集合
		List<RouteImg> list = routeImgDao.findImgByRid(rid);
		//将图片集合存入
		route.setRouteImgList(list);
		
		
		//5.通过cid获取分类对象category
		Category category = categoryDao.findByCid(cid);
		route.setCategory(category);
		return route;
	}
	@Override
	public boolean isRed(int rid, int uid) {
		Favorite f = favoriteDao.findByRidAndUid(rid,uid);
		return f==null;
	}

}
